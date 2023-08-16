import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import myResult.TNRResult
import my.NAV
import myJDDManager.JDD
import my.Log

'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.CDTList) {
		
	myJDD.setCasDeTest(cdt)
	
	TNRResult.addStartTestCase(cdt)
	
	Log.addINFO(myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))

	TNRResult.addEndTestCase()
}

