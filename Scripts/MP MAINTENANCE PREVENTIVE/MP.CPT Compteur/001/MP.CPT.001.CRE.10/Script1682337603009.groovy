import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import my.result.TNRResult
import my.NAV
import my.JDD

'Lecture du JDD'
def myJDD = new JDD()
		
		
for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)
	
	
	'Boucle sur les lignes d\'un même TC'
	for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		
		myJDD.setCasDeTestNum(i)
		
	    'Naviguer vers la bonne url et controle des infos du cartouche'
	    NAV.goToURL_Creation_and_checkCartridge()
		
		TNRResult.addSTEPGRP("ONGLET COMPTEUR")
			
			
			KW.scrollAndSetText(myJDD,"ID_CODCOM")
	
			KW.scrollAndSetText(myJDD,"ID_CODUNI")
	
		
		
					
		TNRResult.addSTEPACTION('VALIDATION')
			
		    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
		
		    NAV.verifierEcranResultat(myJDD.getStrData())
	}

	my.SQL.checkJDDWithBD(myJDD)
	
	TNRResult.addEndTestCase()

} // fin du if



