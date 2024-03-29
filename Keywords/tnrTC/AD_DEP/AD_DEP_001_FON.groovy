package tnrTC.AD_DEP

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
public class AD_DEP_001_FON {

	private final String TC_TITLE = "Vérifier les informations 'A propos'"

	
	@BeforeMethod
	public void beforeMethod(ITestContext context, ITestResult result) {
		Tools.setCAS_DE_TEST_PATTERN(this.getClass().getName())
	}
	
	@Test
	public void verifierInfoAPropos() {

		JDD myJDD = new JDD()

		for (String cdt in myJDD.getCDTList()) {

			myJDD.setCasDeTest(cdt)

			TNRResult.addStartTestCase(cdt)

			'Naviguer vers la bonne url'
			String url = GlobalVariable.BASE_URL.toString() + myJDD.getData('URL')
			STEP.navigateToUrl(url,'A propos de Mainta')

			if (STEP.verifyElementPresent(myJDD,'tab_APropos', GlobalVariable.TIMEOUT as int)) {

				TNRResult.addSTEPBLOCK("Controle des versions à l'écran")
				/*
				 STEP.verifyTextContains(myJDD, 'VER_BDD')
				 List text = []
				 text = myJDD.getStrData('VER_MOS_XML').split(' ')
				 STEP.verifyTextContains(myJDD, 'VER_MOS_XML', text[0])
				 STEP.verifyTextContains(myJDD, 'VER_MOS_XML', text[1])
				 text = myJDD.getStrData('VER_MOSIWS').split(' ')
				 STEP.verifyTextContains(myJDD, 'VER_MOSIWS', text[0])
				 STEP.verifyTextContains(myJDD, 'VER_MOSIWS', text[1])
				 text = myJDD.getStrData('VER_MOS_XMLI').split(' ')
				 STEP.verifyTextContains(myJDD, 'VER_MOS_XMLI', text[0])
				 STEP.verifyTextContains(myJDD, 'VER_MOS_XMLI', text[1])
				 */
				STEP.verifyText(myJDD, 'VER_BDD')
				STEP.verifyText(myJDD, 'VER_MOS_XML')
				STEP.verifyText(myJDD, 'VER_MOSIWS')
				STEP.verifyText(myJDD, 'VER_MOS_XMLI')

				TNRResult.addSTEPBLOCK("Controle en BDD")

				STEP.verifyMaintaVersion(myJDD.getStrData('VER_BDD'))
			}

			TNRResult.addEndTestCase()
		}
	}
}//end of class
