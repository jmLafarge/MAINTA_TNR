import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import my.Log as MYLOG
import my.NAV

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())

	
	MYLOG.addSTEPGRP("ONGLET COMPTEUR")
		
		KW.scrollAndClick(myJDD,"tab_Compteur")
		KW.waitForElementVisible(myJDD,"tab_CompteurSelected")
		
		KW.verifyElementCheckedOrNot(myJDD,"ST_INA","O")
		KW.verifyValue(myJDD,"ID_CODCOM")
		KW.verifyValue(myJDD,"ST_DES")
		KW.verifyValue(myJDD,"ID_CODUNI")
		KW.verifyValue(myJDD,"ID_CODGES")
		KW.verifyValue(myJDD,"ST_DESGES")
		KW.verifyElementCheckedOrNot(myJDD,"ST_MAJDEL","O")
		KW.verifyValue(myJDD,"NU_DEL")
		KW.verifyElementCheckedOrNot(myJDD,"ST_MPH","O")
		
		MYLOG.addSTEPBLOCK("INDICATION ACTUELLE")
		KW.verifyValue(myJDD,"DT_MAJN")
		
		KW.verifyDateValue(myJDD,'DT_DATREF')
		
		
		KW.verifyValue(myJDD,"NU_VALN")
		MYLOG.addSTEPBLOCK("SAISIR UNE NOUVELLE VALEUR")
		KW.verifyElementCheckedOrNot(myJDD,"ST_MAJDEL","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_DELTA","O")
		KW.verifyValue(myJDD,"DATE")
		KW.verifyValue(myJDD,"HEURE")
		KW.verifyValue(myJDD,"INDICATION")
		MYLOG.addSTEPBLOCK("COMPTEUR PRINCIPAL")
		KW.verifyValue(myJDD,"ID_CODCOMPRI")
		KW.verifyValue(myJDD,"ST_DESID_CODCOMPRI")
		MYLOG.addSTEPBLOCK("OPTION DE MISE A JOUR DES CODES COMPTEUR")
		KW.verifyElementCheckedOrNot(myJDD,"ST_COMMAJEQU","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_COMMAJMAT","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_COMNOTMAJ","O")
		
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
	



}

