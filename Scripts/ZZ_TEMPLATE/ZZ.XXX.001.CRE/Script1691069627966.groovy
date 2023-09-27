import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrWebUI.*

import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLCreate(1); STEP.checkCreateScreen(2)
	



	
	
	
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.simpleClick(0, GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(0, myJDD.getStrData())
	
		STEP.checkJDDWithBD(0, myJDD)
		
	TNRResult.addEndTestCase()

} // fin du if



