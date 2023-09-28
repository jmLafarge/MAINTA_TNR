package tnrCheck.data

import groovy.transform.CompileStatic
import tnrJDDManager.JDDKW
import tnrJDDManager.JDDParam
import tnrLog.Log
import tnrSqlManager.InfoDB


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

	private static final String CLASS_NAME = 'CheckKW'


	/**
	 * Vérifie la validité des mots-clés dans les données du JDD/PREJDD.
	 *
	 * @param typeFile     Le type du fichier contenant les données (JDD ou PREJDD)
	 * @param datasList    La liste des données.
	 * @param myJDDParam   Le JDDParam du JDD
	 * @param JDDFullName  Le nom complet du fichier JDD/PREJDD.
	 * @param sheetName    Le nom de la feuille
	 * @return             Le statut après vérification (false si FAIL, sinon true).
	 */
	static boolean run(String typeFile,List <Map<String, Map<String, Object>>> datasList, JDDParam myJDDParam, String JDDFullName, String sheetName) {
		Log.addTraceBEGIN(CLASS_NAME,"run",[typeFile:typeFile , 'datasList.size()':datasList.size() , JDDFullName:JDDFullName , sheetName:sheetName ])
		Log.addDEBUGDETAIL("Contrôle des mots clés dans les DATA")
		boolean status =true
		List <String> namesWithSEQUENCEID = []
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
					if (JDDKW.isSEQUENCEID(val)) {
						namesWithSEQUENCEID.add(name)
					}
				}
			}
		}
		status &= checkSEQUENCE(namesWithSEQUENCEID.unique(), myJDDParam, JDDFullName, sheetName)
		Log.addTraceEND(CLASS_NAME,"run",status)
		return status
	}




	/**
	 * Vérifie si les rubriques avec SEQUENCEID ont bien le paramèytre SEQUENCE renseigné avec la table des sequence_ID.
	 *
	 * @param namesWithSEQUENCEID	Liste des noms avec des valeurs '$SEQUENCEID'
	 * @param myJDDParam 			paramètres du JDD
	 * @param JDDFullName 			Nom complet du JDD
	 * @param sheetName 			Nom de la feuille du JDD
	 * @return 						Retourne 'true' si toutes les séquences sont valides, 'false' sinon
	 *
	 */
	static private boolean checkSEQUENCE(List <String> namesWithSEQUENCEID, JDDParam myJDDParam,String JDDFullName, String sheetName) {
		Log.addTraceBEGIN(CLASS_NAME,"checkSEQUENCE",[namesWithSEQUENCEID:namesWithSEQUENCEID , myJDDParam:myJDDParam , JDDFullName:JDDFullName , sheetName:sheetName ])
		boolean status =true
		namesWithSEQUENCEID.each { name ->
			String tableSEQUENCE = myJDDParam.getSEQUENCEFor(name)
			if (!tableSEQUENCE) {
				Log.addDETAILFAIL("$JDDFullName ($sheetName) : Manque le paramètre SEQUENCE pour '$name'")
				status=false
			}else if(!InfoDB.isTableExist(tableSEQUENCE)) {
				Log.addDETAILFAIL("$JDDFullName ($sheetName) : La table '$tableSEQUENCE' pour la SEQUENCE de '$name' n'existe pas !")
				status=false
			}
		}
		Log.addTraceEND(CLASS_NAME,"checkSEQUENCE",status)
		return status
	}



}// Fin de class
