import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrWebUI.*

import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()
	
		
	TNRResult.addStartTestCase('TEST')
	
	STEP_NAV.goToURL_Creation(1, '50')
	

			KW.scrollAndSelectOptionByLabel(myJDD, "NU_TYP",'2')
			STEP.delay(2)
			KW.scrollAndSelectOptionByLabel(myJDD, "NU_TYP",'0')
			STEP.delay(2)
			KW.scrollAndSelectOptionByLabel(myJDD, "NU_TYP",'1')
			STEP.delay(2)
			
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP",'2')
			STEP.delay(2)
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP",'0')
			STEP.delay(2)
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP",'1')
			
		
	TNRResult.addEndTestCase()

//} // fin du if



