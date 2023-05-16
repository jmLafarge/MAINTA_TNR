import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.result.TNRResult
import my.NAV
import my.JDD

'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())
	

	TNRResult.addSTEPGRP("ONGLET ACTEUR")
	
	KW.scrollAndClick(myJDD,"tab_Acteur")
	KW.waitForElementVisible(myJDD,"tab_ActeurSelected")
	
	KW.verifyValue(myJDD,"ID_CODINT")
	KW.verifyOptionSelectedByValue(myJDD,"ST_ETA")
	KW.verifyElementCheckedOrNot(myJDD,"ST_INA","O")
	KW.verifyValue(myJDD,"ST_NOM")
	KW.verifyValue(myJDD,"ST_PRE")
	KW.verifyValue(myJDD,"ID_CODGES")
	KW.verifyValue(myJDD,"ST_DESGES")
	KW.verifyValue(myJDD,"ID_CODGESAFF")
	KW.verifyValue(myJDD,"ST_DESID_CODGESAFF")
	KW.verifyValue(myJDD,"ST_MAIL")
	KW.verifyValue(myJDD,"ST_TELPHO")
	KW.verifyValue(myJDD,"ST_TELMOB")
	KW.verifyValue(myJDD,"ST_TELCOP")
	KW.verifyValue(myJDD,"ST_MAT")
	
	TNRResult.addSTEPBLOCK("RÔLE DANS L'ORGANISATION")
	KW.verifyElementCheckedOrNot(myJDD,"ST_DEM","O")
	KW.verifyElementCheckedOrNot(myJDD,"ST_INT","O")
	KW.verifyElementCheckedOrNot(myJDD,"ST_ACH","O")
	KW.verifyElementCheckedOrNot(myJDD,"ST_REC","O")
	
	TNRResult.addSTEPBLOCK("RÔLE UTILISATEUR/INVITE")
	KW.verifyElementCheckedOrNot(myJDD,"ST_UTI","O")
	KW.verifyElementCheckedOrNot(myJDD,"ST_INVPRE","O")
	KW.verifyElementCheckedOrNot(myJDD,"ST_INVBT","O")
	KW.verifyElementCheckedOrNot(myJDD,"ST_INVDA","O")
	
	TNRResult.addSTEPGRP("ONGLET AFFECTATION")
	
	KW.scrollAndClick(myJDD,"tab_Affectation")
	KW.waitForElementVisible(myJDD,"tab_AffectationSelected")
	
	KW.verifyValue(myJDD,"ST_FAM")
	KW.verifyValue(myJDD,"ST_GRO")
	KW.verifyValue(myJDD,"ST_DES")
	KW.verifyElementCheckedOrNot(myJDD,"ST_GRP","O")
	KW.verifyValue(myJDD,"ID_CODCAT")
	KW.verifyValue(myJDD,"ST_DESID_CODCAT")
	KW.verifyValue(myJDD,"NU_COUHOR")
	KW.verifyValue(myJDD,"ID_CODCAL")
	KW.verifyValue(myJDD,"ST_DESID_CODCAL")
	
	TNRResult.addSTEPBLOCK("RÔLE DANS LA GESTION DES CODES MATRICULE")
	
	KW.verifyElementCheckedOrNot(myJDD,"ST_GES","O")
	KW.verifyElementCheckedOrNot(myJDD,"ST_EXP","O")
	KW.verifyElementCheckedOrNot(myJDD,"ST_MAI","O")
	KW.verifyElementCheckedOrNot(myJDD,"ST_PRIPRE","O")
	
	TNRResult.addSTEPGRP("ONGLET ZONE")
	
	KW.scrollAndClick(myJDD,"tab_Zone")
	KW.waitForElementVisible(myJDD,"tab_ZoneSelected")
	

		
		// Traitement spécifique pour ID_NUMZON

		if (myJDD.getData('ID_NUMZON')!=0) {
			KW.verifyValue(myJDD,'ID_NUMZON')
			KW.verifyValue(myJDD,"ST_DESID_NUMZON")
		}else {
			KW.verifyValue(myJDD,'ID_NUMZON', '')
		}

	TNRResult.addEndTestCase()
}

