package tnrCheck.data

import groovy.transform.CompileStatic
import tnrJDDManager.JDDKW
import tnrLog.Log


/**
 *
 * vérifie les mot clés (KW) dans le JDD ou dans le PREJDDD
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
@CompileStatic
public class CheckKW {

	private static final String CLASS_FOR_LOG = 'CheckKW'


	/**
	 * Vérifie la validité des mots-clés dans les données du JDD/PREJDD.
	 *
	 * @param typeFile     Le type du fichier contenant les données (JDD ou PREJDD)
	 * @param datasList    La liste des données.
	 * @param JDDFullName  Le nom complet du fichier JDD/PREJDD.
	 * @param sheetName    Le nom de la feuille
	 * @return             Le statut après vérification (true si pas de FAIL, sinon false).
	 */
	static boolean run(String typeFile,List <Map<String, Map<String, Object>>> datasList, String JDDFullName, String sheetName) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"run",[typeFile:typeFile , 'datasList.size()':datasList.size() , JDDFullName:JDDFullName , sheetName:sheetName ])
		Log.addDEBUGDETAIL("Contrôle des mots clés dans les DATA")
		boolean status =true
		// Parcours de chaque ligne de données
		datasList.each { lines ->
			lines.each { cdt,datas ->
				datas.each { name,val ->
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

}// Fin de class
