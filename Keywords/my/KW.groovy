package my

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.Log as MYLOG


/**
 * Personaliser et de regrouper certaines actions WebUI
 * 
 * @author X1009638
 *
 */
class KW {



	static delay(Number second) {

		MYLOG.addSTEP("Attente de $second seconde(s)")
		WebUI.delay(second)
	}


	static waitForPageLoad(int seconds=GlobalVariable.TIMEOUTForPageLoad) {
		
		MYLOG.addSTEP("Attendre chargement de la page ...MAX $seconds seconde(s)")
		WebUI.waitForPageLoad(seconds)
		
	}


	static openBrowser(String url){
		try {
			WebUI.openBrowser(url, FailureHandling.STOP_ON_FAILURE)
			this.waitForPageLoad()
			MYLOG.addSTEPPASS("Ouverture du navigateur à l'URL : $url")
			def browser = my.Tools.getBrowserAndVersion()
			MYLOG.addSUBSTEP("*********************************************************************")
			MYLOG.addSUBSTEP("  Nom du navigateur".padRight(26) + browser.NAME)
			MYLOG.addSUBSTEP("  Version du navigateur".padRight(26) + browser.VERSION)
			MYLOG.addSUBSTEP("*********************************************************************")
			my.Result.addBrowserInfo()
		} catch (Exception ex) {
			MYLOG.addSTEPERROR("Ouverture du navigateur à l'URL : $url")
			MYLOG.addDETAIL(ex.getMessage())
		}
	}





	static navigateToUrl(String url,String nomUrl){
		try {
			WebUI.navigateToUrl(url, FailureHandling.STOP_ON_FAILURE)
			this.waitForPageLoad()
			MYLOG.addSTEPPASS("Navigation vers l'URL '$nomUrl' : $url")
		} catch (Exception ex) {
			MYLOG.addSTEPERROR("Navigation vers l'URL '$nomUrl' : $url")
			MYLOG.addDETAIL(ex.getMessage())
		}
	}




	static closeBrowser(){
		try {
			WebUI.closeBrowser()
			MYLOG.addSTEPPASS("Fermeture du navigateur")
		} catch (Exception ex) {
			MYLOG.addSTEPERROR("Fermeture du navigateur")
			MYLOG.addDETAIL(ex.getMessage())
		}
	}




	static maximizeWindow(){
		try {
			WebUI.maximizeWindow(FailureHandling.STOP_ON_FAILURE)
			MYLOG.addSTEPPASS("Maximise la fenêtre")
		} catch (Exception ex) {
			MYLOG.addSTEPERROR("Maximise la fenêtre")
			MYLOG.addDETAIL(ex.getMessage())
		}
	}




