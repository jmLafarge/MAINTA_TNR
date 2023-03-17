import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.NAV



'Lecture du JDD'
def myJDD = new my.JDD()



'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0 ) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getData('ID_CODINT'))


	/*
	 * de temps en temps l'alert s'ouvre furtivement sans avoir le temps d'Accepter ! 
	 * --> Peut être dù au fait qu'il y ait d'autres webDrivers qui tournent
	 * 		-->ne pas oublier de faire  >Tools > Web > Terminate running WebDrivers
	 * 
	 * SOLUTION on boucle 3x 
	 */
	
	'Suppression'
	for ( n in 1..3) {
		my.Log.addSUBSTEP("Tentative de suppression $n/3" )
		KW.scrollAndClick(myJDD,'button_Supprimer')
		if (KW.waitAndAcceptAlert(GlobalVariable.TIMEOUT,null)) {
			KW.delay(1)
			'Vérification du test case - écran'
			NAV.verifierEcranGrille()
			break
		}
	}
	
	
	'Vérification en BD que l\'objet n\'existe plus'
	my.SQL.checkIDNotInBD(myJDD)
	
} // fin du if


