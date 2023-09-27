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
	STEP_NAV.goToURL_Creation_and_checkCartridge(1,'','SEL=0&OPERATION=NEW_SON')
	

		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")

		
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
		
		STEP.simpleClick(0, myJDD, "tab_Equipement")
		STEP.verifyElementVisible(0, myJDD, "tab_EquipementSelected")
		
		STEP.setText(0, myJDD, "ST_CODCOU")
		STEP.setText(0, myJDD, "ST_CODPERS")
		STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_INA", "O")
		STEP.setText(0, myJDD, "ST_DESEQU")
		STEP.scrollAndSelectOptionByLabel(0, myJDD, "ST_ETA")
		STEP.scrollAndSelectOptionByLabel(0, myJDD, "NU_CRI")
		STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_NIVABS", "O")
		STEP.setText(0, myJDD, "ID_CODGES")
		STEP.setText(0, myJDD, "ID_NUMEMP") //EMP_CODLON
		STEP.setText(0, myJDD, "ID_CODIMP")
		STEP.setText(0, myJDD, "ID_NUMGRO") // GRO_CODLON
		STEP.setText(0, myJDD, "ID_CODCOM")
		STEP.setText(0, myJDD, "NU_USA")
		STEP.setText(0, myJDD, "ID_CODCON")
		
		STEP.scrollToPosition('', 0, 0)
		
	TNRResult.addSTEPGRP("ONGLET FICHE")
		
		STEP.simpleClick(0, myJDD, "tab_Fiche")
		STEP.verifyElementVisible(0, myJDD, "tab_FicheSelected")
		
		STEP.setText(0, myJDD, "ST_NOMFOU")
			STEP.setText(0, myJDD, "ST_REFFOU")
			STEP.setText(0, myJDD, "NU_PRIACH")
			STEP.setText(0, myJDD, "ST_NOMCON")
			STEP.setText(0, myJDD, "ST_REFCON")
			STEP.setDate(0, myJDD, "DT_ACH")
			STEP.setDate(0, myJDD, "DT_SER")
			STEP.setText(0, myJDD, "NU_PRIACT")
			STEP.setDate(0, myJDD, "DT_FINGAR")
			STEP.setDate(0, myJDD, "DT_FINVIE")
			STEP.setText(0, myJDD, "NU_COUHOR")
			STEP.setText(0, myJDD, "NU_USAGAR")
			STEP.setText(0, myJDD, "NU_FINUSA")
			STEP.setText(0, myJDD, "NU_COUARR")
			STEP.setText(0, myJDD, "ST_NUMINV")
			STEP.setText(0, myJDD, "ST_OBS")
			STEP.setText(0, myJDD, "ID_CODCAL")
			STEP.setText(0, myJDD, "ID_CODCONTRA")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_COM", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_MAT", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_CONTRABT", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_ANA", "O")
		
		STEP.scrollToPosition('', 0, 0)
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		STEP.simpleClick(0, myJDD, "tab_Notes")
		STEP.verifyElementVisible(0, myJDD, "tab_NotesSelected")

		/*
		Comment différencier --> en rajoutant un ecolonne dans 001C avec le ID_NUMDOC1, ID_NUMDOC2
		 - les notes equipement ID_NUMDOC1
		 - les notes consignes ID_NUMDOC2
		*/
		
	TNRResult.addSTEPGRP("ONGLET ADRESSE")
		
		STEP.simpleClick(0, myJDD, "tab_Adresse")
		STEP.verifyElementVisible(0, myJDD, "tab_AdresseSelected")


	
	
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.simpleClick(0, GlobalJDD.myGlobalJDD,'button_Valider')
		
		STEP.checkResultScreen(0, myJDD.getStrData('ST_CODCOU'),'', 'Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMEQU')
	
		STEP.checkJDDWithBD(0, myJDD)
		
	TNRResult.addEndTestCase()

} // fin du if



