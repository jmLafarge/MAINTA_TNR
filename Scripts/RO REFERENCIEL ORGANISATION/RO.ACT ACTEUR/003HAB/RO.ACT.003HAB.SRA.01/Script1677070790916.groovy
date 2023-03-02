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
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	my.Log.addSTEPGRP('ONGLET HABILITATION')
	
		'Clic sur le bon onglet'
	    KW.scrollAndClick(myJDD.makeTO('a_Habilitation'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_HabilitationSelected'))
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				my.Log.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
			'Ajout'
	        KW.scrollAndClick(myJDD.makeTO('a_AjouterHabilitation'))
	
	        WebUI.delay(1)
	
	        KW.scrollAndSetText(myJDD.makeTO('SelectionHabilitation_input_Filtre'), myJDD.getStrData('ID_CODHAB'))
	
	        WebUI.delay(1)
	
	        KW.scrollAndClick(myJDD.makeTO('SelectionHabilitation_td'))
	
	        KW.scrollAndClick(myJDD.makeTO('SelectionHabilitation_button_Ajouter'))
			
			WebUI.delay(1)
	
	        KW.waitAndVerifyElementText(myJDD.makeTO('ID_CODHAB'), myJDD.getStrData('ID_CODHAB'))
			
			if (!my.JDDKW.isNULL(myJDD.getData('DT_DATDEB'))) {
			
		        KW.scrollAndDoubleClick(myJDD.makeTO('td_DateDebut'))
		
		        WebUI.delay(1)
				
				//
				// Le double Clic ne fonctionne pas sur Firefox --> voir pour le remplacer par Sélection puis F2
				//
				//
	
				KW.setDate(myJDD.makeTO('DT_DATDEB'), myJDD.getData('DT_DATDEB'))
			}
	
			if (!my.JDDKW.isNULL(myJDD.getData('DT_DATFIN'))) {
				
		        KW.scrollAndClick(myJDD.makeTO('SelectionHabilitation_td'))
		
		        KW.scrollAndDoubleClick(myJDD.makeTO('td_DateFin'))
		
		        //WebUI.doubleClick(myJDD.makeTO('1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_DateFin', [('textHAB') : myJDD.getData('ID_CODHAB]))
		        //WebUI.delay(1)
	
		        KW.setDate(myJDD.makeTO('DT_DATFIN'), myJDD.getData('DT_DATFIN'))
	
				KW.scrollAndClick(myJDD.makeTO('ID_CODHAB'))
			}
	    }// fin du for
	
	
	my.Log.addSTEPGRP('CONTROLE')

		'Vérification des valeurs en BD'
		my.SQL.checkJDDWithBD(myJDD)
	
} // fin du if



