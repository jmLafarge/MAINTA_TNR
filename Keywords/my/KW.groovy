package my

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
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
			my.Log.addDEBUG("waitForAlert OK")
			return true
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("waitForAlert")
			my.Log.addDETAIL(ex.getMessage())
			return false
		}
	}


	static boolean acceptAlert() {
		try {
			WebUI.acceptAlert(FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Accepter Alerte")
			return true
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Accepter Alerte")
			my.Log.addDETAIL(ex.getMessage())
			return false
		}
	}



	static String sendKeys(TestObject tObj, String keys) {
		try {
			WebUI.sendKeys(tObj, keys, FailureHandling.STOP_ON_FAILURE)
			my.Log.addDEBUG("Envoie touches clavier '$keys' sur '${tObj.getObjectId()}'")
			return null
		} catch (Exception ex) {
			return my.Log.addDETAIL(ex.getMessage())
		}
	}




	static verifyElementChecked(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {
		try {
			WebUI.verifyElementChecked(tObj,timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Contrôle si '${tObj.getObjectId()}' est coché")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Contrôle si '${tObj.getObjectId()}' est coché")
			my.Log.addDETAIL(ex.getMessage())
		}
	}



	static verifyElementText(TestObject tObj, String text)  {
		String gText = WebUI.getText(tObj)
		try {
			WebUI.verifyElementText(tObj, text,FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérification du texte '$text' sur '" + tObj.getObjectId() + "'")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Vérification du texte '$text' sur '" + tObj.getObjectId() + "' KO, la valeur est '$gText' !")
			my.Log.addDETAIL(ex.getMessage())
		}
	} // end of def



	static verifyElementNotChecked(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {
		try {
			WebUI.verifyElementNotChecked(tObj,timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Contrôle si '${tObj.getObjectId()}' est décoché")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Contrôle si '${tObj.getObjectId()}' est décoché")
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
			my.Log.addSTEPPASS("Contrôle que l'élément '${tObj.getObjectId()}' est visible")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Contrôle que l'élément '${tObj.getObjectId()}' est visible")
			my.Log.addDETAIL(ex.getMessage())
		}
	} // end of def


	static verifyValue(TestObject tObj, String JDDval) {
		String val = WebUI.getAttribute(tObj, 'Value')
		if (val==null) my.Log.addERROR("L'attribut 'Value' n'existe pas !")
		if (val==JDDval) {
			my.Log.addSTEPPASS("Vérifier la valeur de '" + tObj.getObjectId() + "', valeur='$JDDval'")
		}else if (JDDval==my.JDDKW.getKW_NULL() && val=='') {
			my.Log.addSTEPPASS("Vérifier la valeur de '" + tObj.getObjectId() + "', valeur= Null ou Vide")
		}else {
			my.Log.addSTEPFAIL("Vérifier la valeur de '" + "KO, la valeur est '" + WebUI.getAttribute(tObj, 'Value') + "' !")
		}
	} // end of def


	static verifyOptionSelectedByValue(TestObject tObj, String val, boolean isRegex = false, int timeOut = GlobalVariable.TIMEOUT) {
		if (WebUI.verifyOptionSelectedByValue(tObj, val, isRegex, timeOut)) {
			my.Log.addSTEPPASS("Vérifier si l'option '$val' de '" + tObj.getObjectId() + "' est sélectionnée")
		}else{
			my.Log.addSTEPFAIL("KO, l'option '$val' de '" + tObj.getObjectId() + "' n'est pas sélectionnée !")
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



	static waitAndVerifyElementText(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT)  {

		this.waitForElementVisible(tObj, timeOut)
		this.verifyElementText(tObj, text)
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
				my.Log.addSTEPPASS("Cocher case à cocher '" + tObj.getObjectId() + "'")
				my.Log.addDETAIL("déjà cochée")
			}else {
				String msg = this.sendKeys(tObj, Keys.chord(Keys.SPACE))
				if (msg==null) {
					my.Log.addSTEPPASS("Cocher case à cocher '" + tObj.getObjectId() + "'")
				}else {
					my.Log.addSTEPFAIL("Cocher case à cocher '" + tObj.getObjectId() + "'")
					my.Log.addDETAIL(msg)
				}
			}
		}else {
			if (!etat) {
				my.Log.addSTEPPASS("Décocher case à cocher '" + tObj.getObjectId() + "'")
				my.Log.addDETAIL("déjà décochée")
			}else {
				String msg = this.sendKeys(tObj, Keys.chord(Keys.SPACE))
				if (msg==null) {
					my.Log.addSTEPPASS("Décocher case à cocher '" + tObj.getObjectId() + "'")
				}else {
					my.Log.addSTEPFAIL("Décocher case à cocher '" + tObj.getObjectId() + "'")
					my.Log.addDETAIL(msg)
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





} // end of class