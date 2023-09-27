
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrWebUI.*


'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
	STEP.goToGridURL(1)
	STEP.checkGridScreen(2)
	
	'Filtrer la valeur dans la grille'
    STEP.setText(3, myJDD,'input_Filtre_Grille', myJDD.getStrData())

	'Attendre que le nombre de record = 1'
	STEP.verifyElementVisible(4, GlobalJDD.myGlobalJDD,'nbrecordsGRID_1')
	
	'Vérifier que la valeur soit dans la grille filtrée'
	STEP.verifyText(5, myJDD,'td_Grille', myJDD.getStrData())

	
	TNRResult.addEndTestCase()
} // fin du if




