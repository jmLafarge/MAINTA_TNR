import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*




JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
	STEP.goToURLReadUpdateDelete(myJDD.getStrData('ID_CODINT'))
	STEP.checkReadUpdateDeleteScreen(myJDD.getStrData('ID_CODINT'))

	TNRResult.addSTEPGRP('ONGLET ZONE')
	
		STEP.simpleClick(myJDD,"tab_Zone")
		STEP.verifyElementVisible(myJDD,"tab_ZoneSelected")

		//STEP.scrollToPosition( 0, 0)
		
		'Boucle sur les lignes d\'un même TC'
	    //for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		for (i in 2..1) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)

	        //STEP.simpleClick(myJDD,'ID_NUMREF')
			
			if (myJDD.getStrData('ST_DEF')=='N' || i==1) {				
				'Suppression'
				STEP.simpleClick(myJDD,'span_Supprime_Emplacement')
				//WUI.delay(1000)	
				STEP.verifyElementNotPresent(myJDD,'ID_NUMREF')
				
			}
	    }

		
	TNRResult.addSTEPACTION('CONTROLE')
	'Vérification en BD que l\'objet n\'existe plus'
	STEP.checkIDNotInBD(myJDD)		

	TNRResult.addEndTestCase()
} // fin du if




