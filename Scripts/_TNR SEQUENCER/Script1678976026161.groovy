import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.exception.KatalonRuntimeException
import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable




my.Sequencer.testCasesList.each { TCMap ->
	
	GlobalVariable.CASDETESTENCOURS =TCMap.TCNAME
	
	for(int i = 1 ; i <= TCMap.REP ;i++) {
		
		my.Log.addStartTestCase(GlobalVariable.CASDETESTENCOURS)
		
		try {
			WebUI.callTestCase(findTestCase(TCMap.TCFULLNAME), [:], FailureHandling.STOP_ON_FAILURE)
		 
		} catch (StepErrorException  ex) {
			my.Log.addERROR("Erreur d'exécution du TestCase")
			my.Log.addDETAIL(ex.getMessage())
		} catch (Exception e) {
			my.Log.addERROR("Erreur TestCase")
			my.Log.addDETAIL(e.getMessage())
		} catch (StepFailedException exx) {
			my.Log.addERROR("StepFailedException")
			my.Log.addDETAIL(exx.getMessage())
		} catch (KatalonRuntimeException ke) {
			my.Log.addERROR("KatalonRuntimeException")
			my.Log.addDETAIL(ke.getMessage())
		}
		
		my.Log.addEndTestCase()
	}	
}

my.Result.addEndInfo()
