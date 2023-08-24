package myCheck

import groovy.transform.CompileStatic
import my.InfoDB
import my.Log

@CompileStatic
public class CheckColumn {

	private static final String CLASS_FORLOG = 'CheckColumn'


	public static boolean run(String typeFile, List <String> headersList, String table, boolean status) {

		Log.addTraceBEGIN(CLASS_FORLOG,"run",[typeFile:typeFile , headersList:headersList , table:table , status:status])

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
		Log.addTraceEND(CLASS_FORLOG,"run",status)
		return status
	}
}
