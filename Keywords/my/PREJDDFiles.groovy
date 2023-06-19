package my

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.io.FileType
import groovy.transform.CompileStatic
import my.SQL

@CompileStatic
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

		Log.addSubTITLE("Lecture du PREJDD : '" + PREJDDfilemap.getAt(modObj)+ " ($tabName)")
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
			Log.addINFO("")
			Log.addINFO("Traitement $cdt")
			if (!row || cdt =='') {
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
						int seq = (int)value
						if (sequence.containsKey(seqTable)) {
							if (seq > sequence.getAt(seqTable)) {
								sequence.put(seqTable, seq)
							}
						}else {
							Log.addDEBUG("Détection d'une sequence sur $fieldName, table $seqTable")
							sequence.put(seqTable, seq)
						}
					}
					
					
					
					// cas d'un champ lié à une FOREIGNKEY
					String FK = myJDD.getParamForThisName('FOREIGNKEY',fieldName)
					if (FK) {
						
						Log.addDEBUG("Détection d'une FK sur $fieldName, FK= $FK")
						
						if (!my.JDDKW.isNULL(value.toString()) && !my.JDDKW.isVIDE(value.toString())){

							String PR = myJDD.getParamForThisName('PREREQUIS',fieldName)
							
							if (PR) {
								
								Log.addDEBUG("PR=$PR")
								
								value =getValueFromFK(PR,FK,cdt,value.toString())
	
							}else {
								Log.addERROR("Pas de PREREQUIS pour '$fieldName' : lecture de la FK=$FK impossible")
							}
							
						}else {
							Log.addDEBUG(" - Pas de lecture de FK, la valeur est : "+value.toString())
						}
					}
					
					
					
					// cas d'un champ lié à une INTERNALVALUE
					String IV = myJDD.getParamForThisName('INTERNALVALUE',fieldName)
					
					if (IV) {
						
						if (value) {
						
							Log.addDEBUG("Détection d'une IV sur $fieldName, IV= $IV value=$value :")
							
							String internalVal = my.PropertiesReader.getMyProperty('IV_' + IV + '_' + value)
							
							Log.addDEBUG("/t- internal value =$internalVal")
							
							value = internalVal
						}else {
							Log.addERROR("Détection d'une INTERNALVALUE sur $fieldName, IV= $IV, la valeur est null ou vide.ARRET DU PROGRAMME")
							System.exit(0)
						}
					}
					
					
					
					


					switch (value) {

						case my.JDDKW.getKW_ORDRE() :
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

							if (value instanceof java.util.Date) {
								Log.addDEBUG("\t\t instanceof java.util.Date = TRUE")
								val = value.format('yyyy-dd-MM HH:mm:ss.SSS')

							}else {
								val = value.toString()
							}
							break
					}
					

					if (InfoBDD.isImage(myJDD.getDBTableName(), fieldName)) {
						values.add(getRTFTEXT(value.toString()).getBytes())
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
				Log.addINFO(req)
				SQL.executeSQL(req)
				//fileSQL.append("$req\n")
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
		
		Log.addDEBUG("Lecture du PREJDD pour la FK : '" + PREJDDfilemap.getAt(modObj)+ " ($tabName)")
		XSSFWorkbook book = my.XLS.open(PREJDDfilemap.getAt(modObj))

		Sheet sheet = book.getSheet(tabName)
		List <String> headersPREJDD = my.XLS.loadRow(sheet.getRow(0))
		List <List> datas = my.PREJDD.loadDATA(sheet,headersPREJDD.size())
		
		int fieldIndex =XLS.getColumnIndexOfColumnName(sheet, field)
		int idIndex =XLS.getColumnIndexOfColumnName(sheet, id)
		
		Log.addDEBUG("Pour le champ '$field' l'index = '$fieldIndex' la valeur est '$valeur'")
		Log.addDEBUG("Pour le champ '$id' l'index = '$idIndex' le cdt est '$cdt'")
		
		try {  
			String val = datas.find { it[0] == cdt && it[fieldIndex] == valeur }[idIndex]
			Log.addDEBUG("La valeur de l'ID trouvé est $val")
			return val
		} catch (NullPointerException e) {
		  Log.addERROR("La valeur recherchée n'a pas été trouvée.ARRET DU PROGRAMME")
		  System.exit(0)
		}
		
		
	}



	static insertIfNotExist(String table, String PKwhere, List fields, List values) {

		Log.addDEBUG("SELECT count(*) FROM $table WHERE $PKwhere")
		Map result = SQL.getFirstRow("SELECT count(*) as nbr FROM $table WHERE $PKwhere")

		if(result) {
			if (result.nbr == 0) {

				List li = Collections.nCopies(fields.size(), '?')

				String query = "INSERT INTO $table (" + fields.join(',') +") VALUES (" + li.join(',') + ")"

				Log.addINFO(query)
				Log.addINFO(values.join(','))

				//SQL.executeInsert(query,values)

			}else if (result.nbr == 1){
				Log.addDEBUG("La valeur $PKwhere existe déjà")
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
