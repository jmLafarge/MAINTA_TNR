import my.KW
import my.NAV



'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0 ) {

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Grille_and_checkCartridge()
	
	'Début de sasie des valeurs du JDD dans l\'écran'
    KW.scrollAndSetText(myJDD,'input_Filtre_Grille', myJDD.getData('xxxxxxxxxxxxxxxxxxxxxxxxxx'))

	'Attendre le filtrage du tableau'
	KW.delay(1)
	
	KW.verifyElementText(NAV.myGlobalJDD,'nbrecordsGRID','1')
	KW.verifyElementText( myJDD,'td_Grille', myJDD.getData('xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'))

} // fin du if




