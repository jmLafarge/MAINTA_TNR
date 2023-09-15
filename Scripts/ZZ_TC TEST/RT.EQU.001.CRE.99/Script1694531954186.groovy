import tnrJDDManager.JDD
import tnrWebUI.KW
import tnrWebUI.NAV
import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()
		
myJDD.setCasDeTest('RT.EQU.001.MAJ.01')
	
	TNRResult.addStartTestCase('TEST')
	
	NAV.goToURL_Creation('7')
	
		
		KW.scrollAndSelectOptionByLabel(myJDD, "NU_CRI")
		/*
		KW.delay(2)
		KW.scrollAndSelectOptionByValue(myJDD, "NU_CRI")
		KW.delay(2)
		KW.scrollAndSelectOptionByValue(myJDD, "NU_CRI")
		*/
		
	TNRResult.addEndTestCase()





