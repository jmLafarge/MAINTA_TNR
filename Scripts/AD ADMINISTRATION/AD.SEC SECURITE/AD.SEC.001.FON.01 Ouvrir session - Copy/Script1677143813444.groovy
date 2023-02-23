import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW


'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	KW.openBrowser(GlobalVariable.BASE_URL)

	KW.maximizeWindow()
	
	KW.setText(myJDD.makeTO('in_user'), myJDD.getData('in_user'))
	
	KW.setEncryptedText(myJDD.makeTO('in_passw'), myJDD.getData('in_passw'))

	KW.click(myJDD.makeTO('button_Connexion'))
	
	if (WebUI.waitForElementPresent(myJDD.makeTO('frame_Main'), GlobalVariable.TIMEOUT,FailureHandling.OPTIONAL)) {
		
		WebUI.switchToFrame(myJDD.makeTO('frame_Main'), GlobalVariable.TIMEOUT)
		
		if (WebUI.waitForElementPresent(myJDD.makeTO('a_Menuburger'), GlobalVariable.TIMEOUT,FailureHandling.OPTIONAL)) {
		
			my.Log.addSTEPPASS("Connexion OK")
		}else {
			
			my.Log.addSTEPFAIL("Erreur de connexion, le bureau Mainta n'est pas présent")
		}
		WebUI.switchToDefaultContent()
		
	// pas de frame peut être une reconnexion, donc on teste la fenetre de reconnexion
	} else if (WebUI.waitForElementPresent(myJDD.makeTO('input_Oui'), 1,FailureHandling.OPTIONAL)) {
		
	    KW.click(myJDD.makeTO('input_Oui'))
		
		if (WebUI.waitForElementPresent(myJDD.makeTO('a_Menuburger'), GlobalVariable.TIMEOUT,FailureHandling.OPTIONAL)) {
			
			my.Log.addSTEPPASS("Reconnexion OK")
		}else {
			
			my.Log.addSTEPFAIL("Erreur de re-connexion, le bureau Mainta n'est pas présent")
		}
		
	} else {
		
		my.Log.addSTEPERROR("Erreur de connexion")
	}

}


