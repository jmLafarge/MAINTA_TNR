import internal.GlobalVariable
import my.JDD
import my.KW
import my.NAV
import my.SQL
import my.result.TNRResult


'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET HABILITATION')
	
		//KW.scrollAndClick(myJDD,"tab_Habilitation")
		KW.click(myJDD,"tab_Habilitation")
		KW.waitForElementVisible(myJDD,"tab_HabilitationSelected")
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Supression $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
	        KW.waitAndVerifyElementText(myJDD,'ID_CODHAB')
		        
			'Suppression'
			for ( n in 1..3) {
				TNRResult.addSUBSTEP("Tentative de suppression $n/3" )
				KW.scrollAndClick(myJDD,'span_Supprime_Habilitation')
				if (KW.waitAndAcceptAlert(GlobalVariable.TIMEOUT,null)) {	
					KW.delay(1)	
					KW.verifyElementNotPresent(myJDD,'ID_CODHAB')
					break
				}
			}
	    }
		
	'Vérification en BD que l\'objet n\'existe plus'
	SQL.checkIDNotInBD(myJDD)
	
	
	TNRResult.addEndTestCase()

} // fin du if





