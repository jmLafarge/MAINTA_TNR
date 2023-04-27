import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import my.Log as MYLOG
import my.NAV
import my.JDD


'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	MYLOG.addStartTestCase(cdt)
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())



	  
	MYLOG.addSTEPGRP("ONGLET COMPTEUR")
	
		KW.scrollAndClick(myJDD,"tab_Compteur")
		KW.waitForElementVisible(myJDD,"tab_CompteurSelected")
		
		KW.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		
		KW.scrollAndSetText(myJDD, "ST_DES")
		KW.scrollAndSetText(myJDD, "ID_CODUNI")
		KW.searchWithHelper(myJDD, "ID_CODGES","","")
		//ST_DESGES --> pas d'action en modification
		KW.scrollAndCheckIfNeeded(myJDD,"ST_MAJDEL","O")
		KW.scrollAndSetText(myJDD, "NU_DEL")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_MPH","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_TELE","O")
		
		MYLOG.addSTEPBLOCK("INDICATION ACTUELLE")
		KW.scrollAndSetText(myJDD, "DT_MAJN")
		KW.scrollAndSetText(myJDD, "DT_DATREF")
		KW.scrollAndSetText(myJDD, "NU_VALN")
		
		/* pas de test pour l'instant sur cette partie
		MYLOG.addSTEPBLOCK("SAISIR UNE NOUVELLE VALEUR")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_MAJDEL","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_DELTA","O")
		KW.scrollAndSetText(myJDD, "DATE")
		KW.scrollAndSetText(myJDD, "HEURE")
		KW.scrollAndSetText(myJDD, "INDICATION")
		*/
		
		MYLOG.addSTEPBLOCK("COMPTEUR PRINCIPAL")
		KW.searchWithHelper(myJDD, "ID_CODCOMPRI","","SEARCH_ID_CODCOM")
		//ST_DESID_CODCOMPRI --> pas d'action en modification
		MYLOG.addSTEPBLOCK("OPTION DE MISE A JOUR DES CODES COMPTEUR")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_COMMAJEQU","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_COMMAJMAT","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_COMNOTMAJ","O")
	
		/* pas de test pour l'instant sur cette partie
	MYLOG.addSTEPGRP("ONGLET EQUIPEMENT")
	
		WebUI.scrollToPosition(0, 0)
		KW.delay(1)
		KW.scrollAndClick(myJDD,"tab_Equipement")
		KW.waitForElementVisible(myJDD,"tab_EquipementSelected")
	
	MYLOG.addSTEPGRP("ONGLET MATRICULE")
	
		KW.scrollAndClick(myJDD,"tab_Matricule")
		KW.waitForElementVisible(myJDD,"tab_MatriculeSelected")
	
	
	MYLOG.addSTEPGRP("ONGLET HISTORIQUE")
	
		KW.scrollAndClick(myJDD,"tab_Historique")
		KW.waitForElementVisible(myJDD,"tab_HistoriqueSelected")
	
	
	MYLOG.addSTEPGRP("ONGLET COMPTEUR AUXILIAIRE")
	
		KW.scrollAndClick(myJDD,"tab_CompteurAux")
		KW.waitForElementVisible(myJDD,"tab_CompteurAuxSelected")
	*/
	  
	  
	  
	  

	MYLOG.addSTEPACTION('VALIDATION')
	
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
	
		my.SQL.checkJDDWithBD(myJDD)
		
		
	MYLOG.addEndTestCase()
	
} // fin du if


