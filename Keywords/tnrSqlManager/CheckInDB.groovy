package tnrSqlManager

import javax.swing.text.DefaultStyledDocument
import javax.swing.text.rtf.RTFEditorKit

import groovy.transform.CompileStatic
import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
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
	
	private static final String CLASS_NAME = 'CheckInDB'
		
	
	
	public static void verifyMaintaVersion( String maintaVersion) {
		Log.addTraceBEGIN(CLASS_NAME,"verifyMaintaVersion",[ maintaVersion:maintaVersion])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyMaintaVersion')
		String verBDD = SQL.getMaintaVersion()
		if (verBDD == maintaVersion) {
			TNRResult.addSTEPPASS(strStepID,"Controle de la version $maintaVersion en BDD")
		}else {
			TNRResult.addSTEPFAIL(strStepID,"Controle de la version $maintaVersion en BDD")
			TNRResult.addDETAIL("La valeur en BDD est $verBDD")
		}
		Log.addTraceEND(CLASS_NAME,"verifyMaintaVersion")
	}
	
	
	
	


	static void checkIDNotInBD( JDD myJDD){

		Log.addTraceBEGIN(CLASS_NAME,"checkIDNotInBD",[myJDD:myJDD.toString()])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'checkIDNotInBD' + myJDD.toString())
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
		Log.addTraceEND(CLASS_NAME,"checkIDNotInBD")
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
	static void checkJDDWithBD( JDD myJDD,Map specificValueMap=[:],String sql =''){

		Log.addTraceBEGIN(CLASS_NAME,"checkJDDWithBD",[ myJDD:myJDD.toString() , specificValueMap:specificValueMap , sql:sql])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'checkJDDWithBD' + myJDD.toString())
		
		Thread.sleep(500)

		String verifStatus = 'PASS'

		//04102023 TNRResult.addBeginBlock("Début de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
		TNRResult.addSTEPBLOCK("Vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')

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

				String whereWithAllPK = SQL.getWhereWithAllPK(myJDD,casDeTestNum)
				query = "SELECT * FROM " + myJDD.getDBTableName() + whereWithAllPK
				
				if (whereWithAllPK.contains(JDDKW.getKW_SEQUENCEID())) {
					TNRResult.addSTEPFAIL(strStepID, "Requête impossible, pas de SEQUENCE_ID connu")
					TNRResult.addDETAIL(query)
					verifStatus = 'FAIL'
				}else {
					try {
						List rows = SQL.getRows(query)
						if (rows.size() == 0) {
							TNRResult.addSTEPFAIL(strStepID, "Pas de résultat pour la requête : $query")
							verifStatus = 'FAIL'
						}else if (rows.size() > 1){
							TNRResult.addSTEPFAIL(strStepID, rows.size() + "résultats pour la requête : $query")
							verifStatus = 'FAIL'
						}else {
							row=rows[0]
						}
					}
					catch(Exception ex) {
						verifStatus = 'ERROR'
						TNRResult.addSTEPERROR(strStepID, "Erreur d'execution de sql.rows($query) : ")
						TNRResult.addDETAIL(ex.getMessage())
					}
				}
			}


			//04102023 if (verifStatus =='PASS') {

				row.each{fieldName,val ->
					//verifStatus = SQL.checkValue(myJDD,fieldName.toString(), val,verifStatus, specificValueMap,casDeTestNum)
					checkValue(myJDD,fieldName.toString(), val, specificValueMap,casDeTestNum)
				}//row

			//04102023}//pass
		}//for

		//04102023 TNRResult.addEndBlock("Fin de la vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')',verifStatus)
		Log.addTraceEND(CLASS_NAME,"checkJDDWithBD")

	}
	
	
	
	
	
	public static void checkValue(tnrJDDManager.JDD myJDD, String fieldName, def valDB, Map specificValueMap, int casDeTestNum) {
		Log.addTraceBEGIN(CLASS_NAME,"checkValue",[myJDD:myJDD.toString() , fieldName:fieldName , val:valDB , specificValueMap:specificValueMap,casDeTestNum:casDeTestNum])
		
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'checkValue' + myJDD.toString() + fieldName)
		def valJDD = myJDD.getData(fieldName,casDeTestNum)
		boolean specificValue = !specificValueMap.isEmpty() && specificValueMap.containsKey(fieldName)
		
		if (myJDD.myJDDParam.isOBSOLETE(fieldName)) {
			addStepInfo(strStepID,fieldName, "Pas de contrôle la rubrique est OBSOLETE")
			
		}else if (myJDD.isDataUPD(fieldName,casDeTestNum)) {

			String newVal = myJDD.getData(fieldName,casDeTestNum,true).toString()

			if (newVal==valDB.toString()) {
				addStepPass(strStepID, '',fieldName,newVal,valDB)
			}else {
				addStepFail(strStepID, '',fieldName,newVal,valDB)
			}
		
		}else if (JDDKW.isNU(valJDD)){
			
			addStepInfo(strStepID,fieldName, "Pas de contrôle la rubrique est NU (non utilisée)")
			
		}else if (JDDKW.isNULL(valJDD)) {
			
			if (valDB == null) {
				addStepPass(strStepID, '',fieldName,'NULL',valDB)
			}else {
				addStepFail(strStepID, '',fieldName,'NULL',valDB)
			}

		}else if (JDDKW.isVIDE(valJDD)) {
			
			if (valDB == '') {
				addStepPass(strStepID, '',fieldName,'VIDE',valDB)
			}else {
				addStepFail(strStepID, '',fieldName,'VIDE',valDB)
			}

		}else if (JDDKW.isORDRE(valJDD)) {

			addStepFail(strStepID, 'ORDRE ***** reste àfaire *****',fieldName,valJDD,valDB)
			
		}else if (JDDKW.isSEQUENCEID(valJDD)) {

			addStepFail(strStepID, 'SEQUENCEID',fieldName,valJDD,valDB)
			
		}else if (JDDKW.isDATE(valJDD)) {
			
			if (valDB instanceof java.sql.Timestamp) {
				addStepPass(strStepID, 'DATEJML',fieldName,valJDD,valDB)
			}else {
				addStepFail(strStepID, 'DATEJML',fieldName,valJDD,valDB)
			}
			
			
		}else if (JDDKW.isDATETIME(valJDD)) {
			
			
			if (valDB instanceof java.sql.Timestamp) {
				addStepPass(strStepID, 'DATETIME',fieldName,valJDD,valDB)
			}else {
				addStepFail(strStepID, 'DATETIME',fieldName,valJDD,valDB)
			}
			
		}else if (InfoDB.isImage(myJDD.getDBTableName(), fieldName)) {
				

			
			if ( valDB == InfoDB.castJDDVal(myJDD.getDBTableName(), fieldName, valJDD)) {
				addStepPass(strStepID, '',fieldName,valJDD,valDB)
			}else {
				addStepFail(strStepID, '',fieldName,valJDD,valDB)
			}
			
		}else if (!specificValue && myJDD.myJDDParam.getFOREIGNKEYFor(fieldName)) {

			if (!JDDKW.isNU(valJDD) && !JDDKW.isNULL(valJDD)) {
				if (!SQL.checkForeignKey(myJDD, fieldName, valDB)) {
				}
			}else {
				TNRResult.addSTEPINFO('',"$fieldName est NU ou NULL")
			}

		}else if (specificValue) {

			String valClass = valDB ? valDB.getClass() : 'NULL'
			Log.addTrace("Pour '$fieldName' la class de la valeur en BD est:$valDB,  la class de la valeur spécifique est  : " + specificValueMap[fieldName].getClass())


			if ( valDB == InfoDB.castJDDVal(myJDD.getDBTableName(), fieldName, specificValueMap[fieldName])) {
				addStepPass(strStepID, 'spécifique',fieldName,specificValueMap[fieldName],valDB)
			}else {
				addStepFail(strStepID, 'spécifique',fieldName,specificValueMap[fieldName],valDB)
			}
		}else {

		}

		Log.addTraceEND(CLASS_NAME,"checkValue")
	}
	
	private static addStepInfo(String strStepID, String fieldName, String msg) {
		//Log.addTrace("Contrôle de la valeur $type de '$fieldName' OK : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'" )
		TNRResult.addSTEPINFO(strStepID,"- '$fieldName' : $msg" )
	}
	
	private static addStepPass(String strStepID, String type,String fieldName, def valJDD, def val) {
		//Log.addTrace("Contrôle de la valeur $type de '$fieldName' OK : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'" )
		TNRResult.addSTEPPASS(strStepID,"- '$fieldName' : Contrôle de la valeur $type, la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'" )
	}



	private static addStepFail(String strStepID, String type,String fieldName, def valJDD, def val) {
		//TNRResult.addDETAIL("Contrôle de la valeur $type de '$fieldName' KO : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'")
		TNRResult.addSTEPFAIL(strStepID,"- '$fieldName' : Contrôle de la valeur $type, la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'")
	}
	
	
}
