import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

import internal.GlobalVariable
import my.Log

class TestListener {
	
	private static final String CLASS_FORLOG = 'TestListener'

	private boolean testSuite=false
	
	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
//		Log.addTraceBEGIN(CLASS_FORLOG,"TestListener.beforeTestSuite('${testSuiteContext.getTestSuiteId()}')")
//		testSuite=true
//		Log.addTITLE('Lancement de ' + testSuiteContext.getTestSuiteId())
//		RestoreDB.run()
//		TNRResult.addStartInfo(testSuiteContext.getTestSuiteId())
//		Tools.addInfoContext()
//		Log.addTraceEND(CLASS_FORLOG,"TestListener.beforeTestSuite()")
	}
	
	
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		Log.addTraceBEGIN(CLASS_FORLOG,"beforeTestCase",[getTestCaseId:testCaseContext.getTestCaseId()])
		if (testSuite) {
			String TCName = testCaseContext.getTestCaseId().split('/')[-1].split(' ')[0]
			Log.addTrace("TCName : '$TCName'")
			GlobalVariable.CASDETESTENCOURS = TCName
			GlobalVariable.CASDETESTPATTERN = TCName
		}
		Log.addTraceEND(CLASS_FORLOG,"beforeTestCase")
	}


	
	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		Log.addTraceBEGIN(CLASS_FORLOG,"afterTestCase",[getTestCaseId:testCaseContext.getTestCaseId()])
		Log.addTrace('afterTestCase : ' + testCaseContext.getTestCaseId().split('/')[-1] + ' : '+testCaseContext.getTestCaseStatus())
		Log.addTraceEND(CLASS_FORLOG,"afterTestCase")
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
//		Log.addTraceBEGIN(CLASS_FORLOG,"TestListener.afterTestSuite('${testSuiteContext.getTestSuiteId()}')")
//		TNRResult.close(testSuiteContext.getTestSuiteId())
//		Log.addTraceEND(CLASS_FORLOG,"TestListener.afterTestSuite()")
	}

}