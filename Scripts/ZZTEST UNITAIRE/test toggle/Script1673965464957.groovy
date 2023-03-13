import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.extend_KW as extend_KW

String FCTCODE = 'acteur'

Map mfct = [('acteur') : '21', ('article') : '79', ('organisation') : '233', ('emplacement') : '5']

def extKW = new extend_KW()

'A supprimer : pour tester seul'
if (GlobalVariable.TC_CONNEXION) {
    WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SEC SECURITE/AD.SEC.001.FON.01 Ouvrir session'), [:])
}

//CustomKeywords.'my.NAV.goToURL_Grille_and_checkCartridge'(FCTCODE)
String url = ((GlobalVariable.BASE_URL + 'E') + (mfct[FCTCODE])) + '?'

KeywordUtil.logInfo('Got to : ' + url)

WebUI.navigateToUrl(url)

String code = 'E' + (mfct[FCTCODE])

KeywordUtil.logInfo('Vérification écran : ' + code)

WebUI.verifyElementPresent(findTestObject('null'), 1)

WebUI.verifyElementVisible(findTestObject('null'))

WebUI.verifyElementInViewport(findTestObject('null'), 1)

WebUI.getElementHeight(findTestObject('null'))

WebUI.getElementLeftPosition(findTestObject('null'))

WebElement anElement = WebUI.findWebElement(findTestObject('null'))

println anElement.getProperties()

WebUI.verifyElementClickable(findTestObject('null'))


WebUI.scrollToPosition(9999, 99999)
KW.delay(2)

WebUI.scrollToElement(findTestObject('null'), 1)
KW.delay(2)



WebUI.verifyElementPresent(findTestObject('null'), 1)

WebUI.verifyElementVisible(findTestObject('null'))

WebUI.verifyElementInViewport(findTestObject('null'), 1)

WebUI.getElementHeight(findTestObject('null'))

WebUI.getElementLeftPosition(findTestObject('null'))

println anElement.getProperties()

WebUI.verifyElementClickable(findTestObject('null'))

