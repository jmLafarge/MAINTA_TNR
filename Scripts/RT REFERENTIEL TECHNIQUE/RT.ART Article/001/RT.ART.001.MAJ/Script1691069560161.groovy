import internal.GlobalVariable
import myJDDManager.JDD
import myJDDManager.JDDFiles
import my.KW
import my.NAV
import my.SQL
import myResult.TNRResult


'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData("ID_CODART"))


	TNRResult.addSTEPGRP("ONGLET ARTICLE")
			
			KW.scrollAndClick(myJDD,"tab_Article")
			KW.waitForElementVisible(myJDD,"tab_ArticleSelected")
			
			//KW.scrollAndSetText(myJDD, "ID_CODART")
			KW.scrollAndSelectOptionByValue(myJDD,"ST_ETA")
			//KW.scrollAndCheckIfNeeded(myJDD,"CODARTAUTO","O")
			KW.scrollAndSetText(myJDD, "ST_DES")
			KW.scrollAndSelectOptionByValue(myJDD,"ST_TYPART")
			KW.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
			
			KW.searchWithHelper(myJDD, "ID_CODNATART","","")
			//ST_DESID_CODNATART --> pas d'action en modification
			KW.searchWithHelper(myJDD, "ID_CODGES","","")
			//ST_DESGES --> pas d'action en modification
			
		TNRResult.addSTEPBLOCK("FOURNISSEUR NORMALISE")
		
		// Lire le JDD spécifique
		def JDD_ARTFOU = new my.JDD(JDDFiles.getFullnameFromModObj('RT.ART'),'001B',GlobalVariable.CASDETESTENCOURS)
			
			KW.searchWithHelper(JDD_ARTFOU, "ID_CODFOU","","")
			//ST_DESID_CODFOU --> pas d'action en modification
			KW.scrollAndSetText(JDD_ARTFOU, "ST_DES")
			KW.scrollAndSetText(JDD_ARTFOU, "ST_REFFOU")
			
		TNRResult.addSTEPBLOCK("STOCK")
			
			KW.scrollAndCheckIfNeeded(myJDD,"ST_MAT","O")
			KW.searchWithHelper(myJDD, "ID_CODUNI","","")
			KW.scrollAndSetText(myJDD, "NU_PRIPMP")
			
		TNRResult.addSTEPBLOCK("ACHATS")
			
			KW.scrollAndCheckIfNeeded(myJDD,"ST_CONOBL","O")
			KW.scrollAndSetText(myJDD, "ST_TXTCDE")
			KW.searchWithHelper(myJDD, "ST_CODCOM","","SEARCH_ID_CODCMP")
			//ST_DESST_CODCOM --> pas d'action en modification
			KW.searchWithHelper(myJDD, "ID_CODTVA","","")
			
			/*
			KW.scrollAndCheckIfNeeded(myJDD,"MAJ_NOM","O")
			KW.searchWithHelper(myJDD, "NOM_CODLON","","")
			//ST_DESNOM --> pas d'action en modification
			
			KW.scrollAndCheckIfNeeded(myJDD,"MAJ_EQU","O")
			KW.searchWithHelper(myJDD, "EQU_CODLON","","")
			//ST_DESEQU --> pas d'action en modification
			KW.scrollAndSetText(myJDD, "ART_EQU_QTE")
			KW.scrollAndSetText(myJDD, "ART_EQU_OBS")
			
			KW.scrollAndCheckIfNeeded(myJDD,"MAJ_MODFAM","O")
			KW.searchWithHelper(myJDD, "ID_CODFAM","","")
			//ST_DESID_CODFAM --> pas d'action en modification
			KW.scrollAndSetText(myJDD, "MODFAM_CODLON")
			KW.scrollAndSetText(myJDD, "ART_MODFAM_QTE")
			KW.scrollAndSetText(myJDD, "ART_MODFAM_OBS")
			*/
	  
	  
	  
	  
	  
	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData("ID_CODART"))
	
		SQL.checkJDDWithBD(myJDD)
		
	TNRResult.addEndTestCase()
	
} // fin du if


