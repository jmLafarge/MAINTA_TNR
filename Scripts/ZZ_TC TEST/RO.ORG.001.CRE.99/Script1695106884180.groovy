import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDKW
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*


'Lecture du JDD'
JDD myJDD = new JDD()
		
		
myJDD.setCasDeTest('RO.ORG.001.CRE.99')
	
	TNRResult.addStartTestCase('TEST')
	
	STEP_NAV.goToURL_Creation(1, '233')
	

	STEP.verifyRadioChecked(0, myJDD, "NU_TYP")
	
	
	STEP.scrollAndSetRadio(0, myJDD, "LblNU_TYP")
	STEP.verifyRadioChecked(0, myJDD, "NU_TYP")
	
	myJDD.myJDDData.setValueOf('NU_TYP', 0, 'RO.ORG.001.CRE.99', 1)
	
	WUI.delay( 1000)
	
	STEP.scrollAndSetRadio(0, myJDD, "LblNU_TYP")
	STEP.verifyRadioChecked(0, myJDD, "NU_TYP")

	
	TNRResult.addEndTestCase()





