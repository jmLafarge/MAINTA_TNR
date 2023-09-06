package tnrCheck.data

import groovy.transform.CompileStatic
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnrSqlManager.InfoDB

/**
 *
 * vérifie les valeurs et les doublons de clés primaires (PK) dans les JDD/PREJDDD
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
@CompileStatic
public class CheckPK {


	static private final String CLASS_FOR_LOG = 'CheckPK'



	static boolean run(List<Map<String, Map<String, Object>>> datasList, List <String> PKList, String JDDFullName, String sheetName) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "run", ['datasList.size()':datasList.size() , PKList:PKList , JDDFullName:JDDFullName , sheetName:sheetName])
		boolean status = true
		if (PKList) {
			Log.addDEBUGDETAIL("Contrôle absence de doublon sur PRIMARY KEY : " +  PKList.join(' , '))

			List <String>  concatenedPKVals = []
			List <String>  cdts = []

			datasList.eachWithIndex { lines,idx ->
				lines.each { cdt,datas ->
					String concatenedPKVal = concatPKVal(datas,PKList)
					if (concatenedPKVal){
						cdts.add(cdt)
						if (concatenedPKVals.contains(concatenedPKVal)) {
							int i = concatenedPKVals.indexOf(concatenedPKVal)
							String c = cdts[i]
							Log.addDETAILFAIL("$JDDFullName ($sheetName) ligne ${idx+1} cas de test:$cdt, la PK $concatenedPKVal existe déjà en ligne ${i+1} cas de test:$c")
							status=false
						}else {
							concatenedPKVals.add(concatenedPKVal)
						}
					}
				}
			}
		}else {
			Log.addDETAIL("$JDDFullName ($sheetName) : Pas de PRIMARY KEY !")
		}
		Log.addTraceEND(CLASS_FOR_LOG, "run",status)
		return status
	}



	private static String concatPKVal(Map<String, Object> datas,List<String>PKList) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "concatPKVal", [datas:datas.size() , PKList:PKList])
		List concatenedPKVals = []
		datas.each{name,val ->
			if (name in PKList) {
				if (JDDKW.isUPD(val)) {
					concatenedPKVals.add(JDDKW.getOldValueOfKW_UPD(val))
				}else if (JDDKW.isTBD(val)) {
					concatenedPKVals.add(JDDKW.getValueOfKW_TBD(val))
				}else {
					concatenedPKVals.add(val)
				}
			}
		}
		String concatenedPKVal = concatenedPKVals.join(' - ')
		if (concatenedPKVal.contains(JDDKW.getKW_SEQUENCEID()) || concatenedPKVal.contains(JDDKW.getKW_ORDRE())) {
			concatenedPKVal = ''
		}
		Log.addTraceEND(CLASS_FOR_LOG, "concatPKVal",concatenedPKVal)
		return concatenedPKVal
	}
} // end of class