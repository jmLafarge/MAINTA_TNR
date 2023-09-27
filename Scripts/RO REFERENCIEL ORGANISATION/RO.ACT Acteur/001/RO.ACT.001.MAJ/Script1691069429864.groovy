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
	STEP.goToURLReadUpdateDelete(1,myJDD.getStrData())
	STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData())

		
	TNRResult.addSTEPGRP("ONGLET ACTEUR")
	
		STEP.simpleClick(3, myJDD,"tab_Acteur")
		STEP.verifyElementVisible(4, myJDD,"tab_ActeurSelected")

		STEP.scrollAndSelectOptionByLabel(5, myJDD,"ST_ETA")
		STEP.scrollAndCheckIfNeeded(6, myJDD,"ST_INA","O")
		//STEP.setText(0, myJDD, "ID_CODINT") 
		STEP.setText(8, myJDD, "ST_NOM")
		STEP.setText(9, myJDD, "ST_PRE")
		STEP.setText(10, myJDD, "ST_MAIL")
		STEP.setText(11, myJDD, "ST_TELPHO")
		STEP.setText(12, myJDD, "ST_TELMOB")
		STEP.setText(13, myJDD, "ST_TELCOP")

		TNRResult.addSTEPBLOCK("SERVICE")
		STEP.searchWithHelper(14, myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		STEP.scrollAndCheckIfNeeded(15, myJDD,"ST_GRP","O")

		STEP.scrollToPosition('',0, 0)

	TNRResult.addSTEPGRP("ONGLET AFFECTATION")

		STEP.simpleClick(20, myJDD,"tab_Affectation")
		STEP.verifyElementVisible(21, myJDD,"tab_AffectationSelected")
		
		STEP.scrollToPosition('', 0, 0)

		STEP.searchWithHelper(22, myJDD, "ID_CODGESAFF","","SEARCH_ID_CODGES") //specific
		//ST_DESID_CODGESAFF --> pas d'action en modification
		STEP.setText(23, myJDD, "ST_MAT")
		STEP.setText(24, myJDD, "ST_FAM")
		STEP.setText(25, myJDD, "ST_GRO")
		STEP.setText(26, myJDD, "ST_DES")

		TNRResult.addSTEPBLOCK("CATEGORIE")
		STEP.searchWithHelper(27, myJDD, "ID_CODCAT","","")
		//ST_DESID_CODCAT --> pas d'action en modification
		STEP.setText(28, myJDD, "NU_COUHOR")


	TNRResult.addSTEPGRP("ONGLET ROLE")

		STEP.simpleClick(30, myJDD,"tab_Role")
		STEP.verifyElementVisible(31, myJDD,"tab_RoleSelected")

		STEP.scrollAndCheckIfNeeded(32, myJDD,"ST_GES","O")
		STEP.scrollAndCheckIfNeeded(33, myJDD,"ST_EXP","O")
		STEP.scrollAndCheckIfNeeded(34, myJDD,"ST_MAI","O")
		STEP.scrollAndCheckIfNeeded(35, myJDD,"ST_PRO","O")
		STEP.scrollAndCheckIfNeeded(36, myJDD,"ST_DEM","O")
		STEP.scrollAndCheckIfNeeded(37, myJDD,"ST_INT","O")
		STEP.scrollAndCheckIfNeeded(38, myJDD,"ST_ACH","O")
		STEP.scrollAndCheckIfNeeded(39, myJDD,"ST_REC","O")

		TNRResult.addSTEPBLOCK("ROLE DANS L'ORGANISATION")
		STEP.scrollAndCheckIfNeeded(40, myJDD,"ST_UTI","O")
		STEP.scrollAndCheckIfNeeded(41, myJDD,"ST_UTIMOB","O")
		STEP.scrollAndCheckIfNeeded(42, myJDD,"ST_INVPRE","O")
		STEP.scrollAndCheckIfNeeded(43, myJDD,"ST_INVBT","O")
		STEP.scrollAndCheckIfNeeded(44, myJDD,"ST_INVDA","O")

	TNRResult.addSTEPGRP("ONGLET PREVENTIF")

		STEP.simpleClick(50, myJDD,"tab_Preventif")
		STEP.verifyElementVisible(51, myJDD,"tab_PreventifSelected")

		STEP.scrollAndCheckIfNeeded(52, myJDD,"ST_PRIPRE","O")
		STEP.setText(53, myJDD, "NU_TAUPRE")
		STEP.searchWithHelper(54, myJDD, "ID_CODCAL","","")
		//ST_DESID_CODCAL --> pas d'action en modification

	TNRResult.addSTEPGRP("ONGLET ZONE")

		STEP.simpleClick(60, myJDD,"tab_Zone")
		STEP.verifyElementVisible(61, myJDD,"tab_ZoneSelected")	



	TNRResult.addSTEPACTION('VALIDATION')

		STEP.simpleClick(90, myJDD,'button_Valider')
	
		STEP.checkResultScreen(91, myJDD.getStrData())
	
		STEP.checkJDDWithBD(92, myJDD)
		
	TNRResult.addEndTestCase()
} // fin du if


