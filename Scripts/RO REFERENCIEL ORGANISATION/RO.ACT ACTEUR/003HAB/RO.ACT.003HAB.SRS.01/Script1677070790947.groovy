import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.NAV




'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getData('ID_CODINT'))

	'Clic sur le bon onglet'
    KW.scrollAndClick(myJDD.makeTO('a_Habilitation'))
	
	'Vérification de l\'onglet'
	KW.waitForElementVisible(myJDD.makeTO('h4_Habilitation'),GlobalVariable.TIMEOUT)

	'Boucle sur les lignes d\'un même TC'
    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {

		WebUI.delay(1)
		
        'Supression'
		KW.scrollAndClick(myJDD.makeTO('span_Supprime_Habilitation'))

		'Gestion du message Alert'
	    if (WebUI.waitForAlert(GlobalVariable.TIMEOUT)) {
			
			WebUI.acceptAlert()
			
			'Vérification du test case - Supression dans le tableau'
	        WebUI.verifyElementNotPresent(myJDD.makeTO('ID_CODHAB'), GlobalVariable.TIMEOUT)
			
			'Vérification du test case - Controle des valeurs du myJDD.data en BD'
			
			// A FAIRE ****************************************************
			
		}else {
			
			my.Log.addSTEPFAIL("Pas de popup 'Alert' pour la suppression !")
			
		}
    }
	
	
	//Vérification en BD
	
	
	

} // fin du if




