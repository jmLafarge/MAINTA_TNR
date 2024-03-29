package tnrPREJDDManager

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import tnrCommon.ExcelUtils
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnrSqlManager.SQL


@CompileStatic
public class PREJDD {

	static String savJDDNAME='' // Pour gérer l'affichage de tous les JDD dans le Log
	static String savJDDNAME2=''// Pour gérer l'affichage des JDD KO  dans le Log
	static String savTxt=''		// Pour gérer l'affichage des lignes de controle KO dan sle Log
	static String savCDTSR=''
	static String PREJDDFullName =''
	private static boolean status = true


	static boolean checkPREJDD(Map map){

		status = true
		PREJDDFullName = PREJDDFileMapper.getFullnameFromModObj(map.getAt('PREJDDMODOBJ').toString())

		XSSFWorkbook book = ExcelUtils.open(PREJDDFullName)

		Sheet sheet = book.getSheet(map.getAt('PREJDDTAB').toString())

		Log.addDEBUG("Controle de '" + map.getAt('JDDID') + "' de '" + map.getAt('JDDNAME') + "'  dans '" + PREJDDFileMapper.getFullnameFromModObj(map.getAt('PREJDDMODOBJ').toString()) + "' (" + map.getAt('PREJDDTAB') + ") '"+ map.getAt('PREJDDID') + "'")

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
					Log.addDEBUG('')
					Log.addDEBUG('\t'+ map.getAt('JDDNAME').toString())
					savJDDNAME = map.getAt('JDDNAME').toString()
				}


				String cdt = cdtVal.toString().split("'")[1]
				String val = cdtVal.toString().split("'")[3]
				String JDDFullName= JDDFileMapper.getFullnameFromModObj(map.getAt('PREJDDMODOBJ').toString())
				tnrJDDManager.JDD myJDD = new JDD(JDDFullName,'001',null)

				// vérifier si la valeur n'est  pas déjà en BDD
				int cpt = SQL.checkIfExist(myJDD.getDBTableName(), map.getAt('JDDID').toString()+"='$val'")


				if (cpt==1) {
					Log.addDEBUGDETAIL(cdtVal.toString()+' trouvé en BDD')
				}else {

					if (savJDDNAME2 != map.getAt('JDDNAME').toString()) {
						Log.addINFO('')
						Log.addINFO('\t'+ map.getAt('JDDNAME').toString())
						savJDDNAME2 = map.getAt('JDDNAME').toString()
						savTxt=''
					}
					String txt="Controle de '" + map.getAt('JDDID') + "' dans '" + PREJDDFileMapper.getFullnameFromModObj(map.getAt('PREJDDMODOBJ').toString()) + "' (" + map.getAt('PREJDDTAB') + ") '"+ map.getAt('PREJDDID') + "'"
					if (savTxt != txt) {
						Log.addINFO('')
						Log.addINFO("\t $txt")
						savTxt = txt
					}
					Log.addDETAILFAIL("Cas de test '$cdt', '${map.getAt('JDDID')}' = '$val' non trouvé !")
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
		if (sheet) {
			int idxID  = ExcelUtils.getColumnIndexOfColumnName(sheet, ID)

			if (idxID!=-1) {

				for (int numLine : 1..sheet.getLastRowNum()) {

					Row row = sheet.getRow(numLine)

					// exit if lastRow of param
					if (!row || ExcelUtils.getCellValue(row.getCell(0))=='') {
						break
					}

					String casDeTest = ExcelUtils.getCellValue(row.getCell(0))
					String IDvalue = ExcelUtils.getCellValue(row.getCell(idxID))

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
		}
		return list
	}



}// end oof class
