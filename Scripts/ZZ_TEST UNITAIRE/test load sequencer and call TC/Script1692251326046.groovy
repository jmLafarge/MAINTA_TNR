import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.InfoDB
import myResult.TNRResult
import my.Sequencer
import my.Tools
import myResult.ResultGenerator as XLSResult





 





Sequencer.testCasesList.each { TCMap ->
	
	GlobalVariable.CASDETESTPATTERN = TCMap.CDTPATTERN
	
	
	
	WebUI.callTestCase(findTestCase(TCMap.TCFULLNAME), ['UNEVAR':'SAVALEUR'], FailureHandling.STOP_ON_FAILURE)
	
	
	
}