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
 * 
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */

@CompileStatic
public class KWElement {

	private static final String CLASS_FOR_LOG = 'KWElement'

	private static final String MSG_OK = ''

	
	
	public static String goToElementByObj(TestObject tObj , String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "goToElementByObj", [tObj: tObj, name: name])

		String errMsg = MSG_OK
		boolean ret = false

		if (verifyElementInViewport(tObj, name, 1,status)!=MSG_OK) {
			if (scrollToElement(tObj, name, timeout,status)!=MSG_OK) {
				if (verifyElementInViewport(tObj, name, timeout,status)==MSG_OK) {
					ret = true
				}else {
					errMsg = "L'élément '${tObj.getObjectId()}' n'est pas visible dans le viewport"
				}
			}
		}

		Log.addTraceEND(CLASS_FOR_LOG, "goToElementByObj",errMsg)
		return errMsg
	}



	public static String verifyElementInViewport(TestObject tObj, String name, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyElementInViewport", [tObj: tObj, name: name , timeout:timeout , status:status])
		String errMsg = MSG_OK
		boolean ret =false
		ret = WebUI.verifyElementInViewport(tObj, 1, FailureHandling.OPTIONAL)
		if (ret) {
			Log.addINFO("L'élément '${tObj.getObjectId()}' est visible dans le viewport")
		}else {
			errMsg = "L'élément '${tObj.getObjectId()}' n'est pas visible dans le viewport"
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyElementInViewport",errMsg)
		return errMsg
	}





	private static String scrollToElement(TestObject tObj, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "scrollToElement", [tObj: tObj , name: name , timeout:timeout , status:status])
		String errMsg = MSG_OK
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
		Log.addTraceEND(CLASS_FOR_LOG, "scrollToElement",errMsg)
		return errMsg
	}
} // end of class
