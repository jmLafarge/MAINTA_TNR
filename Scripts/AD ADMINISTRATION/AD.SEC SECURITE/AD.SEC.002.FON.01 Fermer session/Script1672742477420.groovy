import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.JDD


'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	if (WebUI.waitForElementPresent(myJDD.makeTO('frame_Main'), 1, FailureHandling.OPTIONAL)) {
		
		WebUI.switchToFrame(myJDD.makeTO('frame_Main'), GlobalVariable.TIMEOUT)
		
	    WebUI.scrollToPosition(0, 0)
	    WebUI.delay(1)
		
		my.Log.addSTEP("Clic sur l'icone 'Deconnexion'")
	    WebUI.click(myJDD.makeTO('icon_Logout')) 
		
		WebUI.switchToDefaultContent()
		
	} else {
		WebUI.scrollToPosition(0, 0)
	    WebUI.delay(1)
	
		my.Log.addSTEP("Clic sur l'icone 'Deconnexion'")
	    WebUI.click(myJDD.makeTO('icon_Logout'))
	}
	
	if (!WebUI.waitForElementPresent(myJDD.makeTO('in_passw'), GlobalVariable.TIMEOUT)) {
		
		my.Log.addSTEPFAIL("Erreur de déconnexion la page de connexion n'est pas présente" )
	}else {
		my.Log.addSTEPPASS("Déconnexion OK")
	}
	
	my.Log.addSTEP("Fermeture du navigateur")
	WebUI.closeBrowser()

}

