package tnrCheck.header

import groovy.transform.CompileStatic
import tnrLog.Log
import tnrSqlManager.InfoDB


/**
 * Valide la table et les colonnes d'un JDD ou d'un PREJDD par rapport à la table en BDD
 * 
 * @author JM Lafarge
 * @version 1.0
 * 
 */
@CompileStatic
public class CheckHeader {

	private static final String CLASS_FOR_LOG = 'CheckHeader'



	/**
	 * Vérifie si la table existe en BDD et si les colonnes du JDD/PREJDD correspondent aux colonnes de la table en BDD.
	 *
	 * @param typeFile     Le type du fichier (JDD ou PREJDD)
	 * @param headersList  La liste des en-têtes de colonnes 
	 * @param tableName    Le nom de la table en BDD
	 * @return             Le statut après vérification (true si pas de FAIL, sinon false )
	 */
	public static boolean run(String typeFile, List <String> headersList, String tableName) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"run",[typeFile:typeFile , headersList:headersList , table:tableName ])

		Log.addDEBUGDETAIL("Contrôle de la table")
		boolean status = true
		// Vérifie si la table existe
		if (InfoDB.isTableExist(tableName)) {
			Log.addDEBUGDETAIL("Contrôle des colonnes")
			// Parcours chaque colonne de la table
			InfoDB.getDatasForTable(tableName).each{col,details ->
				// Vérifie si la colonne est présente dans les en-têtes du fichier
				if (col in headersList) {
					int colPosInFile = headersList.indexOf(col)+1
					int colPosInDB = InfoDB.getORDINAL_POSITION(tableName, col)
					// Vérifie si la position de la colonne est la même dans le fichier et en BDD
					if (colPosInFile==colPosInDB) {
						Log.addTrace("'$col' OK")
					}else {
						Log.addDETAILFAIL("'$col' est dans le $typeFile mais pas à la bonne place : $colPosInFile au lieu de $colPosInDB en BDD")
						status=false
					}
				}else {
					Log.addDETAILFAIL("Le champ '$col' n'est pas dans le $typeFile")
					status=false
				}
			}
		}else {
			Log.addDETAILFAIL("La table '$tableName' n'existe pas !")
			status=false
		}
		Log.addTraceEND(CLASS_FOR_LOG,"run",status)
		return status
	}

}// fin de class
