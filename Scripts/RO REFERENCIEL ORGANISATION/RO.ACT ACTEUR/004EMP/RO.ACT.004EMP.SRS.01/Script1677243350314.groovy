import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import my.NAV




'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {

	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	my.Log.addSTEPGRP('ONGLET ZONE')
	
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD.makeTO('a_Zone'))
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD.makeTO('a_ZoneSelected'))
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			myJDD.setCasDeTestNum(i)
	
	        KW.waitAndVerifyElementText(myJDD.makeTO('ID_NUMREF'), myJDD.getStrData('ID_NUMREF'))
		
	        'Supression'
			KW.scrollAndClick(myJDD.makeTO('span_Supprime_Emplacement'))
		        
			for ( n in 2..4) {
				'Suppression'
				KW.scrollAndClick(myJDD.makeTO('span_Supprime_Emplacement'))
				if (KW.waitAndAcceptAlert()) {	
					WebUI.delay(1)	
					KW.verifyElementNotPresent(myJDD.makeTO('ID_NUMREF'))
					break
				}else {
					my.Log.addSTEP(n+"eme tentative de suppression" )
				}
			}
	    }
		
	'Vérification en BD que l\'objet n\'existe plus'
	my.SQL.checkIDNotInBD(myJDD)
	
	
	

} // fin du if




