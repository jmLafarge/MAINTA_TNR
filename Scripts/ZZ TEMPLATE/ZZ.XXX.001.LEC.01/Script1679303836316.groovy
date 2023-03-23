import my.KW
import my.NAV as NAV
import my.Log as MYLOG

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'))

	
	MYLOG.addSTEPGRP('ONGLET xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx')

	
	'Clic sur le bon onglet'
	KW.scrollAndClick(myJDD,'xxxxxxxxxxxxx')
	
	'Vérification de l\'onglet'
	KW.waitForElementVisible(myJDD,'xxxxxxxxxxxxxxxxxx')

	'Début de lecture des valeurs du JDD'


}

