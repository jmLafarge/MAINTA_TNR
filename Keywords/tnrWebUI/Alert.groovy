package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrLog.Log
import tnrResultManager.TNRResult


/**
 * Personalise les actions WebUI
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */

@CompileStatic
public class Alert {

	private static final String CLASS_FOR_LOG = 'Alert'

	private static final String CLASS_CODE = 'ALE'



	private static boolean waitForAlert( def stepID, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "waitForAlert", [ stepID:stepID , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '01',stepID)
		boolean ret = false
		try {
			WebUI.waitForAlert(timeout, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(strStepID, "Attendre la fenetre de confirmation")
			ret = true
		} catch (Exception ex) {
			TNRResult.addSTEP(strStepID, "Attendre la fenetre de confirmation",status)
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "waitForAlert",ret)
		return ret
	}




	private static boolean acceptAlert(def stepID, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "acceptAlert", [ stepID:stepID , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '02',stepID)
		boolean ret = false
		try {
			WebUI.acceptAlert(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(strStepID, "Accepter la demande de confirmation")
			ret = true
		} catch (Exception ex) {
			TNRResult.addSTEP(strStepID, "Accepter la demande de confirmation", status)
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "acceptAlert",ret)
		return ret
	}




	public static boolean waitAndAcceptAlert(def stepID, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "waitAndAcceptAlert", [ stepID:stepID , status:status])
		boolean ret = false
		if (waitForAlert(stepID,timeout, status)) {
			ret = acceptAlert(stepID,status)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "waitAndAcceptAlert",ret)
		return ret
	}
}
