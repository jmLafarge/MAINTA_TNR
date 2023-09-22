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
	STEP_NAV.goToURL_Creation_and_checkCartridge(1,'','SEL=0&OPERATION=NEW_SON')
	

		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")

		
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
		
		STEP.click(0, myJDD, "tab_Equipement")
		STEP.verifyElementVisible(0, myJDD, "tab_EquipementSelected")
		
		STEP.setText(0, myJDD, "ST_CODCOU")
		STEP.setText(0, myJDD, "ST_CODPERS")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
		STEP.setText(0, myJDD, "ST_DESEQU")
		KW.scrollAndSelectOptionByLabel(myJDD, "ST_ETA")
		KW.scrollAndSelectOptionByLabel(myJDD, "NU_CRI")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_NIVABS", "O")
		STEP.setText(0, myJDD, "ID_CODGES")
		STEP.setText(0, myJDD, "ID_NUMEMP") //EMP_CODLON
		STEP.setText(0, myJDD, "ID_CODIMP")
		STEP.setText(0, myJDD, "ID_NUMGRO") // GRO_CODLON
		STEP.setText(0, myJDD, "ID_CODCOM")
		STEP.setText(0, myJDD, "NU_USA")
		STEP.setText(0, myJDD, "ID_CODCON")
		
		STEP.scrollToPosition(0, 0)
		
	TNRResult.addSTEPGRP("ONGLET FICHE")
		
		STEP.click(0, myJDD, "tab_Fiche")
		STEP.verifyElementVisible(0, myJDD, "tab_FicheSelected")
		
		STEP.setText(0, myJDD, "ST_NOMFOU")
			STEP.setText(0, myJDD, "ST_REFFOU")
			STEP.setText(0, myJDD, "NU_PRIACH")
			STEP.setText(0, myJDD, "ST_NOMCON")
			STEP.setText(0, myJDD, "ST_REFCON")
			KW.setDate(myJDD, "DT_ACH")
			KW.setDate(myJDD, "DT_SER")
			STEP.setText(0, myJDD, "NU_PRIACT")
			KW.setDate(myJDD, "DT_FINGAR")
			KW.setDate(myJDD, "DT_FINVIE")
			STEP.setText(0, myJDD, "NU_COUHOR")
			STEP.setText(0, myJDD, "NU_USAGAR")
			STEP.setText(0, myJDD, "NU_FINUSA")
			STEP.setText(0, myJDD, "NU_COUARR")
			STEP.setText(0, myJDD, "ST_NUMINV")
			STEP.setText(0, myJDD, "ST_OBS")
			STEP.setText(0, myJDD, "ID_CODCAL")
			STEP.setText(0, myJDD, "ID_CODCONTRA")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_COM", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_MAT", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_CONTRABT", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_ANA", "O")
		
		STEP.scrollToPosition(0, 0)
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		STEP.click(0, myJDD, "tab_Notes")
		STEP.verifyElementVisible(0, myJDD, "tab_NotesSelected")

		/*
		Comment différencier --> en rajoutant un ecolonne dans 001C avec le ID_NUMDOC1, ID_NUMDOC2
		 - les notes equipement ID_NUMDOC1
		 - les notes consignes ID_NUMDOC2
		*/
		
	TNRResult.addSTEPGRP("ONGLET ADRESSE")
		
		STEP.click(0, myJDD, "tab_Adresse")
		STEP.verifyElementVisible(0, myJDD, "tab_AdresseSelected")


	
	
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.click(0, GlobalJDD.myGlobalJDD,'button_Valider')
		
		NAV.verifierEcranResultat(myJDD.getStrData('ST_CODCOU'),'', 'Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMEQU')
	
		SQL.checkJDDWithBD(myJDD)
		
	TNRResult.addEndTestCase()

} // fin du if



