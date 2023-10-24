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
	
	TNRResult.addSTEPGRP('ONGLET METIER')
	
		//STEP.simpleClick(myJDD,"Tab_Metier")
		STEP.simpleClick(myJDD,"Tab_Metier")
		STEP.verifyElementVisible(myJDD,"Tab_MetierSelected")
		
		STEP.scrollToPosition( 0, 0)
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			//STEP.scrollToPosition(0,1)
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
			'Ajout'
	        STEP.simpleClick(myJDD,'a_AjouterMetier')
			
			if (STEP.isDivModalOpened('Métier')) {
	
		        STEP.setText(myJDD,'SelectionMetier_input_Filtre', myJDD.getStrData('ID_CODMET'))
				if (STEP.verifyElementVisible(GlobalJDD.myGlobalJDD,'nbrecordsGRID_1')) {
			        STEP.simpleClick(myJDD,'SelectionMetier_td')
					STEP.setText(myJDD,'SelectionMetier_input_ST_NIV', myJDD.getStrData('ST_NIV'))
			        STEP.simpleClick(myJDD,'SelectionMetier_button_Ajouter')
					
					if (STEP.isDivModalClosed('Métier')) {
				        STEP.verifyText(myJDD,'ID_CODMET')
						
						if (!JDDKW.isNULL(myJDD.getData('DT_DATDEB'))) {
					        STEP.doubleClick(myJDD,'td_DateDebut')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
							STEP.setDate(myJDD,'DT_DATDEB')
						}
				
						if (!JDDKW.isNULL(myJDD.getData('DT_DATFIN'))) {
							
					        STEP.simpleClick(myJDD,'SelectionMetier_td')
					        STEP.doubleClick(myJDD,'td_DateFin')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
					        STEP.setDate(myJDD,'DT_DATFIN')
							STEP.simpleClick(myJDD,'ID_CODMET')
						}
					}
				}
	    	}
	    }// fin du for
	
	
	TNRResult.addSTEPACTION('CONTROLE')

		
		STEP.checkJDDWithBD(myJDD)
	
	TNRResult.addEndTestCase()
} // fin du if



