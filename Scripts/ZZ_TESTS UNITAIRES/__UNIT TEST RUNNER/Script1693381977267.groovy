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
 * 		IMPORTANT : Pour passer la valeur null quand il n'y a qu'un seul paramètre, il faut caster ce null en tableau : maMethode.invoke(monObjet, [null] as Object[])
 * 
 *
 * @author JM Lafarge
 * @version 1.0
 */

String tcTestFolder = TNRPropertiesReader.getMyProperty('UNIT_TC_PATH')
String tcTestPath = TNRPropertiesReader.getMyProperty('TC_PATH') + File.separator + tcTestFolder

Log.addTITLE('TESTS UNITAIRES')

// Crée la liste des Tests case de Tests Unitaires
List <String,String> TCTestsFullnames =[]

Log.addINFO("Liste des Classes testées : ")
Log.addINFO("")
new File(tcTestPath).eachFileRecurse(FileType.FILES) { file ->
	
	Log.addTrace('file.getPath() : '+file.getPath())
	
	String TCName= file.getName().replace('.tc','').split(' ')[0]
	
	if (TCName.endsWith('Tests')) {
		String TCFullname = file.getPath().replace(TNRPropertiesReader.getMyProperty('TC_PATH') + File.separator, '').replace('.tc', '')
		Log.addDETAIL('- ' + getClassTest(TCFullname, tcTestFolder))
		TCTestsFullnames.add(TCFullname)
	}
}


int nbrClassTested =0
// Lance les Tests Unitaires 
TCTestsFullnames.each { TCTestsFullname ->
	try {
		Log.addSubTITLE('TEST ' + getClassTest(TCTestsFullname, tcTestFolder))
		WebUI.callTestCase(findTestCase(TCTestsFullname), ['MYNAME':'JML'], FailureHandling.STOP_ON_FAILURE)
		nbrClassTested++
	} catch (StepErrorException  ex) {
		Log.addErrorAndStop("Erreur d'exécution du TestCase : " + ex.getMessage() + addMsg(nbrClassTested,TCTestsFullnames.size()))

	} catch (Exception e) {
		Log.addErrorAndStop("Erreur TestCase : " + e.getMessage() + addMsg(nbrClassTested,TCTestsFullnames.size()))
	} catch (StepFailedException exx) {
		Log.addErrorAndStop("StepFailedException : " + exx.getMessage() + addMsg(nbrClassTested,TCTestsFullnames.size()))
	} catch (KatalonRuntimeException ke) {
		Log.addErrorAndStop("KatalonRuntimeException : " + ke.getMessage() + addMsg(nbrClassTested,TCTestsFullnames.size()))
	}
}
Log.addINFO(addMsg(nbrClassTested,TCTestsFullnames.size()))





/**
 * Retourne un message indiquant le nombre de classes testées par rapport au total.
 *
 * @param nbrClassTested Le nombre de classes qui ont été testées.
 * @param total          Le nombre total de classes.
 * @return               String indiquant le nombre de classes testées par rapport au total.
 */
private String addMsg(int nbrClassTested ,int total) {
	return "\nNombre de classes testées    : $nbrClassTested / $total"
}




/**
 * Retourne le nom du package.Class de la classe testée obtenu à partir du nom complet du Test case
 *
 * @param TCTestsFullname Le nom complet (chemin complet) du fichier de test.
 * @param tcTestFolder Le chemin du dossier contenant les tests.
 * @return le nom du package.Class de la classe testée.
 * 
 * Exemple d'utilisation :
 *  getClassTest("/mon/dossier/TestsMaClasse", "/mon/dossier") retournera "MaClasse"
 */
private String getClassTest(String TCTestsFullname, String tcTestFolder) {
    return TCTestsFullname.replace(tcTestFolder + File.separator, '')
                          .replace(File.separator, '.')
                          .replace('Tests', '')
}



