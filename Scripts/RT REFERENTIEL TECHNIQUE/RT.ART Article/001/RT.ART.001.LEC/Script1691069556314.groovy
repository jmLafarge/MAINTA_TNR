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
    STEP.goToURLReadUpdateDelete(myJDD.getStrData('ID_CODART'))
	STEP.checkReadUpdateDeleteScreen(myJDD.getStrData('ID_CODART'))

	
	TNRResult.addSTEPGRP("ONGLET ARTICLE")
			
			STEP.simpleClick(myJDD,"tab_Article")
			STEP.verifyElementVisible(myJDD,"tab_ArticleSelected")
			
			STEP.verifyValue(myJDD,"ID_CODART")
			STEP.verifyOptionSelectedByLabel(myJDD,"ST_ETA")

			STEP.verifyValue(myJDD,"ST_DES")
			STEP.verifyOptionSelectedByLabel(myJDD,"ST_TYPART")
			STEP.verifyBoxCheckedOrNot(myJDD,"ST_INA","O")
			
			STEP.verifyValue(myJDD,"ID_CODNATART")
			STEP.verifyValue(myJDD,"ST_DESID_CODNATART")
			STEP.verifyValue(myJDD,"ID_CODGES")
			STEP.verifyValue(myJDD,"ST_DESGES")
			
			STEP.verifyText(new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS),"OL_DOC")

			
		TNRResult.addSTEPBLOCK("FOURNISSEUR NORMALISE")
		
			// Lire le JDD spécifique
			JDD JDD_ARTFOU = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001B',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
				STEP.verifyValue(JDD_ARTFOU,"ID_CODFOU")
			STEP.verifyValue(myJDD,"ST_DESID_CODFOU")
				STEP.verifyValue(JDD_ARTFOU,"ST_DES")
				STEP.verifyValue(JDD_ARTFOU,"ST_REFFOU")
			
		TNRResult.addSTEPBLOCK("STOCK")
			
			STEP.verifyBoxCheckedOrNot(myJDD,"ST_MAT","O")
			STEP.verifyValue(myJDD,"ID_CODUNI")
			STEP.verifyValue(myJDD,"NU_PRIPMP")
			
		TNRResult.addSTEPBLOCK("ACHATS")
			
			STEP.verifyBoxCheckedOrNot(myJDD,"ST_CONOBL","O")
			STEP.verifyValue(myJDD,"ST_TXTCDE")
			STEP.verifyValue(myJDD,"ST_CODCOM")
			STEP.verifyValue(myJDD,"ST_DESST_CODCOM")
			STEP.verifyValue(myJDD,"ID_CODTVA")
			
			/*
			STEP.verifyBoxCheckedOrNot(myJDD,"MAJ_NOM","O")
			STEP.verifyValue(myJDD,"NOM_CODLON")
			STEP.verifyValue(myJDD,"ST_DESNOM")
			
			STEP.verifyBoxCheckedOrNot(myJDD,"MAJ_EQU","O")
			STEP.verifyValue(myJDD,"EQU_CODLON")
			STEP.verifyValue(myJDD,"ST_DESEQU")
			STEP.verifyValue(myJDD,"ART_EQU_QTE")
			STEP.verifyValue(myJDD,"ART_EQU_OBS")
			
			STEP.verifyBoxCheckedOrNot(myJDD,"MAJ_MODFAM","O")
			STEP.verifyValue(myJDD,"ID_CODFAM")
			STEP.verifyValue(myJDD,"ST_DESID_CODFAM")
			STEP.verifyValue(myJDD,"MODFAM_CODLON")
			STEP.verifyValue(myJDD,"ART_MODFAM_QTE")
			STEP.verifyValue(myJDD,"ART_MODFAM_OBS")

			*/


	TNRResult.addEndTestCase()
}

