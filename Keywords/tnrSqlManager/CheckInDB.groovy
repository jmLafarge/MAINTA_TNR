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
		Thread.sleep(100)
		int nbrLigneCasDeTest =myJDD.getNbrLigneCasDeTest()

		TNRResult.addSTEPACTION("Vérification de la suppression des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
		TNRResult.setAllowScreenshots(false)

		for (casDeTestNum in 1..nbrLigneCasDeTest) {
			myJDD.setCasDeTestNum(casDeTestNum)
			String strStepID = StepID.getStrStepID(CLASS_NAME + 'checkIDNotInBD' + myJDD.toString())
			if (nbrLigneCasDeTest>1) {
				TNRResult.addSUBSTEP("Contrôle de la suppression du cas de test $casDeTestNum / $nbrLigneCasDeTest")
			}

			def fval =SQL.getFirstVal("SELECT count(*) FROM " + myJDD.getDBTableName() + SQL.getWhereWithAllPK(myJDD))
			int ret = (fval) ? fval as int: 0
			if (ret == 0) {
				TNRResult.addSTEPPASS(strStepID,"Suppression OK" )
			}else {
				TNRResult.addSTEPFAIL(strStepID,"Suppression KO" )
			}
		}
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

		Thread.sleep(100)

		TNRResult.addSTEPACTION("Vérification des valeurs en Base de Données ("+ myJDD.getDBTableName() + ')')
		TNRResult.setAllowScreenshots(false)
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

				String whereWithAllPK = SQL.getWhereWithAllPK(myJDD)
				query = "SELECT * FROM " + myJDD.getDBTableName() + whereWithAllPK

				if (whereWithAllPK.contains(JDDKW.getKW_SEQUENCEID())) {
					TNRResult.addSTEPFAIL(strStepID, "Requête impossible, pas de SEQUENCE_ID connu")
					TNRResult.addDETAIL(query)
				}else {
					try {
						List rows = SQL.getRows(query)
						if (rows.size() == 0) {
							TNRResult.addSTEPFAIL(strStepID, "Pas de résultat pour la requête : $query")
						}else if (rows.size() > 1){
							TNRResult.addSTEPFAIL(strStepID, rows.size() + "résultats pour la requête : $query")
						}else {
							row=rows[0]

						}
					}
					catch(Exception ex) {
						TNRResult.addSTEPERROR(strStepID, "Erreur d'execution de sql.rows($query) : ")
						TNRResult.addDETAIL(ex.getMessage())
					}
				}
			}

			if (row) {
				row.each{fieldName,val ->
					checkValue(myJDD,fieldName.toString(), val, specificValueMap,casDeTestNum)
				}
			}
		}
		TNRResult.setAllowScreenshots(true)
		Log.addTraceEND(CLASS_NAME,"checkJDDWithBD")
	}





	public static void checkValue(tnrJDDManager.JDD myJDD, String fieldName, def valDB, Map specificValueMap, int casDeTestNum) {
		Log.addTraceBEGIN(CLASS_NAME,"checkValue",[myJDD:myJDD.toString() , fieldName:fieldName , val:valDB , specificValueMap:specificValueMap,casDeTestNum:casDeTestNum])

		String strStepID = StepID.getStrStepID(CLASS_NAME + 'checkValue' + myJDD.toString() + fieldName)
		def valJDD = myJDD.getData(fieldName,casDeTestNum)
		boolean specificValue = !specificValueMap.isEmpty() && specificValueMap.containsKey(fieldName)
		String fullFieldName =  myJDD.getDBTableName() + '.' + fieldName

		if (myJDD.myJDDParam.isOBSOLETE(fieldName)) {
			addStepInfo(strStepID,fieldName, "La rubrique est OBSOLETE")

		}else if (myJDD.isDataUPD(fieldName,casDeTestNum)) {
			String newVal = myJDD.getData(fieldName,casDeTestNum,true).toString()

			if (newVal==valDB.toString()) {
				addStepPass(strStepID, fullFieldName,newVal,valDB)
			}else {
				addStepFail(strStepID, fullFieldName,newVal,valDB)
			}

		}else if (JDDKW.isNU(valJDD)){
			addStepInfo(strStepID,fullFieldName, "La rubrique est NU (non utilisée)")

		}else if (JDDKW.isNULL(valJDD)) {
			if (valDB == null) {
				addStepPass(strStepID, fullFieldName,'NULL',valDB)
			}else {
				addStepFail(strStepID, fullFieldName,'NULL',valDB)
			}

		}else if (JDDKW.isVIDE(valJDD)) {
			if (valDB == '') {
				addStepPass(strStepID, fullFieldName,'VIDE',valDB)
			}else {
				addStepFail(strStepID, fullFieldName,'VIDE',valDB)
			}

		}else if (JDDKW.isORDRE(valJDD)) {

			addStepFail(strStepID, fullFieldName,valJDD,valDB)

		}else if (JDDKW.isSEQUENCEID(valJDD)) {
			addStepFail(strStepID, fullFieldName,valJDD,valDB)

		}else if (JDDKW.isDATE(valJDD)) {
			if (valDB instanceof java.sql.Timestamp) {
				addStepPass(strStepID, fullFieldName,valJDD,valDB)
			}else {
				addStepFail(strStepID, fullFieldName,valJDD,valDB)
			}


		}else if (JDDKW.isDATETIME(valJDD)) {
			if (valDB instanceof java.sql.Timestamp) {
				addStepPass(strStepID, fullFieldName,valJDD,valDB)
			}else {
				addStepFail(strStepID, fullFieldName,valJDD,valDB)
			}

		}else if (InfoDB.isImage(myJDD.getDBTableName(), fieldName)) {
			valDB = SQL.getTextFromImageType(myJDD,fieldName)
			if ( valDB == (valJDD.toString() + '\n')) {
				addStepPass(strStepID,fullFieldName,valJDD,valDB)
			}else {
				addStepFail(strStepID,fullFieldName,valJDD,valDB)
			}

		}else if (!specificValue && myJDD.myJDDParam.getFOREIGNKEYFor(fieldName)) {
			def valueFromFK = SQL.getValueFromForeignKey(myJDD, fieldName)
			if (valueFromFK == null) {
				addStepFail(strStepID,fullFieldName,"$valJDD (null)",valDB)
			}else if ( valDB == valueFromFK) {
				addStepPass(strStepID,fullFieldName,"$valJDD ($valueFromFK)",valDB)
			}else {
				addStepFail(strStepID, fullFieldName,"$valJDD ($valueFromFK)",valDB)
			}

		}else if (specificValue) {
			String valClass = valDB ? valDB.getClass() : 'NULL'
			Log.addTrace("Pour '$fullFieldName' la class de la valeur en BD est:$valDB,  la class de la valeur spécifique est  : " + specificValueMap[fieldName].getClass())
			if ( valDB == InfoDB.castJDDVal(myJDD.getDBTableName(), fieldName, specificValueMap[fieldName])) {
				addStepPass(strStepID,fullFieldName,specificValueMap[fieldName],valDB)
			}else {
				addStepFail(strStepID,fullFieldName,specificValueMap[fieldName],valDB)
			}

		}else {
			if ( valDB == InfoDB.castJDDVal(myJDD.getDBTableName(), fieldName, valJDD)) {
				addStepPass(strStepID,fullFieldName,valJDD,valDB)
			}else {
				addStepFail(strStepID,fullFieldName,valJDD,valDB)
			}
		}
		Log.addTraceEND(CLASS_NAME,"checkValue")
	}


	private static addStepInfo(String strStepID, String fullFieldName, String msg) {
		//Log.addTrace("Contrôle de la valeur $type de '$fieldName' OK : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'" )
		TNRResult.addSTEPINFO(strStepID,"Contrôle en BDD de '$fullFieldName' : $msg" )
	}


	private static addStepPass(String strStepID, String fullFieldName, def valJDD, def val) {
		//Log.addTrace("Contrôle de la valeur $type de '$fieldName' OK : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'" )
		TNRResult.addSTEPPASS(strStepID,"Contrôle en BDD de '$fullFieldName' : La valeur attendue est '$valJDD' et la valeur en BD est  : '$val'" )
	}



	private static addStepFail(String strStepID, String fullFieldName, def valJDD, def val) {
		//TNRResult.addDETAIL("Contrôle de la valeur $type de '$fieldName' KO : la valeur attendue est '$valJDD' et la valeur en BD est  : '$val'")
		TNRResult.addSTEPFAIL(strStepID,"Contrôle en BDD de '$fullFieldName' : La valeur attendue est '$valJDD' et la valeur en BD est  : '$val'")
	}


}
