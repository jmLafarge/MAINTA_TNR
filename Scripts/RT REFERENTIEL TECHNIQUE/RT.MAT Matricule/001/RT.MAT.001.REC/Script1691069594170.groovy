import tnrWebUI.*

import tnrResultManager.TNRResult
import tnrJDDManager.JDD

'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Grille_and_checkCartridge()
		
	'Filtrer la valeur dans la grille'
    KW.setText(myJDD,'input_Filtre_Grille', myJDD.getStrData())

	'Attendre que le nombre de record = 1'
	KW.isElementVisible(NAV.myGlobalJDD,'nbrecordsGRID_1')
	
	'Vérifier que la valeur soit dans la grille filtrée'
	KW.verifyText(myJDD,'td_Grille', myJDD.getStrData())

	
	TNRResult.addEndTestCase()
} // fin du if




