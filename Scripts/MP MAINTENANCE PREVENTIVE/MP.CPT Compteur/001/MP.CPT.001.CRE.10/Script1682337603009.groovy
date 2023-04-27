import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import my.Log as MYLOG
import my.NAV
import my.JDD

'Lecture du JDD'
def myJDD = new JDD()
		
		
for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
		
	MYLOG.addStartTestCase(cdt)
	
	
	'Boucle sur les lignes d\'un même TC'
	for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		
		myJDD.setCasDeTestNum(i)
		
	    'Naviguer vers la bonne url et controle des infos du cartouche'
	    NAV.goToURL_Creation_and_checkCartridge()
		
		MYLOG.addSTEPGRP("ONGLET COMPTEUR")
			
			
			KW.scrollAndSetText(myJDD,"ID_CODCOM")
	
			KW.scrollAndSetText(myJDD,"ID_CODUNI")
	
		
		
					
		MYLOG.addSTEPACTION('VALIDATION')
			
		    KW.scrollAndClick(NAV.myGlobalJDD,'button_Valider')
		
		    NAV.verifierEcranResultat(myJDD.getStrData())
	}

	my.SQL.checkJDDWithBD(myJDD)
	
	MYLOG.addEndTestCase()

} // fin du if



