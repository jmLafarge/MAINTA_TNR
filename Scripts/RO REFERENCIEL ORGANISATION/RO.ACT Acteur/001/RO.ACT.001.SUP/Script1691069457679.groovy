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
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData())
	STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData())


	/*
	 * de temps en temps l'alert s'ouvre furtivement sans avoir le temps d'Accepter ! 
	 * --> Peut être dù au fait qu'il y ait d'autres webDrivers qui tournent
	 * 		-->ne pas oublier de faire  >Tools > Web > Terminate running WebDrivers
	 * 
	 * SOLUTION on boucle 3x 
	 */
	
	'Suppression'
	for ( n in 1..3) {
		TNRResult.addSUBSTEP("Tentative de suppression $n/3" )
		STEP.simpleClick(3, myJDD,'button_Supprimer')
		if (STEP.waitAndAcceptAlert(4, GlobalVariable.TIMEOUT,null)) {
			WUI.delay( 1000)
			'Vérification du test case - écran'
			STEP.checkGridScreen(5)
			break
		}
	}
	
	
	'Vérification en BD que l\'objet n\'existe plus'
	STEP.checkIDNotInBD(90, myJDD)
	
	TNRResult.addEndTestCase()
	
} // fin du if


