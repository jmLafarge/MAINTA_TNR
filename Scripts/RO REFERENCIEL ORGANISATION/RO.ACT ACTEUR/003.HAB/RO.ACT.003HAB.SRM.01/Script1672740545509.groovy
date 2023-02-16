import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.NAV




'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getData('ID_CODINT'))

    'Clic sur le bon onglet'
    KW.scrollAndClick(myJDD.makeTO('a_Habilitation'))

	'Vérification de l\'onglet'
	WebUI.waitForElementVisible(myJDD.makeTO('h4_Habilitation'),GlobalVariable.TIMEOUT)

	'Boucle sur les lignes d\'un même TC'
    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {

		'Début de sasie des valeurs du myJDD.data dans l\'écran'
        KW.waitForElementVisible(myJDD.makeTO('ID_CODHAB'), GlobalVariable.TIMEOUT)

        KW.scrollAndDoubleClick(myJDD.makeTO('td_DateDebut'))
		
		KW.waitForElementVisible(myJDD.makeTO('DT_DATDEB'),GlobalVariable.TIMEOUT)

        KW.setDate(myJDD.makeTO('DT_DATDEB'), myJDD.getData('DT_DATDEB',i))

        KW.scrollAndClick(myJDD.makeTO('ID_CODHAB'))

        KW.scrollAndDoubleClick(myJDD.makeTO('td_DateFin'))
		
		KW.waitForElementVisible(myJDD.makeTO('DT_DATFIN'),GlobalVariable.TIMEOUT)

        KW.setDate(myJDD.makeTO('DT_DATFIN'), myJDD.getData('DT_DATFIN',i))

        KW.scrollAndClick(myJDD.makeTO('ID_CODHAB'))
		
		
		'Vérification du test case - Controle des valeurs du myJDD.data en BD'
		
		// A FAIRE ****************************************************
		
		
    }// fin du for
	
	
	//Vérification en BD
	
} // fin du if



