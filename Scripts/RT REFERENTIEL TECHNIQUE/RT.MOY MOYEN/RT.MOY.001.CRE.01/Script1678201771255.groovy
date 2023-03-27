import my.KW
import my.NAV
import my.Log as MYLOG

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_Creation_and_checkCartridge()
	
	
		KW.scrollAndSetText(myJDD,'ID_CODMOY')
		KW.scrollAndSetText(myJDD,'ST_DES')
		KW.scrollAndSetText(myJDD,'ST_GRO')
		KW.scrollAndSetText(myJDD,'ID_CODCAT')
		KW.scrollAndSetText(myJDD,'NU_COUHOR')
	
	
	MYLOG.addSTEPGRP('VALIDATION')

		KW.scrollAndClick(myJDD,'button_Valider')
		
		//NAV.verifierEcranResultat()
			
		KW.verifyElementText(NAV.myGlobalJDD,'span_Selection', myJDD.getStrData('ID_CODMOY'))
		
		my.SQL.checkJDDWithBD(myJDD)

} // fin du if