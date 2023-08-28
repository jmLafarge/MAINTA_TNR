import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.exception.KatalonRuntimeException
import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.io.FileType
import tnrCommon.TNRPropertiesReader
import tnrLog.Log

/**
 * UNIT TEST RUNNER
 * 
 * Lance tous les tests unitaires présent dans le dossier 'ZZ_TEST UNITAIRE'
 * Ne lance que les tests qui se terminent par 'Tests'.
 * 
 * Les tests unitaires sont nommés avec le nom de la classe à tester suivi de 'Tests'
 * Les tests unitaires sont rangés dans des dossiers qui portent le même nom que les packages des classes à tester
 * 
 * Les tests KO ne provoquent pas d'erreur Katalon (en rouge dans Log Viewer) mais des Warning (en jaune dans Log Viewer).
 * --> ce qui permet de différencier les erreurs de code, des tests KO.
 * 
 * Dans les Log.txt, les tests OK apparissent avec un status :), les tests KO apparaissent avec un status KO.
 * --> ce qui permet de différencier les FAIL(Log potentiellemnt normal suivant les tests éffectués), des tests KO.
 * 
 * Les tests unitaires ne contrôlent que le résultat de la fonction testée. Les éventuels traces dans les Log sont à contrôles visuellement.
 * 
 * 
 * Pour tester 
 * 
 * 		Log.addAssert("Un message pour expliquer de test" , la valeur de retour attendue , la fonction a testé)
 * 
 * Pour tester les fonctions private
 * 
 * 		import java.lang.reflect.Method
 * 
 * 		Method method = LaClassATesté.class.getDeclaredMethod("La methode private", énumérer les class des paramètres ex : List.class, String.class, Object.class)
 * 
 * 		method.setAccessible(true)
 * 
 * 		Log.addAssert("(private) Un message pour expliquer de test" , la valeur de retour attendue ,  method.invoke(instance de LaClassATesté (ou la LaClassATesté si static), énumérer les valeurs des paramètres ex : ['truc','machin','chose'], 'INTER','ID_CODINT'))
 * 
 * 
 *
 * @author JM Lafarge
 * @version 1.0
 */

String tcTestFolder = 'ZZ_UNIT TESTS'
String tcTestPath = TNRPropertiesReader.getMyProperty('TC_PATH') + File.separator + tcTestFolder

new File(tcTestPath).eachFileRecurse(FileType.FILES) { file ->

	Log.addTrace('file.getPath() : '+file.getPath())

	String TCFullName = file.getPath().replace(TNRPropertiesReader.getMyProperty('TC_PATH') + File.separator, '').replace('.tc', '')
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
