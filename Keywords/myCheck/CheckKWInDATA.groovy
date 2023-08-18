package myCheck

import groovy.transform.CompileStatic
import my.Log
import myJDDManager.JDDKW


@CompileStatic
public class CheckKWInDATA {
	
	private static final String CLASS_FORLOG = 'CheckKWInDATA'

	static boolean run(List <Map<String, Map<String, String>>> datas, boolean PREJDD, String JDDFullName, String sheetName, boolean status) {
		Log.addTraceBEGIN(CLASS_FORLOG,"run",[datas:datas.size() , PREJDD:PREJDD , JDDFullName:JDDFullName , sheetName:sheetName , status:status])
		Log.addDEBUGDETAIL("Contrôle des mots clés dans les DATA")
		datas.each { lineMap ->
			lineMap.each { cdt,dataMap ->
				dataMap.each { name,val ->					
					if ((val instanceof String) && val.startsWith('$') && !JDDKW.isAllowedKeyword(val)) {
						Log.addDETAILFAIL("$JDDFullName ($sheetName) : Le mot clé '$val' est inconnu. Trouvé dans le cas de test $cdt colonne $name")
						status=false
					}else if (PREJDD && (val instanceof String) && val.startsWith('$') && JDDKW.startWithUPD(val)) {
						Log.addDETAILFAIL("$JDDFullName ($sheetName) : Le mot clé '$val' n'est pas autorisé dans les PREJDD. Trouvé dans le cas de test $cdt colonne $name")
						status=false
					}
					
				}
			}

		}
		Log.addTraceEND(CLASS_FORLOG,"run",status)
		return status
	}
	
}
