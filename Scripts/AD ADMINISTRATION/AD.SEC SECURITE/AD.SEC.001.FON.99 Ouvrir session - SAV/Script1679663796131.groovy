import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.Log as MYLOG


'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	KW.openBrowser(GlobalVariable.BASE_URL)

	KW.maximizeWindow()
	
	KW.scrollAndSetText(myJDD,'in_user')
	
	KW.setEncryptedText(myJDD,'in_passw')

	KW.click(myJDD,'button_Connexion')
	
	//if (WebUI.verifyElementPresent(myJDD.makeTO('frame_Main'), GlobalVariable.TIMEOUT,FailureHandling.OPTIONAL)) {
	
	if (KW.verifyElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT,null)) {
			
		MYLOG.addSTEPPASS("Connexion OK")
		
	//}else if (WebUI.verifyElementPresent(myJDD.makeTO('input_Oui'), GlobalVariable.TIMEOUT,FailureHandling.OPTIONAL)) {
		
	}else if (KW.verifyElementPresent(myJDD,'input_Oui', GlobalVariable.TIMEOUT,null)) {
		
		    KW.click(myJDD,'input_Oui')
			
			//if (WebUI.verifyElementPresent(myJDD.makeTO('frame_Main'), GlobalVariable.TIMEOUT,FailureHandling.OPTIONAL)) {
				
			if (KW.verifyElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT,null)) {
				
				MYLOG.addSTEPPASS("Reconnexion OK")
			}else {
				
				MYLOG.addSTEPFAIL("Erreur de re-connexion, le bureau Mainta n'est pas présent")
			}
			
	} else {
		
		MYLOG.addSTEPFAIL("Connexion KO")
		
	}

}



