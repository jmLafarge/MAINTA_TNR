import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; 
import tnrJDDManager.JDDKW
import tnrResultManager.TNRResult
import tnrWebUI.*




JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(myJDD.getStrData('ID_CODINT'));STEP.checkReadUpdateDeleteScreen(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET ZONE')
	
		//STEP.simpleClick(myJDD,"tab_Zone")
		STEP.simpleClick(myJDD,"tab_Zone")
		STEP.verifyElementVisible(myJDD,"tab_ZoneSelected")
		
		STEP.scrollToPosition( 0, 0)

		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
			'Ajout'
			STEP.simpleClick(myJDD,'a_AjouterEmplacement')
	
			if (STEP.isDivModalOpened('Emplacement')) {

		        STEP.setText(myJDD,'SelectionEmplacement_input_Filtre', myJDD.getStrData('ID_NUMREF'))
				if (STEP.verifyElementVisible(GlobalJDD.myGlobalJDD,'nbrecordsGRID_1')) {
			        STEP.simpleClick(myJDD,'SelectionEmplacement_td')
			        STEP.simpleClick(myJDD,'SelectionEmplacement_button_Ajouter')
					STEP.simpleClick(myJDD,'SelectionEmplacement_button_Fermer')
					
					if (STEP.isDivModalClosed('Emplacement')) {
			
				        if (STEP.verifyText(myJDD,'ID_NUMREF')) {
							myJDD.replaceSEQUENCIDInJDD('ID_NUMZONLIG')
						}else {
							TNRResult.addDETAIL("Impossible de remplacer SEQUENCEID par ID_NUMREF dans JDD")
						}
			
						
						if (!JDDKW.isNULL(myJDD.getData('DT_DATDEB'))) {
						
					        STEP.doubleClick(myJDD,'td_DateDebut')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
							STEP.setDate(myJDD,'DT_DATDEB')
						}
				
						if (!JDDKW.isNULL(myJDD.getData('DT_DATFIN'))) {
							
					        STEP.simpleClick(myJDD,'SelectionEmplacement_td')
					        STEP.doubleClick(myJDD,'td_DateFin')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
					        STEP.setDate(myJDD,'DT_DATFIN')
							STEP.simpleClick(myJDD,'ID_NUMREF')
						}
					}
				}
			}
	    }// fin du for
	
		TNRResult.addSTEPACTION('CONTROLE')
	
		
		STEP.checkJDDWithBD(myJDD)			

		
		TNRResult.addEndTestCase()

} // fin du if



