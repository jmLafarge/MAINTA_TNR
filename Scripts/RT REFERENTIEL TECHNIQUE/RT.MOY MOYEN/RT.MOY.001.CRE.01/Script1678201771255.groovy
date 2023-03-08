import my.KW
import my.NAV

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_Creation_and_checkCartridge()
	
	
	KW.scrollAndSetText(myJDD.makeTO('ID_CODMOY'), myJDD.getStrData('ID_CODMOY'))
	
	KW.scrollAndSetText(myJDD.makeTO('ST_DES'), myJDD.getStrData('ST_DES'))
	
	KW.scrollAndSetText(myJDD.makeTO('ST_GRO'), myJDD.getStrData('ST_GRO'))
	
	KW.scrollAndSetText(myJDD.makeTO('ID_CODCAT'), myJDD.getStrData('ID_CODCAT'))
	
	KW.scrollAndSetText(myJDD.makeTO('NU_COUHOR'), myJDD.getStrData('NU_COUHOR'))
	
	
	my.Log.addSTEPGRP('VALIDATION')
	
	'Validation de la saisie'
	KW.scrollAndClick(myJDD.makeTO('button_Valider'))
	
	'Vérification du test case - écran résulat'
	//NAV.verifierEcranResultat()
		
	KW.verifyElementText(NAV.myGlobalJDD.makeTO('span_Selection'), myJDD.getStrData('ID_CODMOY'))
	
	'Vérification des valeurs en BD'
	my.SQL.checkJDDWithBD(myJDD)

} // fin du if