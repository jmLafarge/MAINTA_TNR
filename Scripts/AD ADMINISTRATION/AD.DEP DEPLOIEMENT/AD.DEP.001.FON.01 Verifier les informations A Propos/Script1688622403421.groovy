import internal.GlobalVariable
import my.JDD
import my.KW
import my.SQL
import my.result.TNRResult

'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
    'Naviguer vers la bonne url'
	String url = GlobalVariable.BASE_URL + myJDD.getData('URL')
	KW.navigateToUrl(url,'A propos de Mainta')
	
	if (KW.verifyElementPresent(myJDD,'tab_APropos', GlobalVariable.TIMEOUT)) {
		
		TNRResult.addSTEPBLOCK("Controle des versions à l'écran")
		
			KW.verifyElementTextContains(myJDD, 'VER_BDD')
			
			List text = []
			
			text = myJDD.getStrData('VER_MOS_XML').split(' ')
			KW.verifyElementTextContains(myJDD, 'VER_MOS_XML', text[0])
			KW.verifyElementTextContains(myJDD, 'VER_MOS_XML', text[1])
	
			text = myJDD.getStrData('VER_MOSIWS').split(' ')
			KW.verifyElementTextContains(myJDD, 'VER_MOSIWS', text[0])
			KW.verifyElementTextContains(myJDD, 'VER_MOSIWS', text[1])
			
			text = myJDD.getStrData('VER_MOS_XMLI').split(' ')
			KW.verifyElementTextContains(myJDD, 'VER_MOS_XMLI', text[0])
			KW.verifyElementTextContains(myJDD, 'VER_MOS_XMLI', text[1])


		TNRResult.addSTEPBLOCK("Controle en BDD")
		
			String verBDD = SQL.getMaintaVersion()
			String verJDD = myJDD.getStrData('VER_BDD')
			
			if (verBDD == verJDD) {
				TNRResult.addSTEPPASS("Controle de la version $verJDD en BDD")
			}else {
				TNRResult.addSTEPFAIL("Controle de la version $verJDD en BDD")
				TNRResult.addDETAIL("La valeur en BDD est $verBDD")
			}
			
		
	}
	
	TNRResult.addEndTestCase()
}



