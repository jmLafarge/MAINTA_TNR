import internal.GlobalVariable
import my.JDD
import my.JDDFiles
import my.KW
import my.NAV
import my.result.TNRResult

'Lecture du JDD'
def myJDD = new JDD()
		
		
for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Creation_and_checkCartridge()
	

	TNRResult.addSTEPGRP("ONGLET ARTICLE")
		
			KW.scrollAndClick(myJDD,"tab_Article")
			KW.waitForElementVisible(myJDD,"tab_ArticleSelected")
			
			KW.scrollAndSetText(myJDD,"ID_CODART")
			KW.scrollAndSelectOptionByValue(myJDD,"ST_ETA")
			KW.scrollAndCheckIfNeeded(myJDD,"CODARTAUTO","O")
			KW.scrollAndSetText(myJDD,"ST_DES")
			KW.scrollAndSelectOptionByValue(myJDD,"ST_TYPART")
			KW.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
			
			KW.scrollAndSetText(myJDD,"ID_CODNATART")
			//ST_DESID_CODNATART --> pas d'action en création
			KW.scrollAndSetText(myJDD,"ID_CODGES")
			//ST_DESGES --> pas d'action en création
			
		TNRResult.addSTEPBLOCK("FOURNISSEUR NORMALISE")
		
			// Lire le JDD spécifique
			def JDD_ARTFOU = new my.JDD(JDDFiles.getFullName('RT.ART'),'001B',GlobalVariable.CASDETESTENCOURS)
			
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////
	
	// gestion des Notes
	
	//soit
	KW.scrollAndSetText(myJDD,"Notes",myJDD.getStrData('OL_DOC'))
	
	//soit comme fournisseur
	
	
			
	TNRResult.addSTEPACTION('VALIDATION')
		
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData('ID_CODART')) // prendre en compte la valeur de ARTNUM
	
		my.SQL.checkJDDWithBD(myJDD) // prendre en compte la valeur de ARTNUM car le where du elect se fait sur ID_CODART
		
		
	TNRResult.addEndTestCase()

} // fin du if



