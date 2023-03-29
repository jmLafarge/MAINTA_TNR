import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.exception.KatalonRuntimeException
import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.Log as MYLOG
import my.Result
import my.Sequencer





Sequencer.testCasesList.each { TCMap ->
	
	GlobalVariable.CASDETESTENCOURS =TCMap.TCNAME
	
	for(int i = 1 ; i <= TCMap.REP ;i++) {
		
		MYLOG.addStartTestCase(GlobalVariable.CASDETESTENCOURS)

		try {
			WebUI.callTestCase(findTestCase(TCMap.TCFULLNAME), [:], FailureHandling.STOP_ON_FAILURE)

		} catch (StepErrorException  ex) {
			MYLOG.addERROR("Erreur d'exécution du TestCase")
			MYLOG.addDETAIL(ex.getMessage())
		} catch (Exception e) {
			MYLOG.addERROR("Erreur TestCase")
			MYLOG.addDETAIL(e.getMessage())
		} catch (StepFailedException exx) {
			MYLOG.addERROR("StepFailedException")
			MYLOG.addDETAIL(exx.getMessage())
		} catch (KatalonRuntimeException ke) {
			MYLOG.addERROR("KatalonRuntimeException")
			MYLOG.addDETAIL(ke.getMessage())
		}

		
		MYLOG.addEndTestCase()
	}	
}


Result.addEndInfo()
