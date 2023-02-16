import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.util.KeywordUtil as KU
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.NAV



'Lecture du JDD'
def myJDD = new my.JDD()



'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Creation_and_checkCartridge()
	
	//////////////////////////////////////////////////////////////////////////////////// ONGLET ACTEUR

		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD.makeTO('a_Acteur'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_ActeurSelected'),GlobalVariable.TIMEOUT)
	
		'Début de sasie des valeurs du JDD'
	
		KW.scrollAndSelectOptionByValue(myJDD.makeTO('ST_ETA'), myJDD.getStrData('ST_ETA'))
		
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_INA'),myJDD.getStrData('ST_INA')=='O')
		
	    KW.scrollAndSetText(myJDD.makeTO('ID_CODINT'), myJDD.getStrData('ID_CODINT'))
	
	    KW.scrollAndSetText(myJDD.makeTO('ST_NOM'), myJDD.getStrData('ST_NOM'))
	
	    KW.scrollAndSetText(myJDD.makeTO('ST_PRE'), myJDD.getStrData('ST_PRE'))
	
	    KW.scrollAndSetText(myJDD.makeTO('ST_MAIL'), myJDD.getStrData('ST_MAIL'))
	
	    KW.scrollAndSetText(myJDD.makeTO('ST_TELPHO'), myJDD.getStrData('ST_TELPHO'))
	
	    KW.scrollAndSetText(myJDD.makeTO('ST_TELMOB'), myJDD.getStrData('ST_TELMOB'))
	
	    KW.scrollAndSetText(myJDD.makeTO('ST_TELCOP'), myJDD.getStrData('ST_TELCOP'))
	
		KW.scrollAndSetText(myJDD.makeTO('ID_CODGES'), myJDD.getStrData('ID_CODGES'))
		
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_GRP'),myJDD.getStrData('ST_GRP')=='O')
		
		// l'attribut value ou title  de ST_DESGES n'est renseigné qu'à la lecture ! le test IMPOSSIBLE de tester en création ou en modif

	
	//////////////////////////////////////////////////////////////////////////////////// ONGLET AFFECTATION
			
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD.makeTO('a_Affectation'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_AffectationSelected'),GlobalVariable.TIMEOUT)
		
		'Début de sasie des valeurs du JDD'
		KW.scrollAndSetText(myJDD.makeTO('ID_CODGESAFF'), myJDD.getStrData('ID_CODGESAFF'))
	
		KW.scrollAndSetText(myJDD.makeTO('ST_MAT'), myJDD.getStrData('ST_MAT'))
	
		KW.scrollAndSetText(myJDD.makeTO('ST_FAM'), myJDD.getStrData('ST_FAM'))
		
		KW.scrollAndSetText(myJDD.makeTO('ST_GRO'), myJDD.getStrData('ST_GRO'))
		
		KW.scrollAndSetText(myJDD.makeTO('ST_DES'), myJDD.getStrData('ST_DES'))
		
		KW.scrollAndSetText(myJDD.makeTO('ID_CODCAT'), myJDD.getStrData('ID_CODCAT'))
		
		KW.scrollAndSetText(myJDD.makeTO('NU_COUHOR'), myJDD.getStrData('NU_COUHOR'))
		
	
	//////////////////////////////////////////////////////////////////////////////////// ONGLET ROLE
	
			
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD.makeTO('a_Role'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_RoleSelected'),GlobalVariable.TIMEOUT)
		
		'Début de sasie des valeurs du JDD'
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_GES'),myJDD.getStrData('ST_GES')=='O')
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_EXP'),myJDD.getStrData('ST_EXP')=='O')
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_MAI'),myJDD.getStrData('ST_MAI')=='O')
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_PRO'),myJDD.getStrData('ST_PRO')=='O')
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_DEM'),myJDD.getStrData('ST_DEM')=='O')
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_INT'),myJDD.getStrData('ST_INT')=='O')
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_ACH'),myJDD.getStrData('ST_ACH')=='O')
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_REC'),myJDD.getStrData('ST_REC')=='O')
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_UTI'),myJDD.getStrData('ST_UTI')=='O')
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_UTIMOB'),myJDD.getStrData('ST_UTIMOB')=='O')
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_INVPRE'),myJDD.getStrData('ST_INVPRE')=='O')
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_INVBT'),myJDD.getStrData('ST_INVBT')=='O')
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_INVDA'),myJDD.getStrData('ST_INVDA')=='O')


	
	//////////////////////////////////////////////////////////////////////////////////// ONGLET PREVENTIF
		
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD.makeTO('a_Preventif'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_PreventifSelected'),GlobalVariable.TIMEOUT)
		
		'Début de sasie des valeurs du JDD'
		KW.scrollAndClickIfNeeded(myJDD.makeTO('ST_PRIPRE'),myJDD.getStrData('ST_PRIPRE')=='O')
		
		KW.scrollAndSetText(myJDD.makeTO('NU_TAUPRE'), myJDD.getStrData('NU_TAUPRE'))
		
		KW.scrollAndSetText(myJDD.makeTO('ID_CODCAL'), myJDD.getStrData('ID_CODCAL'))
		
		
	//////////////////////////////////////////////////////////////////////////////////// ONGLET ZONE
	
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD.makeTO('a_Zone'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_ZoneSelected'),GlobalVariable.TIMEOUT)
		
		'Début de sasie des valeurs du JDD'
		KW.scrollAndSetText(myJDD.makeTO('ID_NUMZON'), myJDD.getStrData('ID_NUMZON'))
		
		
		
		
    'Validation de la saisie'
    KW.scrollAndClick(myJDD.makeTO('button_Valider'))

    'Vérification du test case - écran résulat'
    NAV.verifierEcranResultat()
		
    KW.verifyElementText(myJDD.makeTO('a_Resultat_ID'), myJDD.getStrData('ID_CODINT'))
	
	'Vérification des valeurs en BD'
	my.SQL.checkJDDWithBD(myJDD)

} // fin du if



