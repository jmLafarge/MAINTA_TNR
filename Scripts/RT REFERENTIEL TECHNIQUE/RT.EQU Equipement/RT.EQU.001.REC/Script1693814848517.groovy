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
    KW.setText(myJDD,'input_Filtre_Grille', myJDD.getStrData('ST_CODCOU'))

	KW.click(myJDD,'button_Selectionner')

	KW.delay(1)
	
	'Vérifier que la valeur soit dans la grille filtrée'
	KW.verifyText(myJDD,'td_Grille', myJDD.getStrData('ST_CODCOU'))

	
	TNRResult.addEndTestCase()
} // fin du if




