import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())
	

	TNRResult.addSTEPGRP("ONGLET ACTEUR")
	
		STEP.click(0, myJDD,"tab_Acteur")
		STEP.verifyElementVisible(0, myJDD,"tab_ActeurSelected")

		KW.verifyOptionSelectedByLabel(myJDD,"ST_ETA")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_INA","O")
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
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_GRP","O")


	TNRResult.addSTEPGRP("ONGLET AFFECTATION")

		STEP.click(0, myJDD,"tab_Affectation")
		STEP.verifyElementVisible(0, myJDD,"tab_AffectationSelected")

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

		STEP.click(0, myJDD,"tab_Role")
		STEP.verifyElementVisible(0, myJDD,"tab_RoleSelected")

		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_GES","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_EXP","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_MAI","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_PRO","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_DEM","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_INT","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_ACH","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_REC","O")

		TNRResult.addSTEPBLOCK("ROLE DANS L'ORGANISATION")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_UTI","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_UTIMOB","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_INVPRE","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_INVBT","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_INVDA","O")

	TNRResult.addSTEPGRP("ONGLET PREVENTIF")

		STEP.click(0, myJDD,"tab_Preventif")
		STEP.verifyElementVisible(0, myJDD,"tab_PreventifSelected")

		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_PRIPRE","O")
		KW.verifyValue(myJDD,"NU_TAUPRE")
		KW.verifyValue(myJDD,"ID_CODCAL")
		KW.verifyValue(myJDD,"ST_DESID_CODCAL")

	TNRResult.addSTEPGRP("ONGLET ZONE")

		STEP.click(0, myJDD,"tab_Zone")
		STEP.verifyElementVisible(0, myJDD,"tab_ZoneSelected")
	

	TNRResult.addEndTestCase()
}

