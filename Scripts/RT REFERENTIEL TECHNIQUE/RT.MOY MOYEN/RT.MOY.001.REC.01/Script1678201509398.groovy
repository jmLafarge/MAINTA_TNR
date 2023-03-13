import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import my.NAV

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_Grille_and_checkCartridge()
	
	'Début de sasie des valeurs du JDD dans l\'écran'
	KW.scrollAndSetText(myJDD.makeTO('input_Filtre'), myJDD.getData('ID_CODMOY'))

	'Attendre le filtrage du tableau'
	KW.delay(1)
	
	'mise à jour dynamique du xpath'
	'Vérification des valeurs'
	KW.scrollWaitAndVerifyElementText( myJDD.makeTO('td_Grille'), myJDD.getData('ID_CODMOY'))
	
} // fin du if