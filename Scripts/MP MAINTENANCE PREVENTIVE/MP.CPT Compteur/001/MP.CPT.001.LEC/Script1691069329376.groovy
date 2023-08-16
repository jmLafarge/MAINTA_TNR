import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import myResult.TNRResult
import my.NAV
import myJDDManager.JDD

'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData())

	
	TNRResult.addSTEPGRP("ONGLET COMPTEUR")
		
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
		KW.verifyElementCheckedOrNot(myJDD,"ST_TELE","O")
		
		TNRResult.addSTEPBLOCK("INDICATION ACTUELLE")
		KW.verifyValue(myJDD,"DT_MAJN")
		
		KW.verifyDateValue(myJDD,'DT_DATREF')
		
		
		KW.verifyValue(myJDD,"NU_VALN")
		
		/* pas de test pour l'instant sur cette partie
		TNRResult.addSTEPBLOCK("SAISIR UNE NOUVELLE VALEUR")
		KW.verifyElementCheckedOrNot(myJDD,"ST_MAJDEL","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_DELTA","O")
		KW.verifyValue(myJDD,"DATE")
		KW.verifyValue(myJDD,"HEURE")
		KW.verifyValue(myJDD,"INDICATION")
		*/
		
		TNRResult.addSTEPBLOCK("COMPTEUR PRINCIPAL")
		KW.verifyValue(myJDD,"ID_CODCOMPRI")
		KW.verifyValue(myJDD,"ST_DESID_CODCOMPRI")
		
		TNRResult.addSTEPBLOCK("OPTION DE MISE A JOUR DES CODES COMPTEUR")
		KW.verifyElementCheckedOrNot(myJDD,"ST_COMMAJEQU","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_COMMAJMAT","O")
		KW.verifyElementCheckedOrNot(myJDD,"ST_COMNOTMAJ","O")
		KW.verifyValue(myJDD,"NU_DELPRC")
		KW.verifyValue(myJDD,"NU_DELVAL")
		
		/* pas de test pour l'instant sur cette partie
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
	
		KW.scrollToPosition(0, 0)
		KW.delay(1)
		KW.scrollAndClick(myJDD,"tab_Equipement")
		KW.waitForElementVisible(myJDD,"tab_EquipementSelected")
		
	TNRResult.addSTEPGRP("ONGLET MATRICULE")
		
		KW.scrollAndClick(myJDD,"tab_Matricule")
		KW.waitForElementVisible(myJDD,"tab_MatriculeSelected")
		
		
	TNRResult.addSTEPGRP("ONGLET HISTORIQUE")
		
		KW.scrollAndClick(myJDD,"tab_Historique")
		KW.waitForElementVisible(myJDD,"tab_HistoriqueSelected")
		
		
	TNRResult.addSTEPGRP("ONGLET COMPTEUR AUXILIAIRE")
		
		KW.scrollAndClick(myJDD,"tab_CompteurAux")
		KW.waitForElementVisible(myJDD,"tab_CompteurAuxSelected")
		*/


	TNRResult.addEndTestCase()
}

