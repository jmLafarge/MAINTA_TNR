import tnrWebUI.KW
import tnrWebUI.NAV
import tnrSqlManager.SQL
import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrResultManager.TNRResult


'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET METIER')
	
		//KW.scrollAndClick(myJDD,"Tab_Metier")
		KW.scrollAndClick(myJDD,"Tab_Metier")
		KW.waitForElementVisible(myJDD,"Tab_MetierSelected")
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			KW.scrollToPositionAndWait(0,0,1)
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
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
				
		        KW.scrollAndClick(myJDD,'SelectionMetier_td')
		
		        KW.scrollAndDoubleClick(myJDD,'td_DateFin')
		
		        //WebUI.doubleClick(myJDD,'1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_DateFin', [('textHAB') : myJDD.getData('ID_CODHAB]))
		        //KW.delay(1)
	
		        KW.setDate(myJDD,'DT_DATFIN')
	
				KW.scrollAndClick(myJDD,'ID_CODMET')
			}
	    }// fin du for
	
	
	TNRResult.addSTEPACTION('CONTROLE')

		'Vérification des valeurs en BD'
		SQL.checkJDDWithBD(myJDD)
	
	TNRResult.addEndTestCase()
} // fin du if



