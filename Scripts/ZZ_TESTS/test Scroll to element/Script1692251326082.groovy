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
    WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SEC SECURITE/AD.SEC.001.FON.01 Ouvrir session - mot de passe valide'), [:])
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
JDD_list = CustomKeywords.'old.JDDv1.getValuesOf'( JDDpath , JDDname, 'CRE.01')

println(JDD_list)

if (JDD_list.size() > 0) {
    JDD = (JDD_list[1])

    organisation = JDD.ID_CODGES.padRight(6, '.')
	
	// Navigation vers la bonne URL
    'Naviguer vers l\'url'
    CustomKeywords.'NAV.goToURL_Creation'(FCTCODE)


	
	
	
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
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ID_CODINT)
	
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ST_NOM)
	
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ST_PRE)
	
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ST_MAIL)
	
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ST_TELPHO)
	
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ST_TELMOB)
	
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ST_TELCOP)
*/
}


/*------------------------------------------------
 * 	MODIFICATION
 ------------------------------------------------*/
'Lecture du JDD'
JDD_list = CustomKeywords.'old.JDDv1.getValuesOf'( JDDpath , JDDname, 'MAJ.01')

println(JDD_list)

if (JDD_list.size() > 0) {
    JDD = (JDD_list[1])

    organisation = JDD.ID_CODGES.padRight(6, '.')

    CustomKeywords.'STEP.goToURLReadUpdateDelete'(FCTCODE, JDD.ID_CODINT)
	



	/*
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ST_NOM)
	
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ST_PRE)
	
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ST_MAIL)
	
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ST_TELPHO)
	
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ST_TELMOB)
	
	//WebUI.scrollToElement(findTestObject('null'),1)
	WebUI.setText(findTestObject('null'),
		JDD.ST_TELCOP)
*/
	
	
    WUI.delay(1000)
	
}



'A supprimer : pour tester seul'
if (GlobalVariable.TC_CONNEXION) {
    WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SEC SECURITE/AD.SEC.014.FON.01 Fermer session'), [:])
}

