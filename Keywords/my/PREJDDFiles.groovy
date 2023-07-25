package my

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.io.FileType
import groovy.transform.CompileStatic
import my.SQL
import my.NAV
import my.JDDKW

@CompileStatic
public class PREJDDFiles {


	public static Map <String,String> PREJDDfilemap = [:]


	static {
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
		Log.addTrace("insertPREJDDinDB() modObj = '$modObj' tabName = '$tabName'")
		def myJDD = new my.JDD(JDDFiles.JDDfilemap.getAt(modObj),tabName)

		Log.addINFO("Traitement de : '" + PREJDDfilemap.getAt(modObj)+ " ($tabName)")
		XSSFWorkbook book = my.XLS.open(PREJDDfilemap.getAt(modObj))
		// set tab (sheet)
		Sheet sheet = book.getSheet(tabName)
		Row row0 = sheet.getRow(0)

		Map <String,Integer> sequence = [:]
		int maxORDRE = 0

		List PKlist=InfoBDD.getPK(myJDD.getDBTableName())

		// for each data line
		for (int numline : (1..sheet.getLastRowNum())) {
			Row row = sheet.getRow(numline)
			// exit if lastRow
			String cdt = my.XLS.getCellValue(row.getCell(0))
			if (!row || cdt =='') {
				break
			}
			Log.addTrace("",0)
			Log.addTrace("Traitement $cdt",0)

			List fields =[]
			List values =[]
			String val = ''
			List PKwhere = []
			for (Cell c in row) {
				String fieldName = my.XLS.getCellValue(row0.getCell(c.getColumnIndex()))
				if (fieldName=="") {
					break
				}



				def valueOfJDD = my.XLS.getCellValue(c)
				Log.addTrace("\t\tCell value = '$valueOfJDD' getClass()=" + valueOfJDD.getClass(),2)

				if (c.getColumnIndex()>0) {
					Log.addTrace("\t\tAjout fieldName='$fieldName' value='$valueOfJDD' in req SQL",2)

					if (!my.JDDKW.isNU(valueOfJDD.toString()) && !myJDD.isOBSOLETE(fieldName)) {
						fields.add(fieldName)
					}



					// cas d'un champ lié à une séquence
					String seqTable = myJDD.getParamForThisName('SEQUENCE',fieldName)
					if (seqTable && !JDDKW.isNULL(valueOfJDD)){
						int seq = (int)valueOfJDD
						if (sequence.containsKey(seqTable)) {
							if (seq > sequence.getAt(seqTable)) {
								sequence.put(seqTable, seq)
							}
						}else {
							Log.addTrace("Détection d'une sequence sur $fieldName, table $seqTable")
							sequence.put(seqTable, seq)
						}
					}



					// cas d'un champ lié à une FOREIGNKEY
					String FK = myJDD.getParamForThisName('FOREIGNKEY',fieldName)
					if (FK) {

						Log.addTrace("Détection d'une FK sur $fieldName, FK= $FK")

						if (!my.JDDKW.isNULL(valueOfJDD.toString()) && !my.JDDKW.isVIDE(valueOfJDD.toString())){

							String PR = myJDD.getParamForThisName('PREREQUIS',fieldName)

							if (PR) {

								Log.addTrace("PR=$PR")

								valueOfJDD =getValueFromFK(PR,FK,cdt,valueOfJDD.toString())

							}else {
								Log.addERROR("Pas de PREREQUIS pour '$fieldName' : lecture de la FK=$FK impossible")
							}

						}else {
							Log.addTrace(" - Pas de lecture de FK, la valeur est : "+valueOfJDD.toString())
						}
					}



					// cas d'un champ lié à une INTERNALVALUE
					String IV = myJDD.getParamForThisName('INTERNALVALUE',fieldName)

					if (IV) {

						if (valueOfJDD) {

							Log.addTrace("Détection d'une IV sur $fieldName, IV= $IV value=$valueOfJDD :")

							String internalVal = NAV.myGlobalJDD.getInternalValueOf(IV,valueOfJDD.toString())

							Log.addTrace("/t- internal value =$internalVal")

							valueOfJDD = internalVal
						}else {
							Log.addERROR("Détection d'une INTERNALVALUE sur $fieldName, IV= $IV, la valeur est null ou vide.ARRET DU PROGRAMME")
							System.exit(0)
						}
					}



					// Cas des val TBD
					if (JDDKW.startWithTBD(valueOfJDD)) {

						Log.addTrace("Détection d'une valeur TBD sur $fieldName '$valueOfJDD'")

						def newValue = JDDKW.getValueOfKW_TBD(valueOfJDD)
						// si une valeur de test existe, on remplace la valeur du JDD par cette valeur
						if (newValue) {
							valueOfJDD = newValue
						}else {
							Log.addERROR("Détection d'une valeur TBD sur $fieldName sans valeur de test. ARRET DU PROGRAMME")
							System.exit(0)
						}

					}

					// Cas des val $UPD$...$...
					if (JDDKW.startWithUPD(valueOfJDD)) {

						Log.addERROR("Détection d'une valeur UPD sur $fieldName INTERDIT sur PREJDD. ARRET DU PROGRAMME")
						System.exit(0)
					}



					switch (valueOfJDD) {

						case JDDKW.getKW_ORDRE() :
							if (maxORDRE == 0) {
								maxORDRE = SQL.getMaxFromTable(fieldName, myJDD.getDBTableName())
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

							if (valueOfJDD instanceof java.util.Date) {
								Log.addTrace("\t\t instanceof java.util.Date = TRUE")
								val = valueOfJDD.format('yyyy-dd-MM HH:mm:ss.SSS')

							}else {
								val = valueOfJDD.toString()
							}
							break
					}


					if (InfoBDD.isImage(myJDD.getDBTableName(), fieldName)) {
						values.add(getRTFTEXT(valueOfJDD.toString()).getBytes())
					}else if (!my.JDDKW.isNU(valueOfJDD.toString()) && !myJDD.isOBSOLETE(fieldName) ) {
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
				Log.addTrace(req,0)
				SQL.executeSQL(req)
			}
		}
	}



	static String getValueFromFK(String PR,String FK, String cdt,String valeur) {

		def pr = PR.split(/\*/)
		def fk = FK.split(/\*/)

		String modObj = pr[0]
		String tabName = pr[1]
		String id=fk[0]
		String field=fk[2]

		Log.addTrace("Lecture du PREJDD pour la FK : '" + PREJDDfilemap.getAt(modObj)+ " ($tabName)")
		XSSFWorkbook book = my.XLS.open(PREJDDfilemap.getAt(modObj))

		Sheet sheet = book.getSheet(tabName)
		List <String> headersPREJDD = my.XLS.loadRow(sheet.getRow(0))
		List <List> datas = my.PREJDD.loadDATA(sheet,headersPREJDD.size())

		int fieldIndex =XLS.getColumnIndexOfColumnName(sheet, field)
		int idIndex =XLS.getColumnIndexOfColumnName(sheet, id)

		Log.addTrace("Pour le champ '$field' l'index = '$fieldIndex' la valeur est '$valeur'")
		Log.addTrace("Pour le champ '$id' l'index = '$idIndex' le cdt est '$cdt'")

		try {
			String val = datas.find { it[0] == cdt && it[fieldIndex] == valeur }[idIndex]
			Log.addTrace("La valeur de l'ID trouvé est $val")
			return val
		} catch (NullPointerException e) {
			Log.addERROR("La valeur recherchée n'a pas été trouvée.ARRET DU PROGRAMME")
			System.exit(0)
		}
	}



	static insertIfNotExist(String table, String PKwhere, List fields, List values) {

		Log.addTrace("insertIfNotExist($table, $PKwhere)")

		Map result = SQL.getFirstRow("SELECT count(*) as nbr FROM $table WHERE $PKwhere")

		if(result) {
			if (result.nbr == 0) {

				List li = Collections.nCopies(fields.size(), '?')

				String query = "INSERT INTO $table (" + fields.join(',') +") VALUES (" + li.join(',') + ")"

				Log.addTrace(query,0)
				Log.addTrace(values.join(','),0)

				SQL.executeInsert(query,values)

				Log.addTrace("insertIfNotExist() --> OK")

			}else if (result.nbr == 1){
				Log.addTrace("insertIfNotExist() --> La valeur $PKwhere existe déjà")
			}else {
				Log.addERROR("Plusieurs valeurs trouvées pour :")
				Log.addINFO("SELECT count(*) FROM $table WHERE $PKwhere")
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
