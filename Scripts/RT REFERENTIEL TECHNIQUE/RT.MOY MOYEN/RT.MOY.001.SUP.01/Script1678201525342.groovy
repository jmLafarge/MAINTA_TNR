import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.NAV

'Lecture du JDD'
def myJDD = new my.JDD()

'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getData('ID_CODMOY'))
	
	'Suppression'
	for ( n in 1..3) {
		my.Log.addSUBSTEP("Tentative de suppression $n/3" )
		KW.scrollAndClick(myJDD.makeTO('button_Supprimer'))
		if (KW.waitAndAcceptAlert(GlobalVariable.TIMEOUT,'WARNING')) {
			KW.delay(1)
			'Vérification du test case - écran'
			NAV.verifierEcranGrille()
			break
		}
	}
	
	
	'Vérification en BD que l\'objet n\'existe plus'
	my.SQL.checkIDNotInBD(myJDD)

} // fin du if