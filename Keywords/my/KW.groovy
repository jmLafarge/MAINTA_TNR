package my

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import my.result.TNRResult
import my.JDD
import my.TO

/**
 * Personaliser et de regrouper certaines actions WebUI
 * 
 * @author X1009638
 *
 */

@CompileStatic
class KW {



	static delay(Number second) {

		TNRResult.addSTEP("Attente de $second seconde(s)")
		WebUI.delay(second)
	}



	static waitForPageLoad(int seconds=GlobalVariable.TIMEOUTForPageLoad) {

		TNRResult.addSTEP("Attendre chargement de la page ...MAX $seconds seconde(s)")
		WebUI.waitForPageLoad(seconds)
	}



	static openBrowser(String url){
		try {
			WebUI.openBrowser(url, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS("Ouverture du navigateur à l'URL :")
			TNRResult.addDETAIL("URL : $url")
			TNRResult.addBrowserInfo()
			waitForPageLoad()
		} catch (Exception ex) {
			TNRResult.addSTEPERROR("Ouverture du navigateur à l'URL :")
			TNRResult.addDETAIL("URL : $url")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}





	static navigateToUrl(String url,String nomUrl){
		try {
			WebUI.navigateToUrl(url, FailureHandling.STOP_ON_FAILURE)
			waitForPageLoad()
			TNRResult.addSTEPPASS("Navigation vers l'URL '$nomUrl' :")
			TNRResult.addDETAIL("URL : $url")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR("Navigation vers l'URL '$nomUrl' :")
			TNRResult.addDETAIL("URL : $url")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}




	static closeBrowser(){
		try {
			WebUI.closeBrowser()
			TNRResult.addSTEPPASS("Fermeture du navigateur")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR("Fermeture du navigateur")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}




	static maximizeWindow(){
		try {
			WebUI.maximizeWindow(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS("Maximise la fenêtre")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR("Maximise la fenêtre")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}




	static switchToFrame(JDD myJDD, String name) {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
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
	}




	static setText(JDD myJDD, String name, String text=null, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			try {
				WebUI.setText(tObj, text, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Saisie du texte '$text' sur '" + tObj.getObjectId() + "'")
			} catch (Exception ex) {
				TNRResult.addSTEP("Saisie du texte '$text' sur '" + tObj.getObjectId() + "'", status)
				TNRResult.addDETAIL(ex.getMessage())
				Log.addTrace('')
			}
		}else {
			TNRResult.addSTEPERROR("Saisie du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
	}




	static setEncryptedText(JDD myJDD, String name, String text=null) {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			try {
				WebUI.setEncryptedText(tObj, text, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Saisie du mot de passe sur '" + tObj.getObjectId() + "'")
			} catch (Exception ex) {
				TNRResult.addSTEPFAIL("Saisie du mot de passe sur '" + tObj.getObjectId() + "'")
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR("Saisie du mot de passe sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
	}




	static click(JDD myJDD, String name, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			try {
				WebUI.click(tObj, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Clic sur '" + tObj.getObjectId() + "'")
			} catch (Exception ex) {
				TNRResult.addSTEP("Clic sur '" + tObj.getObjectId() + "'",status)
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR("Clic sur '$name' imposible")
			TNRResult.addDETAIL(msgTO)
		}
	}


	static doubleClick(JDD myJDD, String name, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			try {
				WebUI.doubleClick(tObj, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Double click sur '" + tObj.getObjectId() + "'")
			} catch (Exception ex) {
				TNRResult.addSTEP("Double click sur '" + tObj.getObjectId() + "'",status)
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR("Double click sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
	}




	static scrollToElement(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			try {
				WebUI.scrollToElement(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
				Log.addTrace("Scroll to '${tObj.getObjectId()}' OK")
			} catch (Exception ex) {
				TNRResult.addSTEPERROR("Scroll to '${tObj.getObjectId()}'")
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR("Scroll to '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
	}




	static boolean waitForAlert(int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		try {
			WebUI.waitForAlert(timeOut, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS("Attendre la fenetre de confirmation")
			return true
		} catch (Exception ex) {
			TNRResult.addSTEP("Attendre la fenetre de confirmation",status)
			TNRResult.addDETAIL(ex.getMessage())
			return false
		}
	}




	static boolean acceptAlert(String status = 'FAIL') {
		try {
			WebUI.acceptAlert(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS("Accepter la demande de confirmation")
			return true
		} catch (Exception ex) {
			TNRResult.addSTEP("Accepter la demande de confirmation", status)
			TNRResult.addDETAIL(ex.getMessage())
			return false
		}
	}





	static String sendKeys(JDD myJDD, String name, String keys, String msg = '' , String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			try {
				WebUI.sendKeys(tObj, keys, FailureHandling.STOP_ON_FAILURE)
				if (msg) {
					TNRResult.addSTEPPASS(msg)
				}else {
					Log.addTrace("Envoie touche(s) clavier '$keys' sur '${tObj.getObjectId()}'")
				}
				return null
			} catch (Exception ex) {
				if (msg) {
					TNRResult.addSTEP(msg,status)
					TNRResult.addDETAIL(ex.getMessage())
				}
				return ex.getMessage()
			}
		}else {
			TNRResult.addSTEPERROR("Envoie touche(s) clavier '$keys' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
	}








	static boolean verifyElementNotPresent(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			try {
				WebUI.verifyElementNotPresent(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' ne soit plus présent")
				return true
			} catch (Exception ex) {
				TNRResult.addSTEP("Vérifier que '${tObj.getObjectId()}' ne soit plus présent", status)
				TNRResult.addDETAIL(ex.getMessage())
				return false
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que '$name' ne soit plus présent impossible")
			TNRResult.addDETAIL(msgTO)
		}
	}





	/**
	 * STEP : vérifie si l'élément Web est présent dans le DOM, même s'il n'est pas visible à l'utilisateur
	 * @param myJDD
	 * @param name
	 * @param timeOut
	 * @param status
	 * @return
	 */
	static boolean verifyElementPresent(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {

		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			try {
				WebUI.verifyElementPresent(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' soit présent")
				return true
			}catch (Exception ex) {
				TNRResult.addSTEP("Vérifier que '${tObj.getObjectId()}' soit présent", status)
				TNRResult.addDETAIL(ex.getMessage())
				TNRResult.addDETAIL("XPATH = "+tObj.getSelectorCollection().get(SelectorMethod.XPATH))
				return false
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que '$name' soit présent impossible")
			TNRResult.addDETAIL(msgTO)
			return false
		}
	}




	/**
	 * vérifie si l'élément Web est présent dans le DOM, même s'il n'est pas visible à l'utilisateur
	 * @param myJDD
	 * @param name
	 * @param timeOut
	 * @return
	 */
	static boolean isElementPresent(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT) {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			try {
				if (WebUI.verifyElementPresent(tObj, timeOut, FailureHandling.OPTIONAL)) {
					TNRResult.addSTEP("L'élément '${tObj.getObjectId()}' est présent")
					return true
				}else {
					TNRResult.addSTEP("L'élément '${tObj.getObjectId()}' n'est pas présent")
					return false
				}
			} catch (Exception ex) {
				TNRResult.addSTEPERROR("Vérifier si l'élément '$name' est présent")
				TNRResult.addDETAIL(ex.getMessage())
				return false
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier si l'élément '$name' est présent impossible")
			TNRResult.addDETAIL(msgTO)
			return false
		}
	}





	static boolean verifyElementText(JDD myJDD, String name, String text=null, String status = 'FAIL')  {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			String gText = WebUI.getText(tObj)

			if (WebUI.verifyElementText(tObj, text,FailureHandling.OPTIONAL)) {
				TNRResult.addSTEPPASS("Vérification du texte '$text' sur '" + tObj.getObjectId() + "'")
				return true
			} else {
				TNRResult.addSTEP("Vérification du texte '$text' sur '" + tObj.getObjectId() + "'",status)
				TNRResult.addDETAIL("la valeur est '$gText' !")
				return false
			}
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
	} // end of def



	static boolean verifyElementTextContains(JDD myJDD, String name, String text=null, String status = 'FAIL')  {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			String gText = WebUI.getText(tObj)

			if (gText.contains(text)) {
				TNRResult.addSTEPPASS("Vérification du texte '$text' sur '" + tObj.getObjectId() + "'")
				return true
			} else {
				TNRResult.addSTEP("Vérification du texte '$text' sur '" + tObj.getObjectId() + "'",status)
				TNRResult.addDETAIL("la valeur est '$gText' !")
				return false
			}
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
	} // end of def




	/** attend que l'élément Web soit présent dans le DOM et visible à l'utilisateur
	 * 
	 * @param myJDD
	 * @param name
	 * @param timeOut
	 * @param status
	 * @return
	 */
	static waitForElementVisible(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {

			try {
				if (WebUI.waitForElementVisible(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)) {
					TNRResult.addSTEPPASS("Vérifier que l'élément '${tObj.getObjectId()}' soit visible")
					return true
				}else {
					TNRResult.addSTEP("Vérifier que l'élément '${tObj.getObjectId()}' soit visible", status)
					TNRResult.addDETAIL("KO après $timeOut seconde(s)")
					return false
				}
				//WebUI.verifyElementInViewport(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
			} catch (Exception ex) {
				TNRResult.addSTEP("Vérifier que l'élément '${tObj.getObjectId()}' soit visible", status)
				TNRResult.addDETAIL(ex.getMessage())
				return false
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que l'élément '$name' soit visible impossible")
			TNRResult.addDETAIL(msgTO)
			return false
		}
	} // end of def





	static verifyValue(JDD myJDD, String name, String text=null, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			if (JDDKW.isNU(text)) {
				TNRResult.addSTEP("Pas de vérification pour $name, valeur du JDD = NU")
			}else {
				def val = WebUI.getAttribute(tObj, 'value')
				Log.addTrace('val.getClass() : ' + val.getClass() + '   ' + val)
				if (val==null) {
					TNRResult.addSTEPERROR("Vérifier que la valeur de '" + name + "', soit '$text'")
					TNRResult.addDETAIL("L'attribut 'value' n'existe pas !")
				}else if (val==text) {
					TNRResult.addSTEPPASS("Vérifier que la valeur de '" + name + "', soit '$text'")
				}else if (text==my.JDDKW.getKW_NULL() && val=='') {
					TNRResult.addSTEPPASS("Vérifier que la valeur de '" + name + "', soit Null ou Vide")
				}else {
					TNRResult.addSTEP("Vérifier que la valeur de '" + name + "', soit '$text'",status)
					TNRResult.addDETAIL("La valeur du champ est '" + WebUI.getAttribute(tObj, 'value') + "' !")
				}
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que la valeur de '$name' = '$text'  impossible")
			TNRResult.addDETAIL(msgTO)
		}
	} // end of def









	static verifyOptionSelectedByValue(JDD myJDD, String name, String text=null, boolean isRegex = false, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			if (WebUI.verifyOptionSelectedByValue(tObj, text, isRegex, timeOut)) {
				TNRResult.addSTEPPASS("Vérifier que l'option '$text' de '" + tObj.getObjectId() + "' soit sélectionnée")
			}else{
				TNRResult.addSTEP("Vérifier que l'option '$text' de '" + tObj.getObjectId() + "' soit sélectionnée KO", status)
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que l'option '$text' de '$name' soit sélectionnée")
			TNRResult.addDETAIL(msgTO)
		}
	}















	static scrollAndClick(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		scrollToElement(myJDD, name, timeOut,status)
		delay(1)
		waitForElementVisible(myJDD, name, timeOut,status)
		click(myJDD, name,status)
	} // end of def






	static scrollAndDoubleClick(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		scrollToElement(myJDD, name, timeOut,status)
		delay(1)
		waitForElementVisible(myJDD, name, timeOut,status)
		doubleClick(myJDD, name,status)
	} // end of def






	static scrollAndSelectOptionByValue(JDD myJDD, String name, String text=null, boolean isRegex = true, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			scrollToElement(myJDD, name, timeOut,status)
			waitForElementVisible(myJDD, name, timeOut,status)
			try {
				WebUI.selectOptionByValue(tObj, text, isRegex,FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Scroll et select option '$text' sur '" + tObj.getObjectId() + "'")
			} catch (Exception ex) {
				TNRResult.addSTEP("Scroll et select option '$text' sur '" + tObj.getObjectId() + "'",status)
				TNRResult.addDETAIL(ex.getMessage())
			}

		}else {
			TNRResult.addSTEPERROR("Scroll et select option '$text' sur '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	} // end of def



	static scrollAndSetRadio(JDD myJDD, String name, String text=null, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (text==null) text = myJDD.getStrData(name)
		if (text != my.JDDKW.getKW_NULL()) {
			if (text == my.JDDKW.getKW_VIDE()) text=''
			scrollToElement(myJDD, name, timeOut, status)
			waitForElementVisible(myJDD, name, timeOut, status)
			click(myJDD, name,status)
		}
	} // end of def
	


	static scrollAndSetText(JDD myJDD, String name, String text=null, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (text==null) text = myJDD.getStrData(name)
		if (text != my.JDDKW.getKW_NULL()) {
			if (text == my.JDDKW.getKW_VIDE()) text=''
			scrollToElement(myJDD, name, timeOut, status)
			waitForElementVisible(myJDD, name, timeOut, status)
			setText(myJDD, name, text, status)
		}
	} // end of def



	static scrollAndSetDate(JDD myJDD, String name, def val=null,  String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (val==null) val = myJDD.getData(name)
		if (val != my.JDDKW.getKW_NULL()) {
			scrollToElement(myJDD, name, timeOut, status)
			waitForElementVisible(myJDD, name, timeOut, status)
			setDate(myJDD, name, val, dateFormat, timeOut,status)
		}
	} // end of def


	static boolean waitAndAcceptAlert(int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (waitForAlert(timeOut, status)) {
			return acceptAlert(status)
		}else {
			return false
		}
	} // end of def





	static setDate(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (val==null) val = myJDD.getData(name)
		if ( val instanceof Date) {
			setText(myJDD, name, val.format(dateFormat), status)
		}else {
			TNRResult.addSTEPERROR("Saisie du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
	} // end of def





	static verifyDateText(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT , String status = 'FAIL')  {

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
	} // end of def




	static verifyDateValue(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTrace("verifyDateFromValue : name='$name' val='${val.toString()} dateFormat='dateFormat' status='$status'")

		if (val==null) val = myJDD.getData(name)
		if (val == '$VIDE') {
			verifyValue(myJDD, name, '', status)
		}else if ( val instanceof Date) {
			verifyValue(myJDD, name, val.format(dateFormat), status)
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
	} // end of def


	static scrollWaitAndVerifyElementText(JDD myJDD, String name, String text=null, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {

		if (text==null) text = myJDD.getStrData(name)
		scrollToElement(myJDD, name, timeOut, status)
		waitAndVerifyElementText(myJDD, name, text,timeOut, status)

	} // end of def




	static boolean waitAndVerifyElementText(JDD myJDD, String name, String text=null, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL')  {

		if (text==null) text = myJDD.getStrData(name)
		if (waitForElementVisible(myJDD, name, timeOut, status)) {
			return verifyElementText(myJDD, name, text, status)
		}else {
			return false
		}

	} // end of def






	static scrollAndCheckIfNeeded(JDD myJDD, String name, String textTrue, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL')  {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			TO myTOLbl = new TO() ; TestObject tObjLbl  = myTOLbl.make(myJDD,'Lbl'+name) ;String msgLbl = myTOLbl.getMsg()
			if (tObjLbl) {
				boolean cond = myJDD.getStrData(name)==textTrue

				scrollToElement(myJDD, name,timeOut,status)
				waitForElementVisible(myJDD, name, timeOut,status)
				if (cond) {
					if (WebUI.verifyElementChecked(tObj,timeOut, FailureHandling.OPTIONAL)) {
						TNRResult.addSTEPPASS("Cocher la case à cocher '" + name + "'")
						TNRResult.addDETAIL("déjà cochée")
					}else {
						try {
							WebUI.click(tObjLbl, FailureHandling.STOP_ON_FAILURE)
							TNRResult.addSTEPPASS("Cocher la case à cocher '" + name + "'")
						} catch (Exception ex) {
							TNRResult.addSTEP("Cocher la case à cocher '" + name + "'", status)
							TNRResult.addDETAIL(ex.getMessage())
						}
					}
				}else {
					if (WebUI.verifyElementNotChecked(tObj,timeOut, FailureHandling.OPTIONAL)) {
						TNRResult.addSTEPPASS("Décocher la case à cocher '" + name + "'")
						TNRResult.addDETAIL("déjà décochée")
					}else {
						try {
							WebUI.click(tObjLbl, FailureHandling.STOP_ON_FAILURE)
							TNRResult.addSTEPPASS("Décocher la case à cocher '" + name + "'")
						} catch (Exception ex) {
							TNRResult.addSTEP("Décocher la case à cocher '" + name + "'", status)
							TNRResult.addDETAIL(ex.getMessage())
						}
					}
				}
			}else {
				TNRResult.addSTEPERROR("Label Case à cocher 'Lbl$name'")
				TNRResult.addDETAIL(msgTO)
			}
		}else {
			TNRResult.addSTEPERROR("Case à cocher '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	} // end of def






	static verifyElementCheckedOrNot(JDD myJDD, String name, String textTrue, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
			boolean cond = myJDD.getStrData(name)==textTrue

			scrollToElement(myJDD, name,timeOut,status)
			waitForElementVisible(myJDD, name, timeOut,status)
			if (cond) {
				if (WebUI.verifyElementChecked(tObj,timeOut, FailureHandling.OPTIONAL)) {
					TNRResult.addSTEPPASS("Vérifier que '${tObj.getObjectId()}'soit coché")
				}else {
					TNRResult.addSTEPFAIL("Vérifier que '${tObj.getObjectId()}'soit coché")
					TNRResult.addDETAIL('La case est décochée !')
				}
			}else {
				if (WebUI.verifyElementNotChecked(tObj,timeOut, FailureHandling.OPTIONAL)) {
					TNRResult.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' soit décoché")
				}else {
					TNRResult.addSTEPFAIL("Vérifier que '${tObj.getObjectId()}' soit décoché")
					TNRResult.addDETAIL('La case est cochée !')
				}
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier état case à cocher '$name'")
			TNRResult.addDETAIL(msgTO)
		}

	}





	static getCheckBoxImgStatus(JDD myJDD, String name)  {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (tObj) {
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





	static verifyCheckBoxImgChecked(JDD myJDD, String name, String status = 'FAIL')  {
		def etat = getCheckBoxImgStatus(myJDD, name)
		if (etat ==null) {
			// l'erreur est déjà remontée par getCheckBoxImgStatus
		}else if (etat) {
			TNRResult.addSTEPPASS("Vérifier que la case à cocher (img) '" + name + "' soit cochée")
		}else {
			TNRResult.addSTEP("Vérifier que la case à cocher (img) '" + name + "' soit cochée", status)
		}

	}





	static verifyCheckBoxImgNotChecked(JDD myJDD, String name, String status = 'FAIL')  {
		def etat = getCheckBoxImgStatus(myJDD, name)
		if (etat ==null) {
			// l'erreur est déjà remontée par getCheckBoxImgStatus
		}else if (!etat) {
			TNRResult.addSTEPPASS("Vérifier que la case à cocher (img) '" + name + "' soit cochée")
		}else {
			TNRResult.addSTEP("Vérifier que la case à cocher (img) '" + name + "' soit cochée", status)
		}
	}





	static verifyImgCheckedOrNot(JDD myJDD, String name, String textTrue, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		boolean cond = myJDD.getStrData(name)==textTrue
		if (cond) {
			verifyCheckBoxImgChecked(myJDD, name, status)
		}else {
			verifyCheckBoxImgNotChecked(myJDD, name, status)
		}
	}





	static verifyImg(JDD myJDD, String name, boolean cond, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (cond) {
			verifyElementPresent(myJDD, name, timeOut, status)
		}
	}





	static searchWithHelper(JDD myJDD, String name , String btnXpath = '' , String inputSearchName = '' ){

		String val = myJDD.getStrData(name)

		if (JDDKW.isNULL(val) || JDDKW.isNU(val)) {

			TNRResult.addSTEP("Pas de recherche pour $name, valeur du JDD = $val")

		}else {

			TNRResult.addSUBSTEP("Saisie de $name en utilisant l'assistant de recherche")

			if (btnXpath=='') {
				btnXpath = "//a[@id='Btn$name']/i"
			}

			if (inputSearchName=='') inputSearchName = "SEARCH_$name"



			String inputXpath 	= "//input[@name='$inputSearchName']"
			String tdXpath 		= "//div[@id='v-dbtdhtmlx1']/table/tbody//td[3][text()='$val']"

			myJDD.xpathTO.put('btnSearch', btnXpath)
			myJDD.xpathTO.put('inputSearch', inputXpath)
			myJDD.xpathTO.put('tdSearch', tdXpath)

			scrollAndClick(myJDD,'btnSearch')

			WebUI.switchToWindowIndex('1')

			setText(myJDD,'inputSearch', myJDD.getStrData(name))

			'mise à jour dynamique du xpath'
			scrollWaitAndVerifyElementText(myJDD,'tdSearch', myJDD.getStrData(name))

			click(myJDD,'tdSearch')

			WebUI.switchToWindowIndex('0')
		}

	}


} // end of class