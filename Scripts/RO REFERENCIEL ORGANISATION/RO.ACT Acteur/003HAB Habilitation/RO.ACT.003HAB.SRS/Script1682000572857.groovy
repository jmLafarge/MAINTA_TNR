import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET HABILITATION')
	
		//STEP.click(0, myJDD,"tab_Habilitation")
		STEP.click(0, myJDD,"tab_Habilitation")
		STEP.verifyElementVisible(0, myJDD,"tab_HabilitationSelected")
		
		STEP.scrollToPosition(0, 0)
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Supression $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
	        STEP.verifyText(0, myJDD,'ID_CODHAB')
		        
			'Suppression'
			for ( n in 1..3) {
				TNRResult.addSUBSTEP("Tentative de suppression $n/3" )
				STEP.click(0, myJDD,'span_Supprime_Habilitation')
				if (KW.waitAndAcceptAlert(GlobalVariable.TIMEOUT,null)) {	
					STEP.delay(1)	
					KW.verifyElementNotPresent(myJDD,'ID_CODHAB')
					break
				}
			}
	    }
		
	'Vérification en BD que l\'objet n\'existe plus'
	SQL.checkIDNotInBD(myJDD)
	
	
	TNRResult.addEndTestCase()

} // fin du if





