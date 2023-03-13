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
		KW.scrollAndClick(myJDD,'a_Zone')
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD,'a_ZoneSelected')
		
		'Boucle sur les lignes d\'un même TC'
	    //for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		for (i in 2..1) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				my.Log.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
	        KW.waitAndVerifyElementText(myJDD,'ID_NUMREF')
			
			if (myJDD.getStrData('ST_DEF')=='N' || i==1) {
		        'Supression'
				KW.scrollAndClick(myJDD,'span_Supprime_Emplacement')
				KW.delay(1)	
				KW.verifyElementNotPresent(myJDD,'ID_NUMREF')
			}
	    }
		
	my.Log.addSTEPGRP('CONTROLE')
	'Vérification en BD que l\'objet n\'existe plus'
	my.SQL.checkIDNotInBD(myJDD)		

} // fin du if




