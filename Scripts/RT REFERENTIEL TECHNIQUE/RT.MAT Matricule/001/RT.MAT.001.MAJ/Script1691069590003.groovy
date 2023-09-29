import internal.GlobalVariable
import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; 
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrWebUI.*






'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(myJDD.getStrData()); STEP.checkReadUpdateDeleteScreen(myJDD.getStrData())


			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")
	
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			STEP.simpleClick(myJDD, "tab_Matricule")
			STEP.verifyElementVisible(myJDD, "tab_MatriculeSelected")
			
			STEP.setText(myJDD, "ST_NUMINV")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_INA", "O")
			STEP.setText(myJDD, "ST_DES")
			STEP.selectOptionByLabel(myJDD, "ST_ETA")
			STEP.selectOptionByLabel(myJDD, "NU_TYP")
			STEP.setText(myJDD, "NU_PRISTO")
			STEP.searchWithHelper(myJDD, "ID_CODART","","")
			STEP.searchWithHelper(myJDD, "ID_CODMOY","","")
			STEP.searchWithHelper(myJDD, "ID_CODGES","","")
			STEP.searchWithHelper(myJDD, "ID_NUMCRI")
			STEP.searchWithHelper(myJDD, "ID_CODIMP","","")
			STEP.setText(myJDD, "ID_NUMGRO")
			STEP.searchWithHelper(myJDD, "ID_CODCOM","","")
			STEP.setText(myJDD, "NU_USA")
			STEP.searchWithHelper(myJDD, "ID_CODCON","","")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_TEC", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_REP", "O")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			STEP.simpleClick(myJDD, "tab_Fiche")
			STEP.verifyElementVisible(myJDD, "tab_FicheSelected")
			
			STEP.searchWithHelper(myJDD, "ID_CODFOUINI","","SEARCH_ID_CODFOU")
			STEP.setText(myJDD, "ST_REFFOU")
			STEP.searchWithHelper(myJDD, "ID_CODCONSTR","","SEARCH_ID_CODFOU")
			STEP.setText(myJDD, "ST_REFCON")
			STEP.setDate(myJDD, "DT_ACH")
			STEP.setDate(myJDD, "DT_FAC")
			STEP.setText(myJDD, "NU_PRIACH")
			STEP.setDate(myJDD, "DT_FINGAR")
			STEP.setDate(myJDD, "DT_FINVIE")
			STEP.setText(myJDD, "NU_PRIACT")
			STEP.setText(myJDD, "NU_USAGAR")
			STEP.setText(myJDD, "NU_FINUSA")
			
			STEP.searchWithHelper(myJDD, "ID_CODCAL","","")
			
			STEP.searchWithHelper(myJDD, "ID_CODGAR","","")
			STEP.searchWithHelper(myJDD, "ID_REFCOM","","")
			STEP.setText(myJDD, "ST_AFFCOM")
			STEP.searchWithHelper(myJDD, "ID_CODINTPRO","","SEARCH_ID_CODINT")
			STEP.searchWithHelper(myJDD, "ID_CODINTGES","","SEARCH_ID_CODINT")
			STEP.searchWithHelper(myJDD, "ID_CODINTEXP","","SEARCH_ID_CODINT")
			STEP.searchWithHelper(myJDD, "ID_CODINTMAI","","SEARCH_ID_CODINT")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_CONTRA", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_CONTRABT", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_PRE", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_INS", "O")
	
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			STEP.simpleClick(myJDD, "tab_Notes")
			STEP.verifyElementVisible(myJDD, "tab_NotesSelected")
			
			def JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.MAT'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
			STEP.scrollToPosition( 0, 0)
			
			STEP.setMemoText(JDD_Note.getStrData("OL_DOC"), 'Notes',true,myJDD,'')
			

			
			
			
			
		TNRResult.addSTEPGRP("ONGLET ETAT")
			
			STEP.simpleClick(myJDD, "tab_Etat")
			STEP.verifyElementVisible(myJDD, "tab_EtatSelected")
			
			STEP.setRadio(myJDD, "LblST_POS")
			
			STEP.setText(myJDD, "ID_NUMEMP")
			STEP.searchWithHelper(myJDD, "ID_CODMAG","","")
			STEP.searchWithHelper(myJDD, "ID_CODFOU","","")
			STEP.setText(myJDD, "DT_DEP")
			STEP.setText(myJDD, "DT_RET")


	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(myJDD.getStrData())

		STEP.checkJDDWithBD(myJDD)
		STEP.checkJDDWithBD(JDD_Note)
		
	TNRResult.addEndTestCase()
} // fin du if


