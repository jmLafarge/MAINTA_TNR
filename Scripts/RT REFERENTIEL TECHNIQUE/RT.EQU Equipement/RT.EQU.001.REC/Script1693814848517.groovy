import tnrJDDManager.JDD
import tnrWebUI.KW
import tnrWebUI.NAV
import tnrResultManager.TNRResult

'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Grille_and_checkCartridge()
	
	'Filtrer la valeur dans la grille'
    KW.scrollAndSetText(myJDD,'input_Filtre_Grille', myJDD.getStrData('ST_CODCOU'))

	KW.scrollAndClick(myJDD,'button_Selectionner')

	KW.delay(1)
	
	'Vérifier que la valeur soit dans la grille filtrée'
	KW.verifyElementText(myJDD,'td_Grille', myJDD.getStrData('ST_CODCOU'))

	
	TNRResult.addEndTestCase()
} // fin du if




