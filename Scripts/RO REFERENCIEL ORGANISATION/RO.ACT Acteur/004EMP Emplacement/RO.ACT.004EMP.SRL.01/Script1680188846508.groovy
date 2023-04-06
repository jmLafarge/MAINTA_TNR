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
if (myJDD.getNbrLigneCasDeTest() > 0) {


	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))

		KW.scrollAndClick(myJDD,"tab_Zone")
		KW.waitForElementVisible(myJDD,"tab_ZoneSelected")
			
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				MYLOG.addSTEPLOOP("Lecture $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
	        KW.waitAndVerifyElementText(myJDD,'ID_NUMREF')
			
			KW.verifyImgCheckedOrNot(myJDD,'ST_DEF','O')
			
			KW.verifyImg(myJDD,'span_ST_TYP_emp',myJDD.getStrData('ST_TYP')=='EMP')
	
			if (myJDD.getData('DT_DATDEB',i) != '$VIDE') {
				
				KW.verifyDate(myJDD,'td_DateDebut', myJDD.getData('DT_DATDEB'))
				
			}else {
				
				KW.verifyElementText(myJDD,'td_DateDebut', '')
			}
			
			if (myJDD.getData('DT_DATFIN',i) != '$VIDE') {
				
				KW.verifyDate(myJDD,'td_DateFin', myJDD.getData('DT_DATFIN'))
				
			}else {
				
				KW.verifyElementText(myJDD,'td_DateFin', '')
			}
			
	    }// fin du for
	
} // fin du if



