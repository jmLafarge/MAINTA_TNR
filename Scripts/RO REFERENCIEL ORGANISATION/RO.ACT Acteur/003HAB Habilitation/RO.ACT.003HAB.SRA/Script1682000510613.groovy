import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrResultManager.TNRResult
import tnrWebUI.*



// Lecture du JDD
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(myJDD.getStrData('ID_CODINT'))
	STEP.checkReadUpdateDeleteScreen(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP("ONGLET HABILITATION")

		STEP.simpleClick(myJDD,"tab_Habilitation")
		STEP.verifyElementVisible(myJDD,"tab_HabilitationSelected")
		
		STEP.scrollToPosition( 0, 0)
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
			'Ajout'
	        STEP.simpleClick(myJDD,'a_AjouterHabilitation')
	
	        if (STEP.isDivModalOpened('Habilitation')) {
	
		        STEP.setText(myJDD,'SelectionHabilitation_input_Filtre', myJDD.getStrData('ID_CODHAB'))
				
				if (STEP.verifyElementVisible(GlobalJDD.myGlobalJDD,'nbrecordsGRID_1')) {
					
			        STEP.simpleClick(myJDD,'SelectionHabilitation_td')
			        STEP.simpleClick(myJDD,'SelectionHabilitation_button_Ajouter')
					
					if (STEP.isDivModalClosed('Habilitation')) {
						
				        STEP.verifyText(myJDD,'ID_CODHAB')
						
						if (!JDDKW.isNULL(myJDD.getData('DT_DATDEB'))) {
						
					        STEP.doubleClick(myJDD,'td_DateDebut')	
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
							STEP.setDate(myJDD,'DT_DATDEB')
								
						}
				
						if (!JDDKW.isNULL(myJDD.getData('DT_DATFIN'))) {
							
					        STEP.simpleClick(myJDD,'SelectionHabilitation_td')
					        STEP.doubleClick(myJDD,'td_DateFin')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
					        STEP.setDate(myJDD,'DT_DATFIN')
							STEP.simpleClick(myJDD,'ID_CODHAB')
						}
					}
				}
	        }
			
	    }// fin du for
	
	
	TNRResult.addSTEPACTION('CONTROLE')

		'Vérification des valeurs en BD'
		STEP.checkJDDWithBD(myJDD)		
		
	TNRResult.addEndTestCase()
} // fin du if



