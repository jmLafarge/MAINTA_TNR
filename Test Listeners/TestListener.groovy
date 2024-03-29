import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

import internal.GlobalVariable
import tnrLog.Log

class TestListener {
	
	private static final String CLASS_NAME = 'TestListener'

	private boolean testSuite=false
	
	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		Log.addTraceBEGIN(CLASS_NAME,"TestListener.beforeTestSuite('${testSuiteContext.getTestSuiteId()}')")
//		testSuite=true
//		Log.addTITLE('Lancement de ' + testSuiteContext.getTestSuiteId())
//		RestoreDB.run()
//		TNRResult.addStartInfo(testSuiteContext.getTestSuiteId())
//		Tools.addInfoContext()
		Log.addTraceEND(CLASS_NAME,"TestListener.beforeTestSuite()")
	}
	
	
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		Log.addTraceBEGIN(CLASS_NAME,"beforeTestCase",[getTestCaseId:testCaseContext.getTestCaseId()])
		if (testSuite) {
			String TCName = testCaseContext.getTestCaseId().split('/')[-1].split(' ')[0]
			Log.addTrace("TCName : '$TCName'")
			GlobalVariable.CAS_DE_TEST_EN_COURS = TCName
			GlobalVariable.CAS_DE_TEST_PATTERN = TCName
		}
		Log.addTraceEND(CLASS_NAME,"beforeTestCase")
	}


	
	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		Log.addTraceBEGIN(CLASS_NAME,"afterTestCase",[getTestCaseId:testCaseContext.getTestCaseId()])
		Log.addTrace(testCaseContext.getTestCaseId().split('/')[-1] + ' : '+testCaseContext.getTestCaseStatus())
		Log.addTraceEND(CLASS_NAME,"afterTestCase")
		if (!testSuite) {
			Log.addEndLog()
		}
	}

	
	
	
	
	
	/**
	 * Executes after every test suite ends.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@AfterTestSuite
	def afterTestSuite(TestSuiteContext testSuiteContext) {
//		Log.addTraceBEGIN(CLASS_NAME,"TestListener.afterTestSuite('${testSuiteContext.getTestSuiteId()}')")
//		TNRResult.closeTNR(testSuiteContext.getTestSuiteId())
//		Log.addTraceEND(CLASS_NAME,"TestListener.afterTestSuite()")
		Log.addEndLog()
	}

}