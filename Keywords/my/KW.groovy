package my

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import org.openqa.selenium.Keys as Keys

import internal.GlobalVariable

/**
 * Personaliser et de regrouper certaines actions WebUI
 * 
 * @author X1009638
 *
 */
class KW {

	
	/**
	 * @param second
	 * @return
	 */
	static delay(Number second) {

		my.Log.addDEBUG("Delay $second second(s)")
		WebUI.delay(second)
	}


	/**
	 * @param url
	 * @return
	 */
	static openBrowser(String url){
		try {
			WebUI.openBrowser(url, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Ouverture du navigateur à l'URL : $url")
			def browser = my.Tools.getBrowserAndVersion()
			my.Log.addSUBSTEP("*********************************************************************")
			my.Log.addSUBSTEP("  Nom du navigateur".padRight(26) + browser.NAME)
			my.Log.addSUBSTEP("  Version du navigateur".padRight(26) + browser.VERSION)
			my.Log.addSUBSTEP("*********************************************************************")
			my.Result.addBrowserInfo()
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


	static closeBrowser(){
		try {
			WebUI.closeBrowser()
			my.Log.addSTEPPASS("Fermeture du navigateur")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Fermeture du navigateur")
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



	static setText(JDD myJDD, String name, String text=null, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		if (text==null) text = myJDD.getStrData(name)
		try {
			WebUI.setText(tObj, text, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Saisie du texte '$text' sur '" + tObj.getObjectId() + "'")
		} catch (Exception ex) {
			my.Log.addSTEP("Saisie du texte '$text' sur '" + tObj.getObjectId() + "'", status)
			my.Log.addDETAIL(ex.getMessage())
		}
	}




	static setEncryptedText(JDD myJDD, String name, String text=null) {
		TestObject tObj = myJDD.makeTO(name)
		if (text==null) text = myJDD.getStrData(name)
		try {
			WebUI.setEncryptedText(tObj, text, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Saisie du mot de passe sur " + tObj.getObjectId() + "'")
		} catch (Exception ex) {
			my.Log.addSTEPFAIL("Saisie du mot de passe sur '" + tObj.getObjectId() + "'")
			my.Log.addDETAIL(ex.getMessage())
		}
	}



	static click(JDD myJDD, String name, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		try {
			WebUI.click(tObj, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Clic sur '" + tObj.getObjectId() + "'")
		} catch (Exception ex) {
			my.Log.addSTEP("Clic sur '" + tObj.getObjectId() + "'",status)
			my.Log.addDETAIL(ex.getMessage())
		}
	}


	static doubleClick(JDD myJDD, String name, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		try {
			WebUI.doubleClick(tObj, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Double click sur '" + tObj.getObjectId() + "'")
		} catch (Exception ex) {
			my.Log.addSTEP("Double click sur '" + tObj.getObjectId() + "'",status)
			my.Log.addDETAIL(ex.getMessage())
		}
	}


	static scrollToElement(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		try {
			WebUI.scrollToElement(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addDEBUG("Scroll to '${tObj.getObjectId()}' OK")
		} catch (Exception ex) {
			my.Log.addSTEP("Scroll to '${tObj.getObjectId()}'", status)
			my.Log.addDETAIL(ex.getMessage())
		}
	}


	static boolean waitForAlert(int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		try {
			WebUI.waitForAlert(timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Attendre la fenetre de confirmation")
			return true
		} catch (Exception ex) {
			my.Log.addSTEP("Attendre la fenetre de confirmation",status)
			my.Log.addDETAIL(ex.getMessage())
			return false
		}
	}


	static boolean acceptAlert(String status = 'FAIL') {
		try {
			WebUI.acceptAlert(FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Accepter la demande de confirmation")
			return true
		} catch (Exception ex) {
			my.Log.addSTEP("Accepter la demande de confirmation", status)
			my.Log.addDETAIL(ex.getMessage())
			return false
		}
	}



	static String sendKeys(JDD myJDD, String name, String keys, String msg = '' , String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
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
				my.Log.addSTEP(msg,status)
				my.Log.addDETAIL(ex.getMessage())
			}
			return ex.getMessage()
		}
	}




	static verifyElementChecked(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		try {
			WebUI.verifyElementChecked(tObj,timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérifier que '${tObj.getObjectId()}'soit coché")
		} catch (Exception ex) {
			my.Log.addSTEP("Vérifier que '${tObj.getObjectId()}' soit coché", status)
			my.Log.addDETAIL(ex.getMessage())
		}
	}


	static boolean verifyElementNotPresent(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		try {
			WebUI.verifyElementNotPresent(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' ne soit plus présent")
			return true
		} catch (Exception ex) {
			my.Log.addSTEP("Vérifier que '${tObj.getObjectId()}' ne soit plus présent", status)
			my.Log.addDETAIL(ex.getMessage())
			return false
		}
	}





	static boolean verifyElementPresent(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		try {
			WebUI.verifyElementPresent(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' soit présent")
			return true
		} catch (Exception ex) {
			my.Log.addSTEP("Vérifier que '${tObj.getObjectId()}' soit présent", status)
			my.Log.addDETAIL(ex.getMessage())
			return false
		}
	}



	static boolean verifyElementText(JDD myJDD, String name, String text=null, String status = 'FAIL')  {
		TestObject tObj = myJDD.makeTO(name)
		if (text==null) text = myJDD.getStrData(name)
		String gText = WebUI.getText(tObj)
		try {
			WebUI.verifyElementText(tObj, text,FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérification du texte '$text' sur '" + tObj.getObjectId() + "'")
			return true
		} catch (Exception ex) {
			my.Log.addSTEP("Vérification du texte '$text' sur '" + tObj.getObjectId() + "' KO, la valeur est '$gText' !", status)
			my.Log.addDETAIL(ex.getMessage())
			return false
		}
	} // end of def



	static verifyElementNotChecked(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		try {
			WebUI.verifyElementNotChecked(tObj,timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' soit décoché")
		} catch (Exception ex) {
			my.Log.addSTEP("Vérifier que '${tObj.getObjectId()}' soit décoché", status)
			my.Log.addDETAIL(ex.getMessage())
		}
	}




	static waitForElementClickable(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		try {
			WebUI.waitForElementClickable(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addDEBUG("'${tObj.getObjectId()}' est clickable")
		} catch (Exception ex) {
			my.Log.addSTEP("'${tObj.getObjectId()}' n'est pas clickable", status)
			my.Log.addDETAIL(ex.getMessage())
		}
	} // end of def



	static waitForElementVisible(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		try {
			WebUI.waitForElementVisible(tObj, timeOut, FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Vérifier que l'élément '${tObj.getObjectId()}' soit visible")
		} catch (Exception ex) {
			my.Log.addSTEP("Vérifier que l'élément '${tObj.getObjectId()}' soit visible", status)
			my.Log.addDETAIL(ex.getMessage())
		}
	} // end of def




	static verifyValue(JDD myJDD, String name, String text=null, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		if (text==null) text = myJDD.getStrData(name)
		def val = WebUI.getAttribute(tObj, 'value')
		my.Log.addDEBUG('val.getClass() : ' + val.getClass() + '   ' + val)
		if (val==null) {
			my.Log.addERROR("L'attribut 'value' n'existe pas !")
		}
		if (val==text) {
			my.Log.addSTEPPASS("Vérifier que la valeur de '" + name + "', soit '$text'")
		}else if (text==my.JDDKW.getKW_NULL() && val=='') {
			my.Log.addSTEPPASS("Vérifier que la valeur de '" + name + "', soit Null ou Vide")
		}else {
			my.Log.addSTEP("Vérifier la valeur de '" + name+ "' KO, valeur attendue '$text', valeur du champ '" + WebUI.getAttribute(tObj, 'value') + "' !", status)
		}
	} // end of def


	static verifyOptionSelectedByValue(JDD myJDD, String name, String text=null, boolean isRegex = false, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		if (text==null) text = myJDD.getStrData(name)
		if (WebUI.verifyOptionSelectedByValue(tObj, text, isRegex, timeOut)) {
			my.Log.addSTEPPASS("Vérifier que l'option '$text' de '" + tObj.getObjectId() + "' soit sélectionnée")
		}else{
			my.Log.addSTEP("Vérifier que l'option '$text' de '" + tObj.getObjectId() + "' soit sélectionnée KO", status)
		}
	}



	static scrollAndClick(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		this.scrollToElement(myJDD, name, timeOut,status)
		this.waitForElementClickable(myJDD, name, timeOut,status)
		this.click(myJDD, name,status)
	} // end of def




	static scrollAndDoubleClick(JDD myJDD, String name, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		this.scrollToElement(myJDD, name, timeOut,status)
		this.waitForElementVisible(myJDD, name, timeOut,status)
		this.doubleClick(myJDD, name,status)
	} // end of def




	static scrollAndSelectOptionByValue(JDD myJDD, String name, String text=null, boolean isRegex = true, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		if (text==null) text = myJDD.getStrData(name)
		this.scrollToElement(myJDD, name, timeOut,status)
		this.waitForElementVisible(myJDD, name, timeOut,status)
		try {
			WebUI.selectOptionByValue(tObj, text, isRegex,FailureHandling.STOP_ON_FAILURE)
			my.Log.addSTEPPASS("Scroll et select option '$text' sur '" + tObj.getObjectId() + "'")
		} catch (Exception ex) {
			my.Log.addSTEP("Scroll et select option '$text' sur '" + tObj.getObjectId() + "'",status)
			my.Log.addDETAIL(ex.getMessage())
		}
	} // end of def




	static scrollAndSetText(JDD myJDD, String name, String text=null, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
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
		TestObject tObj = myJDD.makeTO(name)
		if (val==null) val = myJDD.getData(name)
		if ( val instanceof Date) {
			this.setText(myJDD, name, val.format(dateFormat), status)
		}else {
			my.Log.addERROR('Erreur de JDD de ' + tObj.getObjectId() + ', la valeur ' + val.toString() + "' n'est pas une date ! getClass = " + val.getClass())
		}
	} // end of def



	static verifyDate(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		TestObject tObj = myJDD.makeTO(name)
		if (val==null) val = myJDD.getData(name)
		if ( val instanceof Date) {
			my.Log.addDEBUG('val.format(dateFormat) :' +val.format(dateFormat))
			this.verifyElementText(myJDD, name, val.format(dateFormat), status)
		}else {
			my.Log.addERROR('Erreur de JDD de ' + tObj.getObjectId() + ', la valeur ' + val.toString() + "' n'est pas une date ! getClass = " + val.getClass())
		}
	} // end of def



	static scrollWaitAndVerifyElementText(JDD myJDD, String name, String text=null, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		if (text==null) text = myJDD.getStrData(name)
		this.scrollToElement(myJDD, name, timeOut, status)
		this.waitAndVerifyElementText(myJDD, name, text,timeOut, status)
	} // end of def



	static boolean waitAndVerifyElementText(JDD myJDD, String name, String text=null, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL')  {
		TestObject tObj = myJDD.makeTO(name)
		if (text==null) text = myJDD.getStrData(name)
		this.waitForElementVisible(myJDD, name, timeOut, status)
		return this.verifyElementText(myJDD, name, text, status)
	} // end of def




	static scrollAndCheckIfNeeded(JDD myJDD, String name, String textTrue, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL')  {
		TestObject tObj = myJDD.makeTO(name)
		TestObject tObjLbl = myJDD.makeTO('Lbl'+name)

		boolean cond = myJDD.getStrData(name)==textTrue

		this.scrollToElement(myJDD, name,timeOut,status)
		boolean etat = WebUI.getAttribute(tObj, 'checked')=='yes'
		my.Log.addDEBUG("etat : $etat")
		if (cond) {
			if (etat) {
				my.Log.addSTEPPASS("Cocher la case à cocher '" + name + "'")
				my.Log.addDETAIL("déjà cochée")
			}else {
				try {
					WebUI.click(tObjLbl, FailureHandling.STOP_ON_FAILURE)
					my.Log.addSTEPPASS("Cocher la case à cocher '" + name + "'")
				} catch (Exception ex) {
					my.Log.addSTEP("Cocher la case à cocher '" + name + "'", status)
					my.Log.addDETAIL(ex.getMessage())
				}
			}
		}else {
			if (!etat) {
				my.Log.addSTEPPASS("Décocher la case à cocher '" + name + "'")
				my.Log.addDETAIL("déjà décochée")
			}else {
				try {
					WebUI.click(tObjLbl, FailureHandling.STOP_ON_FAILURE)
					my.Log.addSTEPPASS("Décocher la case à cocher '" + name + "'")
				} catch (Exception ex) {
					my.Log.addSTEP("Décocher la case à cocher '" + name + "'", status)
					my.Log.addDETAIL(ex.getMessage())
				}
			}
		}
	} // end of def



	static verifyElementCheckedOrNot(JDD myJDD, String name, boolean cond, int timeOut = GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TestObject tObj = myJDD.makeTO(name)
		if (cond) {
			this.verifyElementChecked(myJDD, name,timeOut, status)
		}else {
			this.verifyElementNotChecked(myJDD, name,timeOut, status)
		}
	}




	static getCheckBoxImgStatus(JDD myJDD, String name)  {
		TestObject tObj = myJDD.makeTO(name)
		if (WebUI.getAttribute(tObj, 'src').endsWith('133.gif')) {
			return true
		}else if (WebUI.getAttribute(tObj, 'src').endsWith('134.gif')) {
			return false
		}else {
			my.Log.addERROR("L'attribut src de l'objet " + name + " n'est pas conforme, la valeur est : " + WebUI.getAttribute(tObj, 'src'))
			return null
		}
	}


	static verifyCheckBoxImgChecked(JDD myJDD, String name, String status = 'FAIL')  {
		def etat = this.getCheckBoxImgStatus(myJDD, name)
		if (etat ==null) {
			// l'erreur est déjà remontée par getCheckBoxImgStatus
		}else if (etat) {
			my.Log.addSTEPPASS("Vérifier que la case à cocher (img) '" + name + "' soit cochée")
		}else {
			my.Log.addSTEP("Vérifier que la case à cocher (img) '" + name + "' soit cochée", status)
		}

	}


	static verifyCheckBoxImgNotChecked(JDD myJDD, String name, String status = 'FAIL')  {
		def etat = this.getCheckBoxImgStatus(myJDD, name)
		if (etat ==null) {
			// l'erreur est déjà remontée par getCheckBoxImgStatus
		}else if (!etat) {
			my.Log.addSTEPPASS("Vérifier que la case à cocher (img) '" + name + "' soit cochée")
		}else {
			my.Log.addSTEP("Vérifier que la case à cocher (img) '" + name + "' soit cochée", status)
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

		my.Log.addSUBSTEP("Saisie de $name en utilisant l'assistant de recherche")

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