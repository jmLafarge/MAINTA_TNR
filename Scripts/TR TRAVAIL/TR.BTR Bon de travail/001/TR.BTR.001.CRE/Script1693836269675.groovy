import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrWebUI.*

import tnrSqlManager.SQL
import tnrResultManager.TNRResult


JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLCreate(); STEP.checkCreateScreen()
	



	
	
	
				
	TNRResult.addSTEPACTION('VALIDATION')
		
	    STEP.simpleClick(GlobalJDD.myGlobalJDD,'button_Valider')
	
	    STEP.checkResultScreen(myJDD.getStrData())
	
		STEP.checkJDDWithBD(myJDD)
		
	TNRResult.addEndTestCase()

} // fin du if



