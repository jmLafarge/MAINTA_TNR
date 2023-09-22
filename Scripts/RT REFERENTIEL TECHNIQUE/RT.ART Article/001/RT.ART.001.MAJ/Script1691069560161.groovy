import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*




'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData("ID_CODART"))


	TNRResult.addSTEPGRP("ONGLET ARTICLE")
			
			STEP.click(0, myJDD,"tab_Article")
			STEP.verifyElementVisible(0, myJDD,"tab_ArticleSelected")
			
			//STEP.setText(0, myJDD, "ID_CODART")
			KW.scrollAndSelectOptionByLabel(myJDD,"ST_ETA")
			//KWCheckbox.scrollAndCheckIfNeeded(myJDD,"CODARTAUTO","O")
			STEP.setText(0, myJDD, "ST_DES")
			KW.scrollAndSelectOptionByLabel(myJDD,"ST_TYPART")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
			
			KWSearchHelper.launch(myJDD, "ID_CODNATART","","")
			//ST_DESID_CODNATART --> pas d'action en modification
			KWSearchHelper.launch(myJDD, "ID_CODGES","","")
			//ST_DESGES --> pas d'action en modification
			
			def JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
			KWMemo.setText(JDD_Note.getStrData("OL_DOC"), 'Notes',true,myJDD,'')
			
			
		TNRResult.addSTEPBLOCK("FOURNISSEUR NORMALISE")
		
		// Lire le JDD spécifique
		JDD JDD_ARTFOU = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001B',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
			KWSearchHelper.launch(JDD_ARTFOU, "ID_CODFOU","","")
			//ST_DESID_CODFOU --> pas d'action en modification
			STEP.setText(0, JDD_ARTFOU, "ST_DES")
			STEP.setText(0, JDD_ARTFOU, "ST_REFFOU")
			
		TNRResult.addSTEPBLOCK("STOCK")
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_MAT","O")
			KWSearchHelper.launch(myJDD, "ID_CODUNI","","")
			STEP.setText(0, myJDD, "NU_PRIPMP")
			
		TNRResult.addSTEPBLOCK("ACHATS")
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_CONOBL","O")
			STEP.setText(0, myJDD, "ST_TXTCDE")
			KWSearchHelper.launch(myJDD, "ST_CODCOM","","SEARCH_ID_CODCMP")
			//ST_DESST_CODCOM --> pas d'action en modification
			KWSearchHelper.launch(myJDD, "ID_CODTVA","","")
			
			/*
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"MAJ_NOM","O")
			KWSearchHelper.launch(myJDD, "NOM_CODLON","","")
			//ST_DESNOM --> pas d'action en modification
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"MAJ_EQU","O")
			KWSearchHelper.launch(myJDD, "EQU_CODLON","","")
			//ST_DESEQU --> pas d'action en modification
			STEP.setText(0, myJDD, "ART_EQU_QTE")
			STEP.setText(0, myJDD, "ART_EQU_OBS")
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"MAJ_MODFAM","O")
			KWSearchHelper.launch(myJDD, "ID_CODFAM","","")
			//ST_DESID_CODFAM --> pas d'action en modification
			STEP.setText(0, myJDD, "MODFAM_CODLON")
			STEP.setText(0, myJDD, "ART_MODFAM_QTE")
			STEP.setText(0, myJDD, "ART_MODFAM_OBS")
			*/
	  
	  
	  
	  
	  
	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    STEP.click(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData("ID_CODART"))
	
		SQL.checkJDDWithBD(myJDD)
		SQL.checkJDDWithBD(JDD_Note)
		
	TNRResult.addEndTestCase()
	
} // fin du if


