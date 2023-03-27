import my.KW
import my.Log as MYLOG
import my.NAV


'Lecture du JDD'
def myJDD = new my.JDD()
		
		
'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Creation_and_checkCartridge()
	

	MYLOG.addSTEPGRP('ONGLET FOURNISSEUR')
	
		KW.scrollAndClick(myJDD,'a_Fournisseur')
		KW.waitForElementVisible(myJDD,'a_FournisseurSelected')
		
		KW.scrollAndSetText(myJDD,'ID_CODFOU')
		KW.scrollAndSetText(myJDD,'ST_NOM')
		KW.scrollAndSetText(myJDD,'ID_CODGES')
		//KW.searchWithHelper(myJDD, 'ST_DESGES','','')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_CONSTR','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_INA','O')
		KW.scrollAndSetText(myJDD,'ST_CODCOM')
		//KW.searchWithHelper(myJDD, 'ST_DESST_CODCOM','','')
		KW.scrollAndSetText(myJDD,'ST_TELPHO')
		KW.scrollAndSetText(myJDD,'ST_CONSTR')
		KW.scrollAndSetText(myJDD,'ST_TELMOB')
		KW.scrollAndSetText(myJDD,'ST_EMA')
		KW.scrollAndSetText(myJDD,'ST_TELCOP')
		KW.scrollAndSetText(myJDD,'ST_TELEX')
		
	MYLOG.addSTEPGRP('ONGLET COMMANDE')
	
		KW.scrollAndClick(myJDD,'a_Commande')
		KW.waitForElementVisible(myJDD,'a_CommandeSelected')
		
		KW.scrollAndSetText(myJDD,'ST_PRITEL')
		KW.scrollAndSetText(myJDD,'ID_CODPAI')
		//KW.searchWithHelper(myJDD, 'ST_DESID_CODPAI','','')
		KW.scrollAndSetText(myJDD,'ST_NOTPRO')
		KW.scrollAndSetText(myJDD,'ID_CODMOD')
		//KW.searchWithHelper(myJDD, 'ST_DESID_CODMOD','','')
		KW.scrollAndSetText(myJDD,'ID_CODDEV')
		//KW.searchWithHelper(myJDD, 'ST_DESID_CODDEV','','')
		KW.scrollAndSetText(myJDD,'ID_CODPOR')
		//KW.searchWithHelper(myJDD, 'ST_DESID_CODPOR','','')
		KW.scrollAndSetText(myJDD,'ID_CODEMB')
		//KW.searchWithHelper(myJDD, 'ST_DESID_CODEMB','','')
		KW.scrollAndSetText(myJDD,'ST_REL')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_FIGCAT','O')
		KW.scrollAndSetText(myJDD,'ST_TXTBAS1')
		KW.scrollAndSetText(myJDD,'ST_TXTBAS2')
		KW.scrollAndSetText(myJDD,'ST_TXTBAS3')
		KW.scrollAndSetText(myJDD,'ST_TXTBAS4')
		KW.scrollAndSetText(myJDD,'ST_TXTBAS5')
		KW.scrollAndSetText(myJDD,'ST_TXTBAS6')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_FIGCDE','O')
		
	MYLOG.addSTEPGRP('ONGLET NOTES')
	
		KW.scrollAndClick(myJDD,'a_Notes')
		KW.waitForElementVisible(myJDD,'a_NotesSelected')

		
		
			
	
	
	
	
	
	
				
	MYLOG.addSTEPGRP('VALIDATION')

    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')

    NAV.verifierEcranResultat()
		
    KW.verifyElementText(NAV.myGlobalJDD,'a_Resultat_ID', myJDD.getStrData('ID_CODFOU'))

	my.SQL.checkJDDWithBD(myJDD)

} // fin du if



