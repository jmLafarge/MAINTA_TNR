import my.KW
import my.NAV
import my.Log as MYLOG


'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0 ) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())


	MYLOG.addSTEPGRP("ONGLET FOURNISSEUR")
		
		KW.scrollAndClick(myJDD,"tab_Fournisseur")
		KW.waitForElementVisible(myJDD,"tab_FournisseurSelected")
		
		KW.scrollAndSetText(myJDD, "ID_CODFOU")
		KW.scrollAndSetText(myJDD, "ST_NOM")
		KW.searchWithHelper(myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		KW.scrollAndCheckIfNeeded(myJDD,"ST_CONSTR","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		KW.searchWithHelper(myJDD, "ST_CODCOM","","")
		//ST_DESST_CODCOM --> pas d'action en modification
		
		KW.scrollAndSetText(myJDD, "ST_TELPHO")
		KW.scrollAndSetText(myJDD, "ST_CON")
		KW.scrollAndSetText(myJDD, "ST_TELMOB")
		KW.scrollAndSetText(myJDD, "ST_EMA")
		KW.scrollAndSetText(myJDD, "ST_TELCOP")
		KW.scrollAndSetText(myJDD, "ST_TELEX")
		
	MYLOG.addSTEPGRP("ONGLET COMMANDE")
		
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
		
		KW.scrollAndSetText(myJDD, "ST_TXTBAS1")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS2")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS3")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS4")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS5")
		KW.scrollAndSetText(myJDD, "ST_TXTBAS6")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_FIGCDE","O")
		
	MYLOG.addSTEPGRP("ONGLET NOTES")
		
		KW.scrollAndClick(myJDD,"tab_Notes")
		KW.waitForElementVisible(myJDD,"tab_NotesSelected")
	

	
	
	
	
	  

	  		 

	MYLOG.addSTEPACTION('VALIDATION')
	
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat()
	
	    KW.verifyElementText(NAV.myGlobalJDD,'a_Resultat_ID', myJDD.getStrData())
	
		my.SQL.checkJDDWithBD(myJDD)
	
} // fin du if


