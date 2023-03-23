import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

import internal.GlobalVariable
import my.Log as MYLOG
import my.NAV as NAV

class TestListener {
	
	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		
		MYLOG.addTITLE('Lancement de ' + testSuiteContext.getTestSuiteId())


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
			
			my.Result.addStartInfo()
			
			MYLOG.addSUBSTEP('INFO CONTEXTE')
			MYLOG.addDETAIL("Nom de l'OS".padRight(26) + System.getProperty("os.name"))
			MYLOG.addDETAIL("Version de l'OS".padRight(26) + System.getProperty("os.version"))
			MYLOG.addDETAIL("Architecture de l'OS".padRight(26) + System.getProperty("os.arch"))
			MYLOG.addDETAIL("Version de MAINTA".padRight(26) + my.SQL.getMaintaVersion())
			MYLOG.addDETAIL("Base de donnée".padRight(26) + GlobalVariable.BDD_URL)
			MYLOG.addINFO('')
			
			if (NAV.myGlobalJDD == null) { NAV.loadJDDGLOBAL() }
	
		}else if (testCaseContext.getTestCaseId().contains('ZZTEST UNITAIRE')) {
			
			MYLOG.addTITLE("Lancement du test $TCName")
			
		}else {
			
			TCName = (TCName.contains(' ')) ? TCName.split(' ')[0] : TCName
			
			MYLOG.addDEBUG("TCName after split(' ')[0] : " + TCName)
			if (my.InfoBDD.map.isEmpty()) { my.InfoBDD.load() }
			if (my.TCFiles.TCfileMap.isEmpty()) { my.TCFiles.load() }
			if (my.JDDFiles.JDDfilemap.isEmpty()) { my.JDDFiles.load() }
			if (my.Sequencer.testCasesList.isEmpty()) { my.Sequencer.load() }
			if (NAV.myGlobalJDD == null) { NAV.loadJDDGLOBAL() }
			
			
			GlobalVariable.CASDETESTENCOURS = TCName
		}

	}

	
	
	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		
		MYLOG.addDEBUG('afterTestCase : ' + testCaseContext.getTestCaseStatus())
		MYLOG.addINFO('')
		MYLOG.addINFO('************  FIN  du test : ' + testCaseContext.getTestCaseId().split('/')[-1] +' ************')
	}



}