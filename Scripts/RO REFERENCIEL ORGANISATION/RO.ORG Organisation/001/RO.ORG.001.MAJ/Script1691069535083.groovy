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
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData('ID_NUMGES'))
	STEP.checkReadUpdateDeleteScreen(2, myJDD.getStrData())



		//Rappel pour ajouter un block dans le fichier Resultat :
		//TNRResult.addSTEPBLOCK("DU TEXTE")
			
			
		TNRResult.addSTEPGRP("ONGLET ORGANISATION")
			
			STEP.simpleClick(0, myJDD, "tab_Organisation")
			STEP.verifyElementVisible(0, myJDD, "tab_OrganisationSelected")
			
			STEP.scrollAndSetRadio(0, myJDD, "LblNU_TYP")
			
			STEP.setText(0, myJDD, "ST_CODCOU",myJDD.getStrData("ST_CODCOU", null,true))
			STEP.setText(0, myJDD, "ST_CODPERSGES")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_INA", "O")
			STEP.setText(0, myJDD, "ST_DES")
			STEP.searchWithHelper(0, myJDD, "ID_CODIMP","","")
			STEP.searchWithHelper(0, myJDD, "ID_CODCAL","","")
			STEP.searchWithHelper(0, myJDD, "ID_GESNIV","","")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_EXT", "O")
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_AFF", "O")
			
			STEP.setText(0, myJDD, "NU_EFF")
			
			
			
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			STEP.simpleClick(0, myJDD, "tab_Adresses")
			STEP.verifyElementVisible(0, myJDD, "tab_AdressesSelected")
			
			
			STEP.scrollAndCheckIfNeeded(0, myJDD, "ST_RAT", "O")
	  

	TNRResult.addSTEPACTION('VALIDATION')

		STEP.simpleClick(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(0, myJDD.getStrData("ST_CODCOU", null , true),'','Resultat_ID')
	
		STEP.checkJDDWithBD(0, myJDD)
	
		
	TNRResult.addEndTestCase()
} // fin du if


