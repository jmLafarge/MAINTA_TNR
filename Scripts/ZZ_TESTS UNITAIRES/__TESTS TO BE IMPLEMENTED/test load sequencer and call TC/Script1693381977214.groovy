import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import tnrSqlManager.InfoDB
import tnrResultManager.TNRResult
import tnr.Sequencer
import tnrCommon.Tools
import tnrResult.ResultGenerator as XLSResult





 





Sequencer.testCasesList.each { TCMap ->
	
	GlobalVariable.CAS_DE_TEST_PATTERN = TCMap.CDTPATTERN
	
	
	
	WebUI.callTestCase(findTestCase(TCMap.TCFULLNAME), ['UNEVAR':'SAVALEUR'], FailureHandling.STOP_ON_FAILURE)
	
	
	
}