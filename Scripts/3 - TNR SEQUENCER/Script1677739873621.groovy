import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

my.Result.addStartInfo()

my.Sequencer.testCasesList.each { TCMap ->
	
	GlobalVariable.CASDETESTENCOURS =TCMap.TCNAME
	
	for(int i = 1 ; i <= TCMap.REP ;i++) {
		
		my.Log.addStartTestCase(GlobalVariable.CASDETESTENCOURS)
		
		WebUI.callTestCase(findTestCase(TCMap.TCFULLNAME), [:])
		
		my.Log.addEndTestCase()
	}	
}

my.Result.addEndInfo()
