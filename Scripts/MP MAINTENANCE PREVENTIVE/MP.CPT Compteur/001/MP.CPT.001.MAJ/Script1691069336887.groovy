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
	
		KW.click(myJDD,"tab_Compteur")
		KW.isElementVisible(myJDD,"tab_CompteurSelected")
		
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		
		KW.setText(myJDD, "ST_DES")
		KW.setText(myJDD, "ID_CODUNI")
		KWSearchHelper.launch(myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_MAJDEL","O")
		KW.setText(myJDD, "NU_DEL")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_MPH","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_TELE","O")
		
		TNRResult.addSTEPBLOCK("INDICATION ACTUELLE")
		KW.setText(myJDD, "DT_MAJN")
		KW.setText(myJDD, "DT_DATREF")
		KW.setText(myJDD, "NU_VALN")
		
		/* pas de test pour l'instant sur cette partie
		TNRResult.addSTEPBLOCK("SAISIR UNE NOUVELLE VALEUR")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_MAJDEL","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_DELTA","O")
		KW.setText(myJDD, "DATE")
		KW.setText(myJDD, "HEURE")
		KW.setText(myJDD, "INDICATION")
		*/
		
		TNRResult.addSTEPBLOCK("COMPTEUR PRINCIPAL")
		KWSearchHelper.launch(myJDD, "ID_CODCOMPRI","","SEARCH_ID_CODCOM")
		//ST_DESID_CODCOMPRI --> pas d'action en modification
		
		TNRResult.addSTEPBLOCK("OPTION DE MISE A JOUR DES CODES COMPTEUR")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_COMMAJEQU","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_COMMAJMAT","O")
		KWCheckbox.scrollAndCheckIfNeeded(myJDD,"ST_COMNOTMAJ","O")
		KW.setText(myJDD,"NU_DELPRC")
		KW.setText(myJDD,"NU_DELVAL")
		
		/* pas de test pour l'instant sur cette partie
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
	
		KW.scrollToPositionAndWait(0, 0,1)
		KW.click(myJDD,"tab_Equipement")
		KW.isElementVisible(myJDD,"tab_EquipementSelected")
	
	TNRResult.addSTEPGRP("ONGLET MATRICULE")
	
		KW.click(myJDD,"tab_Matricule")
		KW.isElementVisible(myJDD,"tab_MatriculeSelected")
	
	
	TNRResult.addSTEPGRP("ONGLET HISTORIQUE")
	
		KW.click(myJDD,"tab_Historique")
		KW.isElementVisible(myJDD,"tab_HistoriqueSelected")
	
	
	TNRResult.addSTEPGRP("ONGLET COMPTEUR AUXILIAIRE")
	
		KW.click(myJDD,"tab_CompteurAux")
		KW.isElementVisible(myJDD,"tab_CompteurAuxSelected")
	*/
	  
	  
	  
	  

	TNRResult.addSTEPACTION('VALIDATION')
	
	    KW.click(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
	
		SQL.checkJDDWithBD(myJDD)
		
		
	TNRResult.addEndTestCase()
	
} // fin du if


