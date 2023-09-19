import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrJDDManager.JDDKW
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.KW
import tnrWebUI.NAV

'Lecture du JDD'
JDD myJDD = new JDD()
		
		
myJDD.setCasDeTest('RO.ORG.001.CRE.99')
	
	TNRResult.addStartTestCase('TEST')
	
	NAV.goToURL_Creation('233')
	

	KW.verifyRadioChecked(myJDD, "NU_TYP")
	
	
	KW.scrollAndSetRadio(myJDD, "LblNU_TYP")
	KW.verifyRadioChecked(myJDD, "NU_TYP")
	
	myJDD.myJDDData.setValueOf('NU_TYP', 0, 'RO.ORG.001.CRE.99', 1)
	
	KW.delay(1)
	
	KW.scrollAndSetRadio(myJDD, "LblNU_TYP")
	KW.verifyRadioChecked(myJDD, "NU_TYP")

	
	TNRResult.addEndTestCase()





