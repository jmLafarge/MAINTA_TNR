import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper

import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrWebUI.*


'Lecture du JDD'
JDD myJDD = new JDD()
	
	myJDD.setCasDeTest('RT.EQU.001.LEC.01')
		
	TNRResult.addStartTestCase('TEST')
	
    NAV.goToURL_ReadUpdateDelete(myJDD.getStrData(),'7')


		STEP.verifyOptionSelectedByLabel(0, myJDD, "ST_ETA")
		//STEP.verifyOptionSelectedByLabel(0, myJDD, "ST_ETA")
		
		STEP.verifyOptionSelectedByLabel(0, myJDD, "NU_CRI")
		
		

	TNRResult.addEndTestCase()


