import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.KW
import tnrWebUI.Memo
import tnrWebUI.NAV

'Lecture du JDD'
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Creation_and_checkCartridge()
	

	TNRResult.addSTEPGRP("ONGLET ARTICLE")
		
			KW.scrollAndClick(myJDD,"tab_Article")
			KW.waitForElementVisible(myJDD,"tab_ArticleSelected")
			
			KW.scrollAndSetText(myJDD,"ID_CODART")
			KW.scrollAndSelectOptionByLabel(myJDD,"ST_ETA")
			KW.scrollAndCheckIfNeeded(myJDD,"CODARTAUTO","O")
			KW.scrollAndSetText(myJDD,"ST_DES")
			KW.scrollAndSelectOptionByLabel(myJDD,"ST_TYPART")
			KW.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
			
			KW.scrollAndSetText(myJDD,"ID_CODNATART")
			//ST_DESID_CODNATART --> pas d'action en création
			KW.scrollAndSetText(myJDD,"ID_CODGES")
			//ST_DESGES --> pas d'action en création
			
			JDD JDD_Note = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS)
			Memo.setText(JDD_Note.getStrData("OL_DOC"), 'Notes',false,myJDD,'')
			
		TNRResult.addSTEPBLOCK("FOURNISSEUR NORMALISE")
		
			// Lire le JDD spécifique
			JDD JDD_ARTFOU = new JDD(JDDFileMapper.getFullnameFromModObj('RT.ART'),'001B',GlobalVariable.CAS_DE_TEST_EN_COURS)
			
				KW.scrollAndSetText(JDD_ARTFOU,"ID_CODFOU")
				//ST_DESID_CODFOU --> pas d'action en création
				KW.scrollAndSetText(JDD_ARTFOU,"ST_DES")
				KW.scrollAndSetText(JDD_ARTFOU,"ST_REFFOU")
			
		TNRResult.addSTEPBLOCK("STOCK")
			
			KW.scrollAndCheckIfNeeded(myJDD,"ST_MAT","O")
			KW.scrollAndSetText(myJDD,"ID_CODUNI")
			KW.scrollAndSetText(myJDD,"NU_PRIPMP")
			
		TNRResult.addSTEPBLOCK("ACHATS")
			
			KW.scrollAndCheckIfNeeded(myJDD,"ST_CONOBL","O")
			KW.scrollAndSetText(myJDD,"ST_TXTCDE")
			KW.scrollAndSetText(myJDD,"ST_CODCOM")
			//ST_DESST_CODCOM --> pas d'action en création
			KW.scrollAndSetText(myJDD,"ID_CODTVA")
			
			/*
			KW.scrollAndCheckIfNeeded(myJDD,"MAJ_NOM","O")
			KW.scrollAndSetText(myJDD,"NOM_CODLON")
			//ST_DESNOM --> pas d'action en création
			
			KW.scrollAndCheckIfNeeded(myJDD,"MAJ_EQU","O")
			KW.scrollAndSetText(myJDD,"EQU_CODLON")
			//ST_DESEQU --> pas d'action en création
			KW.scrollAndSetText(myJDD,"ART_EQU_QTE")
			KW.scrollAndSetText(myJDD,"ART_EQU_OBS")
			
			KW.scrollAndCheckIfNeeded(myJDD,"MAJ_MODFAM","O")
			KW.scrollAndSetText(myJDD,"ID_CODFAM")
			//ST_DESID_CODFAM --> pas d'action en création
			KW.scrollAndSetText(myJDD,"MODFAM_CODLON")
			KW.scrollAndSetText(myJDD,"ART_MODFAM_QTE")
			KW.scrollAndSetText(myJDD,"ART_MODFAM_OBS")
			*/
	

	
	
			
	TNRResult.addSTEPACTION('VALIDATION')
		
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData('ID_CODART'))
		
		myJDD.replaceSEQUENCIDInJDD('ID_NUMDOC1')
		JDD_Note.replaceSEQUENCIDInJDD('ID_NUMDOC')
	
		SQL.checkJDDWithBD(myJDD) // prendre en compte la valeur de ARTNUM car le where du select se fait sur ID_CODART
		SQL.checkJDDWithBD(JDD_Note)
		
		
	TNRResult.addEndTestCase()

} // fin du if



