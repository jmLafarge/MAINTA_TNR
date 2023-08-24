package myCheck

import groovy.transform.CompileStatic
import my.InfoDB
import my.Log
import myJDDManager.JDDKW


@CompileStatic
public class CheckDoublonOnPK {

	static private final String CLASS_FORLOG = 'CheckDoublonOnPK'

	static boolean run(List<Map<String, Map<String, Object>>> datasList, List <String> PKList, String JDDFullName, String sheetName, boolean status) {
		Log.addTraceBEGIN(CLASS_FORLOG, "run", [datasList:datasList.size() , PKList:PKList , JDDFullName:JDDFullName , sheetName:sheetName , status:status])
		if (PKList) {

			Log.addDETAIL(" - Contrôle absence de doublon sur PRIMARY KEY : " +  PKList.join(' , '))

			List <String>  concatenedPKVals = []
			List <String>  cdts = []

			datasList.eachWithIndex { li,idx ->
				li.each { cdt,datas ->
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
			Log.addDETAILWARNING("$JDDFullName ($sheetName) : Pas de PRIMARY KEY !")
		}
		Log.addTraceEND(CLASS_FORLOG, "run",status)
		return status
	}



	private static String concatPKVal(Map<String, Object> datas,List<String>PKList) {
		Log.addTraceBEGIN(CLASS_FORLOG, "concatPKVal", [datas:datas.size() , PKList:PKList])
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
		Log.addTraceEND(CLASS_FORLOG, "concatPKVal",concatenedPKVal)
		return concatenedPKVal
	}
} // end of class