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

    KW.verifyValue(myJDD.makeTO('ID_CODINT'), myJDD.getData('ID_CODINT'))
	
    KW.verifyValue(myJDD.makeTO('ST_NOM'), myJDD.getData('ST_NOM'))

    KW.verifyValue(myJDD.makeTO('ST_PRE'), myJDD.getData('ST_PRE'))

    KW.verifyValue(myJDD.makeTO('ST_MAIL'), myJDD.getData('ST_MAIL'))

    KW.verifyValue(myJDD.makeTO('ST_TELPHO'), myJDD.getData('ST_TELPHO'))

    KW.verifyValue(myJDD.makeTO('ST_TELMOB'), myJDD.getData('ST_TELMOB'))

    KW.verifyValue(myJDD.makeTO('ST_TELCOP'), myJDD.getData('ST_TELCOP'))

	KW.verifyValue(myJDD.makeTO('ID_CODGES'), myJDD.getData('ID_CODGES'))

	my.Log.addINFO('+++++++++++++++++ reste à faire ST_DESGES à la lecture ++++++++++++++++++++')
	
	/* verifyValue(myJDD.makeTO('ST_DESGES, myJDD.getStrData('ST_DESGES Marche pas, l'attribut Value n'est pas renseigné ni à la création, ni à la modif
 
	'key TAB pour déclencher le remplissage de la description de Organisation'
	WebUI.sendKeys(myJDD.makeTO('ID_CODGES, Keys.chord( Keys.TAB))

	WebUI.delay(1)

	KW.verifyValue(myJDD.makeTO('ST_DESGES, myJDD.getStrData('ST_DESGES))
	*/
    
}

