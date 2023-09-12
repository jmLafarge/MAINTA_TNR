import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrWebUI.KW
import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	if (KW.isElementPresent(myJDD,'frame_Main', GlobalVariable.TIMEOUT,'INFO')) {
		
		KW.switchToFrame(myJDD,'frame_Main')

	    KW.scrollToPositionAndWait(0, 0,1)

	    KW.click(myJDD,'icon_Logout')
		
		WebUI.switchToDefaultContent()
		
	} else {
		// y a pas de frame_main quand on appelle les url en direct ! 
		
		KW.scrollToPositionAndWait(0, 0,1)
	
	    KW.click(myJDD,'icon_Logout')

	}
	
	if (KW.waitForElementVisible(myJDD,'in_passw', GlobalVariable.TIMEOUT)) {
		
		TNRResult.addSTEP("Déconnexion OK",null)
		
		'Vérification des valeurs en BD'
		SQL.checkJDDWithBD(myJDD,[:],"SELECT * FROM UTILOG ORDER bY DT_LOG DESC")
		
	}else {
		TNRResult.addSTEP("Déconnexion KO",null )
	}
	
	KW.closeBrowser()
	TNRResult.addEndTestCase()

}

