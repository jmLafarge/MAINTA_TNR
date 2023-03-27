import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import my.Log as MYLOG
import my.KW
import my.NAV

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_Grille_and_checkCartridge()
	
	'Filtrer la valeur dans la grille'
    KW.scrollAndSetText(myJDD,'input_Filtre_Grille', myJDD.getStrData())

	'Attendre que le nombre de record = 1'
	KW.waitForElementVisible(NAV.myGlobalJDD,'nbrecordsGRID_1')
	
	'Vérifier que la valeur soit dans la grille filtrée'
	KW.verifyElementText(myJDD,'td_Grille', myJDD.getStrData())
	
} // fin du if