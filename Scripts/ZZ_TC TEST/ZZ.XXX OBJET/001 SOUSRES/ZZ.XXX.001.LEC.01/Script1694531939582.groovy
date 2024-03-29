import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import tnrWebUI.*
import tnrResultManager.TNRResult

import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrLog.Log


JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
	
	TNRResult.addStartTestCase(cdt)

    Log.addINFO(myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))
	
	
	TNRResult.addEndTestCase()
}
