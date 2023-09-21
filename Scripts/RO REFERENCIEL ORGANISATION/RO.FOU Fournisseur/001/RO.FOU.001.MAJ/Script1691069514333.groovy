import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*






'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())


	TNRResult.addSTEPGRP("ONGLET FOURNISSEUR")
	
		KW.click(myJDD,"tab_Fournisseur")
		KW.isElementVisible(myJDD,"tab_FournisseurSelected")
		
		//KW.setText(myJDD, "ID_CODFOU")
		KW.setText(myJDD, "ST_NOM")
		KWSearchHelper.launch(myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_CONSTR","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		KWSearchHelper.launch(myJDD, "ST_CODCOM","","SEARCH_ID_CODCMP") //specific
		//ST_DESST_CODCOM --> pas d'action en modification
	
		
		TNRResult.addSTEPBLOCK("ADRESSE")
		
		JDD JDD_Adr = new JDD(JDDFileMapper.getFullnameFromModObj('RO.ADR'),'001',GlobalVariable.CAS_DE_TEST_EN_COURS)
		
		KW.click(myJDD,"TD_Adresse")
		KW.click(myJDD,"BTN_ModifierAdresse")
		
		if (KWDivModal.isOpened()) {
		
			//KWCheckbox.scrollAndCheckIfNeeded(JDD_Adr,"ST_ADRPAR","O")
			KW.setText(JDD_Adr, "ST_ADR")
			KW.setText(JDD_Adr, "ST_ADRCOM")
			KW.setText(JDD_Adr, "ST_ADRCOM2")
			KW.setText(JDD_Adr, "ST_MENSPE")
			KW.setText(JDD_Adr, "ST_CODPOS")
			KW.setText(JDD_Adr, "ST_VIL")
			KW.setText(JDD_Adr, "ST_CEDEX")
			KW.setText(JDD_Adr, "NU_CEDEX")
			KW.setText(JDD_Adr, "ID_CODPAY")
			KW.setText(JDD_Adr, "ST_PAY")
			KW.setText(JDD_Adr, "ST_REFEXT")
			KW.setText(JDD_Adr, "ST_RELAPA")
			KW.setText(JDD_Adr, "ST_GPS")
			
			KW.click(JDD_Adr,"BTN_Valider")
			
			KWDivModal.isClosed()
		}
		
		TNRResult.addSTEPBLOCK("CONTACT")
		KW.setText(myJDD, "ST_TELPHO")
		KW.setText(myJDD, "ST_CON")
		KW.setText(myJDD, "ST_TELMOB")
		KW.setText(myJDD, "ST_EMA")
		KW.setText(myJDD, "ST_TELCOP")
		KW.setText(myJDD, "ST_TELEX")
		
	TNRResult.addSTEPGRP("ONGLET COMMANDE")
		
		KW.click(myJDD,"tab_Commande")
		KW.isElementVisible(myJDD,"tab_CommandeSelected")
		
		KW.setText(myJDD, "ST_PRIDEL")
		KWSearchHelper.launch(myJDD, "ID_CODPAI","","")
		//ST_DESID_CODPAI --> pas d'action en modification
		KW.setText(myJDD, "ST_NOTPRO")
		KWSearchHelper.launch(myJDD, "ID_CODMOD","","")
		//ST_DESID_CODMOD --> pas d'action en modification
		KWSearchHelper.launch(myJDD, "ID_CODDEV","","")
		//ST_DESID_CODDEV --> pas d'action en modification
		KWSearchHelper.launch(myJDD, "ID_CODPOR","","")
		//ST_DESID_CODPOR --> pas d'action en modification
		KWSearchHelper.launch(myJDD, "ID_CODEMB","","")
		//ST_DESID_CODEMB --> pas d'action en modification
		KW.setText(myJDD, "ST_REL")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_FIGCAT","O")
		
		TNRResult.addSTEPBLOCK("TEXTES COMMANDE")
		KW.setText(myJDD, "ST_TXTBAS1")
		KW.setText(myJDD, "ST_TXTBAS2")
		KW.setText(myJDD, "ST_TXTBAS3")
		KW.setText(myJDD, "ST_TXTBAS4")
		KW.setText(myJDD, "ST_TXTBAS5")
		KW.setText(myJDD, "ST_TXTBAS6")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_FIGCDE","O")
	
	TNRResult.addSTEPGRP("ONGLET NOTES")
	
		KW.click(myJDD,"tab_Notes")
		KW.isElementVisible(myJDD,"tab_NotesSelected")
		
		JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RO.FOU'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
		KW.scrollToPositionAndWait(0, 0,1)
		KWMemo.setText(JDD_Note.getStrData("OL_DOC"), 'Notes',true,myJDD,'')
	
	  		 
		
	TNRResult.addSTEPACTION('VALIDATION')
	
	    KW.click(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
	
		
		SQL.checkJDDWithBD(myJDD)
		SQL.checkJDDWithBD(JDD_Note)
		SQL.checkJDDWithBD(JDD_Adr)
	
	TNRResult.addEndTestCase()
} // fin du if


