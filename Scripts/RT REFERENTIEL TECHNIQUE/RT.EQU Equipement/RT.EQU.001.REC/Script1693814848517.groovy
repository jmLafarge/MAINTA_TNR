import tnrJDDManager.JDD
import tnrWebUI.*

import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Grille_and_checkCartridge()
	
	'Filtrer la valeur dans la grille'
    STEP.setText(0, myJDD,'input_Filtre_Grille', myJDD.getStrData('ST_CODCOU'))

	STEP.click(0, myJDD,'button_Selectionner')

	STEP.delay(1)
	
	'Vérifier que la valeur soit dans la grille filtrée'
	STEP.verifyText(0, myJDD,'td_Grille', myJDD.getStrData('ST_CODCOU'))

	
	TNRResult.addEndTestCase()
} // fin du if




