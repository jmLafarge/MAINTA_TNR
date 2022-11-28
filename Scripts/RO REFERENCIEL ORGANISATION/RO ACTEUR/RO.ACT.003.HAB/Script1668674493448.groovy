import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

'pour tester seul --> à supprimer'
if (GlobalVariable.TC_CONNEXION) {
    WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SECURITE/AD.SEC.001.FON.01 Ouvrir session'), [:])
}


/*
 * 
 * 
 * PAS FINI maque le controle en base et la gestion des valeurs du JDD : vide (on fait rien sur cette valeur) ou $VIDE (ça doit être vide)
 * 
 */

'Init Variable'
String JDDpath = 'RO/ACTEUR/'
String JDDname = 'RO.ACT.003.HAB'


String FCTCODE = 'acteur'

def JDD_list

Map JDD = [:]



KeywordUtil.logInfo('---------- Début de AJOUT ----------')

'Lecture du JDD'
JDD_list = CustomKeywords.'my.JDD.getValuesOf'( JDDpath , JDDname, 'AJT.01')

println(JDD_list)

'Si il y a un test case'
if (JDD_list.size() > 0) {
	
	
    JDD = (JDD_list[1])
	println JDD
	
	// Navigation vers la bonne URL

	'Naviguer vers la bonne url et controle des infos du cartouche'
    CustomKeywords.'my.Fct_url_ecran.goToURL_RUD_and_checkCartridge'(FCTCODE, JDD.ID_CODINT)
	
	'Clic sur le bon onglet'
    CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/a_Habilitation'))
	
	'Vérification de l\'onglet'
    CustomKeywords.'my.extend_KW.waitAndVerifyText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/h4_Habilitation'), 'HABILITATION',GlobalVariable.TIMEOUT)

	'Boucle sur les lignes d\'un même TC'
    for (int i : (1..JDD_list.size())) {
		
		'Lectrure du JDD (i)'
        JDD = (JDD_list[i])

		'Ajout'
        CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/a_Ajouter'))

        WebUI.delay(1)

        WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/Window_Recherche_Habilitation/input_Filtre_Habilitation'), 
            JDD.ID_CODHAB)

        WebUI.delay(1)

        CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/Window_Recherche_Habilitation/td_Habilitation', 
                [('textHAB') : JDD.ID_CODHAB]))

        CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/Window_Recherche_Habilitation/button_Ajouter'))

        WebUI.verifyElementPresent(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Habilitation', [('textHAB') : JDD.ID_CODHAB]),GlobalVariable.TIMEOUT)

        CustomKeywords.'my.extend_KW.scrollAndDoubleClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Debut_Habilitation', 
                [('textHAB') : JDD.ID_CODHAB]))

        WebUI.delay(1)
		
		//
		// Le double Clic ne fonctionne pas sur Firefox --> voir pour le remplacer par Sélection puis F2
		//
		//

        WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/textarea_Debut_Habilitation', [('textHAB') : JDD.ID_CODHAB]), 
            JDD.DT_DATDEB)

        CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Habilitation', 
                [('textHAB') : JDD.ID_CODHAB]))

        CustomKeywords.'my.extend_KW.scrollAndDoubleClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Fin_Habilitation', 
                [('textHAB') : JDD.ID_CODHAB]))

        //WebUI.doubleClick(findTestObject('1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Fin_Habilitation', [('textHAB') : JDD.ID_CODHAB]))
        //WebUI.delay(1)
        WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/textarea_Fin_Habilitation', [('textHAB') : JDD.ID_CODHAB]), 
            JDD.DT_DATFIN)

        CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Habilitation', 
                [('textHAB') : JDD.ID_CODHAB]))
		
    }// fin du for
	
} // fin du if

KeywordUtil.logInfo('---------- Fin de AJOUT ----------')






KeywordUtil.logInfo('---------- Début de MODIFICATION ----------')

'Lecture du JDD'
JDD_list = CustomKeywords.'my.JDD.getValuesOf'( JDDpath , JDDname, 'MAJ.01')

println(JDD_list)

'Si il y a un test case'
if (JDD_list.size() > 0) {
	
	'Récupération du JDD du test case numéro 01'
    JDD = (JDD_list[1])
	
	'Naviguer vers la bonne url et controle des infos du cartouche'
	CustomKeywords.'my.Fct_url_ecran.goToURL_RUD_and_checkCartridge'(FCTCODE, JDD.ID_CODINT)

    'Clic sur le bon onglet'
    CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/a_Habilitation'))

	'Vérification de l\'onglet'
    WebUI.verifyElementText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/h4_Habilitation'), 'HABILITATION')

	'Boucle sur les lignes d\'un même TC'
    for (int i : (1..JDD_list.size())) {
		
		'Lectrure du JDD (i)'
        JDD = (JDD_list[i])

		'Début de sasie des valeurs du JDD dans l\'écran'
        WebUI.verifyElementPresent(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Habilitation', [('textHAB') : JDD.ID_CODHAB]), 
            2)

        CustomKeywords.'my.extend_KW.scrollAndDoubleClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Debut_Habilitation', 
                [('textHAB') : JDD.ID_CODHAB]))

        WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/textarea_Debut_Habilitation', [('textHAB') : JDD.ID_CODHAB]), 
            JDD.DT_DATDEB)

        CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Habilitation', 
                [('textHAB') : JDD.ID_CODHAB]))

        CustomKeywords.'my.extend_KW.scrollAndDoubleClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Fin_Habilitation', 
                [('textHAB') : JDD.ID_CODHAB]))

        WebUI.setText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/textarea_Fin_Habilitation', [('textHAB') : JDD.ID_CODHAB]), 
            JDD.DT_DATFIN)

        CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Habilitation', 
                [('textHAB') : JDD.ID_CODHAB]))
		
		'Vérification du test case - Controle des valeurs du JDD en BD'
		
		// A FAIRE ****************************************************
		
		
    }// fin du for
	
} // fin du if

