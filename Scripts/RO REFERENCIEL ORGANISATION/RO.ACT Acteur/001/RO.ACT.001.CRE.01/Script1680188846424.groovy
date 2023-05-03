import my.KW
import my.NAV
import my.result.TNRResult
import my.JDD

'Lecture du JDD'
def myJDD = new JDD()
		
		
for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Creation_and_checkCartridge()
	


	TNRResult.addSTEPGRP("ONGLET ACTEUR")
		
		KW.scrollAndClick(myJDD,"tab_Acteur")
		KW.waitForElementVisible(myJDD,"tab_ActeurSelected")
		
		KW.scrollAndSelectOptionByValue(myJDD,"ST_ETA")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		KW.scrollAndSetText(myJDD,"ID_CODINT")
		KW.scrollAndSetText(myJDD,"ST_NOM")
		KW.scrollAndSetText(myJDD,"ST_PRE")
		KW.scrollAndSetText(myJDD,"ST_MAIL")
		KW.scrollAndSetText(myJDD,"ST_TELPHO")
		KW.scrollAndSetText(myJDD,"ST_TELMOB")
		KW.scrollAndSetText(myJDD,"ST_TELCOP")
		
		TNRResult.addSTEPBLOCK("SERVICE")
		KW.scrollAndSetText(myJDD,"ID_CODGES")
		//ST_DESGES --> pas d'action en création
		KW.scrollAndCheckIfNeeded(myJDD,"ST_GRP","O")
		
		
	TNRResult.addSTEPGRP("ONGLET AFFECTATION")
		
		KW.scrollAndClick(myJDD,"tab_Affectation")
		KW.waitForElementVisible(myJDD,"tab_AffectationSelected")
		
		KW.scrollAndSetText(myJDD,"ID_CODGESAFF")
		//ST_DESID_CODGESAFF --> pas d'action en création
		KW.scrollAndSetText(myJDD,"ST_MAT")
		KW.scrollAndSetText(myJDD,"ST_FAM")
		KW.scrollAndSetText(myJDD,"ST_GRO")
		KW.scrollAndSetText(myJDD,"ST_DES")
		
		TNRResult.addSTEPBLOCK("CATEGORIE")
		KW.scrollAndSetText(myJDD,"ID_CODCAT")
		//ST_DESID_CODCAT --> pas d'action en création
		KW.scrollAndSetText(myJDD,"NU_COUHOR")
		
		
	TNRResult.addSTEPGRP("ONGLET ROLE")
		
		KW.scrollAndClick(myJDD,"tab_Role")
		KW.waitForElementVisible(myJDD,"tab_RoleSelected")
		
		KW.scrollAndCheckIfNeeded(myJDD,"ST_GES","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_EXP","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_MAI","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_PRO","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_DEM","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_INT","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_ACH","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_REC","O")
		
		TNRResult.addSTEPBLOCK("ROLE DANS L'ORGANISATION")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_UTI","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_UTIMOB","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_INVPRE","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_INVBT","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_INVDA","O")
		
	TNRResult.addSTEPGRP("ONGLET PREVENTIF")
		
		KW.scrollAndClick(myJDD,"tab_Preventif")
		KW.waitForElementVisible(myJDD,"tab_PreventifSelected")
		
		KW.scrollAndCheckIfNeeded(myJDD,"ST_PRIPRE","O")
		KW.scrollAndSetText(myJDD,"NU_TAUPRE")
		KW.scrollAndSetText(myJDD,"ID_CODCAL")
		//ST_DESID_CODCAL --> pas d'action en création
		
	TNRResult.addSTEPGRP("ONGLET ZONE")
		
		KW.scrollAndClick(myJDD,"tab_Zone")
		KW.waitForElementVisible(myJDD,"tab_ZoneSelected")
		
		// Traitement spécifique pour ID_NUMZON
		//ST_DESID_NUMZON --> pas d'action en création
		
		Map specificValueMap = [:]
		if (myJDD.getData('ID_NUMZON')!=0) {
			KW.scrollAndSetText(myJDD,'ID_NUMZON')
		}else {
			specificValueMap.put('ID_NUMZON', 0)
		}
	
			
	TNRResult.addSTEPACTION('VALIDATION')
		
	    KW.scrollAndClick(myJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
		
		my.SQL.checkJDDWithBD(myJDD,specificValueMap)
		
		
	TNRResult.addEndTestCase()
} // fin du if



