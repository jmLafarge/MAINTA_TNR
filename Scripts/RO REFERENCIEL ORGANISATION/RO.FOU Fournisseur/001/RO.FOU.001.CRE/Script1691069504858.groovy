import tnrJDDManager.JDD
import tnrWebUI.KW
import tnrWebUI.NAV
import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
def myJDD = new JDD()
		
		
for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Creation_and_checkCartridge()
	

	
	TNRResult.addSTEPGRP("ONGLET FOURNISSEUR")
	
		KW.scrollAndClick(myJDD,"tab_Fournisseur")
		KW.waitForElementVisible(myJDD,"tab_FournisseurSelected")
		
		KW.scrollAndSetText(myJDD,"ID_CODFOU")
		KW.scrollAndSetText(myJDD,"ST_NOM")
		KW.scrollAndSetText(myJDD,"ID_CODGES")
		//ST_DESGES --> pas d'action en création
		KW.scrollAndCheckIfNeeded(myJDD,"ST_CONSTR","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		KW.scrollAndSetText(myJDD,"ST_CODCOM")
		//ST_DESST_CODCOM --> pas d'action en création
		
		//TNRResult.addSTEPBLOCK("ADRESSE")
		
		TNRResult.addSTEPBLOCK("CONTACT")
		KW.scrollAndSetText(myJDD,"ST_TELPHO")
		KW.scrollAndSetText(myJDD,"ST_CON")
		KW.scrollAndSetText(myJDD,"ST_TELMOB")
		KW.scrollAndSetText(myJDD,"ST_EMA")
		KW.scrollAndSetText(myJDD,"ST_TELCOP")
		KW.scrollAndSetText(myJDD,"ST_TELEX")
		
	TNRResult.addSTEPGRP("ONGLET COMMANDE")
		
		KW.scrollAndClick(myJDD,"tab_Commande")
		KW.waitForElementVisible(myJDD,"tab_CommandeSelected")
		
		KW.scrollAndSetText(myJDD,"ST_PRIDEL")
		KW.scrollAndSetText(myJDD,"ID_CODPAI")
		//ST_DESID_CODPAI --> pas d'action en création
		KW.scrollAndSetText(myJDD,"ST_NOTPRO")
		KW.scrollAndSetText(myJDD,"ID_CODMOD")
		//ST_DESID_CODMOD --> pas d'action en création
		KW.scrollAndSetText(myJDD,"ID_CODDEV")
		//ST_DESID_CODDEV --> pas d'action en création
		KW.scrollAndSetText(myJDD,"ID_CODPOR")
		//ST_DESID_CODPOR --> pas d'action en création
		KW.scrollAndSetText(myJDD,"ID_CODEMB")
		//ST_DESID_CODEMB --> pas d'action en création
		KW.scrollAndSetText(myJDD,"ST_REL")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_FIGCAT","O")
		
		TNRResult.addSTEPBLOCK("TEXTES COMMANDE")
		KW.scrollAndSetText(myJDD,"ST_TXTBAS1")
		KW.scrollAndSetText(myJDD,"ST_TXTBAS2")
		KW.scrollAndSetText(myJDD,"ST_TXTBAS3")
		KW.scrollAndSetText(myJDD,"ST_TXTBAS4")
		KW.scrollAndSetText(myJDD,"ST_TXTBAS5")
		KW.scrollAndSetText(myJDD,"ST_TXTBAS6")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_FIGCDE","O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		KW.scrollAndClick(myJDD,"tab_Notes")
		KW.waitForElementVisible(myJDD,"tab_NotesSelected")


				
	TNRResult.addSTEPACTION('VALIDATION')

	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)

		SQL.checkJDDWithBD(myJDD)

	TNRResult.addEndTestCase()
} // fin du if



