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
	STEP.goToURLReadUpdateDelete(1,myJDD.getStrData('ID_CODINT'));STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET METIER')
	
		//STEP.simpleClick(0, myJDD,"Tab_Metier")
		STEP.simpleClick(0, myJDD,"Tab_Metier")
		STEP.verifyElementVisible(0, myJDD,"Tab_MetierSelected")
		
		STEP.scrollToPosition('', 0, 0)
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Suppression $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
	        STEP.verifyText(0, myJDD,'ID_CODMET')
		
			'Suppression'
			for ( n in 1..3) {
				TNRResult.addSUBSTEP("Tentative de suppression $n/3" )
				STEP.simpleClick(0, myJDD,'span_Supprime_Metier')
				if (STEP.waitAndAcceptAlert(0, GlobalVariable.TIMEOUT,null)) {	
					WUI.delay( 1000)	
					STEP.verifyElementNotPresent(0, myJDD,'ID_CODMET')
					break
				}
			}
	    }
		
	'Vérification en BD que l\'objet n\'existe plus'
	STEP.checkIDNotInBD(0, myJDD)
	
	TNRResult.addEndTestCase()
	

} // fin du if




