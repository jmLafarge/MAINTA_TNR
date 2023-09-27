import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD

import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData()); STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData())

	

	
	
	TNRResult.addEndTestCase()
}

