import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.KW
import tnrWebUI.Memo
import tnrWebUI.NAV
import tnrWebUI.WebWindow


'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
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
			KW.scrollAndSelectOptionByLabel(myJDD, "ST_ETA")
			KW.scrollAndSelectOptionByLabel(myJDD, "NU_TYP")
			KW.scrollAndSetText(myJDD, "NU_PRISTO")
			KW.searchWithHelper(myJDD, "ID_CODART","","")
			KW.searchWithHelper(myJDD, "ID_CODMOY","","")
			KW.searchWithHelper(myJDD, "ID_CODGES","","")
			KW.searchWithHelper(myJDD, "ID_NUMCRI")
			KW.searchWithHelper(myJDD, "ID_CODIMP","","")
			KW.scrollAndSetText(myJDD, "ID_NUMGRO")
			KW.searchWithHelper(myJDD, "ID_CODCOM","","")
			KW.scrollAndSetText(myJDD, "NU_USA")
			KW.searchWithHelper(myJDD, "ID_CODCON","","")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_TEC", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_REP", "O")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			KW.scrollAndClick(myJDD, "tab_Fiche")
			KW.waitForElementVisible(myJDD, "tab_FicheSelected")
			
			KW.searchWithHelper(myJDD, "ID_CODFOUINI","","SEARCH_ID_CODFOU")
			KW.scrollAndSetText(myJDD, "ST_REFFOU")
			KW.searchWithHelper(myJDD, "ID_CODCONSTR","","SEARCH_ID_CODFOU")
			KW.scrollAndSetText(myJDD, "ST_REFCON")
			KW.scrollAndSetDate(myJDD, "DT_ACH")
			KW.scrollAndSetDate(myJDD, "DT_FAC")
			KW.scrollAndSetText(myJDD, "NU_PRIACH")
			KW.scrollAndSetDate(myJDD, "DT_FINGAR")
			KW.scrollAndSetDate(myJDD, "DT_FINVIE")
			KW.scrollAndSetText(myJDD, "NU_PRIACT")
			KW.scrollAndSetText(myJDD, "NU_USAGAR")
			KW.scrollAndSetText(myJDD, "NU_FINUSA")
			
			KW.searchWithHelper(myJDD, "ID_CODCAL","","")
			
			KW.searchWithHelper(myJDD, "ID_CODGAR","","")
			KW.searchWithHelper(myJDD, "ID_REFCOM","","")
			KW.scrollAndSetText(myJDD, "ST_AFFCOM")
			KW.searchWithHelper(myJDD, "ID_CODINTPRO","","SEARCH_ID_CODINT")
			KW.searchWithHelper(myJDD, "ID_CODINTGES","","SEARCH_ID_CODINT")
			KW.searchWithHelper(myJDD, "ID_CODINTEXP","","SEARCH_ID_CODINT")
			KW.searchWithHelper(myJDD, "ID_CODINTMAI","","SEARCH_ID_CODINT")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_CONTRA", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_CONTRABT", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_PRE", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_INS", "O")
	
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			KW.scrollAndClick(myJDD, "tab_Notes")
			KW.waitForElementVisible(myJDD, "tab_NotesSelected")
			
			def JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.MAT'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
			KW.scrollToPositionAndWait(0, 0,1)
			
			Memo.setText(JDD_Note.getStrData("OL_DOC"), 'Notes',true,myJDD,'')
			

			
			
			
			
		TNRResult.addSTEPGRP("ONGLET ETAT")
			
			KW.scrollAndClick(myJDD, "tab_Etat")
			KW.waitForElementVisible(myJDD, "tab_EtatSelected")
			
			KW.scrollAndSetRadio(myJDD, "LblST_POS")
			
			KW.scrollAndSetText(myJDD, "ID_NUMEMP")
			KW.searchWithHelper(myJDD, "ID_CODMAG","","")
			KW.searchWithHelper(myJDD, "ID_CODFOU","","")
			KW.scrollAndSetText(myJDD, "DT_DEP")
			KW.scrollAndSetText(myJDD, "DT_RET")


	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())

		SQL.checkJDDWithBD(myJDD)
		SQL.checkJDDWithBD(JDD_Note)
		
	TNRResult.addEndTestCase()
} // fin du if


