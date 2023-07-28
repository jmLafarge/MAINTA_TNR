package my

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import my.PREJDDFiles

import groovy.transform.CompileStatic


@CompileStatic
public class PREJDD {

	static String savJDDNAME='' // Pour gérer l'affichage de tous les JDD dans le Log
	static String savJDDNAME2=''// Pour gérer l'affichage des JDD KO  dans le Log
	static String savTxt=''		// Pour gérer l'affichage des lignes de controle KO dan sle Log
	static String savCDTSR=''
	static String PREJDDFullName =''
	private static boolean status = true


	static boolean checkPREJDD(Map map){

		PREJDDFullName = PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ').toString())

		XSSFWorkbook book = my.XLS.open(PREJDDFullName)

		Sheet sheet = book.getSheet(map.getAt('PREJDDTAB').toString())

		Log.addTrace("Controle de '" + map.getAt('JDDID') +"' de '" + map.getAt('JDDNAME') + "'  dans '" + PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ').toString()) + "' (" + map.getAt('PREJDDTAB') + ") '"+ map.getAt('PREJDDID') + "'")

		List list = getListOfCasDeTestAndIDValue(sheet, map.getAt('PREJDDID').toString())

		int nbFound =0

		map.getAt('LISTCDTVAL').each{ cdtVal ->

			boolean found = false

			list.each{ cdtValPre ->
				if (cdtVal == cdtValPre) {
					found =true
					nbFound++
				}
			}

			if (found) {
				Log.addTrace(cdtVal.toString()+' trouvé')
			}else {

				if (savJDDNAME != map.getAt('JDDNAME').toString()) {
					Log.addTrace('')
					Log.addTrace('\t- '+ map.getAt('JDDNAME').toString())
					savJDDNAME = map.getAt('JDDNAME').toString()
				}

				// vérifier si la valeur n'est  pas déjà en BDD
				String val = cdtVal.toString().split("'")[3]
				String JDDFullName= JDDFiles.getJDDFullName(map.getAt('PREJDDMODOBJ').toString())
				my.JDD myJDD = new JDD(JDDFullName,'001',null,false)

				int cpt = SQL.checkIfExist(myJDD.getDBTableName(), map.getAt('JDDID').toString()+"='$val'")

				if (cpt==1) {
					Log.addTrace(cdtVal.toString()+' trouvé en BDD')
				}else {

					if (savJDDNAME2 != map.getAt('JDDNAME').toString()) {
						Log.addINFO('')
						Log.addINFO('\t- '+ map.getAt('JDDNAME').toString())
						savJDDNAME2 = map.getAt('JDDNAME').toString()
						savTxt=''
					}
					String txt="Controle de '" + map.getAt('JDDID') + "' dans '" + PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ').toString()) + "' (" + map.getAt('PREJDDTAB') + ") '"+ map.getAt('PREJDDID') + "'"
					if (savTxt != txt) {
						Log.addINFO('')
						Log.addINFO("\t\t$txt")
						savTxt = txt

						/*
						 Log.addINFO('')
						 map.each { cle, valeur ->
						 Log.addINFO("\tClé: $cle, Valeur: $valeur")
						 }
						 Log.addINFO('')
						 */


					}
					Log.addDETAILFAIL(cdtVal.toString()+' non trouvé !')
					status = false
				}
			}
		}
		List listCDTVAL = map.getAt('LISTCDTVAL')
		Log.addDEBUGDETAIL(nbFound + "/" + listCDTVAL.size() + ' trouvé(s)')

		return status
	}


	private static List getListOfCasDeTestAndIDValue(Sheet sheet, String ID) {

		List list = []

		int idxID  = my.XLS.getColumnIndexOfColumnName(sheet, ID)

		if (idxID!=-1) {

			for (int numLine : 1..sheet.getLastRowNum()) {

				Row row = sheet.getRow(numLine)

				// exit if lastRow of param
				if (!row || my.XLS.getCellValue(row.getCell(0))=='') {
					break
				}

				String casDeTest = my.XLS.getCellValue(row.getCell(0))
				String IDvalue = my.XLS.getCellValue(row.getCell(idxID))

				if (!JDDKW.isAllowedKeyword(IDvalue)) {

					String casDeTest_IDvalue = "'" + casDeTest + "' - '" + IDvalue + "'"

					if (list.contains(casDeTest_IDvalue)) {
						String CDTSR = "$PREJDDFullName (${sheet.getSheetName()})"
						if (CDTSR!=savCDTSR) {
							Log.addINFO('')
							Log.addINFO("\t\tControle unicité du couple CasDeTest - Sous-ressources dans $CDTSR")
							savCDTSR = CDTSR
						}

						Log.addDETAILFAIL("$casDeTest_IDvalue existe déjà ")
						status = false
					}else {
						list.add(casDeTest_IDvalue)
					}
				}else {
					Log.addTrace("La valeur de $ID est un mot clé : $IDvalue")
				}
			}
		}
		return list
	}



	static List loadDATA(Sheet sheet,int size) {
		Log.addTrace('Lecture PREJDD data')
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		List datas =[]
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			datas << my.XLS.loadRow(row,size)
		}
		Log.addTrace('PREJDD data size = ' + datas.size() )
		return datas
	}


}// en dof class
