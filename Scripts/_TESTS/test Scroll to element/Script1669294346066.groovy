import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable


'A supprimer : pour tester seul'
if (GlobalVariable.TC_CONNEXION) {
    WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SECURITE/AD.SEC.001.FON.01 Ouvrir session'), [:])
}

/*------------------------------------------------
 * 	INIT
 ------------------------------------------------*/
String JDDpath = 'RO/ACTEUR/'
String JDDname = 'RO.ACT.001'

String FCTCODE = 'acteur'

def JDD_list

Map JDD = [:]

String organisation = ''

/*------------------------------------------------
* 	CREATION
------------------------------------------------*/
'Lecture du JDD'
JDD_list = CustomKeywords.'my.JDD.getValuesOf'( JDDpath , JDDname, 'CRE.01')

println(JDD_list)

if (JDD_list.size() > 0) {
    JDD = (JDD_list[1])

    organisation = JDD.ID_CODGES.padRight(6, '.')
	
	// Navigation vers la bonne URL
    'Naviguer vers l\'url'
    CustomKeywords.'my.Fct_url_ecran.goToURL_Creation'(FCTCODE)


	
	
	
	// 
	WebDriver driver = DriverFactory.getWebDriver()
	
	List<WebElement> elements = driver.findElements(By.tagName("input"));
	
	for (WebElement element : elements) {
		if (element.getAttribute("type") == "hidden"){
			// ne pas afficher les hidden
		}else {
			println("********************************* :" + element.getAttribute("id"));
		}
	}
	
	
	
	/*
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Acteur_ID_CODINT'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Acteur_ID_CODINT'),
		JDD.ID_CODINT)
	
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Nom_ST_NOM'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Nom_ST_NOM'),
		JDD.ST_NOM)
	
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Prenom_ST_PRE'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Prenom_ST_PRE'),
		JDD.ST_PRE)
	
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_E-Mail_ST_MAIL'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_E-Mail_ST_MAIL'),
		JDD.ST_MAIL)
	
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Telephone_ST_TELPHO'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Telephone_ST_TELPHO'),
		JDD.ST_TELPHO)
	
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Mobile_ST_TELMOB'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Mobile_ST_TELMOB'),
		JDD.ST_TELMOB)
	
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Fax_ST_TELCOP'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Fax_ST_TELCOP'),
		JDD.ST_TELCOP)
*/
}


/*------------------------------------------------
 * 	MODIFICATION
 ------------------------------------------------*/
'Lecture du JDD'
JDD_list = CustomKeywords.'my.JDD.getValuesOf'( JDDpath , JDDname, 'MAJ.01')

println(JDD_list)

if (JDD_list.size() > 0) {
    JDD = (JDD_list[1])

    organisation = JDD.ID_CODGES.padRight(6, '.')

    CustomKeywords.'my.Fct_url_ecran.goToURL_RUD'(FCTCODE, JDD.ID_CODINT)
	



	/*
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Nom_ST_NOM'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Nom_ST_NOM'),
		JDD.ST_NOM)
	
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Prenom_ST_PRE'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Prenom_ST_PRE'),
		JDD.ST_PRE)
	
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_E-Mail_ST_MAIL'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_E-Mail_ST_MAIL'),
		JDD.ST_MAIL)
	
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Telephone_ST_TELPHO'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Telephone_ST_TELPHO'),
		JDD.ST_TELPHO)
	
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Mobile_ST_TELMOB'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Mobile_ST_TELMOB'),
		JDD.ST_TELMOB)
	
	//WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Fax_ST_TELCOP'),1)
	WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Fax_ST_TELCOP'),
		JDD.ST_TELCOP)
*/
	
	
    WebUI.delay(1)
	
}



'A supprimer : pour tester seul'
if (GlobalVariable.TC_CONNEXION) {
    WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SECURITE/AD.SEC.002.FON.01 Fermer session'), [:])
}

