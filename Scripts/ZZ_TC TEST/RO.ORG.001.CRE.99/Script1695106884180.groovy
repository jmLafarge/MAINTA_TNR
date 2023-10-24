import tnrJDDManager.JDD;   
import tnrResultManager.TNRResult
import tnrWebUI.*



JDD myJDD = new JDD()
		
		
myJDD.setCasDeTest('RO.ORG.001.CRE.99')
	
	TNRResult.addStartTestCase('TEST')
	
	STEP.goToURLCreate('233')
	

	STEP.verifyRadioChecked(myJDD, "NU_TYP")
	
	
	STEP.setRadio(myJDD, "LblNU_TYP")
	STEP.verifyRadioChecked(myJDD, "NU_TYP")
	
	myJDD.myJDDData.setValueOf('NU_TYP', 0, 'RO.ORG.001.CRE.99', 1)
	
	WUI.delay(1000)
	
	STEP.setRadio(myJDD, "LblNU_TYP")
	STEP.verifyRadioChecked(myJDD, "NU_TYP")

	
	TNRResult.addEndTestCase()





