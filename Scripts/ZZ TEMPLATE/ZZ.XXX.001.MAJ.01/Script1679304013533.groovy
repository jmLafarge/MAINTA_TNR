import my.KW
import my.Log as MYLOG
import my.NAV



'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0 ) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())



	  
	  
	  
	  
	  
	  

	MYLOG.addSTEPGRP('VALIDATION')
	
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat()
	
	    KW.verifyElementText(NAV.myGlobalJDD,'a_Resultat_ID', myJDD.getStrData())
	
		my.SQL.checkJDDWithBD(myJDD)
	
} // fin du if


