import internal.GlobalVariable
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
    STEP.goToURLReadUpdateDelete(myJDD.getStrData()); STEP.checkReadUpdateDeleteScreen(myJDD.getStrData())

	
	TNRResult.addSTEPGRP("ONGLET FOURNISSEUR")
	
		STEP.simpleClick(myJDD,"tab_Fournisseur")
		STEP.verifyElementVisible(myJDD,"tab_FournisseurSelected")
		
		STEP.verifyValue(myJDD,"ID_CODFOU")
		STEP.verifyValue(myJDD,"ST_NOM")
		STEP.verifyValue(myJDD,"ID_CODGES")
		STEP.verifyValue(myJDD,"ST_DESGES")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_CONSTR","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_INA","O")
		STEP.verifyValue(myJDD,"ST_CODCOM")
		STEP.verifyValue(myJDD,"ST_DESST_CODCOM")
		
		//TNRResult.addSTEPBLOCK("ADRESSE") pas de test case pour lecture adresse
		
		TNRResult.addSTEPBLOCK("CONTACT")
		STEP.verifyValue(myJDD,"ST_TELPHO")
		STEP.verifyValue(myJDD,"ST_CON")
		STEP.verifyValue(myJDD,"ST_TELMOB")
		STEP.verifyValue(myJDD,"ST_EMA")
		STEP.verifyValue(myJDD,"ST_TELCOP")
		STEP.verifyValue(myJDD,"ST_TELEX")
		
	TNRResult.addSTEPGRP("ONGLET COMMANDE")
		
		STEP.simpleClick(myJDD,"tab_Commande")
		STEP.verifyElementVisible(myJDD,"tab_CommandeSelected")
		
		STEP.verifyValue(myJDD,"ST_PRIDEL")
		STEP.verifyValue(myJDD,"ID_CODPAI")
		STEP.verifyValue(myJDD,"ST_DESID_CODPAI")
		STEP.verifyValue(myJDD,"ST_NOTPRO")
		STEP.verifyValue(myJDD,"ID_CODMOD")
		STEP.verifyValue(myJDD,"ST_DESID_CODMOD")
		STEP.verifyValue(myJDD,"ID_CODDEV")
		STEP.verifyValue(myJDD,"ST_DESID_CODDEV")
		STEP.verifyValue(myJDD,"ID_CODPOR")
		STEP.verifyValue(myJDD,"ST_DESID_CODPOR")
		STEP.verifyValue(myJDD,"ID_CODEMB")
		STEP.verifyValue(myJDD,"ST_DESID_CODEMB")
		STEP.verifyValue(myJDD,"ST_REL")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_FIGCAT","O")
		
		TNRResult.addSTEPBLOCK("TEXTES COMMANDE")
		STEP.verifyValue(myJDD,"ST_TXTBAS1")
		STEP.verifyValue(myJDD,"ST_TXTBAS2")
		STEP.verifyValue(myJDD,"ST_TXTBAS3")
		STEP.verifyValue(myJDD,"ST_TXTBAS4")
		STEP.verifyValue(myJDD,"ST_TXTBAS5")
		STEP.verifyValue(myJDD,"ST_TXTBAS6")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_FIGCDE","O")
		
	TNRResult.addSTEPGRP("ONGLET NOTES")
		
		STEP.simpleClick(myJDD,"tab_Notes")
		STEP.verifyElementVisible(myJDD,"tab_NotesSelected")
		
		STEP.scrollToPosition( 0, 0)
		
		STEP.verifyText(new JDD(JDDFileMapper.getFullnameFromModObj('RO.FOU'),'001A',GlobalVariable.CAS_DE_TEST_EN_COURS),"OL_DOC")

	TNRResult.addEndTestCase()
}

