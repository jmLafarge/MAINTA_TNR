import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.SetupTestCase
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.annotation.TearDownTestCase
import com.kms.katalon.core.configuration.RunConfiguration

import internal.GlobalVariable

/**
 * Some methods below are samples for using SetUp/TearDown in a test suite.
 */

/**
 * Setup test suite environment.
 */
@SetUp(skipped = false) // Please change skipped to be false to activate this method.
def setUp() {
	// Put your code here.
	my.JDDFiles.load()
}

/**
 * Clean test suites environment.
 */
@TearDown(skipped = true) // Please change skipped to be false to activate this method.
def tearDown() {
	// Put your code here.
}

/**
 * Run before each test case starts.
 */
@SetupTestCase(skipped = false) // Please change skipped to be false to activate this method.
def setupTestCase() {
	// Put your code here.
	/*
	String TCName = RunConfiguration.getExecutionSourceName().toString()
	
	TCName = (TCName.contains(' ')) ? TCName.split(' ')[0] : TCName
	
	GlobalVariable.JDDTABNAME = my.Tools.getTabNameFromTCName(TCName)
	GlobalVariable.CASDETESTPATTERN = TCName
	GlobalVariable.JDDFULLNAME = my.JDDFiles.getJDDFileFullNameFromModObj(my.Tools.getModObjFromTCName(TCName))
	*/
}

/**
 * Run after each test case ends.
 */
@TearDownTestCase(skipped = true) // Please change skipped to be false to activate this method.
def tearDownTestCase() {
	// Put your code here.
}

/**
 * References:
 * Groovy tutorial page: http://docs.groovy-lang.org/next/html/documentation/
 */