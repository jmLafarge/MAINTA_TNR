import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable
import my.KW
import my.Log as MYLOG
import my.JDD

'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	MYLOG.addStartTestCase(cdt)
	
	if (KW.isElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT)) {
		
		KW.switchToFrame(myJDD,'frame_Main')

	    WebUI.scrollToPosition(0, 0)
	    KW.delay(1)

	    KW.click(myJDD,'icon_Logout')
		
		WebUI.switchToDefaultContent()
		
	} else {
		// y a pas de frame_main quand on appelle les url en direct ! 
		
		WebUI.scrollToPosition(0, 0)
	    KW.delay(1)
	
	    KW.click(myJDD,'icon_Logout')

	}
	
	if (KW.waitForElementVisible(myJDD,'in_passw', GlobalVariable.TIMEOUT)) {
		
		MYLOG.addSTEP("Déconnexion OK",null)
		
		// il manque le ctrl en BDD car on ne sais pas quoi tester ?
		
	}else {
		MYLOG.addSTEP("Déconnexion KO",null )
	}
	
	KW.closeBrowser()
	MYLOG.addEndTestCase()

}

