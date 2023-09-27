import org.openqa.selenium.Keys

import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrLog.Log
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()

Log.addINFO('CDTList: ' +myJDD.getCDTList().join(' - '))

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	STEP.goToURLReadUpdateDelete(1,myJDD.getStrData('ID_CODINT'));STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET HABILITATION')
	
		//STEP.simpleClick(0, myJDD,"tab_Habilitation")
		STEP.simpleClick(0, myJDD,"tab_Habilitation")
		STEP.verifyElementVisible(0, myJDD,"tab_HabilitationSelected")
		
		STEP.scrollToPosition('', 0, 0)
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Modification $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
	        STEP.doubleClick(0, myJDD,'td_DateDebut')
			STEP.verifyElementVisible(0, myJDD,'DT_DATDEB')
	        STEP.setDate(0, myJDD,'DT_DATDEB')
			STEP.sendKeys(0, myJDD,'DT_DATDEB', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
			WUI.delay( 1000)
			
	        STEP.doubleClick(0, myJDD,'td_DateFin')
			STEP.verifyElementVisible(0, myJDD,'DT_DATFIN')
	        STEP.setDate(0, myJDD,'DT_DATFIN')
			STEP.sendKeys(0, myJDD,'DT_DATFIN', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
			WUI.delay( 1000)
			
	    }// fin du for
		
		
	TNRResult.addSTEPACTION('CONTROLE')

		'Vérification des valeurs en BD'
		STEP.checkJDDWithBD(0, myJDD)
		
		
	TNRResult.addEndTestCase()
} // fin du if



