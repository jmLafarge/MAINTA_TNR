import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*




'Lecture du JDD'
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET METIER')
	
		//STEP.click(0, myJDD,"Tab_Metier")
		STEP.click(0, myJDD,"Tab_Metier")
		STEP.verifyElementVisible(0, myJDD,"Tab_MetierSelected")
		
		STEP.scrollToPosition(0, 0)
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			//STEP.scrollToPosition(0,0,1)
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
			'Ajout'
	        STEP.click(0, myJDD,'a_AjouterMetier')
			
			if (KWDivModal.isOpened()) {
	
		        STEP.setText(0, myJDD,'SelectionMetier_input_Filtre', myJDD.getStrData('ID_CODMET'))
				if (KWDivModal.isNbRecordsEqualTo(1)) {
			        STEP.click(0, myJDD,'SelectionMetier_td')
					STEP.setText(0, myJDD,'SelectionMetier_input_ST_NIV', myJDD.getStrData('ST_NIV'))
			        STEP.click(0, myJDD,'SelectionMetier_button_Ajouter')
					
					if (KWDivModal.isClosed()) {
				        STEP.verifyText(0, myJDD,'ID_CODMET')
						
						if (!JDDKW.isNULL(myJDD.getData('DT_DATDEB'))) {
					        KW.doubleClick(myJDD,'td_DateDebut')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
							KW.setDate(myJDD,'DT_DATDEB')
						}
				
						if (!JDDKW.isNULL(myJDD.getData('DT_DATFIN'))) {
							
					        STEP.click(0, myJDD,'SelectionMetier_td')
					        KW.doubleClick(myJDD,'td_DateFin')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
					        KW.setDate(myJDD,'DT_DATFIN')
							STEP.click(0, myJDD,'ID_CODMET')
						}
					}
				}
	    	}
	    }// fin du for
	
	
	TNRResult.addSTEPACTION('CONTROLE')

		'Vérification des valeurs en BD'
		SQL.checkJDDWithBD(myJDD)
	
	TNRResult.addEndTestCase()
} // fin du if



