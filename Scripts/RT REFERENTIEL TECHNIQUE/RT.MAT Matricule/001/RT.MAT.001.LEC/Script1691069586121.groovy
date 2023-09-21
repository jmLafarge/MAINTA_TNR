import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrWebUI.*


'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())

	

			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")

			
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			KW.click(myJDD, "tab_Matricule")
			KW.isElementVisible(myJDD, "tab_MatriculeSelected")
			
			KW.verifyValue(myJDD, "ID_NUMMAT")
			KW.verifyValue(myJDD, "ST_NUMINV")
			KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_INA", "O")
			KW.verifyValue(myJDD, "ST_DES")
			KW.verifyValue(myJDD, "ST_ETA")
			KW.verifyOptionSelectedByLabel(myJDD, "NU_TYP")
			KW.verifyValue(myJDD, "NU_PRISTO")
			KW.verifyValue(myJDD, "ID_CODART")
			KW.verifyValue(myJDD, "ST_DESID_CODART")
			KW.verifyValue(myJDD, "ID_CODMOY")
			KW.verifyValue(myJDD, "ST_DESID_CODMOY")
			KW.verifyValue(myJDD, "ID_CODGES")
			KW.verifyValue(myJDD, "ST_DESGES")
			KW.verifyValue(myJDD, "ID_NUMCRI")
			KW.verifyValue(myJDD, "ST_DESID_NUMCRI")
			KW.verifyValue(myJDD, "ID_CODIMP")
			KW.verifyValue(myJDD, "ST_DESIMP")
			KW.verifyValue(myJDD, "ID_NUMGRO")
			KW.verifyValue(myJDD, "ST_DESGRO")
			KW.verifyValue(myJDD, "ID_CODCOM")
			KW.verifyValue(myJDD, "ST_DESID_CODCOM")
			KW.verifyValue(myJDD, "NU_USA")
			KW.verifyValue(myJDD, "ID_CODCON")
			KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_TEC", "O")
			KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_REP", "O")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			KW.click(myJDD, "tab_Fiche")
			KW.isElementVisible(myJDD, "tab_FicheSelected")
			
			KW.verifyValue(myJDD, "ID_CODFOUINI")
			KW.verifyValue(myJDD, "ST_REFFOU")
			KW.verifyValue(myJDD, "ID_CODCONSTR")
			KW.verifyValue(myJDD, "ST_REFCON")
			KW.verifyDateValue(myJDD, "DT_ACH")
			KW.verifyDateValue(myJDD, "DT_FAC")
			KW.verifyValue(myJDD, "NU_PRIACH")
			KW.verifyDateValue(myJDD, "DT_FINGAR")
			KW.verifyDateValue(myJDD, "DT_FINVIE")
			KW.verifyValue(myJDD, "NU_PRIACT")
			KW.verifyValue(myJDD, "NU_USAGAR")
			KW.verifyValue(myJDD, "NU_FINUSA")
			KW.verifyValue(myJDD, "ID_CODCAL")
			KW.verifyValue(myJDD, "ST_DESID_CODCAL")
			KW.verifyValue(myJDD, "ID_CODGAR")
			KW.verifyValue(myJDD, "ST_DESID_CODGAR")
			KW.verifyValue(myJDD, "ID_REFCOM")
			KW.verifyValue(myJDD, "ST_DESID_REFCOM")
			KW.verifyValue(myJDD, "ST_AFFCOM")
			KW.verifyValue(myJDD, "ID_CODINTPRO")
			KW.verifyValue(myJDD, "ST_NOM_ID_CODINTPRO")
			KW.verifyValue(myJDD, "ID_CODINTGES")
			KW.verifyValue(myJDD, "ST_NOM_ID_CODINTGES")
			KW.verifyValue(myJDD, "ID_CODINTEXP")
			KW.verifyValue(myJDD, "ST_NOM_ID_CODINTEXP")
			KW.verifyValue(myJDD, "ID_CODINTMAI")
			KW.verifyValue(myJDD, "ST_NOM_ID_CODINTMAI")
			KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_CONTRA", "O")
			KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_CONTRABT", "O")
			KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_PRE", "O")
			KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_INS", "O")
			
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			KW.click(myJDD, "tab_Notes")
			KW.isElementVisible(myJDD, "tab_NotesSelected")
			
			KW.scrollToPositionAndWait(0, 0,1)
			
			KW.verifyText(new JDD(JDDFileMapper.getFullnameFromModObj('RT.MAT'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS),"OL_DOC")
			
			
		TNRResult.addSTEPGRP("ONGLET ETAT")
			
			KW.click(myJDD, "tab_Etat")
			KW.isElementVisible(myJDD, "tab_EtatSelected")
			
			KW.verifyRadioChecked(myJDD, "ST_POS")
			
			KW.verifyValue(myJDD, "ID_NUMEMP")
			KW.verifyValue(myJDD, "ST_DESEMP")
			KW.verifyValue(myJDD, "ST_CODLON")
			KW.verifyValue(myJDD, "EQU_DES")
			KW.verifyValue(myJDD, "ID_CODMAG")
			KW.verifyValue(myJDD, "ST_DESMAG")
			KW.verifyValue(myJDD, "ID_CODFOU")
			KW.verifyValue(myJDD, "ST_DESID_CODFOU")
			KW.verifyValue(myJDD, "ST_AUT")
			KW.verifyValue(myJDD, "DT_DEP")
			KW.verifyValue(myJDD, "DT_RET")
			//KWCheckbox.verifyElementCheckedOrNot(myJDD, "NUMMAT2NUMAUTO", "O")
			//KWCheckbox.verifyElementCheckedOrNot(myJDD, "DISABLE_RECTIFSTO", "O")
	
	
	
	TNRResult.addEndTestCase()
}

