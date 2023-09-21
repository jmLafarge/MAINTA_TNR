import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*


'Lecture du JDD'
JDD myJDD = new JDD()



for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())


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
		KW.click(myJDD,'button_Supprimer')
		if (KW.waitAndAcceptAlert(GlobalVariable.TIMEOUT,null)) {
			KW.delay(1)
			'Vérification du test case - écran'
			NAV.verifierEcranGrille()
			break
		}
	}
	
	
	'Vérification en BD que l\'objet n\'existe plus'
	SQL.checkIDNotInBD(myJDD)
	
	TNRResult.addEndTestCase()
	
} // fin du if


