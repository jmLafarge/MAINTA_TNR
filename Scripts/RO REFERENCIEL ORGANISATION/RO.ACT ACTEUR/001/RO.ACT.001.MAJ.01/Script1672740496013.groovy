import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.KW
import my.NAV




'Lecture du JDD'
def myJDD = new my.JDD()


'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0 ) {
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getData('ID_CODINT'))

	'Début de sasie des valeurs du JDD dans l\'écran'
	
    KW.scrollAndSetText(myJDD.makeTO('ST_NOM'), myJDD.getData('ST_NOM'))

    KW.scrollAndSetText(myJDD.makeTO('ST_PRE'), myJDD.getData('ST_PRE'))

    KW.scrollAndSetText(myJDD.makeTO('ST_MAIL'), myJDD.getData('ST_MAIL'))

    KW.scrollAndSetText(myJDD.makeTO('ST_TELPHO'), myJDD.getData('ST_TELPHO'))

    KW.scrollAndSetText(myJDD.makeTO('ST_TELMOB'), myJDD.getData('ST_TELMOB'))

    KW.scrollAndSetText(myJDD.makeTO('ST_TELCOP'), myJDD.getData('ST_TELCOP'))


	'Cas particulier de Organisation - sélection par recherche'
	KW.scrollAndClick(myJDD.makeTO('a_Organisation'))

	WebUI.switchToWindowIndex('1')

	KW.setText(myJDD.makeTO('input_Recherche_Organisation'), myJDD.getData('ID_CODGES'))
	

	'mise à jour dynamique du xpath'	
	KW.scrollWaitAndVerifyText(myJDD.makeTO('td_Recherche_Organisation'), myJDD.getData('ID_CODGES'))
	
	WebUI.click(myJDD.makeTO('td_Recherche_Organisation'))

	WebUI.switchToWindowIndex('0')
	
	
    'Validation de la saisie'
    KW.scrollAndClick(myJDD.makeTO('button_Valider'))

    'Vérification du test case - écran résulat'
    NAV.verifierEcranResultat()

    KW.verifyElementText(myJDD.makeTO('a_Resultat_ID'), myJDD.getData('ID_CODINT'))

	'Vérification des valeurs en BD'
	my.SQL.checkJDDWithBD(myJDD)
	
} // fin du if


