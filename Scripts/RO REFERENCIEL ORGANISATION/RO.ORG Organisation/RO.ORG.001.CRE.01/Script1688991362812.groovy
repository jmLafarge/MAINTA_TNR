import my.KW
import my.result.TNRResult
import my.NAV
import my.JDD

'Lecture du JDD'
def myJDD = new JDD()
		
		
for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Creation_and_checkCartridge()
	

		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
		
		TNRResult.addSTEPGRP("ONGLET ORGANISATION")
		
			KW.scrollAndClick(myJDD, "tab_Organisation")
			KW.waitForElementVisible(myJDD, "tab_OrganisationSelected")
		
			KW.scrollAndSetText(myJDD, "ST_CODCOU")
			KW.scrollAndSetText(myJDD, "ST_CODPERSGES")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
			KW.scrollAndSetText(myJDD, "ST_DES")
			KW.scrollAndSetText(myJDD, "ID_CODIMP")
			KW.scrollAndSetText(myJDD, "ID_CODCAL")
			KW.scrollAndSetText(myJDD, "ID_GESNIV")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_EXT", "O")
			KW.scrollAndCheckIfNeeded(myJDD, "ST_AFF", "O")
			
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			KW.scrollAndClick(myJDD, "tab_Adresses")
			KW.waitForElementVisible(myJDD, "tab_AdressesSelected")
			
			KW.scrollAndCheckIfNeeded(myJDD, "ST_RAT", "O")

	
	
	
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
	
		my.SQL.checkJDDWithBD(myJDD)
		
	TNRResult.addEndTestCase()

} // fin du if



