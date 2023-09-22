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


			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")
	
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			STEP.click(0, myJDD, "tab_Matricule")
			STEP.verifyElementVisible(0, myJDD, "tab_MatriculeSelected")
			
			STEP.setText(0, myJDD, "ST_NUMINV")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
			STEP.setText(0, myJDD, "ST_DES")
			KW.scrollAndSelectOptionByLabel(myJDD, "ST_ETA")
			KW.scrollAndSelectOptionByLabel(myJDD, "NU_TYP")
			STEP.setText(0, myJDD, "NU_PRISTO")
			KWSearchHelper.launch(myJDD, "ID_CODART","","")
			KWSearchHelper.launch(myJDD, "ID_CODMOY","","")
			KWSearchHelper.launch(myJDD, "ID_CODGES","","")
			KWSearchHelper.launch(myJDD, "ID_NUMCRI")
			KWSearchHelper.launch(myJDD, "ID_CODIMP","","")
			STEP.setText(0, myJDD, "ID_NUMGRO")
			KWSearchHelper.launch(myJDD, "ID_CODCOM","","")
			STEP.setText(0, myJDD, "NU_USA")
			KWSearchHelper.launch(myJDD, "ID_CODCON","","")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_TEC", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_REP", "O")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			STEP.click(0, myJDD, "tab_Fiche")
			STEP.verifyElementVisible(0, myJDD, "tab_FicheSelected")
			
			KWSearchHelper.launch(myJDD, "ID_CODFOUINI","","SEARCH_ID_CODFOU")
			STEP.setText(0, myJDD, "ST_REFFOU")
			KWSearchHelper.launch(myJDD, "ID_CODCONSTR","","SEARCH_ID_CODFOU")
			STEP.setText(0, myJDD, "ST_REFCON")
			KW.setDate(myJDD, "DT_ACH")
			KW.setDate(myJDD, "DT_FAC")
			STEP.setText(0, myJDD, "NU_PRIACH")
			KW.setDate(myJDD, "DT_FINGAR")
			KW.setDate(myJDD, "DT_FINVIE")
			STEP.setText(0, myJDD, "NU_PRIACT")
			STEP.setText(0, myJDD, "NU_USAGAR")
			STEP.setText(0, myJDD, "NU_FINUSA")
			
			KWSearchHelper.launch(myJDD, "ID_CODCAL","","")
			
			KWSearchHelper.launch(myJDD, "ID_CODGAR","","")
			KWSearchHelper.launch(myJDD, "ID_REFCOM","","")
			STEP.setText(0, myJDD, "ST_AFFCOM")
			KWSearchHelper.launch(myJDD, "ID_CODINTPRO","","SEARCH_ID_CODINT")
			KWSearchHelper.launch(myJDD, "ID_CODINTGES","","SEARCH_ID_CODINT")
			KWSearchHelper.launch(myJDD, "ID_CODINTEXP","","SEARCH_ID_CODINT")
			KWSearchHelper.launch(myJDD, "ID_CODINTMAI","","SEARCH_ID_CODINT")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_CONTRA", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_CONTRABT", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_PRE", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INS", "O")
	
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			STEP.click(0, myJDD, "tab_Notes")
			STEP.verifyElementVisible(0, myJDD, "tab_NotesSelected")
			
			def JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.MAT'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
			STEP.scrollToPosition(0, 0)
			
			KWMemo.setText(JDD_Note.getStrData("OL_DOC"), 'Notes',true,myJDD,'')
			

			
			
			
			
		TNRResult.addSTEPGRP("ONGLET ETAT")
			
			STEP.click(0, myJDD, "tab_Etat")
			STEP.verifyElementVisible(0, myJDD, "tab_EtatSelected")
			
			KW.scrollAndSetRadio(myJDD, "LblST_POS")
			
			STEP.setText(0, myJDD, "ID_NUMEMP")
			KWSearchHelper.launch(myJDD, "ID_CODMAG","","")
			KWSearchHelper.launch(myJDD, "ID_CODFOU","","")
			STEP.setText(0, myJDD, "DT_DEP")
			STEP.setText(0, myJDD, "DT_RET")


	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    STEP.click(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())

		SQL.checkJDDWithBD(myJDD)
		SQL.checkJDDWithBD(JDD_Note)
		
	TNRResult.addEndTestCase()
} // fin du if


