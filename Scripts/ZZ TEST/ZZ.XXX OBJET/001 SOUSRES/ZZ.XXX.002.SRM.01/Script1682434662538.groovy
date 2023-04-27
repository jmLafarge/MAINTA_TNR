import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import my.Log as MYLOG
import my.NAV
import my.JDD


'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.CDTList) {
		
	myJDD.setCasDeTest(cdt)
	
	MYLOG.addStartTestCase(cdt)
	
	MYLOG.addINFO(myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))

	MYLOG.addEndTestCase()
}

