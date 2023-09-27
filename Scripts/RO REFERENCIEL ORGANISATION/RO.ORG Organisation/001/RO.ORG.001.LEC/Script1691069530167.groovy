import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData('ID_NUMGES'))
	STEP.checkReadUpdateDeleteScreen(2, myJDD.getStrData())

	
		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
	
		TNRResult.addSTEPGRP("ONGLET ORGANISATION")
		
			STEP.simpleClick(0, myJDD, "tab_Organisation")
			STEP.verifyElementVisible(0, myJDD, "tab_OrganisationSelected")
		
			STEP.verifyValue(0, myJDD, "ST_CODCOU")
			STEP.verifyValue(0, myJDD, "ST_CODPERSGES")
			STEP.verifyElementCheckedOrNot(0, myJDD, "ST_INA", "O")
			STEP.verifyValue(0, myJDD, "ST_DES")
			STEP.verifyValue(0, myJDD, "ID_CODGES")
			STEP.verifyValue(0, myJDD, "ST_DESGES")
			STEP.verifyValue(0, myJDD, "ID_CODIMP")
			STEP.verifyValue(0, myJDD, "ST_DESIMP")
			STEP.verifyValue(0, myJDD, "ID_CODCAL")
			STEP.verifyValue(0, myJDD, "ST_DESID_CODCAL")
			STEP.verifyValue(0, myJDD, "ID_GESNIV")
			STEP.verifyValue(0, myJDD, "ST_DESID_GESNIV")
			STEP.verifyValue(0, myJDD, "ST_CODZON")
			STEP.verifyValue(0, myJDD, "ST_DESID_NUMZON")
			STEP.verifyValue(0, myJDD, "NU_EFF")
			STEP.verifyElementCheckedOrNot(0, myJDD, "ST_EXT", "O")
			STEP.verifyElementCheckedOrNot(0, myJDD, "ST_AFF", "O")

			STEP.verifyRadioChecked(0, myJDD, "NU_TYP")
			
		
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			STEP.simpleClick(0, myJDD, "tab_Adresses")
			STEP.verifyElementVisible(0, myJDD, "tab_AdressesSelected")
			
			STEP.verifyElementCheckedOrNot(0, myJDD, "ST_RAT", "O")
	
	
	TNRResult.addEndTestCase()
}

