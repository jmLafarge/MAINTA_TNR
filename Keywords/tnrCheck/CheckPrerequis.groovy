package tnrCheck

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import tnrJDDManager.JDD
import tnrJDDManager.JDDData
import tnrJDDManager.JDDFileMapper
import tnrJDDManager.JDDHeader
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnrPREJDDManager.PREJDD
import tnrPREJDDManager.PREJDDFileMapper
import tnrSqlManager.InfoDB

@CompileStatic
public class CheckPrerequis {


	private static final String CLASS_FOR_LOG = 'CheckPrerequis'

	private static List <Map> list =[]
	private static JDD myJDD
	private static XSSFWorkbook PREJDDBook
	private static boolean status = true

	static boolean run2(String type, JDD myJDD, String fullName, boolean status) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"run",[:])

		Log.addDETAIL(" - Contrôle des PREREQUIS")
		List <Map <String, Object>> list2 = getAllPrerequis2(type, myJDD, fullName)

		Log.addINFO('****************************************************************')
		/*
		 Log.addSubTITLE("Contrôle des PREREQUIS")
		 Log.addINFO("\t\tDétails en cas d'erreur")
		 Log.addINFO("\t\t    CAS DE TEST      -     VALEUR")
		 Log.addINFO('')
		 */
		//Controle si tous les PREREQUIS des JDD/PREJDD sont bien dans les PREJDD

		
		list2.each{ map ->
			/*
			map.each { key,val ->
				Log.addINFO("\t$key:" + val.toString())
			}
			
			if (!checkCdtValInPREJDD(map)) {
				status=false
			}
			*/
			println map
		}
		
		
		if (status) {
			Log.addINFO('     ***  OK   ***')
		}

		Log.addTraceEND(CLASS_FOR_LOG,"run")
		return status
	}



	private static boolean checkCdtValInPREJDD(Map <String, Object> map) {

		boolean ret = true



		return ret
	}





	private static List <Map <String, Object>> getAllPrerequis2(String type, JDD myJDD, String fullName) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"getAllPrerequis",[type:type , fullName:fullName])

		List <Map <String, Object>> list2 =[]
		myJDD.myJDDParam.getAllPREREQUIS().each { name,value ->
			if (!(value in ['', 'OBSOLETE'])) {
				List cdtValList =getListCDTVAL(myJDD.myJDDData.getList(),myJDD.getDBTableName(),name)
				if (!cdtValList.isEmpty()) {
					Map <String, Object> prerequisMap = [:]
					prerequisMap.putAt('PREREQUIS',value)
					prerequisMap.putAt('CDTVALLIST',cdtValList)
					list2.add(prerequisMap)
				}
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getAllPrerequis")
		return list2
	}






	/**
	 * Récupere la liste des couples CDT - Valeur de la colonne name
	 * 
	 * Sauf si la valeur est null ou vide ou $NU ou $NULK ou $VIDE
	 * Sauf si le CDT est un cdt de création et que le name est une PK (il ne doit pas être en prérequis casr on va le créer)
	 * Pour les $UPD*oldValue*newValue, la oldValue doit être en prérequis, mais pas la newValue
	 * 
	 * @param datas
	 * @param table
	 * @param name
	 * @return
	 */
	private static List <String >getListCDTVAL(List <Map<String, Map<String, String>>> datas,String table, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getListCDTVAL",[datas:datas , table:table , name:name])
		List<String> list =[]

		if (InfoDB.isTableExist(table)) {

			if (InfoDB.inTable(table, name)) {
				List<List<String>> listAllCDTVAL = []
				datas.each { outerMap ->
					outerMap.each { key, innerMap ->
						listAllCDTVAL << [key, innerMap[name]]
					}
				}
				listAllCDTVAL.each {li ->
					String cdt = li[0]
					String val = li[1]
					if (val!=null && val!='' && !JDDKW.isNU(val) && !JDDKW.isNULL(val) && !JDDKW.isVIDE(val) ) {
						String cdtVal = "'$cdt' - '$val'"
						if (cdt.toString().contains('.CRE.') && InfoDB.isPK(table,name)) {
							Log.addTrace("skip : $cdtVal")
						}else {
							if (JDDKW.getOldValueOfKW_UPD(val)) {
								cdtVal= "'$cdt' - '" + JDDKW.getOldValueOfKW_UPD(val) + "'"
								list.add(cdtVal)
								Log.addTrace("add $cdtVal \$UPD")
							}else {
								list.add(cdtVal)
								Log.addTrace("add $cdtVal")
							}
						}
					}
				}
			}else {
				Log.addERROR("La colonne '$name' n'existe pas dans la table '$table'!")
			}
		}else {
			Log.addERROR("La table '$table' n'existe pas!")
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getListCDTVAL",list)
		return list
	}


} //end of class
