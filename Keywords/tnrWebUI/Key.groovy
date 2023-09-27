package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import tnrJDDManager.JDD
import tnrLog.Log
import tnrResultManager.TNRResult

/**
 * Gère les touches
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */
@CompileStatic
public class Key {


	private static final String CLASS_FOR_LOG = 'Key'

	private static final String CLASS_CODE = 'KEY'



	static String sendKeys(def stepID, JDD myJDD, String name, String keys, String msg = '' , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "sendKeys", [stepID:stepID , myJDD: myJDD, name: name , keys:keys, msg:msg , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '01',stepID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		String ret = null
		if (!msgTO) {
			try {
				WebUI.sendKeys(tObj, keys, FailureHandling.STOP_ON_FAILURE)
				if (msg) {
					TNRResult.addSTEPPASS(strStepID, msg)
				}else {
					Log.addTrace("Envoie touche(s) clavier '$keys' sur '${tObj.getObjectId()}'")
				}
			} catch (Exception ex) {
				if (msg) {
					TNRResult.addSTEP(strStepID, msg,status)
					TNRResult.addDETAIL(ex.getMessage())
				}
				ret = ex.getMessage()
			}
		}else {
			TNRResult.addSTEPERROR(strStepID, "Envoie touche(s) clavier '$keys' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "sendKeys",ret)
		return ret
	}
}
