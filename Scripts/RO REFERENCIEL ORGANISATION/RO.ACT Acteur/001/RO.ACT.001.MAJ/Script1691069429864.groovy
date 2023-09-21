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
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())

		
	TNRResult.addSTEPGRP("ONGLET ACTEUR")
	
		KW.click(myJDD,"tab_Acteur")
		KW.isElementVisible(myJDD,"tab_ActeurSelected")

		KW.scrollAndSelectOptionByLabel(myJDD,"ST_ETA")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		//KW.setText(myJDD, "ID_CODINT") 
		KW.setText(myJDD, "ST_NOM")
		KW.setText(myJDD, "ST_PRE")
		KW.setText(myJDD, "ST_MAIL")
		KW.setText(myJDD, "ST_TELPHO")
		KW.setText(myJDD, "ST_TELMOB")
		KW.setText(myJDD, "ST_TELCOP")

		TNRResult.addSTEPBLOCK("SERVICE")
		KWSearchHelper.launch(myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_GRP","O")

		KW.scrollToPositionAndWait(0, 0,1)

	TNRResult.addSTEPGRP("ONGLET AFFECTATION")

		KW.click(myJDD,"tab_Affectation")
		KW.isElementVisible(myJDD,"tab_AffectationSelected")
		
		KW.scrollToPositionAndWait(0, 0,1)

		KWSearchHelper.launch(myJDD, "ID_CODGESAFF","","SEARCH_ID_CODGES") //specific
		//ST_DESID_CODGESAFF --> pas d'action en modification
		KW.setText(myJDD, "ST_MAT")
		KW.setText(myJDD, "ST_FAM")
		KW.setText(myJDD, "ST_GRO")
		KW.setText(myJDD, "ST_DES")

		TNRResult.addSTEPBLOCK("CATEGORIE")
		KWSearchHelper.launch(myJDD, "ID_CODCAT","","")
		//ST_DESID_CODCAT --> pas d'action en modification
		KW.setText(myJDD, "NU_COUHOR")


	TNRResult.addSTEPGRP("ONGLET ROLE")

		KW.click(myJDD,"tab_Role")
		KW.isElementVisible(myJDD,"tab_RoleSelected")

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

		KW.click(myJDD,"tab_Preventif")
		KW.isElementVisible(myJDD,"tab_PreventifSelected")

		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_PRIPRE","O")
		KW.setText(myJDD, "NU_TAUPRE")
		KWSearchHelper.launch(myJDD, "ID_CODCAL","","")
		//ST_DESID_CODCAL --> pas d'action en modification

	TNRResult.addSTEPGRP("ONGLET ZONE")

		KW.click(myJDD,"tab_Zone")
		KW.isElementVisible(myJDD,"tab_ZoneSelected")	



	TNRResult.addSTEPACTION('VALIDATION')

		KW.click(myJDD,'button_Valider')
	
		NAV.verifierEcranResultat(myJDD.getStrData())
	
		SQL.checkJDDWithBD(myJDD)
		
	TNRResult.addEndTestCase()
} // fin du if


