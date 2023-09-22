import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrWebUI.*




'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)


	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))

		//STEP.click(0, myJDD,"tab_Zone")
		STEP.click(0, myJDD,"tab_Zone")
		STEP.verifyElementVisible(0, myJDD,"tab_ZoneSelected")
			
		STEP.scrollToPosition(0, 0)
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Lecture $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
	        STEP.verifyText(0, myJDD,'ID_NUMREF')
			
			KWCheckbox.verifyImgCheckedOrNot(myJDD,'ST_DEF','O')
			
			KWCheckbox.verifyImg(myJDD,'span_ST_TYP_emp',myJDD.getStrData('ST_TYP')=='EMP')
	
			KW.verifyDateText(myJDD,'td_DateDebut', myJDD.getData('DT_DATDEB'))
				
			KW.verifyDateText(myJDD,'td_DateFin', myJDD.getData('DT_DATFIN'))
				
			
	    }// fin du for
		
	TNRResult.addEndTestCase()
	
} // fin du if



