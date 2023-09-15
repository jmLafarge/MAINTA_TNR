import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import tnrWebUI.KW
import tnrWebUI.NAV
import tnrResultManager.TNRResult
import tnrJDDManager.JDD


'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)


	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))

	TNRResult.addSTEPGRP("ONGLET HABILITATION")

		//KW.scrollAndClick(myJDD,"tab_Habilitation")
		KW.scrollAndClick(myJDD,"tab_Habilitation")
		KW.waitForElementVisible(myJDD,"tab_HabilitationSelected")
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Lecture $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
	        KW.waitAndVerifyElementText(myJDD,'ID_CODHAB')
	
	
			KW.verifyDateText(myJDD,'td_DateDebut', myJDD.getData('DT_DATDEB'))
				
			KW.verifyDateText(myJDD,'td_DateFin', myJDD.getData('DT_DATFIN'))
				
			
			
	    }// fin du for
		
	TNRResult.addEndTestCase()
} // fin du if



