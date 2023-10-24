import org.openqa.selenium.Keys

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import tnrJDDManager.JDD; 
import tnrLog.Log
import tnrResultManager.TNRResult
import tnrWebUI.*




JDD myJDD = new JDD()

Log.addINFO('CDTList: ' +myJDD.getCDTList().join(' - '))

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	STEP.goToURLReadUpdateDelete(myJDD.getStrData('ID_CODINT'));STEP.checkReadUpdateDeleteScreen(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET HABILITATION')
	
		STEP.simpleClick(myJDD,"tab_Habilitation")
		STEP.verifyElementVisible(myJDD,"tab_HabilitationSelected")
		
		STEP.scrollToPosition( 0, 0)
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Modification $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
			STEP.simpleClick(myJDD, 'ID_CODHAB')
			
	        STEP.doubleClick(myJDD,'td_DateDebut')
	        STEP.setDate(myJDD,'DT_DATDEB')
			STEP.sendKeys(myJDD,'DT_DATDEB', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
			STEP.verifyElementNotPresent(myJDD, 'DT_DATDEB')

			
	        STEP.doubleClick(myJDD,'td_DateFin')
	        STEP.setDate(myJDD,'DT_DATFIN')
			STEP.sendKeys(myJDD,'DT_DATFIN', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
			STEP.verifyElementNotPresent(myJDD, 'DT_DATFIN')
			
	    }// fin du for
		
		
	TNRResult.addSTEPACTION('CONTROLE')

		
		STEP.checkJDDWithBD(myJDD)
		
		
	TNRResult.addEndTestCase()
} // fin du if



