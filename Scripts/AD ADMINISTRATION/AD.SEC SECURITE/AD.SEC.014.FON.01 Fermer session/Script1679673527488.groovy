import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*

'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	if (KW.isElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT)) {
		
		STEP.switchToFrame(myJDD,'frame_Main')

	    STEP.scrollToPosition(0, 0)

	    STEP.click(1, myJDD,'icon_Logout')
		
		STEP.switchToDefaultContent()
		
	} else {
		// y a pas de frame_main quand on appelle les url en direct ! 
		
		STEP.scrollToPosition(0, 0)
	
	    STEP.click(2, myJDD,'icon_Logout')

	}
	
	if (STEP.verifyElementVisible(0, myJDD,'in_passw', GlobalVariable.TIMEOUT)) {
		
		TNRResult.addSTEPINFO("Déconnexion OK")
		
		'Vérification des valeurs en BD'
		SQL.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")
		
	}else {
		TNRResult.addSTEPINFO("Déconnexion KO")
	}
	
	STEP.closeBrowser(7)
	TNRResult.addEndTestCase()

}

