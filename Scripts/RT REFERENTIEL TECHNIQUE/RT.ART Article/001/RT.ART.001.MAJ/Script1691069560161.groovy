import internal.GlobalVariable
import tnrJDDManager.GlobalJDD
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
			
			//STEP.setText(myJDD, "ID_CODART")
			STEP.selectOptionByLabel(myJDD,"ST_ETA")
			//STEP.clickCheckboxIfNeeded(myJDD,"CODARTAUTO","O")
			STEP.setText(myJDD, "ST_DES")
			STEP.selectOptionByLabel(myJDD,"ST_TYPART")
			STEP.clickCheckboxIfNeeded(myJDD,"ST_INA","O")
			
			STEP.searchWithHelper(myJDD, "ID_CODNATART","","")
			//ST_DESID_CODNATART --> pas d'action en modification
			STEP.searchWithHelper(myJDD, "ID_CODGES","","")
			//ST_DESGES --> pas d'action en modification
			
			def JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
			STEP.setMemoText(JDD_Note.getStrData("OL_DOC"), 'Notes',true,myJDD,'')
			
			
		TNRResult.addSTEPBLOCK("FOURNISSEUR NORMALISE")
		
		// Lire le JDD spécifique
		JDD JDD_ARTFOU = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001B',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
			STEP.searchWithHelper(JDD_ARTFOU, "ID_CODFOU","","")
			//ST_DESID_CODFOU --> pas d'action en modification
			STEP.setText(JDD_ARTFOU, "ST_DES")
			STEP.setText(JDD_ARTFOU, "ST_REFFOU")
			
		TNRResult.addSTEPBLOCK("STOCK")
			
			STEP.clickCheckboxIfNeeded(myJDD,"ST_MAT","O")
			STEP.searchWithHelper(myJDD, "ID_CODUNI","","")
			STEP.setText(myJDD, "NU_PRIPMP")
			
		TNRResult.addSTEPBLOCK("ACHATS")
			
			STEP.clickCheckboxIfNeeded(myJDD,"ST_CONOBL","O")
			STEP.setText(myJDD, "ST_TXTCDE")
			STEP.searchWithHelper(myJDD, "ST_CODCOM","","SEARCH_ID_CODCMP")
			//ST_DESST_CODCOM --> pas d'action en modification
			STEP.searchWithHelper(myJDD, "ID_CODTVA","","")
			
			/*
			STEP.clickCheckboxIfNeeded(myJDD,"MAJ_NOM","O")
			STEP.searchWithHelper(myJDD, "NOM_CODLON","","")
			//ST_DESNOM --> pas d'action en modification
			
			STEP.clickCheckboxIfNeeded(myJDD,"MAJ_EQU","O")
			STEP.searchWithHelper(myJDD, "EQU_CODLON","","")
			//ST_DESEQU --> pas d'action en modification
			STEP.setText(myJDD, "ART_EQU_QTE")
			STEP.setText(myJDD, "ART_EQU_OBS")
			
			STEP.clickCheckboxIfNeeded(myJDD,"MAJ_MODFAM","O")
			STEP.searchWithHelper(myJDD, "ID_CODFAM","","")
			//ST_DESID_CODFAM --> pas d'action en modification
			STEP.setText(myJDD, "MODFAM_CODLON")
			STEP.setText(myJDD, "ART_MODFAM_QTE")
			STEP.setText(myJDD, "ART_MODFAM_OBS")
			*/
	  
	  
	  
	  
	  
	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(myJDD.getStrData("ID_CODART"))
	
		STEP.checkJDDWithBD(myJDD)
		STEP.checkJDDWithBD(JDD_Note)
		
	//TNRResult.addEndTestCase()
	
} // fin du if


