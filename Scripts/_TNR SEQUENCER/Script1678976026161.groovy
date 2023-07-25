
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.exception.KatalonRuntimeException
import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.InfoBDD
import my.JDDFiles
import my.Log
import my.NAV
import my.Sequencer
import my.TCFiles
import my.Tools
import my.result.TNRResult

//JML InfoBDD.load() 
//JML TCFiles.load()
//JML JDDFiles.load()
//JML Sequencer.load()

Log.addTITLE("Lancement de TNR SEQUENCER")

TNRResult.addStartInfo('TNR SEQUENCEUR')

Tools.addInfoContext()

Sequencer.testCasesList.each { TCMap ->
	
	
	GlobalVariable.CASDETESTPATTERN = TCMap.CDTPATTERN
			
	for(int i = 1 ; i <= TCMap.REP ;i++) {


		try {
			WebUI.callTestCase(findTestCase(TCMap.TCFULLNAME), ['MYNAME':'JML'], FailureHandling.STOP_ON_FAILURE)

		} catch (StepErrorException  ex) {
			TNRResult.addSTEPERROR("Erreur d'exécution du TestCase")
			TNRResult.addDETAIL(ex.getMessage())
		} catch (Exception e) {
			TNRResult.addSTEPERROR("Erreur TestCase")
			TNRResult.addDETAIL(e.getMessage())
		} catch (StepFailedException exx) {
			TNRResult.addSTEPERROR("StepFailedException")
			TNRResult.addDETAIL(exx.getMessage())
		} catch (KatalonRuntimeException ke) {
			TNRResult.addSTEPERROR("KatalonRuntimeException")
			TNRResult.addDETAIL(ke.getMessage())
		}
		
		// pour être sur en cas d'erreur du try/catch
		TNRResult.addEndTestCase()

	}	
}

TNRResult.close('TNR SEQUENCER')

