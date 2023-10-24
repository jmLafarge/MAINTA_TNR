import tnrJDDManager.JDD;  
import tnrResultManager.TNRResult
import tnrWebUI.*


JDD myJDD = new JDD()
	
		
	TNRResult.addStartTestCase('TEST')
	
	STEP_NAV.goToURL_Creation(1, '50')
	

			STEP.selectOptionByLabel(myJDD, "NU_TYP",'2')
			WUI.delay(2)
			STEP.selectOptionByLabel(myJDD, "NU_TYP",'0')
			WUI.delay(2)
			STEP.selectOptionByLabel(myJDD, "NU_TYP",'1')
			WUI.delay(2)
			
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP",'2')
			WUI.delay(2)
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP",'0')
			WUI.delay(2)
			KW.scrollAndSelectOptionByValue(myJDD, "NU_TYP",'1')
			
		
	TNRResult.addEndTestCase()

//} // fin du if



