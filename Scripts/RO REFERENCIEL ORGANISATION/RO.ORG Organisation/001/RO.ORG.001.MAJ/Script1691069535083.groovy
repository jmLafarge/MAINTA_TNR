import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
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
			
			STEP.setRadio(myJDD, "LblNU_TYP")
			
			STEP.setText(myJDD, "ST_CODCOU",myJDD.getStrData("ST_CODCOU", null,true))
			STEP.setText(myJDD, "ST_CODPERSGES")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_INA", "O")
			STEP.setText(myJDD, "ST_DES")
			STEP.searchWithHelper(myJDD, "ID_CODIMP","","")
			STEP.searchWithHelper(myJDD, "ID_CODCAL","","")
			STEP.searchWithHelper(myJDD, "ID_GESNIV","","")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_EXT", "O")
			STEP.clickCheckboxIfNeeded(myJDD, "ST_AFF", "O")
			
			STEP.setText(myJDD, "NU_EFF")
			
			
			
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			STEP.simpleClick(myJDD, "tab_Adresses")
			STEP.verifyElementVisible(myJDD, "tab_AdressesSelected")
			
			
			STEP.clickCheckboxIfNeeded(myJDD, "ST_RAT", "O")
	  

	TNRResult.addSTEPACTION('VALIDATION')

		STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(myJDD.getStrData("ST_CODCOU", null , true),'','Resultat_ID')
	
		STEP.checkJDDWithBD(myJDD)
	
		
	//TNRResult.addEndTestCase()
} // fin du if


