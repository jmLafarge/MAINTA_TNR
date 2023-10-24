
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(myJDD.getStrData()); 
	STEP.checkReadUpdateDeleteScreen(myJDD.getStrData())



	  
	TNRResult.addSTEPGRP("ONGLET COMPTEUR")
	
		STEP.simpleClick(myJDD,"tab_Compteur")
		STEP.verifyElementVisible(myJDD,"tab_CompteurSelected")
		
		STEP.clickCheckboxIfNeeded(myJDD,"ST_INA","O")
		
		STEP.setText(myJDD, "ST_DES")
		STEP.setText(myJDD, "ID_CODUNI")
		STEP.searchWithHelper(myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		STEP.clickCheckboxIfNeeded(myJDD,"ST_MAJDEL","O")
		STEP.setText(myJDD, "NU_DEL")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_MPH","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_TELE","O")
		
	TNRResult.addSTEPBLOCK("INDICATION ACTUELLE")
		STEP.setText(myJDD, "DT_MAJN")
		STEP.setText(myJDD, "DT_DATREF")
		STEP.setText(myJDD, "NU_VALN")
		
		/* pas de test pour l'instant sur cette partie
	TNRResult.addSTEPBLOCK("SAISIR UNE NOUVELLE VALEUR")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_MAJDEL","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_DELTA","O")
		STEP.setText(myJDD, "DATE")
		STEP.setText(myJDD, "HEURE")
		STEP.setText(myJDD, "INDICATION")
		*/
		
	TNRResult.addSTEPBLOCK("COMPTEUR PRINCIPAL")
		STEP.searchWithHelper(myJDD, "ID_CODCOMPRI","","SEARCH_ID_CODCOM")
		//ST_DESID_CODCOMPRI --> pas d'action en modification
		
	TNRResult.addSTEPBLOCK("OPTION DE MISE A JOUR DES CODES COMPTEUR")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_COMMAJEQU","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_COMMAJMAT","O")
		STEP.clickCheckboxIfNeeded(myJDD,"ST_COMNOTMAJ","O")
		STEP.setText(myJDD,"NU_DELPRC")
		STEP.setText(myJDD,"NU_DELVAL")
		
		/* pas de test pour l'instant sur cette partie
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
	
		STEP.scrollToPosition( 0, 0)
		STEP.simpleClick(myJDD,"tab_Equipement")
		STEP.verifyElementVisible(myJDD,"tab_EquipementSelected")
	
	TNRResult.addSTEPGRP("ONGLET MATRICULE")
	
		STEP.simpleClick(myJDD,"tab_Matricule")
		STEP.verifyElementVisible(myJDD,"tab_MatriculeSelected")
	
	
	TNRResult.addSTEPGRP("ONGLET HISTORIQUE")
	
		STEP.simpleClick(myJDD,"tab_Historique")
		STEP.verifyElementVisible(myJDD,"tab_HistoriqueSelected")
	
	
	TNRResult.addSTEPGRP("ONGLET COMPTEUR AUXILIAIRE")
	
		STEP.simpleClick(myJDD,"tab_CompteurAux")
		STEP.verifyElementVisible(myJDD,"tab_CompteurAuxSelected")
	*/
	  
	  
	  
	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(myJDD.getStrData())
	
		STEP.checkJDDWithBD(myJDD)
		
		
	TNRResult.addEndTestCase()
	
} // fin du if


