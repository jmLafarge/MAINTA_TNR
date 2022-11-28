import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

'A supprimer : pour tester seul'
if (GlobalVariable.TC_CONNEXION) {
    WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SECURITE/AD.SEC.001.FON.01 Ouvrir session'), [:])
}


'Init Variable'

String JDDpath = 'RO/ACTEUR/'
String JDDname = 'RO.ACT.001'

String FCTCODE = 'acteur'

def JDD_list

Map JDD = [:]

String organisation = ''





KeywordUtil.logInfo('---------- Début de création ----------')

'Lecture du JDD'
JDD_list = CustomKeywords.'my.JDD.getValuesOf'( JDDpath , JDDname, 'CRE.01')

println(JDD_list)

'Si il y a un test case'
if (JDD_list.size() > 0) {
	
    'Récupération du JDD du test case numéro 01'
    JDD = (JDD_list[1])

    'Naviguer vers la bonne url et controle des infos du cartouche'
    CustomKeywords.'my.Fct_url_ecran.goToURL_Creation_and_checkCartridge'(FCTCODE)

    'Initialisation des variables propre à ce Test case'
    organisation = JDD.ID_CODGES.padRight(6, '.')

    'Début de sasie des valeurs du JDD dans l\'écran'
    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Acteur_ID_CODINT'), 
        JDD.ID_CODINT)

    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Nom_ST_NOM'), 
        JDD.ST_NOM)

    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Prenom_ST_PRE'), 
        JDD.ST_PRE)

    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_E-Mail_ST_MAIL'), 
        JDD.ST_MAIL)

    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Telephone_ST_TELPHO'), 
        JDD.ST_TELPHO)

    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Mobile_ST_TELMOB'), 
        JDD.ST_TELMOB)

    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Fax_ST_TELCOP'), 
        JDD.ST_TELCOP)

    'Cas particulier de Organisation - sélection par recherche'
    CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/i_Organisation_icon'))

    WebUI.switchToWindowIndex('1')

    WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Recherche_Organisation/input_filtre_Organisation'), 
        organisation)

    CustomKeywords.'my.extend_KW.scrollWaitAndVerifyText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Recherche_Organisation/td_Organisation', 
            [('textORG') : organisation]), organisation, GlobalVariable.TIMEOUT)

    WebUI.click(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Recherche_Organisation/td_Organisation', [('textORG') : organisation]))

    WebUI.switchToWindowIndex('0')

    'Validation de la saisie'
    CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/button_Valider'))

    'Vérification du test case - écran'
    CustomKeywords.'my.Fct_url_ecran.verifierEcranResultat'(FCTCODE)

    WebUI.verifyElementText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Résultat/a_ID'), JDD.ID_CODINT)

    'Vérification du test case - Controle des valeurs du JDD en BD'
    CustomKeywords.'my.MSSQL.verifJDD_enBD'(FCTCODE, 'ID_CODINT', JDD.ID_CODINT, [('ST_NOM') : JDD.ST_NOM, ('ST_PRE') : JDD.ST_PRE
            , ('ST_MAIL') : JDD.ST_MAIL, ('ST_TELPHO') : JDD.ST_TELPHO, ('ST_TELMOB') : JDD.ST_TELMOB, ('ST_TELCOP') : JDD.ST_TELCOP
            , ('ID_CODGES') : organisation])

} // fin du if

KeywordUtil.logInfo('---------- Fin de création ----------')






KeywordUtil.logInfo('---------- Début de lecture ----------')

'Lecture du JDD'
JDD_list = CustomKeywords.'my.JDD.getValuesOf'( JDDpath , JDDname, 'LEC.01')

