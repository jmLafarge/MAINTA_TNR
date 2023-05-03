import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.result.TNRResult
import my.JDD

'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	KW.closeBrowser()
	
	KW.delay(1)
	
	KW.openBrowser(GlobalVariable.BASE_URL)

	KW.maximizeWindow()
	
	KW.scrollAndSetText(myJDD,'in_user')
	
	KW.setEncryptedText(myJDD,'in_passw')

	KW.click(myJDD,'button_Connexion')
	
	if (KW.verifyElementPresent(myJDD,'input_Oui', GlobalVariable.TIMEOUT,null)) {
		
			KW.click(myJDD,'input_Oui')
			
			if (KW.verifyElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT)) {
					
				TNRResult.addSTEP("Reconnexion OK")
				
				'Vérification des valeurs en BD'
				my.SQL.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")
					
			} else {
				
				TNRResult.addSTEP("Reconnexion KO")
				
			}
	}else {
		
		TNRResult.addSTEPFAIL("Reconnexion KO")
	}
	TNRResult.addEndTestCase()
}



