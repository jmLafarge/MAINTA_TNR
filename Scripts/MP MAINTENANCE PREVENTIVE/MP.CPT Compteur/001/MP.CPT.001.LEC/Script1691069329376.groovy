import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import tnrWebUI.*
import tnrResultManager.TNRResult

import tnrJDDManager.JDD

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
		
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_INA","O")
		KW.verifyValue(myJDD,"ID_CODCOM")
		KW.verifyValue(myJDD,"ST_DES")
		KW.verifyValue(myJDD,"ID_CODUNI")
		KW.verifyValue(myJDD,"ID_CODGES")
		KW.verifyValue(myJDD,"ST_DESGES")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_MAJDEL","O")
		KW.verifyValue(myJDD,"NU_DEL")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_MPH","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_TELE","O")
		
		TNRResult.addSTEPBLOCK("INDICATION ACTUELLE")
		KW.verifyValue(myJDD,"DT_MAJN")
		
		KW.verifyDateValue(myJDD,'DT_DATREF')
		
		
		KW.verifyValue(myJDD,"NU_VALN")
		
		/* pas de test pour l'instant sur cette partie
		TNRResult.addSTEPBLOCK("SAISIR UNE NOUVELLE VALEUR")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_MAJDEL","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_DELTA","O")
		KW.verifyValue(myJDD,"DATE")
		KW.verifyValue(myJDD,"HEURE")
		KW.verifyValue(myJDD,"INDICATION")
		*/
		
		TNRResult.addSTEPBLOCK("COMPTEUR PRINCIPAL")
		KW.verifyValue(myJDD,"ID_CODCOMPRI")
		KW.verifyValue(myJDD,"ST_DESID_CODCOMPRI")
		
		TNRResult.addSTEPBLOCK("OPTION DE MISE A JOUR DES CODES COMPTEUR")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_COMMAJEQU","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_COMMAJMAT","O")
		KWCheckbox.verifyElementCheckedOrNot(myJDD,"ST_COMNOTMAJ","O")
		KW.verifyValue(myJDD,"NU_DELPRC")
		KW.verifyValue(myJDD,"NU_DELVAL")
		
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


	TNRResult.addEndTestCase()
}

