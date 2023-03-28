import org.openqa.selenium.Keys

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import my.NAV
import my.Log as MYLOG



'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	MYLOG.addSTEPGRP('ONGLET HABILITATION')
	
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD,'a_Habilitation')
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD,'a_HabilitationSelected')
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				MYLOG.addSTEPLOOP("Modification $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
	        KW.waitAndVerifyElementText(myJDD,'ID_CODHAB')
			KW.delay(1)
	        KW.scrollAndDoubleClick(myJDD,'td_DateDebut')
			KW.delay(1)
			KW.waitForElementVisible(myJDD,'DT_DATDEB')
			KW.delay(1)
	        KW.setDate(myJDD,'DT_DATDEB')
			KW.delay(1)
			KW.sendKeys(myJDD,'DT_DATDEB', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
			KW.delay(1)
	        KW.scrollAndClick(myJDD,'ID_CODHAB')
			KW.delay(1)
	        KW.scrollAndDoubleClick(myJDD,'td_DateFin')
			KW.delay(1)
			KW.waitForElementVisible(myJDD,'DT_DATFIN')
			KW.delay(1)
	        KW.setDate(myJDD,'DT_DATFIN')
			KW.delay(1)
			KW.sendKeys(myJDD,'DT_DATFIN', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
			KW.delay(1)
			
	    }// fin du for
		
		
	MYLOG.addSTEPGRP('CONTROLE')

		'Vérification des valeurs en BD'
		my.SQL.checkJDDWithBD(myJDD)
		
} // fin du if



