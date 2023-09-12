import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrWebUI.KW
import tnrWebUI.NAV
import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
def myJDD = new JDD()
	
		
	TNRResult.addStartTestCase('TEST')
	
	NAV.goToURL_Creation('50')
	

			KW.scrollAndSelectOptionByLabel(myJDD, "NU_TYP",'2')
			KW.delay(2)
			KW.scrollAndSelectOptionByLabel(myJDD, "NU_TYP",'0')
			KW.delay(2)
			KW.scrollAndSelectOptionByLabel(myJDD, "NU_TYP",'1')
			KW.delay(2)
			
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP",'2')
			KW.delay(2)
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP",'0')
			KW.delay(2)
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP",'1')
			
		
	TNRResult.addEndTestCase()

//} // fin du if



