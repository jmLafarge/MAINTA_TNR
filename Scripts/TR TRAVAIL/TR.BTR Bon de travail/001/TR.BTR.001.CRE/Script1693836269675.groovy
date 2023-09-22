import tnrJDDManager.JDD
import tnrWebUI.*

import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP_NAV.goToURL_Creation_and_checkCartridge(1)
	



	
	
	
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.click(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    NAV.verifierEcranResultat(myJDD.getStrData())
	
		SQL.checkJDDWithBD(myJDD)
		
	TNRResult.addEndTestCase()

} // fin du if



