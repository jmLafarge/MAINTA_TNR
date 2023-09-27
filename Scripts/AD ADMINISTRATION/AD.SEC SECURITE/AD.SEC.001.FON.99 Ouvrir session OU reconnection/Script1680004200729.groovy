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

	STEP.openBrowser(0, GlobalVariable.BASE_URL)

	STEP.maximizeWindow(1)

	STEP.setText(2, myJDD,'in_user')

	STEP.setEncryptedText(3, myJDD,'in_passw')

	STEP.simpleClick(4, myJDD,'button_Connexion')

	if (STEP.verifyElementPresent(5, myJDD,'frame_Main', GlobalVariable.TIMEOUT,null)) {

		TNRResult.addSTEPINFO('', "Connexion OK")

		'Vérification des valeurs en BD'
		STEP.checkJDDWithBD(0, myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")

	} else if (STEP.verifyElementPresent(6, myJDD,'input_Oui', GlobalVariable.TIMEOUT,null)) {

		STEP.simpleClick(0, myJDD,'input_Oui')

		if (STEP.verifyElementPresent(7, myJDD,'frame_Main', GlobalVariable.TIMEOUT)) {

			TNRResult.addSTEPINFO('', "Reconnexion OK")

			'Vérification des valeurs en BD'
			STEP.checkJDDWithBD(0, myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")

		} else {

			TNRResult.addSTEPINFO('', "Reconnexion KO")
		}

	} else {

		TNRResult.addSTEPINFO('', "Connexion KO")

	}
	TNRResult.addEndTestCase()
}