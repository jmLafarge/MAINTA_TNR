import tnrWebUI.KW
import tnrWebUI.NAV
import tnrSqlManager.SQL
import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP("ONGLET HABILITATION")

		//KW.scrollAndClick(myJDD,"tab_Habilitation")
		KW.scrollAndClick(myJDD,"tab_Habilitation")
		KW.waitForElementVisible(myJDD,"tab_HabilitationSelected")
		
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
			'Ajout'
	        KW.scrollAndClick(myJDD,'a_AjouterHabilitation')
	
	        KW.delay(1)
	
	        KW.scrollAndSetText(myJDD,'SelectionHabilitation_input_Filtre', myJDD.getStrData('ID_CODHAB'))
	
	        //KW.delay(1)
	
	        KW.scrollAndClick(myJDD,'SelectionHabilitation_td')
	
	        KW.scrollAndClick(myJDD,'SelectionHabilitation_button_Ajouter')
			
			KW.delay(1)
	
	        KW.waitAndVerifyElementText(myJDD,'ID_CODHAB')
			
			if (!JDDKW.isNULL(myJDD.getData('DT_DATDEB'))) {
			
		        KW.scrollAndDoubleClick(myJDD,'td_DateDebut')
		
		        KW.delay(1)
				
				//
				// Le double Clic ne fonctionne pas sur Firefox --> voir pour le remplacer par Sélection puis F2
				//
				//
	
				KW.setDate(myJDD,'DT_DATDEB')
			}
	
			if (!JDDKW.isNULL(myJDD.getData('DT_DATFIN'))) {
				
		        KW.scrollAndClick(myJDD,'SelectionHabilitation_td')
		
		        KW.scrollAndDoubleClick(myJDD,'td_DateFin')
		
		        //WebUI.doubleClick(myJDD,'1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_DateFin', [('textHAB') : myJDD.getData('ID_CODHAB]))
		        //KW.delay(1)
	
		        KW.setDate(myJDD,'DT_DATFIN')
	
				KW.scrollAndClick(myJDD,'ID_CODHAB')
			}
	    }// fin du for
	
	
	TNRResult.addSTEPACTION('CONTROLE')

		'Vérification des valeurs en BD'
		SQL.checkJDDWithBD(myJDD)		
		
	TNRResult.addEndTestCase()
} // fin du if



