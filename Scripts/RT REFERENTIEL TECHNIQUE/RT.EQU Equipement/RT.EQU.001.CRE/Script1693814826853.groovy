import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; 
import tnrResultManager.TNRResult
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
	STEP.goToURLCreate('','SEL=0&OPERATION=NEW_SON')
	STEP.checkCreateScreen()
	

		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")

		
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
		
		STEP.simpleClick(myJDD, "tab_Equipement")
		STEP.verifyElementVisible(myJDD, "tab_EquipementSelected")
		
		STEP.setText(myJDD, "ST_CODCOU")
		STEP.setText(myJDD, "ST_CODPERS")
		STEP.clickCheckboxIfNeeded(myJDD, "ST_INA", "O")
		STEP.setText(myJDD, "ST_DESEQU")
		STEP.selectOptionByLabel(myJDD, "ST_ETA")
		STEP.selectOptionByLabel(myJDD, "NU_CRI")
		STEP.clickCheckboxIfNeeded(myJDD, "ST_NIVABS", "O")
		STEP.setText(myJDD, "ID_CODGES")
		STEP.setText(myJDD, "ID_NUMEMP") //EMP_CODLON
		STEP.setText(myJDD, "ID_CODIMP")
		STEP.setText(myJDD, "ID_NUMGRO") // GRO_CODLON
		STEP.setText(myJDD, "ID_CODCOM")
		STEP.setText(myJDD, "NU_USA")
		STEP.setText(myJDD, "ID_CODCON")
		
		STEP.scrollToPosition( 0, 0)
		
	TNRResult.addSTEPGRP("ONGLET FICHE")
		
		STEP.simpleClick(myJDD, "tab_Fiche")
		STEP.verifyElementVisible(myJDD, "tab_FicheSelected")
		
		STEP.setText(myJDD, "ST_NOMFOU")
			STEP.setText(myJDD, "ST_REFFOU")
			STEP.setText(myJDD, "NU_PRIACH")
			STEP.setText(myJDD, "ST_NOMCON")
			STEP.setText(myJDD, "ST_REFCON")
			STEP.setDate(myJDD, "DT_ACH")
			STEP.setDate(myJDD, "DT_SER")
			STEP.setText(myJDD, "NU_PRIACT")
			STEP.setDate(myJDD, "DT_FINGAR")
			STEP.setDate(myJDD, "DT_FINVIE")
			STEP.setText(myJDD, "NU_COUHOR")
			STEP.setText(myJDD, "NU_USAGAR")
			STEP.setText(myJDD, "NU_FINUSA")
			STEP.setText(myJDD, "NU_COUARR")
			STEP.setText(myJDD, "ST_NUMINV")
			STEP.setText(myJDD, "ST_OBS")
			STEP.setText(myJDD, "ID_CODCAL")
			STEP.setText(myJDD, "ID_CODCONTRA")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_COM", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_MAT", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_CONTRABT", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_ANA", "O")
		
		STEP.scrollToPosition( 0, 0)
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		STEP.simpleClick(myJDD, "tab_Notes")
		STEP.verifyElementVisible(myJDD, "tab_NotesSelected")

		/*
		Comment différencier --> en rajoutant un ecolonne dans 001C avec le ID_NUMDOC1, ID_NUMDOC2
		 - les notes equipement ID_NUMDOC1
		 - les notes consignes ID_NUMDOC2
		*/
		
	TNRResult.addSTEPGRP("ONGLET ADRESSE")
		
		STEP.simpleClick(myJDD, "tab_Adresse")
		STEP.verifyElementVisible(myJDD, "tab_AdresseSelected")


	
	
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Valider')
		
		STEP.checkResultScreen(myJDD.getStrData('ST_CODCOU'),'', 'Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMEQU')
	
		STEP.checkJDDWithBD(myJDD)
		
	TNRResult.addEndTestCase()

} // fin du if



