import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.Log as MYLOG


'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	if (WebUI.waitForElementPresent(myJDD.makeTO('frame_Main'), 1, FailureHandling.OPTIONAL)) {
		
		WebUI.switchToFrame(myJDD.makeTO('frame_Main'), GlobalVariable.TIMEOUT)
		
	    WebUI.scrollToPosition(0, 0)
	    KW.delay(1)

	    KW.click(myJDD,'icon_Logout')
		
		WebUI.switchToDefaultContent()
		
	} else {
		WebUI.scrollToPosition(0, 0)
	    KW.delay(1)
	
	    KW.click(myJDD,'icon_Logout')
	}
	
	if (!WebUI.waitForElementPresent(myJDD.makeTO('in_passw'), GlobalVariable.TIMEOUT)) {
		
		MYLOG.addSTEPFAIL("Erreur de déconnexion la page de connexion n'est pas présente" )
	}else {
		MYLOG.addSTEPPASS("Déconnexion OK")
	}
	
	KW.closeBrowser()

}

