import internal.GlobalVariable
import tnrJDDManager.JDDFileMapper

import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrWebUI.*


'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD(myJDD.getStrData())
	NAV.verifierEcranRUD(myJDD.getStrData('ST_CODCOU'))

	
		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
		
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
		
		STEP.click(0, myJDD, "tab_Equipement")
		STEP.verifyElementVisible(0, myJDD, "tab_EquipementSelected")
		
		KW.verifyValue(myJDD, "ST_CODCOU")
		KW.verifyValue(myJDD, "ST_CODPERS")
		KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_INA", "O")
		KW.verifyValue(myJDD, "ST_DESEQU")
		KW.verifyValue(myJDD, "ST_CODLON")
		KW.verifyOptionSelectedByLabel(myJDD, "ST_ETA")
		KW.verifyOptionSelectedByLabel(myJDD, "NU_CRI")
		KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_NIVABS", "O")
		KW.verifyValue(myJDD, "ID_CODGES")
		KW.verifyValue(myJDD, "ST_DESGES")
		//KW.verifyValue(myJDD, "EMP_CODLON")
		KW.verifyValue(myJDD, "ST_DESEMP")
		KW.verifyValue(myJDD, "ID_CODIMP")
		KW.verifyValue(myJDD, "ST_DESIMP")
		//KW.verifyValue(myJDD, "GRO_CODLON")
		KW.verifyValue(myJDD, "ST_DESGRO")
		KW.verifyValue(myJDD, "ID_CODCOM")
		KW.verifyValue(myJDD, "ST_DESID_CODCOM")
		KW.verifyValue(myJDD, "NU_USA")
		KW.verifyValue(myJDD, "ID_CODCON")
		
	TNRResult.addSTEPGRP("ONGLET FICHE")
		
		STEP.click(0, myJDD, "tab_Fiche")
		STEP.verifyElementVisible(0, myJDD, "tab_FicheSelected")
		
		KW.verifyValue(myJDD, "ST_NOMFOU")
		KW.verifyValue(myJDD, "ST_REFFOU")
		KW.verifyValue(myJDD, "NU_PRIACH")
		KW.verifyValue(myJDD, "ST_NOMCON")
		KW.verifyValue(myJDD, "ST_REFCON")
		KW.verifyDateValue(myJDD, "DT_ACH")
		KW.verifyDateValue(myJDD, "DT_SER")
		KW.verifyValue(myJDD, "NU_PRIACT")
		KW.verifyDateValue(myJDD, "DT_FINGAR")
		KW.verifyDateValue(myJDD, "DT_FINVIE")
		KW.verifyValue(myJDD, "NU_COUHOR")
		KW.verifyValue(myJDD, "NU_USAGAR")
		KW.verifyValue(myJDD, "NU_FINUSA")
		KW.verifyValue(myJDD, "NU_COUARR")
		KW.verifyValue(myJDD, "ST_NUMINV")
		KW.verifyValue(myJDD, "ST_OBS")
		KW.verifyValue(myJDD, "ID_CODCAL")
		KW.verifyValue(myJDD, "ST_DESID_CODCAL")
		KW.verifyValue(myJDD, "ID_CODCONTRA")
		KW.verifyValue(myJDD, "ST_DESID_CODCONTRA")
		KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_COM", "O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_MAT", "O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_CONTRABT", "O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_ANA", "O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		STEP.click(0, myJDD, "tab_Notes")
		STEP.verifyElementVisible(0, myJDD, "tab_NotesSelected")
		
		STEP.scrollToPosition(0, 0)
		
		JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.EQU'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS)
		
		String notes = JDD_Note.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC1'))
		String consignes = JDD_Note.myJDDData.getValueOf('OL_DOC',cdt,'ID_NUMDOC',myJDD.getData('ID_NUMDOC2'))
		
		STEP.verifyText(0, JDD_Note,"DOC_Notes",notes)
		STEP.verifyText(0, JDD_Note,"DOC_Consignes",consignes)

		
		
	TNRResult.addSTEPGRP("ONGLET ADRESSE")
		
		STEP.click(0, myJDD, "tab_Adresse")
		STEP.verifyElementVisible(0, myJDD, "tab_AdresseSelected")
	
	
	TNRResult.addEndTestCase()
}

