

import internal.GlobalVariable
import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; 
import tnrJDDManager.JDDFileMapper
import tnrJDDManager.JDDKW
import tnrResultManager.TNRResult
import tnrWebUI.*




JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLCreate(); STEP.checkCreateScreen()
	


			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")
		
			
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			STEP.simpleClick(myJDD, "tab_Matricule")
			STEP.verifyElementVisible(myJDD, "tab_MatriculeSelected")
			
			

			STEP.setText(myJDD, "ID_NUMMAT")
			STEP.setText(myJDD, "ST_NUMINV")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_INA", "O")
			STEP.setText(myJDD, "ST_DES")
			STEP.selectOptionByLabel(myJDD, "ST_ETA")
			STEP.selectOptionByLabel(myJDD, "NU_TYP")
			STEP.setText(myJDD, "NU_PRISTO")
			STEP.setText(myJDD, "ID_CODART")		
			if(!JDDKW.isNULL(myJDD.getStrData('ID_CODMOY'))) {
				STEP.setText(myJDD, "ID_CODMOY")
			}
			STEP.setText(myJDD, "ID_CODGES")
			STEP.setText(myJDD, "ID_NUMCRI")
			STEP.setText(myJDD, "ID_CODIMP") 
			STEP.setText(myJDD, "ID_NUMGRO") //GRO_CODLON
			STEP.setText(myJDD, "ID_CODCOM")
			STEP.setText(myJDD, "NU_USA")
			STEP.setText(myJDD, "ID_CODCON")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_TEC", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_PAT", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_REP", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_SOUASS", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_SOUINV", "O")
			
			STEP.setDate(myJDD, "DT_ENT")
			STEP.setDate(myJDD, "DT_SER")
			STEP.setText(myJDD, "NU_VALINI")
			STEP.setText(myJDD, "NU_VALINV")
			
			STEP.setText(myJDD, "DT_DEP")
			STEP.setText(myJDD, "DT_RET")
			
			//STEP.scrollToPosition(0, 0)
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			STEP.simpleClick(myJDD, "tab_Fiche")
			STEP.verifyElementVisible(myJDD, "tab_FicheSelected")
			
			STEP.setText(myJDD, "ID_CODFOUINI")
			STEP.setText(myJDD, "ST_REFFOU")
			STEP.setText(myJDD, "ID_CODCONSTR")
			STEP.setText(myJDD, "ST_REFCON")
			STEP.setDate(myJDD, "DT_ACH")
			STEP.setDate(myJDD, "DT_FAC")
			STEP.setText(myJDD, "NU_PRIACH")
			STEP.setDate(myJDD, "DT_FINGAR")
			STEP.setDate(myJDD, "DT_FINVIE")
			STEP.setText(myJDD, "NU_PRIACT")
			STEP.setText(myJDD, "NU_USAGAR")
			STEP.setText(myJDD, "NU_FINUSA")
			
			STEP.setText(myJDD, "ID_CODCAL")
			STEP.setText(myJDD, "ID_CODGAR")
			STEP.setText(myJDD, "ID_REFCOM") 
			STEP.setText(myJDD, "ST_AFFCOM")
			
			STEP.setText(myJDD, "ID_CODINTPRO")
			STEP.setText(myJDD, "ID_CODINTGES")
			STEP.setText(myJDD, "ID_CODINTEXP")
			STEP.setText(myJDD, "ID_CODINTMAI")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_CONTRA", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_CONTRABT", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_PRE", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_INS", "O")
			

			
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			STEP.simpleClick(myJDD, "tab_Notes")
			STEP.verifyElementVisible(myJDD, "tab_NotesSelected")
			
			def JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.MAT'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
			STEP.scrollToPosition( 0, 0)
			STEP.setMemoText(JDD_Note.getStrData("OL_DOC"), 'Notes',false,myJDD,'')
			
			
			
			
	// Pas de test avec ONGLET ETAT
			
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Valider')
		
		//Vérifier que l’écran de mouvement technique est affiché et saisir:
		
		STEP.checkReadUpdateDeleteScreen(myJDD.getStrData(),'343')
		
		
			
		switch (myJDD.getStrData('SCENARIO')) {
	
			case 'C2.1':
				STEP.setRadio(myJDD, "LblCHOIX")
				STEP.setText(myJDD, "EQU_CODLON",myJDD.getStrData('ID_NUMEQU'))
				break;
	
			case 'C2.2':
				STEP.setRadio(myJDD, "LblCHOIX")
				STEP.setRadio(myJDD, "LblCHOIX2")
				STEP.setText(myJDD, "MATID_CODMAG",myJDD.getStrData('ID_CODMAG'))
				break;
	
			case 'C2.3':
				STEP.setRadio(myJDD, "LblCHOIX")
				STEP.setRadio(myJDD, "LblCHOIX2")
				STEP.setText(myJDD, "MATID_CODFOU",myJDD.getStrData('ID_CODFOU'))
				break;
			case 'C2.4':
				STEP.setRadio(myJDD, "LblCHOIX")
				STEP.setRadio(myJDD, "LblCHOIX2")
				STEP.setText(myJDD, "MATST_AUT",myJDD.getStrData('ST_AUT'))
				break;
			case 'C3.2':
				STEP.setRadio(myJDD, "LblCHOIX")
				STEP.setRadio(myJDD, "LblCHOIX2")
				STEP.setText(myJDD, "MATST_AUT",myJDD.getStrData('ST_AUT'))
				break;
		}
			

		
		//2eme validation
		
		STEP.simpleClick(myJDD,'button_Valider2')
	
	    STEP.checkResultScreen(myJDD.getStrData())
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC',-2)
		JDD_Note.replaceSEQUENCIDInJDD('ID_NUMDOC',-2)
		
		STEP.checkJDDWithBD(myJDD)
		STEP.checkJDDWithBD(JDD_Note)
		
	TNRResult.addEndTestCase()

} // fin du if



