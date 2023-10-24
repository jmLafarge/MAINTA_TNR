package tnrTC.AD_SEC

import org.testng.ITestContext
import org.testng.ITestResult
import org.testng.annotations.BeforeMethod

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
public class AD_SEC_014_FON {

	private final String TC_TITLE = 'Fermer la session'

	@BeforeMethod
	public void beforeMethod(ITestContext context, ITestResult result) {
		Tools.setCAS_DE_TEST_PATTERN(this.getClass().getName())
	}

	public void fermerLaSession() {


		JDD myJDD = new JDD()

		for (String cdt in myJDD.getCDTList()) {

			myJDD.setCasDeTest(cdt)

			TNRResult.addStartTestCase(cdt)

			if (WUI.isElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT as int)) {

				WUI.switchToFrame(myJDD,'frame_Main')

				STEP.scrollToPosition(0, 0)

				STEP.simpleClick(myJDD,'icon_Logout')

				STEP.switchToDefaultContent()
			} else {
				// y a pas de frame_main quand on appelle les url en direct !

				STEP.scrollToPosition(0, 0)

				STEP.simpleClick(myJDD,'icon_Logout')

			}

			if (STEP.verifyElementVisible(myJDD,'in_passw', GlobalVariable.TIMEOUT as int)) {

				TNRResult.addSTEPINFO('',"Déconnexion OK")

				STEP.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")
			}else {
				TNRResult.addSTEPINFO('',"Déconnexion KO")
			}

			STEP.closeBrowser()
			TNRResult.addEndTestCase()
		}
	}
}//end of class
