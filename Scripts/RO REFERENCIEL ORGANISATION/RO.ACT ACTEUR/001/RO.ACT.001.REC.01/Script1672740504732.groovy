import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.NAV
import my.Log as MYLOG


'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0 ) {

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Grille_and_checkCartridge()
	
    KW.scrollAndSetText(myJDD,'input_Filtre_Grille', myJDD.getData('ID_CODINT'))

	'Attendre le filtrage du tableau'
	KW.delay(1)
	
	KW.verifyElementText(NAV.myGlobalJDD,'nbrecordsGRID','1')
	KW.verifyElementText(myJDD,'td_Grille', myJDD.getData('ID_CODINT'))

} // fin du if