'Si il y a un test case'
if (JDD_list.size() > 0) {
	
	'Récupération du JDD du test case numéro 01'
    JDD = (JDD_list[1])

	'Naviguer vers la bonne url et controle des infos du cartouche'
	CustomKeywords.'my.Fct_url_ecran.goToURL_RUD_and_checkCartridge'(FCTCODE, JDD.ID_CODINT)
	
	'Initialisation des variables propre à ce Test case'
    organisation = JDD.ID_CODGES.padRight(6, '.')
	
	'Début de sasie des valeurs du JDD dans l\'écran'
    WebUI.verifyElementAttributeValue(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Acteur_ID_CODINT'), 
        'Value', JDD.ID_CODINT, GlobalVariable.TIMEOUT)

    WebUI.verifyElementAttributeValue(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Nom_ST_NOM'), 
        'Value', JDD.ST_NOM, GlobalVariable.TIMEOUT)

    WebUI.verifyElementAttributeValue(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Prenom_ST_PRE'), 
        'Value', JDD.ST_PRE, GlobalVariable.TIMEOUT)

    WebUI.verifyElementAttributeValue(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_E-Mail_ST_MAIL'), 
        'Value', JDD.ST_MAIL, GlobalVariable.TIMEOUT)

    WebUI.verifyElementAttributeValue(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Telephone_ST_TELPHO'), 
        'Value', JDD.ST_TELPHO, GlobalVariable.TIMEOUT)

    WebUI.verifyElementAttributeValue(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Mobile_ST_TELMOB'), 
        'Value', JDD.ST_TELMOB, GlobalVariable.TIMEOUT)

    WebUI.verifyElementAttributeValue(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Fax_ST_TELCOP'), 
        'Value', JDD.ST_TELCOP, GlobalVariable.TIMEOUT)

    WebUI.verifyElementAttributeValue(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Organisation_ID_CODGES'), 
        'Value', organisation, GlobalVariable.TIMEOUT)

	'Vérification du test case - Controle des valeurs du JDD en BD'
    CustomKeywords.'my.MSSQL.verifJDD_enBD'(FCTCODE, 'ID_CODINT', JDD.ID_CODINT, [('ST_NOM') : JDD.ST_NOM, ('ST_PRE') : JDD.ST_PRE
            , ('ST_MAIL') : JDD.ST_MAIL, ('ST_TELPHO') : JDD.ST_TELPHO, ('ST_TELMOB') : JDD.ST_TELMOB, ('ST_TELCOP') : JDD.ST_TELCOP
            , ('ID_CODGES') : organisation])
	
} // fin du if

KeywordUtil.logInfo('---------- Fin de lecture ----------')





KeywordUtil.logInfo('---------- Début de modification ----------')


'Lecture du JDD'
JDD_list = CustomKeywords.'my.JDD.getValuesOf'( JDDpath , JDDname, 'MAJ.01')

println(JDD_list)

'Si il y a un test case'
if (JDD_list.size() > 0) {
	
	'Récupération du JDD du test case numéro 01'
    JDD = (JDD_list[1])

	'Naviguer vers la bonne url et controle des infos du cartouche'
    CustomKeywords.'my.Fct_url_ecran.goToURL_RUD_and_checkCartridge'(FCTCODE, JDD.ID_CODINT)
	
	'Initialisation des variables propre à ce Test case'
	organisation = JDD.ID_CODGES.padRight(6, '.')

	'Début de sasie des valeurs du JDD dans l\'écran'
    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Nom_ST_NOM'), 
        JDD.ST_NOM)

    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Prenom_ST_PRE'), 
        JDD.ST_PRE)

    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_E-Mail_ST_MAIL'), 
        JDD.ST_MAIL)

    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Telephone_ST_TELPHO'), 
        JDD.ST_TELPHO)

    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Mobile_ST_TELMOB'), 
        JDD.ST_TELMOB)

    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Fax_ST_TELCOP'), 
        JDD.ST_TELCOP)

    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Organisation_ID_CODGES'), 
        organisation)

    'key TAB pour déclencher le remplissage de la description de Organisation'
    WebUI.sendKeys(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Organisation_ID_CODGES'), Keys.chord(
            Keys.TAB))

    WebUI.delay(1)

    WebUI.verifyElementAttributeValue(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/input_Organisation_ST_DESGES'), 
        'Value', JDD.ST_DESGES, GlobalVariable.TIMEOUT)
	
	'Validation de la saisie'
    CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/button_Valider'))
	
	'Vérification du test case - écran'
    CustomKeywords.'my.Fct_url_ecran.verifierEcranResultat'(FCTCODE)

    WebUI.verifyElementText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Résultat/a_ID'), JDD.ID_CODINT)

	'Vérification du test case - Controle des valeurs du JDD en BD'
    CustomKeywords.'my.MSSQL.verifJDD_enBD'(FCTCODE, 'ID_CODINT', JDD.ID_CODINT, [('ST_NOM') : JDD.ST_NOM, ('ST_PRE') : JDD.ST_PRE
            , ('ST_MAIL') : JDD.ST_MAIL, ('ST_TELPHO') : JDD.ST_TELPHO, ('ST_TELMOB') : JDD.ST_TELMOB, ('ST_TELCOP') : JDD.ST_TELCOP
            , ('ID_CODGES') : organisation])
	
} // fin du if

