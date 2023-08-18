import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.exception.KatalonRuntimeException
import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.io.FileType
import my.Log

/**
 * Unit tests
 * Lance tous les tests unitaires présent dans le dossier 'ZZ_TEST UNITAIRE'
 * Ne lance que les tests qui se termine par Tests
 * 
 * Les Tests Cases sont nommés avec le nom de la classe à tester suivi de 'Tests'
 * Les Tests Cases sont rangés dans des dossiers qui portent le même nom que les packages des classes à tester
 *
 * @author JM Lafarge
 * @since 1.0
 */

String tcTestFolder = 'ZZ_TEST UNITAIRE'
String tcTestPath = my.PropertiesReader.getMyProperty('TC_PATH') + File.separator + tcTestFolder

new File(tcTestPath).eachFileRecurse(FileType.FILES) { file ->

	Log.addTrace('file.getPath() : '+file.getPath())

	String TCFullName = file.getPath().replace(my.PropertiesReader.getMyProperty('TC_PATH') + File.separator, '').replace('.tc', '')
	String TCName= file.getName().replace('.tc','').split(' ')[0]

	//Ne lance que les tests qui se termine par Tests
	if (TCName.endsWith('Tests')) {

		try {
			
			String classTest = TCFullName.replace(tcTestFolder + File.separator, '').replace(File.separator,'.').replace('Tests', '')

			Log.addSubTITLE('TEST ' + classTest)
			
			WebUI.callTestCase(findTestCase(TCFullName), ['MYNAME':'JML'], FailureHandling.STOP_ON_FAILURE)

		} catch (StepErrorException  ex) {
			Log.addERROR("Erreur d'exécution du TestCase")
			Log.addDETAIL(ex.getMessage())
		} catch (Exception e) {
			Log.addERROR("Erreur TestCase")
			Log.addDETAIL(e.getMessage())
		} catch (StepFailedException exx) {
			Log.addERROR("StepFailedException")
			Log.addDETAIL(exx.getMessage())
		} catch (KatalonRuntimeException ke) {
			Log.addERROR("KatalonRuntimeException")
			Log.addDETAIL(ke.getMessage())
		}

	}
}
