import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import tnrWebUI.*
import tnrResultManager.TNRResult

import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD


JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
	STEP.goToURLReadUpdateDelete(myJDD.getStrData())
	STEP.checkReadUpdateDeleteScreen(myJDD.getStrData())
	
	TNRResult.addSTEPGRP("ONGLET COMPTEUR")
		
		STEP.simpleClick(myJDD,"tab_Compteur")
		STEP.verifyElementVisible(myJDD,"tab_CompteurSelected")
		
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_INA","O")
		STEP.verifyValue(myJDD,"ID_CODCOM")
		STEP.verifyValue(myJDD,"ST_DES")
		STEP.verifyValue(myJDD,"ID_CODUNI")
		STEP.verifyValue(myJDD,"ID_CODGES")
		STEP.verifyValue(myJDD,"ST_DESGES")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_MAJDEL","O")
		STEP.verifyValue(myJDD,"NU_DEL")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_MPH","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_TELE","O")
		
	TNRResult.addSTEPBLOCK("INDICATION ACTUELLE")
		STEP.verifyValue(myJDD,"DT_MAJN")
		
		STEP.verifyDateValue(myJDD,'DT_DATREF')
		
		
		STEP.verifyValue(myJDD,"NU_VALN")
		
		/* pas de test pour l'instant sur cette partie
		TNRResult.addSTEPBLOCK("SAISIR UNE NOUVELLE VALEUR")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_MAJDEL","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_DELTA","O")
		STEP.verifyValue(myJDD,"DATE")
		STEP.verifyValue(myJDD,"HEURE")
		STEP.verifyValue(myJDD,"INDICATION")
		*/
		
	TNRResult.addSTEPBLOCK("COMPTEUR PRINCIPAL")
		STEP.verifyValue(myJDD,"ID_CODCOMPRI")
		STEP.verifyValue(myJDD,"ST_DESID_CODCOMPRI")
		
	TNRResult.addSTEPBLOCK("OPTION DE MISE A JOUR DES CODES COMPTEUR")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_COMMAJEQU","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_COMMAJMAT","O")
		STEP.verifyBoxCheckedOrNot(myJDD,"ST_COMNOTMAJ","O")
		STEP.verifyValue(myJDD,"NU_DELPRC")
		STEP.verifyValue(myJDD,"NU_DELVAL")
		
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


	TNRResult.addEndTestCase()
}

