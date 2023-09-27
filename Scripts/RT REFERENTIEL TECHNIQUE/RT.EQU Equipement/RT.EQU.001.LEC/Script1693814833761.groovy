import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper

import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrWebUI.*


'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_ReadUpdateDelete(myJDD.getStrData())
	NAV.checkReadUpdateDeleteScreen(myJDD.getStrData('ST_CODCOU'))

	
		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
		
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
		
		STEP.simpleClick(0, myJDD, "tab_Equipement")
		STEP.verifyElementVisible(0, myJDD, "tab_EquipementSelected")
		
		STEP.verifyValue(0, myJDD, "ST_CODCOU")
		STEP.verifyValue(0, myJDD, "ST_CODPERS")
		STEP.verifyElementCheckedOrNot(0, myJDD, "ST_INA", "O")
		STEP.verifyValue(0, myJDD, "ST_DESEQU")
		STEP.verifyValue(0, myJDD, "ST_CODLON")
		STEP.verifyOptionSelectedByLabel(0, myJDD, "ST_ETA")
		STEP.verifyOptionSelectedByLabel(0, myJDD, "NU_CRI")
		STEP.verifyElementCheckedOrNot(0, myJDD, "ST_NIVABS", "O")
		STEP.verifyValue(0, myJDD, "ID_CODGES")
		STEP.verifyValue(0, myJDD, "ST_DESGES")
		//STEP.verifyValue(0, myJDD, "EMP_CODLON")
		STEP.verifyValue(0, myJDD, "ST_DESEMP")
		STEP.verifyValue(0, myJDD, "ID_CODIMP")
		STEP.verifyValue(0, myJDD, "ST_DESIMP")
		//STEP.verifyValue(0, myJDD, "GRO_CODLON")
		STEP.verifyValue(0, myJDD, "ST_DESGRO")
		STEP.verifyValue(0, myJDD, "ID_CODCOM")
		STEP.verifyValue(0, myJDD, "ST_DESID_CODCOM")
		STEP.verifyValue(0, myJDD, "NU_USA")
		STEP.verifyValue(0, myJDD, "ID_CODCON")
		
	TNRResult.addSTEPGRP("ONGLET FICHE")
		
		STEP.simpleClick(0, myJDD, "tab_Fiche")
		STEP.verifyElementVisible(0, myJDD, "tab_FicheSelected")
		
		STEP.verifyValue(0, myJDD, "ST_NOMFOU")
		STEP.verifyValue(0, myJDD, "ST_REFFOU")
		STEP.verifyValue(0, myJDD, "NU_PRIACH")
		STEP.verifyValue(0, myJDD, "ST_NOMCON")
		STEP.verifyValue(0, myJDD, "ST_REFCON")
		STEP.verifyDateValue(0, myJDD, "DT_ACH")
		STEP.verifyDateValue(0, myJDD, "DT_SER")
		STEP.verifyValue(0, myJDD, "NU_PRIACT")
		STEP.verifyDateValue(0, myJDD, "DT_FINGAR")
		STEP.verifyDateValue(0, myJDD, "DT_FINVIE")
		STEP.verifyValue(0, myJDD, "NU_COUHOR")
		STEP.verifyValue(0, myJDD, "NU_USAGAR")
		STEP.verifyValue(0, myJDD, "NU_FINUSA")
		STEP.verifyValue(0, myJDD, "NU_COUARR")
		STEP.verifyValue(0, myJDD, "ST_NUMINV")
		STEP.verifyValue(0, myJDD, "ST_OBS")
		STEP.verifyValue(0, myJDD, "ID_CODCAL")
		STEP.verifyValue(0, myJDD, "ST_DESID_CODCAL")
		STEP.verifyValue(0, myJDD, "ID_CODCONTRA")
		STEP.verifyValue(0, myJDD, "ST_DESID_CODCONTRA")
		STEP.verifyElementCheckedOrNot(0, myJDD, "ST_COM", "O")
		STEP.verifyElementCheckedOrNot(0, myJDD, "ST_MAT", "O")
		STEP.verifyElementCheckedOrNot(0, myJDD, "ST_CONTRABT", "O")
		STEP.verifyElementCheckedOrNot(0, myJDD, "ST_ANA", "O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		STEP.simpleClick(0, myJDD, "tab_Notes")
		STEP.verifyElementVisible(0, myJDD, "tab_NotesSelected")
		
		STEP.scrollToPosition('', 0, 0)
		
		JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.EQU'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
		
		String notes = JDD_Note.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC1'))
		String consignes = JDD_Note.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC2'))
		
		STEP.verifyText(0, JDD_Note,"DOC_Notes",notes)
		STEP.verifyText(0, JDD_Note,"DOC_Consignes",consignes)

		
		
	TNRResult.addSTEPGRP("ONGLET ADRESSE")
		
		STEP.simpleClick(0, myJDD, "tab_Adresse")
		STEP.verifyElementVisible(0, myJDD, "tab_AdresseSelected")
	
	
	TNRResult.addEndTestCase()
}

