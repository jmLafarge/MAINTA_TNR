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
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODART'))

	
	TNRResult.addSTEPGRP("ONGLET ARTICLE")
			
			KW.scrollAndClick(myJDD,"tab_Article")
			KW.waitForElementVisible(myJDD,"tab_ArticleSelected")
			
			KW.verifyValue(myJDD,"ID_CODART")
			KW.verifyOptionSelectedByValue(myJDD,"ST_ETA")

			KW.verifyValue(myJDD,"ST_DES")
			KW.verifyOptionSelectedByValue(myJDD,"ST_TYPART")
			KW.verifyElementCheckedOrNot(myJDD,"ST_INA","O")
			
			KW.verifyValue(myJDD,"ID_CODNATART")
			KW.verifyValue(myJDD,"ST_DESID_CODNATART")
			KW.verifyValue(myJDD,"ID_CODGES")
			KW.verifyValue(myJDD,"ST_DESGES")
			
		TNRResult.addSTEPBLOCK("FOURNISSEUR NORMALISE")
		
			// Lire le JDD spécifique
			def JDD_ARTFOU = new my.JDD(JDDFiles.getFullName('RT.ART'),'001B',GlobalVariable.CASDETESTENCOURS)
			
				KW.verifyValue(JDD_ARTFOU,"ID_CODFOU")
			KW.verifyValue(myJDD,"ST_DESID_CODFOU")
				KW.verifyValue(JDD_ARTFOU,"ST_DES")
				KW.verifyValue(JDD_ARTFOU,"ST_REFFOU")
			
		TNRResult.addSTEPBLOCK("STOCK")
			
			KW.verifyElementCheckedOrNot(myJDD,"ST_MAT","O")
			KW.verifyValue(myJDD,"ID_CODUNI")
			KW.verifyValue(myJDD,"NU_PRIPMP")
			
		TNRResult.addSTEPBLOCK("ACHATS")
			
			KW.verifyElementCheckedOrNot(myJDD,"ST_CONOBL","O")
			KW.verifyValue(myJDD,"ST_TXTCDE")
			KW.verifyValue(myJDD,"ST_CODCOM")
			KW.verifyValue(myJDD,"ST_DESST_CODCOM")
			KW.verifyValue(myJDD,"ID_CODTVA")
			
			/*
			KW.verifyElementCheckedOrNot(myJDD,"MAJ_NOM","O")
			KW.verifyValue(myJDD,"NOM_CODLON")
			KW.verifyValue(myJDD,"ST_DESNOM")
			
			KW.verifyElementCheckedOrNot(myJDD,"MAJ_EQU","O")
			KW.verifyValue(myJDD,"EQU_CODLON")
			KW.verifyValue(myJDD,"ST_DESEQU")
			KW.verifyValue(myJDD,"ART_EQU_QTE")
			KW.verifyValue(myJDD,"ART_EQU_OBS")
			
			KW.verifyElementCheckedOrNot(myJDD,"MAJ_MODFAM","O")
			KW.verifyValue(myJDD,"ID_CODFAM")
			KW.verifyValue(myJDD,"ST_DESID_CODFAM")
			KW.verifyValue(myJDD,"MODFAM_CODLON")
			KW.verifyValue(myJDD,"ART_MODFAM_QTE")
			KW.verifyValue(myJDD,"ART_MODFAM_OBS")

			*/


	TNRResult.addEndTestCase()
}

