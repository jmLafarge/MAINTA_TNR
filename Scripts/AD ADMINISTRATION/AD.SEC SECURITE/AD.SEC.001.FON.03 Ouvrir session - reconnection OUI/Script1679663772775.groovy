import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrWebUI.*
import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	STEP.closeBrowser(1)
	
	WUI.delay(1000)
	
	STEP.openBrowser(3, GlobalVariable.BASE_URL)

	STEP.maximizeWindow(4)
	
	STEP.setText(5, myJDD,'in_user')
	
	STEP.setEncryptedText(6, myJDD,'in_passw')

	STEP.simpleClick(7, myJDD,'button_Connexion')
	
	if (STEP.verifyElementPresent(8, myJDD,'input_Oui', GlobalVariable.TIMEOUT,null)) {
		
			STEP.simpleClick(9, myJDD,'input_Oui')
			
			if (STEP.verifyElementPresent(10, myJDD,'frame_Main', GlobalVariable.TIMEOUT)) {
					
				TNRResult.addSTEPINFO('', "Reconnexion OK")
				
				'Vérification des valeurs en BD'
				STEP.checkJDDWithBD(0, myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")
					
			} else {
				
				TNRResult.addSTEPINFO('', "Reconnexion KO")
				
			}
	}else {
		
		TNRResult.addSTEPINFO('', "Reconnexion KO")
	}
	TNRResult.addEndTestCase()
}



