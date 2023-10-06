import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
	STEP.goToURLReadUpdateDelete(myJDD.getStrData('ID_CODINT'));STEP.checkReadUpdateDeleteScreen(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET METIER')
	
		//STEP.simpleClick(myJDD,"Tab_Metier")
		STEP.simpleClick(myJDD,"Tab_Metier")
		STEP.verifyElementVisible(myJDD,"Tab_MetierSelected")
		
		STEP.scrollToPosition( 0, 0)
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Suppression $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
	        STEP.verifyText(myJDD,'ID_CODMET')
		
			'Suppression'
			for ( n in 1..3) {
				TNRResult.addSUBSTEP("Tentative de suppression $n/3" )
				STEP.simpleClick(myJDD,'span_Supprime_Metier')
				if (STEP.waitAndAcceptAlert( (int)GlobalVariable.TIMEOUT,(n==3) ? 'FAIL':'INFO')) {
					WUI.delay(1000)	
					STEP.verifyElementNotPresent(myJDD,'ID_CODMET')
					break
				}
			}
	    }
		
	'Vérification en BD que l\'objet n\'existe plus'
	STEP.checkIDNotInBD(myJDD)
	
	TNRResult.addEndTestCase()
	

} // fin du if




