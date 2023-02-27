package my

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import org.openqa.selenium.Keys as Keys

import internal.GlobalVariable

class KW {

	/* 
	 * Personaliser et de regrouper certaines actions WebUI
	 *
	 *
	 *
	 *
	 * 
	 * 
	 * 
	 */


	static openBrowser(String url){
		try {
			WebUI.openBrowser(url, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Ouverture du navigateur à l'URL : $url")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Ouverture du navigateur à l'URL : $url")
			my.Log.addDETAIL(ex.getMessage())
		}
	}


	static navigateToUrl(String url,String nomUrl){
		try {
			WebUI.navigateToUrl(url, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Navigation vers l'URL '$nomUrl' : $url")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Navigation vers l'URL '$nomUrl' : $url")
			my.Log.addDETAIL(ex.getMessage())
		}
	}



	static maximizeWindow(){
		try {
			WebUI.maximizeWindow(FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Maximise la fenêtre")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Maximise la fenêtre")
			my.Log.addDETAIL(ex.getMessage())
		}
	}



	static setText(TestObject tObj, String text) {
		try {
			WebUI.setText(tObj, text, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Saisie du texte '$text' sur '" + tObj.getObjectId() + "'")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Saisie du texte '$text' sur '" + tObj.getObjectId() + "'")
			my.Log.addDETAIL(ex.getMessage())
		}
	}



	static setEncryptedText(TestObject tObj, String text) {
		try {
			WebUI.setEncryptedText(tObj, text, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Saisie du mot de passe sur " + tObj.getObjectId() + "'")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Saisie du mot de passe sur '" + tObj.getObjectId() + "'")
			my.Log.addDETAIL(ex.getMessage())
		}
	}



	static click(TestObject tObj) {
		try {
			WebUI.click(tObj, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Clic sur '" + tObj.getObjectId() + "'")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Clic sur '" + tObj.getObjectId() + "'")
			my.Log.addDETAIL(ex.getMessage())
		}
	}


	static doubleClick(TestObject tObj) {
		try {
			WebUI.doubleClick(tObj, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Double click sur '" + tObj.getObjectId() + "'")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Double click sur '" + tObj.getObjectId() + "'")
			my.Log.addDETAIL(ex.getMessage())
		}
	}


	static scrollToElement(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {
		try {
			WebUI.scrollToElement(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addDEBUG("Scroll to '${tObj.getObjectId()}' OK")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Scroll to '${tObj.getObjectId()}'")
			my.Log.addDETAIL(ex.getMessage())
		}
	}


	static boolean waitForAlert(int timeOut = GlobalVariable.TIMEOUT) {
		try {
			WebUI.waitForAlert(timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Attendre la fenetre de confirmation")
			return true
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Attendre la fenetre de confirmation")
			my.Log.addDETAIL(ex.getMessage())
			return false
		}
	}


	static boolean acceptAlert() {
		try {
			WebUI.acceptAlert(FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Accepter la demande de confirmation")
			return true
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Accepter la demande de confirmation")
			my.Log.addDETAIL(ex.getMessage())
			return false
		}
	}



	static String sendKeys(TestObject tObj, String keys, String msg = '' ) {
		try {
			WebUI.sendKeys(tObj, keys, FailureHandling.STOP_ON_FAILURE)
			if (msg) {
				my.Log.addSTEPPASS(msg)
			}else {
				my.Log.addDEBUG("Envoie touche(s) clavier '$keys' sur '${tObj.getObjectId()}'")
			}
			return null
		} catch (Exception ex) {
			if (msg) {
				my.Log.addSTEPFAIL(msg)
				my.Log.addDETAIL(ex.getMessage())
			}
			return ex.getMessage()
		}
	}




	static verifyElementChecked(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {
		try {
			WebUI.verifyElementChecked(tObj,timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérifier que '${tObj.getObjectId()}'soit coché")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Vérifier que '${tObj.getObjectId()}' soit coché")
			my.Log.addDETAIL(ex.getMessage())
		}
	}


	static boolean verifyElementNotPresent(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {
		try {
			WebUI.verifyElementNotPresent(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' ne soit plus présent")
			return true
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Vérifier que '${tObj.getObjectId()}' ne soit plus présent")
			my.Log.addDETAIL(ex.getMessage())
			return false
		}
	}





	static boolean verifyElementPresent(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {
		try {
			WebUI.verifyElementPresent(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' soit présent")
			return true
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Vérifier que '${tObj.getObjectId()}' soit présent")
			my.Log.addDETAIL(ex.getMessage())
			return false
		}
	}



	static boolean verifyElementText(TestObject tObj, String text)  {
		String gText = WebUI.getText(tObj)
		try {
			WebUI.verifyElementText(tObj, text,FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérification du texte '$text' sur '" + tObj.getObjectId() + "'")
			return true
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Vérification du texte '$text' sur '" + tObj.getObjectId() + "' KO, la valeur est '$gText' !")
			my.Log.addDETAIL(ex.getMessage())
			return false
		}
	} // end of def



	static verifyElementNotChecked(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {
		try {
			WebUI.verifyElementNotChecked(tObj,timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' soit décoché")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Vérifier que '${tObj.getObjectId()}' soit décoché")
			my.Log.addDETAIL(ex.getMessage())
		}
	}




	static waitForElementClickable(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {
		try {
			WebUI.waitForElementClickable(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addDEBUG("'${tObj.getObjectId()}' est clickable")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("'${tObj.getObjectId()}' n'est pas clickable")
			my.Log.addDETAIL(ex.getMessage())
		}
	} // end of def



	static waitForElementVisible(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {
		try {
			WebUI.waitForElementVisible(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérifier que l'élément '${tObj.getObjectId()}' soit visible")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Vérifier que l'élément '${tObj.getObjectId()}' soit visible")
			my.Log.addDETAIL(ex.getMessage())
		}
	} // end of def




	static verifyValue(TestObject tObj, String JDDval) {
		def val = WebUI.getAttribute(tObj, 'value')
		my.Log.addDEBUG('val.getClass() : ' + val.getClass() + '   ' + val)
		if (val==null) {
			my.Log.addERROR("L'attribut 'value' n'existe pas !")
		}
		if (val==JDDval) {
			my.Log.addSTEPPASS("Vérifier que la valeur de '" + tObj.getObjectId() + "', soit '$JDDval'")
		}else if (JDDval==my.JDDKW.getKW_NULL() && val=='') {
			my.Log.addSTEPPASS("Vérifier que la valeur de '" + tObj.getObjectId() + "', soit Null ou Vide")
		}else {
			my.Log.addSTEPFAIL("Vérifier la valeur de '" + tObj.getObjectId() + "' KO, valeur attendue '$JDDval', valeur du champ '" + WebUI.getAttribute(tObj, 'value') + "' !")
		}
	} // end of def


	static verifyOptionSelectedByValue(TestObject tObj, String val, boolean isRegex = false, int timeOut = GlobalVariable.TIMEOUT) {
		if (WebUI.verifyOptionSelectedByValue(tObj, val, isRegex, timeOut)) {
			my.Log.addSTEPPASS("Vérifier que l'option '$val' de '" + tObj.getObjectId() + "' soit sélectionnée")
		}else{
			my.Log.addSTEPFAIL("Vérifier que l'option '$val' de '" + tObj.getObjectId() + "' soit sélectionnée KO")
		}
	}



	static scrollAndClick(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {
		this.scrollToElement(tObj, timeOut)
		this.waitForElementClickable(tObj, timeOut)
		this.click(tObj)
	} // end of def




	static scrollAndDoubleClick(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {
		this.scrollToElement(tObj, timeOut)
		this.waitForElementVisible(tObj, timeOut)
		this.doubleClick(tObj)
	} // end of def




	static scrollAndSelectOptionByValue(TestObject tObj, String val, boolean isRegex = true, int timeOut = GlobalVariable.TIMEOUT) {
		this.scrollToElement(tObj, timeOut)
		this.waitForElementVisible(tObj, timeOut)
		try {
			WebUI.selectOptionByValue(tObj, val, isRegex,FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Scroll et select option '$val' sur '" + tObj.getObjectId() + "'")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Scroll et select option '$val' sur '" + tObj.getObjectId() + "'")
			my.Log.addDETAIL(ex.getMessage())
		}
	} // end of def




	static scrollAndSetText(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT) {
		if (text != my.JDDKW.getKW_NULL()) {
			if (text == my.JDDKW.getKW_VIDE()) text=''
			this.scrollToElement(tObj, timeOut)
			this.waitForElementVisible(tObj, timeOut)
			this.setText(tObj, text)
		}
	} // end of def




	static boolean waitAndAcceptAlert(int timeOut = GlobalVariable.TIMEOUT) {
		if (this.waitForAlert(timeOut)) {
			return this.acceptAlert()
		}else {
			return false
		}
	} // end of def



	static setDate(TestObject tObj, def val, String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT) {
		if ( val instanceof Date) {
			this.setText(tObj, val.format(dateFormat))
		}else {
			my.Log.addERROR('Erreur de JDD de ' + tObj.getObjectId() + ', la valeur ' + val.toString() + "' n'est pas une date ! getClass = " + val.getClass())
		}
	} // end of def



	static verifyDate(TestObject tObj, def val, String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT )  {
		if ( val instanceof Date) {
			my.Log.addDEBUG('val.format(dateFormat) :' +val.format(dateFormat))
			this.verifyElementText(tObj, val.format(dateFormat))
		}else {
			my.Log.addERROR('Erreur de JDD de ' + tObj.getObjectId() + ', la valeur ' + val.toString() + "' n'est pas une date ! getClass = " + val.getClass())
		}
	} // end of def



	static scrollWaitAndVerifyElementText(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT) {
		this.scrollToElement(tObj, timeOut)
		this.waitAndVerifyElementText(tObj, text,timeOut)
	} // end of def



	static boolean waitAndVerifyElementText(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT)  {

		this.waitForElementVisible(tObj, timeOut)
		return this.verifyElementText(tObj, text)
	} // end of def





	/*
	 static waitAndVerifyElementText_justWarning(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT)  {
	 this.waitForElementVisible(tObj, timeOut)
	 String gText = WebUI.getText(tObj)
	 if (text==gText) {
	 my.Log.addSTEPPASS("Vérification du texte '$text' sur '" + tObj.getObjectId() + "'")
	 }else {
	 my.Log.addSTEPWARNING("Vérification du texte '$text' sur '" + tObj.getObjectId() + "' KO, la valeur est '$gText' !")
	 }
	 } // end of def
	 */




	static scrollAndCheckIfNeeded(TestObject tObj, boolean cond, int timeOut = GlobalVariable.TIMEOUT)  {
		this.scrollToElement(tObj,timeOut)
		boolean etat = WebUI.getAttribute(tObj, 'checked')=='yes'
		if (cond) {
			if (etat) {
				my.Log.addSTEPPASS("Cocher la case à cocher '" + tObj.getObjectId() + "'")
				my.Log.addDETAIL("déjà cochée")
			}else {
				String msg = this.sendKeys(tObj, Keys.chord(Keys.SPACE))
				if (msg) {
					my.Log.addSTEPFAIL("Cocher la case à cocher '" + tObj.getObjectId() + "'")
					my.Log.addDETAIL(msg)
				}else {
					my.Log.addSTEPPASS("Cocher la case à cocher '" + tObj.getObjectId() + "'")
				}
			}
		}else {
			if (!etat) {
				my.Log.addSTEPPASS("Décocher la case à cocher '" + tObj.getObjectId() + "'")
				my.Log.addDETAIL("déjà décochée")
			}else {
				String msg = this.sendKeys(tObj, Keys.chord(Keys.SPACE))
				if (msg) {
					my.Log.addSTEPFAIL("Décocher la case à cocher '" + tObj.getObjectId() + "'")
					my.Log.addDETAIL(msg)
				}else {
					my.Log.addSTEPPASS("Décocher la case à cocher '" + tObj.getObjectId() + "'")
				}
			}
		}
	} // end of def





	static verifyElementCheckedOrNot(TestObject tObj, boolean cond, int timeOut = GlobalVariable.TIMEOUT) {
		if (cond) {
			this.verifyElementChecked(tObj,timeOut)
		}else {
			this.verifyElementNotChecked(tObj,timeOut)
		}
	}




	static getCheckBoxImgStatus(TestObject tObj)  {
		if (WebUI.getAttribute(tObj, 'src').endsWith('133.gif')) {
			return true
		}else if (WebUI.getAttribute(tObj, 'src').endsWith('134.gif')) {
			return false
		}else {
			my.Log.addERROR("L'attribut src de l'objet " + tObj.getObjectId() + " n'est pas conforme, la valeur est : " + WebUI.getAttribute(tObj, 'src'))
			return null
		}
	}


	static verifyCheckBoxImgChecked(TestObject tObj)  {

		def etat = this.getCheckBoxImgStatus(tObj)
		if (etat ==null) {
			// l'erreur est déjà remontée par getCheckBoxImgStatus
		}else if (etat) {
			my.Log.addSTEPPASS("Vérifier que la case à cocher (img) '" + tObj.getObjectId() + "' soit cochée")
		}else {
			my.Log.addSTEPFAIL("Vérifier que la case à cocher (img) '" + tObj.getObjectId() + "' soit cochée")
		}

	}


	static verifyCheckBoxImgNotChecked(TestObject tObj)  {

		def etat = this.getCheckBoxImgStatus(tObj)
		if (etat ==null) {
			// l'erreur est déjà remontée par getCheckBoxImgStatus
		}else if (!etat) {
			my.Log.addSTEPPASS("Vérifier que la case à cocher (img) '" + tObj.getObjectId() + "' soit cochée")
		}else {
			my.Log.addSTEPFAIL("Vérifier que la case à cocher (img) '" + tObj.getObjectId() + "' soit cochée")
		}
	}





	static verifyImgCheckedOrNot(TestObject tObj, boolean cond, int timeOut = GlobalVariable.TIMEOUT) {
		if (cond) {
			this.verifyCheckBoxImgChecked(tObj)
		}else {
			this.verifyCheckBoxImgNotChecked(tObj)
		}
	}

	static verifyImg(TestObject tObj, boolean cond, int timeOut = GlobalVariable.TIMEOUT) {
		if (cond) {
			this.verifyElementPresent(tObj, timeOut)
		}
	}





} // end of class