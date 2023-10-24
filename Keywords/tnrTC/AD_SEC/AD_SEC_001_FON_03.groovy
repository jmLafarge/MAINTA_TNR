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
import tnrWebUI.WUI


/**
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
@CompileStatic
public class AD_SEC_001_FON_03 {

	private final String TC_TITLE = 'Ouvrir une session avec reconnection OUI'
	
	@BeforeMethod
	public void beforeMethod(ITestContext context, ITestResult result) {
		Tools.setCAS_DE_TEST_PATTERN(this.getClass().getName())
	}

	@Test
	public void ouvrirSessionAvecReconnectionOUI() {

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
