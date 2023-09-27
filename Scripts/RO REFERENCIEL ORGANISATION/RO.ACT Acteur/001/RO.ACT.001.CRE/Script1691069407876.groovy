
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLCreate(1)
	STEP.checkCreateScreen(2)
	


	TNRResult.addSTEPGRP("ONGLET ACTEUR")
	
		STEP.simpleClick(1, myJDD,"tab_Acteur")
		STEP.verifyElementVisible(2, myJDD,"tab_ActeurSelected")
	
		STEP.scrollAndSelectOptionByLabel(3, myJDD,"ST_ETA")
		STEP.scrollAndCheckIfNeeded(4, myJDD,"ST_INA","O")
		STEP.setText(5, myJDD,"ID_CODINT")
		STEP.setText(6, myJDD,"ST_NOM")
		STEP.setText(7, myJDD,"ST_PRE")
		STEP.setText(8, myJDD,"ST_MAIL")
		STEP.setText(9, myJDD,"ST_TELPHO")
		STEP.setText(10, myJDD,"ST_TELMOB")
		STEP.setText(11, myJDD,"ST_TELCOP")
	
	TNRResult.addSTEPBLOCK("SERVICE")
		STEP.setText(12, myJDD,"ID_CODGES")
		//ST_DESGES --> pas d'action en création
		STEP.scrollAndCheckIfNeeded(13, myJDD,"ST_GRP","O")
	
	
	TNRResult.addSTEPGRP("ONGLET AFFECTATION")
		
		STEP.simpleClick(0, myJDD,"tab_Affectation")
		STEP.verifyElementVisible(0, myJDD,"tab_AffectationSelected")
	
		STEP.setText(14, myJDD,"ID_CODGESAFF")
		//ST_DESID_CODGESAFF --> pas d'action en création
		STEP.setText(15, myJDD,"ST_MAT")
		STEP.setText(16, myJDD,"ST_FAM")
		STEP.setText(17, myJDD,"ST_GRO")
		STEP.setText(18, myJDD,"ST_DES")
	
	TNRResult.addSTEPBLOCK("CATEGORIE")
		STEP.setText(19, myJDD,"ID_CODCAT")
		//ST_DESID_CODCAT --> pas d'action en création
		STEP.setText(20, myJDD,"NU_COUHOR")
	
	
	TNRResult.addSTEPGRP("ONGLET ROLE")
	
		STEP.simpleClick(21, myJDD,"tab_Role")
		STEP.verifyElementVisible(22, myJDD,"tab_RoleSelected")
	
		STEP.scrollAndCheckIfNeeded(23, myJDD,"ST_GES","O")
		STEP.scrollAndCheckIfNeeded(24, myJDD,"ST_EXP","O")
		STEP.scrollAndCheckIfNeeded(25, myJDD,"ST_MAI","O")
		STEP.scrollAndCheckIfNeeded(26, myJDD,"ST_PRO","O")
		STEP.scrollAndCheckIfNeeded(27, myJDD,"ST_DEM","O")
		STEP.scrollAndCheckIfNeeded(28, myJDD,"ST_INT","O")
		STEP.scrollAndCheckIfNeeded(29, myJDD,"ST_ACH","O")
		STEP.scrollAndCheckIfNeeded(30, myJDD,"ST_REC","O")
	
	TNRResult.addSTEPBLOCK("ROLE DANS L'ORGANISATION")
		STEP.scrollAndCheckIfNeeded(31, myJDD,"ST_UTI","O")
		STEP.scrollAndCheckIfNeeded(32, myJDD,"ST_UTIMOB","O")
		STEP.scrollAndCheckIfNeeded(33, myJDD,"ST_INVPRE","O")
		STEP.scrollAndCheckIfNeeded(34, myJDD,"ST_INVBT","O")
		STEP.scrollAndCheckIfNeeded(35, myJDD,"ST_INVDA","O")
	
	TNRResult.addSTEPGRP("ONGLET PREVENTIF")
	
		STEP.simpleClick(36, myJDD,"tab_Preventif")
		STEP.verifyElementVisible(37, myJDD,"tab_PreventifSelected")
	
		STEP.scrollAndCheckIfNeeded(38, myJDD,"ST_PRIPRE","O")
		STEP.setText(39, myJDD,"NU_TAUPRE")
		STEP.setText(40, myJDD,"ID_CODCAL")
		//ST_DESID_CODCAL --> pas d'action en création
	
	TNRResult.addSTEPGRP("ONGLET ZONE")
	
		STEP.simpleClick(41, myJDD,"tab_Zone")
		STEP.verifyElementVisible(42, myJDD,"tab_ZoneSelected")
		
	
			
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.simpleClick(90, myJDD,'button_Valider')
	
	    STEP.checkResultScreen(91, myJDD.getStrData())
		
		STEP.checkJDDWithBD(92, myJDD)
		
		
	TNRResult.addEndTestCase()
} // fin du if



