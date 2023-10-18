import org.openqa.selenium.Keys

import internal.GlobalVariable
import tnrJDDManager.JDD; 
import tnrLog.Log
import tnrResultManager.TNRResult
import tnrWebUI.*




'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	STEP.goToURLReadUpdateDelete(myJDD.getStrData('ID_CODINT'));STEP.checkReadUpdateDeleteScreen(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET ZONE')
	
		STEP.simpleClick(myJDD,"tab_Zone")
		STEP.verifyElementVisible(myJDD,"tab_ZoneSelected")
		
		STEP.scrollToPosition( 0, 0)
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Modification $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
				STEP.simpleClick(myJDD, 'ID_NUMREF')
				
				STEP.clickImgboxIfNeeded(myJDD, 'ST_DEF', 'O')
				
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
	
	'Vérification des valeurs en BD'
	STEP.checkJDDWithBD(myJDD)	
	
	//TNRResult.addEndTestCase()
} // fin du if



