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
    NAV.goToURL_Creation_and_checkCartridge()
	

	TNRResult.addSTEPGRP("ONGLET ARTICLE")
		
			KW.click(myJDD,"tab_Article")
			KW.isElementVisible(myJDD,"tab_ArticleSelected")
			
			KW.setText(myJDD,"ID_CODART")
			KW.scrollAndSelectOptionByLabel(myJDD,"ST_ETA")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"CODARTAUTO","O")
			KW.setText(myJDD,"ST_DES")
			KW.scrollAndSelectOptionByLabel(myJDD,"ST_TYPART")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
			
			KW.setText(myJDD,"ID_CODNATART")
			//ST_DESID_CODNATART --> pas d'action en création
			KW.setText(myJDD,"ID_CODGES")
			//ST_DESGES --> pas d'action en création
			
			JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
			KWMemo.setText(JDD_Note.getStrData("OL_DOC"), 'Notes',false,myJDD,'')
			
		TNRResult.addSTEPBLOCK("FOURNISSEUR NORMALISE")
		
			// Lire le JDD spécifique
			JDD JDD_ARTFOU = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001B',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
				KW.setText(JDD_ARTFOU,"ID_CODFOU")
				//ST_DESID_CODFOU --> pas d'action en création
				KW.setText(JDD_ARTFOU,"ST_DES")
				KW.setText(JDD_ARTFOU,"ST_REFFOU")
			
		TNRResult.addSTEPBLOCK("STOCK")
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_MAT","O")
			KW.setText(myJDD,"ID_CODUNI")
			KW.setText(myJDD,"NU_PRIPMP")
			
		TNRResult.addSTEPBLOCK("ACHATS")
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_CONOBL","O")
			KW.setText(myJDD,"ST_TXTCDE")
			KW.setText(myJDD,"ST_CODCOM")
			//ST_DESST_CODCOM --> pas d'action en création
			KW.setText(myJDD,"ID_CODTVA")
			
			/*
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"MAJ_NOM","O")
			KW.setText(myJDD,"NOM_CODLON")
			//ST_DESNOM --> pas d'action en création
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"MAJ_EQU","O")
			KW.setText(myJDD,"EQU_CODLON")
			//ST_DESEQU --> pas d'action en création
			KW.setText(myJDD,"ART_EQU_QTE")
			KW.setText(myJDD,"ART_EQU_OBS")
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD,"MAJ_MODFAM","O")
			KW.setText(myJDD,"ID_CODFAM")
			//ST_DESID_CODFAM --> pas d'action en création
			KW.setText(myJDD,"MODFAM_CODLON")
			KW.setText(myJDD,"ART_MODFAM_QTE")
			KW.setText(myJDD,"ART_MODFAM_OBS")
			*/
	

	
	
			
	TNRResult.addSTEPACTION('VALIDATION')
		
	    KW.click(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData('ID_CODART'))
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC1')
		JDD_Note.replaceSEQUENCIDInJDD('ID_NUMDOC')
	
		SQL.checkJDDWithBD(myJDD) // prendre en compte la valeur de ARTNUM car le where du select se fait sur ID_CODART
		SQL.checkJDDWithBD(JDD_Note)
		
		
	TNRResult.addEndTestCase()

} // fin du if



