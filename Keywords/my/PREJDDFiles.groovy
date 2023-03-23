package my

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.io.FileType
import my.Log as MYLOG

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
		String fileSQLName = my.PropertiesReader.getMyProperty('SQL_PATH') + File.separator +  modObj + '_' + tabName + '.sql'
		MYLOG.addSTEP("Création du script SQL : '$fileSQLName'")
		File fileSQL =new File(fileSQLName)
		if (fileSQL.exists()){ fileSQL.delete() }
		fileSQL =new File(fileSQLName)
		fileSQL.append("PRINT 'DEBUT INSERTION TABLE " + myJDD.getDBTableName() + "';\n")
		Map sequence = [:]
		def maxORDRE = null
		// for each data line
		for (int numline : (1..sheet.getLastRowNum())) {
			Row row = sheet.getRow(numline)
			// exit if lastRow
			if (my.XLS.getCellValue(row.getCell(0))=="") {
				break
			}
			String fields =''
			String values =''
			for (Cell c in row) {
				String fieldName = my.XLS.getCellValue(row0.getCell(c.getColumnIndex()))
				if (fieldName=="") {
					break
				}
				def value = my.XLS.getCellValue(c)
				MYLOG.addDEBUG("\t\tCell value = '$value' getClass()=" + value.getClass(),2)
				if (fieldName!='CAS_DE_TEST') {
					MYLOG.addDEBUG("\t\tAjout fieldName='$fieldName' value='$value' in req SQL",2)
					fields=fields +','+ fieldName
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

					/* Pas utilisé dans les PREJDD car on ne peut pas toujours faire le lien
					 // cas d'un champ lié à une clé étrangère
					 String FK = myJDD.getParamForThisName('FOREIGNKEY',fieldName)
					 if (FK!=null){
					 value = my.SQL.getValueFromForeignKey(myJDD.getSqlForForeignKey(FK, value))
					 }
					 */

					switch (value) {

						case my.JDDKW.getKW_ORDRE() :
							if (maxORDRE == null) {
								maxORDRE = my.SQL.getMaxFromTable(fieldName, myJDD.getDBTableName())
							}
							maxORDRE++
							values = values + ',' + maxORDRE.toString()
							break

						case my.JDDKW.getKW_NULL() :
							values = values + ",NULL"
							break

						case my.JDDKW.getKW_VIDE() :
							values = values + ",''"
							break

						case my.JDDKW.getKW_DATE() :
							def now = new Date()
							values = values + ",'" + now.format('yyyy-dd-MM') + "'"
							break

						case my.JDDKW.getKW_DATETIME() :
							def now = new Date()
							values = values + ",'" + now.format('yyyy-dd-MM HH:mm:ss.SSS') + "'"
							break
						default :
							if (value instanceof java.util.Date) {
								MYLOG.addDEBUG("\t\t instanceof java.util.Date = TRUE")
								values = values + ",'" + value.format('yyyy-dd-MM HH:mm:ss.SSS') + "'"
							}else {
								values = values + ",'$value'"
							}
							break
					}
				}
			}
			fields = fields.substring(1)
			values = values.substring(1)
			String req = "INSERT INTO " + myJDD.getDBTableName() + " ($fields) VALUES ($values);"
			MYLOG.addDETAIL(req)
			fileSQL.append("$req\n")
		}

		my.Tools.parseMap(sequence)
		if (sequence.size()>0) {
			sequence.each { table, val ->
				String req = "DBCC CHECKIDENT ($table, RESEED,$val);"
				MYLOG.addDETAIL(req)
				fileSQL.append("$req\n")
			}
		}
		fileSQL.append("PRINT 'FIN INSERTION TABLE " + myJDD.getDBTableName() + "';\n")
	}


	static add(String modObj,String fullName) {
		this.PREJDDfilemap.put(modObj,fullName)
	}



} // end of class
