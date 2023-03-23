import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.util.KeywordUtil as KU
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.NAV
import my.Log as MYLOG


'Lecture du JDD'
def myJDD = new my.JDD()
		
		
'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Creation_and_checkCartridge()
	
	MYLOG.addSTEPGRP('ONGLET ')

	'Clic sur le bon onglet'
	KW.scrollAndClick(myJDD,'xxxxxxxxxxxxx')
	
	'Vérification de l\'onglet'
	KW.waitForElementVisible(myJDD,'xxxxxxxxxxxxxxxxxx')

	'Début de sasie des valeurs du JDD'
	
	
	
				
	MYLOG.addSTEPGRP('VALIDATION')
		
    'Validation de la saisie'
    KW.scrollAndClick(myJDD,'button_Valider')

    'Vérification du test case - écran résulat'
    NAV.verifierEcranResultat()
		
    KW.verifyElementText(myJDD,'a_Resultat_ID', myJDD.getStrData('ID_CODFOU'))
	
	'Vérification des valeurs en BD'
	my.SQL.checkJDDWithBD(myJDD)

} // fin du if



