package tnrTC.AD_SEC

import org.testng.ITestContext
import org.testng.ITestResult
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrCommon.Tools
import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrWebUI.STEP


/**
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
@CompileStatic
public class AD_SEC_001_FON_01 {

	private final String TC_TITLE = 'Ouvrir une session avec un mot de passe valide'
	
	@BeforeMethod
	public void beforeMethod(ITestContext context, ITestResult result) {
		Tools.setCAS_DE_TEST_PATTERN(this.getClass().getName())
	}

	@Test
	public void ouvrirSessionAvecMdpValide() {

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

				STEP.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")
			} else {

				TNRResult.addSTEPINFO('',"Connexion KO")
			}

			TNRResult.addEndTestCase()
		}
	}
}//end of class
