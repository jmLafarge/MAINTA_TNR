import my.JDD
import my.KW
import my.NAV
import my.SQL
import my.result.TNRResult

'Lecture du JDD'
def myJDD = new JDD()


		
		
for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_Creation_and_checkCartridge()
	


	TNRResult.addSTEPGRP("ONGLET COMPTEUR")
		
		KW.scrollAndClick(myJDD,"tab_Compteur")
		KW.waitForElementVisible(myJDD,"tab_CompteurSelected")
		
		KW.scrollAndCheckIfNeeded(myJDD,"ST_INA","O")
		KW.scrollAndSetText(myJDD,"ID_CODCOM")
		KW.scrollAndSetText(myJDD,"ST_DES")
		KW.scrollAndSetText(myJDD,"ID_CODUNI")
		KW.scrollAndSetText(myJDD,"ID_CODGES")
		//ST_DESGES --> pas d'action en création
		KW.scrollAndCheckIfNeeded(myJDD,"ST_MAJDEL","O")
		KW.scrollAndSetText(myJDD,"NU_DEL")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_MPH","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_TELE","O")
				
		TNRResult.addSTEPBLOCK("INDICATION ACTUELLE")
		KW.scrollAndSetText(myJDD,"DT_MAJN")
		//KW.scrollAndSetText(myJDD,"DT_DATREF")
		KW.scrollAndSetDate(myJDD,"DT_DATREF")
		
		KW.scrollAndSetText(myJDD,"NU_VALN")
		
		/* pas de test pour l'instant sur cette partie
		TNRResult.addSTEPBLOCK("SAISIR UNE NOUVELLE VALEUR")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_MAJDEL","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_DELTA","O")
		KW.scrollAndSetText(myJDD,"DATE")
		KW.scrollAndSetText(myJDD,"HEURE")
		KW.scrollAndSetText(myJDD,"INDICATION")
		*/
		
		TNRResult.addSTEPBLOCK("COMPTEUR PRINCIPAL")
		KW.scrollAndSetText(myJDD,"ID_CODCOMPRI")
		//ST_DESID_CODCOMPRI --> pas d'action en création
		
		TNRResult.addSTEPBLOCK("OPTION DE MISE A JOUR DES CODES COMPTEUR")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_COMMAJEQU","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_COMMAJMAT","O")
		KW.scrollAndCheckIfNeeded(myJDD,"ST_COMNOTMAJ","O")
		KW.scrollAndSetText(myJDD,"NU_DELPRC")
		KW.scrollAndSetText(myJDD,"NU_DELVAL")
		
		
		/* pas de test pour l'instant sur cette partie
	TNRResult.addSTEPGRP("ONGLET EQUIPEMENT")
	
		WebUI.scrollToPosition(0, 0)
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
	
	
	
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
	
		SQL.checkJDDWithBD(myJDD)
		
	TNRResult.addEndTestCase()

} // fin du if



