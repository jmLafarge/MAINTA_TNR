import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)


	'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData('ID_CODINT'))
	STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData('ID_CODINT'))

	TNRResult.addSTEPGRP("ONGLET HABILITATION")

		//STEP.simpleClick(0, myJDD,"tab_Habilitation")
		STEP.simpleClick(0, myJDD,"tab_Habilitation")
		STEP.verifyElementVisible(0, myJDD,"tab_HabilitationSelected")
		
		STEP.scrollToPosition('', 0, 0)
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Lecture $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
	        STEP.verifyText(0, myJDD,'ID_CODHAB')
	
	
			KW.verifyDateText(myJDD,'td_DateDebut', myJDD.getData('DT_DATDEB'))
				
			KW.verifyDateText(myJDD,'td_DateFin', myJDD.getData('DT_DATFIN'))
				
			
			
	    }// fin du for
		
	TNRResult.addEndTestCase()
} // fin du if



