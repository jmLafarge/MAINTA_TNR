import my.JDD
import my.KW
import my.NAV
import my.SQL
import my.result.TNRResult


'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())


			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")

			
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			KW.scrollAndClick(myJDD, "tab_Matricule")
			KW.waitForElementVisible(myJDD, "tab_MatriculeSelected")
			
			KW.scrollAndSetText(myJDD, "ST_NUMINV")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
			KW.scrollAndSetText(myJDD, "ST_DES")
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP")
			KW.scrollAndSetText(myJDD, "NU_PRISTO")
			KW.searchWithHelper(myJDD, "ID_CODART","","")
			KW.searchWithHelper(myJDD, "ID_CODMOY","","")
			KW.searchWithHelper(myJDD, "ID_CODGES","","")
			KW.searchWithHelper(myJDD, "ID_NUMCRI","","")
			KW.searchWithHelper(myJDD, "ID_CODIMP","","")
			KW.scrollAndSetText(myJDD, "GRO_CODLON")
			KW.searchWithHelper(myJDD, "ID_CODCOM","","")
			KW.scrollAndSetText(myJDD, "NU_USA")
			KW.searchWithHelper(myJDD, "ID_CODCON","","")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_TEC", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_REP", "O")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			KW.scrollAndClick(myJDD, "tab_Fiche")
			KW.waitForElementVisible(myJDD, "tab_FicheSelected")
			
			KW.searchWithHelper(myJDD, "ID_CODFOUINI","","")
			KW.scrollAndSetText(myJDD, "ST_REFFOU")
			KW.searchWithHelper(myJDD, "ID_CODCONSTR","","")
			KW.scrollAndSetText(myJDD, "ST_REFCON")
			KW.scrollAndSetText(myJDD, "DT_ACH")
			KW.scrollAndSetText(myJDD, "DT_FAC")
			KW.scrollAndSetText(myJDD, "NU_PRIACH")
			KW.scrollAndSetText(myJDD, "DT_FINGAR")
			KW.scrollAndSetText(myJDD, "DT_FINVIE")
			KW.scrollAndSetText(myJDD, "NU_PRIACT")
			KW.scrollAndSetText(myJDD, "NU_USAGAR")
			KW.scrollAndSetText(myJDD, "NU_FINUSA")
			KW.searchWithHelper(myJDD, "ID_CODCAL","","")
			KW.searchWithHelper(myJDD, "ID_CODGAR","","")
			KW.searchWithHelper(myJDD, "ID_REFCOM","","")
			KW.scrollAndSetText(myJDD, "ST_AFFCOM")
			KW.searchWithHelper(myJDD, "ID_CODINTPRO","","")
			KW.searchWithHelper(myJDD, "ID_CODINTGES","","")
			KW.searchWithHelper(myJDD, "ID_CODINTEXP","","")
			KW.searchWithHelper(myJDD, "ID_CODINTMAI","","")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_CONTRA", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_CONTRABT", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_PRE", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_INS", "O")
			
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			KW.scrollAndClick(myJDD, "tab_Notes")
			KW.waitForElementVisible(myJDD, "tab_NotesSelected")
			
			
		TNRResult.addSTEPGRP("ONGLET ETAT")
			
			KW.scrollAndClick(myJDD, "tab_Etat")
			KW.waitForElementVisible(myJDD, "tab_EtatSelected")
			
			KW.scrollAndSetText(myJDD, "EMP_CODLON")
			KW.searchWithHelper(myJDD, "ID_CODMAG","","")
			KW.searchWithHelper(myJDD, "ID_CODFOU","","")
			KW.scrollAndSetText(myJDD, "DT_DEP")
			KW.scrollAndSetText(myJDD, "DT_RET")
			KW.scrollAndCheckIfNeeded(myJDD, "NUMMAT2NUMAUTO", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "DISABLE_RECTIFSTO", "O")

	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
	
		SQL.checkJDDWithBD(myJDD)
	
		
	TNRResult.addEndTestCase()
} // fin du if


