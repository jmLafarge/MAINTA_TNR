import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable


'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	my.Log.addSTEP('Ouverture du navigateur')
	WebUI.openBrowser(GlobalVariable.BASE_URL)
	
	my.Log.addSTEP('Maximise la fenêtre')
	WebUI.maximizeWindow()
	
	my.Log.addSTEP("Saisie de nom d'utilisateur")
	WebUI.setText(myJDD.makeTO('in_user'), myJDD.getData('in_user'), FailureHandling.STOP_ON_FAILURE)
	
	my.Log.addSTEP("Saisie du mot de passe")
	WebUI.setEncryptedText(myJDD.makeTO('in_passw'), myJDD.getData('in_passw'), FailureHandling.STOP_ON_FAILURE)
	
	my.Log.addSTEP("Clic sur le bouton connexion")
	WebUI.click(myJDD.makeTO('button_Connexion'), FailureHandling.STOP_ON_FAILURE)
	
	if (WebUI.waitForElementPresent(myJDD.makeTO('frame_Main'), GlobalVariable.TIMEOUT,FailureHandling.OPTIONAL)) {
		
		WebUI.switchToFrame(myJDD.makeTO('frame_Main'), GlobalVariable.TIMEOUT)
		
		if (WebUI.waitForElementPresent(myJDD.makeTO('div_Desk'), GlobalVariable.TIMEOUT,FailureHandling.OPTIONAL)) {
		
			my.Log.addSTEPPASS("Connexion OK")
		}else {
			
			my.Log.addSTEPFAIL("Erreur de connexion, le bureau Mainta n'est pas présent")
		}
		
		WebUI.switchToDefaultContent()
		
	// pas de frame peut être une reconnexion, donc on teste la fenetre de reconnexion
	} else if (WebUI.waitForElementPresent(myJDD.makeTO('input_Oui'), 1,FailureHandling.OPTIONAL)) {
		
	    WebUI.click(myJDD.makeTO('input_Oui'), FailureHandling.STOP_ON_FAILURE)
		
		if (WebUI.waitForElementPresent(myJDD.makeTO('div_Desk'), GlobalVariable.TIMEOUT,FailureHandling.OPTIONAL)) {
			
			my.Log.addSTEPPASS("Reconnexion OK")
		}else {
			
			my.Log.addSTEPFAIL("Erreur de re-connexion, le bureau Mainta n'est pas présent")
		}
		
	} else {
		
		my.Log.addERROR("Erreur de connexion")
	}

	
}


