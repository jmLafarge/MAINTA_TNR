import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrResultManager.TNRResult
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData('ID_CODINT'))
	STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP("ONGLET HABILITATION")

		//STEP.simpleClick(0, myJDD,"tab_Habilitation")
		STEP.simpleClick(3, myJDD,"tab_Habilitation")
		STEP.verifyElementVisible(4, myJDD,"tab_HabilitationSelected")
		
		STEP.scrollToPosition('', 0, 0)
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
			'Ajout'
	        STEP.simpleClick(5, myJDD,'a_AjouterHabilitation')
	
	        if (STEP.isDivModalOpened(6)) {
	
		        STEP.setText(7, myJDD,'SelectionHabilitation_input_Filtre', myJDD.getStrData('ID_CODHAB'))
				if (STEP.verifyElementVisible(10, GlobalJDD.myGlobalJDD,'nbrecordsGRID_1')) {
			        STEP.simpleClick(11, myJDD,'SelectionHabilitation_td')
			        STEP.simpleClick(12, myJDD,'SelectionHabilitation_button_Ajouter')
					if (STEP.isDivModalClosed(13)) {
				        STEP.verifyText(14, myJDD,'ID_CODHAB')
						
						if (!JDDKW.isNULL(myJDD.getData('DT_DATDEB'))) {
						
					        STEP.doubleClick(15, myJDD,'td_DateDebut')	
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
							STEP.setDate(16, myJDD,'DT_DATDEB')
								
						}
				
						if (!JDDKW.isNULL(myJDD.getData('DT_DATFIN'))) {
							
					        STEP.simpleClick(17, myJDD,'SelectionHabilitation_td')
					        STEP.doubleClick(18, myJDD,'td_DateFin')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
					        STEP.setDate(19, myJDD,'DT_DATFIN')
							STEP.simpleClick(20, myJDD,'ID_CODHAB')
						}
					}
				}
	        }
			
	    }// fin du for
	
	
	TNRResult.addSTEPACTION('CONTROLE')

		'Vérification des valeurs en BD'
		STEP.checkJDDWithBD(90, myJDD)		
		
	TNRResult.addEndTestCase()
} // fin du if



