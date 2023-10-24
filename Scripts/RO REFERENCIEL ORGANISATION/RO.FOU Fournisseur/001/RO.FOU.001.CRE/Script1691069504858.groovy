import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLCreate(); STEP.checkCreateScreen()
	

	
	TNRResult.addSTEPGRP("ONGLET FOURNISSEUR")
	
		STEP.simpleClick(myJDD,"tab_Fournisseur")
		STEP.verifyElementVisible(myJDD,"tab_FournisseurSelected")
		
		STEP.setText(myJDD,"ID_CODFOU")
		STEP.setText(myJDD,"ST_NOM")
		STEP.setText(myJDD,"ID_CODGES")
		//ST_DESGES --> pas d'action en création
		STEP.clickCheckboxIfNeeded(myJDD,"ST_CONSTR","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_INA","O")
		STEP.setText(myJDD,"ST_CODCOM")
		//ST_DESST_CODCOM --> pas d'action en création
		
		//TNRResult.addSTEPBLOCK("ADRESSE")
		
		TNRResult.addSTEPBLOCK("CONTACT")
		STEP.setText(myJDD,"ST_TELPHO")
		STEP.setText(myJDD,"ST_CON")
		STEP.setText(myJDD,"ST_TELMOB")
		STEP.setText(myJDD,"ST_EMA")
		STEP.setText(myJDD,"ST_TELCOP")
		STEP.setText(myJDD,"ST_TELEX")
		
	TNRResult.addSTEPGRP("ONGLET COMMANDE")
		
		STEP.simpleClick(myJDD,"tab_Commande")
		STEP.verifyElementVisible(myJDD,"tab_CommandeSelected")
		
		STEP.setText(myJDD,"ST_PRIDEL")
		STEP.setText(myJDD,"ID_CODPAI")
		//ST_DESID_CODPAI --> pas d'action en création
		STEP.setText(myJDD,"ST_NOTPRO")
		STEP.setText(myJDD,"ID_CODMOD")
		//ST_DESID_CODMOD --> pas d'action en création
		STEP.setText(myJDD,"ID_CODDEV")
		//ST_DESID_CODDEV --> pas d'action en création
		STEP.setText(myJDD,"ID_CODPOR")
		//ST_DESID_CODPOR --> pas d'action en création
		STEP.setText(myJDD,"ID_CODEMB")
		//ST_DESID_CODEMB --> pas d'action en création
		STEP.setText(myJDD,"ST_REL")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_FIGCAT","O")
		
		TNRResult.addSTEPBLOCK("TEXTES COMMANDE")
		STEP.setText(myJDD,"ST_TXTBAS1")
		STEP.setText(myJDD,"ST_TXTBAS2")
		STEP.setText(myJDD,"ST_TXTBAS3")
		STEP.setText(myJDD,"ST_TXTBAS4")
		STEP.setText(myJDD,"ST_TXTBAS5")
		STEP.setText(myJDD,"ST_TXTBAS6")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_FIGCDE","O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		STEP.simpleClick(myJDD,"tab_Notes")
		STEP.verifyElementVisible(myJDD,"tab_NotesSelected")
		
		// Pas de cas de test pour Notes
	
	
				
	TNRResult.addSTEPACTION('VALIDATION')

	    STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(myJDD.getStrData())
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)
		//JDD_Note.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)
		
		STEP.checkJDDWithBD(myJDD)
		//STEP.checkJDDWithBD(JDD_Note)

	TNRResult.addEndTestCase()
} // fin du if



