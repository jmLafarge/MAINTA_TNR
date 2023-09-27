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
	
	STEP.openBrowser(1, GlobalVariable.BASE_URL)

	STEP.maximizeWindow(2)
	
	STEP.setText(3, myJDD,'in_user')
	
	STEP.setEncryptedText(4, myJDD,'in_passw')

	STEP.simpleClick(5, myJDD,'button_Connexion')

	if (STEP.verifyElementPresent(6, myJDD,'span_error', GlobalVariable.TIMEOUT)) {
			
		TNRResult.addSTEPINFO('', "Connexion invalide OK")
		
		STEP.verifyText(7,myJDD, 'span_error')
		
		'Vérification des valeurs en BD'
		STEP.checkJDDWithBD(0, myJDD,[:],"SELECT *FROM UTILOG ORDER bY DT_LOG DESC")
			
	} else {
		
		TNRResult.addSTEPINFO('', "Connexion invalide KO")
		
	}
	
	STEP.closeBrowser(8)
	TNRResult.addEndTestCase()
}



