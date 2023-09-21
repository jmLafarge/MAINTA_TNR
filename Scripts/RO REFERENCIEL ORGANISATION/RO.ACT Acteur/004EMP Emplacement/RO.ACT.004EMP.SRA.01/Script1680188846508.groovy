import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*



'Lecture du JDD'
JDD myJDD = new JDD()

boolean err = false

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET ZONE')
	
		//KW.click(myJDD,"tab_Zone")
		KW.click(myJDD,"tab_Zone")
		KW.isElementVisible(myJDD,"tab_ZoneSelected")
		
		KW.scrollToPositionAndWait(0, 0,1)

		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
			'Ajout'
			KW.click(myJDD,'a_AjouterEmplacement')
	
			if (KWDivModal.isOpened()) {

		        KW.setText(myJDD,'SelectionEmplacement_input_Filtre', myJDD.getStrData('ID_NUMREF'))
				if (KWDivModal.isNbRecordsEqualTo(1)) {
			        KW.click(myJDD,'SelectionEmplacement_td')
			        KW.click(myJDD,'SelectionEmplacement_button_Ajouter')
					KW.click(myJDD,'SelectionEmplacement_button_Fermer')
					
					if (KWDivModal.isClosed()) {
			
				        if (KW.verifyText(myJDD,'ID_NUMREF')) {
							myJDD.replaceSEQUENCIDInJDD('ID_NUMZONLIG')
						}else {
							TNRResult.addDETAIL("Impossible de remplacer SEQUENCEID par ID_NUMREF dans JDD")
							err = true
						}
			
						
						if (!JDDKW.isNULL(myJDD.getData('DT_DATDEB'))) {
						
					        KW.doubleClick(myJDD,'td_DateDebut')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
							KW.setDate(myJDD,'DT_DATDEB')
						}
				
						if (!JDDKW.isNULL(myJDD.getData('DT_DATFIN'))) {
							
					        KW.click(myJDD,'SelectionEmplacement_td')
					        KW.doubleClick(myJDD,'td_DateFin')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
					        KW.setDate(myJDD,'DT_DATFIN')
							KW.click(myJDD,'ID_NUMREF')
						}
					}else {
						err=true
					}
				}else {
					err=true
				}
			}else {
				err=true
			}
	    }// fin du for
	
		TNRResult.addSTEPACTION('CONTROLE')
	
		if (!err) {
			'Vérification des valeurs en BD'
			SQL.checkJDDWithBD(myJDD)			
		}else {
			TNRResult.addSTEPFAIL("Impossible d'effectuer le contrôle")
		}
		
		TNRResult.addEndTestCase()

} // fin du if



