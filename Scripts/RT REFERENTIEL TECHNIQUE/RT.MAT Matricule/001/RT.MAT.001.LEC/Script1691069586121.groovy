import internal.GlobalVariable
import tnrJDDManager.JDD;  
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrWebUI.*


'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(myJDD.getStrData()); STEP.checkReadUpdateDeleteScreen(myJDD.getStrData())

	

			//Rappel pour ajouter un block dans le fichier Resultat :
			//TNRResult.addSTEPBLOCK("DU TEXTE")

			
		TNRResult.addSTEPGRP("ONGLET MATRICULE")
			
			STEP.simpleClick(myJDD, "tab_Matricule")
			STEP.verifyElementVisible(myJDD, "tab_MatriculeSelected")
			
			STEP.verifyValue(myJDD, "ID_NUMMAT")
			STEP.verifyValue(myJDD, "ST_NUMINV")
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_INA", "O")
			STEP.verifyValue(myJDD, "ST_DES")
			STEP.verifyValue(myJDD, "ST_ETA")
			STEP.verifyOptionSelectedByLabel(myJDD, "NU_TYP")
			STEP.verifyValue(myJDD, "NU_PRISTO")
			
			STEP.verifyValue(myJDD, "ID_CODART")
			STEP.verifyValue(myJDD, "ST_DESID_CODART")
			STEP.verifyValue(myJDD, "ID_CODMOY")
			STEP.verifyValue(myJDD, "ST_DESID_CODMOY")
			STEP.verifyValue(myJDD, "ID_CODGES")
			STEP.verifyValue(myJDD, "ST_DESGES")
			STEP.verifyValue(myJDD, "ID_NUMCRI")
			STEP.verifyValue(myJDD, "ST_DESID_NUMCRI")
			STEP.verifyValue(myJDD, "ID_CODIMP")
			STEP.verifyValue(myJDD, "ST_DESIMP")
			STEP.verifyValue(myJDD, "ID_NUMGRO") //GRO_CODLON
			STEP.verifyValue(myJDD, "ST_DESGRO")
			STEP.verifyValue(myJDD, "ID_CODCOM")
			STEP.verifyValue(myJDD, "ST_DESID_CODCOM")
			STEP.verifyValue(myJDD, "NU_USA")
			STEP.verifyValue(myJDD, "ID_CODCON")
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_PAT", "O")
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_TEC", "O")
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_REP", "O")
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_SOUASS", "O")
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_SOUINV", "O")
			
			STEP.verifyDateValue(myJDD, "DT_ENT")
			STEP.verifyDateValue(myJDD, "DT_SER")
			STEP.verifyValue(myJDD, "NU_VALINI")
			STEP.verifyValue(myJDD, "NU_VALINV")
			
			STEP.verifyValue(myJDD, "DT_DEP")
			STEP.verifyValue(myJDD, "DT_RET")
			
		TNRResult.addSTEPGRP("ONGLET FICHE")
			
			STEP.simpleClick(myJDD, "tab_Fiche")
			STEP.verifyElementVisible(myJDD, "tab_FicheSelected")
			
			STEP.verifyValue(myJDD, "ID_CODFOUINI")
			STEP.verifyValue(myJDD, "ST_REFFOU")
			STEP.verifyValue(myJDD, "ID_CODCONSTR")
			STEP.verifyValue(myJDD, "ST_REFCON")
			
			STEP.verifyDateValue(myJDD, "DT_ACH")
			STEP.verifyDateValue(myJDD, "DT_FAC")
			STEP.verifyValue(myJDD, "NU_PRIACH")
			STEP.verifyDateValue(myJDD, "DT_FINGAR")
			STEP.verifyDateValue(myJDD, "DT_FINVIE")
			STEP.verifyValue(myJDD, "NU_PRIACT")
			STEP.verifyValue(myJDD, "NU_USAGAR")
			STEP.verifyValue(myJDD, "NU_FINUSA")
			
			STEP.verifyValue(myJDD, "ID_CODCAL")
			STEP.verifyValue(myJDD, "ST_DESID_CODCAL")
			STEP.verifyValue(myJDD, "ID_CODGAR")
			STEP.verifyValue(myJDD, "ST_DESID_CODGAR")
			STEP.verifyValue(myJDD, "ID_REFCOM")
			STEP.verifyValue(myJDD, "ST_DESID_REFCOM")
			STEP.verifyValue(myJDD, "ST_AFFCOM")
			
			STEP.verifyValue(myJDD, "ID_CODINTPRO")
			STEP.verifyValue(myJDD, "ST_NOM_ID_CODINTPRO")
			STEP.verifyValue(myJDD, "ID_CODINTGES")
			STEP.verifyValue(myJDD, "ST_NOM_ID_CODINTGES")
			STEP.verifyValue(myJDD, "ID_CODINTEXP")
			STEP.verifyValue(myJDD, "ST_NOM_ID_CODINTEXP")
			STEP.verifyValue(myJDD, "ID_CODINTMAI")
			STEP.verifyValue(myJDD, "ST_NOM_ID_CODINTMAI")
			
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_CONTRA", "O")
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_CONTRABT", "O")
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_PRE", "O")
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_INS", "O")
			
			
		TNRResult.addSTEPGRP("ONGLET NOTES")
			
			STEP.simpleClick(myJDD, "tab_Notes")
			STEP.verifyElementVisible(myJDD, "tab_NotesSelected")
			
			STEP.scrollToPosition( 0, 0)
			
			STEP.verifyText(new JDD(JDDFileMapper.getFullnameFromModObj('RT.MAT'),'001C',GlobalVariable.CAS_DE_TEST_EN_COURS),"OL_DOC")
			
			
		TNRResult.addSTEPGRP("ONGLET ETAT")
			
			STEP.simpleClick(myJDD, "tab_Etat")
			STEP.verifyElementVisible(myJDD, "tab_EtatSelected")
			
			STEP.verifyRadioChecked(myJDD, "ST_POS")
			
			STEP.verifyValue(myJDD, "ID_NUMEMP")
			STEP.verifyValue(myJDD, "ST_DESEMP")
			STEP.verifyValue(myJDD, "ST_CODLON")
			STEP.verifyValue(myJDD, "EQU_DES")
			STEP.verifyValue(myJDD, "ID_CODMAG")
			STEP.verifyValue(myJDD, "ST_DESMAG")
			STEP.verifyValue(myJDD, "ID_CODFOU")
			STEP.verifyValue(myJDD, "ST_DESID_CODFOU")
			STEP.verifyValue(myJDD, "ST_AUT")

			//STEP.verifyBoxCheckedOrNot(myJDD, "NUMMAT2NUMAUTO", "O")
			//STEP.verifyBoxCheckedOrNot(myJDD, "DISABLE_RECTIFSTO", "O")
	
	
	
	TNRResult.addEndTestCase()
}