KeywordUtil.logInfo('---------- Fin de MODIFICATION ----------')






KeywordUtil.logInfo('---------- Début de LECTURE ----------')

'Lecture du JDD'
JDD_list = CustomKeywords.'my.JDD.getValuesOf'( JDDpath , JDDname, 'LEC.01')

println(JDD_list)

'Si il y a un test case'
if (JDD_list.size() > 0) {
	
	'Récupération du JDD du test case numéro 01'
    JDD = (JDD_list[1])

	'Naviguer vers la bonne url et controle des infos du cartouche'
    CustomKeywords.'my.Fct_url_ecran.goToURL_RUD_and_checkCartridge'(FCTCODE, JDD.ID_CODINT)

	'Clic sur le bon onglet'
    CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/a_Habilitation'))

	'Vérification de l\'onglet'
    CustomKeywords.'my.extend_KW.waitAndVerifyText'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/h4_Habilitation'), 'HABILITATION',)

	'Boucle sur les lignes d\'un même TC'
    for (int i : (1..JDD_list.size())) {
		
		'Lectrure du JDD (i)'
        JDD = (JDD_list[i])
		
        WebUI.verifyElementPresent(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Habilitation', [('textHAB') : JDD.ID_CODHAB]), 
            2)

		String dat_hab = WebUI.getText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Debut_Habilitation', [('textHAB') : JDD.ID_CODHAB]))
		
		'je teste Si (le JDD  n est pas vide) ou si (le champ n est pas vide) '
		//
		// A revoir en fonction DU JDD si vide ou $VIDE
		//
		if ( (JDD.DT_DATDEB !='') || (dat_hab.size()>1) ) {
			
			WebUI.verifyElementText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Debut_Habilitation',
				[('textHAB') : JDD.ID_CODHAB]), JDD.DT_DATDEB)
			
		}

		dat_hab = WebUI.getText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Fin_Habilitation', [('textHAB') : JDD.ID_CODHAB]))
		
		if ( (JDD.DT_DATFIN !='') || (dat_hab.size()>1) ) {
			
			WebUI.verifyElementText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Fin_Habilitation', [
                    ('textHAB') : JDD.ID_CODHAB]), JDD.DT_DATFIN)
		}
		
    }// fin du for
	
} // fin du if

KeywordUtil.logInfo('---------- Fin de LECTURE ----------')






KeywordUtil.logInfo('---------- Début de SUPPRESSION ----------')

'Lecture du JDD'
JDD_list = CustomKeywords.'my.JDD.getValuesOf'( JDDpath , JDDname, 'SUP.01')

println(JDD_list)

'Si il y a un test case'
if (JDD_list.size() > 0) {
	
	'Récupération du JDD du test case numéro 01'
    JDD = (JDD_list[1])

	'Naviguer vers la bonne url et controle des infos du cartouche'
    CustomKeywords.'my.Fct_url_ecran.goToURL_RUD_and_checkCartridge'(FCTCODE, JDD.ID_CODINT)

	'Clic sur le bon onglet'
    CustomKeywords.'my.extend_KW.scrollAndClick'(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/a_Habilitation'))
	
	'Vérification de l\'onglet'
	WebUI.verifyElementText(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/h4_Habilitation'), 'HABILITATION')

	'Boucle sur les lignes d\'un même TC'
    for (int i : (1..JDD_list.size())) {
		
		KeywordUtil.logInfo('***************             SUPPRESSION ' + i +' de : ' + JDD.ID_CODHAB + '           *************')
		
		'Lectrure du JDD (i)'
        JDD = (JDD_list[i])
		
		WebUI.delay(1)
		
        'Supression'
		WebUI.scrollToElement(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/span_Supprime_Habilitation',
			        [('textHAB') : JDD.ID_CODHAB]),GlobalVariable.TIMEOUT)
		
		WebUI.click(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/span_Supprime_Habilitation',
			[('textHAB') : JDD.ID_CODHAB]))

		'Gestion du message Alert'
	    if (WebUI.waitForAlert(GlobalVariable.TIMEOUT)) {
			
			WebUI.acceptAlert()
			
			'Vérification du test case - Supression dans le tableau'
	        WebUI.verifyElementNotPresent(findTestObject('Object Repository/1 - RO/ACTEUR/Page_Fiche Acteur/Tab_Habilitation/td_Habilitation', 
	                [('textHAB') : JDD.ID_CODHAB]), 2)
			
			'Vérification du test case - Controle des valeurs du JDD en BD'
			
			// A FAIRE ****************************************************
			
		}else {
			
			// KO
			KeywordUtil.logInfo('***************             PAS DE FENETRE ALERT           *************')
			
		}
    }

} // fin du if

KeywordUtil.logInfo('---------- Fin de SUPPRESSION ----------')



'A supprimer : pour tester seul'
if (GlobalVariable.TC_CONNEXION) {
    WebUI.callTestCase(findTestCase('AD ADMINISTRATION/AD.SECURITE/AD.SEC.002.FON.01 Fermer session'), [:])
}

