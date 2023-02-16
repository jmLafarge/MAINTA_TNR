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
    NAV.goToURL_Grille_and_checkCartridge()
	
	'Début de sasie des valeurs du JDD dans l\'écran'
    KW.scrollAndSetText(myJDD.makeTO('input_Filtre_Acteur'), myJDD.getData('ID_CODINT'))

	'mise à jour dynamique du xpath'
	'Vérification des valeurs'
	KW.waitAndVerifyText( myJDD.makeTO('td_Grille_Acteur'), myJDD.getData('ID_CODINT'))

} // fin du if




