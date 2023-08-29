package tnrCheck.column

import groovy.transform.CompileStatic
import tnrLog.Log
import tnrSqlManager.InfoDB


/**
 * Valide les colonnes d'un JDD ou d'un PREJDD par rapport à la table en BD
 * 
 * @author JM Lafarge
 * @version 1.0
 * 
 */
@CompileStatic
public class CheckColumn {

	private static final String CLASS_FOR_LOG = 'CheckColumn'


	/**
	 * Exécute la validation sur les colonnes de la table spécifiée.
	 *
	 * @param fileType    Type du fichier (JDD ou PREJDD).
	 * @param headersList La liste des en-têtes à valider.
	 * @param table Le nom de la table contre laquelle effectuer la validation.
	 * @return Le statut final de la validation.
	 */
	public static boolean run(String fileType, List <String> headersList, String table) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"run",[fileType:fileType , headersList:headersList , table:table ])

		Log.addDEBUGDETAIL("Contrôle des colonnes")

		boolean status = true

		if (InfoDB.isTableExist(table)) {

			InfoDB.getDatasForTable(table).each{col,details ->

				if (col in headersList) {
					int colPosInFile = headersList.indexOf(col)+1
					int colPosInDB = InfoDB.getORDINAL_POSITION(table, col)

					if (colPosInFile==colPosInDB) {
						Log.addTrace("'$col' OK")
					}else {
						Log.addDETAILFAIL("'$col' est dans le $fileType mais pas à la bonne place : $colPosInFile au lieu de $colPosInDB en BDD")
						status=false
					}
				}else {
					Log.addDETAILFAIL("Le champ '$col' n'est pas dans le $fileType")
					status=false
				}
			}
		}else {
			Log.addDETAILFAIL("La table '$table' n'existe pas !")
			status = false
		}
		Log.addTraceEND(CLASS_FOR_LOG,"run",status)
		return status
	}
}// fin de class
