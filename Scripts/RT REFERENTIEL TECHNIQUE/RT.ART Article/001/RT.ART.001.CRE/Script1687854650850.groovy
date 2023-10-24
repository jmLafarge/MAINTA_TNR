import internal.GlobalVariable
import tnrJDDManager.GlobalJDD
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
    STEP.goToURLCreate(); STEP.checkCreateScreen()
	

	TNRResult.addSTEPGRP("ONGLET ARTICLE")
		
			STEP.simpleClick(myJDD,"tab_Article")
			STEP.verifyElementVisible(myJDD,"tab_ArticleSelected")
			
			STEP.setText(myJDD,"ID_CODART")
			STEP.selectOptionByLabel(myJDD,"ST_ETA")
			STEP.clickCheckboxIfNeeded(myJDD,"CODARTAUTO","O")
			STEP.setText(myJDD,"ST_DES")
			STEP.selectOptionByLabel(myJDD,"ST_TYPART")
			STEP.clickCheckboxIfNeeded(myJDD,"ST_INA","O")
			
			STEP.setText(myJDD,"ID_CODNATART")
			//ST_DESID_CODNATART --> pas d'action en création
			STEP.setText(myJDD,"ID_CODGES")
			//ST_DESGES --> pas d'action en création
			
			JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
			STEP.setMemoText(JDD_Note.getStrData("OL_DOC"), 'Notes',false,myJDD,'')
			
		TNRResult.addSTEPBLOCK("FOURNISSEUR NORMALISE")
		
			// Lire le JDD spécifique
			JDD JDD_ARTFOU = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001B',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
				STEP.setText(JDD_ARTFOU,"ID_CODFOU")
				//ST_DESID_CODFOU --> pas d'action en création
				STEP.setText(JDD_ARTFOU,"ST_DES")
				STEP.setText(JDD_ARTFOU,"ST_REFFOU")
			
		TNRResult.addSTEPBLOCK("STOCK")
			
			STEP.clickCheckboxIfNeeded(myJDD,"ST_MAT","O")
			STEP.setText(myJDD,"ID_CODUNI")
			STEP.setText(myJDD,"NU_PRIPMP")
			
		TNRResult.addSTEPBLOCK("ACHATS")
			
			STEP.clickCheckboxIfNeeded(myJDD,"ST_CONOBL","O")
			STEP.setText(myJDD,"ST_TXTCDE")
			STEP.setText(myJDD,"ST_CODCOM")
			//ST_DESST_CODCOM --> pas d'action en création
			STEP.setText(myJDD,"ID_CODTVA")
			
			/*
			STEP.clickCheckboxIfNeeded(myJDD,"MAJ_NOM","O")
			STEP.setText(myJDD,"NOM_CODLON")
			//ST_DESNOM --> pas d'action en création
			
			STEP.clickCheckboxIfNeeded(myJDD,"MAJ_EQU","O")
			STEP.setText(myJDD,"EQU_CODLON")
			//ST_DESEQU --> pas d'action en création
			STEP.setText(myJDD,"ART_EQU_QTE")
			STEP.setText(myJDD,"ART_EQU_OBS")
			
			STEP.clickCheckboxIfNeeded(myJDD,"MAJ_MODFAM","O")
			STEP.setText(myJDD,"ID_CODFAM")
			//ST_DESID_CODFAM --> pas d'action en création
			STEP.setText(myJDD,"MODFAM_CODLON")
			STEP.setText(myJDD,"ART_MODFAM_QTE")
			STEP.setText(myJDD,"ART_MODFAM_OBS")
			*/
	

	
	
			
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(myJDD.getStrData('ID_CODART'))
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC1')
		JDD_Note.replaceSEQUENCIDInJDD('ID_NUMDOC')
	
		STEP.checkJDDWithBD(myJDD) // prendre en compte la valeur de ARTNUM car le where du select se fait sur ID_CODART
		STEP.checkJDDWithBD(JDD_Note)
		
		
	TNRResult.addEndTestCase()

} // fin du if