KeywordUtil.logInfo('---------- Fin de MODIFICATION ----------')





KeywordUtil.logInfo('---------- Début de RECHERCHE ----------')

'Lecture du JDD'
JDD_list = CustomKeywords.'my.JDD.getValuesOf'( JDDpath , JDDname, 'REC.01')

println(JDD_list)

'Si il y a un test case'
if (JDD_list.size() > 0) {
	
	'Récupération du JDD du test case numéro 01'
    JDD = (JDD_list[1])

	'Naviguer vers la bonne url et controle des infos du cartouche'
    CustomKeywords.'my.Fct_url_ecran.goToURL_Grille_and_checkCartridge'(FCTCODE)
	
	'Début de sasie des valeurs du JDD dans l\'écran'
    CustomKeywords.'my.extend_KW.scrollAndSetText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Grille/input_filtre_Acteur'), 
        JDD.ID_CODINT)

	'Vérification des valeurs'
    CustomKeywords.'my.extend_KW.waitAndVerifyText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Grille/td_Acteur', 
            [('textACT') : JDD.ID_CODINT]), JDD.ID_CODINT, GlobalVariable.TIMEOUT)

} // fin du if

KeywordUtil.logInfo('---------- Fin de RECHERCHE ----------')





KeywordUtil.logInfo('---------- Début de SUPPRESION ----------')

'Lecture du JDD'
JDD_list = CustomKeywords.'my.JDD.getValuesOf'( JDDpath , JDDname, 'SUP.01')

println(JDD_list)

'Si il y a un test case'
if (JDD_list.size() > 0) {
	
	'Récupération du JDD du test case numéro 01'
    JDD = (JDD_list[1])

	'Naviguer vers la bonne url et controle des infos du cartouche'
    CustomKeywords.'my.Fct_url_ecran.goToURL_RUD_and_checkCartridge'(FCTCODE, JDD.ID_CODINT)

    'Suppression'
    WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/button_Supprimer'), GlobalVariable.TIMEOUT)

    WebUI.click(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/button_Supprimer'))
	
	//
	// de temps en temps l'alert s'ouvre furtivement sans avoir le temps d'Accepter ! 
	//
	// --> Peut être dù au fait qu'il y ait d'autres webDrivers qui tournent
	//
	// > > > > >  ne pas oublier de faire  >Tools > Web > Terminate running WebDrivers
	//
	
	'Gestion du message Alert'
    if (WebUI.waitForAlert(GlobalVariable.TIMEOUT)) {
		
        WebUI.acceptAlert()

		'Vérification du test case - écran'
        CustomKeywords.'my.Fct_url_ecran.verifierEcranGrille'(FCTCODE)

		'Vérification du test case - Controle des valeurs du JDD en BD'
        CustomKeywords.'my.MSSQL.verifSupprID_enBD'(FCTCODE, 'ID_CODINT', JDD.ID_CODINT)
		
    } else {
        KeywordUtil.logInfo('***************             PAS DE FENETRE ALERT           *************')
    }
	
} // fin du if

KeywordUtil.logInfo('---------- Fin de SUPPRESION ----------')




'A supprimer : pour tester seul'
if (GlobalVariable.TC_CONNEXION) {
    WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SECURITE/AD.SEC.002.FON.01 Fermer session'), [:])
}

