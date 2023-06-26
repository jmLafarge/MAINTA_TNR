import internal.GlobalVariable
import my.JDD
import my.KW
import my.NAV
import my.result.TNRResult


'Lecture du JDD'
def myJDD = new JDD()



for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODART'))



	
	'Suppression'
	for ( n in 1..3) {
		TNRResult.addSUBSTEP("Tentative de suppression $n/3" )
		KW.scrollAndClick(NAV.myGlobalJDD,'button_Supprimer')
		if (KW.waitAndAcceptAlert(GlobalVariable.TIMEOUT,null)) {
			KW.delay(1)
			'Vérification du test case - écran'
			NAV.verifierEcranGrille()
			break
		}
	}
	
	
	'Vérification en BD que l\'objet n\'existe plus'
	my.SQL.checkIDNotInBD(myJDD)
	
	TNRResult.addEndTestCase()
} // fin du if


