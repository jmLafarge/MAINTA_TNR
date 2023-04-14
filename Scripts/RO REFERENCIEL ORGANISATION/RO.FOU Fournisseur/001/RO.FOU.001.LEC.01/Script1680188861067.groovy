import internal.GlobalVariable
import my.KW
import my.Log as MYLOG
import my.NAV


'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())

	

	MYLOG.addSTEPGRP("ONGLET FOURNISSEUR")
		
		KW.scrollAndClick(myJDD,"tab_Fournisseur")
		KW.waitForElementVisible(myJDD,"tab_FournisseurSelected")
		
		KW.verifyValue(myJDD,"ID_CODFOU")
		KW.verifyValue(myJDD,"ST_NOM")
		KW.verifyValue(myJDD,"ID_CODGES")
		KW.verifyValue(myJDD,"ST_DESGES")
		KW.verifyElementCheckedOrNot(myJDD,"ST_CONSTR","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_INA","O")
		KW.verifyValue(myJDD,"ST_CODCOM")
		KW.verifyValue(myJDD,"ST_DESST_CODCOM")
		
		//MYLOG.addSTEPBLOCK("ADRESSE")
		
		MYLOG.addSTEPBLOCK("CONTACT")
		KW.verifyValue(myJDD,"ST_TELPHO")
		KW.verifyValue(myJDD,"ST_CON")
		KW.verifyValue(myJDD,"ST_TELMOB")
		KW.verifyValue(myJDD,"ST_EMA")
		KW.verifyValue(myJDD,"ST_TELCOP")
		KW.verifyValue(myJDD,"ST_TELEX")
		
	MYLOG.addSTEPGRP("ONGLET COMMANDE")
		
		KW.scrollAndClick(myJDD,"tab_Commande")
		KW.waitForElementVisible(myJDD,"tab_CommandeSelected")
		
		KW.verifyValue(myJDD,"ST_PRIDEL")
		KW.verifyValue(myJDD,"ID_CODPAI")
		KW.verifyValue(myJDD,"ST_DESID_CODPAI")
		KW.verifyValue(myJDD,"ST_NOTPRO")
		KW.verifyValue(myJDD,"ID_CODMOD")
		KW.verifyValue(myJDD,"ST_DESID_CODMOD")
		KW.verifyValue(myJDD,"ID_CODDEV")
		KW.verifyValue(myJDD,"ST_DESID_CODDEV")
		KW.verifyValue(myJDD,"ID_CODPOR")
		KW.verifyValue(myJDD,"ST_DESID_CODPOR")
		KW.verifyValue(myJDD,"ID_CODEMB")
		KW.verifyValue(myJDD,"ST_DESID_CODEMB")
		KW.verifyValue(myJDD,"ST_REL")
		KW.verifyElementCheckedOrNot(myJDD,"ST_FIGCAT","O")
		
		MYLOG.addSTEPBLOCK("TEXTES COMMANDE")
		KW.verifyValue(myJDD,"ST_TXTBAS1")
		KW.verifyValue(myJDD,"ST_TXTBAS2")
		KW.verifyValue(myJDD,"ST_TXTBAS3")
		KW.verifyValue(myJDD,"ST_TXTBAS4")
		KW.verifyValue(myJDD,"ST_TXTBAS5")
		KW.verifyValue(myJDD,"ST_TXTBAS6")
		KW.verifyElementCheckedOrNot(myJDD,"ST_FIGCDE","O")
		
	MYLOG.addSTEPGRP("ONGLET NOTES")
		
		KW.scrollAndClick(myJDD,"tab_Notes")
		KW.waitForElementVisible(myJDD,"tab_NotesSelected")
		
		KW.verifyElementText(new my.JDD(my.JDDFiles.getFullName('RO.FOU'),'001A',GlobalVariable.CASDETESTENCOURS),"OL_DOC")


}

