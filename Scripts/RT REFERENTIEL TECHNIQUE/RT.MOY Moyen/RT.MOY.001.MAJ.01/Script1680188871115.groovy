import my.KW
import my.NAV
import my.Log as MYLOG

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())
	
	
		KW.scrollAndSetText(myJDD,'ST_DES')
		KW.scrollAndSetText(myJDD,'ST_GRO')
		KW.searchWithHelper(myJDD,'ID_CODCAT',"//a[@title='Rechercher']/i")
		KW.scrollAndSetText(myJDD,'NU_COUHOR')
	
	
	MYLOG.addSTEPGRP('VALIDATION')

		KW.scrollAndClick(myJDD,'button_Valider')

		//NAV.verifierEcranResultat()
			
		KW.verifyElementText(NAV.myGlobalJDD,'span_Selection', myJDD.getStrData())

		my.SQL.checkJDDWithBD(myJDD)

} // fin du if