
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



// Lecture du JDD
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLCreate()
	STEP.checkCreateScreen()
	


	TNRResult.addSTEPGRP("ONGLET ACTEUR")
	
		STEP.simpleClick(myJDD,"tab_Acteur")
		STEP.verifyElementVisible(myJDD,"tab_ActeurSelected")
	
		STEP.selectOptionByLabel(myJDD,"ST_ETA")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_INA","O")
		STEP.setText(myJDD,"ID_CODINT")
		STEP.setText(myJDD,"ST_NOM")
		STEP.setText(myJDD,"ST_PRE")
		STEP.setText(myJDD,"ST_MAIL")
		STEP.setText(myJDD,"ST_TELPHO")
		STEP.setText(myJDD,"ST_TELMOB")
		STEP.setText(myJDD,"ST_TELCOP")
	
	TNRResult.addSTEPBLOCK("SERVICE")
		STEP.setText(myJDD,"ID_CODGES")
		//ST_DESGES --> pas d'action en création
		STEP.clickCheckboxIfNeeded(myJDD,"ST_GRP","O")
	
	
	TNRResult.addSTEPGRP("ONGLET AFFECTATION")
		
		STEP.simpleClick(myJDD,"tab_Affectation")
		STEP.verifyElementVisible(myJDD,"tab_AffectationSelected")
	
		STEP.setText(myJDD,"ID_CODGESAFF")
		//ST_DESID_CODGESAFF --> pas d'action en création
		STEP.setText(myJDD,"ST_MAT")
		STEP.setText(myJDD,"ST_FAM")
		STEP.setText(myJDD,"ST_GRO")
		STEP.setText(myJDD,"ST_DES")
	
	TNRResult.addSTEPBLOCK("CATEGORIE")
		STEP.setText(myJDD,"ID_CODCAT")
		//ST_DESID_CODCAT --> pas d'action en création
		STEP.setText(myJDD,"NU_COUHOR")
	
	
	TNRResult.addSTEPGRP("ONGLET ROLE")
	
		STEP.simpleClick(myJDD,"tab_Role")
		STEP.verifyElementVisible(myJDD,"tab_RoleSelected")
	
		STEP.clickCheckboxIfNeeded(myJDD,"ST_GES","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_EXP","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_MAI","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_PRO","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_DEM","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_INT","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_ACH","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_REC","O")
	
	TNRResult.addSTEPBLOCK("ROLE DANS L'ORGANISATION")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_UTI","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_UTIMOB","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_INVPRE","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_INVBT","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_INVDA","O")
	
	TNRResult.addSTEPGRP("ONGLET PREVENTIF")
	
		STEP.simpleClick(myJDD,"tab_Preventif")
		STEP.verifyElementVisible(myJDD,"tab_PreventifSelected")
	
		STEP.clickCheckboxIfNeeded(myJDD,"ST_PRIPRE","O")
		STEP.setText(myJDD,"NU_TAUPRE")
		STEP.setText(myJDD,"ID_CODCAL")
		//ST_DESID_CODCAL --> pas d'action en création
	
	TNRResult.addSTEPGRP("ONGLET ZONE")
	
		STEP.simpleClick(myJDD,"tab_Zone")
		STEP.verifyElementVisible(myJDD,"tab_ZoneSelected")
		
	
			
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.simpleClick(myJDD,'button_Valider')
	
	    STEP.checkResultScreen(myJDD.getStrData())
		
		STEP.checkJDDWithBD(myJDD)
		
		
	TNRResult.addEndTestCase()
} // fin du if



