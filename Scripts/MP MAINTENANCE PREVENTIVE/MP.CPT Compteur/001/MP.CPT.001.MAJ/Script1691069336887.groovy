
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
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData()); 
	STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData())



	  
	TNRResult.addSTEPGRP("ONGLET COMPTEUR")
	
		STEP.simpleClick(3, myJDD,"tab_Compteur")
		STEP.verifyElementVisible(4, myJDD,"tab_CompteurSelected")
		
		STEP.scrollAndCheckIfNeeded(5, myJDD,"ST_INA","O")
		
		STEP.setText(6, myJDD, "ST_DES")
		STEP.setText(7, myJDD, "ID_CODUNI")
		STEP.searchWithHelper(8, myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		STEP.scrollAndCheckIfNeeded(9, myJDD,"ST_MAJDEL","O")
		STEP.setText(10, myJDD, "NU_DEL")
		STEP.scrollAndCheckIfNeeded(11, myJDD,"ST_MPH","O")
		STEP.scrollAndCheckIfNeeded(12, myJDD,"ST_TELE","O")
		
	TNRResult.addSTEPBLOCK("INDICATION ACTUELLE")
		STEP.setText(13, myJDD, "DT_MAJN")
		STEP.setText(14, myJDD, "DT_DATREF")
		STEP.setText(15, myJDD, "NU_VALN")
		
		/* pas de test pour l'instant sur cette partie
	TNRResult.addSTEPBLOCK("SAISIR UNE NOUVELLE VALEUR")
		STEP.scrollAndCheckIfNeeded(0, myJDD,"ST_MAJDEL","O")
		STEP.scrollAndCheckIfNeeded(0, myJDD,"ST_DELTA","O")
		STEP.setText(0, myJDD, "DATE")
		STEP.setText(0, myJDD, "HEURE")
		STEP.setText(0, myJDD, "INDICATION")
		*/
		
	TNRResult.addSTEPBLOCK("COMPTEUR PRINCIPAL")
		STEP.searchWithHelper(16, myJDD, "ID_CODCOMPRI","","SEARCH_ID_CODCOM")
		//ST_DESID_CODCOMPRI --> pas d'action en modification
		
	TNRResult.addSTEPBLOCK("OPTION DE MISE A JOUR DES CODES COMPTEUR")
		STEP.scrollAndCheckIfNeeded(17, myJDD,"ST_COMMAJEQU","O")
		STEP.scrollAndCheckIfNeeded(18, myJDD,"ST_COMMAJMAT","O")
		STEP.scrollAndCheckIfNeeded(19, myJDD,"ST_COMNOTMAJ","O")
		STEP.setText(20, myJDD,"NU_DELPRC")
		STEP.setText(21, myJDD,"NU_DELVAL")
		
		/* pas de test pour l'instant sur cette partie
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
	
		STEP.scrollToPosition('', 0, 0)
		STEP.simpleClick(0, myJDD,"tab_Equipement")
		STEP.verifyElementVisible(0, myJDD,"tab_EquipementSelected")
	
	TNRResult.addSTEPGRP("ONGLET MATRICULE")
	
		STEP.simpleClick(0, myJDD,"tab_Matricule")
		STEP.verifyElementVisible(0, myJDD,"tab_MatriculeSelected")
	
	
	TNRResult.addSTEPGRP("ONGLET HISTORIQUE")
	
		STEP.simpleClick(0, myJDD,"tab_Historique")
		STEP.verifyElementVisible(0, myJDD,"tab_HistoriqueSelected")
	
	
	TNRResult.addSTEPGRP("ONGLET COMPTEUR AUXILIAIRE")
	
		STEP.simpleClick(0, myJDD,"tab_CompteurAux")
		STEP.verifyElementVisible(0, myJDD,"tab_CompteurAuxSelected")
	*/
	  
	  
	  
	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    STEP.simpleClick(90, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(91, myJDD.getStrData())
	
		STEP.checkJDDWithBD(92, myJDD)
		
		
	TNRResult.addEndTestCase()
	
} // fin du if


