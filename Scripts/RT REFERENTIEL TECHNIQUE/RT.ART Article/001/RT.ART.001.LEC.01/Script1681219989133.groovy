import my.KW
import my.NAV
import my.result.TNRResult
import my.JDD


'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODART'))

	
	TNRResult.addSTEPGRP("ONGLET ARTICLE")
		
		KW.verifyValue(myJDD,"ID_CODART")
		KW.verifyOptionSelectedByValue(myJDD,"ST_ETA")
		//KW.verifyElementCheckedOrNot(myJDD,"CODARTAUTO","O")
		KW.verifyValue(myJDD,"ST_DES")
		
		KW.verifyOptionSelectedByValue(myJDD,"ST_TYPART")
		KW.verifyElementCheckedOrNot(myJDD,"ST_INA","O")
		KW.verifyValue(myJDD,"ID_CODNATART")
		KW.verifyValue(myJDD,"ST_DESID_CODNATART")
		KW.verifyValue(myJDD,"ID_CODGES")
		KW.verifyValue(myJDD,"ST_DESGES")
		KW.verifyValue(myJDD,"Notes")
		
	TNRResult.addSTEPBLOCK("FOURNISSEUR NORMALISE")
		
		KW.verifyValue(myJDD,"ID_CODFOU")
		KW.verifyValue(myJDD,"ST_DESID_CODFOU")
		KW.verifyValue(myJDD,"ST_DESFOU")
		KW.verifyValue(myJDD,"ST_REFFOU")
		
	TNRResult.addSTEPBLOCK("STOCK")
		
		KW.verifyElementCheckedOrNot(myJDD,"ST_MAT","O")
		KW.verifyValue(myJDD,"ID_CODUNI")
		KW.verifyValue(myJDD,"NU_PRIPMP")
		
	TNRResult.addSTEPBLOCK("ACHAT")
		
		KW.verifyElementCheckedOrNot(myJDD,"ST_CONOBL","O")
		KW.verifyValue(myJDD,"ST_TXTCDE")
		KW.verifyValue(myJDD,"ST_CODCOM")
		KW.verifyValue(myJDD,"ST_DESST_CODCOM")
		KW.verifyValue(myJDD,"ID_CODTVA")
		
	TNRResult.addSTEPBLOCK("ASSOCIATION")
		
		KW.verifyElementCheckedOrNot(myJDD,"MAJ_NOM","O")
		KW.verifyElementCheckedOrNot(myJDD,"MAJ_EQU","O")
		KW.verifyElementCheckedOrNot(myJDD,"MAJ_MODFAM","O")
	


	TNRResult.addEndTestCase()
}

