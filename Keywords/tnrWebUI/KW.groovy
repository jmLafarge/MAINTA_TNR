package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
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
class KW {


	private static final String CLASS_FOR_LOG = 'KW'
















	static String getText(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT )  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getText", [myJDD: myJDD, name: name , timeout:timeout ])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		String gText = ''
		if (!msgTO) {
			if (WUI.isElementPresentByObj(tObj,timeout)) {
				gText = getTextByObj(tObj)
			}
		}else {
			TNRResult.addSTEPERROR('-1', "Lecture du texte sur '${tObj.getObjectId()}' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getText",gText)
		return gText
	}


	static String getTextByObj(TestObject tObj )  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getTextByObj", [tObj:tObj ])
		String gText = ''
		try {
			gText = WebUI.getText(tObj,FailureHandling.STOP_ON_FAILURE)
			//Log.addINFO("Lecture du texte sur '${tObj.getObjectId()}' : '$gText'")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR('-1',"Lecture du texte sur '${tObj.getObjectId()}' KO")
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getTextByObj",gText)
		return gText
	}









	
	
} // Fin de class