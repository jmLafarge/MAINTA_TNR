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
 * Gère les recherches par Assistant
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */
@CompileStatic
public class WUI {

	private static final String CLASS_NAME = 'WUI'


	public static void delay(long ms ) {
		String sec = String.format("%.3f", ms/1000)
		TNRResult.addSTEPINFO("Attente de $sec seconde(s)")
		Thread.sleep(ms)
	}


	static void switchToFrame(JDD myJDD, String name) {
		Log.addTraceBEGIN(CLASS_NAME, "switchToFrame", [myJDD:myJDD.toString() , name: name])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			try {
				WebUI.switchToFrame(tObj, (int)GlobalVariable.TIMEOUT,FailureHandling.STOP_ON_FAILURE)
				Log.addINFO("Switch to frame '$name'")
			} catch (Exception ex) {
				TNRResult.addSTEPERROR('',"Switch to frame '$name'")
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR('',"Switch to frame '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_NAME, "switchToFrame")
	}







	static boolean isElementPresent(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_NAME, "isElementPresent", [myJDD:myJDD.toString() , name: name , timeout:timeout])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			ret = isElementPresentByObj(tObj,timeout)
		}else {
			TNRResult.addSTEPERROR('-1',"Vérifier si l'élément '$name' est présent impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_NAME, "isElementPresent",ret)
		return ret
	}





	static boolean isElementPresentByObj(TestObject tObj, int timeout = (int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_NAME, "isElementPresent", [tObj: tObj , timeout:timeout])
		boolean ret = false
		if (WebUI.verifyElementPresent(tObj, timeout, FailureHandling.OPTIONAL)) {
			Log.addINFO("L'élément '${tObj.getObjectId()}' est présent")
			ret = true
		}else {
			Log.addINFO("L'élément '${tObj.getObjectId()}' n'est pas présent")
		}
		Log.addTraceEND(CLASS_NAME, "isElementPresent",ret)
		return ret
	}















	public static String goToElementByObj(TestObject tObj , String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "goToElementByObj", [tObj: tObj, name: name])

		String errMsg = waitElementInViewport(tObj, 0,status)
		//Si msg err
		if (errMsg){
			errMsg = scrollToElement(tObj, timeout,status)
			//si msg err
			if (!errMsg) {
				errMsg = waitElementInViewport(tObj, timeout,status)
			}
		}
		Log.addTraceEND(CLASS_NAME, "goToElementByObj",errMsg)
		return errMsg
	}


	private static String waitElementInViewport(TestObject tObj, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "waitElementInViewport", [tObj: tObj , timeout:timeout , status:status])
		String errMsg = ''

		long startTime = System.currentTimeMillis()
		long elapsedSeconds = 0
		boolean eltInViewport = false
		while ((elapsedSeconds <= timeout) && !eltInViewport) {
			eltInViewport = WebUI.verifyElementInViewport(tObj, 1, FailureHandling.OPTIONAL)
			// Calculer le temps écoulé en secondes
			long currentTime = System.currentTimeMillis()
			elapsedSeconds = ((currentTime - startTime) / 1000) as long
		}
		if (eltInViewport) {
			Log.addINFO("L'élément '${tObj.getObjectId()}' est visible dans le viewport")
		}else {
			errMsg = "L'élément '${tObj.getObjectId()}' n'est pas visible dans le viewport"
		}
		Log.addTraceEND(CLASS_NAME, "waitElementInViewport",errMsg)
		return errMsg
	}


	/*
	 private static String verifyElementInViewport(TestObject tObj, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
	 Log.addTraceBEGIN(CLASS_NAME, "verifyElementInViewport", [tObj: tObj , timeout:timeout , status:status])
	 String errMsg = ''
	 boolean eltInViewport = WebUI.verifyElementInViewport(tObj, 1, FailureHandling.OPTIONAL)
	 if (eltInViewport) {
	 Log.addINFO("L'élément '${tObj.getObjectId()}' est visible dans le viewport")
	 }else {
	 errMsg = "L'élément '${tObj.getObjectId()}' n'est pas visible dans le viewport"
	 }
	 Log.addTraceEND(CLASS_NAME, "verifyElementInViewport",errMsg)
	 return errMsg
	 }
	 */




	private static String scrollToElement(TestObject tObj , int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "scrollToElement", [tObj: tObj , timeout:timeout , status:status])
		String errMsg = ''
		if (WebUI.waitForElementVisible(tObj, timeout, FailureHandling.OPTIONAL)) {
			Log.addINFO("L'élément '${tObj.getObjectId()}' est visible")
			try {
				WebUI.scrollToElement(tObj, timeout, FailureHandling.STOP_ON_FAILURE)
				Log.addINFO("Scroll to '${tObj.getObjectId()}' OK")
			} catch (Exception ex) {
				errMsg = "Impossible de scroller vers '${tObj.getObjectId()}':"+ex.getMessage()
			}
		}else {
			errMsg = "L'élément '${tObj.getObjectId()}' n'est pas visible"
		}
		Log.addTraceEND(CLASS_NAME, "scrollToElement",errMsg)
		return errMsg
	}
} //end of class
