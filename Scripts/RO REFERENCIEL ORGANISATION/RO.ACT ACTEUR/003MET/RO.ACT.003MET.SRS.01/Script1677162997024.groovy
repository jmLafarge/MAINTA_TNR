import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
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
				my.Log.addSTEPLOOP("Suppression $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
	        KW.waitAndVerifyElementText(myJDD,'ID_CODMET')
		
			'Suppression'
			for ( n in 1..3) {
				my.Log.addSUBSTEP("Tentative de suppression $n/3" )
				KW.scrollAndClick(myJDD,'span_Supprime_Metier')
				if (KW.waitAndAcceptAlert(GlobalVariable.TIMEOUT,null)) {	
					KW.delay(1)	
					KW.verifyElementNotPresent(myJDD,'ID_CODMET')
					break
				}
			}
	    }
		
	'Vérification en BD que l\'objet n\'existe plus'
	my.SQL.checkIDNotInBD(myJDD)
	
	
	

} // fin du if




