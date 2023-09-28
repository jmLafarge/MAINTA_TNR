import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrWebUI.*

import tnrSqlManager.SQL
import tnrResultManager.TNRResult


'Lecture du JDD'
JDD myJDD = new JDD()



for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	STEP.goToURLReadUpdateDelete(myJDD.getStrData('ID_CODINT'))
	STEP.checkReadUpdateDeleteScreen(myJDD.getStrData('ID_CODINT'))



	
	'Suppression'
	for ( n in 1..3) {
		TNRResult.addSUBSTEP("Tentative de suppression $n/3" )
		if (STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Supprimer')) {
			if (STEP.waitAndAcceptAlert(GlobalVariable.TIMEOUT,null)) {
				WUI.delay(1000)
				'Vérification du test case - écran'
				STEP.checkGridScreen()
				break
			}
		}
	}
	
	
	'Vérification en BD que l\'objet n\'existe plus'
	STEP.checkIDNotInBD(myJDD)
	
	TNRResult.addEndTestCase()
} // fin du if


