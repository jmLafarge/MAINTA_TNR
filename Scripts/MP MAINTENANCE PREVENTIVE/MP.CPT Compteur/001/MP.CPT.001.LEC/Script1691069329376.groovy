import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import tnrWebUI.*
import tnrResultManager.TNRResult

import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD

'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
	STEP.goToURLReadUpdateDelete(1,myJDD.getStrData())
	STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData())
	
	TNRResult.addSTEPGRP("ONGLET COMPTEUR")
		
		STEP.simpleClick(3, myJDD,"tab_Compteur")
		STEP.verifyElementVisible(4, myJDD,"tab_CompteurSelected")
		
		STEP.verifyElementCheckedOrNot(5,myJDD,"ST_INA","O")
		STEP.verifyValue(6,myJDD,"ID_CODCOM")
		STEP.verifyValue(7,myJDD,"ST_DES")
		STEP.verifyValue(8,myJDD,"ID_CODUNI")
		STEP.verifyValue(9,myJDD,"ID_CODGES")
		STEP.verifyValue(10,myJDD,"ST_DESGES")
		STEP.verifyElementCheckedOrNot(11,myJDD,"ST_MAJDEL","O")
		STEP.verifyValue(12,myJDD,"NU_DEL")
		STEP.verifyElementCheckedOrNot(13,myJDD,"ST_MPH","O")
		STEP.verifyElementCheckedOrNot(14,myJDD,"ST_TELE","O")
		
	TNRResult.addSTEPBLOCK("INDICATION ACTUELLE")
		STEP.verifyValue(15,myJDD,"DT_MAJN")
		
		STEP.verifyDateValue(16,myJDD,'DT_DATREF')
		
		
		STEP.verifyValue(17,myJDD,"NU_VALN")
		
		/* pas de test pour l'instant sur cette partie
		TNRResult.addSTEPBLOCK("SAISIR UNE NOUVELLE VALEUR")
		STEP.verifyElementCheckedOrNot(0, myJDD,"ST_MAJDEL","O")
		STEP.verifyElementCheckedOrNot(0, myJDD,"ST_DELTA","O")
		STEP.verifyValue(0, myJDD,"DATE")
		STEP.verifyValue(0, myJDD,"HEURE")
		STEP.verifyValue(0, myJDD,"INDICATION")
		*/
		
	TNRResult.addSTEPBLOCK("COMPTEUR PRINCIPAL")
		STEP.verifyValue(20,myJDD,"ID_CODCOMPRI")
		STEP.verifyValue(21,myJDD,"ST_DESID_CODCOMPRI")
		
	TNRResult.addSTEPBLOCK("OPTION DE MISE A JOUR DES CODES COMPTEUR")
		STEP.verifyElementCheckedOrNot(22,myJDD,"ST_COMMAJEQU","O")
		STEP.verifyElementCheckedOrNot(23,myJDD,"ST_COMMAJMAT","O")
		STEP.verifyElementCheckedOrNot(24,myJDD,"ST_COMNOTMAJ","O")
		STEP.verifyValue(25,myJDD,"NU_DELPRC")
		STEP.verifyValue(26,myJDD,"NU_DELVAL")
		
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


	TNRResult.addEndTestCase()
}

