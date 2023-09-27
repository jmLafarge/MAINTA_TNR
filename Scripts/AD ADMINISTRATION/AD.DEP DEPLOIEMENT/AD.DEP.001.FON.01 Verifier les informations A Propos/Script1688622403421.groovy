import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrWebUI.*
import tnrSqlManager.SQL
import tnrResultManager.TNRResult

'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url'
	String url = GlobalVariable.BASE_URL + myJDD.getData('URL')
	STEP.navigateToUrl(1, url,'A propos de Mainta')
	
	if (STEP.verifyElementPresent(2, myJDD,'tab_APropos', GlobalVariable.TIMEOUT)) {
		
		TNRResult.addSTEPBLOCK("Controle des versions à l'écran")
		
			STEP.verifyTextContains(3, myJDD, 'VER_BDD')
			
			List text = []
			
			text = myJDD.getStrData('VER_MOS_XML').split(' ')
			STEP.verifyTextContains(4, myJDD, 'VER_MOS_XML', text[0])
			STEP.verifyTextContains(5, myJDD, 'VER_MOS_XML', text[1])
	
			text = myJDD.getStrData('VER_MOSIWS').split(' ')
			STEP.verifyTextContains(6, myJDD, 'VER_MOSIWS', text[0])
			STEP.verifyTextContains(7, myJDD, 'VER_MOSIWS', text[1])
			
			text = myJDD.getStrData('VER_MOS_XMLI').split(' ')
			STEP.verifyTextContains(8, myJDD, 'VER_MOS_XMLI', text[0])
			STEP.verifyTextContains(9, myJDD, 'VER_MOS_XMLI', text[1])


		TNRResult.addSTEPBLOCK("Controle en BDD")
			
			STEP.verifyMaintaVersion(9,myJDD.getStrData('VER_BDD'))
			
			
		
	}
	
	TNRResult.addEndTestCase()
}



