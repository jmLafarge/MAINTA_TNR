import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.InfoBDD
import my.result.TNRResult
import my.Sequencer
import my.Tools
import my.result.ResultGenerator as XLSResult





 





Sequencer.testCasesList.each { TCMap ->
	
	GlobalVariable.CASDETESTPATTERN = TCMap.CDTPATTERN
	
	
	
	WebUI.callTestCase(findTestCase(TCMap.TCFULLNAME), [:], FailureHandling.STOP_ON_FAILURE)
	
	
	
}