import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrWebUI.*

import tnrSqlManager.SQL
import tnrResultManager.TNRResult


'Lecture du JDD'
JDD myJDD = new JDD()



for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())



	
	'Suppression'
	for ( n in 1..3) {
		TNRResult.addSUBSTEP("Tentative de suppression $n/3" )
		if (KW.click(NAV.myGlobalJDD,'button_Supprimer')) {
			if (KW.waitAndAcceptAlert(GlobalVariable.TIMEOUT,null)) {
				KW.delay(1)
				'Vérification du test case - écran'
				NAV.verifierEcranGrille()
				break
			}
		}else {
			break
		}
	}
	
	
	'Vérification en BD que l\'objet n\'existe plus'
	SQL.checkIDNotInBD(myJDD)
	
	TNRResult.addEndTestCase()
} // fin du if


