import org.openqa.selenium.Keys

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.NAV




'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	NAV.goToURL_RUD_and_checkCartridge(myJDD.getStrData('ID_CODINT'))
	
	my.Log.addSTEPGRP('ONGLET ZONE')
	
		'Clic sur le bon onglet'
		KW.scrollAndClick(myJDD,'a_Zone')
		
		'Vérification de l\'onglet'
		KW.waitForElementVisible(myJDD,'a_ZoneSelected')
		
		'Boucle sur les lignes d\'un même TC'
	    for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
			
			if (myJDD.getNbrLigneCasDeTest()>1) {
				my.Log.addSTEPLOOP("Modification $i / " + myJDD.getNbrLigneCasDeTest())
			}
			
			myJDD.setCasDeTestNum(i)
	
	        KW.waitAndVerifyElementText(myJDD,'ID_NUMREF')
			
			def etat_ST_DEF = KW.getCheckBoxImgStatus(myJDD,'ST_DEF')
			
			if (etat_ST_DEF != null) {
				if (myJDD.getStrData('ST_DEF')=='O' && !etat_ST_DEF) {
					'Mettre par défaut'
					for ( n in 1..3) {
						my.Log.addSTEP("Tentative pour cocher la valeur par défaut $n/3" )
						KW.scrollAndClick(myJDD,'ST_DEF')
						if (KW.waitAndAcceptAlert(GlobalVariable.TIMEOUT,'WARNING')) {
							KW.delay(1)
							KW.verifyCheckBoxImgChecked(myJDD,'ST_DEF')
						}
					}
				}else if (myJDD.getStrData('ST_DEF')=='O' && etat_ST_DEF) {
					my.Log.addSTEPPASS("La case à cocher (img) 'ST_DEF' est déjà cochée" )
				}else if (myJDD.getStrData('ST_DEF')=='N' && !etat_ST_DEF) {
					my.Log.addSTEPPASS("La case à cocher (img) 'ST_DEF' est déjà décochée" )
				}else if (myJDD.getStrData('ST_DEF')=='N' && etat_ST_DEF) {
					my.Log.addSTEP("La case à cocher (img) 'ST_DEF' est cochée" )
				}else {
					my.Log.addERROR("Erreur inatendu sur la case à cocher (img) 'ST_DEF', vérifier la valeur : " + myJDD.getStrData('ST_DEF') )
				}
				
			}
			
	        KW.scrollAndDoubleClick(myJDD,'td_DateDebut')
			//KW.scrollAndClick(myJDD,'td_DateDebut'))
			//KW.sendKeys(myJDD,'td_DateDebut'), Keys.chord(Keys.F2),"Envoie de la touche F2 pour saisir la date")
			
			KW.waitForElementVisible(myJDD,'DT_DATDEB')
	
	        KW.setDate(myJDD,'DT_DATDEB')
			KW.sendKeys(myJDD,'DT_DATDEB', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")
	
	        KW.scrollAndDoubleClick(myJDD,'td_DateFin')
			//KW.scrollAndClick(myJDD,'td_DateFin'))
			//KW.sendKeys(myJDD,'td_DateFin'), Keys.chord(Keys.F2),"Envoie de la touche F2 pour saisir la date")
			
			KW.waitForElementVisible(myJDD,'DT_DATFIN')
	
	        KW.setDate(myJDD,'DT_DATFIN')
			KW.sendKeys(myJDD,'DT_DATFIN', Keys.chord(Keys.RETURN),"Envoie de la touche ENTREE pour valider la date")

			
		}// fin du for

	my.Log.addSTEPGRP('CONTROLE')
	
	'Vérification des valeurs en BD'
	my.SQL.checkJDDWithBD(myJDD)	
			
} // fin du if



