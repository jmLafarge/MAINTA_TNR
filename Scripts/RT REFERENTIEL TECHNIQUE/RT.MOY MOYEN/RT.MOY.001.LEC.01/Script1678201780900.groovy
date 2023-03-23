import my.KW
import my.NAV
import my.Log as MYLOG

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getData('ID_CODMOY'))

	KW.verifyValue(myJDD,'ID_CODMOY')
	
	KW.verifyValue(myJDD,'ST_DES')
	
	KW.verifyValue(myJDD,'ST_GRO')
	
	KW.verifyValue(myJDD,'ID_CODCAT')
	
	KW.verifyValue(myJDD,'NU_COUHOR')
	


} // fin du if