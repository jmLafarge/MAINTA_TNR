import my.KW
import my.NAV
import my.Log as MYLOG


'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0 ) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'))

	
	  MYLOG.addSTEPGRP('ONGLET xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx')
	  

	  		 

	MYLOG.addSTEPGRP('VALIDATION')
	
	
    'Validation de la saisie'
    KW.scrollAndClick(myJDD,'button_Valider')

    'Vérification du test case - écran résulat'
    NAV.verifierEcranResultat()

    KW.verifyElementText(myJDD,'a_Resultat_ID', myJDD.getStrData('xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'))

	'Vérification des valeurs en BD'
	my.SQL.checkJDDWithBD(myJDD)
	
} // fin du if


