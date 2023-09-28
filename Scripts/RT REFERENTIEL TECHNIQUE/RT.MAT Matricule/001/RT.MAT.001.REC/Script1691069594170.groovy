import tnrWebUI.*

import tnrResultManager.TNRResult
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD

'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
   STEP.goToGridURL();STEP.checkGridScreen()
		
	'Filtrer la valeur dans la grille'
    STEP.setText(myJDD,'input_Filtre_Grille', myJDD.getStrData())

	'Attendre que le nombre de record = 1'
	STEP.verifyElementVisible(GlobalJDD.myGlobalJDD,'nbrecordsGRID_1')
	
	'Vérifier que la valeur soit dans la grille filtrée'
	STEP.verifyText(myJDD,'td_Grille', myJDD.getStrData())

	
	TNRResult.addEndTestCase()
} // fin du if




