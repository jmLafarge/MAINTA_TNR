import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrWebUI.KW
import tnrWebUI.NAV
import tnrSqlManager.SQL
import tnrResultManager.TNRResult


'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET METIER')
	
		//KW.scrollAndClick(myJDD,"Tab_Metier")
		KW.click(myJDD,"Tab_Metier")
		KW.waitForElementVisible(myJDD,"Tab_MetierSelected")
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Suppression $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
	        KW.waitAndVerifyElementText(myJDD,'ID_CODMET')
		
			'Suppression'
			for ( n in 1..3) {
				TNRResult.addSUBSTEP("Tentative de suppression $n/3" )
				KW.scrollAndClick(myJDD,'span_Supprime_Metier')
				if (KW.waitAndAcceptAlert(GlobalVariable.TIMEOUT,null)) {	
					KW.delay(1)	
					KW.verifyElementNotPresent(myJDD,'ID_CODMET')
					break
				}
			}
	    }
		
	'Vérification en BD que l\'objet n\'existe plus'
	SQL.checkIDNotInBD(myJDD)
	
	TNRResult.addEndTestCase()
	

} // fin du if




