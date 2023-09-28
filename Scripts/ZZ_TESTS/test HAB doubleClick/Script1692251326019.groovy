import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import tnrWebUI.*
import tnrWebUI.* as GlobalVariable
import tnrResultManager.TNRResult

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
	WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SEC SECURITE/AD.SEC.001.FON.01 Ouvrir session - mot de passe valide'), [:])
}

CustomKeywords.'STEP.goToURLReadUpdateDelete_and_checkCartridge'('acteur', 'RO.ACT.003.HAB.AJT.01')

CustomKeywords.'KW.click'(findTestObject('null'))

//clickUsingJS(findTestObject('null'),1)

WUI.delay(1000)

STEP.scrollToPosition( 0, 0)

WebUI.verifyElementPresent(findTestObject('null', [('textHAB') : 'ATEX1']),2)

WUI.delay(1000)

//WebUI.setText(findTestObject('null', [('textHAB') : 'ATEX1']),'01/01/2022')

//WebUI.doubleClick(findTestObject('null', [('textHAB') : 'ATEX1']))
//WebUI.click(findTestObject('null', [('textHAB') : 'ATEX1']))
//WUI.delay(1000)
WebUI.sendKeys(findTestObject('null', [('textHAB') : 'ATEX1']), Keys.chord(
	Keys.F2))

/*
println '********************'
println '********************'
println WebUI.getText(findTestObject('null', [('textHAB') : 'ATEX1']))
println '********************'
println '********************'
*/


/*
CustomKeywords.'KW.doubleClick'(findTestObject('null',
		[('textHAB') : 'ATEX1']))

WebUI.waitForElementClickable(findTestObject('null', [('textHAB') : 'ATEX1']),2)

WebUI.doubleClick(findTestObject('null', [('textHAB') : 'ATEX1']))
doubleClickUsingJS(findTestObject('null', [('textHAB') : 'ATEX1']),1)
WebUI.click(findTestObject('null', [('textHAB') : 'ATEX1']))
WebUI.click(findTestObject('null', [('textHAB') : 'ATEX1']))
WebUI.sendKeys(findTestObject('null', [('textHAB') : 'ATEX1']), Keys.chord(
	Keys.F2))

WUI.delay(1000)

WebUI.setText(findTestObject('null', [('textHAB') : 'ATEX1']),
	'12/01/2023')
*/
