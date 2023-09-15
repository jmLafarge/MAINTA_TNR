import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import tnrWebUI.KW
import tnrResultManager.TNRResult
import tnrWebUI.NAV
import tnrJDDManager.JDD

'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())
	

	TNRResult.addSTEPGRP("ONGLET ACTEUR")
	
		KW.scrollAndClick(myJDD,"tab_Acteur")
		KW.waitForElementVisible(myJDD,"tab_ActeurSelected")

		KW.verifyOptionSelectedByLabel(myJDD,"ST_ETA")
		KW.verifyElementCheckedOrNot(myJDD,"ST_INA","O")
		KW.verifyValue(myJDD,"ID_CODINT")
		KW.verifyValue(myJDD,"ST_NOM")
		KW.verifyValue(myJDD,"ST_PRE")
		KW.verifyValue(myJDD,"ST_MAIL")
		KW.verifyValue(myJDD,"ST_TELPHO")
		KW.verifyValue(myJDD,"ST_TELMOB")
		KW.verifyValue(myJDD,"ST_TELCOP")

		TNRResult.addSTEPBLOCK("SERVICE")
		KW.verifyValue(myJDD,"ID_CODGES")
		KW.verifyValue(myJDD,"ST_DESGES")
		KW.verifyElementCheckedOrNot(myJDD,"ST_GRP","O")


	TNRResult.addSTEPGRP("ONGLET AFFECTATION")

		KW.scrollAndClick(myJDD,"tab_Affectation")
		KW.waitForElementVisible(myJDD,"tab_AffectationSelected")

		KW.verifyValue(myJDD,"ID_CODGESAFF")
		KW.verifyValue(myJDD,"ST_DESID_CODGESAFF")
		KW.verifyValue(myJDD,"ST_MAT")
		KW.verifyValue(myJDD,"ST_FAM")
		KW.verifyValue(myJDD,"ST_GRO")
		KW.verifyValue(myJDD,"ST_DES")

		TNRResult.addSTEPBLOCK("CATEGORIE")
		KW.verifyValue(myJDD,"ID_CODCAT")
		KW.verifyValue(myJDD,"ST_DESID_CODCAT")
		KW.verifyValue(myJDD,"NU_COUHOR")


	TNRResult.addSTEPGRP("ONGLET ROLE")

		KW.scrollAndClick(myJDD,"tab_Role")
		KW.waitForElementVisible(myJDD,"tab_RoleSelected")

		KW.verifyElementCheckedOrNot(myJDD,"ST_GES","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_EXP","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_MAI","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_PRO","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_DEM","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_INT","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_ACH","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_REC","O")

		TNRResult.addSTEPBLOCK("ROLE DANS L'ORGANISATION")
		KW.verifyElementCheckedOrNot(myJDD,"ST_UTI","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_UTIMOB","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_INVPRE","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_INVBT","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_INVDA","O")

	TNRResult.addSTEPGRP("ONGLET PREVENTIF")

		KW.scrollAndClick(myJDD,"tab_Preventif")
		KW.waitForElementVisible(myJDD,"tab_PreventifSelected")

		KW.verifyElementCheckedOrNot(myJDD,"ST_PRIPRE","O")
		KW.verifyValue(myJDD,"NU_TAUPRE")
		KW.verifyValue(myJDD,"ID_CODCAL")
		KW.verifyValue(myJDD,"ST_DESID_CODCAL")

	TNRResult.addSTEPGRP("ONGLET ZONE")

		KW.scrollAndClick(myJDD,"tab_Zone")
		KW.waitForElementVisible(myJDD,"tab_ZoneSelected")
	

	TNRResult.addEndTestCase()
}

