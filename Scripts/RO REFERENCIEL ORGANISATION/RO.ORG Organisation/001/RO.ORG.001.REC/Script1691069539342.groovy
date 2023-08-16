import my.KW
import my.NAV
import myResult.TNRResult
import myJDDManager.JDD

'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Grille_and_checkCartridge()
	
	'Filtrer la valeur dans la grille'
	KW.scrollAndSetText(myJDD,'input_Filtre_Grille', myJDD.getStrData())
	
	KW.scrollAndClick(myJDD,'button_Selectionner')

	KW.delay(1)
	
	'Vérifier que la valeur soit dans la grille filtrée'
	KW.verifyElementText(myJDD,'td_Grille', myJDD.getStrData())
	
	TNRResult.addEndTestCase()
} // fin du if




