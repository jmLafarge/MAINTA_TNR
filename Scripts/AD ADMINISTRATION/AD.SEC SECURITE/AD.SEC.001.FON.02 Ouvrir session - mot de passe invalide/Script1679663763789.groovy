import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrWebUI.KW
import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	KW.openBrowser(GlobalVariable.BASE_URL)

	KW.maximizeWindow()
	
	KW.scrollAndSetText(myJDD,'in_user')
	
	KW.setEncryptedText(myJDD,'in_passw')

	KW.click(myJDD,'button_Connexion')

	if (KW.verifyElementPresent(myJDD,'span_error', GlobalVariable.TIMEOUT)) {
			
		TNRResult.addSTEP("Connexion invalide OK")
		
		KW.verifyElementText(myJDD, 'span_error')
		
		'Vérification des valeurs en BD'
		SQL.checkJDDWithBD(myJDD,[:],"SELECT *FROM UTILOG ORDER bY DT_LOG DESC")
			
	} else {
		
		TNRResult.addSTEP("Connexion invalide KO")
		
	}
	
	KW.closeBrowser()
	TNRResult.addEndTestCase()
}



