import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLCreate(1); STEP.checkCreateScreen(2)
	

	TNRResult.addSTEPGRP("ONGLET ARTICLE")
		
			STEP.simpleClick(0, myJDD,"tab_Article")
			STEP.verifyElementVisible(0, myJDD,"tab_ArticleSelected")
			
			STEP.setText(0, myJDD,"ID_CODART")
			STEP.scrollAndSelectOptionByLabel(0, myJDD,"ST_ETA")
			STEP.scrollAndCheckIfNeeded(0, myJDD,"CODARTAUTO","O")
			STEP.setText(0, myJDD,"ST_DES")
			STEP.scrollAndSelectOptionByLabel(0, myJDD,"ST_TYPART")
			STEP.scrollAndCheckIfNeeded(0, myJDD,"ST_INA","O")
			
			STEP.setText(0, myJDD,"ID_CODNATART")
			//ST_DESID_CODNATART --> pas d'action en création
			STEP.setText(0, myJDD,"ID_CODGES")
			//ST_DESGES --> pas d'action en création
			
			JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
			STEP.setMemoText(0, JDD_Note.getStrData("OL_DOC"), 'Notes',false,myJDD,'')
			
		TNRResult.addSTEPBLOCK("FOURNISSEUR NORMALISE")
		
			// Lire le JDD spécifique
			JDD JDD_ARTFOU = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001B',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
				STEP.setText(0, JDD_ARTFOU,"ID_CODFOU")
				//ST_DESID_CODFOU --> pas d'action en création
				STEP.setText(0, JDD_ARTFOU,"ST_DES")
				STEP.setText(0, JDD_ARTFOU,"ST_REFFOU")
			
		TNRResult.addSTEPBLOCK("STOCK")
			
			STEP.scrollAndCheckIfNeeded(0, myJDD,"ST_MAT","O")
			STEP.setText(0, myJDD,"ID_CODUNI")
			STEP.setText(0, myJDD,"NU_PRIPMP")
			
		TNRResult.addSTEPBLOCK("ACHATS")
			
			STEP.scrollAndCheckIfNeeded(0, myJDD,"ST_CONOBL","O")
			STEP.setText(0, myJDD,"ST_TXTCDE")
			STEP.setText(0, myJDD,"ST_CODCOM")
			//ST_DESST_CODCOM --> pas d'action en création
			STEP.setText(0, myJDD,"ID_CODTVA")
			
			/*
			STEP.scrollAndCheckIfNeeded(0, myJDD,"MAJ_NOM","O")
			STEP.setText(0, myJDD,"NOM_CODLON")
			//ST_DESNOM --> pas d'action en création
			
			STEP.scrollAndCheckIfNeeded(0, myJDD,"MAJ_EQU","O")
			STEP.setText(0, myJDD,"EQU_CODLON")
			//ST_DESEQU --> pas d'action en création
			STEP.setText(0, myJDD,"ART_EQU_QTE")
			STEP.setText(0, myJDD,"ART_EQU_OBS")
			
			STEP.scrollAndCheckIfNeeded(0, myJDD,"MAJ_MODFAM","O")
			STEP.setText(0, myJDD,"ID_CODFAM")
			//ST_DESID_CODFAM --> pas d'action en création
			STEP.setText(0, myJDD,"MODFAM_CODLON")
			STEP.setText(0, myJDD,"ART_MODFAM_QTE")
			STEP.setText(0, myJDD,"ART_MODFAM_OBS")
			*/
	

	
	
			
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.simpleClick(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(0, myJDD.getStrData('ID_CODART'))
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC1')
		JDD_Note.replaceSEQUENCIDInJDD('ID_NUMDOC')
	
		STEP.checkJDDWithBD(0, myJDD) // prendre en compte la valeur de ARTNUM car le where du select se fait sur ID_CODART
		STEP.checkJDDWithBD(0, JDD_Note)
		
		
	TNRResult.addEndTestCase()

} // fin du if



