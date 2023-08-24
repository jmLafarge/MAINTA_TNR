package myPREJDDManager

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.io.FileType
import groovy.transform.CompileStatic
import my.InfoDB
import my.Log
import my.SQL
import my.XLS
import myJDDManager.JDDFileMapper
import myJDDManager.JDDKW
import myJDDManager.JDDIV


@CompileStatic
public class PREJDDFileMapper {


	private static final String CLASS_FORLOG = 'PREJDDFiles'


	public static Map <String,String> PREJDDfilemap = [:]


	static {
		Log.addTraceBEGIN(CLASS_FORLOG,"static",[:])
		Log.addSubTITLE("Load PREJDDfileList",'-',120)
		Log.addINFO("\t"+'MODOBJ'.padRight(11) + 'JDDFULLNAME')
		Log.addINFO('')

		new File(my.PropertiesReader.getMyProperty('PREJDD_PATH')).eachFileRecurse(FileType.FILES) { file ->
			// keep only TC Name like PREJDD.*.xlsx
			if (file.getName()==~ /PREJDD\..*\.xlsx/ && file.getPath()==~ /^((?!standby).)*$/) {
				String modObj = file.getName().replace('PREJDD.','').replace('.xlsx','')
				PREJDDfilemap.put(modObj,file.getPath())
				Log.addINFO('\t' + modObj.padRight(11) + file.getPath())
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"static")
	}




	static String getFullnameFromModObj(String modObj){
		Log.addTraceBEGIN(CLASS_FORLOG,"getFullname",[modObj:modObj])
		Log.addTraceEND(CLASS_FORLOG,"getFullname",PREJDDfilemap[modObj])
		return PREJDDfilemap[modObj]
	}




	static insertPREJDDinDB(String modObj, String tabName) {
		Log.addTraceBEGIN(CLASS_FORLOG,"insertPREJDDinDB",[modObj:modObj,tabName:tabName])

		def myJDD = new myJDDManager.JDD(JDDFileMapper.JDDfilemap.getAt(modObj),tabName)

		Log.addINFO("Traitement de : '" + PREJDDfilemap.getAt(modObj)+ " ($tabName)")
		XSSFWorkbook book = my.XLS.open(PREJDDfilemap.getAt(modObj))
		// set tab (sheet)
		Sheet sheet = book.getSheet(tabName)
		Row row0 = sheet.getRow(0)

		Map <String,Integer> sequence = [:]
		int maxORDRE = 0

		List PKlist=InfoDB.getPK(myJDD.getDBTableName())

		// for each data line
		for (int numline : (1..sheet.getLastRowNum())) {
			Row row = sheet.getRow(numline)
			// exit if lastRow
			String cdt = my.XLS.getCellValue(row.getCell(0))
			if (!row || cdt =='') {
				break
			}
			Log.addTrace("")
			Log.addTrace("Traitement $cdt")

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
				Log.addTrace("\tCell value = '$valueOfJDD' getClass()=" + valueOfJDD.getClass())

				if (c.getColumnIndex()>0) {



					if (!myJDDManager.JDDKW.isNU(valueOfJDD.toString()) && !myJDD.isOBSOLETE(fieldName)) {
						fields.add(fieldName)
						Log.addTrace("\tAjout fieldName='$fieldName' in req SQL")

						// cas d'un champ lié à une séquence
						String seqTable = myJDD.myJDDParam.getSEQUENCEFor(fieldName)
						if (seqTable && !JDDKW.isNULL(valueOfJDD)){
							int seq = (int)valueOfJDD
							if (sequence.containsKey(seqTable)) {
								if (seq > sequence.getAt(seqTable)) {
									sequence.put(seqTable, seq)
									Log.addTrace("Ajout d'une sequence sur '$fieldName', table '$seqTable', seq '$seq'")
								}
							}else {
								Log.addTrace("Détection d'une sequence sur '$fieldName', table '$seqTable', seq '$seq'")
								sequence.put(seqTable, seq)
							}
						}



						// cas d'un champ lié à une FOREIGNKEY
						String FK = myJDD.myJDDParam.getFOREIGNKEYFor(fieldName)
						if (FK) {

							Log.addTrace("Détection d'une FK sur $fieldName, FK= $FK")

							if (!myJDDManager.JDDKW.isNULL(valueOfJDD.toString()) && !myJDDManager.JDDKW.isVIDE(valueOfJDD.toString())){

								valueOfJDD =getValueFromFK(FK,cdt,valueOfJDD.toString())

							}else {
								Log.addTrace(" - Pas de lecture de FK, la valeur est '${valueOfJDD.toString()}'")
							}
						}



						// cas d'un champ lié à une INTERNALVALUE

						String paraIV = myJDD.myJDDParam.getINTERNALVALUEFor(fieldName)

						if (paraIV) {

							if (valueOfJDD) {

								Log.addTrace("Détection d'une IV sur $fieldName, IV= $paraIV valueOfJDD$valueOfJDD :")

								String internalVal = JDDIV.getInternalValueOf(paraIV,valueOfJDD.toString())

								Log.addTrace("- internal value =$internalVal")

								valueOfJDD = internalVal
							}else {
								Log.addErrorAndStop("Détection d'une INTERNALVALUE sur $fieldName, IV= $paraIV, la valeur est null ou vide.")
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
								Log.addErrorAndStop("Détection d'une valeur TBD sur $fieldName sans valeur de test.")
							}

						}

						// Cas des val $UPD$...$...
						if (JDDKW.startWithUPD(valueOfJDD)) {

							Log.addErrorAndStop("Détection d'une valeur UPD sur $fieldName INTERDIT sur PREJDD.")
						}



						switch (valueOfJDD) {

							case JDDKW.getKW_ORDRE() :
								if (maxORDRE == 0) {
									maxORDRE = SQL.getMaxFromTable(fieldName, myJDD.getDBTableName())
								}
								maxORDRE++
								val = maxORDRE.toString()
								break

							case myJDDManager.JDDKW.getKW_NULL() :
								val = null
								break

							case myJDDManager.JDDKW.getKW_VIDE() :
								val = ''
								break

							case myJDDManager.JDDKW.getKW_DATE() :
								def now = new Date()
								val =  now.format('yyyy-dd-MM')
								break

							case myJDDManager.JDDKW.getKW_DATETIME() :
								def now = new Date()
								val = now.format('yyyy-dd-MM HH:mm:ss.SSS')
								break
							default :

								if (valueOfJDD instanceof java.util.Date) {
									Log.addTrace("\tinstanceof java.util.Date = TRUE")
									val = valueOfJDD.format('yyyy-dd-MM HH:mm:ss.SSS')

								}else {
									val = valueOfJDD.toString()
								}
								break
						}


						if (InfoDB.isImage(myJDD.getDBTableName(), fieldName)) {
							values.add(getRTFTEXT(valueOfJDD.toString()).getBytes())
						}else if (!myJDDManager.JDDKW.isNU(valueOfJDD.toString()) && !myJDD.isOBSOLETE(fieldName) ) {
							values.add(val)
						}

						if (PKlist.contains(fieldName)) {
							PKwhere.add("$fieldName = '$val'")

						}

					}

				}
			}

			insertIfNotExist(myJDD.getDBTableName(), PKwhere.join(' AND '), fields, values)

		}

		if (sequence.size()>0) {
			sequence.each { table, val ->
				String req = "DBCC CHECKIDENT ($table, RESEED,${val+1});"
				SQL.executeSQL(req)
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"insertPREJDDinDB")
	}





	static String getValueFromFK(String FK, String cdt,String valeur) {

		Log.addTraceBEGIN(CLASS_FORLOG,"getValueFromFK",[FK:FK,cdt:cdt,valeur:valeur])

		def fk = FK.split(/\*/)

		String id=fk[0]
		String table = fk[1]
		String field=fk[2]

		String val = SQL.getFirstVal("SELECT $id FROM $table WHERE $field = '$valeur'").toString()

		if (val) {
			Log.addTraceEND(CLASS_FORLOG,"getValueFromFK",val)
			return val
		}else {
			Log.addErrorAndStop("La valeur recherchée n'a pas été trouvée.ARRET DU PROGRAMME")
		}
	}





	static insertIfNotExist(String table, String PKwhere, List fields, List values) {

		Log.addTraceBEGIN(CLASS_FORLOG,"insertIfNotExist",[table:table,PKwhere:PKwhere,fields:fields,values:values])

		Map result = SQL.getFirstRow("SELECT count(*) as nbr FROM $table WHERE $PKwhere")

		if(result) {
			if (result.nbr == 0) {

				List li = Collections.nCopies(fields.size(), '?')

				String query = "INSERT INTO $table (" + fields.join(',') +") VALUES (" + li.join(',') + ")"

				Log.addTrace(query)
				Log.addTrace(values.join(','))

				SQL.executeInsert(query,values)

				Log.addTrace("insertIfNotExist() --> OK")

			}else if (result.nbr == 1){
				Log.addTrace("insertIfNotExist() --> La valeur $PKwhere existe déjà")
			}else {
				Log.addERROR("Plusieurs valeurs trouvées pour :")
				Log.addINFO("SELECT count(*) FROM $table WHERE $PKwhere")
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"insertIfNotExist",result.size())
	}


	static add(String modObj,String fullName) {
		PREJDDfilemap.put(modObj,fullName)
	}



	private static String getRTFTEXT(String val) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getRTFTEXT",[val:val])
		String strBegin ="{\\rtf1\\fbidis\\ansi\\ansicpg0\\uc1\\deff0\\deflang0\\deflangfe0{\\fonttbl{\\f0\\fnil Arial;}}{\\colortbl;}{\\stylesheet{\\s0\\fi0\\li0\\ql\\ri0\\sb0\\sa0 Paragraph Style;}{\\*\\cs1\\f0\\fs24 Font Style;}}\\pard\\s0\\fi0\\li0\\ql\\ri0\\sb0\\sa0\\itap0 \\plain \\cs1\\f0\\fs24 "
		String strEnd	="\\par}"
		String RTFText = strBegin+val+strEnd
		Log.addTraceEND(CLASS_FORLOG,"getRTFTEXT")
		return RTFText
	}

	static createInDB() {

		Log.addTraceBEGIN(CLASS_FORLOG,"createInDB",[:])

		Log.addTITLE("Lancement de CREATE PREJDD IN DB")


		def listPREJDD =	 [
			['RO.CAT', '001'],
			['RO.HAB', '001'],
			['RO.MET', '001'],
			['RO.CAL', '001'],
			['RO.CAL', '001A'],
			['RT.EMP', '001'],
			['RO.ORG', '001A'],
			['RO.ORG', '001'],
			['RO.ACT', '001'],
			['RO.ACT', '005'],
			['RO.ACT', '003HAB'],
			['RO.ACT', '003MET'],
			['RO.ACT', '004EMP'],
			['RO.SOCIETE', '001'],
			['RO.UTI', '001'],
			['RO.CCO', '001'],
			['RO.DEV', '001'],
			['AC.CEM', '001'],
			['AC.CMR', '001'],
			['AC.CPA', '001'],
			['AC.CPO', '001'],
			['RO.ADR', '001'],
			['RO.LIE', '001'],
			['RO.FOU', '001A'],
			['RO.FOU', '001'],
			['MP.CPT', '001'],
			['RT.NOM', '001'],
			['RT.ART', '001'],
			['RT.ART', '001A'],
			['RT.ART', '001B'],
			['RT.ART', '001C'],
			['RT.MOY', '001'],
			['RT.EQU', '001'],
			['RT.EQU', '001A'],
			['RT.EQU', '001B'],
			['RT.EQU', '001C'],
			['RT.MAT', '001'],
			['RT.MAT', '001A'],
			['RT.MAT', '001B'],
			['RT.MAT', '001C'],
			['RO.IMP', '001'],
			['RO.IMP', '001A']
		]





		Log.addSubTITLE('Création des PREJDD')

		listPREJDD.each { li ->

			PREJDDFileMapper.insertPREJDDinDB(li[0],li[1])

		}


		Log.addSubTITLE('Liste des PREJDD non créés (avec détails des cas de test impactés) :')

		PREJDDFileMapper.PREJDDfilemap.each { modObj,fullName ->

			XSSFWorkbook book = XLS.open(fullName)
			for (Sheet sheet : book ) {
				String sheetName = sheet.getSheetName()
				if (!['Version', 'MODELE'].contains(sheetName)) {
					//println sheet.getSheetName()
					if (!listPREJDD.contains([modObj, sheetName])) {
						if (XLS.getCellValue(sheet.getRow(1).getCell(0))=='') {
							Log.addToList('emptyPREJDD',"$fullName ($sheetName) est vide")
						}else {
							Log.add('WARNING',"\tManque $fullName ($sheetName)")
							// for each data line
							for (int numline : (1..sheet.getLastRowNum())) {
								Row row = sheet.getRow(numline)
								// exit if lastRow
								String cdt = my.XLS.getCellValue(row.getCell(0))
								if (!row || cdt =='') {
									break
								}else {
									Log.addDETAIL("\t- $cdt")
								}
							}
						}

					}
				}

			}

		}



		Log.addSubTITLE('Liste des PREJDD vide :')
		Log.writeList('emptyPREJDD')


		Log.addTITLE("Fin des créations des PRE REQUIS")


		Log.addTraceEND(CLASS_FORLOG,"createInDB")
	}

} // end of class
