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

	TNRResult.addSTEPGRP('ONGLET ZONE')
	
		STEP.click(0, myJDD,"tab_Zone")
		STEP.verifyElementVisible(0, myJDD,"tab_ZoneSelected")

		STEP.scrollToPosition(0, 0)
		
		'Boucle sur les lignes d\'un même TC'
	    //for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		for (i in 2..1) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)

	        //STEP.click(0, myJDD,'ID_NUMREF')
			
			if (myJDD.getStrData('ST_DEF')=='N' || i==1) {				
				'Suppression'
				STEP.click(0, myJDD,'span_Supprime_Emplacement')
				//STEP.delay(1)	
				KW.verifyElementNotPresent(myJDD,'ID_NUMREF')
				
			}
	    }

		
	TNRResult.addSTEPACTION('CONTROLE')
	'Vérification en BD que l\'objet n\'existe plus'
	SQL.checkIDNotInBD(myJDD)		

	TNRResult.addEndTestCase()
} // fin du if




