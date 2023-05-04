import org.openqa.selenium.Keys

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import my.NAV
import my.result.TNRResult
import my.JDD


'Lecture du JDD'
def myJDD = new JDD()

Log.addINFO('CDTList: ' +myJDD.CDTList.join(' - '))

for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET HABILITATION')
	
		KW.scrollAndClick(myJDD,"tab_Habilitation")
		KW.waitForElementVisible(myJDD,"tab_HabilitationSelected")

		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Modification $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
	        KW.scrollAndDoubleClick(myJDD,'td_DateDebut')
			KW.waitForElementVisible(myJDD,'DT_DATDEB')
	        KW.setDate(myJDD,'DT_DATDEB')
			KW.sendKeys(myJDD,'DT_DATDEB', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
			KW.delay(1)
			
	        KW.scrollAndDoubleClick(myJDD,'td_DateFin')
			KW.waitForElementVisible(myJDD,'DT_DATFIN')
	        KW.setDate(myJDD,'DT_DATFIN')
			KW.sendKeys(myJDD,'DT_DATFIN', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
			KW.delay(1)
			
	    }// fin du for
		
		
	TNRResult.addSTEPACTION('CONTROLE')

		'Vérification des valeurs en BD'
		my.SQL.checkJDDWithBD(myJDD)
		
		
	TNRResult.addEndTestCase()
} // fin du if


