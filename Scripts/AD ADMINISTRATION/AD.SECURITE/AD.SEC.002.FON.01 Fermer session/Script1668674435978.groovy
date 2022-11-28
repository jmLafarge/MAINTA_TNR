import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable


if (WebUI.waitForElementPresent(findTestObject('Object Repository/0 - COMMUN/Accueil/frame_main'), 1, FailureHandling.OPTIONAL)) {
    WebUI.scrollToPosition(0, 0)
    WebUI.delay(1)
	
    WebUI.click(findTestObject('Object Repository/0 - COMMUN/Accueil/Icone_LogOut_main')) 
	
} else {
	WebUI.scrollToPosition(0, 0)
    WebUI.delay(1)

    WebUI.click(findTestObject('Object Repository/0 - COMMUN/Accueil/Icone_LogOut'))
}

WebUI.waitForElementPresent(findTestObject('Object Repository/0 - COMMUN/Connexion/input_Mot de passe'), GlobalVariable.TIMEOUT)

WebUI.closeBrowser()

