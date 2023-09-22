import tnrJDDManager.JDD
import tnrWebUI.*

import tnrSqlManager.SQL
import tnrResultManager.TNRResult


'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())



	  
	TNRResult.addSTEPGRP("ONGLET COMPTEUR")
	
		STEP.click(0, myJDD,"tab_Compteur")
		STEP.verifyElementVisible(0, myJDD,"tab_CompteurSelected")
		
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		
		STEP.setText(0, myJDD, "ST_DES")
		STEP.setText(0, myJDD, "ID_CODUNI")
		KWSearchHelper.launch(myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_MAJDEL","O")
		STEP.setText(0, myJDD, "NU_DEL")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_MPH","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_TELE","O")
		
		TNRResult.addSTEPBLOCK("INDICATION ACTUELLE")
		STEP.setText(0, myJDD, "DT_MAJN")
		STEP.setText(0, myJDD, "DT_DATREF")
		STEP.setText(0, myJDD, "NU_VALN")
		
		/* pas de test pour l'instant sur cette partie
		TNRResult.addSTEPBLOCK("SAISIR UNE NOUVELLE VALEUR")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_MAJDEL","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_DELTA","O")
		STEP.setText(0, myJDD, "DATE")
		STEP.setText(0, myJDD, "HEURE")
		STEP.setText(0, myJDD, "INDICATION")
		*/
		
		TNRResult.addSTEPBLOCK("COMPTEUR PRINCIPAL")
		KWSearchHelper.launch(myJDD, "ID_CODCOMPRI","","SEARCH_ID_CODCOM")
		//ST_DESID_CODCOMPRI --> pas d'action en modification
		
		TNRResult.addSTEPBLOCK("OPTION DE MISE A JOUR DES CODES COMPTEUR")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_COMMAJEQU","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_COMMAJMAT","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_COMNOTMAJ","O")
		STEP.setText(0, myJDD,"NU_DELPRC")
		STEP.setText(0, myJDD,"NU_DELVAL")
		
		/* pas de test pour l'instant sur cette partie
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
	
		STEP.scrollToPosition(0, 0)
		STEP.click(0, myJDD,"tab_Equipement")
		STEP.verifyElementVisible(0, myJDD,"tab_EquipementSelected")
	
	TNRResult.addSTEPGRP("ONGLET MATRICULE")
	
		STEP.click(0, myJDD,"tab_Matricule")
		STEP.verifyElementVisible(0, myJDD,"tab_MatriculeSelected")
	
	
	TNRResult.addSTEPGRP("ONGLET HISTORIQUE")
	
		STEP.click(0, myJDD,"tab_Historique")
		STEP.verifyElementVisible(0, myJDD,"tab_HistoriqueSelected")
	
	
	TNRResult.addSTEPGRP("ONGLET COMPTEUR AUXILIAIRE")
	
		STEP.click(0, myJDD,"tab_CompteurAux")
		STEP.verifyElementVisible(0, myJDD,"tab_CompteurAuxSelected")
	*/
	  
	  
	  
	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    STEP.click(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
	
		SQL.checkJDDWithBD(myJDD)
		
		
	TNRResult.addEndTestCase()
	
} // fin du if


