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
	
	TNRResult.addSTEPGRP("ONGLET HABILITATION")

		//KW.click(myJDD,"tab_Habilitation")
		KW.click(myJDD,"tab_Habilitation")
		KW.isElementVisible(myJDD,"tab_HabilitationSelected")
		
		KW.scrollToPositionAndWait(0, 0,1)
	
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
			'Ajout'
	        KW.click(myJDD,'a_AjouterHabilitation')
	
	        if (KWDivModal.isOpened()) {
	
		        KW.setText(myJDD,'SelectionHabilitation_input_Filtre', myJDD.getStrData('ID_CODHAB'))
				if (KWDivModal.isNbRecordsEqualTo(1)) {
			        KW.click(myJDD,'SelectionHabilitation_td')
			        KW.click(myJDD,'SelectionHabilitation_button_Ajouter')
					if (KWDivModal.isClosed()) {
				        KW.verifyText(myJDD,'ID_CODHAB')
						
						if (!JDDKW.isNULL(myJDD.getData('DT_DATDEB'))) {
						
					        KW.doubleClick(myJDD,'td_DateDebut')	
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
							KW.setDate(myJDD,'DT_DATDEB')
								
						}
				
						if (!JDDKW.isNULL(myJDD.getData('DT_DATFIN'))) {
							
					        KW.click(myJDD,'SelectionHabilitation_td')
					        KW.doubleClick(myJDD,'td_DateFin')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
					        KW.setDate(myJDD,'DT_DATFIN')
							KW.click(myJDD,'ID_CODHAB')
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



