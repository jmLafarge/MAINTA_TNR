import my.KW
import my.NAV
import my.SQL
import myJDDManager.JDD
import myJDDManager.JDDKW
import myResult.TNRResult

'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET ZONE')
	
		//KW.scrollAndClick(myJDD,"tab_Zone")
		KW.click(myJDD,"tab_Zone")
		KW.waitForElementVisible(myJDD,"tab_ZoneSelected")

		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			'Ajout'
			KW.scrollAndClick(myJDD,'a_AjouterEmplacement')
	
			KW.delay(1)
			
			myJDD.setCasDeTestNum(i)
	
	        KW.scrollAndSetText(myJDD,'SelectionEmplacement_input_Filtre', myJDD.getStrData('ID_NUMREF'))
	
	        KW.scrollAndClick(myJDD,'SelectionEmplacement_td')
	
	        KW.scrollAndClick(myJDD,'SelectionEmplacement_button_Ajouter')
			
			KW.scrollAndClick(myJDD,'SelectionEmplacement_button_Fermer')
			
			KW.delay(1)
	
	        if (KW.waitAndVerifyElementText(myJDD,'ID_NUMREF')) {
				myJDD.replaceSEQUENCIDInJDD('ID_NUMZONLIG')
			}else {
				TNRResult.addDETAIL("Impossible de remplacer SEQUENCEID par ID_NUMREF dans JDD")
			}

			
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
				
		        KW.scrollAndClick(myJDD,'SelectionEmplacement_td')
		
		        KW.scrollAndDoubleClick(myJDD,'td_DateFin')
		
		        //WebUI.doubleClick(myJDD,'1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_DateFin', [('textHAB') : myJDD.getData('ID_CODHAB]))
		        //KW.delay(1)
	
		        KW.setDate(myJDD,'DT_DATFIN')
	
				KW.scrollAndClick(myJDD,'ID_NUMREF')
			}
			

	    }// fin du for
	
		TNRResult.addSTEPACTION('CONTROLE')
	
			'Vérification des valeurs en BD'
			SQL.checkJDDWithBD(myJDD)			
				
		TNRResult.addEndTestCase()

} // fin du if



