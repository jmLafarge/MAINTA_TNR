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
	
	
	// Navigation vers la bonne URL

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getData('ID_CODINT'))
	
	'Clic sur le bon onglet'
    KW.scrollAndClick(myJDD.makeTO('a_Habilitation'))
	
	'Vérification de l\'onglet'
    KW.waitForElementVisible(myJDD.makeTO('h4_Habilitation'),GlobalVariable.TIMEOUT)

	'Boucle sur les lignes d\'un même TC'
    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {

		'Ajout'
        KW.scrollAndClick(myJDD.makeTO('a_AjouterHabilitation'))

        WebUI.delay(1)

        KW.setText(myJDD.makeTO('SelectionHabilitation_input_Filtre'), myJDD.getData('ID_CODHAB',i))

        WebUI.delay(1)

        KW.scrollAndClick(myJDD.makeTO('SelectionHabilitation_td'))

        KW.scrollAndClick(myJDD.makeTO('SelectionHabilitation_button_Ajouter'))

        KW.waitForElementVisible(myJDD.makeTO('ID_CODHAB'),GlobalVariable.TIMEOUT)
		
		if (myJDD.getData('DT_DATDEB',i) != my.PropertiesReader.getMyProperty('JDD_KEYWORD_VIDE')) {
		
	        KW.scrollAndDoubleClick(myJDD.makeTO('td_DateDebut'))
	
	        WebUI.delay(1)
			
			//
			// Le double Clic ne fonctionne pas sur Firefox --> voir pour le remplacer par Sélection puis F2
			//
			//

			KW.setDate(myJDD.makeTO('DT_DATDEB'), myJDD.getData('DT_DATDEB',i))
		}

		if (myJDD.getData('DT_DATFIN',i) != my.PropertiesReader.getMyProperty('JDD_KEYWORD_VIDE')) {
			
	        KW.scrollAndClick(myJDD.makeTO('SelectionHabilitation_td'))
	
	        KW.scrollAndDoubleClick(myJDD.makeTO('td_DateFin'))
	
	        //WebUI.doubleClick(myJDD.makeTO('1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_DateFin', [('textHAB') : myJDD.getData('ID_CODHAB]))
	        //WebUI.delay(1)

	        KW.setDate(myJDD.makeTO('DT_DATFIN'), myJDD.getData('DT_DATFIN',i))

			KW.scrollAndClick(myJDD.makeTO('ID_CODHAB'))
		}
		
    }// fin du for
	
	
	//Vérification en BD
	
} // fin du if



