import org.openqa.selenium.Keys

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import my.NAV




'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	my.Log.addSTEPGRP('ONGLET METIER')
	
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD,'a_Metier')
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD,'a_MetierSelected')
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				my.Log.addSTEPLOOP("Modification $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
	        KW.waitAndVerifyElementText(myJDD,'ID_CODMET')
			
	        KW.scrollAndDoubleClick(myJDD,'td_DateDebut')
			//KW.scrollAndClick(myJDD,'td_DateDebut'))
			//KW.sendKeys(myJDD,'td_DateDebut'), Keys.chord(Keys.F2),"Envoie de la touche F2 pour saisir la date")
			
			KW.waitForElementVisible(myJDD,'DT_DATDEB')
	
	        KW.setDate(myJDD,'DT_DATDEB', myJDD.getData('DT_DATDEB'))
			KW.sendKeys(myJDD,'DT_DATDEB', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
	
	        KW.scrollAndClick(myJDD,'ID_CODMET')
	
	        KW.scrollAndDoubleClick(myJDD,'td_DateFin')
			//KW.scrollAndClick(myJDD,'td_DateFin'))
			//KW.sendKeys(myJDD,'td_DateFin'), Keys.chord(Keys.F2),"Envoie de la touche F2 pour saisir la date")
			
			KW.waitForElementVisible(myJDD,'DT_DATFIN')
	
	        KW.setDate(myJDD,'DT_DATFIN')
			KW.sendKeys(myJDD,'DT_DATFIN', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
			
	    }// fin du for
		
		
	my.Log.addSTEPGRP('CONTROLE')

		'Vérification des valeurs en BD'
		my.SQL.checkJDDWithBD(myJDD)
		
} // fin du if



