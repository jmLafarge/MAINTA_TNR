import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser(GlobalVariable.BASE_URL)

WebUI.maximizeWindow()

WebUI.setText(findTestObject('0 - COMMUN/Connexion/input_Utilisateur'), GlobalVariable.USER, FailureHandling.STOP_ON_FAILURE)

WebUI.setEncryptedText(findTestObject('0 - COMMUN/Connexion/input_Mot de passe'), GlobalVariable.MDP, FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('0 - COMMUN/Connexion/button_Connexion'), FailureHandling.STOP_ON_FAILURE)

// Acceptation
if (WebUI.waitForElementPresent(findTestObject('0 - COMMUN/Accueil/div_desk'), GlobalVariable.TIMEOUT,FailureHandling.OPTIONAL)) {
	
    KeywordUtil.logInfo('Connection OK')
	
} else if (WebUI.waitForElementPresent(findTestObject('0 - COMMUN/Connexion/Reconnexion/input_Oui'), 1,FailureHandling.OPTIONAL)) {
	
    WebUI.click(findTestObject('0 - COMMUN/Connexion/Reconnexion/input_Oui'), FailureHandling.STOP_ON_FAILURE)
	
	KeywordUtil.logInfo('Reconnection OK')
	
} else {
    KeywordUtil.markFailed('Connection KO')
}

