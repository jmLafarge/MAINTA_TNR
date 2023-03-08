import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

my.Result.addStartInfo()

my.Log.addSUBSTEP('INFO CONTEXTE')
my.Log.addDETAIL("Nom de l'OS".padRight(26) + System.getProperty("os.name"))
my.Log.addDETAIL("Version de l'OS".padRight(26) + System.getProperty("os.version"))
my.Log.addDETAIL("Architecture de l'OS".padRight(26) + System.getProperty("os.arch"))
my.Log.addDETAIL("Version de MAINTA".padRight(26) + my.SQL.getMaintaVersion())
my.Log.addDETAIL("Base de donnée".padRight(26) + GlobalVariable.BDD_URL)
my.Log.addINFO('')


my.Sequencer.testCasesList.each { TCMap ->
	
	GlobalVariable.CASDETESTENCOURS =TCMap.TCNAME
	
	for(int i = 1 ; i <= TCMap.REP ;i++) {
		
		my.Log.addStartTestCase(GlobalVariable.CASDETESTENCOURS)
		
		WebUI.callTestCase(findTestCase(TCMap.TCFULLNAME), [:])
		
		my.Log.addEndTestCase()
	}	
}

my.Result.addEndInfo()
