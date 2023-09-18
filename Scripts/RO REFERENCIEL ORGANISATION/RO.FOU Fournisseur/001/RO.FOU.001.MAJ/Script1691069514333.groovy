import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.KW
import tnrWebUI.Memo
import tnrWebUI.NAV

'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())


	TNRResult.addSTEPGRP("ONGLET FOURNISSEUR")
	
		KW.scrollAndClick(myJDD,"tab_Fournisseur")
		KW.waitForElementVisible(myJDD,"tab_FournisseurSelected")
		
		//KW.scrollAndSetText(myJDD, "ID_CODFOU")
		KW.scrollAndSetText(myJDD, "ST_NOM")
		KW.searchWithHelper(myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		KW.scrollAndCheckIfNeeded(myJDD,"ST_CONSTR","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		KW.searchWithHelper(myJDD, "ST_CODCOM","","SEARCH_ID_CODCMP") //specific
		//ST_DESST_CODCOM --> pas d'action en modification
	
		
		TNRResult.addSTEPBLOCK("ADRESSE")
		
		JDD JDD_Adr = new JDD(JDDFileMapper.getFullnameFromModObj('RO.ADR'),'001',GlobalVariable.CAS_DE_TEST_EN_COURS)
		
		KW.scrollAndClick(myJDD,"TD_Adresse")
		KW.scrollAndClick(myJDD,"BTN_ModifierAdresse")
		
		if (KW.isElementPresent(myJDD, 'ADR_divActive', 2)) {
			
			TNRResult.addSTEPPASS("La fenetre de saisie de l'adresse est ouverte")
		
			//KW.scrollAndCheckIfNeeded(JDD_Adr,"ST_ADRPAR","O")
			KW.scrollAndSetText(JDD_Adr, "ST_ADR")
			KW.scrollAndSetText(JDD_Adr, "ST_ADRCOM")
			KW.scrollAndSetText(JDD_Adr, "ST_ADRCOM2")
			KW.scrollAndSetText(JDD_Adr, "ST_MENSPE")
			KW.scrollAndSetText(JDD_Adr, "ST_CODPOS")
			KW.scrollAndSetText(JDD_Adr, "ST_VIL")
			KW.scrollAndSetText(JDD_Adr, "ST_CEDEX")
			KW.scrollAndSetText(JDD_Adr, "NU_CEDEX")
			KW.scrollAndSetText(JDD_Adr, "ID_CODPAY")
			KW.scrollAndSetText(JDD_Adr, "ST_PAY")
			KW.scrollAndSetText(JDD_Adr, "ST_REFEXT")
			KW.scrollAndSetText(JDD_Adr, "ST_RELAPA")
			KW.scrollAndSetText(JDD_Adr, "ST_GPS")
			
			KW.scrollAndClick(JDD_Adr,"BTN_Valider")
			
			if (KW.isElementPresent(myJDD, 'ADR_divInactive', 2)) {
				TNRResult.addSTEPPASS("Fermeture de la fenetre de saisie de l'adresse")
			}else {
				TNRResult.addSTEPFAIL("Fermeture de la fenetre de saisie de l'adresse")
			}
		}

		
		TNRResult.addSTEPBLOCK("CONTACT")
		KW.scrollAndSetText(myJDD, "ST_TELPHO")
		KW.scrollAndSetText(myJDD, "ST_CON")
		KW.scrollAndSetText(myJDD, "ST_TELMOB")
		KW.scrollAndSetText(myJDD, "ST_EMA")
		KW.scrollAndSetText(myJDD, "ST_TELCOP")
		KW.scrollAndSetText(myJDD, "ST_TELEX")
		
	TNRResult.addSTEPGRP("ONGLET COMMANDE")
		
		KW.scrollAndClick(myJDD,"tab_Commande")
		KW.waitForElementVisible(myJDD,"tab_CommandeSelected")
		
		KW.scrollAndSetText(myJDD, "ST_PRIDEL")
		KW.searchWithHelper(myJDD, "ID_CODPAI","","")
		//ST_DESID_CODPAI --> pas d'action en modification
		KW.scrollAndSetText(myJDD, "ST_NOTPRO")
		KW.searchWithHelper(myJDD, "ID_CODMOD","","")
		//ST_DESID_CODMOD --> pas d'action en modification
		KW.searchWithHelper(myJDD, "ID_CODDEV","","")
		//ST_DESID_CODDEV --> pas d'action en modification
		KW.searchWithHelper(myJDD, "ID_CODPOR","","")
		//ST_DESID_CODPOR --> pas d'action en modification
		KW.searchWithHelper(myJDD, "ID_CODEMB","","")
		//ST_DESID_CODEMB --> pas d'action en modification
		KW.scrollAndSetText(myJDD, "ST_REL")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_FIGCAT","O")
		
		TNRResult.addSTEPBLOCK("TEXTES COMMANDE")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS1")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS2")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS3")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS4")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS5")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS6")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_FIGCDE","O")
	
	TNRResult.addSTEPGRP("ONGLET NOTES")
	
		KW.scrollAndClick(myJDD,"tab_Notes")
		KW.waitForElementVisible(myJDD,"tab_NotesSelected")
		
		JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RO.FOU'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
		KW.scrollToPositionAndWait(0, 0,1)
		Memo.setText(JDD_Note.getStrData("OL_DOC"), 'Notes',true,myJDD,'')
	
	  		 
		
	TNRResult.addSTEPACTION('VALIDATION')
	
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
	
		
		SQL.checkJDDWithBD(myJDD)
		SQL.checkJDDWithBD(JDD_Note)
		SQL.checkJDDWithBD(JDD_Adr)
	
	TNRResult.addEndTestCase()
} // fin du if


