package my
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable



class Fct_url_ecran {

	// Naviguer vers les URL et controler les écrans

	/**
	 * Permet de calculer l'url pour acceder directement au SCRUD des objets
	 * et de vérifier l'écran correspondant
	 */

	// map des objets / code fonction
	Map mfct = [
		acteur:'21',
		article:'79',
		organisation:'233',
		emplacement:'5']



	/**
	 * Vérifier écran Grille / recherche
	 */
	@Keyword
	def verifierEcranGrille(String fct, int timeOut = GlobalVariable.TIMEOUT) {
		String code = "E" + mfct[fct]
		KeywordUtil.logInfo("Vérification écran : " + code)
		WebUI.scrollToPosition(0, 0)
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/0 - COMMUN/Cartridge/a_toggle'))
		WebUI.delay(1)
		WebUI.waitForElementVisible(findTestObject('Object Repository/0 - COMMUN/Cartridge/span_ecran'),timeOut)
		WebUI.verifyElementText(findTestObject('Object Repository/0 - COMMUN/Cartridge/span_ecran'), code,FailureHandling.OPTIONAL)
	} // end of def

	/**
	 * Vérifier écran Résultat
	 */
	@Keyword
	def verifierEcranResultat(String fct, int timeOut = GlobalVariable.TIMEOUT) {
		String code = mfct[fct]
		KeywordUtil.logInfo("Vérification écran : " + code)
		WebUI.scrollToPosition(0, 0)
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/0 - COMMUN/Cartridge/a_toggle'))
		WebUI.delay(1)
		WebUI.waitForElementVisible(findTestObject('Object Repository/0 - COMMUN/Cartridge/span_ecran'), timeOut)
		WebUI.verifyElementText(findTestObject('Object Repository/0 - COMMUN/Cartridge/span_ecran'), code,FailureHandling.OPTIONAL)
	} // end of def

	/**
	 * Vérifier écran Création
	 */
	@Keyword
	def verifierEcranCreation(String fct, int timeOut = GlobalVariable.TIMEOUT) {
		String code = mfct[fct] + " - Création"
		KeywordUtil.logInfo("Vérification écran : " + code)
		WebUI.scrollToPosition(0, 0)
		WebUI.delay(1)
		WebUI.click(findTestObject('Object Repository/0 - COMMUN/Cartridge/a_toggle'))
		WebUI.delay(1)
		WebUI.waitForElementVisible(findTestObject('Object Repository/0 - COMMUN/Cartridge/span_ecran'), timeOut)
		WebUI.verifyElementText(findTestObject('Object Repository/0 - COMMUN/Cartridge/span_ecran'), code,FailureHandling.OPTIONAL)
	} // end of def

	/**
	 * Vérifier écran Lecture / modification / suppression
	 */
	@Keyword
	def verifierEcranRUD(String fct, id, int timeOut = GlobalVariable.TIMEOUT) {
		def extKW = new extend_KW()
		String code = mfct[fct] + " - Consultation ou modification"
		KeywordUtil.logInfo("Vérification écran : " + code)
		WebUI.scrollToPosition(0, 0)
		WebUI.delay(1)
		extKW.waitAndVerifyText(findTestObject('Object Repository/0 - COMMUN/Cartridge/span_selection'), id,timeOut)
		WebUI.click(findTestObject('Object Repository/0 - COMMUN/Cartridge/a_toggle'))
		WebUI.delay(1)
		WebUI.waitForElementVisible(findTestObject('Object Repository/0 - COMMUN/Cartridge/span_ecran'), timeOut)
		WebUI.verifyElementText(findTestObject('Object Repository/0 - COMMUN/Cartridge/span_ecran'), code,FailureHandling.OPTIONAL)
	} // end of def






	/**
	 * Aller à l'url de la Grille / recherche
	 */
	@Keyword
	def goToURL_Grille(String fct) {
		String url = GlobalVariable.BASE_URL + "E" + mfct[fct] + "?"
		KeywordUtil.logInfo("Got to : " + url)
		WebUI.navigateToUrl(url)
	} // end of def


	/**
	 * Aller à l'url de création
	 */
	@Keyword
	def goToURL_Creation(String fct) {
		String url = GlobalVariable.BASE_URL + "FormE" + mfct[fct] + "?"
		KeywordUtil.logInfo("Got to : " + url)
		WebUI.navigateToUrl(url)
	} // end of def

	/**
	 * Aller à l'url de Lecture / modification / suppression
	 */
	@Keyword
	def goToURL_RUD(String fct, String id) {
		String url = GlobalVariable.BASE_URL + "FormE" + mfct[fct] + "?" + "ID1=" + id
		KeywordUtil.logInfo("Got to : " + url)
		WebUI.navigateToUrl(url)
	} // end of def





	/**
	 * Aller à l'url de Lecture / modification / suppression et vérifier le cartouche
	 */
	@Keyword
	def goToURL_RUD_and_checkCartridge(String fct, String id, int timeOut = GlobalVariable.TIMEOUT) {
		goToURL_RUD(fct, id)
		verifierEcranRUD(fct, id,timeOut)
	} // end of def

	/**
	 * Aller à l'url de Grille / recherche et vérifier le cartouche
	 */
	@Keyword
	def goToURL_Grille_and_checkCartridge(String fct, int timeOut = GlobalVariable.TIMEOUT) {
		goToURL_Grille(fct)
		verifierEcranGrille(fct,timeOut)
	} // end of def

	/**
	 * Aller à l'url de création et vérifier le cartouche
	 */
	@Keyword
	def goToURL_Creation_and_checkCartridge(String fct, int timeOut = GlobalVariable.TIMEOUT) {
		goToURL_Creation(fct)
		verifierEcranCreation(fct,timeOut)
	} // end of def

} // end of class