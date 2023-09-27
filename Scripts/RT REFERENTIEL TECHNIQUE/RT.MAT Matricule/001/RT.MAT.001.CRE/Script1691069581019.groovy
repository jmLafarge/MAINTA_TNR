import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDKW
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLCreate(1); STEP.checkCreateScreen(2)
	


			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")
		
			
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			STEP.simpleClick(0, myJDD, "tab_Matricule")
			STEP.verifyElementVisible(0, myJDD, "tab_MatriculeSelected")
			
			

			STEP.setText(0, myJDD, "ID_NUMMAT")
			STEP.setText(0, myJDD, "ST_NUMINV")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_INA", "O")
			STEP.setText(0, myJDD, "ST_DES")
			STEP.scrollAndSelectOptionByLabel(0, myJDD, "ST_ETA")
			STEP.scrollAndSelectOptionByLabel(0, myJDD, "NU_TYP")
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
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_TEC", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_PAT", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_REP", "O")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			STEP.simpleClick(0, myJDD, "tab_Fiche")
			STEP.verifyElementVisible(0, myJDD, "tab_FicheSelected")
			
			STEP.setText(0, myJDD, "ID_CODFOUINI")
			STEP.setText(0, myJDD, "ST_REFFOU")
			STEP.setText(0, myJDD, "ID_CODCONSTR")
			STEP.setText(0, myJDD, "ST_REFCON")
			STEP.setDate(0, myJDD, "DT_ACH")
			STEP.setDate(0, myJDD, "DT_FAC")
			STEP.setText(0, myJDD, "NU_PRIACH")
			STEP.setDate(0, myJDD, "DT_FINGAR")
			STEP.setDate(0, myJDD, "DT_FINVIE")
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
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_CONTRA", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_CONTRABT", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_PRE", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_INS", "O")
			

			
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			STEP.simpleClick(0, myJDD, "tab_Notes")
			STEP.verifyElementVisible(0, myJDD, "tab_NotesSelected")
			
			def JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.MAT'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
			STEP.scrollToPosition('', 0, 0)
			STEP.setMemoText(0, JDD_Note.getStrData("OL_DOC"), 'Notes',false,myJDD,'')
			
			
			
			
	// Pas de test avec ONGLET ETAT
			
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.simpleClick(0, GlobalJDD.myGlobalJDD,'button_Valider')
		
		//Vérifier que l’écran de mouvement technique est affiché et saisir:
		
		NAV.checkReadUpdateDeleteScreen(myJDD.getStrData(),'343')
		
		
			
		switch (myJDD.getStrData('SCENARIO')) {
	
			case 'C2.1':
				STEP.scrollAndSetRadio(0, myJDD, "LblCHOIX")
				STEP.setText(0, myJDD, "EQU_CODLON",myJDD.getStrData('ID_NUMEQU'))
				break;
	
			case 'C2.2':
				STEP.scrollAndSetRadio(0, myJDD, "LblCHOIX")
				STEP.scrollAndSetRadio(0, myJDD, "LblCHOIX2")
				STEP.setText(0, myJDD, "MATID_CODMAG",myJDD.getStrData('ID_CODMAG'))
				break;
	
			case 'C2.3':
				STEP.scrollAndSetRadio(0, myJDD, "LblCHOIX")
				STEP.scrollAndSetRadio(0, myJDD, "LblCHOIX2")
				STEP.setText(0, myJDD, "MATID_CODFOU",myJDD.getStrData('ID_CODFOU'))
				break;
			case 'C2.4':
				STEP.scrollAndSetRadio(0, myJDD, "LblCHOIX")
				STEP.scrollAndSetRadio(0, myJDD, "LblCHOIX2")
				STEP.setText(0, myJDD, "MATST_AUT",myJDD.getStrData('ST_AUT'))
				break;
			case 'C3.2':
				STEP.scrollAndSetRadio(0, myJDD, "LblCHOIX")
				STEP.scrollAndSetRadio(0, myJDD, "LblCHOIX2")
				STEP.setText(0, myJDD, "MATST_AUT",myJDD.getStrData('ST_AUT'))
				break;
		}
			

		
		//2eme validation
		
		STEP.simpleClick(0, myJDD,'button_Valider2')
	
	    STEP.checkResultScreen(0, myJDD.getStrData())
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)
		JDD_Note.replaceSEQUENCIDInJDD('ID_NUMDOC',-1)
		
		STEP.checkJDDWithBD(0, myJDD)
		STEP.checkJDDWithBD(0, JDD_Note)
		
	TNRResult.addEndTestCase()

} // fin du if



