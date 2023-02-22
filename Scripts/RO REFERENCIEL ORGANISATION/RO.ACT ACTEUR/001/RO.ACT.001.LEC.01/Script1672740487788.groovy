import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import my.KW as KW
import my.NAV as NAV
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getData('ID_CODINT'))

	
	my.Log.addSTEPGRP('ONGLET ACTEUR')

		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD.makeTO('a_Acteur'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_ActeurSelected'))
	
		'Début de lecture des valeurs du JDD'
	
		KW.verifyOptionSelectedByValue(myJDD.makeTO('ST_ETA'), myJDD.getStrData('ST_ETA'))
		
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_INA'),myJDD.getStrData('ST_INA')=='O')
		
	    KW.verifyValue(myJDD.makeTO('ID_CODINT'), myJDD.getStrData('ID_CODINT'))
	
	    KW.verifyValue(myJDD.makeTO('ST_NOM'), myJDD.getStrData('ST_NOM'))
	
	    KW.verifyValue(myJDD.makeTO('ST_PRE'), myJDD.getStrData('ST_PRE'))
	
	    KW.verifyValue(myJDD.makeTO('ST_MAIL'), myJDD.getStrData('ST_MAIL'))
	
	    KW.verifyValue(myJDD.makeTO('ST_TELPHO'), myJDD.getStrData('ST_TELPHO'))
	
	    KW.verifyValue(myJDD.makeTO('ST_TELMOB'), myJDD.getStrData('ST_TELMOB'))
	
	    KW.verifyValue(myJDD.makeTO('ST_TELCOP'), myJDD.getStrData('ST_TELCOP'))
	
		KW.verifyValue(myJDD.makeTO('ID_CODGES'), myJDD.getStrData('ID_CODGES'))
		
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_GRP'),myJDD.getStrData('ST_GRP')=='O')
		
		KW.verifyValue(myJDD.makeTO('ST_DESGES'), myJDD.getStrData('ST_DESGES'))
	
	my.Log.addSTEPGRP('ONGLET AFFECTATION')
		
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD.makeTO('a_Affectation'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_AffectationSelected'))
		
		'Début de lecture des valeurs du JDD'
		KW.verifyValue(myJDD.makeTO('ID_CODGESAFF'), myJDD.getStrData('ID_CODGESAFF'))
		
		KW.verifyValue(myJDD.makeTO('ST_DESID_CODGESAFF'), myJDD.getStrData('ST_DESID_CODGESAFF'))
	
		KW.verifyValue(myJDD.makeTO('ST_MAT'), myJDD.getStrData('ST_MAT'))
	
		KW.verifyValue(myJDD.makeTO('ST_FAM'), myJDD.getStrData('ST_FAM'))
		
		KW.verifyValue(myJDD.makeTO('ST_GRO'), myJDD.getStrData('ST_GRO'))
		
		KW.verifyValue(myJDD.makeTO('ST_DES'), myJDD.getStrData('ST_DES'))
		
		KW.verifyValue(myJDD.makeTO('ID_CODCAT'), myJDD.getStrData('ID_CODCAT'))
		
		KW.verifyValue(myJDD.makeTO('ST_DESID_CODCAT'), myJDD.getStrData('ST_DESID_CODCAT'))
		
		KW.verifyValue(myJDD.makeTO('NU_COUHOR'), myJDD.getStrData('NU_COUHOR'))
		
	
	my.Log.addSTEPGRP('ONGLET ROLE')
	
			
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD.makeTO('a_Role'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_RoleSelected'))
		
		'Début de lecture des valeurs du JDD'

		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_GES'),myJDD.getStrData('ST_GES')=='O')
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_EXP'),myJDD.getStrData('ST_EXP')=='O')
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_MAI'),myJDD.getStrData('ST_MAI')=='O')
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_PRO'),myJDD.getStrData('ST_PRO')=='O')
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_DEM'),myJDD.getStrData('ST_DEM')=='O')
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_INT'),myJDD.getStrData('ST_INT')=='O')
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_ACH'),myJDD.getStrData('ST_ACH')=='O')
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_REC'),myJDD.getStrData('ST_REC')=='O')
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_UTI'),myJDD.getStrData('ST_UTI')=='O')
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_UTIMOB'),myJDD.getStrData('ST_UTIMOB')=='O')
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_INVPRE'),myJDD.getStrData('ST_INVPRE')=='O')
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_INVBT'),myJDD.getStrData('ST_INVBT')=='O')
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_INVDA'),myJDD.getStrData('ST_INVDA')=='O')

	
	my.Log.addSTEPGRP('ONGLET PREVENTIF')
		
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD.makeTO('a_Preventif'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_PreventifSelected'))
		
		'Début de lecture des valeurs du JDD'
		KW.verifyElementCheckedOrNot(myJDD.makeTO('ST_PRIPRE'),myJDD.getStrData('ST_PRIPRE')=='O')
		
		KW.verifyValue(myJDD.makeTO('NU_TAUPRE'), myJDD.getStrData('NU_TAUPRE'))
		
		KW.verifyValue(myJDD.makeTO('ID_CODCAL'), myJDD.getStrData('ID_CODCAL'))
		
		KW.verifyValue(myJDD.makeTO('ST_DESID_CODCAL'), myJDD.getStrData('ST_DESID_CODCAL'))


	my.Log.addSTEPGRP('ONGLET ZONE')
	
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD.makeTO('a_Zone'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_ZoneSelected'))
		
		'Début de lecture des valeurs du JDD'
		KW.verifyValue(myJDD.makeTO('ID_NUMZON'), myJDD.getStrData('ID_NUMZON'))	


}

