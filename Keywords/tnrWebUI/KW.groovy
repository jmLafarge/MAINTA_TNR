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


	static void delay(Number second) {
		TNRResult.addSTEP("Attente de $second seconde(s)")
		WebUI.delay(second)
	}



	static void waitForPageLoad(int seconds=GlobalVariable.TIMEOUTForPageLoad) {
		TNRResult.addSTEP("Attendre chargement de la page ...MAX $seconds seconde(s)")
		WebUI.waitForPageLoad(seconds)
	}



	static void scrollToPositionAndWait(int x, int y, Number second = 0) {
		Log.addINFO("Scroll à la position $x , $y et attendre $second seconde(s)")
		WebUI.scrollToPosition(x, y)
		delay(second)
	}


	static void openBrowser(String url){
		try {
			WebUI.openBrowser(url, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS("Ouverture du navigateur à l'URL :")
			TNRResult.addDETAIL(url)
			TNRResult.addBrowserInfo()
			waitForPageLoad()
			WebWindow.storeMainWindowHandle()
		} catch (Exception ex) {
			TNRResult.addSTEPERROR("Ouverture du navigateur à l'URL :")
			TNRResult.addDETAIL(url)
			TNRResult.addDETAIL(ex.getMessage())
		}
	}





	static void navigateToUrl(String url,String nomUrl){
		try {
			WebUI.navigateToUrl(url, FailureHandling.STOP_ON_FAILURE)
			waitForPageLoad()
			TNRResult.addSTEPPASS("Navigation vers l'URL '$nomUrl' :")
			TNRResult.addDETAIL(url)
		} catch (Exception ex) {
			TNRResult.addSTEPERROR("Navigation vers l'URL '$nomUrl' :")
			TNRResult.addDETAIL(url)
			TNRResult.addDETAIL(ex.getMessage())
		}
	}




	static void closeBrowser(){
		try {
			WebUI.closeBrowser()
			TNRResult.addSTEPPASS("Fermeture du navigateur")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR("Fermeture du navigateur")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}




	static void maximizeWindow(){
		try {
			WebUI.maximizeWindow(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS("Maximise la fenêtre")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR("Maximise la fenêtre")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}




	static void switchToFrame(JDD myJDD, String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "switchToFrame", [myJDD: myJDD.toString(), name: name])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			try {
				WebUI.switchToFrame(tObj, (int)GlobalVariable.TIMEOUT)
				TNRResult.addSTEP("Switch to frame '$name'",null)
			} catch (Exception ex) {
				TNRResult.addSTEPERROR("Switch to frame '$name'")
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR("Switch to frame '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "switchToFrame")
	}




	static void setText(JDD myJDD, String name, String text=null, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setText", [myJDD: myJDD.toString(), name: name , text:text , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			//String objValt = WebUI.getText(tObj)
			String objValue = WebUI.getAttribute(tObj, 'value')
			Log.addTrace("objText:'$objValue'")
			if (objValue==text){
				TNRResult.addSTEPPASS("Saisie du texte '$text' sur '${tObj.getObjectId()}'")
				TNRResult.addDETAIL("La valeur est déjà saisie, pas de modification.")
			}else {
				try {
					WebUI.setText(tObj, text, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS("Saisie du texte '$text' sur '${tObj.getObjectId()}'")
				} catch (Exception ex) {
					TNRResult.addSTEP("Saisie du texte '$text' sur '${tObj.getObjectId()}'", status)
					TNRResult.addDETAIL(ex.getMessage())
					Log.addTrace('')
				}
			}
		}else {
			TNRResult.addSTEPERROR("Saisie du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "setText")
	}




	static void setEncryptedText(JDD myJDD, String name, String text=null) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setEncryptedText", [myJDD: myJDD.toString(), name: name , text:text])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			try {
				WebUI.setEncryptedText(tObj, text, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Saisie du mot de passe sur '${tObj.getObjectId()}'")
			} catch (Exception ex) {
				TNRResult.addSTEPFAIL("Saisie du mot de passe sur '${tObj.getObjectId()}'")
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR("Saisie du mot de passe sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "setEncryptedText")
	}




	static boolean click(JDD myJDD, String name, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "click", [myJDD: myJDD.toString(), name: name, status:status])
		boolean ret = false
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			try {
				WebUI.click(tObj, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Clic sur '${tObj.getObjectId()}'")
				ret= true
			} catch (Exception ex) {
				TNRResult.addSTEP("Clic sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR("Clic sur '$name' imposible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "click",ret)
		return ret
	}


	static void doubleClick(JDD myJDD, String name, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "doubleClick", [myJDD: myJDD.toString(), name: name , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			try {
				WebUI.doubleClick(tObj, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Double click sur '${tObj.getObjectId()}'")
			} catch (Exception ex) {
				TNRResult.addSTEP("Double click sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR("Double click sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "doubleClick")
	}






	static boolean waitForAlert(int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "waitForAlert", [ timeout:timeout , status:status])
		boolean ret = false
		try {
			WebUI.waitForAlert(timeout, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS("Attendre la fenetre de confirmation")
			ret = true
		} catch (Exception ex) {
			TNRResult.addSTEP("Attendre la fenetre de confirmation",status)
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "waitForAlert",ret)
		return ret
	}




	static boolean acceptAlert(String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "acceptAlert", [ status:status])
		boolean ret = false
		try {
			WebUI.acceptAlert(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS("Accepter la demande de confirmation")
			ret = true
		} catch (Exception ex) {
			TNRResult.addSTEP("Accepter la demande de confirmation", status)
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "acceptAlert",ret)
		return ret
	}


	static boolean waitAndAcceptAlert(int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "waitAndAcceptAlert", [ status:status])
		boolean ret = false
		if (waitForAlert(timeout, status)) {
			ret = acceptAlert(status)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "waitAndAcceptAlert",ret)
		return ret
	}







	static String sendKeys(JDD myJDD, String name, String keys, String msg = '' , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "sendKeys", [myJDD: myJDD.toString(), name: name , keys:keys, msg:msg , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		String ret = null
		if (!msgTO) {
			try {
				WebUI.sendKeys(tObj, keys, FailureHandling.STOP_ON_FAILURE)
				if (msg) {
					TNRResult.addSTEPPASS(msg)
				}else {
					Log.addTrace("Envoie touche(s) clavier '$keys' sur '${tObj.getObjectId()}'")
				}
			} catch (Exception ex) {
				if (msg) {
					TNRResult.addSTEP(msg,status)
					TNRResult.addDETAIL(ex.getMessage())
				}
				ret = ex.getMessage()
			}
		}else {
			TNRResult.addSTEPERROR("Envoie touche(s) clavier '$keys' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "sendKeys",ret)
		return ret
	}








	static boolean verifyElementNotPresent(JDD myJDD, String name, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyElementNotPresent", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			try {
				WebUI.verifyElementNotPresent(tObj, timeout, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' ne soit plus présent")
				ret = true
			} catch (Exception ex) {
				TNRResult.addSTEP("Vérifier que '${tObj.getObjectId()}' ne soit plus présent", status)
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que '$name' ne soit plus présent impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyElementNotPresent",ret)
		return ret
	}





	static boolean verifyElementPresent(JDD myJDD, String name, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyElementPresent", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			try {
				WebUI.verifyElementPresent(tObj, timeout, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' soit présent")
				ret = true
			}catch (Exception ex) {
				TNRResult.addSTEP("Vérifier que '${tObj.getObjectId()}' soit présent", status)
				TNRResult.addDETAIL(ex.getMessage())
				TNRResult.addDETAIL("XPATH = "+tObj.getSelectorCollection().get(SelectorMethod.XPATH))
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que '$name' soit présent impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyElementPresent",ret)
		return ret
	}









	static boolean verifyElementText(JDD myJDD, String name, String text=null, String status = 'FAIL')  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyElementText", [myJDD: myJDD.toString(), name: name , text:text , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			String gText = WebUI.getText(tObj)

			if (WebUI.verifyElementText(tObj, text,FailureHandling.OPTIONAL)) {
				TNRResult.addSTEPPASS("Vérification du texte '$text' sur '${tObj.getObjectId()}'")
				ret = true
			} else {
				TNRResult.addSTEP("Vérification du texte '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL("la valeur est '$gText' !")
			}
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyElementText",ret)
		return ret
	}






	static boolean verifyElementTextContains(JDD myJDD, String name, String text=null, String status = 'FAIL')  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyElementTextContains", [myJDD: myJDD.toString(), name: name , text:text , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			String gText = WebUI.getText(tObj)
			if (gText.contains(text)) {
				TNRResult.addSTEPPASS("Vérification du texte '$text' sur '${tObj.getObjectId()}'")
				ret = true
			} else {
				TNRResult.addSTEP("Vérification du texte '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL("la valeur est '$gText' !")
			}
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyElementTextContains",ret)
		return ret
	}






	static void verifyValue(JDD myJDD, String name, String text=null, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyValue", [myJDD: myJDD.toString(), name: name , text:text , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		int timeout  = (int) GlobalVariable.TIMEOUT
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			if (JDDKW.isNU(text)) {
				TNRResult.addSTEP("Pas de vérification pour $name, valeur du JDD = NU")
			}else if (goToElement(myJDD, name, timeout,status)) {
				String val = WebUI.getAttribute(tObj, 'value')
				Log.addTrace("value=$val")
				if (val==null) {
					TNRResult.addSTEPERROR("Vérifier que la valeur de '" + name + "', soit '$text'")
					TNRResult.addDETAIL("L'attribut 'value' n'existe pas !")
				}else if (val==text) {
					TNRResult.addSTEPPASS("Vérifier que la valeur de '" + name + "', soit '$text'")
				}else if (text==tnrJDDManager.JDDKW.getKW_NULL() && val=='') {
					TNRResult.addSTEPPASS("Vérifier que la valeur de '" + name + "', soit Null ou Vide")
				}else {
					TNRResult.addSTEP("Vérifier que la valeur de '" + name + "', soit '$text'",status)
					TNRResult.addDETAIL("La valeur du champ est '" + WebUI.getAttribute(tObj, 'value') + "' !")
				}
			}else {
				TNRResult.addSTEP("Vérifier que la valeur de '" + name + "', soit '$text'",status)
				TNRResult.addDETAIL("L'élément '$name' n'est pas visble après $timeout seconde(s)")
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que la valeur de '$name' = '$text'  impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyValue")
	}


	
	static void verifyRadioChecked(JDD myJDD, String name,  int timeout = GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyRadioChecked", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (JDDKW.isNU(myJDD.getStrData(name))) {
				TNRResult.addSTEP("Pas de vérification pour $name, valeur du JDD = NU")
			}else if (goToElement(myJDD, name, timeout,status)) {
				String val = WebUI.getAttribute(tObj, 'checked')
				Log.addTrace("value=$val")
				if (val==null) {
					TNRResult.addSTEP("Vérifier que le bouton radio '$name', soit sélectionné",status)
					TNRResult.addDETAIL("L'attribut 'checked' n'existe pas !")
				}else if (val=='true') {
					TNRResult.addSTEPPASS("Vérifier que le bouton radio '$name', soit sélectionné")
				}else {
					TNRResult.addSTEP("Vérifier que le bouton radio '$name', soit sélectionné",status)
					TNRResult.addDETAIL("La valeur de l'attribut checked est '$val' !")
				}
			}else {
				TNRResult.addSTEP("Vérifier que le bouton radio '$name', soit sélectionné",status)
				TNRResult.addDETAIL("L'élément '$name' n'est pas visble après $timeout seconde(s)")
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que le bouton radio '$name', soit sélectionné impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyRadioChecked")
	}




	private static void verifyOptionSelectedByLabel(JDD myJDD, String name, String text=null, boolean isRegex = false, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyOptionSelectedByLabel", [myJDD: myJDD.toString(), name: name , text:text , isRegex:isRegex , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {

			if (goToElement(myJDD, name, timeout,status)) {
				if (text==null) text = myJDD.getStrData(name)

				String paraIV = myJDD.myJDDParam.getINTERNALVALUEFor(name)
				if (paraIV) {
					String valIV = myJDD.myJDDIV.getValueOf(paraIV, text)
					if (valIV) {
						try {
							WebUI.verifyOptionSelectedByLabel(tObj, valIV, isRegex,timeout, FailureHandling.STOP_ON_FAILURE)
							TNRResult.addSTEPPASS("Vérifier que l'option '$valIV'($text) de '${tObj.getObjectId()}' soit sélectionnée")
						} catch (Exception ex) {
							TNRResult.addSTEP("Vérifier que l'option '$valIV'($text) de '${tObj.getObjectId()}' soit sélectionnée",status)
							TNRResult.addDETAIL(ex.getMessage())
						}
					}else {
						TNRResult.addSTEP("Vérifier que l'option '?'($text) de '${tObj.getObjectId()}' soit sélectionnée",status)
						TNRResult.addDETAIL("Pas de valeur trouvée pour l'INTERNALVALUE")
					}
				}else {
					try {
						WebUI.verifyOptionSelectedByLabel(tObj, text, isRegex,timeout, FailureHandling.STOP_ON_FAILURE)
						TNRResult.addSTEPPASS("Vérifier que l'option '$text' de '${tObj.getObjectId()}' soit sélectionnée")
					} catch (Exception ex) {
						TNRResult.addSTEP("Vérifier que l'option '$text' de '${tObj.getObjectId()}' soit sélectionnée",status)
						TNRResult.addDETAIL(ex.getMessage())
					}
				}
			}else {
				TNRResult.addSTEP("Vérifier l'option '$text' de '$name'",status)
				TNRResult.addDETAIL("L'élément '$name' n'est pas visble après $timeout seconde(s)")
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier l'option '$text' de '$name'")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyOptionSelectedByLabel")
	}







	static boolean verifyElementInViewport(JDD myJDD, String name, int timeoutInMilliseconds , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyElementInViewport", [myJDD: myJDD.toString(), name: name , timeoutInMilliseconds:timeoutInMilliseconds , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			int waitedTime = 0
			while (waitedTime <= timeoutInMilliseconds) {
				ret = WebUI.verifyElementInViewport(tObj, (int) GlobalVariable.TIMEOUT, FailureHandling.OPTIONAL)
				if(ret) {
					break
				}
				Thread.sleep(50)
				waitedTime += 50
			}
			if (ret) {
				Log.addINFO("L'élément '${tObj.getObjectId()}' est visible dans le viewport")
			}else {
				Log.addINFO("L'élément '${tObj.getObjectId()}' n'est pas visible dans le viewport")
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyElementInViewport",ret)
		return ret
	}






	static boolean isElementPresent(JDD myJDD, String name, int timeout = GlobalVariable.TIMEOUT,String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "isElementPresent", [myJDD: myJDD.toString(), name: name , timeout:timeout])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			try {
				if (WebUI.verifyElementPresent(tObj, timeout, FailureHandling.OPTIONAL)) {
					Log.addINFO("L'élément '${tObj.getObjectId()}' est présent")
					ret = true
				}else {
					TNRResult.addSTEP("L'élément '${tObj.getObjectId()}' n'est pas présent",status)
				}
			} catch (Exception ex) {
				TNRResult.addSTEPERROR("Vérifier si l'élément '$name' est présent")
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier si l'élément '$name' est présent impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "isElementPresent",ret)
		return ret
	}





	static boolean scrollToElement(JDD myJDD, String name, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "scrollToElement", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			if (isElementPresent(myJDD, name, timeout)) {
				try {
					WebUI.scrollToElement(tObj, timeout, FailureHandling.STOP_ON_FAILURE)
					//Log.addINFO("Scroll to '${tObj.getObjectId()}' OK")
					ret = true
				} catch (Exception ex) {
					TNRResult.addSTEPERROR("Scroll to '${tObj.getObjectId()}'")
					TNRResult.addDETAIL(ex.getMessage())
				}
			}
		}else {
			TNRResult.addSTEPERROR("Scroll to '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "scrollToElement",ret)
		return ret
	}


	

	

	static boolean goToElement(JDD myJDD, String name, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "goToElement", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			if (verifyElementInViewport(myJDD, name, 0,status)) {
				ret = true
			}else if (scrollToElement(myJDD, name, timeout,status)) {
				if (verifyElementInViewport(myJDD, name, 500,status)) {
					ret = true
				}else {
					TNRResult.addSTEP("L'élément '${tObj.getObjectId()}' n'est pas visible dans le viewport",status)
				}
			}
		}else {
			TNRResult.addSTEPERROR("Go to '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "goToElement",ret)
		return ret
	}
































	/** attend que l'élément Web soit présent dans le DOM et visible à l'utilisateur
	 * 
	 * @param myJDD
	 * @param name
	 * @param timeout
	 * @param status
	 * @return
	 */
	static boolean waitForElementVisible(JDD myJDD, String name, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "waitForElementVisible", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			try {
				if (WebUI.waitForElementVisible(tObj, timeout, FailureHandling.OPTIONAL)) {
					//TNRResult.addSTEPPASS("Vérifier que l'élément '${tObj.getObjectId()}' soit visible")
					ret = verifyElementInViewport(myJDD, name, 100,status)
					if (WebUI.getAttribute(tObj, 'disabled', FailureHandling.OPTIONAL)) {
						//TNRResult.addDETAIL("Elément 'disabled'")
						Log.addINFO("Elément 'disabled'")
						ret = false
					}else if (WebUI.getAttribute(tObj, 'readonly', FailureHandling.OPTIONAL)) {
						//TNRResult.addDETAIL("Elément 'readonly'")
						Log.addINFO("Elément 'readonly'")
						ret = false
					}
				}else {
					TNRResult.addSTEP("Attendre que l'élément '${tObj.getObjectId()}' soit visible",status)
					TNRResult.addDETAIL("KO après $timeout seconde(s)")
				}
			} catch (Exception ex) {
				TNRResult.addSTEP("Attendre que l'élément '${tObj.getObjectId()}' soit visible", status)
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que l'élément '$name' soit visible impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "waitForElementVisible",ret)
		return ret
	}














	static boolean scrollAndClick(JDD myJDD, String name, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "scrollAndClick", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		boolean ret = false
		if (goToElement(myJDD, name, timeout,status)) {
			//if (waitForElementVisible(myJDD, name, timeout,status)) {
			ret = click(myJDD, name,status)
			/*
			 }else {
			 TNRResult.addDETAIL("Clic sur '$name' impossible")
			 }
			 */
		}
		Log.addTraceEND(CLASS_FOR_LOG, "scrollAndClick",ret)
		return ret
	}






	static void scrollAndDoubleClick(JDD myJDD, String name, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (goToElement(myJDD, name, timeout,status)) {
			//if (waitForElementVisible(myJDD, name, timeout,status)) {
			doubleClick(myJDD, name,status)
			/*
			 }else {
			 TNRResult.addDETAIL("Double click sur '$name' imposible")
			 }
			 */
		}
	}






	static void scrollAndSelectOptionByLabel(JDD myJDD, String name, String text=null, boolean isRegex = true, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			if (goToElement(myJDD, name, timeout,status)) {
				//if (waitForElementVisible(myJDD, name, timeout,status)) {
				String paraIV = myJDD.myJDDParam.getINTERNALVALUEFor(name)
				if (paraIV) {
					String valIV = myJDD.myJDDIV.getValueOf(paraIV, text)
					if (valIV) {
						try {
							WebUI.selectOptionByLabel(tObj, valIV, isRegex,FailureHandling.STOP_ON_FAILURE)
							TNRResult.addSTEPPASS("Scroll et select option '$valIV'($text) sur '${tObj.getObjectId()}'")
						} catch (Exception ex) {
							TNRResult.addSTEP("Scroll et select option '$valIV'($text) sur '${tObj.getObjectId()}'",status)
							TNRResult.addDETAIL(ex.getMessage())
						}
					}else {
						TNRResult.addSTEP("Scroll et select option '?'($text) sur '${tObj.getObjectId()}'",status)
						TNRResult.addDETAIL("Pas de valeur trouvée pour l'INTERNALVALUE")
					}
				}else {
					try {
						WebUI.selectOptionByLabel(tObj, text, isRegex,FailureHandling.STOP_ON_FAILURE)
						TNRResult.addSTEPPASS("Scroll et select option '$text' sur '${tObj.getObjectId()}'")
					} catch (Exception ex) {
						TNRResult.addSTEP("Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
						TNRResult.addDETAIL(ex.getMessage())
					}
				}
				//}
			}
		}else {
			TNRResult.addSTEPERROR("Scroll et select option '$text' sur '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}





	// pas utilisé, on utilise scrollAndSelectOptionByLabel
	private static void scrollAndSelectOptionByValue(JDD myJDD, String name, String text=null, boolean isRegex = true, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			if (goToElement(myJDD, name, timeout,status)) {
				//if (waitForElementVisible(myJDD, name, timeout,status)) {
				try {
					WebUI.selectOptionByValue(tObj, text, isRegex,FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS("Scroll et select option '$text' sur '${tObj.getObjectId()}'")
				} catch (Exception ex) {
					TNRResult.addSTEP("Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(ex.getMessage())
				}
				//}
			}
		}else {
			TNRResult.addSTEPERROR("Scroll et select option '$text' sur '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}





	static void scrollAndSetRadio(JDD myJDD, String name, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "scrollAndSetRadio", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		if (goToElement(myJDD, name, timeout, status)) {
			//Création du TO label en tenant compte de la valeur --> voir resolveXpath
			TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
			String nameWithoutLbl = name.substring(3)
			if (!msgTO) {
				try {
					WebUI.click(tObj, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS("Clic sur bouton radio '$nameWithoutLbl' (" + myJDD.getStrData(nameWithoutLbl) + ')')
				} catch (Exception ex) {
					TNRResult.addSTEP("Clic sur bouton radio '$nameWithoutLbl' (" + myJDD.getStrData(nameWithoutLbl) + ')',status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEPERROR("Clic sur '$nameWithoutLbl' imposible")
				TNRResult.addDETAIL(msgTO)
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG, "scrollAndSetRadio")
	}
	
	
	



	static void scrollAndSetText(JDD myJDD, String name, String text=null, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (text==null) text = myJDD.getStrData(name)
		if (tnrJDDManager.JDDKW.isNULL(text) || tnrJDDManager.JDDKW.isNU(text)) {
			Log.addINFO('Pas de traitement')
		}else {
			if (goToElement(myJDD, name, timeout, status)) {
				//if (waitForElementVisible(myJDD, name, timeout, status)) {
				setText(myJDD, name, text, status)
				//}
			}
		}
	}



	static void scrollAndSetDate(JDD myJDD, String name, def val=null,  String dateFormat = 'dd/MM/yyyy', int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (val==null) val = myJDD.getData(name)
		if (tnrJDDManager.JDDKW.isNULL(val) || tnrJDDManager.JDDKW.isNU(val)) {
			Log.addINFO('Pas de traitement')
		}else {
			if (goToElement(myJDD, name, timeout, status)) {
				//if (waitForElementVisible(myJDD, name, timeout, status)) {
				setDate(myJDD, name, val, dateFormat, timeout,status)
				//}
			}
		}
	}








	static void setDate(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (val==null) val = myJDD.getData(name)
		if ( val instanceof Date) {
			setText(myJDD, name, val.format(dateFormat), status)
		}else {
			TNRResult.addSTEPERROR("Saisie du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
	}





	static void verifyDateText(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTrace("verifyDateText : name='$name' val='${val.toString()} dateFormat='dateFormat' status='$status'")

		if (val==null) val = myJDD.getData(name)

		if (val == '$VIDE') {
			verifyElementText(myJDD, name, '', status)
		}else if ( val instanceof Date) {
			Log.addTrace('val.format(dateFormat) :' +val.format(dateFormat))
			verifyElementText(myJDD, name, val.format(dateFormat), status)
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
	}




	static void verifyDateValue(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyDateValue", [myJDD: myJDD.toString(), name: name , val:val , dateFormat:dateFormat , timeout:timeout , status:status])

		if (val==null) val = myJDD.getData(name)
		if (val == '$VIDE') {
			verifyValue(myJDD, name, '', status)
		}else if ( val instanceof Date) {
			verifyValue(myJDD, name, val.format(dateFormat), status)
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyDateValue")
	}
	
	
	
	
	static void verifyTimeValue(JDD myJDD, String name, def val=null, String timeFormat = 'HH:mm:ss', int timeout = GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyTimeValue", [myJDD: myJDD.toString(), name: name , val:val , timeFormat:timeFormat , timeout:timeout , status:status])

		if (val==null) val = myJDD.getData(name)
		if (val == '$VIDE') {
			verifyValue(myJDD, name, '', status)
		}else if ( val instanceof Date) {
			verifyValue(myJDD, name, val.format(timeFormat), status)
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyTimeValue")
	}
		
			
			


	static void scrollWaitAndVerifyElementText(JDD myJDD, String name, String text=null, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {

		if (text==null) text = myJDD.getStrData(name)
		if (goToElement(myJDD, name, timeout, status)) {
			//waitAndVerifyElementText(myJDD, name, text,timeout, status)
			verifyElementText(myJDD, name, text, status)
		}
	}




	static boolean waitAndVerifyElementText(JDD myJDD, String name, String text=null, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL')  {

		if (text==null) text = myJDD.getStrData(name)
		if (waitForElementVisible(myJDD, name, timeout, status)) {
			return verifyElementText(myJDD, name, text, status)
		}else {
			return false
		}
	}






	static void scrollAndCheckIfNeeded(JDD myJDD, String name, String textTrue, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL')  {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			TO myTOLbl = new TO() ; TestObject tObjLbl  = myTOLbl.make(myJDD,'Lbl'+name) ;String msgLbl = myTOLbl.getMsg()
			if (tObjLbl) {
				boolean cond = myJDD.getStrData(name)==textTrue

				if (goToElement(myJDD, name,timeout,status)) {
					if (cond) {
						if (WebUI.verifyElementChecked(tObj,timeout, FailureHandling.OPTIONAL)) {
							TNRResult.addSTEPPASS("Cocher la case à cocher '" + name + "'")
							TNRResult.addDETAIL("déjà cochée")
						}else {
							try {
								//if (waitForElementVisible(myJDD, name, timeout,status)) {
									WebUI.click(tObjLbl, FailureHandling.STOP_ON_FAILURE)
									TNRResult.addSTEPPASS("Cocher la case à cocher '" + name + "'")
									/*
								}else {
									TNRResult.addSTEP("Cocher la case à cocher '" + name + "'", status)
								}
								*/
							} catch (Exception ex) {
								TNRResult.addSTEP("Cocher la case à cocher '" + name + "'", status)
								TNRResult.addDETAIL(ex.getMessage())
							}
						}
					}else {
						if (WebUI.verifyElementNotChecked(tObj,timeout, FailureHandling.OPTIONAL)) {
							TNRResult.addSTEPPASS("Décocher la case à cocher '" + name + "'")
							TNRResult.addDETAIL("déjà décochée")
						}else {
							try {
								//if (waitForElementVisible(myJDD, name, timeout,status)) {
									WebUI.click(tObjLbl, FailureHandling.STOP_ON_FAILURE)
									TNRResult.addSTEPPASS("Décocher la case à cocher '" + name + "'")
									/*
								}else {
									TNRResult.addSTEP("Décocher la case à cocher '" + name + "'", status)
								}
								*/
							} catch (Exception ex) {
								TNRResult.addSTEP("Décocher la case à cocher '" + name + "'", status)
								TNRResult.addDETAIL(ex.getMessage())
							}
						}
					}
				}else {
					TNRResult.addSTEP("Cocher/Décocher la case à cocher '" + name + "'", status)
				}
			}else {
				TNRResult.addSTEPERROR("Label Case à cocher 'Lbl$name'")
				TNRResult.addDETAIL(msgTO)
			}
		}else {
			TNRResult.addSTEPERROR("Case à cocher '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}






	static void verifyElementCheckedOrNot(JDD myJDD, String name, String textTrue, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			boolean cond = myJDD.getStrData(name)==textTrue
			if (goToElement(myJDD, name, timeout,status)) {
				if (cond) {
					if (WebUI.verifyElementChecked(tObj,timeout, FailureHandling.OPTIONAL)) {
						TNRResult.addSTEPPASS("Vérifier que '${tObj.getObjectId()}'soit coché")
					}else {
						TNRResult.addSTEP("Vérifier que '${tObj.getObjectId()}'soit coché",status)
						TNRResult.addDETAIL('La case est décochée !')
					}
				}else {
					if (WebUI.verifyElementNotChecked(tObj,timeout, FailureHandling.OPTIONAL)) {
						TNRResult.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' soit décoché")
					}else {
						TNRResult.addSTEP("Vérifier que '${tObj.getObjectId()}' soit décoché",status)
						TNRResult.addDETAIL('La case est cochée !')
					}
				}

			}else {
				TNRResult.addSTEP("Vérifier la case à cocher '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL("L'élément '$name' n'est pas visble après $timeout seconde(s)")
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier état case à cocher '$name'")
			TNRResult.addDETAIL(msgTO)
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
				TNRResult.addSTEPERROR("Vérifier état case à cocher '$name'")
				TNRResult.addDETAIL("L'attribut src de l'objet " + name + " n'est pas conforme, la valeur est : " + WebUI.getAttribute(tObj, 'src'))
				return null
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier état case à cocher '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}





	static void verifyCheckBoxImgChecked(JDD myJDD, String name, String status = 'FAIL')  {
		def etat = getCheckBoxImgStatus(myJDD, name)
		if (etat ==null) {
			// l'erreur est déjà remontée par getCheckBoxImgStatus
		}else if (etat) {
			TNRResult.addSTEPPASS("Vérifier que la case à cocher (img) '" + name + "' soit cochée")
		}else {
			TNRResult.addSTEP("Vérifier que la case à cocher (img) '" + name + "' soit cochée", status)
		}
	}





	static void verifyCheckBoxImgNotChecked(JDD myJDD, String name, String status = 'FAIL')  {
		def etat = getCheckBoxImgStatus(myJDD, name)
		if (etat ==null) {
			// l'erreur est déjà remontée par getCheckBoxImgStatus
		}else if (!etat) {
			TNRResult.addSTEPPASS("Vérifier que la case à cocher (img) '" + name + "' soit cochée")
		}else {
			TNRResult.addSTEP("Vérifier que la case à cocher (img) '" + name + "' soit cochée", status)
		}
	}





	static void verifyImgCheckedOrNot(JDD myJDD, String name, String textTrue, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		boolean cond = myJDD.getStrData(name)==textTrue
		if (cond) {
			verifyCheckBoxImgChecked(myJDD, name, status)
		}else {
			verifyCheckBoxImgNotChecked(myJDD, name, status)
		}
	}





	static void verifyImg(JDD myJDD, String name, boolean cond, int timeout = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (cond) {
			verifyElementPresent(myJDD, name, timeout, status)
		}
	}



	private static String deleteText(TestObject tObj) {

		String msg =null
		try {
			WebUI.setText(tObj, '', FailureHandling.STOP_ON_FAILURE)
			Log.addTrace("Effacement du texte sur l'object '${tObj.getObjectId()}'")
		} catch (Exception ex) {
			msg = "Effacement du texte : " + ex.getMessage()
		}
		return msg
	}


	private static runSearchWithHelper(JDD myJDD, String name , String btnXpath = '' , String inputSearchName = '', int index_td=3 ){

		String val = myJDD.getStrData(name)
		if (btnXpath=='') {
			btnXpath = "//a[@id='Btn$name']/i"
		}
		if (inputSearchName=='') {
			inputSearchName = "SEARCH_$name"
		}
		String inputXpath 	= "//input[@name='$inputSearchName']"
		String tdXpath 		= "//div[@id='v-dbtdhtmlx1']/table/tbody/tr[2]/td[$index_td][text()='$val']"
		myJDD.myJDDXpath.add(['btnSearch':btnXpath , 'inputSearch':inputXpath , 'tdSearch':tdXpath])

		WebWindow.init()

		scrollAndClick(myJDD,'btnSearch')

		if (WebWindow.waitForNewWindowToOpenAndSwitch()) {
			if (waitForElementVisible(myJDD, 'inputSearch')) {
				setText(myJDD,'inputSearch', myJDD.getStrData(name))
				'mise à jour dynamique du xpath'
				scrollWaitAndVerifyElementText(myJDD,'tdSearch', myJDD.getStrData(name))
				click(myJDD,'tdSearch')
			}
			delay(1)
			WebWindow.closeWindowIfOpen()
		}else {
			TNRResult.addSTEP("Saisie de $name en utilisant l'assistant de recherche",'FAIL')
			TNRResult.addDETAIL("La fenetre de recherche ne s'est pas ouverte")
		}
		
		WebWindow.switchToMainWindow()

	}




	static void searchWithHelper(JDD myJDD, String name , String btnXpath = '' , String inputSearchName = '', int index_td=3 ){

		String val = myJDD.getStrData(name)

		if (JDDKW.isNULL(val) || JDDKW.isNU(val)) {
			TNRResult.addSTEP("Pas de recherche pour $name, valeur du JDD = $val")
		}else {
			TNRResult.addSUBSTEP("Saisie de $name en utilisant l'assistant de recherche")
			if (goToElement(myJDD, name)) {
				//if (waitForElementVisible(myJDD, name)) {
				TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
				String value = WebUI.getAttribute(tObj, 'value')
				if (value==val){
					TNRResult.addDETAIL("La valeur est déjà saisie, pas de modification.")
				}else {
					String msg = deleteText(tObj)
					if (msg) {
						TNRResult.addSTEP("Saisie de $name en utilisant l'assistant de recherche",'FAIL')
						TNRResult.addDETAIL(msg)
					}else {
						runSearchWithHelper(myJDD, name , btnXpath , inputSearchName , index_td)
					}
				}
				//}
			}
		}
	}


} // Fin de class