import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper
import tnrResultManager.TNRResult
import tnrWebUI.*




'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData()); STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData())

	
	TNRResult.addSTEPGRP("ONGLET FOURNISSEUR")
	
		STEP.simpleClick(0, myJDD,"tab_Fournisseur")
		STEP.verifyElementVisible(0, myJDD,"tab_FournisseurSelected")
		
		STEP.verifyValue(0, myJDD,"ID_CODFOU")
		STEP.verifyValue(0, myJDD,"ST_NOM")
		STEP.verifyValue(0, myJDD,"ID_CODGES")
		STEP.verifyValue(0, myJDD,"ST_DESGES")
		STEP.verifyElementCheckedOrNot(0, myJDD,"ST_CONSTR","O")
		STEP.verifyElementCheckedOrNot(0, myJDD,"ST_INA","O")
		STEP.verifyValue(0, myJDD,"ST_CODCOM")
		STEP.verifyValue(0, myJDD,"ST_DESST_CODCOM")
		
		//TNRResult.addSTEPBLOCK("ADRESSE") pas de test case pour lecture adresse
		
		TNRResult.addSTEPBLOCK("CONTACT")
		STEP.verifyValue(0, myJDD,"ST_TELPHO")
		STEP.verifyValue(0, myJDD,"ST_CON")
		STEP.verifyValue(0, myJDD,"ST_TELMOB")
		STEP.verifyValue(0, myJDD,"ST_EMA")
		STEP.verifyValue(0, myJDD,"ST_TELCOP")
		STEP.verifyValue(0, myJDD,"ST_TELEX")
		
	TNRResult.addSTEPGRP("ONGLET COMMANDE")
		
		STEP.simpleClick(0, myJDD,"tab_Commande")
		STEP.verifyElementVisible(0, myJDD,"tab_CommandeSelected")
		
		STEP.verifyValue(0, myJDD,"ST_PRIDEL")
		STEP.verifyValue(0, myJDD,"ID_CODPAI")
		STEP.verifyValue(0, myJDD,"ST_DESID_CODPAI")
		STEP.verifyValue(0, myJDD,"ST_NOTPRO")
		STEP.verifyValue(0, myJDD,"ID_CODMOD")
		STEP.verifyValue(0, myJDD,"ST_DESID_CODMOD")
		STEP.verifyValue(0, myJDD,"ID_CODDEV")
		STEP.verifyValue(0, myJDD,"ST_DESID_CODDEV")
		STEP.verifyValue(0, myJDD,"ID_CODPOR")
		STEP.verifyValue(0, myJDD,"ST_DESID_CODPOR")
		STEP.verifyValue(0, myJDD,"ID_CODEMB")
		STEP.verifyValue(0, myJDD,"ST_DESID_CODEMB")
		STEP.verifyValue(0, myJDD,"ST_REL")
		STEP.verifyElementCheckedOrNot(0, myJDD,"ST_FIGCAT","O")
		
		TNRResult.addSTEPBLOCK("TEXTES COMMANDE")
		STEP.verifyValue(0, myJDD,"ST_TXTBAS1")
		STEP.verifyValue(0, myJDD,"ST_TXTBAS2")
		STEP.verifyValue(0, myJDD,"ST_TXTBAS3")
		STEP.verifyValue(0, myJDD,"ST_TXTBAS4")
		STEP.verifyValue(0, myJDD,"ST_TXTBAS5")
		STEP.verifyValue(0, myJDD,"ST_TXTBAS6")
		STEP.verifyElementCheckedOrNot(0, myJDD,"ST_FIGCDE","O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		STEP.simpleClick(0, myJDD,"tab_Notes")
		STEP.verifyElementVisible(0, myJDD,"tab_NotesSelected")
		
		STEP.scrollToPosition('', 0, 0)
		
		STEP.verifyText(0, new JDD(JDDFileMapper.getFullnameFromModObj('RO.FOU'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS),"OL_DOC")

	TNRResult.addEndTestCase()
}

