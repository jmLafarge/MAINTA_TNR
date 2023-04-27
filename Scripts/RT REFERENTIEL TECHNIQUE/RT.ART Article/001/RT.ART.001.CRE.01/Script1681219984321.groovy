import my.KW
import my.Log as MYLOG
import my.NAV
import my.JDD

'Lecture du JDD'
def myJDD = new JDD()
		
		
for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	MYLOG.addStartTestCase(cdt)
	
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
	
	    NAV.verifierEcranResultat(myJDD.getStrData('ID_CODART')) // prendre en compte la valeur de ARTNUM
	
		my.SQL.checkJDDWithBD(myJDD) // prendre en compte la valeur de ARTNUM car le where du elect se fait sur ID_CODART
		
		
	MYLOG.addEndTestCase()

} // fin du if



