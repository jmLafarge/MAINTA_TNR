import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.NAV
import my.Log as MYLOG
import my.JDD

'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	MYLOG.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	MYLOG.addSTEPGRP('ONGLET ZONE')
	
		KW.scrollAndClick(myJDD,"tab_Zone")
		KW.waitForElementVisible(myJDD,"tab_ZoneSelected")

		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				MYLOG.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			'Ajout'
			KW.scrollAndClick(myJDD,'a_AjouterEmplacement')
	
			KW.delay(1)
			
			myJDD.setCasDeTestNum(i)
	
	        KW.scrollAndSetText(myJDD,'SelectionEmplacement_input_Filtre', myJDD.getStrData('ID_NUMREF'))
	
	       // KW.delay(1)
	
	        KW.scrollAndClick(myJDD,'SelectionEmplacement_td')

			myJDD.readSEQUENCID()
	
	        KW.scrollAndClick(myJDD,'SelectionEmplacement_button_Ajouter')
			
			KW.delay(1)
			
			KW.scrollAndClick(myJDD,'SelectionEmplacement_button_Fermer')
			
			KW.delay(1)
	
	        if (KW.waitAndVerifyElementText(myJDD,'ID_NUMREF')) {
				myJDD.replaceSEQUENCIDInJDD()
			}

			
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
				
		        KW.scrollAndClick(myJDD,'SelectionEmplacement_td')
		
		        KW.scrollAndDoubleClick(myJDD,'td_DateFin')
		
		        //WebUI.doubleClick(myJDD,'1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_DateFin', [('textHAB') : myJDD.getData('ID_CODHAB]))
		        //KW.delay(1)
	
		        KW.setDate(myJDD,'DT_DATFIN')
	
				KW.scrollAndClick(myJDD,'ID_NUMREF')
			}
			

	    }// fin du for
	
		MYLOG.addSTEPACTION('CONTROLE')
	
			'Vérification des valeurs en BD'
			my.SQL.checkJDDWithBD(myJDD)			
				
		MYLOG.addEndTestCase()

} // fin du if



