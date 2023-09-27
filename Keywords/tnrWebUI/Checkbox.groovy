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


	private static final String CLASS_FOR_LOG = 'Checkbox'

	private static final String CLASS_CODE = 'BOX'


	static void scrollAndCheckIfNeeded(def stepID, JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL')  {

		String strStepID = StepID.getStrStepID(CLASS_CODE, '01',stepID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			TO myTOLbl = new TO() ; TestObject tObjLbl  = myTOLbl.make(myJDD,'Lbl'+name) ;String msgLbl = myTOLbl.getMsg()
			if (tObjLbl) {
				boolean cond = myJDD.getStrData(name)==textTrue

				String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
				if (!errMsg) {
					if (cond) {
						if (WebUI.verifyElementChecked(tObj,timeout, FailureHandling.OPTIONAL)) {
							TNRResult.addSTEPPASS(strStepID, "Cocher la case à cocher '" + name + "'")
							TNRResult.addDETAIL("déjà cochée")
						}else {
							try {

								WebUI.click(tObjLbl, FailureHandling.STOP_ON_FAILURE)
								TNRResult.addSTEPPASS(strStepID, "Cocher la case à cocher '" + name + "'")
								/*
								 }else {
								 TNRResult.addSTEP(strStepID, "Cocher la case à cocher '" + name + "'", status)
								 }
								 */
							} catch (Exception ex) {
								TNRResult.addSTEP(strStepID, "Cocher la case à cocher '" + name + "'", status)
								TNRResult.addDETAIL(ex.getMessage())
							}
						}
					}else {
						if (WebUI.verifyElementNotChecked(tObj,timeout, FailureHandling.OPTIONAL)) {
							TNRResult.addSTEPPASS(strStepID, "Décocher la case à cocher '" + name + "'")
							TNRResult.addDETAIL("déjà décochée")
						}else {
							try {

								WebUI.click(tObjLbl, FailureHandling.STOP_ON_FAILURE)
								TNRResult.addSTEPPASS(strStepID, "Décocher la case à cocher '" + name + "'")
								/*
								 }else {
								 TNRResult.addSTEP(strStepID, "Décocher la case à cocher '" + name + "'", status)
								 }
								 */
							} catch (Exception ex) {
								TNRResult.addSTEP(strStepID, "Décocher la case à cocher '" + name + "'", status)
								TNRResult.addDETAIL(ex.getMessage())
							}
						}
					}
				}else {
					TNRResult.addSTEP(strStepID, "Cocher/Décocher la case à cocher '" + name + "'", status)
					TNRResult.addDETAIL(errMsg)
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






	static void verifyElementCheckedOrNot(def stepID, JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		String strStepID = StepID.getStrStepID(CLASS_CODE, '02',stepID)
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











	static void verifyCheckBoxImgChecked(String stepID, JDD myJDD, String name, String status = 'FAIL')  {
		String strStepID = StepID.getStrStepID(CLASS_CODE, '03',stepID)
		def etat = getCheckBoxImgStatus(myJDD, name)
		if (etat ==null) {
			// l'erreur est déjà remontée par getCheckBoxImgStatus
		}else if (etat) {
			TNRResult.addSTEPPASS(strStepID, "Vérifier que la case à cocher (img) '" + name + "' soit cochée")
		}else {
			TNRResult.addSTEP(strStepID, "Vérifier que la case à cocher (img) '" + name + "' soit cochée", status)
		}
	}





	private static void verifyCheckBoxImgNotChecked(String stepID, JDD myJDD, String name, String status = 'FAIL')  {
		String strStepID = StepID.getStrStepID(CLASS_CODE, '04',stepID)
		def etat = getCheckBoxImgStatus(myJDD, name)
		if (etat ==null) {
			// l'erreur est déjà remontée par getCheckBoxImgStatus
		}else if (!etat) {
			TNRResult.addSTEPPASS(strStepID, "Vérifier que la case à cocher (img) '" + name + "' soit cochée")
		}else {
			TNRResult.addSTEP(strStepID, "Vérifier que la case à cocher (img) '" + name + "' soit cochée", status)
		}
	}





	static void verifyImgCheckedOrNot(String stepID, JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		String strStepID = StepID.getStrStepID(CLASS_CODE, '05',stepID)
		boolean cond = myJDD.getStrData(name)==textTrue
		if (cond) {
			verifyCheckBoxImgChecked(strStepID, myJDD, name, status)
		}else {
			verifyCheckBoxImgNotChecked(strStepID, myJDD, name, status)
		}
	}





	static void verifyImg(String stepID, JDD myJDD, String name, boolean cond, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		String strStepID = StepID.getStrStepID(CLASS_CODE, '06',stepID)
		if (cond) {
			STEP.verifyElementPresent(strStepID, myJDD, name, timeout, status)
		}
	}



	static boolean getCheckBoxImgStatus(JDD myJDD, String name)  {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (WebUI.getAttribute(tObj, 'src').endsWith('133.gif')) {
				return true
			}else if (WebUI.getAttribute(tObj, 'src').endsWith('134.gif')) {
				return false
			}else {
				TNRResult.addSTEPERROR('', "Vérifier état case à cocher '$name'")
				TNRResult.addDETAIL("L'attribut src de l'objet " + name + " n'est pas conforme, la valeur est : " + WebUI.getAttribute(tObj, 'src'))
				return null
			}
		}else {
			TNRResult.addSTEPERROR('', "Vérifier état case à cocher '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}
}
