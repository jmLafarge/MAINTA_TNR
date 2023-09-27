import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*






'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData()); STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData())


	TNRResult.addSTEPGRP("ONGLET FOURNISSEUR")
	
		STEP.simpleClick(0, myJDD,"tab_Fournisseur")
		STEP.verifyElementVisible(0, myJDD,"tab_FournisseurSelected")
		
		//STEP.setText(0, myJDD, "ID_CODFOU")
		STEP.setText(0, myJDD, "ST_NOM")
		STEP.searchWithHelper(0, myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		STEP.scrollAndCheckIfNeeded(0, myJDD,"ST_CONSTR","O")
		STEP.scrollAndCheckIfNeeded(0, myJDD,"ST_INA","O")
		STEP.searchWithHelper(0, myJDD, "ST_CODCOM","","SEARCH_ID_CODCMP") //specific
		//ST_DESST_CODCOM --> pas d'action en modification
	
		
		TNRResult.addSTEPBLOCK("ADRESSE")
		
		JDD JDD_Adr = new JDD(JDDFileMapper.getFullnameFromModObj('RO.ADR'),'001',GlobalVariable.CAS_DE_TEST_EN_COURS)
		
		STEP.simpleClick(0, myJDD,"TD_Adresse")
		STEP.simpleClick(0, myJDD,"BTN_ModifierAdresse")
		
		if (STEP.isDivModalOpened(0)) {
		
			//STEP.scrollAndCheckIfNeeded(0, JDD_Adr,"ST_ADRPAR","O")
			STEP.setText(0, JDD_Adr, "ST_ADR")
			STEP.setText(0, JDD_Adr, "ST_ADRCOM")
			STEP.setText(0, JDD_Adr, "ST_ADRCOM2")
			STEP.setText(0, JDD_Adr, "ST_MENSPE")
			STEP.setText(0, JDD_Adr, "ST_CODPOS")
			STEP.setText(0, JDD_Adr, "ST_VIL")
			STEP.setText(0, JDD_Adr, "ST_CEDEX")
			STEP.setText(0, JDD_Adr, "NU_CEDEX")
			STEP.setText(0, JDD_Adr, "ID_CODPAY")
			STEP.setText(0, JDD_Adr, "ST_PAY")
			STEP.setText(0, JDD_Adr, "ST_REFEXT")
			STEP.setText(0, JDD_Adr, "ST_RELAPA")
			STEP.setText(0, JDD_Adr, "ST_GPS")
			
			STEP.simpleClick(0, JDD_Adr,"BTN_Valider")
			
			STEP.isDivModalClosed(0)
		}
		
		TNRResult.addSTEPBLOCK("CONTACT")
		STEP.setText(0, myJDD, "ST_TELPHO")
		STEP.setText(0, myJDD, "ST_CON")
		STEP.setText(0, myJDD, "ST_TELMOB")
		STEP.setText(0, myJDD, "ST_EMA")
		STEP.setText(0, myJDD, "ST_TELCOP")
		STEP.setText(0, myJDD, "ST_TELEX")
		
	TNRResult.addSTEPGRP("ONGLET COMMANDE")
		
		STEP.simpleClick(0, myJDD,"tab_Commande")
		STEP.verifyElementVisible(0, myJDD,"tab_CommandeSelected")
		
		STEP.setText(0, myJDD, "ST_PRIDEL")
		STEP.searchWithHelper(0, myJDD, "ID_CODPAI","","")
		//ST_DESID_CODPAI --> pas d'action en modification
		STEP.setText(0, myJDD, "ST_NOTPRO")
		STEP.searchWithHelper(0, myJDD, "ID_CODMOD","","")
		//ST_DESID_CODMOD --> pas d'action en modification
		STEP.searchWithHelper(0, myJDD, "ID_CODDEV","","")
		//ST_DESID_CODDEV --> pas d'action en modification
		STEP.searchWithHelper(0, myJDD, "ID_CODPOR","","")
		//ST_DESID_CODPOR --> pas d'action en modification
		STEP.searchWithHelper(0, myJDD, "ID_CODEMB","","")
		//ST_DESID_CODEMB --> pas d'action en modification
		STEP.setText(0, myJDD, "ST_REL")
		STEP.scrollAndCheckIfNeeded(0, myJDD,"ST_FIGCAT","O")
		
		TNRResult.addSTEPBLOCK("TEXTES COMMANDE")
		STEP.setText(0, myJDD, "ST_TXTBAS1")
		STEP.setText(0, myJDD, "ST_TXTBAS2")
		STEP.setText(0, myJDD, "ST_TXTBAS3")
		STEP.setText(0, myJDD, "ST_TXTBAS4")
		STEP.setText(0, myJDD, "ST_TXTBAS5")
		STEP.setText(0, myJDD, "ST_TXTBAS6")
		STEP.scrollAndCheckIfNeeded(0, myJDD,"ST_FIGCDE","O")
	
	TNRResult.addSTEPGRP("ONGLET NOTES")
	
		STEP.simpleClick(0, myJDD,"tab_Notes")
		STEP.verifyElementVisible(0, myJDD,"tab_NotesSelected")
		
		JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RO.FOU'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
		STEP.scrollToPosition('', 0, 0)
		STEP.setMemoText(0, JDD_Note.getStrData("OL_DOC"), 'Notes',true,myJDD,'')
	
	  		 
		
	TNRResult.addSTEPACTION('VALIDATION')
	
	    STEP.simpleClick(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(0, myJDD.getStrData())
	
		
		STEP.checkJDDWithBD(0, myJDD)
		STEP.checkJDDWithBD(0, JDD_Note)
		STEP.checkJDDWithBD(0, JDD_Adr)
	
	TNRResult.addEndTestCase()
} // fin du if


