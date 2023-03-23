import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.util.KeywordUtil
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
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	MYLOG.addSTEPGRP('ONGLET METIER')
	
		'Clic sur le bon onglet'
	    KW.scrollAndClick(myJDD,'a_Metier')
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD,'a_MetierSelected')
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				MYLOG.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
			'Ajout'
	        KW.scrollAndClick(myJDD,'a_AjouterMetier')
	
	        KW.delay(1)
	
	        KW.scrollAndSetText(myJDD,'SelectionMetier_input_Filtre', myJDD.getStrData('ID_CODMET'))
	
	        KW.delay(1)
	
	        KW.scrollAndClick(myJDD,'SelectionMetier_td')
			
			KW.scrollAndSetText(myJDD,'SelectionMetier_input_ST_NIV', myJDD.getStrData('ST_NIV'))
	
	        KW.scrollAndClick(myJDD,'SelectionMetier_button_Ajouter')
			
			KW.delay(1)
	
	        KW.waitAndVerifyElementText(myJDD,'ID_CODMET')
			
			if (!my.JDDKW.isNULL(myJDD.getData('DT_DATDEB'))) {
			
		        KW.scrollAndDoubleClick(myJDD,'td_DateDebut')
		
		        KW.delay(1)
				
				//
				// Le double Clic ne fonctionne pas sur Firefox --> voir pour le remplacer par Sélection puis F2
				//
				//
	
				KW.setDate(myJDD,'DT_DATDEB')
			}
	
			if (!my.JDDKW.isNULL(myJDD.getData('DT_DATFIN'))) {
				
		        KW.scrollAndClick(myJDD,'SelectionMetier_td')
		
		        KW.scrollAndDoubleClick(myJDD,'td_DateFin')
		
		        //WebUI.doubleClick(myJDD,'1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_DateFin', [('textHAB') : myJDD.getData('ID_CODHAB]))
		        //KW.delay(1)
	
		        KW.setDate(myJDD,'DT_DATFIN')
	
				KW.scrollAndClick(myJDD,'ID_CODMET')
			}
	    }// fin du for
	
	
	MYLOG.addSTEPGRP('CONTROLE')

		'Vérification des valeurs en BD'
		my.SQL.checkJDDWithBD(myJDD)
	
} // fin du if



