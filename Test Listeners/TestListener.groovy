import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class TestListener {
	
	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		
		my.Log.addTITLE('Lancement de ' + testSuiteContext.getTestSuiteId())


	}
	
	
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		
		GlobalVariable.CASDETESTENCOURS = ''
		
		my.Log.addDEBUG("beforeTestCase : '" + testCaseContext.getTestCaseId() + "'")
		
		String TCName = testCaseContext.getTestCaseId().split('/')[-1]	
		
		my.Log.addDEBUG("TCName after split('/')[-1] : '" + TCName +"'")
		
		// si TC est un test on ne charge pas les fichiers TC et JDD
		if (testCaseContext.getTestCaseId().contains('ZZTEST UNITAIRE')) {
			
			my.Log.addTITLE("Lancement du test $TCName")
			
			
		}else if (TCName == '1 - CHECK PREREQUIS'){
			
			my.Log.addTITLE("Lancement de $TCName")
			if (my.InfoBDD.line.isEmpty()) { my.InfoBDD.load() }
			if (my.JDDFiles.JDDfilemap.isEmpty()) { my.JDDFiles.load() }
			if (my.PREJDDFiles.PREJDDfilemap.isEmpty()) { my.PREJDDFiles.load() }
			
		}else if (TCName == '2 - CREATE PREJDD IN DB'){
			
			my.Log.addTITLE("Lancement de $TCName")
			if (my.InfoBDD.line.isEmpty()) { my.InfoBDD.load() }
			if (my.JDDFiles.JDDfilemap.isEmpty()) { my.JDDFiles.load() }
			if (my.PREJDDFiles.PREJDDfilemap.isEmpty()) { my.PREJDDFiles.load() }


		}else if (TCName == '3 - TNR SEQUENCER') {
			if (my.InfoBDD.line.isEmpty()) { my.InfoBDD.load() }
			if (my.TCFiles.TCfileMap.isEmpty()) { my.TCFiles.load() }
			if (my.JDDFiles.JDDfilemap.isEmpty()) { my.JDDFiles.load() }
			if (my.Sequencer.testCasesList.isEmpty()) { my.Sequencer.load() }
			if (my.NAV.myGlobalJDD == null) { my.NAV.loadJDDGLOBAL() }

			my.Log.addTITLE("Lancement de $TCName")
				
		}else {
			/*
			TCName = (TCName.contains(' ')) ? TCName.split(' ')[0] : TCName
			
			my.Log.addDEBUG("TCName after split(' ')[0] : " + TCName)
			if (my.InfoBDD.line.isEmpty()) { my.InfoBDD.load() }
			if (my.TCFiles.TCfileMap.isEmpty()) { my.TCFiles.load() }
			if (my.JDDFiles.JDDfilemap.isEmpty()) { my.JDDFiles.load() }
			if (my.Sequencer.testCasesList.isEmpty()) { my.Sequencer.load() }
			if (my.NAV.myGlobalJDD == null) { my.NAV.loadJDDGLOBAL() }
			
			
			GlobalVariable.CASDETESTENCOURS = TCName
			*/
		}

	}

	
	
	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		
		my.Log.addDEBUG('afterTestCase : ' + testCaseContext.getTestCaseStatus())
		my.Log.addINFO('')
		my.Log.addINFO('************  FIN  du test : ' + testCaseContext.getTestCaseId().split('/')[-1] +' ************')
	}



}