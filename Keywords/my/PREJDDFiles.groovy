package my

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.io.FileType
import my.Log as MYLOG
import my.SQL as MYSQL
import my.InfoBDD as INFOBDD

public class PREJDDFiles {


	public static Map PREJDDfilemap = [:]


	public static load() {

		MYLOG.addSubTITLE("Load PREJDDfileList",'-',120,1)
		MYLOG.addINFO("\t"+'MODOBJ'.padRight(11) + 'JDDFULLNAME',1)
		MYLOG.addINFO('',1)

		new File(my.PropertiesReader.getMyProperty('PREJDD_PATH')).eachFileRecurse(FileType.FILES) { file ->
			// keep only TC Name like PREJDD.*.xlsx
			if (file.getName()==~ /PREJDD\..*\.xlsx/ && file.getPath()==~ /^((?!standby).)*$/) {
				String modObj = file.getName().replace('PREJDD.','').replace('.xlsx','')
				this.PREJDDfilemap.put(modObj,file.getPath())
				MYLOG.addINFO('\t' + modObj.padRight(11) + file.getPath(),1)
			}
		}
	}



	public static String getFullName(String modObj){

		return this.PREJDDfilemap.getAt(modObj)
	}



	static insertPREJDDinDB(String modObj, String tabName) {
		MYLOG.addDEBUG("insertPREJDDinDB() modObj = '$modObj' tabName = '$tabName'")
		def myJDD = new my.JDD(my.JDDFiles.JDDfilemap.getAt(modObj),tabName)

		MYLOG.addSTEP("Lecture du PREJDD : '" + this.PREJDDfilemap.getAt(modObj))
		XSSFWorkbook book = my.XLS.open(this.PREJDDfilemap.getAt(modObj))
		// set tab (sheet)
		Sheet sheet = book.getSheet(tabName)
		Row row0 = sheet.getRow(0)

		Map sequence = [:]
		def maxORDRE = null

		List PKlist=INFOBDD.getPK(myJDD.getDBTableName())

		// for each data line
		for (int numline : (1..sheet.getLastRowNum())) {
			Row row = sheet.getRow(numline)
			// exit if lastRow
			if (my.XLS.getCellValue(row.getCell(0))=="") {
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
				MYLOG.addDEBUG("\t\tCell value = '$value' getClass()=" + value.getClass(),2)
				if (c.getColumnIndex()>0) {
					MYLOG.addDEBUG("\t\tAjout fieldName='$fieldName' value='$value' in req SQL",2)

					if (!my.JDDKW.isNU(value.toString()) ) {
						fields.add(fieldName)
					}


					// cas d'un champ lié à une séquence
					String seqTable = myJDD.getParamForThisName('SEQUENCE',fieldName)
					if (seqTable!=null){
						if (sequence.containsKey(seqTable)) {
							if (value > sequence.getAt(seqTable)) {
								sequence.put(seqTable, value)
							}
						}else {
							MYLOG.addDETAIL("Détection d'une sequence sur $fieldName, table $seqTable")
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
							val = "NULL"
							break

						case my.JDDKW.getKW_VIDE() :
							val = "''"
							break

						case my.JDDKW.getKW_DATE() :
							def now = new Date()
							val =  "'${now.format('yyyy-dd-MM')}'"
							break

						case my.JDDKW.getKW_DATETIME() :
							def now = new Date()
							val = "'${now.format('yyyy-dd-MM HH:mm:ss.SSS')}'"
							break
						default :
							
							if (value instanceof java.util.Date) {
								MYLOG.addDEBUG("\t\t instanceof java.util.Date = TRUE")
								val = "'${value.format('yyyy-dd-MM HH:mm:ss.SSS')}'"
							}else if (INFOBDD.isImage(myJDD.getDBTableName(), fieldName)) {
								
								val = "'${this.getRTFTEXT(value)}'"
							}else {
								val = "'$value'"
							}
							break
					}

					if (!my.JDDKW.isNU(value.toString()) ) {
						values.add(val)
					}

					if (PKlist.contains(fieldName)) {
						PKwhere.add("$fieldName = $val")

					}

				}
			}


			this.insertIfNotExist(myJDD.getDBTableName(), PKwhere.join(' AND '), fields.join(','), values.join(','))

		}

		if (sequence.size()>0) {
			sequence.each { table, val ->
				String req = "DBCC CHECKIDENT ($table, RESEED,$val);"
				MYLOG.addDETAIL(req)
				MYSQL.executeSQL(req)
				//fileSQL.append("$req\n")
			}
		}
	}




	static insertIfNotExist(String table, String PKwhere, String fields, String values) {

		MYLOG.addDEBUG("SELECT count(*) FROM $table WHERE $PKwhere")
		def result = MYSQL.getFirstRow("SELECT count(*) FROM $table WHERE $PKwhere")
		if(result) {
			if (result[0] == 0) {
				MYLOG.addDETAIL("INSERT INTO $table ($fields) VALUES ($values);")
				MYSQL.executeSQL("INSERT INTO $table ($fields) VALUES ($values);")
			}else if (result[0] ==1){
				MYLOG.addDEBUG("La valeur $PKwhere existe déjà")
			}else {
				MYLOG.addERROR("Plusieurs valeurs trouvées pour :")
				MYLOG.addDETAIL("SELECT count(*) FROM $table WHERE $PKwhere")
			}
		}
	}


	static add(String modObj,String fullName) {
		this.PREJDDfilemap.put(modObj,fullName)
	}


	
	private static String getRTFTEXT(String val) {
		String strBegin ="{\\rtf1\\fbidis\\ansi\\ansicpg0\\uc1\\deff0\\deflang0\\deflangfe0{\\fonttbl{\\f0\\fnil Arial;}}{\\colortbl;}{\\stylesheet{\\s0\\fi0\\li0\\ql\\ri0\\sb0\\sa0 Paragraph Style;}{\\*\\cs1\\f0\\fs24 Font Style;}}\\pard\\s0\\fi0\\li0\\ql\\ri0\\sb0\\sa0\\itap0 \\plain \\cs1\\f0\\fs24 "
		String strEnd	="\\par}"
		return strBegin+val+strEnd
	}

} // end of class
