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
			
			KW.click(myJDD, "tab_Organisation")
			KW.isElementVisible(myJDD, "tab_OrganisationSelected")
			
			KW.scrollAndSetRadio(myJDD, "LblNU_TYP")
			
			KW.setText(myJDD, "ST_CODCOU",myJDD.getStrData("ST_CODCOU", null,true))
			KW.setText(myJDD, "ST_CODPERSGES")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_INA", "O")
			KW.setText(myJDD, "ST_DES")
			KWSearchHelper.launch(myJDD, "ID_CODIMP","","")
			KWSearchHelper.launch(myJDD, "ID_CODCAL","","")
			KWSearchHelper.launch(myJDD, "ID_GESNIV","","")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_EXT", "O")
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_AFF", "O")
			
			KW.setText(myJDD, "NU_EFF")
			
			
			
		TNRResult.addSTEPGRP("ONGLET ADRESSES")
		
			KW.click(myJDD, "tab_Adresses")
			KW.isElementVisible(myJDD, "tab_AdressesSelected")
			
			
			KWCheckbox.scrollAndCheckIfNeeded(myJDD, "ST_RAT", "O")
	  

	TNRResult.addSTEPACTION('VALIDATION')

		KW.click(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData("ST_CODCOU", null , true),'','Resultat_ID')
	
		SQL.checkJDDWithBD(myJDD)
	
		
	TNRResult.addEndTestCase()
} // fin du if


