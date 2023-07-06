import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable
import my.KW
import my.result.TNRResult
import my.JDD

'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
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
		
		TNRResult.addSTEP("Déconnexion OK",null)
		
		'Vérification des valeurs en BD'
		my.SQL.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")
		TNRResult.addDETAIL("Corrigé sur Aster mais après V13.0.2 DEVOPS 13064")
		
	}else {
		TNRResult.addSTEP("Déconnexion KO",null )
	}
	
	KW.closeBrowser()
	TNRResult.addEndTestCase()

}

