import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.NAV




'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0 ) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))

	
	  my.Log.addSTEPGRP('ONGLET ACTEUR')
	  
	  'Clic sur le bon onglet' 
	  KW.scrollAndClick(myJDD,'a_Acteur')
	  
	  'Vérification de l\'onglet'
	  KW.waitForElementVisible(myJDD,'a_ActeurSelected')
	  
	  'Début de sasie des valeurs du JDD'
	  
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
	  
	  
	  
	  my.Log.addSTEPGRP('ONGLET AFFECTATION')
	  
	  'Clic sur le bon onglet' 
	  KW.scrollAndClick(myJDD,'a_Affectation')
	  
	  'Vérification de l\'onglet'
	  KW.waitForElementVisible(myJDD,'a_AffectationSelected')
	  
	  'Début de sasie des valeurs du JDD'
	  
	  KW.searchWithHelper(myJDD, 'ID_CODGESAFF','','SEARCH_ID_CODGES')
	  
	  KW.scrollAndSetText(myJDD,'ST_MAT')
	  
	  KW.scrollAndSetText(myJDD,'ST_FAM')
	  
	  KW.scrollAndSetText(myJDD,'ST_GRO')
	  
	  KW.scrollAndSetText(myJDD,'ST_DES')
	  
	  KW.scrollAndSetText(myJDD,'ID_CODCAT')
	  
	  KW.searchWithHelper(myJDD, 'ID_CODCAT')
	  
	  KW.scrollAndSetText(myJDD,'NU_COUHOR')
	  
	  
	  
	  my.Log.addSTEPGRP('ONGLET ROLE')
	  
	  
		  'Clic sur le bon onglet' 
		  KW.scrollAndClick(myJDD,'a_Role')
		  
		  'Vérification de l\'onglet' 
		  KW.waitForElementVisible(myJDD,'a_RoleSelected')
		  
		  'Début de sasie des valeurs du JDD'
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
	  
	  
	 			  
	my.Log.addSTEPGRP('ONGLET PRENVENTIF')
			  
			  'Clic sur le bon onglet' 
			  KW.scrollAndClick(myJDD,'a_Preventif')
			  
			  'Vérification de l\'onglet'
			  KW.waitForElementVisible(myJDD,'a_PreventifSelected')
			  
			  'Début de sasie des valeurs du JDD'
			  KW.scrollAndCheckIfNeeded(myJDD,'ST_PRIPRE','O')
			  
			  KW.scrollAndSetText(myJDD,'NU_TAUPRE')
			  
			  KW.scrollAndSetText(myJDD,'ID_CODCAL')
			  
			  
			  my.Log.addSTEPGRP('ONGLET ZONE')
			  
			  'Clic sur le bon onglet' 
			  KW.scrollAndClick(myJDD,'a_Zone')
			  
			  'Vérification de l\'onglet' 
			  KW.waitForElementVisible(myJDD,'a_ZoneSelected')

			  'Début de sasie des valeurs du JDD' 
			  Map specificValueMap = [:] 
			  if (myJDD.getData('ID_NUMZON')!=0) { 
				  KW.scrollAndSetText(myJDD,'ID_NUMZON')
			  }else { 
				  specificValueMap.put('ID_NUMZON', 0)
			  }
			 

	my.Log.addSTEPGRP('VALIDATION')
	
	
    'Validation de la saisie'
    KW.scrollAndClick(myJDD,'button_Valider')

    'Vérification du test case - écran résulat'
    NAV.verifierEcranResultat()

    KW.verifyElementText(myJDD,'a_Resultat_ID', myJDD.getStrData('ID_CODINT'))

	'Vérification des valeurs en BD'
	my.SQL.checkJDDWithBD(myJDD,specificValueMap)
	
} // fin du if


