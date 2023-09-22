import tnrJDDManager.JDD
import tnrWebUI.*

import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()
		
myJDD.setCasDeTest('RT.EQU.001.MAJ.01')
	
	TNRResult.addStartTestCase('TEST')
	
	STEP_NAV.goToURL_Creation(1, '7')
	
		
		KW.scrollAndSelectOptionByLabel(myJDD, "NU_CRI")
		/*
		STEP.delay(2)
		KW.scrollAndSelectOptionByValue(myJDD, "NU_CRI")
		STEP.delay(2)
		KW.scrollAndSelectOptionByValue(myJDD, "NU_CRI")
		*/
		
	TNRResult.addEndTestCase()





