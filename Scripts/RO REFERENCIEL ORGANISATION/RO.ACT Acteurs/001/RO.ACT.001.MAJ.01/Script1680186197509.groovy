import my.KW
import my.NAV
import my.Log as MYLOG



'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0 ) {

	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())


	MYLOG.addSTEPGRP('ONGLET ACTEUR')

		KW.scrollAndClick(myJDD,'a_Acteur')
		KW.waitForElementVisible(myJDD,'a_ActeurSelected')
	
		KW.scrollAndSelectOptionByValue(myJDD,'ST_ETA')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_INA','O')
		KW.scrollAndSetText(myJDD,'ST_NOM')
		KW.scrollAndSetText(myJDD,'ST_PRE')
		KW.scrollAndSetText(myJDD,'ST_MAIL')
		KW.scrollAndSetText(myJDD,'ST_TELPHO')
		KW.scrollAndSetText(myJDD,'ST_TELMOB')
		KW.scrollAndSetText(myJDD,'ST_TELCOP')
		KW.searchWithHelper(myJDD, 'ID_CODGES')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_GRP','O')

	MYLOG.addSTEPGRP('ONGLET AFFECTATION')

		KW.scrollAndClick(myJDD,'a_Affectation')
		KW.waitForElementVisible(myJDD,'a_AffectationSelected')
	
		KW.searchWithHelper(myJDD, 'ID_CODGESAFF','','SEARCH_ID_CODGES')
		KW.scrollAndSetText(myJDD,'ST_MAT')
		KW.scrollAndSetText(myJDD,'ST_FAM')
		KW.scrollAndSetText(myJDD,'ST_GRO')
		KW.scrollAndSetText(myJDD,'ST_DES')
		KW.scrollAndSetText(myJDD,'ID_CODCAT')
		KW.searchWithHelper(myJDD, 'ID_CODCAT')
		KW.scrollAndSetText(myJDD,'NU_COUHOR')

	MYLOG.addSTEPGRP('ONGLET ROLE')

		KW.scrollAndClick(myJDD,'a_Role')
		KW.waitForElementVisible(myJDD,'a_RoleSelected')
	
		KW.scrollAndCheckIfNeeded(myJDD,'ST_GES','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_EXP','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_MAI','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_PRO','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_DEM','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_INT','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_ACH','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_REC','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_UTI','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_UTIMOB','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_INVPRE','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_INVBT','O')
		KW.scrollAndCheckIfNeeded(myJDD,'ST_INVDA','O')

	MYLOG.addSTEPGRP('ONGLET PRENVENTIF')
	
		KW.scrollAndClick(myJDD,'a_Preventif')
		KW.waitForElementVisible(myJDD,'a_PreventifSelected')
	
	
		KW.scrollAndCheckIfNeeded(myJDD,'ST_PRIPRE','O')
		KW.scrollAndSetText(myJDD,'NU_TAUPRE')
		KW.scrollAndSetText(myJDD,'ID_CODCAL')

	MYLOG.addSTEPGRP('ONGLET ZONE')

		KW.scrollAndClick(myJDD,'a_Zone')
		KW.waitForElementVisible(myJDD,'a_ZoneSelected')
	
		Map specificValueMap = [:]
		if (myJDD.getData('ID_NUMZON')!=0) {
			KW.scrollAndSetText(myJDD,'ID_NUMZON')
		}else {
			specificValueMap.put('ID_NUMZON', 0)
		}


	MYLOG.addSTEPGRP('VALIDATION')

		KW.scrollAndClick(myJDD,'button_Valider')
	
		NAV.verifierEcranResultat()
	
		KW.verifyElementText(NAV.myGlobalJDD,'a_Resultat_ID', myJDD.getStrData())
	
		my.SQL.checkJDDWithBD(myJDD,specificValueMap)
	
} // fin du if


