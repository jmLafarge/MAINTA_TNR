import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.exception.KatalonRuntimeException
import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.Log as MYLOG
import my.Sequencer
import my.result.ResultGenerator as MYRES
import my.InfoBDD
import my.NAV
import my.Tools
import my.TCFiles
import my.JDDFiles

if (InfoBDD.map.isEmpty()) { InfoBDD.load() }
if (TCFiles.TCfileMap.isEmpty()) { TCFiles.load() }
if (JDDFiles.JDDfilemap.isEmpty()) { JDDFiles.load() }
if (Sequencer.testCasesList.isEmpty()) { Sequencer.load() }

MYLOG.addTITLE("Lancement de TNR SEQUENCER")

MYRES.addStartInfo('TNR SEQUENCEUR')

Tools.addInfoContext()

if (NAV.myGlobalJDD == null) { NAV.loadJDDGLOBAL() }

Sequencer.testCasesList.each { TCMap ->
	
	
	GlobalVariable.CASDETESTPATTERN = TCMap.CDTPATTERN
			
	for(int i = 1 ; i <= TCMap.REP ;i++) {


		try {
			WebUI.callTestCase(findTestCase(TCMap.TCFULLNAME), ['MYNAME':'JML'], FailureHandling.STOP_ON_FAILURE)

		} catch (StepErrorException  ex) {
			MYLOG.addSTEPERROR("Erreur d'exécution du TestCase")
			MYLOG.addDETAIL(ex.getMessage())
		} catch (Exception e) {
			MYLOG.addSTEPERROR("Erreur TestCase")
			MYLOG.addDETAIL(e.getMessage())
		} catch (StepFailedException exx) {
			MYLOG.addSTEPERROR("StepFailedException")
			MYLOG.addDETAIL(exx.getMessage())
		} catch (KatalonRuntimeException ke) {
			MYLOG.addSTEPERROR("KatalonRuntimeException")
			MYLOG.addDETAIL(ke.getMessage())
		}
		
		// pour être sur en cas d'erreur du try/catch
		MYLOG.addEndTestCase()

	}	
}

MYRES.addEndInfo()
MYLOG.addINFO('')
MYLOG.addINFO('************  FIN  du test : TNR SEQUENCER ************')

MYRES.close()
