import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrWebUI.*


'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Grille_and_checkCartridge()
	
	'Filtrer la valeur dans la grille'
	STEP.setText(0, myJDD,'input_Filtre_Grille', myJDD.getStrData())
	
	STEP.click(0, myJDD,'button_Selectionner')

	STEP.delay(1)
	
	'Vérifier que la valeur soit dans la grille filtrée'
	STEP.verifyText(0, myJDD,'td_Grille', myJDD.getStrData())
	
	TNRResult.addEndTestCase()
} // fin du if




