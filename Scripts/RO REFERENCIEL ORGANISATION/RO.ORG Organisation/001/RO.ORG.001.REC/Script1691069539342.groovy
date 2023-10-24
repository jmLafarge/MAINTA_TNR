import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrWebUI.STEP
import tnrWebUI.WUI

// Lecture du JDD
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	//'Naviguer vers la bonne url et controle des infos du cartouche'
	STEP.goToGridURL()
	STEP.checkGridScreen()
	
	//'Filtrer la valeur dans la grille'
	STEP.setText(myJDD,'input_Filtre_Grille', myJDD.getStrData())
	
	STEP.simpleClick(myJDD,'button_Selectionner')

	WUI.delay(500)

	//'Vérifier que la valeur soit dans la grille filtrée'
	STEP.verifyText(myJDD,'td_Grille', myJDD.getStrData())
	
	TNRResult.addEndTestCase()

}