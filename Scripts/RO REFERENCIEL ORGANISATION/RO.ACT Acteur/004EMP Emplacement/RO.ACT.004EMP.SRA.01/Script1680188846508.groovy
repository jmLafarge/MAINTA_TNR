import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDKW
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
    STEP.goToURLReadUpdateDelete(1,myJDD.getStrData('ID_CODINT'));STEP.checkReadUpdateDeleteScreen(2,myJDD.getStrData('ID_CODINT'))
	
	TNRResult.addSTEPGRP('ONGLET ZONE')
	
		//STEP.simpleClick(0, myJDD,"tab_Zone")
		STEP.simpleClick(0, myJDD,"tab_Zone")
		STEP.verifyElementVisible(0, myJDD,"tab_ZoneSelected")
		
		STEP.scrollToPosition('', 0, 0)

		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Ajout $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
			
			'Ajout'
			STEP.simpleClick(0, myJDD,'a_AjouterEmplacement')
	
			if (STEP.isDivModalOpened(0)) {

		        STEP.setText(0, myJDD,'SelectionEmplacement_input_Filtre', myJDD.getStrData('ID_NUMREF'))
				if (STEP.verifyElementVisible(10, GlobalJDD.myGlobalJDD,'nbrecordsGRID_1')) {
			        STEP.simpleClick(0, myJDD,'SelectionEmplacement_td')
			        STEP.simpleClick(0, myJDD,'SelectionEmplacement_button_Ajouter')
					STEP.simpleClick(0, myJDD,'SelectionEmplacement_button_Fermer')
					
					if (STEP.isDivModalClosed(0)) {
			
				        if (STEP.verifyText(0, myJDD,'ID_NUMREF')) {
							myJDD.replaceSEQUENCIDInJDD('ID_NUMZONLIG')
						}else {
							TNRResult.addDETAIL("Impossible de remplacer SEQUENCEID par ID_NUMREF dans JDD")
							err = true
						}
			
						
						if (!JDDKW.isNULL(myJDD.getData('DT_DATDEB'))) {
						
					        STEP.doubleClick(0, myJDD,'td_DateDebut')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
							STEP.setDate(0, myJDD,'DT_DATDEB')
						}
				
						if (!JDDKW.isNULL(myJDD.getData('DT_DATFIN'))) {
							
					        STEP.simpleClick(0, myJDD,'SelectionEmplacement_td')
					        STEP.doubleClick(0, myJDD,'td_DateFin')
							//
							// Le double Clic ne fonctionne pas sur Firefox --> F2 non plus :-(
							//
					        STEP.setDate(0, myJDD,'DT_DATFIN')
							STEP.simpleClick(0, myJDD,'ID_NUMREF')
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
			STEP.checkJDDWithBD(0, myJDD)			
		}else {
			TNRResult.addSTEPFAIL("Impossible d'effectuer le contrôle")
		}
		
		TNRResult.addEndTestCase()

} // fin du if



