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
	 * verifyValue				(TestObject tObj, String val,int timeOut = GlobalVariable.TIMEOUT)
	 * scrollAndClick			(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT)
	 * scrollAndDoubleClick		(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT)
	 * scrollWaitAndVerifyText	(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT)
	 * scrollAndSetText			(TestObject tObj, String text ='', int timeOut = GlobalVariable.TIMEOUT)
	 * setText					(TestObject tObj, String text ='')
	 * clic						(TestObject tObj)
	 * acceptAlert				()
	 * setDate					(TestObject tObj, def val, String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT)
	 * verifyDate				(TestObject tObj, def val, String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT )
	 * waitAndVerifyText		(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT)
	 * verifyElementText		(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT)
	 * 
	 * 
	 * 
	 */





	/**
	 *
	 * @param
	 * @return
	 */
	@Keyword
	static verifyValue(TestObject tObj, String val,int timeOut = GlobalVariable.TIMEOUT) {

		if (WebUI.verifyElementAttributeValue(tObj,'Value',val,timeOut)) {
			my.Log.addSTEPPASS("Vérifier la valeur de '" + tObj.getObjectId() + "', valeur='$val'")
		}else {
			my.Log.addSTEPFAIL("Vérification de la valeur='$val' sur '" + tObj.getObjectId() + "' KO, la valeur est '" + WebUI.getAttribute(tObj.getObjectId(), 'Value') + "' !")
		}
	} // end of def



	/**
	 *
	 * @param
	 * @return
	 */
	@Keyword
	static scrollAndClick(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {

		my.Log.addSTEP("Scroll et clic sur '" + tObj.getObjectId() + "'")

		WebUI.scrollToElement(tObj, timeOut)
		this.waitForElementClickable(tObj, timeOut)
		WebUI.click(tObj)
	} // end of def



	/**
	 *
	 * @param
	 * @return
	 */
	@Keyword
	static waitForElementClickable(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {

		if (WebUI.waitForElementClickable(tObj, timeOut)) {
			my.Log.addDEBUG("'${tObj.getObjectId()}' est clickable")
		}else {
			my.Log.addDETAILFAIL("'${tObj.getObjectId()}' n'est pas clickable")
		}
	} // end of def
	
	

	/**
	 *
	 * @param
	 * @return
	 */
	@Keyword
	static scrollAndDoubleClick(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {

		my.Log.addSTEP("Scroll et double-clic sur '" + tObj.getObjectId() + "'")

		WebUI.scrollToElement(tObj, timeOut)
		this.waitForElementVisible(tObj, timeOut)
		WebUI.doubleClick(tObj)
	} // end of def





	/**
	 *
	 * @param
	 * @return
	 */
	@Keyword
	static waitForElementVisible(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {

		if (WebUI.waitForElementVisible(tObj, timeOut)) {
			my.Log.addDEBUG("'${tObj.getObjectId()}' est visible")
		}else {
			my.Log.addDETAILFAIL("'${tObj.getObjectId()}' n'est pas visible")
		}
	} // end of def

	/**
	 *
	 * @param TO
	 * @param text
	 * @param timeOut
	 * @return
	 */

	static scrollAndSelectOptionByValue(TestObject tObj, String val, boolean isRegex = true, int timeOut = GlobalVariable.TIMEOUT) {

		my.Log.addSTEP("Scroll et select option '$val' sur '" + tObj.getObjectId() + "'")

		WebUI.scrollToElement(tObj, timeOut)
		this.waitForElementVisible(tObj, timeOut)
		WebUI.selectOptionByValue(tObj, val, isRegex)

	} // end of def



	/**
	 *
	 * @param TO
	 * @param text
	 * @param timeOut
	 * @return
	 */

	static scrollAndSetText(TestObject tObj, String text ='', int timeOut = GlobalVariable.TIMEOUT) {

		my.Log.addSTEP("Scroll et saisie du texte '$text' sur '" + tObj.getObjectId() + "'")

		WebUI.scrollToElement(tObj, timeOut)
		this.waitForElementVisible(tObj, timeOut)
		WebUI.setText(tObj, text)

	} // end of def




	/**
	 *
	 * @param TO
	 * @param text
	 * @param timeOut
	 * @return
	 */

	static setText(TestObject tObj, String text ='') {

		my.Log.addSTEP("Saisie du texte '$text' sur '" + tObj.getObjectId() + "'")
		WebUI.setText(tObj, text)

	} // end of def


	/**
	 *
	 * @param TO
	 * @param text
	 * @param timeOut
	 * @return
	 */

	static clic(TestObject tObj) {

		my.Log.addSTEP("Clic sur '" + tObj.getObjectId() + "'")
		WebUI.click(tObj)

	} // end of def


	static boolean waitAndAcceptAlert() {

		if (WebUI.waitForAlert(GlobalVariable.TIMEOUT)) {
			WebUI.acceptAlert()
			my.Log.addSTEPPASS("Popup 'Alert' acceptée")
			return true
		}else {
			my.Log.addSTEPFAIL("Pas de popup 'Alert' !")
			return false
		}

	} // end of def


	/**
	 *
	 * @param TO
	 * @param valeur
	 * @param dateFormat	: optional
	 * @param timeOut		: optional
	 * @return
	 */

	static setDate(TestObject tObj, def val, String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT) {

		if ( val instanceof Date) {
			my.Log.addSTEP("Saisie de la date '" + val.format(dateFormat) + "' sur '" + tObj.getObjectId() + "'")
			WebUI.setText(tObj, val.format(dateFormat))
		}else {
			my.Log.addERROR('Erreur de JDD de ' + tObj.getObjectId() + ', la valeur ' + val.toString() + "' n'est pas une date ! getClass = " + val.getClass())
		}

	} // end of def


	/**
	 * Parce que verifyElementText n'a pas de TIMEOUT
	 * @param
	 * @return
	 */
	@Keyword
	static verifyDate(TestObject tObj, def val, String dateFormat = 'dd/MM/yyyy', int timeOut = GlobalVariable.TIMEOUT )  {

		if ( val instanceof Date) {
			my.Log.addDEBUG('val.format(dateFormat) :' +val.format(dateFormat))
			this.verifyElementText(tObj, val.format(dateFormat))
		}else {
			my.Log.addERROR('Erreur de JDD de ' + tObj.getObjectId() + ', la valeur ' + val.toString() + "' n'est pas une date ! getClass = " + val.getClass())
		}

	} // end of def



	/**
	 * Parce que verifyElementText n'a pas de TIMEOUT
	 * @param
	 * @return
	 */
	@Keyword
	static scrollWaitAndVerifyText(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT) {

		WebUI.scrollToElement(tObj, timeOut)
		this.waitAndVerifyText(tObj, text,timeOut)

	} // end of def



	/**
	 * Parce que verifyElementText n'a pas de TIMEOUT
	 * @param
	 * @return
	 */
	@Keyword
	static waitAndVerifyText(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT)  {

		this.waitForElementVisible(tObj, timeOut)
		this.verifyElementText(tObj, text)
	} // end of def


	/**
	 * 
	 * @param
	 * @return
	 */
	@Keyword
	static verifyElementText(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT )  {
		//WebUI.verifyElementText(tObj, text, FailureHandling.OPTIONAL)
		if (WebUI.verifyElementText(tObj, text)) {
			my.Log.addSTEPPASS("Vérification du texte='$text' sur '" + tObj.getObjectId() + "'")
		}else {
			//my.Log.addSTEPFAIL("Vérification du texte='$text' sur '" + tObj.getObjectId() + "' KO, la valeur est '" + WebUI.getText(tObj.getObjectId()) + "' !")
			my.Log.addSTEPFAIL("Vérification du texte='$text' sur '" + tObj.getObjectId() + "' KO")
		}

	} // end of def


	/**
	 * @param
	 * @return
	 */
	@Keyword
	static scrollAndClickIfNeeded(TestObject tObj, boolean cond, int timeOut = GlobalVariable.TIMEOUT)  {
		WebUI.scrollToElement(tObj,timeOut)
		boolean etat = WebUI.verifyElementChecked(tObj, timeOut, FailureHandling.OPTIONAL)
		if (etat && !cond) {
			my.Log.addSTEP("Scroll et uncheck '" + tObj.getObjectId() + "'")
			WebUI.sendKeys(tObj, Keys.chord(Keys.SPACE))
		}else if (!etat && cond){
			my.Log.addSTEP("Scroll et check '" + tObj.getObjectId() + "'")
			WebUI.sendKeys(tObj, Keys.chord(Keys.SPACE))
		}
	} // end of def


} // end of class