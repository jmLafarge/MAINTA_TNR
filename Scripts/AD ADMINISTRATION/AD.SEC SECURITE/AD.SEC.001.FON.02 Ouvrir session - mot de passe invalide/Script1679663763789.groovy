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
	
	STEP.openBrowser(GlobalVariable.BASE_URL)

	STEP.maximizeWindow()
	
	STEP.setText( myJDD,'in_user')
	
	STEP.setEncryptedText(myJDD,'in_passw')

	STEP.simpleClick(myJDD,'button_Connexion')

	if (STEP.verifyElementPresent(myJDD,'span_error', GlobalVariable.TIMEOUT)) {
			
		TNRResult.addSTEPINFO('',"Connexion invalide OK")
		
		STEP.verifyText(myJDD, 'span_error')
		
		'Vérification des valeurs en BD'
		STEP.checkJDDWithBD(myJDD,[:],"SELECT *FROM UTILOG ORDER bY DT_LOG DESC")
			
	} else {
		
		TNRResult.addSTEPINFO('',"Connexion invalide KO")
		
	}
	
	STEP.closeBrowser()
	TNRResult.addEndTestCase()
}



