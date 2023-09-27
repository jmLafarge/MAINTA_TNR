package tnrSqlManager

import groovy.transform.CompileStatic
import tnrJDDManager.JDD
import tnrLog.Log
import tnrResultManager.TNRResult
import tnrWebUI.StepID

/**
 * Gère
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */
@CompileStatic
public class CheckInDB {
	
	private static final String CLASS_FOR_LOG = 'CheckInDB'
	
	private static final String CLASS_CODE = 'CBD'
	
	
	public static void verifyMaintaVersion(def stepID, String maintaVersion) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"verifyMaintaVersion",[stepID:stepID , maintaVersion:maintaVersion])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '01',stepID)
		String verBDD = SQL.getMaintaVersion()
		if (verBDD == maintaVersion) {
			TNRResult.addSTEPPASS(strStepID,"Controle de la version $maintaVersion en BDD")
		}else {
			TNRResult.addSTEPFAIL(strStepID,"Controle de la version $maintaVersion en BDD")
			TNRResult.addDETAIL("La valeur en BDD est $verBDD")
		}
		Log.addTraceEND(CLASS_FOR_LOG,"verifyMaintaVersion")
	}
	
	
	
	


	static void checkIDNotInBD(def stepID, JDD myJDD){

		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkIDNotInBD",[myJDD:myJDD])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '02',stepID)
		Thread.sleep(500)
		//boolean pass = true
		String status = 'PASS'
		int nbrLigneCasDeTest =myJDD.getNbrLigneCasDeTest()

		TNRResult.addBeginBlock("Début de la vérification de la suppression des valeurs en Base de Données")

		for (casDeTestNum in 1..nbrLigneCasDeTest) {
			myJDD.setCasDeTestNum(casDeTestNum)
			if (nbrLigneCasDeTest>1) {
				TNRResult.addSUBSTEP("Contrôle de la suppression du cas de test $casDeTestNum / $nbrLigneCasDeTest")
			}

			def fval =SQL.getFirstVal("SELECT count(*) FROM " + myJDD.getDBTableName() + SQL.getWhereWithAllPK(myJDD,casDeTestNum))
			int ret = (fval) ? fval as int: 0
			if (ret>0) {
				TNRResult.addDETAIL("Supression KO")
				//pass = false
				status = 'FAIL'
			}
		}

		TNRResult.addEndBlock("Fin de la  vérification de la suppression des valeurs en Base de Données",status)
		Log.addTraceEND(CLASS_FOR_LOG,"checkIDNotInBD")
	}

	
	
	
	/**
	 * Vérification à la fin des insertions, donc dans le cas de 2 insertions, il faut boucler sur le nombre d'insertions
	 *
	 * @param
	 * @return
	 *
	 *
	 *
	 */
	static void checkJDDWithBD(def stepID, JDD myJDD,Map specificValueMap=[:],String sql =''){

		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkJDDWithBD",[stepID:stepID , myJDD:myJDD , specificValueMap:specificValueMap , sql:sql])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '03',stepID)
		
		Thread.sleep(500)

		String verifStatus = 'PASS'

		//TNRResult.addSTEP("Début de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
		TNRResult.addBeginBlock("Début de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')

		int nbrLigneCasDeTest =myJDD.getNbrLigneCasDeTest()


		for (casDeTestNum in 1..nbrLigneCasDeTest) {
			myJDD.setCasDeTestNum(casDeTestNum)
			if (nbrLigneCasDeTest>1) {
				TNRResult.addSUBSTEP("Contrôle cas de test $casDeTestNum / $nbrLigneCasDeTest")
			}

			//def rows
			def row

			String query =''
			if (sql) {
				row = SQL.getFirstRow(sql)

			}else {

				query = "SELECT * FROM " + myJDD.getDBTableName() + SQL.getWhereWithAllPK(myJDD,casDeTestNum)

				Log.addTrace("query =  $query")
				try {
					List rows = SQL.getRows(query)
					if (rows.size() == 0) {
						TNRResult.addDETAIL("Pas de résultat pour la requête : $query")
						verifStatus = 'FAIL'
					}else if (rows.size() > 1){
						TNRResult.addDETAIL(rows.size() + "résultats pour la requête : $query")
						verifStatus = 'FAIL'
					}else {
						row=rows[0]
					}
				}
				catch(Exception ex) {
					verifStatus = 'ERROR'
					TNRResult.addDETAIL("Erreur d'execution de sql.rows($query) : ")
					TNRResult.addDETAIL(ex.getMessage())
				}
			}



			if (verifStatus =='PASS') {

				row.each{fieldName,val ->
					verifStatus = SQL.checkValue(myJDD,fieldName.toString(), val,verifStatus, specificValueMap,casDeTestNum)
				}//row

			}//pass
		}//for

		TNRResult.addEndBlock("Fin de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')',verifStatus)
		Log.addTraceEND(CLASS_FOR_LOG,"checkJDDWithBD")

	}
	
}
