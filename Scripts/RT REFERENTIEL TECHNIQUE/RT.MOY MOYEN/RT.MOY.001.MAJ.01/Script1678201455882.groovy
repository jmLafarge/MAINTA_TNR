import my.KW
import my.NAV

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getData('ID_CODMOY'))
	
	
	KW.scrollAndSetText(myJDD,'ST_DES')
	
	KW.scrollAndSetText(myJDD,'ST_GRO')
	
	KW.searchWithHelper(myJDD,'ID_CODCAT',"//a[@title='Rechercher']/i")
	
	KW.scrollAndSetText(myJDD,'NU_COUHOR')
	
	
	my.Log.addSTEPGRP('VALIDATION')
	
	'Validation de la saisie'
	KW.scrollAndClick(myJDD,'button_Valider')
	
	'Vérification du test case - écran résulat'
	//NAV.verifierEcranResultat()
		
	KW.verifyElementText(NAV.myGlobalJDD,'span_Selection', myJDD.getStrData('ID_CODMOY'))
	
	'Vérification des valeurs en BD'
	my.SQL.checkJDDWithBD(myJDD)

} // fin du if