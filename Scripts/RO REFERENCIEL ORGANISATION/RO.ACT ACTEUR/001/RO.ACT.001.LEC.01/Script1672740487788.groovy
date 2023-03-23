import my.KW
import my.NAV as NAV
import my.Log as MYLOG

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))

	
	MYLOG.addSTEPGRP('ONGLET ACTEUR')

		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD,'a_Acteur')
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD,'a_ActeurSelected')
	
		'Début de lecture des valeurs du JDD'
	
		KW.verifyOptionSelectedByValue(myJDD,'ST_ETA')
		
		KW.verifyElementCheckedOrNot(myJDD,'ST_INA','O')
		
	    KW.verifyValue(myJDD,'ID_CODINT')
	
	    KW.verifyValue(myJDD,'ST_NOM')
	
	    KW.verifyValue(myJDD,'ST_PRE')
	
	    KW.verifyValue(myJDD,'ST_MAIL')
	
	    KW.verifyValue(myJDD,'ST_TELPHO')
	
	    KW.verifyValue(myJDD,'ST_TELMOB')
	
	    KW.verifyValue(myJDD,'ST_TELCOP')
	
		KW.verifyValue(myJDD,'ID_CODGES')
		
		KW.verifyElementCheckedOrNot(myJDD,'ST_GRP','O')
		
		KW.verifyValue(myJDD,'ST_DESGES')
	
	MYLOG.addSTEPGRP('ONGLET AFFECTATION')
		
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD,'a_Affectation')
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD,'a_AffectationSelected')
		
		'Début de lecture des valeurs du JDD'
		KW.verifyValue(myJDD,'ID_CODGESAFF')
		
		KW.verifyValue(myJDD,'ST_DESID_CODGESAFF')
	
		KW.verifyValue(myJDD,'ST_MAT')
	
		KW.verifyValue(myJDD,'ST_FAM')
		
		KW.verifyValue(myJDD,'ST_GRO')
		
		KW.verifyValue(myJDD,'ST_DES')
		
		KW.verifyValue(myJDD,'ID_CODCAT')
		
		KW.verifyValue(myJDD,'ST_DESID_CODCAT')
		
		KW.verifyValue(myJDD,'NU_COUHOR')
		
	
	MYLOG.addSTEPGRP('ONGLET ROLE')
	
			
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD,'a_Role')
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD,'a_RoleSelected')
		
		'Début de lecture des valeurs du JDD'

		KW.verifyElementCheckedOrNot(myJDD,'ST_GES','O')
		KW.verifyElementCheckedOrNot(myJDD,'ST_EXP','O')
		KW.verifyElementCheckedOrNot(myJDD,'ST_MAI','O')
		KW.verifyElementCheckedOrNot(myJDD,'ST_PRO','O')
		KW.verifyElementCheckedOrNot(myJDD,'ST_DEM','O')
		KW.verifyElementCheckedOrNot(myJDD,'ST_INT','O')
		KW.verifyElementCheckedOrNot(myJDD,'ST_ACH','O')
		KW.verifyElementCheckedOrNot(myJDD,'ST_REC','O')
		KW.verifyElementCheckedOrNot(myJDD,'ST_UTI','O')
		KW.verifyElementCheckedOrNot(myJDD,'ST_UTIMOB','O')
		KW.verifyElementCheckedOrNot(myJDD,'ST_INVPRE','O')
		KW.verifyElementCheckedOrNot(myJDD,'ST_INVBT','O')
		KW.verifyElementCheckedOrNot(myJDD,'ST_INVDA','O')
		
	MYLOG.addSTEPGRP('ONGLET PREVENTIF')
		
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD,'a_Preventif')
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD,'a_PreventifSelected')
		
		'Début de lecture des valeurs du JDD'
		KW.verifyElementCheckedOrNot(myJDD,'ST_PRIPRE','O')
		
		KW.verifyValue(myJDD,'NU_TAUPRE')
		
		KW.verifyValue(myJDD,'ID_CODCAL')
		
		KW.verifyValue(myJDD,'ST_DESID_CODCAL')


	MYLOG.addSTEPGRP('ONGLET ZONE')
	
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD,'a_Zone')
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD,'a_ZoneSelected')
		
		'Début de lecture des valeurs du JDD'
		if (myJDD.getData('ID_NUMZON')!=0) {
			KW.verifyValue(myJDD,'ID_NUMZON')
		}else {
			KW.verifyValue(myJDD,'ID_NUMZON', '')
		}


}

