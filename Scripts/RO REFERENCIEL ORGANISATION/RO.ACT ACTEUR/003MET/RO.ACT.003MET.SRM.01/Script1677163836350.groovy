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
		KW.scrollAndClick(myJDD.makeTO('a_Metier'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_MetierSelected'))
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				my.Log.addSTEPLOOP("Modification $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
	        KW.waitAndVerifyElementText(myJDD.makeTO('ID_CODMET'), myJDD.getStrData('ID_CODMET'))
			
	        KW.scrollAndDoubleClick(myJDD.makeTO('td_DateDebut'))
			//KW.scrollAndClick(myJDD.makeTO('td_DateDebut'))
			//KW.sendKeys(myJDD.makeTO('td_DateDebut'), Keys.chord(Keys.F2),"Envoie de la touche F2 pour saisir la date")
			
			KW.waitForElementVisible(myJDD.makeTO('DT_DATDEB'))
	
	        KW.setDate(myJDD.makeTO('DT_DATDEB'), myJDD.getData('DT_DATDEB'))
			KW.sendKeys(myJDD.makeTO('DT_DATDEB'), Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
	
	        KW.scrollAndClick(myJDD.makeTO('ID_CODMET'))
	
	        KW.scrollAndDoubleClick(myJDD.makeTO('td_DateFin'))
			//KW.scrollAndClick(myJDD.makeTO('td_DateFin'))
			//KW.sendKeys(myJDD.makeTO('td_DateFin'), Keys.chord(Keys.F2),"Envoie de la touche F2 pour saisir la date")
			
			KW.waitForElementVisible(myJDD.makeTO('DT_DATFIN'))
	
	        KW.setDate(myJDD.makeTO('DT_DATFIN'), myJDD.getData('DT_DATFIN'))
			KW.sendKeys(myJDD.makeTO('DT_DATFIN'), Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
			
	    }// fin du for
		
		
	my.Log.addSTEPGRP('CONTROLE')

		'Vérification des valeurs en BD'
		my.SQL.checkJDDWithBD(myJDD)
		
} // fin du if



