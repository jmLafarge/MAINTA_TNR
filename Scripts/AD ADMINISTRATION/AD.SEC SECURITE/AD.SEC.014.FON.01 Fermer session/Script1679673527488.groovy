import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*

'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	if (WUI.isElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT)) {
		
		WUI.switchToFrame(myJDD,'frame_Main')

	    STEP.scrollToPosition(0, 0)

	    STEP.simpleClick(myJDD,'icon_Logout')
		
		STEP.switchToDefaultContent()
		
	} else {
		// y a pas de frame_main quand on appelle les url en direct ! 
		
		STEP.scrollToPosition(0, 0)
	
	    STEP.simpleClick(myJDD,'icon_Logout')

	}
	
	if (STEP.verifyElementVisible(myJDD,'in_passw', GlobalVariable.TIMEOUT)) {
		
		TNRResult.addSTEPINFO('',"Déconnexion OK")
		
		'Vérification des valeurs en BD'
		STEP.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")
		
	}else {
		TNRResult.addSTEPINFO('',"Déconnexion KO")
	}
	
	STEP.closeBrowser()
	//TNRResult.addEndTestCase()

}

