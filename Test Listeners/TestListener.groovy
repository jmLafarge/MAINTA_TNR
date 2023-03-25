import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

import internal.GlobalVariable
import my.Log as MYLOG
import my.NAV as NAV
import my.Tools
import my.Result

class TestListener {
	
	boolean testSuite=false
	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		
		this.testSuite=true

		MYLOG.addTITLE('Lancement de ' + testSuiteContext.getTestSuiteId())

		if (my.InfoBDD.map.isEmpty()) { my.InfoBDD.load() }
		if (my.TCFiles.TCfileMap.isEmpty()) { my.TCFiles.load() }
		if (my.JDDFiles.JDDfilemap.isEmpty()) { my.JDDFiles.load() }
		if (my.Sequencer.testCasesList.isEmpty()) { my.Sequencer.load() }
		
		Result.addStartInfo(testSuiteContext.getTestSuiteId())

		Tools.addInfoContext()
		
		if (NAV.myGlobalJDD == null) { NAV.loadJDDGLOBAL() }


	}
	
	
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		
		GlobalVariable.CASDETESTENCOURS = ''
		
		MYLOG.addDEBUG("beforeTestCase : '" + testCaseContext.getTestCaseId() + "'")
		
		String TCName = testCaseContext.getTestCaseId().split('/')[-1]	
		
		MYLOG.addDEBUG("TCName after split('/')[-1] : '" + TCName +"'")
		
		
		if (TCName == '_TNR SEQUENCER') {
			if (my.InfoBDD.map.isEmpty()) { my.InfoBDD.load() }
			if (my.TCFiles.TCfileMap.isEmpty()) { my.TCFiles.load() }
			if (my.JDDFiles.JDDfilemap.isEmpty()) { my.JDDFiles.load() }
			if (my.Sequencer.testCasesList.isEmpty()) { my.Sequencer.load() }
			
			MYLOG.addTITLE("Lancement de $TCName")
			
			my.Result.addStartInfo('TNR SEQUENCER')
			
			Tools.addInfoContext()
			
			if (NAV.myGlobalJDD == null) { NAV.loadJDDGLOBAL() }
	
		}else if (TCName == '__JDD GENERATOR'){
			
			my.Log.addTITLE("Lancement de $TCName")
			if (my.InfoBDD.map.isEmpty()) { my.InfoBDD.load() }
			//if (my.JDDFiles.JDDfilemap.isEmpty()) { my.JDDFiles.load() }
			//if (my.PREJDDFiles.PREJDDfilemap.isEmpty()) { my.PREJDDFiles.load() }
			
		}else if (TCName == '_CHECK PREREQUIS'){
			
			my.Log.addTITLE("Lancement de $TCName")
			if (my.InfoBDD.map.isEmpty()) { my.InfoBDD.load() }
			if (my.JDDFiles.JDDfilemap.isEmpty()) { my.JDDFiles.load() }
			if (my.PREJDDFiles.PREJDDfilemap.isEmpty()) { my.PREJDDFiles.load() }
			
		}else if (TCName == '_CREATE PREJDD IN DB'){
			
			my.Log.addTITLE("Lancement de $TCName")
			if (my.InfoBDD.map.isEmpty()) { my.InfoBDD.load() }
			if (my.JDDFiles.JDDfilemap.isEmpty()) { my.JDDFiles.load() }
			if (my.PREJDDFiles.PREJDDfilemap.isEmpty()) { my.PREJDDFiles.load() }
			
			
		}else if (testCaseContext.getTestCaseId().contains('ZZTEST UNITAIRE')) {
			
			MYLOG.addTITLE("Lancement du test $TCName")
			
		}else {
			
			TCName = (TCName.contains(' ')) ? TCName.split(' ')[0] : TCName
			
			MYLOG.addDEBUG("TCName after split(' ')[0] : " + TCName)

			
			
			GlobalVariable.CASDETESTENCOURS = TCName
			

			
			MYLOG.addStartTestCase(GlobalVariable.CASDETESTENCOURS)
		}

	}

	
	
	
	
	
	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		
		MYLOG.addDEBUG('afterTestCase : ' + testCaseContext.getTestCaseStatus())
		
		if (testSuite) {
			MYLOG.addEndTestCase()
		}else {
			MYLOG.addINFO('')
			MYLOG.addINFO('************  FIN  du test : ' + testCaseContext.getTestCaseId().split('/')[-1] +' ************')
		}
	}

	
	
	
	
	
	/**
	 * Executes after every test suite ends.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@AfterTestSuite
	def sampleAfterTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()
		
		Result.addEndInfo()
		
		MYLOG.addINFO('')
		MYLOG.addINFO('************  FIN  de : ' + testSuiteContext.getTestSuiteId() +' ************')
	}

}