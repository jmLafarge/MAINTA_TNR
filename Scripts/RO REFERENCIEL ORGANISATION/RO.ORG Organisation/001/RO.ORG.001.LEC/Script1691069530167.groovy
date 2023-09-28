import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(myJDD.getStrData('ID_NUMGES'))
	STEP.checkReadUpdateDeleteScreen(myJDD.getStrData())

	
		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
		
	
		TNRResult.addSTEPGRP("ONGLET ORGANISATION")
		
			STEP.simpleClick(myJDD, "tab_Organisation")
			STEP.verifyElementVisible(myJDD, "tab_OrganisationSelected")
		
			STEP.verifyValue(myJDD, "ST_CODCOU")
			STEP.verifyValue(myJDD, "ST_CODPERSGES")
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_INA", "O")
			STEP.verifyValue(myJDD, "ST_DES")
			STEP.verifyValue(myJDD, "ID_CODGES")
			STEP.verifyValue(myJDD, "ST_DESGES")
			STEP.verifyValue(myJDD, "ID_CODIMP")
			STEP.verifyValue(myJDD, "ST_DESIMP")
			STEP.verifyValue(myJDD, "ID_CODCAL")
			STEP.verifyValue(myJDD, "ST_DESID_CODCAL")
			STEP.verifyValue(myJDD, "ID_GESNIV")
			STEP.verifyValue(myJDD, "ST_DESID_GESNIV")
			STEP.verifyValue(myJDD, "ST_CODZON")
			STEP.verifyValue(myJDD, "ST_DESID_NUMZON")
			STEP.verifyValue(myJDD, "NU_EFF")
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_EXT", "O")
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_AFF", "O")

			STEP.verifyRadioChecked(0, myJDD, "NU_TYP")
			
		
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			STEP.simpleClick(myJDD, "tab_Adresses")
			STEP.verifyElementVisible(myJDD, "tab_AdressesSelected")
			
			STEP.verifyBoxCheckedOrNot(myJDD, "ST_RAT", "O")
	
	
	TNRResult.addEndTestCase()
}

