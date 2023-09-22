import org.openqa.selenium.Keys

import tnrJDDManager.JDD
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
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET HABILITATION')
	
		//STEP.click(0, myJDD,"tab_Habilitation")
		STEP.click(0, myJDD,"tab_Habilitation")
		STEP.verifyElementVisible(0, myJDD,"tab_HabilitationSelected")
		
		STEP.scrollToPosition(0, 0)
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Modification $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
	        KW.doubleClick(myJDD,'td_DateDebut')
			STEP.verifyElementVisible(0, myJDD,'DT_DATDEB')
	        KW.setDate(myJDD,'DT_DATDEB')
			KW.sendKeys(myJDD,'DT_DATDEB', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
			STEP.delay(1)
			
	        KW.doubleClick(myJDD,'td_DateFin')
			STEP.verifyElementVisible(0, myJDD,'DT_DATFIN')
	        KW.setDate(myJDD,'DT_DATFIN')
			KW.sendKeys(myJDD,'DT_DATFIN', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
			STEP.delay(1)
			
	    }// fin du for
		
		
	TNRResult.addSTEPACTION('CONTROLE')

		'Vérification des valeurs en BD'
		SQL.checkJDDWithBD(myJDD)
		
		
	TNRResult.addEndTestCase()
} // fin du if



