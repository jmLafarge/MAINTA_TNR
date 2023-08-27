package tnrCheck

import groovy.transform.CompileStatic
import tnrLog.Log
import tnrSqlManager.InfoDB


/**
 * This class contains a method to validate the columns of a specified table
 * against a given list of headers. The validation checks whether each column
 * exists in the header list and whether the positions match between the
 * headers list and the table.
 * 
 * @author JM Lafarge
 * @version 1.0
 * 
 */
@CompileStatic
public class CheckColumn {

	private static final String CLASS_FOR_LOG = 'CheckColumn'


	/**
	 * Run the validation on the specified table columns.
	 *
	 * @param typeFile The type of the file containing the headers.
	 * @param headersList The list of headers to validate.
	 * @param table The table name to validate against.
	 * @param status Initial status of the validation (usually true to start with).
	 * @return The final status of the validation.
	 */
	public static boolean run(String typeFile, List <String> headersList, String table, boolean status) {

		Log.addTraceBEGIN(CLASS_FOR_LOG,"run",[typeFile:typeFile , headersList:headersList , table:table , status:status])

		Log.addDETAIL(" - Contrôle des colonnes")

		if (InfoDB.isTableExist(table)) {

			InfoDB.getDatasForTable(table).each{col,details ->

				if (col in headersList) {
					int colPosInFile = headersList.indexOf(col)+1
					int colPosInDB = InfoDB.getORDINAL_POSITION(table, col)

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
			Log.addDETAILFAIL("La table '$table' n'existe pas !")
			return false
		}
		Log.addTraceEND(CLASS_FOR_LOG,"run",status)
		return status
	}
}
