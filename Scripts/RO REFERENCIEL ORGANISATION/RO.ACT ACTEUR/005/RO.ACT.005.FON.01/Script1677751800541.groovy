import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.KW
import my.NAV



'Lecture du JDD'
def myJDD = new my.JDD()
		
		
'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getData('SOURCE'))

	'Click sur le menu Copy'
	KW.scrollAndClick(myJDD.makeTO('a_Copy'))
	
	"Vérifier l'écran"
	NAV.verifierCartridge('Copy')
	
		
    KW.scrollAndSetText(myJDD.makeTO('input_ID_COD'), myJDD.getStrData('ID_CODINT'))

    KW.scrollAndSetText(myJDD.makeTO('input_ST_NOMNEW'), myJDD.getStrData('ST_NOM'))

    KW.scrollAndSetText(myJDD.makeTO('input_ST_PRENEW'), myJDD.getStrData('ST_PRE'))
			
	
	my.Log.addSTEPGRP('VALIDATION')
		
    'Validation de la saisie'
    KW.scrollAndClick(myJDD.makeTO('button_Valider'))

    'Vérification du test case - écran résulat'
    NAV.verifierEcranRUD(myJDD.getData('ID_CODINT'))
	
	'Vérification des valeurs en BD'
	my.SQL.checkJDDWithBD(myJDD)

} // fin du if



