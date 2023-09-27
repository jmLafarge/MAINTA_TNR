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


			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")
	
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			STEP.simpleClick(0, myJDD, "tab_Matricule")
			STEP.verifyElementVisible(0, myJDD, "tab_MatriculeSelected")
			
			STEP.setText(0, myJDD, "ST_NUMINV")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_INA", "O")
			STEP.setText(0, myJDD, "ST_DES")
			STEP.scrollAndSelectOptionByLabel(0, myJDD, "ST_ETA")
			STEP.scrollAndSelectOptionByLabel(0, myJDD, "NU_TYP")
			STEP.setText(0, myJDD, "NU_PRISTO")
			STEP.searchWithHelper(0, myJDD, "ID_CODART","","")
			STEP.searchWithHelper(0, myJDD, "ID_CODMOY","","")
			STEP.searchWithHelper(0, myJDD, "ID_CODGES","","")
			STEP.searchWithHelper(0, myJDD, "ID_NUMCRI")
			STEP.searchWithHelper(0, myJDD, "ID_CODIMP","","")
			STEP.setText(0, myJDD, "ID_NUMGRO")
			STEP.searchWithHelper(0, myJDD, "ID_CODCOM","","")
			STEP.setText(0, myJDD, "NU_USA")
			STEP.searchWithHelper(0, myJDD, "ID_CODCON","","")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_TEC", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_REP", "O")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			STEP.simpleClick(0, myJDD, "tab_Fiche")
			STEP.verifyElementVisible(0, myJDD, "tab_FicheSelected")
			
			STEP.searchWithHelper(0, myJDD, "ID_CODFOUINI","","SEARCH_ID_CODFOU")
			STEP.setText(0, myJDD, "ST_REFFOU")
			STEP.searchWithHelper(0, myJDD, "ID_CODCONSTR","","SEARCH_ID_CODFOU")
			STEP.setText(0, myJDD, "ST_REFCON")
			STEP.setDate(0, myJDD, "DT_ACH")
			STEP.setDate(0, myJDD, "DT_FAC")
			STEP.setText(0, myJDD, "NU_PRIACH")
			STEP.setDate(0, myJDD, "DT_FINGAR")
			STEP.setDate(0, myJDD, "DT_FINVIE")
			STEP.setText(0, myJDD, "NU_PRIACT")
			STEP.setText(0, myJDD, "NU_USAGAR")
			STEP.setText(0, myJDD, "NU_FINUSA")
			
			STEP.searchWithHelper(0, myJDD, "ID_CODCAL","","")
			
			STEP.searchWithHelper(0, myJDD, "ID_CODGAR","","")
			STEP.searchWithHelper(0, myJDD, "ID_REFCOM","","")
			STEP.setText(0, myJDD, "ST_AFFCOM")
			STEP.searchWithHelper(0, myJDD, "ID_CODINTPRO","","SEARCH_ID_CODINT")
			STEP.searchWithHelper(0, myJDD, "ID_CODINTGES","","SEARCH_ID_CODINT")
			STEP.searchWithHelper(0, myJDD, "ID_CODINTEXP","","SEARCH_ID_CODINT")
			STEP.searchWithHelper(0, myJDD, "ID_CODINTMAI","","SEARCH_ID_CODINT")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_CONTRA", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_CONTRABT", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_PRE", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_INS", "O")
	
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			STEP.simpleClick(0, myJDD, "tab_Notes")
			STEP.verifyElementVisible(0, myJDD, "tab_NotesSelected")
			
			def JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.MAT'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
			STEP.scrollToPosition('', 0, 0)
			
			STEP.setMemoText(0, JDD_Note.getStrData("OL_DOC"), 'Notes',true,myJDD,'')
			

			
			
			
			
		TNRResult.addSTEPGRP("ONGLET ETAT")
			
			STEP.simpleClick(0, myJDD, "tab_Etat")
			STEP.verifyElementVisible(0, myJDD, "tab_EtatSelected")
			
			STEP.scrollAndSetRadio(0, myJDD, "LblST_POS")
			
			STEP.setText(0, myJDD, "ID_NUMEMP")
			STEP.searchWithHelper(0, myJDD, "ID_CODMAG","","")
			STEP.searchWithHelper(0, myJDD, "ID_CODFOU","","")
			STEP.setText(0, myJDD, "DT_DEP")
			STEP.setText(0, myJDD, "DT_RET")


	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    STEP.simpleClick(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(0, myJDD.getStrData())

		STEP.checkJDDWithBD(0, myJDD)
		STEP.checkJDDWithBD(0, JDD_Note)
		
	TNRResult.addEndTestCase()
} // fin du if


