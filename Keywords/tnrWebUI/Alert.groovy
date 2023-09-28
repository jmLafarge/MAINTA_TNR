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

	private static final String CLASS_NAME = 'Alert'




	private static boolean waitForAlert(int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "waitForAlert", [  timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'waitForAlert')
		boolean ret = false
		try {
			WebUI.waitForAlert(timeout, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(strStepID, "Attendre la fenetre de confirmation")
			ret = true
		} catch (Exception ex) {
			TNRResult.addSTEP(strStepID, "Attendre la fenetre de confirmation",status)
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_NAME, "waitForAlert",ret)
		return ret
	}




	private static boolean acceptAlert( String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "acceptAlert", [  status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'acceptAlert')
		boolean ret = false
		try {
			WebUI.acceptAlert(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(strStepID, "Accepter la demande de confirmation")
			ret = true
		} catch (Exception ex) {
			TNRResult.addSTEP(strStepID, "Accepter la demande de confirmation", status)
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_NAME, "acceptAlert",ret)
		return ret
	}




	public static boolean waitAndAcceptAlert( int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "waitAndAcceptAlert", [  status:status])
		boolean ret = false
		if (waitForAlert(timeout, status)) {
			ret = acceptAlert(status)
		}
		Log.addTraceEND(CLASS_NAME, "waitAndAcceptAlert",ret)
		return ret
	}
}
