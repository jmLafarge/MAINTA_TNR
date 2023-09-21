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
	NAV.goToURL_Creation_and_checkCartridge('','SEL=0&OPERATION=NEW_SON')
	

		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")

		
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
		
		KW.click(myJDD, "tab_Equipement")
		KW.isElementVisible(myJDD, "tab_EquipementSelected")
		
		KW.setText(myJDD, "ST_CODCOU")
		KW.setText(myJDD, "ST_CODPERS")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
		KW.setText(myJDD, "ST_DESEQU")
		KW.scrollAndSelectOptionByLabel(myJDD, "ST_ETA")
		KW.scrollAndSelectOptionByLabel(myJDD, "NU_CRI")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_NIVABS", "O")
		KW.setText(myJDD, "ID_CODGES")
		KW.setText(myJDD, "ID_NUMEMP") //EMP_CODLON
		KW.setText(myJDD, "ID_CODIMP")
		KW.setText(myJDD, "ID_NUMGRO") // GRO_CODLON
		KW.setText(myJDD, "ID_CODCOM")
		KW.setText(myJDD, "NU_USA")
		KW.setText(myJDD, "ID_CODCON")
		
		KW.scrollToPositionAndWait(0, 0,1)
		
	TNRResult.addSTEPGRP("ONGLET FICHE")
		
		KW.click(myJDD, "tab_Fiche")
		KW.isElementVisible(myJDD, "tab_FicheSelected")
		
		KW.setText(myJDD, "ST_NOMFOU")
			KW.setText(myJDD, "ST_REFFOU")
			KW.setText(myJDD, "NU_PRIACH")
			KW.setText(myJDD, "ST_NOMCON")
			KW.setText(myJDD, "ST_REFCON")
			KW.setDate(myJDD, "DT_ACH")
			KW.setDate(myJDD, "DT_SER")
			KW.setText(myJDD, "NU_PRIACT")
			KW.setDate(myJDD, "DT_FINGAR")
			KW.setDate(myJDD, "DT_FINVIE")
			KW.setText(myJDD, "NU_COUHOR")
			KW.setText(myJDD, "NU_USAGAR")
			KW.setText(myJDD, "NU_FINUSA")
			KW.setText(myJDD, "NU_COUARR")
			KW.setText(myJDD, "ST_NUMINV")
			KW.setText(myJDD, "ST_OBS")
			KW.setText(myJDD, "ID_CODCAL")
			KW.setText(myJDD, "ID_CODCONTRA")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_COM", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_MAT", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_CONTRABT", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_ANA", "O")
		
		KW.scrollToPositionAndWait(0, 0,1)
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		KW.click(myJDD, "tab_Notes")
		KW.isElementVisible(myJDD, "tab_NotesSelected")

		/*
		Comment différencier --> en rajoutant un ecolonne dans 001C avec le ID_NUMDOC1, ID_NUMDOC2
		 - les notes equipement ID_NUMDOC1
		 - les notes consignes ID_NUMDOC2
		*/
		
	TNRResult.addSTEPGRP("ONGLET ADRESSE")
		
		KW.click(myJDD, "tab_Adresse")
		KW.isElementVisible(myJDD, "tab_AdresseSelected")


	
	
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    KW.click(NAV.myGlobalJDD,'button_Valider')
		
		NAV.verifierEcranResultat(myJDD.getStrData('ST_CODCOU'),'', 'Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMEQU')
	
		SQL.checkJDDWithBD(myJDD)
		
	TNRResult.addEndTestCase()

} // fin du if



