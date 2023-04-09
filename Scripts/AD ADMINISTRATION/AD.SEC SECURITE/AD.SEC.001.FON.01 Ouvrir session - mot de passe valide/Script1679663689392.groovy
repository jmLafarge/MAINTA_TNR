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
	
	if (KW.verifyElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT)) {
			
		MYLOG.addSTEP("Connexion OK")
		
		'Vérification des valeurs en BD'
		my.SQL.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")
			
	} else {
		
		MYLOG.addSTEP("Connexion KO")
		
	}
	
}



