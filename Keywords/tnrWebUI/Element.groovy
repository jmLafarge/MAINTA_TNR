package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrLog.Log
import tnrResultManager.TNRResult

/**
 * Gère les recherches par Assistant
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */
@CompileStatic
public class Element {

	private static final String CLASS_NAME = 'Element'



	static boolean verifyElementPresent( JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "verifyElementPresent", [ myJDD:myJDD.toString() , name: name , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyElementPresent'+ myJDD.toString() + name)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			try {
				WebUI.verifyElementPresent(tObj, timeout, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS(strStepID,"Vérifier que '${tObj.getObjectId()}' soit présent")
				ret = true
			}catch (Exception ex) {
				TNRResult.addSTEP(strStepID,"Vérifier que '${tObj.getObjectId()}' soit présent", status)
				TNRResult.addDETAIL(ex.getMessage())
				TNRResult.addDETAIL("XPATH = "+tObj.getSelectorCollection().get(SelectorMethod.XPATH))
			}
		}else {
			TNRResult.addSTEPERROR(strStepID,"Vérifier que '$name' soit présent impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_NAME, "verifyElementPresent",ret)
		return ret
	}




	static boolean verifyElementVisible( JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "verifyElementVisible", [ myJDD:myJDD.toString() , name: name , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyElementVisible'+ myJDD.toString() + name)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
			WUI.delay(500)
			if (!errMsg) {
				TNRResult.addSTEPPASS(strStepID,"Vérifier que l'élément '${tObj.getObjectId()}' soit visible ")
				ret = true
			}else {
				TNRResult.addSTEP(strStepID,"Vérifier que l'élément '${tObj.getObjectId()}' soit visible ",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID,"Vérifier que l'élément '$name' soit visible impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_NAME, "verifyElementVisible",ret)
		return ret
	}


	static boolean verifyElementNotPresent( JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "verifyElementNotPresent", [ myJDD:myJDD.toString() , name: name , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyElementNotPresent'+ myJDD.toString() + name)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			try {
				WebUI.verifyElementNotPresent(tObj, timeout, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS(strStepID, "Vérifier que '${tObj.getObjectId()}' ne soit plus présent")
				ret = true
			} catch (Exception ex) {
				TNRResult.addSTEP(strStepID, "Vérifier que '${tObj.getObjectId()}' ne soit plus présent", status)
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR(strStepID, "Vérifier que '$name' ne soit plus présent impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_NAME, "verifyElementNotPresent",ret)
		return ret
	}
}
