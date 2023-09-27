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
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData()); STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData())



	
	'Suppression'
	for ( n in 1..3) {
		TNRResult.addSUBSTEP("Tentative de suppression $n/3" )
		if (STEP.simpleClick(0, GlobalJDD.myGlobalJDD,'button_Supprimer')) {
			if (STEP.waitAndAcceptAlert(0, GlobalVariable.TIMEOUT,null)) {
				WUI.delay( 1000)
				'Vérification du test case - écran'
				STEP.checkGridScreen()
				break
			}
		}
	}
	
	
	'Vérification en BD que l\'objet n\'existe plus'
	STEP.checkIDNotInBD(0, myJDD)
	
	TNRResult.addEndTestCase()
} // fin du if