	static switchToFrame(JDD myJDD, String name) {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			try {
				WebUI.switchToFrame(tObj, GlobalVariable.TIMEOUT)
				MYLOG.addSTEP("Switch to frame '$name'",null)
			} catch (Exception ex) {
				MYLOG.addSTEPERROR("Switch to frame '$name'")
				MYLOG.addDETAIL(ex.getMessage())
			}
		}else {
			MYLOG.addSTEPERROR("Switch to frame '$name' impossible")
			MYLOG.addDETAIL(msgTO)
		}
	}




	static setText(JDD myJDD, String name, String text=null, String status = 'FAIL') {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			try {
				WebUI.setText(tObj, text, FailureHandling.STOP_ON_FAILURE)
				MYLOG.addSTEPPASS("Saisie du texte '$text' sur '" + tObj.getObjectId() + "'")
			} catch (Exception ex) {
				MYLOG.addSTEP("Saisie du texte '$text' sur '" + tObj.getObjectId() + "'", status)
				MYLOG.addDETAIL(ex.getMessage())
			}
		}else {
			MYLOG.addSTEPERROR("Saisie du texte '$text' sur '$name' impossible")
			MYLOG.addDETAIL(msgTO)
		}
	}




	static setEncryptedText(JDD myJDD, String name, String text=null) {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			try {
				WebUI.setEncryptedText(tObj, text, FailureHandling.STOP_ON_FAILURE)
				MYLOG.addSTEPPASS("Saisie du mot de passe sur '" + tObj.getObjectId() + "'")
			} catch (Exception ex) {
				MYLOG.addSTEPFAIL("Saisie du mot de passe sur '" + tObj.getObjectId() + "'")
				MYLOG.addDETAIL(ex.getMessage())
			}
		}else {
			MYLOG.addSTEPERROR("Saisie du mot de passe sur '$name' impossible")
			MYLOG.addDETAIL(msgTO)
		}
	}




	static click(JDD myJDD, String name, String status = 'FAIL') {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			try {
				WebUI.click(tObj, FailureHandling.STOP_ON_FAILURE)
				MYLOG.addSTEPPASS("Clic sur '" + tObj.getObjectId() + "'")
			} catch (Exception ex) {
				MYLOG.addSTEP("Clic sur '" + tObj.getObjectId() + "'",status)
				MYLOG.addDETAIL(ex.getMessage())
			}
		}else {
			MYLOG.addSTEPERROR("Clic sur '$name' imposible")
			MYLOG.addDETAIL(msgTO)
		}
	}


	static doubleClick(JDD myJDD, String name, String status = 'FAIL') {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			try {
				WebUI.doubleClick(tObj, FailureHandling.STOP_ON_FAILURE)
				MYLOG.addSTEPPASS("Double click sur '" + tObj.getObjectId() + "'")
			} catch (Exception ex) {
				MYLOG.addSTEP("Double click sur '" + tObj.getObjectId() + "'",status)
				MYLOG.addDETAIL(ex.getMessage())
			}
		}else {
			MYLOG.addSTEPERROR("Double click sur '$name' impossible")
			MYLOG.addDETAIL(msgTO)
		}
	}




	static scrollToElement(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			try {
				WebUI.scrollToElement(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
				MYLOG.addDEBUG("Scroll to '${tObj.getObjectId()}' OK")
			} catch (Exception ex) {
				MYLOG.addSTEPERROR("Scroll to '${tObj.getObjectId()}'")
				MYLOG.addDETAIL(ex.getMessage())
			}
		}else {
			MYLOG.addSTEPERROR("Scroll to '$name' impossible")
			MYLOG.addDETAIL(msgTO)
		}
	}




	static boolean waitForAlert(int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		try {
			WebUI.waitForAlert(timeOut, FailureHandling.STOP_ON_FAILURE)
			MYLOG.addSTEPPASS("Attendre la fenetre de confirmation")
			return true
		} catch (Exception ex) {
			MYLOG.addSTEP("Attendre la fenetre de confirmation",status)
			MYLOG.addDETAIL(ex.getMessage())
			return false
		}
	}




	static boolean acceptAlert(String status = 'FAIL') {
		try {
			WebUI.acceptAlert(FailureHandling.STOP_ON_FAILURE)
			MYLOG.addSTEPPASS("Accepter la demande de confirmation")
			return true
		} catch (Exception ex) {
			MYLOG.addSTEP("Accepter la demande de confirmation", status)
			MYLOG.addDETAIL(ex.getMessage())
			return false
		}
	}





	static String sendKeys(JDD myJDD, String name, String keys, String msg = '' , String status = 'FAIL') {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			try {
				WebUI.sendKeys(tObj, keys, FailureHandling.STOP_ON_FAILURE)
				if (msg) {
					MYLOG.addSTEPPASS(msg)
				}else {
					MYLOG.addDEBUG("Envoie touche(s) clavier '$keys' sur '${tObj.getObjectId()}'")
				}
				return null
			} catch (Exception ex) {
				if (msg) {
					MYLOG.addSTEP(msg,status)
					MYLOG.addDETAIL(ex.getMessage())
				}
				return ex.getMessage()
			}
		}else {
			MYLOG.addSTEPERROR("Envoie touche(s) clavier '$keys' sur '$name' impossible")
			MYLOG.addDETAIL(msgTO)
		}
	}








	static boolean verifyElementNotPresent(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			try {
				WebUI.verifyElementNotPresent(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
				MYLOG.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' ne soit plus présent")
				return true
			} catch (Exception ex) {
				MYLOG.addSTEP("Vérifier que '${tObj.getObjectId()}' ne soit plus présent", status)
				MYLOG.addDETAIL(ex.getMessage())
				return false
			}
		}else {
			MYLOG.addSTEPERROR("Vérifier que '$name' ne soit plus présent impossible")
			MYLOG.addDETAIL(msgTO)
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
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			try {
				WebUI.verifyElementPresent(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
				MYLOG.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' soit présent")
				return true
			}catch (Exception ex) {
				MYLOG.addSTEP("Vérifier que '${tObj.getObjectId()}' soit présent", status)
				MYLOG.addDETAIL(ex.getMessage())
				MYLOG.addDETAIL("XPATH = "+tObj.getSelectorCollection().get(SelectorMethod.XPATH))
				return false
			}
		}else {
			MYLOG.addSTEPERROR("Vérifier que '$name' soit présent impossible")
			MYLOG.addDETAIL(msgTO)
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
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			try {
				if (WebUI.verifyElementPresent(tObj, timeOut, FailureHandling.OPTIONAL)) {
					MYLOG.addSTEP("L'élément '${tObj.getObjectId()}' est présent")
					return true
				}else {
					MYLOG.addSTEP("L'élément '${tObj.getObjectId()}' n'est pas présent")
					return false
				}
			} catch (Exception ex) {
				MYLOG.addSTEPERROR("Vérifier si l'élément '$name' est présent")
				MYLOG.addDETAIL(ex.getMessage())
				return false
			}
		}else {
			MYLOG.addSTEPERROR("Vérifier si l'élément '$name' est présent impossible")
			MYLOG.addDETAIL(msgTO)
			return false
		}
	}





	static boolean verifyElementText(JDD myJDD, String name, String text=null, String status = 'FAIL')  {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			String gText = WebUI.getText(tObj)

			if (WebUI.verifyElementText(tObj, text,FailureHandling.OPTIONAL)) {
				MYLOG.addSTEPPASS("Vérification du texte '$text' sur '" + tObj.getObjectId() + "'")
				return true
			} else {
				MYLOG.addSTEP("Vérification du texte '$text' sur '" + tObj.getObjectId() + "'",status)
				MYLOG.addDETAIL("la valeur est '$gText' !")
				return false
			}
		}else {
			MYLOG.addSTEPERROR("Vérification du texte '$text' sur '$name' impossible")
			MYLOG.addDETAIL(msgTO)
		}
	} // end of def

	//




	/** attend que l'élément Web soit présent dans le DOM et visible à l'utilisateur
	 * 
	 * @param myJDD
	 * @param name
	 * @param timeOut
	 * @param status
	 * @return
	 */
	static waitForElementVisible(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {

			try {
				if (WebUI.waitForElementVisible(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)) {
					MYLOG.addSTEPPASS("Vérifier que l'élément '${tObj.getObjectId()}' soit visible")
				}else {
					MYLOG.addSTEP("Vérifier que l'élément '${tObj.getObjectId()}' soit visible KO après $timeOut seconde(s)", status)
				}
				//WebUI.verifyElementInViewport(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
			} catch (Exception ex) {
				MYLOG.addSTEP("Vérifier que l'élément '${tObj.getObjectId()}' soit visible", status)
				MYLOG.addDETAIL(ex.getMessage())
			}
		}else {
			MYLOG.addSTEPERROR("Vérifier que l'élément '$name' soit visible impossible")
			MYLOG.addDETAIL(msgTO)
			return false
		}
	} // end of def





	static verifyValue(JDD myJDD, String name, String text=null, String status = 'FAIL') {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			def val = WebUI.getAttribute(tObj, 'value')
			MYLOG.addDEBUG('val.getClass() : ' + val.getClass() + '   ' + val)
			if (val==null) {
				MYLOG.addSTEPERROR("Vérifier que la valeur de '" + name + "', soit '$text'")
				MYLOG.addDETAIL("L'attribut 'value' n'existe pas !")
			}else if (val==text) {
				MYLOG.addSTEPPASS("Vérifier que la valeur de '" + name + "', soit '$text'")
			}else if (text==my.JDDKW.getKW_NULL() && val=='') {
				MYLOG.addSTEPPASS("Vérifier que la valeur de '" + name + "', soit Null ou Vide")
			}else {
				MYLOG.addSTEP("Vérifier la valeur de '" + name+ "' KO, valeur attendue '$text', valeur du champ '" + WebUI.getAttribute(tObj, 'value') + "' !", status)
			}
		}else {
			MYLOG.addSTEPERROR("Vérifier que la valeur de '" + name + "', soit Null ou Vide impossible")
			MYLOG.addDETAIL(msgTO)
		}
	} // end of def





	static verifyOptionSelectedByValue(JDD myJDD, String name, String text=null, boolean isRegex = false, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			if (WebUI.verifyOptionSelectedByValue(tObj, text, isRegex, timeOut)) {
				MYLOG.addSTEPPASS("Vérifier que l'option '$text' de '" + tObj.getObjectId() + "' soit sélectionnée")
			}else{
				MYLOG.addSTEP("Vérifier que l'option '$text' de '" + tObj.getObjectId() + "' soit sélectionnée KO", status)
			}
		}else {
			MYLOG.addSTEPERROR("Vérifier que l'option '$text' de '$name' soit sélectionnée")
			MYLOG.addDETAIL(msgTO)
		}
	}















	static scrollAndClick(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		this.scrollToElement(myJDD, name, timeOut,status)
		this.delay(1)
		this.waitForElementVisible(myJDD, name, timeOut,status)
		this.click(myJDD, name,status)
	} // end of def






	static scrollAndDoubleClick(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		this.scrollToElement(myJDD, name, timeOut,status)
		this.delay(1)
		this.waitForElementVisible(myJDD, name, timeOut,status)
		this.doubleClick(myJDD, name,status)
	} // end of def






	static scrollAndSelectOptionByValue(JDD myJDD, String name, String text=null, boolean isRegex = true, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			if (text==null) text = myJDD.getStrData(name)
			this.scrollToElement(myJDD, name, timeOut,status)
			this.waitForElementVisible(myJDD, name, timeOut,status)
			try {
				WebUI.selectOptionByValue(tObj, text, isRegex,FailureHandling.STOP_ON_FAILURE)
				MYLOG.addSTEPPASS("Scroll et select option '$text' sur '" + tObj.getObjectId() + "'")
			} catch (Exception ex) {
				MYLOG.addSTEP("Scroll et select option '$text' sur '" + tObj.getObjectId() + "'",status)
				MYLOG.addDETAIL(ex.getMessage())
			}

		}else {
			MYLOG.addSTEPERROR("Scroll et select option '$text' sur '$name'")
			MYLOG.addDETAIL(msgTO)
		}
	} // end of def






	static scrollAndSetText(JDD myJDD, String name, String text=null, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (text==null) text = myJDD.getStrData(name)
		if (text != my.JDDKW.getKW_NULL()) {
			if (text == my.JDDKW.getKW_VIDE()) text=''
			this.scrollToElement(myJDD, name, timeOut, status)
			this.waitForElementVisible(myJDD, name, timeOut, status)
			this.setText(myJDD, name, text, status)
		}
	} // end of def






	static boolean waitAndAcceptAlert(int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (this.waitForAlert(timeOut, status)) {
			return this.acceptAlert(status)
		}else {
			return false
		}
	} // end of def





	static setDate(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (val==null) val = myJDD.getData(name)
		if ( val instanceof Date) {
			this.setText(myJDD, name, val.format(dateFormat), status)
		}else {
			MYLOG.addSTEPERROR("Saisie du texte '${val.toString()}' sur '$name'")
			MYLOG.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
	} // end of def





	static verifyDate(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		if (val==null) val = myJDD.getData(name)
		if ( val instanceof Date) {
			MYLOG.addDEBUG('val.format(dateFormat) :' +val.format(dateFormat))
			this.verifyElementText(myJDD, name, val.format(dateFormat), status)
		}else {
			MYLOG.addSTEPERROR("Vérification du texte '${val.toString()}' sur '$name'")
			MYLOG.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
	} // end of def





	static scrollWaitAndVerifyElementText(JDD myJDD, String name, String text=null, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {

		if (text==null) text = myJDD.getStrData(name)
		this.scrollToElement(myJDD, name, timeOut, status)
		this.waitAndVerifyElementText(myJDD, name, text,timeOut, status)

	} // end of def




	static boolean waitAndVerifyElementText(JDD myJDD, String name, String text=null, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL')  {

		if (text==null) text = myJDD.getStrData(name)
		this.waitForElementVisible(myJDD, name, timeOut, status)
		return this.verifyElementText(myJDD, name, text, status)
	} // end of def






	static scrollAndCheckIfNeeded(JDD myJDD, String name, String textTrue, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL')  {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			def (TestObject tObjLbl, String msgLbl) = myJDD.makeTO('Lbl'+name)
			if (tObjLbl) {
				boolean cond = myJDD.getStrData(name)==textTrue

				this.scrollToElement(myJDD, name,timeOut,status)
				this.waitForElementVisible(myJDD, name, timeOut,status)
				if (cond) {
					if (WebUI.verifyElementChecked(tObj,timeOut, FailureHandling.OPTIONAL)) {
						MYLOG.addSTEPPASS("Cocher la case à cocher '" + name + "'")
						MYLOG.addDETAIL("déjà cochée")
					}else {
						try {
							WebUI.click(tObjLbl, FailureHandling.STOP_ON_FAILURE)
							MYLOG.addSTEPPASS("Cocher la case à cocher '" + name + "'")
						} catch (Exception ex) {
							MYLOG.addSTEP("Cocher la case à cocher '" + name + "'", status)
							MYLOG.addDETAIL(ex.getMessage())
						}
					}
				}else {
					if (WebUI.verifyElementNotChecked(tObj,timeOut, FailureHandling.OPTIONAL)) {
						MYLOG.addSTEPPASS("Décocher la case à cocher '" + name + "'")
						MYLOG.addDETAIL("déjà décochée")
					}else {
						try {
							WebUI.click(tObjLbl, FailureHandling.STOP_ON_FAILURE)
							MYLOG.addSTEPPASS("Décocher la case à cocher '" + name + "'")
						} catch (Exception ex) {
							MYLOG.addSTEP("Décocher la case à cocher '" + name + "'", status)
							MYLOG.addDETAIL(ex.getMessage())
						}
					}
				}
			}else {
				MYLOG.addSTEPERROR("Label Case à cocher 'Lbl$name'")
				MYLOG.addDETAIL(msgTO)
			}
		}else {
			MYLOG.addSTEPERROR("Case à cocher '$name'")
			MYLOG.addDETAIL(msgTO)
		}
	} // end of def






	static verifyElementCheckedOrNot(JDD myJDD, String name, String textTrue, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			boolean cond = myJDD.getStrData(name)==textTrue

			this.scrollToElement(myJDD, name,timeOut,status)
			this.waitForElementVisible(myJDD, name, timeOut,status)
			if (cond) {
				if (WebUI.verifyElementChecked(tObj,timeOut, FailureHandling.OPTIONAL)) {
					MYLOG.addSTEPPASS("Vérifier que '${tObj.getObjectId()}'soit coché")
				}else {
					MYLOG.addSTEPFAIL("Vérifier que '${tObj.getObjectId()}'soit coché")
					MYLOG.addDETAIL('La case est décochée !')
				}
			}else {
				if (WebUI.verifyElementNotChecked(tObj,timeOut, FailureHandling.OPTIONAL)) {
					MYLOG.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' soit décoché")
				}else {
					MYLOG.addSTEPFAIL("Vérifier que '${tObj.getObjectId()}' soit décoché")
					MYLOG.addDETAIL('La case est cochée !')
				}
			}
		}else {
			MYLOG.addSTEPERROR("Vérifier état case à cocher '$name'")
			MYLOG.addDETAIL(msgTO)
		}

	}





	static getCheckBoxImgStatus(JDD myJDD, String name)  {
		def (TestObject tObj, String msgTO) = myJDD.makeTO(name)
		if (tObj) {
			if (WebUI.getAttribute(tObj, 'src').endsWith('133.gif')) {
				return true
			}else if (WebUI.getAttribute(tObj, 'src').endsWith('134.gif')) {
				return false
			}else {
				MYLOG.addSTEPERROR("Vérifier état case à cocher '$name'")
				MYLOG.addDETAIL("L'attribut src de l'objet " + name + " n'est pas conforme, la valeur est : " + WebUI.getAttribute(tObj, 'src'))
				return null
			}
		}else {
			MYLOG.addSTEPERROR("Vérifier état case à cocher '$name'")
			MYLOG.addDETAIL(msgTO)
		}
	}





	static verifyCheckBoxImgChecked(JDD myJDD, String name, String status = 'FAIL')  {
		def etat = this.getCheckBoxImgStatus(myJDD, name)
		if (etat ==null) {
			// l'erreur est déjà remontée par getCheckBoxImgStatus
		}else if (etat) {
			MYLOG.addSTEPPASS("Vérifier que la case à cocher (img) '" + name + "' soit cochée")
		}else {
			MYLOG.addSTEP("Vérifier que la case à cocher (img) '" + name + "' soit cochée", status)
		}

	}





	static verifyCheckBoxImgNotChecked(JDD myJDD, String name, String status = 'FAIL')  {
		def etat = this.getCheckBoxImgStatus(myJDD, name)
		if (etat ==null) {
			// l'erreur est déjà remontée par getCheckBoxImgStatus
		}else if (!etat) {
			MYLOG.addSTEPPASS("Vérifier que la case à cocher (img) '" + name + "' soit cochée")
		}else {
			MYLOG.addSTEP("Vérifier que la case à cocher (img) '" + name + "' soit cochée", status)
		}
	}





	static verifyImgCheckedOrNot(JDD myJDD, String name, String textTrue, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		boolean cond = myJDD.getStrData(name)==textTrue
		if (cond) {
			this.verifyCheckBoxImgChecked(myJDD, name, status)
		}else {
			this.verifyCheckBoxImgNotChecked(myJDD, name, status)
		}
	}





	static verifyImg(JDD myJDD, String name, boolean cond, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		if (cond) {
			this.verifyElementPresent(myJDD, name, timeOut, status)
		}
	}





	static searchWithHelper(JDD myJDD, String name , String btnXpath = '' , String inputName = '' ){

		MYLOG.addSUBSTEP("Saisie de $name en utilisant l'assistant de recherche")

		String val = myJDD.getStrData(name)

		if (btnXpath=='') {
			btnXpath = "//a[@id='Btn$name']/i"
		}

		if (inputName=='') inputName = "SEARCH_$name"



		String inputXpath 	= "//input[@name='$inputName']"
		String tdXpath 		= "//div[@id='v-dbtdhtmlx1']/table/tbody//td[3][text()='$val']"

		myJDD.xpathTO.put('btnSearch', btnXpath)
		myJDD.xpathTO.put('inputSearch', inputXpath)
		myJDD.xpathTO.put('tdSearch', tdXpath)

		this.scrollAndClick(myJDD,'btnSearch')

		WebUI.switchToWindowIndex('1')

		this.setText(myJDD,'inputSearch', myJDD.getStrData(name))

		'mise à jour dynamique du xpath'
		this.scrollWaitAndVerifyElementText(myJDD,'tdSearch', myJDD.getStrData(name))

		this.click(myJDD,'tdSearch')

		WebUI.switchToWindowIndex('0')

	}


} // end of class