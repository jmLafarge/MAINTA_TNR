import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import tnrWebUI.KW
import tnrResultManager.TNRResult
import tnrWebUI.NAV
import tnrJDDManager.JDD
import tnrLog.Log

'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
	
	TNRResult.addStartTestCase(cdt)

    Log.addINFO(myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))
	
	
	TNRResult.addEndTestCase()
}
