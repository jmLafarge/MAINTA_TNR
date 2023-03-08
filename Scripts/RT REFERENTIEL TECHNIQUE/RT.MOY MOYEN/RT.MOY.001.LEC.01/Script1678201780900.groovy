import my.KW
import my.NAV

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getData('ID_CODMOY'))

	KW.verifyValue(myJDD.makeTO('ID_CODMOY'), myJDD.getStrData('ID_CODMOY'))
	
	KW.verifyValue(myJDD.makeTO('ST_DES'), myJDD.getStrData('ST_DES'))
	
	KW.verifyValue(myJDD.makeTO('ST_GRO'), myJDD.getStrData('ST_GRO'))
	
	KW.verifyValue(myJDD.makeTO('ID_CODCAT'), myJDD.getStrData('ID_CODCAT'))
	
	KW.verifyValue(myJDD.makeTO('NU_COUHOR'), myJDD.getStrData('NU_COUHOR'))
	


} // fin du if