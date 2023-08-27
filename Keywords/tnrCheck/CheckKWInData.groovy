package tnrCheck

import groovy.transform.CompileStatic
import tnrLog.Log
import tnrJDDManager.JDDKW


@CompileStatic
public class CheckKWInData {

	private static final String CLASS_FOR_LOG = 'CheckKWInDATA'

	static boolean run(String typeFile,List <Map<String, Map<String, String>>> datas, String JDDFullName, String sheetName, boolean status) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"run",[typeFile:typeFile , datas:datas.size() , JDDFullName:JDDFullName , sheetName:sheetName , status:status])
		Log.addDETAIL(" - Contrôle des mots clés dans les DATA")
		datas.each { lineMap ->
			lineMap.each { cdt,dataMap ->
				dataMap.each { name,val ->
					if ((val instanceof String) && val.startsWith('$') && !JDDKW.isAllowedKeyword(val)) {
						Log.addDETAILFAIL("$JDDFullName ($sheetName) : Le mot clé '$val' est inconnu. Trouvé dans le cas de test $cdt colonne $name")
						status=false
					}else if (typeFile=='PREJDD' && (val instanceof String) && val.startsWith('$') && JDDKW.startWithUPD(val)) {
						Log.addDETAILFAIL("$JDDFullName ($sheetName) : Le mot clé '$val' n'est pas autorisé dans les PREJDD. Trouvé dans le cas de test $cdt colonne $name")
						status=false
					}
				}
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG,"run",status)
		return status
	}
}
