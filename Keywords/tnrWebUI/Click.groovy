package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrLog.Log
import tnrResultManager.TNRResult

/**
 * Gère 
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */
@CompileStatic
public class Click {

	private static final String CLASS_FOR_LOG = 'Click'

	private static final String CLASS_CODE = 'CLK'


	static boolean simpleClick(def stepID,JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "simpleClick", [stepID:stepID , myJDD: myJDD, name: name, timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '01',stepID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				try {
					WebUI.click(tObj, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS(strStepID,"Clic sur '${tObj.getObjectId()}'")
					ret= true
				} catch (Exception ex) {
					TNRResult.addSTEP(strStepID,"Clic sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP(strStepID,"Clic sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID,"Clic sur '$name' imposible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "simpleClick",ret)
		return ret
	}
	
	
	
	static boolean doubleClick(def stepID, JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "doubleClick", [stepID:stepID , myJDD: myJDD, name: name, timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '02',stepID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				try {
					WebUI.doubleClick(tObj, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS(strStepID, "Double click sur '${tObj.getObjectId()}'")
					ret= true
				} catch (Exception ex) {
					TNRResult.addSTEP(strStepID, "Double click sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP(strStepID, "Double clic sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID, "Double click sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "doubleClick",ret)
		return ret
	}
	
	
}
