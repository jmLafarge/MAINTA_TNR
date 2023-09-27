import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDKW
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*




'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData('ID_CODINT'));STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET METIER')
	
		//STEP.simpleClick(0, myJDD,"Tab_Metier")
		STEP.simpleClick(0, myJDD,"Tab_Metier")
		STEP.verifyElementVisible(0, myJDD,"Tab_MetierSelected")
		
		STEP.scrollToPosition('', 0, 0)
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			//STEP.scrollToPosition(0,1)
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
			'Ajout'
	        STEP.simpleClick(0, myJDD,'a_AjouterMetier')
			
			if (STEP.isDivModalOpened(0)) {
	
		        STEP.setText(0, myJDD,'SelectionMetier_input_Filtre', myJDD.getStrData('ID_CODMET'))
				if (STEP.verifyElementVisible(10, GlobalJDD.myGlobalJDD,'nbrecordsGRID_1')) {
			        STEP.simpleClick(0, myJDD,'SelectionMetier_td')
					STEP.setText(0, myJDD,'SelectionMetier_input_ST_NIV', myJDD.getStrData('ST_NIV'))
			        STEP.simpleClick(0, myJDD,'SelectionMetier_button_Ajouter')
					
					if (STEP.isDivModalClosed(0)) {
				        STEP.verifyText(0, myJDD,'ID_CODMET')
						
						if (!JDDKW.isNULL(myJDD.getData('DT_DATDEB'))) {
					        STEP.doubleClick(0, myJDD,'td_DateDebut')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
							STEP.setDate(0, myJDD,'DT_DATDEB')
						}
				
						if (!JDDKW.isNULL(myJDD.getData('DT_DATFIN'))) {
							
					        STEP.simpleClick(0, myJDD,'SelectionMetier_td')
					        STEP.doubleClick(0, myJDD,'td_DateFin')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
					        STEP.setDate(0, myJDD,'DT_DATFIN')
							STEP.simpleClick(0, myJDD,'ID_CODMET')
						}
					}
				}
	    	}
	    }// fin du for
	
	
	TNRResult.addSTEPACTION('CONTROLE')

		'Vérification des valeurs en BD'
		STEP.checkJDDWithBD(0, myJDD)
	
	TNRResult.addEndTestCase()
} // fin du if



