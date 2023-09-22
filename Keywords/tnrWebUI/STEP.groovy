package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import tnrCommon.Tools
import tnrJDDManager.JDD
import tnrLog.Log
import tnrResultManager.TNRResult


public class STEP {
	
	
	private static final String CLASS_FOR_LOG = 'STEP'
	
	private static final int NBCAR_STEPID = 6 
	
	static void navigateToUrl(int stepID, String url,String urlName){
		String strStepID = Tools.formatStrStepID('NTU',stepID,NBCAR_STEPID)
		STEP_NAV.navigateToUrl(strStepID,  url, urlName)
	}
	
	static void openBrowser(int stepID, String url){
		String strStepID = Tools.formatStrStepID('OB',stepID,NBCAR_STEPID)
		STEP_NAV.openBrowser(strStepID, url)
	}
	
	static void closeBrowser(int stepID){
		String strStepID = Tools.formatStrStepID('CB',stepID,NBCAR_STEPID)
		STEP_NAV.closeBrowser(strStepID)
	}
	
	static void maximizeWindow(int stepID){
		String strStepID = Tools.formatStrStepID('MW',stepID,NBCAR_STEPID)
		STEP_NAV.maximizeWindow(strStepID)
	}

	
	static String verifyMaintaVersion(int stepID, String maintaVersion) {
		String strStepID = Tools.formatStrStepID('VMV',stepID,NBCAR_STEPID)
		STEP_SQL.verifyMaintaVersion(strStepID, maintaVersion)
	}
	
