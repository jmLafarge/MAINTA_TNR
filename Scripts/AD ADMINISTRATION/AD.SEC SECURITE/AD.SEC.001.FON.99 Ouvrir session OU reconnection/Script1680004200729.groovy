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

	STEP.setText(myJDD,'in_user')

	STEP.setEncryptedText(myJDD,'in_passw')

	STEP.simpleClick(myJDD,'button_Connexion')

	if (STEP.verifyElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT,null)) {

		TNRResult.addSTEPINFO('',"Connexion OK")

		'Vérification des valeurs en BD'
		STEP.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")

	} else if (STEP.verifyElementPresent(myJDD,'input_Oui', GlobalVariable.TIMEOUT,null)) {

		STEP.simpleClick(myJDD,'input_Oui')

		if (STEP.verifyElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT)) {

			TNRResult.addSTEPINFO('',"Reconnexion OK")

			'Vérification des valeurs en BD'
			STEP.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")

		} else {

			TNRResult.addSTEPINFO('',"Reconnexion KO")
		}

	} else {

		TNRResult.addSTEPINFO('',"Connexion KO")

	}
	TNRResult.addEndTestCase()
}