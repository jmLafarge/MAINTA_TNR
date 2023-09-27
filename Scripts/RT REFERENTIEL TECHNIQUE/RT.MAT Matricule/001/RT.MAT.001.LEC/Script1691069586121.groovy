import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper
import tnrResultManager.TNRResult
import tnrWebUI.*


'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData()); STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData())

	

			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")

			
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			STEP.simpleClick(0, myJDD, "tab_Matricule")
			STEP.verifyElementVisible(0, myJDD, "tab_MatriculeSelected")
			
			STEP.verifyValue(0, myJDD, "ID_NUMMAT")
			STEP.verifyValue(0, myJDD, "ST_NUMINV")
			STEP.verifyElementCheckedOrNot(0, myJDD, "ST_INA", "O")
			STEP.verifyValue(0, myJDD, "ST_DES")
			STEP.verifyValue(0, myJDD, "ST_ETA")
			STEP.verifyOptionSelectedByLabel(0, myJDD, "NU_TYP")
			STEP.verifyValue(0, myJDD, "NU_PRISTO")
			STEP.verifyValue(0, myJDD, "ID_CODART")
			STEP.verifyValue(0, myJDD, "ST_DESID_CODART")
			STEP.verifyValue(0, myJDD, "ID_CODMOY")
			STEP.verifyValue(0, myJDD, "ST_DESID_CODMOY")
			STEP.verifyValue(0, myJDD, "ID_CODGES")
			STEP.verifyValue(0, myJDD, "ST_DESGES")
			STEP.verifyValue(0, myJDD, "ID_NUMCRI")
			STEP.verifyValue(0, myJDD, "ST_DESID_NUMCRI")
			STEP.verifyValue(0, myJDD, "ID_CODIMP")
			STEP.verifyValue(0, myJDD, "ST_DESIMP")
			STEP.verifyValue(0, myJDD, "ID_NUMGRO")
			STEP.verifyValue(0, myJDD, "ST_DESGRO")
			STEP.verifyValue(0, myJDD, "ID_CODCOM")
			STEP.verifyValue(0, myJDD, "ST_DESID_CODCOM")
			STEP.verifyValue(0, myJDD, "NU_USA")
			STEP.verifyValue(0, myJDD, "ID_CODCON")
			STEP.verifyElementCheckedOrNot(0, myJDD, "ST_TEC", "O")
			STEP.verifyElementCheckedOrNot(0, myJDD, "ST_REP", "O")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			STEP.simpleClick(0, myJDD, "tab_Fiche")
			STEP.verifyElementVisible(0, myJDD, "tab_FicheSelected")
			
			STEP.verifyValue(0, myJDD, "ID_CODFOUINI")
			STEP.verifyValue(0, myJDD, "ST_REFFOU")
			STEP.verifyValue(0, myJDD, "ID_CODCONSTR")
			STEP.verifyValue(0, myJDD, "ST_REFCON")
			STEP.verifyDateValue(0, myJDD, "DT_ACH")
			STEP.verifyDateValue(0, myJDD, "DT_FAC")
			STEP.verifyValue(0, myJDD, "NU_PRIACH")
			STEP.verifyDateValue(0, myJDD, "DT_FINGAR")
			STEP.verifyDateValue(0, myJDD, "DT_FINVIE")
			STEP.verifyValue(0, myJDD, "NU_PRIACT")
			STEP.verifyValue(0, myJDD, "NU_USAGAR")
			STEP.verifyValue(0, myJDD, "NU_FINUSA")
			STEP.verifyValue(0, myJDD, "ID_CODCAL")
			STEP.verifyValue(0, myJDD, "ST_DESID_CODCAL")
			STEP.verifyValue(0, myJDD, "ID_CODGAR")
			STEP.verifyValue(0, myJDD, "ST_DESID_CODGAR")
			STEP.verifyValue(0, myJDD, "ID_REFCOM")
			STEP.verifyValue(0, myJDD, "ST_DESID_REFCOM")
			STEP.verifyValue(0, myJDD, "ST_AFFCOM")
			STEP.verifyValue(0, myJDD, "ID_CODINTPRO")
			STEP.verifyValue(0, myJDD, "ST_NOM_ID_CODINTPRO")
			STEP.verifyValue(0, myJDD, "ID_CODINTGES")
			STEP.verifyValue(0, myJDD, "ST_NOM_ID_CODINTGES")
			STEP.verifyValue(0, myJDD, "ID_CODINTEXP")
			STEP.verifyValue(0, myJDD, "ST_NOM_ID_CODINTEXP")
			STEP.verifyValue(0, myJDD, "ID_CODINTMAI")
			STEP.verifyValue(0, myJDD, "ST_NOM_ID_CODINTMAI")
			STEP.verifyElementCheckedOrNot(0, myJDD, "ST_CONTRA", "O")
			STEP.verifyElementCheckedOrNot(0, myJDD, "ST_CONTRABT", "O")
			STEP.verifyElementCheckedOrNot(0, myJDD, "ST_PRE", "O")
			STEP.verifyElementCheckedOrNot(0, myJDD, "ST_INS", "O")
			
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			STEP.simpleClick(0, myJDD, "tab_Notes")
			STEP.verifyElementVisible(0, myJDD, "tab_NotesSelected")
			
			STEP.scrollToPosition('', 0, 0)
			
			STEP.verifyText(0, new JDD(JDDFileMapper.getFullnameFromModObj('RT.MAT'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS),"OL_DOC")
			
			
		TNRResult.addSTEPGRP("ONGLET ETAT")
			
			STEP.simpleClick(0, myJDD, "tab_Etat")
			STEP.verifyElementVisible(0, myJDD, "tab_EtatSelected")
			
			STEP.verifyRadioChecked(0, myJDD, "ST_POS")
			
			STEP.verifyValue(0, myJDD, "ID_NUMEMP")
			STEP.verifyValue(0, myJDD, "ST_DESEMP")
			STEP.verifyValue(0, myJDD, "ST_CODLON")
			STEP.verifyValue(0, myJDD, "EQU_DES")
			STEP.verifyValue(0, myJDD, "ID_CODMAG")
			STEP.verifyValue(0, myJDD, "ST_DESMAG")
			STEP.verifyValue(0, myJDD, "ID_CODFOU")
			STEP.verifyValue(0, myJDD, "ST_DESID_CODFOU")
			STEP.verifyValue(0, myJDD, "ST_AUT")
			STEP.verifyValue(0, myJDD, "DT_DEP")
			STEP.verifyValue(0, myJDD, "DT_RET")
			//STEP.verifyElementCheckedOrNot(0, myJDD, "NUMMAT2NUMAUTO", "O")
			//STEP.verifyElementCheckedOrNot(0, myJDD, "DISABLE_RECTIFSTO", "O")
	
	
	
	TNRResult.addEndTestCase()
}

