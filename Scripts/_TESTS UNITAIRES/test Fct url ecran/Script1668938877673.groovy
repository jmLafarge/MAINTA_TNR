import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable


String FCTCODE = 'acteur'

int cas = 3


'A supprimer : pour tester seul'
if (GlobalVariable.TC_CONNEXION) {
    WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SECURITE/AD.SEC.001.FON.01 Ouvrir session'), [:], FailureHandling.STOP_ON_FAILURE)
}

switch (cas) {
	
	case 1 :

		// sans FailureHandling
		//CustomKeywords.'my.Fct_url_ecran.goToURL_RUD_and_checkCartridge'(FCTCODE, 'RO.ACT.001.LEC.01')
		
		//CustomKeywords.'my.Fct_url_ecran.goToURL_Grille_and_checkCartridge'(FCTCODE)
		
		CustomKeywords.'my.Fct_url_ecran.goToURL_Creation_and_checkCartridge'(FCTCODE)
	
	
		break
	case 2 :
	
		// FailureHandling STOP_ON_FAILURE
		CustomKeywords.'my.Fct_url_ecran.goToURL_RUD_and_checkCartridge'(FCTCODE, 'RO.ACT.001.LEC.01',FailureHandling.STOP_ON_FAILURE)
		
		CustomKeywords.'my.Fct_url_ecran.goToURL_Grille_and_checkCartridge'(FCTCODE,FailureHandling.STOP_ON_FAILURE)
		
		CustomKeywords.'my.Fct_url_ecran.goToURL_Creation_and_checkCartridge'(FCTCODE,FailureHandling.STOP_ON_FAILURE)
	
		break
	case 3 :
	
		// FailureHandling OPTIONAL
		CustomKeywords.'my.Fct_url_ecran.goToURL_RUD_and_checkCartridge'(FCTCODE, 'RO.ACT.001.LEC.01',FailureHandling.OPTIONAL)
		
		CustomKeywords.'my.Fct_url_ecran.goToURL_Grille_and_checkCartridge'(FCTCODE,FailureHandling.OPTIONAL)
		
		CustomKeywords.'my.Fct_url_ecran.goToURL_Creation_and_checkCartridge'(FCTCODE,FailureHandling.OPTIONAL)
	
		break
	case 4 :
		// FailureHandling ONTINUE_ON_FAILURE
		CustomKeywords.'my.Fct_url_ecran.goToURL_RUD_and_checkCartridge'(FCTCODE, 'RO.ACT.001.LEC.01',FailureHandling.CONTINUE_ON_FAILURE)
		
		CustomKeywords.'my.Fct_url_ecran.goToURL_Grille_and_checkCartridge'(FCTCODE,FailureHandling.CONTINUE_ON_FAILURE)
		
		CustomKeywords.'my.Fct_url_ecran.goToURL_Creation_and_checkCartridge'(FCTCODE,FailureHandling.CONTINUE_ON_FAILURE)
	
		break
	default :
	
		
		KeywordUtil.markFailed("Valeur inconnue : " + cas)

} // end of switch case

KeywordUtil.logInfo('***** FIN DU TEST')
