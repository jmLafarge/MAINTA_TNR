
import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP_NAV.goToURL_Creation_and_checkCartridge(1)
	


	TNRResult.addSTEPGRP("ONGLET ACTEUR")
	
		STEP.click(0, myJDD,"tab_Acteur")
		STEP.verifyElementVisible(0, myJDD,"tab_ActeurSelected")
	
		KW.scrollAndSelectOptionByLabel(myJDD,"ST_ETA")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		STEP.setText(0, myJDD,"ID_CODINT")
		STEP.setText(0, myJDD,"ST_NOM")
		STEP.setText(0, myJDD,"ST_PRE")
		STEP.setText(0, myJDD,"ST_MAIL")
		STEP.setText(0, myJDD,"ST_TELPHO")
		STEP.setText(0, myJDD,"ST_TELMOB")
		STEP.setText(0, myJDD,"ST_TELCOP")
	
		TNRResult.addSTEPBLOCK("SERVICE")
		STEP.setText(0, myJDD,"ID_CODGES")
		//ST_DESGES --> pas d'action en création
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_GRP","O")
	
	
	TNRResult.addSTEPGRP("ONGLET AFFECTATION")
		
		STEP.click(0, myJDD,"tab_Affectation")
		STEP.verifyElementVisible(0, myJDD,"tab_AffectationSelected")
	
		STEP.setText(0, myJDD,"ID_CODGESAFF")
		//ST_DESID_CODGESAFF --> pas d'action en création
		STEP.setText(0, myJDD,"ST_MAT")
		STEP.setText(0, myJDD,"ST_FAM")
		STEP.setText(0, myJDD,"ST_GRO")
		STEP.setText(0, myJDD,"ST_DES")
	
		TNRResult.addSTEPBLOCK("CATEGORIE")
		STEP.setText(0, myJDD,"ID_CODCAT")
		//ST_DESID_CODCAT --> pas d'action en création
		STEP.setText(0, myJDD,"NU_COUHOR")
	
	
	TNRResult.addSTEPGRP("ONGLET ROLE")
	
		STEP.click(0, myJDD,"tab_Role")
		STEP.verifyElementVisible(0, myJDD,"tab_RoleSelected")
	
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_GES","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_EXP","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_MAI","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_PRO","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_DEM","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INT","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_ACH","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_REC","O")
	
		TNRResult.addSTEPBLOCK("ROLE DANS L'ORGANISATION")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_UTI","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_UTIMOB","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INVPRE","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INVBT","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INVDA","O")
	
	TNRResult.addSTEPGRP("ONGLET PREVENTIF")
	
		STEP.click(0, myJDD,"tab_Preventif")
		STEP.verifyElementVisible(0, myJDD,"tab_PreventifSelected")
	
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_PRIPRE","O")
		STEP.setText(0, myJDD,"NU_TAUPRE")
		STEP.setText(0, myJDD,"ID_CODCAL")
		//ST_DESID_CODCAL --> pas d'action en création
	
	TNRResult.addSTEPGRP("ONGLET ZONE")
	
		STEP.click(0, myJDD,"tab_Zone")
		STEP.verifyElementVisible(0, myJDD,"tab_ZoneSelected")
		
	
			
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.click(0, myJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
		
		SQL.checkJDDWithBD(myJDD)
		
		
	TNRResult.addEndTestCase()
} // fin du if



