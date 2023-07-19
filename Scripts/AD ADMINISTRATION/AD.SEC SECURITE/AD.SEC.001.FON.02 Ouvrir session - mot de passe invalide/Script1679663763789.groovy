import internal.GlobalVariable
import my.JDD
import my.KW
import my.SQL
import my.result.TNRResult

'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
	
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



