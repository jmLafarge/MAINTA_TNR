import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrWebUI.*




// Lecture du JDD
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)


	'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(myJDD.getStrData('ID_CODINT'));STEP.checkReadUpdateDeleteScreen(myJDD.getStrData('ID_CODINT'))

		//STEP.simpleClick(myJDD,"tab_Zone")
		STEP.simpleClick(myJDD,"tab_Zone")
		STEP.verifyElementVisible(myJDD,"tab_ZoneSelected")
			
		STEP.scrollToPosition( 0, 0)
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Lecture $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
	        STEP.verifyText(myJDD,'ID_NUMREF')
			
			STEP.verifyImgCheckedOrNot(myJDD,'ST_DEF','O')
			
			if (myJDD.getStrData('ST_TYP')=='EMP'){
				STEP.verifyElementPresent(myJDD,'span_ST_TYP_emp')
			}
	
			STEP.verifyDateText(myJDD,'td_DateDebut', myJDD.getData('DT_DATDEB'))
				
			STEP.verifyDateText(myJDD,'td_DateFin', myJDD.getData('DT_DATFIN'))
				
			
	    }// fin du for
		
	TNRResult.addEndTestCase()
	
} // fin du if



