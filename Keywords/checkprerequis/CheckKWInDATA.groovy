package checkprerequis

import groovy.transform.CompileStatic
import my.JDD
import my.JDDKW
import my.Log


@CompileStatic
public class CheckKWInDATA {
	
	
	static boolean run(List <List> datas, boolean PREJDD, String JDDFullName, String sheetName, boolean status) {
		
		Log.addDEBUGDETAIL("Contrôle des mots clés dans les DATA")
		datas.eachWithIndex { li,numli ->
			li.eachWithIndex { val,i ->
				if ((val instanceof String) && val.startsWith('$') && !JDDKW.isAllowedKeyword(val)) {
					Log.addDETAILFAIL("$JDDFullName ($sheetName) : Le mot clé '$val' est inconnu. Trouvé en ligne DATA ${numli+1} colonne ${i+1}")
					status=false
				}else if (PREJDD && (val instanceof String) && val.startsWith('$') && JDDKW.startWithUPD(val)) {
					Log.addDETAILFAIL("$JDDFullName ($sheetName) : Le mot clé '$val' n'est pas autorisé dans les PREJDD. Trouvé en ligne DATA ${numli+1} colonne ${i+1}")
					status=false
				}
			}
			
		}
		return status
	}
	
}
