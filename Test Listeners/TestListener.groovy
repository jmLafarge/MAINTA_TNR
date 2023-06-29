import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

import internal.GlobalVariable
import my.Log
import my.NAV
import my.Tools
import my.result.TNRResult
import my.InfoBDD
import my.TCFiles
import my.JDDFiles

class TestListener {
	
	boolean testSuite=false
	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		
		testSuite=true

		Log.addTITLE('Lancement de ' + testSuiteContext.getTestSuiteId())

		if (InfoBDD.map.isEmpty()) { InfoBDD.load() }
		if (TCFiles.TCfileMap.isEmpty()) { TCFiles.load() }
		if (JDDFiles.JDDfilemap.isEmpty()) { JDDFiles.load() }
		
		TNRResult.addStartInfo(testSuiteContext.getTestSuiteId())

		Tools.addInfoContext()
		
		NAV.loadJDDGLOBAL()


	}
	
	
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		
		Log.addDEBUG("beforeTestCase : '${testCaseContext.getTestCaseId()}'")
		
		if (testSuite) {
			String TCName = testCaseContext.getTestCaseId().split('/')[-1].split(' ')[0]
			Log.addDEBUG("TCName : '$TCName'")
			GlobalVariable.CASDETESTENCOURS = TCName
			GlobalVariable.CASDETESTPATTERN = TCName
		}
	}

	
	
	
	
	
	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		
		Log.addDEBUG('afterTestCase : ' + testCaseContext.getTestCaseId().split('/')[-1] + ' '+testCaseContext.getTestCaseStatus())
		
	}

	
	
	
	
	
	/**
	 * Executes after every test suite ends.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@AfterTestSuite
	def sampleAfterTestSuite(TestSuiteContext testSuiteContext) {
				
		TNRResult.close(testSuiteContext.getTestSuiteId())

	}

}