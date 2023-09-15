import tnrWebUI.KW
import tnrWebUI.NAV
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
    KW.scrollAndSetText(myJDD,'input_Filtre_Grille', myJDD.getStrData('ID_CODART'))

	'Attendre que le nombre de record = 1'
	KW.waitForElementVisible(NAV.myGlobalJDD,'nbrecordsGRID_1')
	
	'Vérifier que la valeur soit dans la grille filtrée'
	KW.verifyElementText(myJDD,'td_Grille', myJDD.getStrData('ID_CODART'))

	TNRResult.addEndTestCase()
} // fin du if




