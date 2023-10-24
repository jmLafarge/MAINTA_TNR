package tnrTC

import org.testng.TestNG

import tnrCommon.Tools
import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult



/**
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
//@CompileStatic
public class TestNGRunner {

	public static void main(String[] args) {

		TNRResult.addStartInfo('TNR TEST')
		Tools.logInfoContext()
		GlobalJDD.myGlobalJDD
		TestNG testng = new TestNG();
		List<String> suites = new ArrayList<>();
		suites.add("tnr.xml");
		testng.setTestSuites(suites);
		testng.run();
		TNRResult.closeTNR('TNR SEQUENCER')
	}


}
