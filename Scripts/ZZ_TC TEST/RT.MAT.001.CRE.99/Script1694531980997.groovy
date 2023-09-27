import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDKW
import tnrWebUI.*

import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()
	
		
	TNRResult.addStartTestCase('TEST')
	
	STEP_NAV.goToURL_Creation(1, '50')
	

			STEP.scrollAndSelectOptionByLabel(0, myJDD, "NU_TYP",'2')
			WUI.delay( 2)
			STEP.scrollAndSelectOptionByLabel(0, myJDD, "NU_TYP",'0')
			WUI.delay( 2)
			STEP.scrollAndSelectOptionByLabel(0, myJDD, "NU_TYP",'1')
			WUI.delay( 2)
			
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP",'2')
			WUI.delay( 2)
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP",'0')
			WUI.delay( 2)
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP",'1')
			
		
	TNRResult.addEndTestCase()

//} // fin du if



