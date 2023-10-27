package tnrWebUI



import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrResultManager.TNRResult

/**
 * Gère les recherches par Assistant
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */
@CompileStatic
public class Checkbox {


	private static final String CLASS_NAME = 'Checkbox'



	static void clickCheckboxIfNeeded( JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL')  {

		String strStepID = StepID.getStrStepID(CLASS_NAME + 'clickCheckboxIfNeeded' + myJDD.toString() + name)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			TO myTOLbl = new TO() ; TestObject tObjLbl  = myTOLbl.make(myJDD,'Lbl'+name) ;String msgLbl = myTOLbl.getMsg()
			if (tObjLbl) {
				boolean cond = myJDD.getStrData(name)==textTrue

				/*
				 String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
				 if (!errMsg) {
				 y avait tout le if (cond) {
				 }else {
				 TNRResult.addSTEP(strStepID, "Cocher/Décocher la case à cocher '" + name + "'", status)
				 TNRResult.addDETAIL(errMsg)
				 }
				 */

				if (cond) {
					String msg = "Cocher la case à cocher '$name'"
					if (WebUI.verifyElementChecked(tObj,timeout, FailureHandling.OPTIONAL)) {
						TNRResult.addSTEPPASS(strStepID, msg)
						TNRResult.addDETAIL("déjà cochée")
					}else {

						Click.simpleClick(myJDD, 'Lbl'+name, timeout, status, msg)

						/*
						 try {
						 WebUI.click(tObjLbl, FailureHandling.STOP_ON_FAILURE)
						 TNRResult.addSTEPPASS(strStepID, "Cocher la case à cocher '" + name + "'")
						 } catch (Exception ex) {
						 TNRResult.addSTEP(strStepID, "Cocher la case à cocher '" + name + "'", status)
						 TNRResult.addDETAIL(ex.getMessage())
						 }
						 */
					}
				}else {
					String msg = "Décocher la case à cocher '$name'"
					if (WebUI.verifyElementNotChecked(tObj,timeout, FailureHandling.OPTIONAL)) {
						TNRResult.addSTEPPASS(strStepID, msg)
						TNRResult.addDETAIL("déjà décochée")
					}else {

						Click.simpleClick(myJDD, 'Lbl'+name, timeout, status, msg)

						/*
						 try {
						 WebUI.click(tObjLbl, FailureHandling.STOP_ON_FAILURE)
						 TNRResult.addSTEPPASS(strStepID, "Décocher la case à cocher '" + name + "'")
						 } catch (Exception ex) {
						 TNRResult.addSTEP(strStepID, "Décocher la case à cocher '" + name + "'", status)
						 TNRResult.addDETAIL(ex.getMessage())
						 }
						 */
					}
				}
			}else {
				TNRResult.addSTEPERROR(strStepID, "Label Case à cocher 'Lbl$name'")
				TNRResult.addDETAIL(msgTO)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID, "Case à cocher '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}






	static void verifyBoxCheckedOrNot( JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyBoxCheckedOrNot'+ myJDD.toString() + name)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			boolean cond = myJDD.getStrData(name)==textTrue
			String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				if (cond) {
					if (WebUI.verifyElementChecked(tObj,timeout, FailureHandling.OPTIONAL)) {
						TNRResult.addSTEPPASS(strStepID, "Vérifier que '${tObj.getObjectId()}'soit coché")
					}else {
						TNRResult.addSTEP(strStepID, "Vérifier que '${tObj.getObjectId()}'soit coché",status)
						TNRResult.addDETAIL('La case est décochée !')
					}
				}else {
					if (WebUI.verifyElementNotChecked(tObj,timeout, FailureHandling.OPTIONAL)) {
						TNRResult.addSTEPPASS(strStepID, "Vérifier que '${tObj.getObjectId()}' soit décoché")
					}else {
						TNRResult.addSTEP(strStepID, "Vérifier que '${tObj.getObjectId()}' soit décoché",status)
						TNRResult.addDETAIL('La case est cochée !')
					}
				}
			}else {
				TNRResult.addSTEP(strStepID, "Vérifier la case à cocher '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID, "Vérifier état case à cocher '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}
} // end of class
