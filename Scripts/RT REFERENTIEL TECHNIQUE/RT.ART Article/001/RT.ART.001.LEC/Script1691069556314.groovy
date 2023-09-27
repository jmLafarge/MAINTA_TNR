import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper
import tnrWebUI.*

import tnrResultManager.TNRResult


'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_ReadUpdateDelete_and_checkCartridge(myJDD.getStrData('ID_CODART'))

	
	TNRResult.addSTEPGRP("ONGLET ARTICLE")
			
			STEP.simpleClick(0, myJDD,"tab_Article")
			STEP.verifyElementVisible(0, myJDD,"tab_ArticleSelected")
			
			STEP.verifyValue(0, myJDD,"ID_CODART")
			STEP.verifyOptionSelectedByLabel(0, myJDD,"ST_ETA")

			STEP.verifyValue(0, myJDD,"ST_DES")
			STEP.verifyOptionSelectedByLabel(0, myJDD,"ST_TYPART")
			STEP.verifyElementCheckedOrNot(0, myJDD,"ST_INA","O")
			
			STEP.verifyValue(0, myJDD,"ID_CODNATART")
			STEP.verifyValue(0, myJDD,"ST_DESID_CODNATART")
			STEP.verifyValue(0, myJDD,"ID_CODGES")
			STEP.verifyValue(0, myJDD,"ST_DESGES")
			
			STEP.verifyText(0, new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS),"OL_DOC")
			
			
		TNRResult.addSTEPBLOCK("FOURNISSEUR NORMALISE")
		
			// Lire le JDD spécifique
			JDD JDD_ARTFOU = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001B',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
				STEP.verifyValue(JDD_ARTFOU,"ID_CODFOU")
			STEP.verifyValue(0, myJDD,"ST_DESID_CODFOU")
				STEP.verifyValue(JDD_ARTFOU,"ST_DES")
				STEP.verifyValue(JDD_ARTFOU,"ST_REFFOU")
			
		TNRResult.addSTEPBLOCK("STOCK")
			
			STEP.verifyElementCheckedOrNot(0, myJDD,"ST_MAT","O")
			STEP.verifyValue(0, myJDD,"ID_CODUNI")
			STEP.verifyValue(0, myJDD,"NU_PRIPMP")
			
		TNRResult.addSTEPBLOCK("ACHATS")
			
			STEP.verifyElementCheckedOrNot(0, myJDD,"ST_CONOBL","O")
			STEP.verifyValue(0, myJDD,"ST_TXTCDE")
			STEP.verifyValue(0, myJDD,"ST_CODCOM")
			STEP.verifyValue(0, myJDD,"ST_DESST_CODCOM")
			STEP.verifyValue(0, myJDD,"ID_CODTVA")
			
			/*
			STEP.verifyElementCheckedOrNot(0, myJDD,"MAJ_NOM","O")
			STEP.verifyValue(0, myJDD,"NOM_CODLON")
			STEP.verifyValue(0, myJDD,"ST_DESNOM")
			
			STEP.verifyElementCheckedOrNot(0, myJDD,"MAJ_EQU","O")
			STEP.verifyValue(0, myJDD,"EQU_CODLON")
			STEP.verifyValue(0, myJDD,"ST_DESEQU")
			STEP.verifyValue(0, myJDD,"ART_EQU_QTE")
			STEP.verifyValue(0, myJDD,"ART_EQU_OBS")
			
			STEP.verifyElementCheckedOrNot(0, myJDD,"MAJ_MODFAM","O")
			STEP.verifyValue(0, myJDD,"ID_CODFAM")
			STEP.verifyValue(0, myJDD,"ST_DESID_CODFAM")
			STEP.verifyValue(0, myJDD,"MODFAM_CODLON")
			STEP.verifyValue(0, myJDD,"ART_MODFAM_QTE")
			STEP.verifyValue(0, myJDD,"ART_MODFAM_OBS")

			*/


	TNRResult.addEndTestCase()
}

