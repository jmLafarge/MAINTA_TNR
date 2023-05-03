package my

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.io.FileType
import my.Log
import my.SQL as MYSQL
import my.InfoBDD
import my.result.TNRResult

public class PREJDDFiles {


	public static Map <String,String> PREJDDfilemap = [:]


	public static load() {

		Log.addSubTITLE("Load PREJDDfileList",'-',120,1)
		Log.addINFO("\t"+'MODOBJ'.padRight(11) + 'JDDFULLNAME',1)
		Log.addINFO('',1)

		new File(my.PropertiesReader.getMyProperty('PREJDD_PATH')).eachFileRecurse(FileType.FILES) { file ->
			// keep only TC Name like PREJDD.*.xlsx
			if (file.getName()==~ /PREJDD\..*\.xlsx/ && file.getPath()==~ /^((?!standby).)*$/) {
				String modObj = file.getName().replace('PREJDD.','').replace('.xlsx','')
				PREJDDfilemap.put(modObj,file.getPath())
				Log.addINFO('\t' + modObj.padRight(11) + file.getPath(),1)
			}
		}
	}



	public static String getFullName(String modObj){

		return PREJDDfilemap.getAt(modObj)
	}



	static insertPREJDDinDB(String modObj, String tabName) {
		Log.addDEBUG("insertPREJDDinDB() modObj = '$modObj' tabName = '$tabName'")
		def myJDD = new my.JDD(JDDFiles.JDDfilemap.getAt(modObj),tabName)

		TNRResult.addSTEP("Lecture du PREJDD : '" + PREJDDfilemap.getAt(modObj)+ " ($tabName)")
		XSSFWorkbook book = my.XLS.open(PREJDDfilemap.getAt(modObj))
		// set tab (sheet)
		Sheet sheet = book.getSheet(tabName)
		Row row0 = sheet.getRow(0)

		Map sequence = [:]
		def maxORDRE = null

		List PKlist=InfoBDD.getPK(myJDD.getDBTableName())

		// for each data line
		for (int numline : (1..sheet.getLastRowNum())) {
			Row row = sheet.getRow(numline)
			// exit if lastRow
			if (!row || my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}
			List fields =[]
			List values =[]
			String val = ''
			List PKwhere = []
			for (Cell c in row) {
				String fieldName = my.XLS.getCellValue(row0.getCell(c.getColumnIndex()))
				if (fieldName=="") {
					break
				}



				def value = my.XLS.getCellValue(c)
				Log.addDEBUG("\t\tCell value = '$value' getClass()=" + value.getClass(),2)
				if (c.getColumnIndex()>0) {
					Log.addDEBUG("\t\tAjout fieldName='$fieldName' value='$value' in req SQL",2)

					if (!my.JDDKW.isNU(value.toString()) && !myJDD.isOBSOLETE(fieldName)) {
						fields.add(fieldName)
					}



					// cas d'un champ lié à une séquence
					String seqTable = myJDD.getParamForThisName('SEQUENCE',fieldName)
					if (seqTable){
						if (sequence.containsKey(seqTable)) {
							if (value > sequence.getAt(seqTable)) {
								sequence.put(seqTable, value)
							}
						}else {
							TNRResult.addDETAIL("Détection d'une sequence sur $fieldName, table $seqTable")
							sequence.put(seqTable, value)
						}
					}

					switch (value) {

						case my.JDDKW.getKW_ORDRE() :
							if (maxORDRE == null) {
								maxORDRE = MYSQL.getMaxFromTable(fieldName, myJDD.getDBTableName())
							}
							maxORDRE++
							val = maxORDRE.toString()
							break

						case my.JDDKW.getKW_NULL() :
							val = null
							break

						case my.JDDKW.getKW_VIDE() :
							val = ''
							break

						case my.JDDKW.getKW_DATE() :
							def now = new Date()
							val =  now.format('yyyy-dd-MM')
							break

						case my.JDDKW.getKW_DATETIME() :
							def now = new Date()
							val = now.format('yyyy-dd-MM HH:mm:ss.SSS')
							break
						default :

							if (value instanceof java.util.Date) {
								Log.addDEBUG("\t\t instanceof java.util.Date = TRUE")
								val = value.format('yyyy-dd-MM HH:mm:ss.SSS')

							}else {
								val = value.toString()
							}
							break
					}

					if (InfoBDD.isImage(myJDD.getDBTableName(), fieldName)) {
						values.add(getRTFTEXT(value).getBytes())
					}else if (!my.JDDKW.isNU(value.toString()) && !myJDD.isOBSOLETE(fieldName) ) {
						values.add(val)
					}

					if (PKlist.contains(fieldName)) {
						PKwhere.add("$fieldName = '$val'")

					}

				}
			}

			insertIfNotExist(myJDD.getDBTableName(), PKwhere.join(' AND '), fields, values)

		}

		if (sequence.size()>0) {
			sequence.each { table, val ->
				String req = "DBCC CHECKIDENT ($table, RESEED,$val);"
				TNRResult.addDETAIL(req)
				MYSQL.executeSQL(req)
				//fileSQL.append("$req\n")
			}
		}
	}




	static insertIfNotExist(String table, String PKwhere, List fields, List values) {

		Log.addDEBUG("SELECT count(*) FROM $table WHERE $PKwhere")
		def result = MYSQL.getFirstRow("SELECT count(*) FROM $table WHERE $PKwhere")
		if(result) {
			if (result[0] == 0) {

				List li = Collections.nCopies(fields.size(), '?')

				String requete = "INSERT INTO $table (" + fields.join(',') +") VALUES (" + li.join(',') + ")"

				TNRResult.addDETAIL(requete)
				TNRResult.addDETAIL(values.join(','))
				try {
					def resultat = MYSQL.sqlInstance.executeInsert(requete,values)
				} catch(Exception ex) {
					Log.addERROR("Erreur d'execution de insertIfNotExist : ")
					TNRResult.addDETAIL(ex.getMessage())
				}

			}else if (result[0] ==1){
				Log.addDEBUG("La valeur $PKwhere existe déjà")
			}else {
				Log.addERROR("Plusieurs valeurs trouvées pour :")
				TNRResult.addDETAIL("SELECT count(*) FROM $table WHERE $PKwhere")
			}
		}
	}


	static add(String modObj,String fullName) {
		PREJDDfilemap.put(modObj,fullName)
	}



	private static String getRTFTEXT(String val) {
		String strBegin ="{\\rtf1\\fbidis\\ansi\\ansicpg0\\uc1\\deff0\\deflang0\\deflangfe0{\\fonttbl{\\f0\\fnil Arial;}}{\\colortbl;}{\\stylesheet{\\s0\\fi0\\li0\\ql\\ri0\\sb0\\sa0 Paragraph Style;}{\\*\\cs1\\f0\\fs24 Font Style;}}\\pard\\s0\\fi0\\li0\\ql\\ri0\\sb0\\sa0\\itap0 \\plain \\cs1\\f0\\fs24 "
		String strEnd	="\\par}"
		return strBegin+val+strEnd
	}

} // end of class
