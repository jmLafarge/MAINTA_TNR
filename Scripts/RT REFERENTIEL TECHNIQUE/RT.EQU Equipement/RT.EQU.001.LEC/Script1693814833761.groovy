import internal.GlobalVariable
import tnrJDDManager.JDD; 
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrWebUI.*


// Lecture du JDD
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(myJDD.getStrData())
	STEP.checkReadUpdateDeleteScreen(myJDD.getStrData('ST_CODCOU'))

	
		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
		
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
		
		STEP.simpleClick(myJDD, "tab_Equipement")
		STEP.verifyElementVisible(myJDD, "tab_EquipementSelected")
		
		STEP.verifyValue(myJDD, "ST_CODCOU")
		STEP.verifyValue(myJDD, "ST_CODPERS")
		STEP.verifyBoxCheckedOrNot(myJDD, "ST_INA", "O")
		STEP.verifyValue(myJDD, "ST_DESEQU")
		STEP.verifyValue(myJDD, "ST_CODLON")
		STEP.verifyOptionSelectedByLabel(myJDD, "ST_ETA")
		STEP.verifyOptionSelectedByLabel(myJDD, "NU_CRI")
		STEP.verifyBoxCheckedOrNot(myJDD, "ST_NIVABS", "O")
		STEP.verifyValue(myJDD, "ID_CODGES")
		STEP.verifyValue(myJDD, "ST_DESGES")
		//STEP.verifyValue(myJDD, "EMP_CODLON")
		STEP.verifyValue(myJDD, "ST_DESEMP")
		STEP.verifyValue(myJDD, "ID_CODIMP")
		STEP.verifyValue(myJDD, "ST_DESIMP")
		//STEP.verifyValue(myJDD, "GRO_CODLON")
		STEP.verifyValue(myJDD, "ST_DESGRO")
		STEP.verifyValue(myJDD, "ID_CODCOM")
		STEP.verifyValue(myJDD, "ST_DESID_CODCOM")
		STEP.verifyValue(myJDD, "NU_USA")
		STEP.verifyValue(myJDD, "ID_CODCON")
		
		STEP.verifyValue(myJDD, "ID_CODCONSIG")
		STEP.verifyValue(myJDD, "ID_CODCLI")
		
	TNRResult.addSTEPGRP("ONGLET FICHE")
		
		STEP.simpleClick(myJDD, "tab_Fiche")
		STEP.verifyElementVisible(myJDD, "tab_FicheSelected")
		
		STEP.verifyValue(myJDD, "ST_NOMFOU")
		STEP.verifyValue(myJDD, "ST_REFFOU")
		STEP.verifyValue(myJDD, "NU_PRIACH")
		STEP.verifyValue(myJDD, "ST_NOMCON")
		STEP.verifyValue(myJDD, "ST_REFCON")
		STEP.verifyDateValue(myJDD, "DT_ACH")
		STEP.verifyDateValue(myJDD, "DT_SER")
		STEP.verifyValue(myJDD, "NU_PRIACT")
		STEP.verifyDateValue(myJDD, "DT_FINGAR")
		STEP.verifyDateValue(myJDD, "DT_FINVIE")
		STEP.verifyValue(myJDD, "NU_COUHOR")
		STEP.verifyValue(myJDD, "NU_USAGAR")
		STEP.verifyValue(myJDD, "NU_FINUSA")
		STEP.verifyValue(myJDD, "NU_COUARR")
		STEP.verifyValue(myJDD, "ST_NUMINV")
		STEP.verifyValue(myJDD, "ST_OBS")
		STEP.verifyValue(myJDD, "ID_CODCAL")
		STEP.verifyValue(myJDD, "ST_DESID_CODCAL")
		STEP.verifyValue(myJDD, "ID_CODCONTRA")
		STEP.verifyValue(myJDD, "ST_DESID_CODCONTRA")
		STEP.verifyBoxCheckedOrNot(myJDD, "ST_COM", "O")
		STEP.verifyBoxCheckedOrNot(myJDD, "ST_MAT", "O")
		STEP.verifyBoxCheckedOrNot(myJDD, "ST_CONTRABT", "O")
		STEP.verifyBoxCheckedOrNot(myJDD, "ST_ANA", "O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		STEP.simpleClick(myJDD, "tab_Notes")
		STEP.verifyElementVisible(myJDD, "tab_NotesSelected")
		
		STEP.scrollToPosition( 0, 0)
		
		JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.EQU'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
		
		String notes = JDD_Note.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC1'))
		String consignes = JDD_Note.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC2'))
		
		STEP.verifyText(JDD_Note,"DOC_Notes",notes)
		STEP.verifyText(JDD_Note,"DOC_Consignes",consignes)

		
		

	
	
	TNRResult.addEndTestCase()
}

