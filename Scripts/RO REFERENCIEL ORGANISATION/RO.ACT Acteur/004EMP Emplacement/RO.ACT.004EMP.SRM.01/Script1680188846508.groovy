import org.openqa.selenium.Keys

import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrLog.Log
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
	
	TNRResult.addSTEPGRP('ONGLET ZONE')
	
		//KW.click(myJDD,"tab_Zone")
		KW.click(myJDD,"tab_Zone")
		KW.isElementVisible(myJDD,"tab_ZoneSelected")
		
		KW.scrollToPositionAndWait(0, 0)
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				TNRResult.addSTEPLOOP("Modification $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
	        if (KW.verifyText(myJDD,'ID_NUMREF')) {
				KW.verifyText(myJDD, 'ID_NUMREF')
			
				def etat_ST_DEF = KWCheckbox.getCheckBoxImgStatus(myJDD,'ST_DEF')
				
				if (etat_ST_DEF != null) {
					if (myJDD.getStrData('ST_DEF')=='O' && !etat_ST_DEF) {
						'Mettre par défaut'
						for ( n in 1..3) {
							TNRResult.addSTEP("Tentative pour cocher la valeur par défaut $n/3" )
							KW.click(myJDD,'ST_DEF')
							if (KW.waitAndAcceptAlert(GlobalVariable.TIMEOUT,null)) {
								KW.delay(1)
								KWCheckbox.verifyCheckBoxImgChecked(myJDD,'ST_DEF')
							}
						}
					}else if (myJDD.getStrData('ST_DEF')=='O' && etat_ST_DEF) {
						TNRResult.addSTEPPASS("La case à cocher (img) 'ST_DEF' est déjà cochée" )
					}else if (myJDD.getStrData('ST_DEF')=='N' && !etat_ST_DEF) {
						TNRResult.addSTEPPASS("La case à cocher (img) 'ST_DEF' est déjà décochée" )
					}else if (myJDD.getStrData('ST_DEF')=='N' && etat_ST_DEF) {
						TNRResult.addSTEP("La case à cocher (img) 'ST_DEF' est cochée" )
					}else {
						Log.addERROR("Erreur inatendu sur la case à cocher (img) 'ST_DEF', vérifier la valeur : " + myJDD.getStrData('ST_DEF') )
					}
					
				}
				
		        KW.doubleClick(myJDD,'td_DateDebut')
				//KW.click(myJDD,'td_DateDebut'))
				//KW.sendKeys(myJDD,'td_DateDebut'), Keys.chord(Keys.F2),"Envoie de la touche F2 pour saisir la date")
				
				//KW.isElementVisible(myJDD,'DT_DATDEB')
		
		        KW.setDate(myJDD,'DT_DATDEB')
				KW.sendKeys(myJDD,'DT_DATDEB', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
		
		        KW.doubleClick(myJDD,'td_DateFin')
				//KW.click(myJDD,'td_DateFin'))
				//KW.sendKeys(myJDD,'td_DateFin'), Keys.chord(Keys.F2),"Envoie de la touche F2 pour saisir la date")
				
				//KW.isElementVisible(myJDD,'DT_DATFIN')
		
		        KW.setDate(myJDD,'DT_DATFIN')
				KW.sendKeys(myJDD,'DT_DATFIN', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
	        }
			
		}// fin du for

	TNRResult.addSTEPACTION('CONTROLE')
	
	'Vérification des valeurs en BD'
	SQL.checkJDDWithBD(myJDD)	
	
	TNRResult.addEndTestCase()
} // fin du if



