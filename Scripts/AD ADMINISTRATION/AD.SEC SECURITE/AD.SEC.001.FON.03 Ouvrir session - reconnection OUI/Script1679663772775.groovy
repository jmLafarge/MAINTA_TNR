import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrWebUI.*
import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	STEP.closeBrowser(1)
	
	STEP.delay(1)
	
	STEP.openBrowser(2, GlobalVariable.BASE_URL)

	STEP.maximizeWindow(3)
	
	STEP.setText(4, myJDD,'in_user')
	
	STEP.setEncryptedText(5, myJDD,'in_passw')

	STEP.click(6, myJDD,'button_Connexion')
	
	if (STEP.verifyElementPresent(7, myJDD,'input_Oui', GlobalVariable.TIMEOUT,null)) {
		
			STEP.click(8, myJDD,'input_Oui')
			
			if (STEP.verifyElementPresent(9, myJDD,'frame_Main', GlobalVariable.TIMEOUT)) {
					
				TNRResult.addSTEPINFO("Reconnexion OK")
				
				'Vérification des valeurs en BD'
				SQL.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")
					
			} else {
				
				TNRResult.addSTEPINFO("Reconnexion KO")
				
			}
	}else {
		
		TNRResult.addSTEPINFO("Reconnexion KO")
	}
	TNRResult.addEndTestCase()
}



