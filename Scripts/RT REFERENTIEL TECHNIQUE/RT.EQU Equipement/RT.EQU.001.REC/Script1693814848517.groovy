import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrWebUI.*

import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
   STEP.goToGridURL();STEP.checkGridScreen()
	
	'Filtrer la valeur dans la grille'
    STEP.setText(myJDD,'input_Filtre_Grille', myJDD.getStrData('ST_CODCOU'))

	STEP.simpleClick(myJDD,'button_Selectionner')

	WUI.delay(1000)
	
	'Vérifier que la valeur soit dans la grille filtrée'
	STEP.verifyText(myJDD,'td_Grille', myJDD.getStrData('ST_CODCOU'))

	
	TNRResult.addEndTestCase()
} // fin du if




