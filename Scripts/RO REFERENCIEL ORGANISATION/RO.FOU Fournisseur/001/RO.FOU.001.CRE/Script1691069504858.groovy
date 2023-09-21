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
    NAV.goToURL_Creation_and_checkCartridge()
	

	
	TNRResult.addSTEPGRP("ONGLET FOURNISSEUR")
	
		KW.click(myJDD,"tab_Fournisseur")
		KW.isElementVisible(myJDD,"tab_FournisseurSelected")
		
		KW.setText(myJDD,"ID_CODFOU")
		KW.setText(myJDD,"ST_NOM")
		KW.setText(myJDD,"ID_CODGES")
		//ST_DESGES --> pas d'action en création
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_CONSTR","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		KW.setText(myJDD,"ST_CODCOM")
		//ST_DESST_CODCOM --> pas d'action en création
		
		//TNRResult.addSTEPBLOCK("ADRESSE")
		
		TNRResult.addSTEPBLOCK("CONTACT")
		KW.setText(myJDD,"ST_TELPHO")
		KW.setText(myJDD,"ST_CON")
		KW.setText(myJDD,"ST_TELMOB")
		KW.setText(myJDD,"ST_EMA")
		KW.setText(myJDD,"ST_TELCOP")
		KW.setText(myJDD,"ST_TELEX")
		
	TNRResult.addSTEPGRP("ONGLET COMMANDE")
		
		KW.click(myJDD,"tab_Commande")
		KW.isElementVisible(myJDD,"tab_CommandeSelected")
		
		KW.setText(myJDD,"ST_PRIDEL")
		KW.setText(myJDD,"ID_CODPAI")
		//ST_DESID_CODPAI --> pas d'action en création
		KW.setText(myJDD,"ST_NOTPRO")
		KW.setText(myJDD,"ID_CODMOD")
		//ST_DESID_CODMOD --> pas d'action en création
		KW.setText(myJDD,"ID_CODDEV")
		//ST_DESID_CODDEV --> pas d'action en création
		KW.setText(myJDD,"ID_CODPOR")
		//ST_DESID_CODPOR --> pas d'action en création
		KW.setText(myJDD,"ID_CODEMB")
		//ST_DESID_CODEMB --> pas d'action en création
		KW.setText(myJDD,"ST_REL")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_FIGCAT","O")
		
		TNRResult.addSTEPBLOCK("TEXTES COMMANDE")
		KW.setText(myJDD,"ST_TXTBAS1")
		KW.setText(myJDD,"ST_TXTBAS2")
		KW.setText(myJDD,"ST_TXTBAS3")
		KW.setText(myJDD,"ST_TXTBAS4")
		KW.setText(myJDD,"ST_TXTBAS5")
		KW.setText(myJDD,"ST_TXTBAS6")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_FIGCDE","O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		KW.click(myJDD,"tab_Notes")
		KW.isElementVisible(myJDD,"tab_NotesSelected")
		
		// Pas de cas de test pour Notes
	
	
				
	TNRResult.addSTEPACTION('VALIDATION')

	    KW.click(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)
		//JDD_Note.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)
		
		SQL.checkJDDWithBD(myJDD)
		//SQL.checkJDDWithBD(JDD_Note)

	TNRResult.addEndTestCase()
} // fin du if



