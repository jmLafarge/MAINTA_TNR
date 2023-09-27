import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData())
	STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData())
	

	TNRResult.addSTEPGRP("ONGLET ACTEUR")
	
		STEP.simpleClick(3, myJDD,"tab_Acteur")
		STEP.verifyElementVisible(4, myJDD,"tab_ActeurSelected")

		STEP.verifyOptionSelectedByLabel(5, myJDD,"ST_ETA")
		STEP.verifyElementCheckedOrNot(0, myJDD,"ST_INA","O")
		STEP.verifyValue(7, myJDD,"ID_CODINT")
		STEP.verifyValue(8, myJDD,"ST_NOM")
		STEP.verifyValue(9, myJDD,"ST_PRE")
		STEP.verifyValue(10, myJDD,"ST_MAIL")
		STEP.verifyValue(11, myJDD,"ST_TELPHO")
		STEP.verifyValue(12, myJDD,"ST_TELMOB")
		STEP.verifyValue(13, myJDD,"ST_TELCOP")

		TNRResult.addSTEPBLOCK("SERVICE")
		STEP.verifyValue(14, myJDD,"ID_CODGES")
		STEP.verifyValue(15, myJDD,"ST_DESGES")
		STEP.verifyElementCheckedOrNot(0, myJDD,"ST_GRP","O")


	TNRResult.addSTEPGRP("ONGLET AFFECTATION")

		STEP.simpleClick(17, myJDD,"tab_Affectation")
		STEP.verifyElementVisible(18, myJDD,"tab_AffectationSelected")

		STEP.verifyValue(20, myJDD,"ID_CODGESAFF")
		STEP.verifyValue(21, myJDD,"ST_DESID_CODGESAFF")
		STEP.verifyValue(22, myJDD,"ST_MAT")
		STEP.verifyValue(23, myJDD,"ST_FAM")
		STEP.verifyValue(24, myJDD,"ST_GRO")
		STEP.verifyValue(25, myJDD,"ST_DES")

		TNRResult.addSTEPBLOCK("CATEGORIE")
		STEP.verifyValue(26, myJDD,"ID_CODCAT")
		STEP.verifyValue(27, myJDD,"ST_DESID_CODCAT")
		STEP.verifyValue(28, myJDD,"NU_COUHOR")


	TNRResult.addSTEPGRP("ONGLET ROLE")

		STEP.simpleClick(29, myJDD,"tab_Role")
		STEP.verifyElementVisible(30, myJDD,"tab_RoleSelected")

		STEP.verifyElementCheckedOrNot(31, myJDD,"ST_GES","O")
		STEP.verifyElementCheckedOrNot(32, myJDD,"ST_EXP","O")
		STEP.verifyElementCheckedOrNot(33, myJDD,"ST_MAI","O")
		STEP.verifyElementCheckedOrNot(34, myJDD,"ST_PRO","O")
		STEP.verifyElementCheckedOrNot(35, myJDD,"ST_DEM","O")
		STEP.verifyElementCheckedOrNot(36, myJDD,"ST_INT","O")
		STEP.verifyElementCheckedOrNot(37, myJDD,"ST_ACH","O")
		STEP.verifyElementCheckedOrNot(38, myJDD,"ST_REC","O")

		TNRResult.addSTEPBLOCK("ROLE DANS L'ORGANISATION")
		STEP.verifyElementCheckedOrNot(40, myJDD,"ST_UTI","O")
		STEP.verifyElementCheckedOrNot(41, myJDD,"ST_UTIMOB","O")
		STEP.verifyElementCheckedOrNot(42, myJDD,"ST_INVPRE","O")
		STEP.verifyElementCheckedOrNot(43, myJDD,"ST_INVBT","O")
		STEP.verifyElementCheckedOrNot(44, myJDD,"ST_INVDA","O")

	TNRResult.addSTEPGRP("ONGLET PREVENTIF")

		STEP.simpleClick(50, myJDD,"tab_Preventif")
		STEP.verifyElementVisible(51, myJDD,"tab_PreventifSelected")

		STEP.verifyElementCheckedOrNot(52, myJDD,"ST_PRIPRE","O")
		STEP.verifyValue(53, myJDD,"NU_TAUPRE")
		STEP.verifyValue(54, myJDD,"ID_CODCAL")
		STEP.verifyValue(55, myJDD,"ST_DESID_CODCAL")

	TNRResult.addSTEPGRP("ONGLET ZONE")

		STEP.simpleClick(60, myJDD,"tab_Zone")
		STEP.verifyElementVisible(61, myJDD,"tab_ZoneSelected")
	

	TNRResult.addEndTestCase()
}

