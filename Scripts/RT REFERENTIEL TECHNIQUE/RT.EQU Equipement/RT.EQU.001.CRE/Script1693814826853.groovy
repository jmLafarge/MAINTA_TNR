import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.KW
import tnrWebUI.Memo
import tnrWebUI.NAV

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
		
		KW.scrollAndClick(myJDD, "tab_Equipement")
		KW.waitForElementVisible(myJDD, "tab_EquipementSelected")
		
		KW.scrollAndSetText(myJDD, "ST_CODCOU")
		KW.scrollAndSetText(myJDD, "ST_CODPERS")
		KW.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
		KW.scrollAndSetText(myJDD, "ST_DESEQU")
		KW.scrollAndSelectOptionByLabel(myJDD, "ST_ETA")
		KW.scrollAndSelectOptionByLabel(myJDD, "NU_CRI")
		KW.scrollAndCheckIfNeeded(myJDD, "ST_NIVABS", "O")
		KW.scrollAndSetText(myJDD, "ID_CODGES")
		KW.scrollAndSetText(myJDD, "ID_NUMEMP") //EMP_CODLON
		KW.scrollAndSetText(myJDD, "ID_CODIMP")
		KW.scrollAndSetText(myJDD, "ID_NUMGRO") // GRO_CODLON
		KW.scrollAndSetText(myJDD, "ID_CODCOM")
		KW.scrollAndSetText(myJDD, "NU_USA")
		KW.scrollAndSetText(myJDD, "ID_CODCON")
		
		KW.scrollToPositionAndWait(0, 0,1)
		
	TNRResult.addSTEPGRP("ONGLET FICHE")
		
		KW.scrollAndClick(myJDD, "tab_Fiche")
		KW.waitForElementVisible(myJDD, "tab_FicheSelected")
		
		KW.scrollAndSetText(myJDD, "ST_NOMFOU")
			KW.scrollAndSetText(myJDD, "ST_REFFOU")
			KW.scrollAndSetText(myJDD, "NU_PRIACH")
			KW.scrollAndSetText(myJDD, "ST_NOMCON")
			KW.scrollAndSetText(myJDD, "ST_REFCON")
			KW.scrollAndSetDate(myJDD, "DT_ACH")
			KW.scrollAndSetDate(myJDD, "DT_SER")
			KW.scrollAndSetText(myJDD, "NU_PRIACT")
			KW.scrollAndSetDate(myJDD, "DT_FINGAR")
			KW.scrollAndSetDate(myJDD, "DT_FINVIE")
			KW.scrollAndSetText(myJDD, "NU_COUHOR")
			KW.scrollAndSetText(myJDD, "NU_USAGAR")
			KW.scrollAndSetText(myJDD, "NU_FINUSA")
			KW.scrollAndSetText(myJDD, "NU_COUARR")
			KW.scrollAndSetText(myJDD, "ST_NUMINV")
			KW.scrollAndSetText(myJDD, "ST_OBS")
			KW.scrollAndSetText(myJDD, "ID_CODCAL")
			KW.scrollAndSetText(myJDD, "ID_CODCONTRA")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_COM", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_MAT", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_CONTRABT", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_ANA", "O")
		
		KW.scrollToPositionAndWait(0, 0,1)
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		KW.scrollAndClick(myJDD, "tab_Notes")
		KW.waitForElementVisible(myJDD, "tab_NotesSelected")

		/*
		Comment différencier
		 - les notes equipement ID_NUMDOC1
		 - les notes consignes ID_NUMDOC2
		 - les notes contrat, ID_NUMDOC4 --> je ne sais pas où elles sont !
		*/
		
	TNRResult.addSTEPGRP("ONGLET ADRESSE")
		
		KW.scrollAndClick(myJDD, "tab_Adresse")
		KW.waitForElementVisible(myJDD, "tab_AdresseSelected")


	
	
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
		
		NAV.verifierEcranResultat(myJDD.getStrData('ST_CODCOU'),'', 'Resultat_ID')
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMEQU')
	
		SQL.checkJDDWithBD(myJDD)
		
	TNRResult.addEndTestCase()

} // fin du if



