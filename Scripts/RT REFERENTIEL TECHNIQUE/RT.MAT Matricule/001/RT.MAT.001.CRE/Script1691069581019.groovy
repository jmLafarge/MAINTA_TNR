import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrJDDManager.JDDKW
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP_NAV.goToURL_Creation_and_checkCartridge(1)
	


			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")
		
			
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			STEP.click(0, myJDD, "tab_Matricule")
			STEP.verifyElementVisible(0, myJDD, "tab_MatriculeSelected")
			
			

			STEP.setText(0, myJDD, "ID_NUMMAT")
			STEP.setText(0, myJDD, "ST_NUMINV")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
			STEP.setText(0, myJDD, "ST_DES")
			KW.scrollAndSelectOptionByLabel(myJDD, "ST_ETA")
			KW.scrollAndSelectOptionByLabel(myJDD, "NU_TYP")
			STEP.setText(0, myJDD, "NU_PRISTO")
			STEP.setText(0, myJDD, "ID_CODART")		
			if(!JDDKW.isNULL(myJDD.getStrData('ID_CODMOY'))) {
				STEP.setText(0, myJDD, "ID_CODMOY")
			}
			STEP.setText(0, myJDD, "ID_CODGES")
			STEP.setText(0, myJDD, "ID_NUMCRI")
			STEP.setText(0, myJDD, "ID_CODIMP")
			STEP.setText(0, myJDD, "ID_NUMGRO")
			STEP.setText(0, myJDD, "ID_CODCOM")
			STEP.setText(0, myJDD, "NU_USA")
			STEP.setText(0, myJDD, "ID_CODCON")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_TEC", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_PAT", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_REP", "O")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			STEP.click(0, myJDD, "tab_Fiche")
			STEP.verifyElementVisible(0, myJDD, "tab_FicheSelected")
			
			STEP.setText(0, myJDD, "ID_CODFOUINI")
			STEP.setText(0, myJDD, "ST_REFFOU")
			STEP.setText(0, myJDD, "ID_CODCONSTR")
			STEP.setText(0, myJDD, "ST_REFCON")
			KW.setDate(myJDD, "DT_ACH")
			KW.setDate(myJDD, "DT_FAC")
			STEP.setText(0, myJDD, "NU_PRIACH")
			KW.setDate(myJDD, "DT_FINGAR")
			KW.setDate(myJDD, "DT_FINVIE")
			STEP.setText(0, myJDD, "NU_PRIACT")
			STEP.setText(0, myJDD, "NU_USAGAR")
			STEP.setText(0, myJDD, "NU_FINUSA")
			STEP.setText(0, myJDD, "ID_CODCAL")
			STEP.setText(0, myJDD, "ID_CODGAR")
			STEP.setText(0, myJDD, "ID_REFCOM") 
			STEP.setText(0, myJDD, "ST_AFFCOM")
			STEP.setText(0, myJDD, "ID_CODINTPRO")
			STEP.setText(0, myJDD, "ID_CODINTGES")
			STEP.setText(0, myJDD, "ID_CODINTEXP")
			STEP.setText(0, myJDD, "ID_CODINTMAI")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_CONTRA", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_CONTRABT", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_PRE", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INS", "O")
			

			
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			STEP.click(0, myJDD, "tab_Notes")
			STEP.verifyElementVisible(0, myJDD, "tab_NotesSelected")
			
			def JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.MAT'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
			STEP.scrollToPosition(0, 0)
			KWMemo.setText(JDD_Note.getStrData("OL_DOC"), 'Notes',false,myJDD,'')
			
			
			
			
	// Pas de test avec ONGLET ETAT
			
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.click(0, GlobalJDD.myGlobalJDD,'button_Valider')
		
		//Vérifier que l’écran de mouvement technique est affiché et saisir:
		
		NAV.verifierEcranRUD(myJDD.getStrData(),'343')
		
		
			
		switch (myJDD.getStrData('SCENARIO')) {
	
			case 'C2.1':
				KW.scrollAndSetRadio(myJDD, "LblCHOIX")
				STEP.setText(0, myJDD, "EQU_CODLON",myJDD.getStrData('ID_NUMEQU'))
				break;
	
			case 'C2.2':
				KW.scrollAndSetRadio(myJDD, "LblCHOIX")
				KW.scrollAndSetRadio(myJDD, "LblCHOIX2")
				STEP.setText(0, myJDD, "MATID_CODMAG",myJDD.getStrData('ID_CODMAG'))
				break;
	
			case 'C2.3':
				KW.scrollAndSetRadio(myJDD, "LblCHOIX")
				KW.scrollAndSetRadio(myJDD, "LblCHOIX2")
				STEP.setText(0, myJDD, "MATID_CODFOU",myJDD.getStrData('ID_CODFOU'))
				break;
			case 'C2.4':
				KW.scrollAndSetRadio(myJDD, "LblCHOIX")
				KW.scrollAndSetRadio(myJDD, "LblCHOIX2")
				STEP.setText(0, myJDD, "MATST_AUT",myJDD.getStrData('ST_AUT'))
				break;
			case 'C3.2':
				KW.scrollAndSetRadio(myJDD, "LblCHOIX")
				KW.scrollAndSetRadio(myJDD, "LblCHOIX2")
				STEP.setText(0, myJDD, "MATST_AUT",myJDD.getStrData('ST_AUT'))
				break;
		}
			

		
		//2eme validation
		
		STEP.click(0, myJDD,'button_Valider2')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)
		JDD_Note.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)
		
		SQL.checkJDDWithBD(myJDD)
		SQL.checkJDDWithBD(JDD_Note)
		
	TNRResult.addEndTestCase()

} // fin du if



