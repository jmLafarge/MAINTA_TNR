import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
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
			
			STEP.click(0, myJDD, "tab_Organisation")
			STEP.verifyElementVisible(0, myJDD, "tab_OrganisationSelected")
			
			KW.scrollAndSetRadio(myJDD, "LblNU_TYP")
			
			STEP.setText(0, myJDD, "ST_CODCOU",myJDD.getStrData("ST_CODCOU", null,true))
			STEP.setText(0, myJDD, "ST_CODPERSGES")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
			STEP.setText(0, myJDD, "ST_DES")
			KWSearchHelper.launch(myJDD, "ID_CODIMP","","")
			KWSearchHelper.launch(myJDD, "ID_CODCAL","","")
			KWSearchHelper.launch(myJDD, "ID_GESNIV","","")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_EXT", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_AFF", "O")
			
			STEP.setText(0, myJDD, "NU_EFF")
			
			
			
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			STEP.click(0, myJDD, "tab_Adresses")
			STEP.verifyElementVisible(0, myJDD, "tab_AdressesSelected")
			
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_RAT", "O")
	  

	TNRResult.addSTEPACTION('VALIDATION')

		STEP.click(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData("ST_CODCOU", null , true),'','Resultat_ID')
	
		SQL.checkJDDWithBD(myJDD)
	
		
	TNRResult.addEndTestCase()
} // fin du if


