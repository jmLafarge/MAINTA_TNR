import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.result.TNRResult
import my.JDD
import my.NAV

'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url'
	String url = GlobalVariable.BASE_URL + myJDD.getData('URL')
	KW.navigateToUrl(url,'A propos de Mainta')
	
	if (KW.verifyElementPresent(myJDD,'tab_APropos', GlobalVariable.TIMEOUT)) {
		
		KW.verifyElementTextContains(myJDD, 'VER_MOS_XML')
		
		'Vérification des valeurs en BD'
		//my.SQL.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")
			
		
	}
	


	
	TNRResult.addEndTestCase()
}



