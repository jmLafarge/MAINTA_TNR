import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD(myJDD.getStrData('ID_NUMGES'))
	NAV.verifierEcranRUD(myJDD.getStrData())

	
		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
	
		TNRResult.addSTEPGRP("ONGLET ORGANISATION")
		
			KW.click(myJDD, "tab_Organisation")
			KW.isElementVisible(myJDD, "tab_OrganisationSelected")
		
			KW.verifyValue(myJDD, "ST_CODCOU")
			KW.verifyValue(myJDD, "ST_CODPERSGES")
			KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_INA", "O")
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
			KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_EXT", "O")
			KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_AFF", "O")

			KW.verifyRadioChecked(myJDD, "NU_TYP")
			
		
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			KW.click(myJDD, "tab_Adresses")
			KW.isElementVisible(myJDD, "tab_AdressesSelected")
			
			KWCheckbox.verifyElementCheckedOrNot(myJDD, "ST_RAT", "O")
	
	
	TNRResult.addEndTestCase()
}

