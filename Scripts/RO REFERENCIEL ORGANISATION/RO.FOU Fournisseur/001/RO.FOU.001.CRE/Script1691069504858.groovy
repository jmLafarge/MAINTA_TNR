import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*


'Lecture du JDD'
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP_NAV.goToURL_Creation_and_checkCartridge(1)
	

	
	TNRResult.addSTEPGRP("ONGLET FOURNISSEUR")
	
		STEP.click(0, myJDD,"tab_Fournisseur")
		STEP.verifyElementVisible(0, myJDD,"tab_FournisseurSelected")
		
		STEP.setText(0, myJDD,"ID_CODFOU")
		STEP.setText(0, myJDD,"ST_NOM")
		STEP.setText(0, myJDD,"ID_CODGES")
		//ST_DESGES --> pas d'action en création
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_CONSTR","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		STEP.setText(0, myJDD,"ST_CODCOM")
		//ST_DESST_CODCOM --> pas d'action en création
		
		//TNRResult.addSTEPBLOCK("ADRESSE")
		
		TNRResult.addSTEPBLOCK("CONTACT")
		STEP.setText(0, myJDD,"ST_TELPHO")
		STEP.setText(0, myJDD,"ST_CON")
		STEP.setText(0, myJDD,"ST_TELMOB")
		STEP.setText(0, myJDD,"ST_EMA")
		STEP.setText(0, myJDD,"ST_TELCOP")
		STEP.setText(0, myJDD,"ST_TELEX")
		
	TNRResult.addSTEPGRP("ONGLET COMMANDE")
		
		STEP.click(0, myJDD,"tab_Commande")
		STEP.verifyElementVisible(0, myJDD,"tab_CommandeSelected")
		
		STEP.setText(0, myJDD,"ST_PRIDEL")
		STEP.setText(0, myJDD,"ID_CODPAI")
		//ST_DESID_CODPAI --> pas d'action en création
		STEP.setText(0, myJDD,"ST_NOTPRO")
		STEP.setText(0, myJDD,"ID_CODMOD")
		//ST_DESID_CODMOD --> pas d'action en création
		STEP.setText(0, myJDD,"ID_CODDEV")
		//ST_DESID_CODDEV --> pas d'action en création
		STEP.setText(0, myJDD,"ID_CODPOR")
		//ST_DESID_CODPOR --> pas d'action en création
		STEP.setText(0, myJDD,"ID_CODEMB")
		//ST_DESID_CODEMB --> pas d'action en création
		STEP.setText(0, myJDD,"ST_REL")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_FIGCAT","O")
		
		TNRResult.addSTEPBLOCK("TEXTES COMMANDE")
		STEP.setText(0, myJDD,"ST_TXTBAS1")
		STEP.setText(0, myJDD,"ST_TXTBAS2")
		STEP.setText(0, myJDD,"ST_TXTBAS3")
		STEP.setText(0, myJDD,"ST_TXTBAS4")
		STEP.setText(0, myJDD,"ST_TXTBAS5")
		STEP.setText(0, myJDD,"ST_TXTBAS6")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_FIGCDE","O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		STEP.click(0, myJDD,"tab_Notes")
		STEP.verifyElementVisible(0, myJDD,"tab_NotesSelected")
		
		// Pas de cas de test pour Notes
	
	
				
	TNRResult.addSTEPACTION('VALIDATION')

	    STEP.click(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)
		//JDD_Note.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)
		
		SQL.checkJDDWithBD(myJDD)
		//SQL.checkJDDWithBD(JDD_Note)

	TNRResult.addEndTestCase()
} // fin du if



