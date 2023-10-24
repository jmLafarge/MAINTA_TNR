package tnrTC.AD_SEC

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrWebUI.STEP
import tnrWebUI.WUI


/**
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
@CompileStatic
public class AD_SEC_001_FON {

	public cdt_01() {

		// Lecture du JDD
		JDD myJDD = new JDD()


		for (String cdt in myJDD.getCDTList()) {

			myJDD.setCasDeTest(cdt)

			TNRResult.addStartTestCase(cdt)

			STEP.openBrowser(GlobalVariable.BASE_URL.toString())

			STEP.maximizeWindow()

			STEP.setText(myJDD,'in_user')

			STEP.setEncryptedText(myJDD,'in_passw')

			STEP.simpleClick(myJDD,'button_Connexion')

			if (STEP.verifyElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT as int)) {

				TNRResult.addSTEPINFO('',"Connexion OK")

				'Vérification des valeurs en BD'
				STEP.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")

			} else {

				TNRResult.addSTEPINFO('',"Connexion KO")

			}

			TNRResult.addEndTestCase()
		}
	}



	public cdt_02() {

		// Lecture du JDD
		JDD myJDD = new JDD()


		for (String cdt in myJDD.getCDTList()) {

			myJDD.setCasDeTest(cdt)

			TNRResult.addStartTestCase(cdt)

			STEP.openBrowser(GlobalVariable.BASE_URL.toString())

			STEP.maximizeWindow()

			STEP.setText( myJDD,'in_user')

			STEP.setEncryptedText(myJDD,'in_passw')

			STEP.simpleClick(myJDD,'button_Connexion')

			if (STEP.verifyElementPresent(myJDD,'span_error', GlobalVariable.TIMEOUT as int)) {

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


	}



	public cdt_03() {

		// Lecture du JDD
		JDD myJDD = new JDD()


		for (String cdt in myJDD.getCDTList()) {

			myJDD.setCasDeTest(cdt)

			TNRResult.addStartTestCase(cdt)

			STEP.closeBrowser()

			WUI.delay(1000)

			STEP.openBrowser(GlobalVariable.BASE_URL.toString())

			STEP.maximizeWindow()

			STEP.setText(myJDD,'in_user')

			STEP.setEncryptedText(myJDD,'in_passw')

			STEP.simpleClick(myJDD,'button_Connexion')

			if (STEP.verifyElementPresent(myJDD,'input_Oui', GlobalVariable.TIMEOUT as int,null)) {

				STEP.simpleClick(myJDD,'input_Oui')

				if (STEP.verifyElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT as int)) {

					TNRResult.addSTEPINFO('',"Reconnexion OK")

					'Vérification des valeurs en BD'
					STEP.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")

				} else {

					TNRResult.addSTEPINFO('',"Reconnexion KO")

				}
			}else {

				TNRResult.addSTEPINFO('',"Reconnexion KO")
			}
			TNRResult.addEndTestCase()
		}

	}

}//end of class
