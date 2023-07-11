import my.KW
import my.NAV
import my.result.TNRResult
import my.JDD

'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())

	
		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
	
		TNRResult.addSTEPGRP("ONGLET ORGANISATION")
		
			KW.scrollAndClick(myJDD, "tab_Organisation")
			KW.waitForElementVisible(myJDD, "tab_OrganisationSelected")
		
			KW.verifyValue(myJDD, "ST_CODCOU")
			KW.verifyValue(myJDD, "ST_CODPERSGES")
			KW.verifyElementCheckedOrNot(myJDD, "ST_INA", "O")
			KW.verifyValue(myJDD, "ST_DES")
			KW.verifyValue(myJDD, "ID_CODGES")
			KW.verifyValue(myJDD, "ST_DESGES")
			KW.verifyValue(myJDD, "ID_CODIMP")
			KW.verifyValue(myJDD, "ST_DESIMP")
			KW.verifyValue(myJDD, "ID_CODCAL")
			KW.verifyValue(myJDD, "ST_DESID_CODCAL")
			KW.verifyValue(myJDD, "ID_GESNIV")
			KW.verifyValue(myJDD, "ST_DESID_GESNIV")
			KW.verifyValue(myJDD, "ST_CODZON")
			KW.verifyValue(myJDD, "ST_DESID_NUMZON")
			KW.verifyValue(myJDD, "NU_EFF")
			KW.verifyElementCheckedOrNot(myJDD, "ST_EXT", "O")
			KW.verifyElementCheckedOrNot(myJDD, "ST_AFF", "O")
		
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			KW.scrollAndClick(myJDD, "tab_Adresses")
			KW.waitForElementVisible(myJDD, "tab_AdressesSelected")
			
			KW.verifyElementCheckedOrNot(myJDD, "ST_RAT", "O")
	
	
	TNRResult.addEndTestCase()
}

