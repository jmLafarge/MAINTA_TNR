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



	static void waitForPageLoad(int seconds = (int)GlobalVariable.TIMEOUTForPageLoad) {
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
			KWWindow.storeMainWindowHandle()
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




	/** NEW
	 * Scroll si besoin et set le text sur l"élément
	 * 
	 * @param myJDD
	 * @param name
	 * @param text
	 * @param timeout
	 * @param status
	 */
	static void setText(JDD myJDD, String name, String text=null , int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setText", [myJDD: myJDD.toString(), name: name , text:text , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			if (tnrJDDManager.JDDKW.isNULL(text) || tnrJDDManager.JDDKW.isNU(text)) {
				Log.addINFO('Pas de traitement')
			}else {
				String objValue = WebUI.getAttribute(tObj, 'value')
				Log.addTrace("objText:'$objValue'")
				if (objValue==text){
					TNRResult.addSTEPPASS("Saisie du texte '$text' sur '${tObj.getObjectId()}'")
					TNRResult.addDETAIL("La valeur est déjà saisie, pas de modification.")
				}else {
					String errMsg = KWElement.goToElement(tObj, name, timeout, status)
					if (!errMsg) {
						try {
							WebUI.setText(tObj, text, FailureHandling.STOP_ON_FAILURE)
							TNRResult.addSTEPPASS("Saisie du texte '$text' sur '${tObj.getObjectId()}'")
						} catch (Exception ex) {
							TNRResult.addSTEP("Saisie du texte '$text' sur '${tObj.getObjectId()}'", status)
							TNRResult.addDETAIL(ex.getMessage())
							Log.addTrace('')
						}
					}else {
						TNRResult.addSTEP("Saisie du texte sur '${tObj.getObjectId()}'",status)
						TNRResult.addDETAIL(errMsg)
					}
				}
			}
		}else {
			TNRResult.addSTEPERROR("Saisie du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "setText")
	}


	/** NEW
	 * Scroll si besoin et set EncryptedText sur l"élément
	 * 
	 * @param myJDD
	 * @param name
	 * @param text
	 * @param timeout
	 * @param status
	 */
	static void setEncryptedText(JDD myJDD, String name, String text=null,int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setEncryptedText", [myJDD: myJDD.toString(), name: name , text:text , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			String errMsg = KWElement.goToElement(tObj, name, timeout, status)
			if (!errMsg) {
				try {
					WebUI.setEncryptedText(tObj, text, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS("Saisie du mot de passe sur '${tObj.getObjectId()}'")
				} catch (Exception ex) {
					TNRResult.addSTEP("Saisie du mot de passe sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP("Saisie du mot de passe sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Saisie du mot de passe sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "setEncryptedText")
	}




	/** NEW
	 * Scroll si besoin et click sur l"élément
	 * @param myJDD
	 * @param name
	 * @param timeout
	 * @param status
	 * @return
	 */
	static boolean click(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "click", [myJDD: myJDD.toString(), name: name, timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			String errMsg = KWElement.goToElement(tObj, name, timeout, status)
			if (!errMsg) {
				try {
					WebUI.click(tObj, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS("Clic sur '${tObj.getObjectId()}'")
					ret= true
				} catch (Exception ex) {
					TNRResult.addSTEP("Clic sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP("Clic sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Clic sur '$name' imposible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "click",ret)
		return ret
	}



	/** NEW
	 * Scroll si besoin et click sur l"élément
	 * 
	 * @param myJDD
	 * @param name
	 * @param timeout
	 * @param status
	 * @return
	 */
	static boolean doubleClick(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "doubleClick", [myJDD: myJDD.toString(), name: name, timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			String errMsg = KWElement.goToElement(tObj, name, timeout, status)
			if (!errMsg) {
				try {
					WebUI.doubleClick(tObj, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS("Double click sur '${tObj.getObjectId()}'")
					ret= true
				} catch (Exception ex) {
					TNRResult.addSTEP("Double click sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP("Double clic sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Double click sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "doubleClick",ret)
		return ret
	}






	static boolean waitForAlert(int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
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


	static boolean waitAndAcceptAlert(int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
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







	/** OK
	 * Vériife que l'élément ne soit plus présent 
	 * 
	 * Utile pour vérifier qu'un élément a été supprimé
	 * 
	 * @param myJDD
	 * @param name
	 * @param timeout
	 * @param status
	 * @return
	 */
	static boolean verifyElementNotPresent(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
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




	/** OK
	 * Vérifie que l'élément est présent
	 * 
	 * Utile pour des éléments sans interaction avec le user, comme par ex: frame, ...
	 * sinon préférer KWElement.goToElement
	 * 
	 * @param myJDD
	 * @param name
	 * @param timeout
	 * @param status
	 * @return
	 */
	static boolean verifyElementPresent(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
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


	
	
	
	
	
	
	
	static String getText(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT )  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getText", [myJDD: myJDD.toString(), name: name , timeout:timeout ])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		String gText = ''
		if (!msgTO) {
			if (isElementPresent(myJDD,name,timeout)) {
				try {
					gText = WebUI.getText(tObj,FailureHandling.STOP_ON_FAILURE)
				} catch (Exception ex) {
					Log.addINFO("Lecture du texte sur '$name' KO")
					Log.addDETAIL(ex.getMessage())
				}
			}
		}else {
			TNRResult.addSTEPERROR("Lecture du texte sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getText",String)
		return String
	}





	/** NEW
	 * scroll si besoin et vérifie que le texte de l'élément
	 *
	 * @param myJDD
	 * @param name
	 * @param text
	 * @param timeout
	 * @param status
	 * @return
	 */
	static boolean verifyText(JDD myJDD, String name, String text=null, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyText", [myJDD: myJDD.toString(), name: name , text:text , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			String gText = WebUI.getText(tObj)
			String errMsg = KWElement.goToElement(tObj, name, timeout, status)
			if (!errMsg) {
				if (WebUI.verifyElementText(tObj, text,FailureHandling.OPTIONAL)) {
					TNRResult.addSTEPPASS("Vérification du texte '$text' sur '${tObj.getObjectId()}'")
					ret = true
				} else {
					TNRResult.addSTEP("Vérification du texte '$text' sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL("la valeur est '$gText' !")
				}
			}else {
				TNRResult.addSTEP("Vérification du texte '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyText",ret)
		return ret
	}





	/** NEW
	 * scroll si besoin et vérifie que le texte de l'élément contient le text
	 * 
	 * @param myJDD
	 * @param name
	 * @param text
	 * @param timeout
	 * @param status
	 * @return
	 */
	static boolean verifyTextContains(JDD myJDD, String name, String text=null, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyTextContains", [myJDD: myJDD.toString(), name: name , text:text , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			String gText = WebUI.getText(tObj)
			String errMsg = KWElement.goToElement(tObj, name, timeout, status)
			if (!errMsg) {
				if (gText.contains(text)) {
					TNRResult.addSTEPPASS("Vérification que le texte '$gText' contient '$text'sur '${tObj.getObjectId()}'")
					ret = true
				} else {
					TNRResult.addSTEP("Vérification que le texte '$gText' contient '$text' sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL("la valeur est '$gText' !")
				}
			}else {
				TNRResult.addSTEP("Vérification que le texte '$gText' contient '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Vérification que le texte '?' contient '$text'sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyTextContains",ret)
		return ret
	}






	static void verifyValue(JDD myJDD, String name, String text=null, int timeout  = (int) GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyValue", [myJDD: myJDD.toString(), name: name , text:text , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			if (JDDKW.isNU(text)) {
				TNRResult.addSTEP("Pas de vérification pour $name, valeur du JDD = NU")
			}else {
				String errMsg = KWElement.goToElement(tObj, name, timeout, status)
				if (!errMsg) {
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
					TNRResult.addDETAIL(errMsg)
				}
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que la valeur de '$name' = '$text'  impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyValue")
	}



	static void verifyRadioChecked(JDD myJDD, String name,  int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyRadioChecked", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (JDDKW.isNU(myJDD.getStrData(name))) {
				TNRResult.addSTEP("Pas de vérification pour $name, valeur du JDD = NU")
			}else {
				String errMsg = KWElement.goToElement(tObj, name, timeout, status)
				if (!errMsg) {
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
					TNRResult.addDETAIL(errMsg)
				}
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que le bouton radio '$name', soit sélectionné impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyRadioChecked")
	}




	private static void verifyOptionSelectedByLabel(JDD myJDD, String name, String text=null, boolean isRegex = false, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyOptionSelectedByLabel", [myJDD: myJDD.toString(), name: name , text:text , isRegex:isRegex , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			String errMsg = KWElement.goToElement(tObj, name, timeout, status)
			if (!errMsg) {
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
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier l'option '$text' de '$name'")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyOptionSelectedByLabel")
	}







	static boolean isElementPresent(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "isElementPresent", [myJDD: myJDD.toString(), name: name , timeout:timeout])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
				if (WebUI.verifyElementPresent(tObj, timeout, FailureHandling.OPTIONAL)) {
					Log.addINFO("L'élément '${tObj.getObjectId()}' est présent")
					ret = true
				}else {
					Log.addINFO("L'élément '${tObj.getObjectId()}' n'est pas présent")
				}
		}else {
			TNRResult.addSTEPERROR("Vérifier si l'élément '$name' est présent impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "isElementPresent",ret)
		return ret
	}



















	/** 
	 * est ce que l'élément is visible à l'utilisateur
	 * 
	 * @param myJDD
	 * @param name
	 * @param timeout
	 * @param status
	 * @return
	 */
	static boolean isElementVisible(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "isElementVisible", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			String errMsg = KWElement.goToElement(tObj, name, timeout, status)
			if (!errMsg) {
				Log.addINFO("L'élément '${tObj.getObjectId()}' est visible ")
				ret = true
			}else {
				Log.addINFO("L'élément '${tObj.getObjectId()}' n'est pas visible ")
				Log.addDETAIL(errMsg)
			}
		}else {
			Log.addINFO("Vérifier que l'élément '$name' soit visible impossible")
			Log.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "isElementVisible",ret)
		return ret
	}











	static void scrollAndSelectOptionByLabel(JDD myJDD, String name, String text=null, boolean isRegex = true, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			String errMsg = KWElement.goToElement(tObj, name, timeout, status)
			if (!errMsg) {
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
			}else {
				TNRResult.addSTEP("Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Scroll et select option '$text' sur '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}





	// pas utilisé, on utilise scrollAndSelectOptionByLabel
	private static void scrollAndSelectOptionByValue(JDD myJDD, String name, String text=null, boolean isRegex = true, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			String errMsg = KWElement.goToElement(tObj, name, timeout, status)
			if (!errMsg) {
				
				try {
					WebUI.selectOptionByValue(tObj, text, isRegex,FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS("Scroll et select option '$text' sur '${tObj.getObjectId()}'")
				} catch (Exception ex) {
					TNRResult.addSTEP("Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP("Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Scroll et select option '$text' sur '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}





	static void scrollAndSetRadio(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "scrollAndSetRadio", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		//Création du TO label en tenant compte de la valeur --> voir resolveXpath
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		String nameWithoutLbl = name.substring(3)
		if (!msgTO) {
			String errMsg = KWElement.goToElement(tObj, name, timeout, status)
			if (!errMsg) {
				try {
					WebUI.click(tObj, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS("Clic sur bouton radio '$nameWithoutLbl' (" + myJDD.getStrData(nameWithoutLbl) + ')')
				} catch (Exception ex) {
					TNRResult.addSTEP("Clic sur bouton radio '$nameWithoutLbl' (" + myJDD.getStrData(nameWithoutLbl) + ')',status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP("Clic sur bouton radio '$nameWithoutLbl' (" + myJDD.getStrData(nameWithoutLbl) + ')',status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Clic sur '$nameWithoutLbl' imposible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "scrollAndSetRadio")
	}




	static void setDate(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setDate", [myJDD: myJDD.toString(), name: name , val:val , dateFormat:dateFormat , timeout:timeout , status:status])
		if (val==null) val = myJDD.getData(name)
		if ( val instanceof Date) {
			setText(myJDD, name, val.format(dateFormat), timeout,status)
		}else {
			TNRResult.addSTEPERROR("Saisie du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "setDate")
	}





	static void verifyDateText(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTrace("verifyDateText : name='$name' val='${val.toString()} dateFormat='dateFormat' status='$status'")

		if (val==null) val = myJDD.getData(name)

		if (val == '$VIDE') {
			verifyText(myJDD, name, '', timeout, status)
		}else if ( val instanceof Date) {
			Log.addTrace('val.format(dateFormat) :' +val.format(dateFormat))
			verifyText(myJDD, name, val.format(dateFormat), timeout,status)
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
	}




	static void verifyDateValue(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyDateValue", [myJDD: myJDD.toString(), name: name , val:val , dateFormat:dateFormat , timeout:timeout , status:status])

		if (val==null) val = myJDD.getData(name)
		if (val == '$VIDE') {
			verifyValue(myJDD, name, '', timeout,status)
		}else if ( val instanceof Date) {
			verifyValue(myJDD, name, val.format(dateFormat),timeout, status)
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyDateValue")
	}




	static void verifyTimeValue(JDD myJDD, String name, def val=null, String timeFormat = 'HH:mm:ss', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyTimeValue", [myJDD: myJDD.toString(), name: name , val:val , timeFormat:timeFormat , timeout:timeout , status:status])

		if (val==null) val = myJDD.getData(name)
		if (val == '$VIDE') {
			verifyValue(myJDD, name, '', timeout,status)
		}else if ( val instanceof Date) {
			verifyValue(myJDD, name, val.format(timeFormat), timeout,status)
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyTimeValue")
	}









	public static String deleteText(TestObject tObj) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "deleteText", [tObj: tObj.getObjectId()])
		String msg =null
		try {
			WebUI.setText(tObj, '', FailureHandling.STOP_ON_FAILURE)
			Log.addTrace("Effacement du texte sur l'object '${tObj.getObjectId()}'")
		} catch (Exception ex) {
			msg = "Effacement du texte : " + ex.getMessage()
		}
		Log.addTraceEND(CLASS_FOR_LOG, "deleteText",msg)
		return msg
	}









} // Fin de class