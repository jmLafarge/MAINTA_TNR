import internal.GlobalVariable
import tnrJDDManager.JDDFileMapper

import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrWebUI.KW
import tnrWebUI.NAV

'Lecture du JDD'
JDD myJDD = new JDD()
	
	myJDD.setCasDeTest('RT.EQU.001.LEC.01')
		
	TNRResult.addStartTestCase('TEST')
	
    NAV.goToURL_RUD(myJDD.getStrData(),'7')


		KW.verifyOptionSelectedByLabel(myJDD, "ST_ETA")
		//KW.verifyOptionSelectedByLabel(myJDD, "ST_ETA")
		
		KW.verifyOptionSelectedByLabel(myJDD, "NU_CRI")
		
		

	TNRResult.addEndTestCase()


