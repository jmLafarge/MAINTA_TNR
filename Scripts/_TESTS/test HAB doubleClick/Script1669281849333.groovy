import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

//click object using Javascript
def clickUsingJS(TestObject to, int timeout) {
	WebDriver driver = DriverFactory.getWebDriver()
	WebElement element = WebUiCommonHelper.findWebElement(to, timeout)
	JavascriptExecutor executor = ((driver) as JavascriptExecutor)
	executor.executeScript('arguments[0].click()', element)
}

//click object using Javascript
def doubleClickUsingJS(TestObject to, int timeout) {
	WebDriver driver = DriverFactory.getWebDriver()
	WebElement element = WebUiCommonHelper.findWebElement(to, timeout)
	JavascriptExecutor executor = ((driver) as JavascriptExecutor)
	executor.executeScript('arguments[0].double_click()', element)
}

'pour tester seul --> à supprimer'
if (GlobalVariable.TC_CONNEXION) {
	WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SECURITE/AD.SEC.001.FON.01 Ouvrir session'), [:])
}

CustomKeywords.'my.Fct_url_ecran.goToURL_RUD_and_checkCartridge'('acteur', 'RO.ACT.003.HAB.AJT.01')

CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/a_Habilitation'))

//clickUsingJS(findTestObject('1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/a_Habilitation'),1)

WebUI.delay(1)

WebUI.scrollToPosition(0, 0)

WebUI.delay(1)

WebUI.verifyElementPresent(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Habilitation', [('textHAB') : 'ATEX1']),2)

WebUI.delay(1)

//CustomKeywords.'my.extend_KW.scrollAndDoubleClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Debut_Habilitation',
//		[('textHAB') : 'ATEX1']))

WebUI.waitForElementClickable(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Debut_Habilitation', [('textHAB') : 'ATEX1']),2)

//WebUI.doubleClick(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Debut_Habilitation', [('textHAB') : 'ATEX1']))
doubleClickUsingJS(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Debut_Habilitation', [('textHAB') : 'ATEX1']),1)


WebUI.delay(1)

WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/textarea_Debut_Habilitation', [('textHAB') : 'ATEX1']),
	'12/01/2023')

