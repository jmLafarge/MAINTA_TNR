import tnrJDDManager.JDD
import tnrWebUI.KW
import tnrWebUI.NAV
import tnrSqlManager.SQL
import tnrResultManager.TNRResult


'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET ZONE')
	
		//KW.scrollAndClick(myJDD,"tab_Zone")
		KW.click(myJDD,"tab_Zone")
		KW.waitForElementVisible(myJDD,"tab_ZoneSelected")

		
		'Boucle sur les lignes d\'un même TC'
	    //for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		for (i in 2..1) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
	        KW.waitAndVerifyElementText(myJDD,'ID_NUMREF')
			
			if (myJDD.getStrData('ST_DEF')=='N' || i==1) {
		        'Supression'
				KW.scrollAndClick(myJDD,'span_Supprime_Emplacement')
				KW.delay(1)	
				KW.verifyElementNotPresent(myJDD,'ID_NUMREF')
			}
	    }
		
	TNRResult.addSTEPACTION('CONTROLE')
	'Vérification en BD que l\'objet n\'existe plus'
	SQL.checkIDNotInBD(myJDD)		

	TNRResult.addEndTestCase()
} // fin du if




