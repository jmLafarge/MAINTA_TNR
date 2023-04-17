import my.KW
import my.Log as MYLOG
import my.NAV


'Lecture du JDD'
def myJDD = new my.JDD()
		
		
'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Creation_and_checkCartridge()
	

	KW.scrollAndSetText(myJDD,"ID_CODART")
	
	KW.scrollAndSetText(myJDD,"ST_DES")
	
	KW.scrollAndSelectOptionByValue(myJDD,"ST_TYPART")
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////
	
	// gestion des Notes
	
	//soit
	KW.scrollAndSetText(myJDD,"Notes",myJDD.getStrData('OL_DOC'))
	
	//soit comme fournisseur
	
	
			
	MYLOG.addSTEPACTION('VALIDATION')
		
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat()
			
	    KW.verifyElementText(NAV.myGlobalJDD,'Resultat_ID', myJDD.getStrData('ID_CODART')) // prendre en compte la valeur de ARTNUM
	
		my.SQL.checkJDDWithBD(myJDD) // prendre en compte la valeur de ARTNUM car le where du elect se fait sur ID_CODART
		
	

} // fin du if



