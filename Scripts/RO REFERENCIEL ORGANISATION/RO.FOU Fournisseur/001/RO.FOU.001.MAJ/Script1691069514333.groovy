import internal.GlobalVariable
import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; 
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrWebUI.*






'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(myJDD.getStrData()); STEP.checkReadUpdateDeleteScreen(myJDD.getStrData())


	TNRResult.addSTEPGRP("ONGLET FOURNISSEUR")
	
		STEP.simpleClick(myJDD,"tab_Fournisseur")
		STEP.verifyElementVisible(myJDD,"tab_FournisseurSelected")
		//STEP.setText(myJDD, "ID_CODFOU")
		STEP.setText(myJDD, "ST_NOM")
		STEP.searchWithHelper(myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		STEP.clickCheckboxIfNeeded(myJDD,"ST_CONSTR","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_INA","O")
		STEP.searchWithHelper(myJDD, "ST_CODCOM","","SEARCH_ID_CODCMP") //specific
		//ST_DESST_CODCOM --> pas d'action en modification
	
		
		TNRResult.addSTEPBLOCK("ADRESSE")
		
		JDD JDD_Adr = new JDD(JDDFileMapper.getFullnameFromModObj('RO.ADR'),'001',GlobalVariable.CAS_DE_TEST_EN_COURS)
		
		STEP.simpleClick(myJDD,"TD_Adresse")
		STEP.simpleClick(myJDD,"BTN_ModifierAdresse")
		
		if (STEP.isDivModalOpened('Adresse')) {
		
			//STEP.clickCheckboxIfNeeded(JDD_Adr,"ST_ADRPAR","O")
			STEP.setText(JDD_Adr, "ST_ADR")
			STEP.setText(JDD_Adr, "ST_ADRCOM")
			STEP.setText(JDD_Adr, "ST_ADRCOM2")
			STEP.setText(JDD_Adr, "ST_MENSPE")
			STEP.setText(JDD_Adr, "ST_CODPOS")
			STEP.setText(JDD_Adr, "ST_VIL")
			STEP.setText(JDD_Adr, "ST_CEDEX")
			STEP.setText(JDD_Adr, "NU_CEDEX")
			STEP.setText(JDD_Adr, "ID_CODPAY")
			STEP.setText(JDD_Adr, "ST_PAY")
			STEP.setText(JDD_Adr, "ST_REFEXT")
			STEP.setText(JDD_Adr, "ST_RELAPA")
			STEP.setText(JDD_Adr, "ST_GPS")
			
			STEP.simpleClick(JDD_Adr,"BTN_Valider")
			
			STEP.isDivModalClosed('Adresse')
		}
		
		TNRResult.addSTEPBLOCK("CONTACT")
		STEP.setText(myJDD, "ST_TELPHO")
		STEP.setText(myJDD, "ST_CON")
		STEP.setText(myJDD, "ST_TELMOB")
		STEP.setText(myJDD, "ST_EMA")
		STEP.setText(myJDD, "ST_TELCOP")
		STEP.setText(myJDD, "ST_TELEX")
		
	TNRResult.addSTEPGRP("ONGLET COMMANDE")
		
		STEP.simpleClick(myJDD,"tab_Commande")
		STEP.verifyElementVisible(myJDD,"tab_CommandeSelected")
		
		STEP.setText(myJDD, "ST_PRIDEL")
		STEP.searchWithHelper(myJDD, "ID_CODPAI","","")
		//ST_DESID_CODPAI --> pas d'action en modification
		STEP.setText(myJDD, "ST_NOTPRO")
		STEP.searchWithHelper(myJDD, "ID_CODMOD","","")
		//ST_DESID_CODMOD --> pas d'action en modification
		STEP.searchWithHelper(myJDD, "ID_CODDEV","","")
		//ST_DESID_CODDEV --> pas d'action en modification
		STEP.searchWithHelper(myJDD, "ID_CODPOR","","")
		//ST_DESID_CODPOR --> pas d'action en modification
		STEP.searchWithHelper(myJDD, "ID_CODEMB","","")
		//ST_DESID_CODEMB --> pas d'action en modification
		STEP.setText(myJDD, "ST_REL")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_FIGCAT","O")
		
		TNRResult.addSTEPBLOCK("TEXTES COMMANDE")
		STEP.setText(myJDD, "ST_TXTBAS1")
		STEP.setText(myJDD, "ST_TXTBAS2")
		STEP.setText(myJDD, "ST_TXTBAS3")
		STEP.setText(myJDD, "ST_TXTBAS4")
		STEP.setText(myJDD, "ST_TXTBAS5")
		STEP.setText(myJDD, "ST_TXTBAS6")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_FIGCDE","O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
	
		STEP.simpleClick(myJDD,"tab_Notes")
		STEP.verifyElementVisible(myJDD,"tab_NotesSelected")
		
		JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RO.FOU'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
		STEP.scrollToPosition( 0, 0)
		STEP.setMemoText(JDD_Note.getStrData("OL_DOC"), 'Notes',true,myJDD,'')
	
	  		 
		
	TNRResult.addSTEPACTION('VALIDATION')
	
	    STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Valider')
	
		STEP.checkResultScreen(myJDD.getStrData())
	
		
		STEP.checkJDDWithBD(myJDD)
		STEP.checkJDDWithBD(JDD_Note)
		STEP.checkJDDWithBD(JDD_Adr)
	
	//TNRResult.addEndTestCase()
} // fin du if


