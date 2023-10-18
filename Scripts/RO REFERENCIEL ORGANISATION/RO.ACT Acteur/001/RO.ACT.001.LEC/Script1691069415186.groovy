import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(myJDD.getStrData())
	STEP.checkReadUpdateDeleteScreen(myJDD.getStrData())
	

	TNRResult.addSTEPGRP("ONGLET ACTEUR")
	
		STEP.simpleClick(myJDD,"tab_Acteur")
		STEP.verifyElementVisible(myJDD,"tab_ActeurSelected")

		STEP.verifyOptionSelectedByLabel(myJDD,"ST_ETA")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_INA","O")
		STEP.verifyValue(myJDD,"ID_CODINT")
		STEP.verifyValue(myJDD,"ST_NOM")
		STEP.verifyValue(myJDD,"ST_PRE")
		STEP.verifyValue(myJDD,"ST_MAIL")
		STEP.verifyValue(myJDD,"ST_TELPHO")
		STEP.verifyValue(myJDD,"ST_TELMOB")
		STEP.verifyValue(myJDD,"ST_TELCOP")

		TNRResult.addSTEPBLOCK("SERVICE")
		STEP.verifyValue(myJDD,"ID_CODGES")
		STEP.verifyValue(myJDD,"ST_DESGES")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_GRP","O")


	TNRResult.addSTEPGRP("ONGLET AFFECTATION")

		STEP.simpleClick(myJDD,"tab_Affectation")
		STEP.verifyElementVisible(myJDD,"tab_AffectationSelected")

		STEP.verifyValue(myJDD,"ID_CODGESAFF")
		STEP.verifyValue(myJDD,"ST_DESID_CODGESAFF")
		STEP.verifyValue(myJDD,"ST_MAT")
		STEP.verifyValue(myJDD,"ST_FAM")
		STEP.verifyValue(myJDD,"ST_GRO")
		STEP.verifyValue(myJDD,"ST_DES")

		TNRResult.addSTEPBLOCK("CATEGORIE")
		STEP.verifyValue(myJDD,"ID_CODCAT")
		STEP.verifyValue(myJDD,"ST_DESID_CODCAT")
		STEP.verifyValue(myJDD,"NU_COUHOR")


	TNRResult.addSTEPGRP("ONGLET ROLE")

		STEP.simpleClick(myJDD,"tab_Role")
		STEP.verifyElementVisible(myJDD,"tab_RoleSelected")

		STEP.verifyBoxCheckedOrNot(myJDD,"ST_GES","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_EXP","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_MAI","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_PRO","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_DEM","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_INT","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_ACH","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_REC","O")

		TNRResult.addSTEPBLOCK("ROLE DANS L'ORGANISATION")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_UTI","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_UTIMOB","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_INVPRE","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_INVBT","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_INVDA","O")

	TNRResult.addSTEPGRP("ONGLET PREVENTIF")

		STEP.simpleClick(myJDD,"tab_Preventif")
		STEP.verifyElementVisible(myJDD,"tab_PreventifSelected")

		STEP.verifyBoxCheckedOrNot(myJDD,"ST_PRIPRE","O")
		STEP.verifyValue(myJDD,"NU_TAUPRE")
		STEP.verifyValue(myJDD,"ID_CODCAL")
		STEP.verifyValue(myJDD,"ST_DESID_CODCAL")

	TNRResult.addSTEPGRP("ONGLET ZONE")

		STEP.simpleClick(myJDD,"tab_Zone")
		STEP.verifyElementVisible(myJDD,"tab_ZoneSelected")
	

	//TNRResult.addEndTestCase()
}