	static void delay(Number seconds) {
		try {
			WebUI.delay(seconds,FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPINFO("Attente de $seconds seconde(s)")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR('-1',"Attente de $seconds seconde(s)")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}
	
	
	
	static void scrollToPosition(int x, int y) {
		try {
			WebUI.scrollToPosition(x, y, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPINFO("Scroll à la position $x , $y")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR('-1',"Scroll à la position $x , $y")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}

	
	static void switchToDefaultContent() {
		try {
			WebUI.switchToDefaultContent(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPINFO("switchToDefaultContent")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR('-1',"switchToDefaultContent")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}
	


	
	static void switchToFrame(JDD myJDD, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "switchToFrame", [myJDD: myJDD.toString(), name: name])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			try {
				WebUI.switchToFrame(tObj, (int)GlobalVariable.TIMEOUT)
				Log.addINFO("Switch to frame '$name'")
			} catch (Exception ex) {
				TNRResult.addSTEPERROR('-1',"Switch to frame '$name'")
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR('-1',"Switch to frame '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "switchToFrame")
	}

	

	
	
	


	
	

	
	
	


	

	static void setText(int stepID, JDD myJDD, String name, String text=null , int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setText", [stepID:stepID , myJDD: myJDD.toString(), name: name , text:text , timeout:timeout , status:status])
		String strStepID = Tools.formatStrStepID('ST',stepID,NBCAR_STEPID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			if (tnrJDDManager.JDDKW.isNU(text)) {
				TNRResult.addSTEPINFO("Valeur '\$NU', pas de saisie de texte sur '${tObj.getObjectId()}'")
			}else {
				String objValue = WebUI.getAttribute(tObj, 'value')
				Log.addTrace("objText:'$objValue'")
				if (objValue==text){
					TNRResult.addSTEPPASS(strStepID,"Saisie du texte '$text' sur '${tObj.getObjectId()}'")
					TNRResult.addDETAIL("La valeur est déjà saisie, pas de modification.")
				}else {
					String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
					if (!errMsg) {
						try {
							WebUI.setText(tObj, text, FailureHandling.STOP_ON_FAILURE)
							TNRResult.addSTEPPASS(strStepID,"Saisie du texte '$text' sur '${tObj.getObjectId()}'")
						} catch (Exception ex) {
							TNRResult.addSTEP(strStepID,"Saisie du texte '$text' sur '${tObj.getObjectId()}'", status)
							TNRResult.addDETAIL(ex.getMessage())
							Log.addTrace('')
						}
					}else {
						TNRResult.addSTEP(strStepID,"Saisie du texte sur '${tObj.getObjectId()}'",status)
						TNRResult.addDETAIL(errMsg)
					}
				}
			}
		}else {
			TNRResult.addSTEPERROR(strStepID,"Saisie du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "setText")
	}
	
	
	

	static void setEncryptedText(int stepID, JDD myJDD, String name, String text=null,int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setEncryptedText", [stepID:stepID , myJDD: myJDD.toString(), name: name , text:text , timeout:timeout , status:status])
		String strStepID = Tools.formatStrStepID('SET',stepID,NBCAR_STEPID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				try {
					WebUI.setEncryptedText(tObj, text, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS(strStepID,"Saisie du mot de passe sur '${tObj.getObjectId()}'")
				} catch (Exception ex) {
					TNRResult.addSTEP(strStepID,"Saisie du mot de passe sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP(strStepID,"Saisie du mot de passe sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID,"Saisie du mot de passe sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "setEncryptedText")
	}

	
	
	static boolean click(int stepID,JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "click", [stepID:stepID , myJDD: myJDD.toString(), name: name, timeout:timeout , status:status])
		String strStepID = Tools.formatStrStepID('C',stepID,NBCAR_STEPID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
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
		Log.addTraceEND(CLASS_FOR_LOG, "click",ret)
		return ret
	}
	
	
	
	
	
	static boolean verifyElementPresent(int stepID, JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyElementPresent", [stepID:stepID , myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		String strStepID = Tools.formatStrStepID('VEP',stepID,NBCAR_STEPID)
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
		Log.addTraceEND(CLASS_FOR_LOG, "verifyElementPresent",ret)
		return ret
	}
	
	
	
	static boolean verifyText(int stepID,JDD myJDD, String name, String text=null, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyText", [stepID:stepID , myJDD: myJDD.toString(), name: name , text:text , timeout:timeout , status:status])
		String strStepID = Tools.formatStrStepID('VT',stepID,NBCAR_STEPID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				String gText = KW.getTextByObj(tObj)
				if (WebUI.verifyElementText(tObj, text,FailureHandling.OPTIONAL)) {
					TNRResult.addSTEPPASS(strStepID,"Vérification du texte '$text' sur '${tObj.getObjectId()}'")
					ret = true
				} else {
					TNRResult.addSTEP(strStepID,"Vérification du texte '$text' sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL("la valeur est '$gText' !")
				}
			}else {
				TNRResult.addSTEP(strStepID,"Vérification du texte '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID,"Vérification du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyText",ret)
		return ret
	}
	
	
	
	static boolean verifyTextContains(int stepID,JDD myJDD, String name, String text=null, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyTextContains", [stepID:stepID , myJDD: myJDD.toString(), name: name , text:text , timeout:timeout , status:status])
		String strStepID = Tools.formatStrStepID('STC',stepID,NBCAR_STEPID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			String gText = WebUI.getText(tObj)
			String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				if (gText.contains(text)) {
					TNRResult.addSTEPPASS(strStepID,"Vérification que le texte '$gText' contient '$text' sur '${tObj.getObjectId()}'")
					ret = true
				} else {
					TNRResult.addSTEP(strStepID,"Vérification que le texte '$gText' contient '$text' sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL("la valeur est '$gText' !")
				}
			}else {
				TNRResult.addSTEP(strStepID,"Vérification que le texte '$gText' contient '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID,"Vérification que le texte '?' contient '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyTextContains",ret)
		return ret
	}

	
	
	
	static boolean verifyElementVisible(int stepID, JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyElementVisible", [stepID:stepID , myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		String strStepID = Tools.formatStrStepID('VEV',stepID,NBCAR_STEPID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
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
		Log.addTraceEND(CLASS_FOR_LOG, "verifyElementVisible",ret)
		return ret
	}

}
