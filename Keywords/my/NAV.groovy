package my



import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import my.JDD


@CompileStatic
class NAV {

	// Naviguer vers les URL et controler les écrans

	/**
	 * Permet de calculer l'url pour acceder directement au SCRUD des objets
	 * et de vérifier l'écran correspondant
	 */



	public static JDD myGlobalJDD = null

	public static loadJDDGLOBAL() {

		Log.addDEBUG('loadJDDGLOBAL()')
		if (!myGlobalJDD) {
			myGlobalJDD = new JDD(my.PropertiesReader.getMyProperty('JDD_PATH') + File.separator + my.PropertiesReader.getMyProperty('JDD_GLOBALFILENAME'),null,null,false)
		}
		Log.addDEBUG(myGlobalJDD.xpathTO.toString())
	}
	

	/**
	 * Vérifier écran 
	 */
	public static verifierCartridge(String txt, int timeOut = GlobalVariable.TIMEOUT) {
		WebUI.scrollToPosition(0, 0)
		KW.delay(1)
		KW.click(myGlobalJDD,'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', txt,timeOut,'WARNING')
	} // end of def


	/**
	 * Vérifier écran Grille / recherche
	 */
	public static verifierEcranGrille(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		if (fct=='') { fct = getFctFromModObj() }
		String code = "E" + fct
		WebUI.scrollToPosition(0, 0)
		KW.delay(1)
		KW.click(myGlobalJDD, 'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', 'E'+ fct, timeOut,'WARNING')
	} // end of def

	/**
	 * Vérifier écran Résultat
	 */
	public static verifierEcranResultat(String val,String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		if (!fct) fct = getFctFromModObj()
		WebUI.scrollToPosition(0, 0)
		KW.delay(1)
		KW.click(myGlobalJDD, 'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', fct,timeOut,'WARNING')
		KW.waitAndVerifyElementText(myGlobalJDD, 'Resultat_ID', val,timeOut)

	} // end of def

	/**
	 * Vérifier écran Création
	 */
	public static verifierEcranCreation(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		if (fct=='') { fct = getFctFromModObj() }
		String code = fct + " - Création"
		WebUI.scrollToPosition(0, 0)
		KW.delay(1)
		KW.click(myGlobalJDD, 'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', code,timeOut,'WARNING')
	} // end of def

	/**
	 * Vérifier écran Lecture / modification / suppression
	 */
	public static verifierEcranRUD(String id, String fct='' , int timeOut = GlobalVariable.TIMEOUT) {
		if (fct=='') { fct = getFctFromModObj() }
		String code = fct + " - Consultation ou modification"
		WebUI.scrollToPosition(0, 0)
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Selection_ID', id,timeOut,'WARNING')
		KW.click(myGlobalJDD, 'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', code,timeOut,'WARNING')
	} // end of def







	/**
	 * Aller à l'url de la Grille / recherche
	 */
	private static goToURL_Grille(String fct) {
		String url = GlobalVariable.BASE_URL.toString() + "E" + fct + "?"
		KW.navigateToUrl(url,'Grille')
	} // end of def


	/**
	 * Aller à l'url de création
	 */
	private static goToURL_Creation(String fct) {
		String url = GlobalVariable.BASE_URL.toString() + "FormE" + fct + "?"
		KW.navigateToUrl(url,'Création')
	} // end of def

	/**
	 * Aller à l'url de Lecture / modification / suppression
	 */
	private static goToURL_RUD(String fct, String id) {
		String url = GlobalVariable.BASE_URL.toString() + "FormE" + fct + "?" + "ID1=" + id
		KW.navigateToUrl(url,'Consultation ou modification')
	} // end of def





	/**
	 * Aller à l'url de Lecture / modification / suppression et vérifier le cartouche
	 */
	public static goToURL_RUD_and_checkCartridge(String id, String fct='' , int timeOut = GlobalVariable.TIMEOUT) {
		if (fct=='') { fct = getFctFromModObj() }
		Log.addDEBUG("NAV.goToURL_RUD_and_checkCartridge(id='$id', fct='$fct')")
		goToURL_RUD(fct, id)
		verifierEcranRUD(id, fct, timeOut)
	} // end of def

	/**
	 * Aller à l'url de Grille / recherche et vérifier le cartouche
	 */
	public static goToURL_Grille_and_checkCartridge(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		if (fct=='') { fct = getFctFromModObj() }
		Log.addDEBUG("NAV.goToURL_Grille_and_checkCartridge(fct='$fct')")
		goToURL_Grille(fct)
		verifierEcranGrille(fct,timeOut)
	} // end of def

	/**
	 * Aller à l'url de création et vérifier le cartouche
	 */
	public static goToURL_Creation_and_checkCartridge(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		if (fct=='') { fct = getFctFromModObj() }
		Log.addDEBUG("NAV.goToURL_Creation_and_checkCartridge(fct='$fct')")
		goToURL_Creation(fct)
		verifierEcranCreation(fct,timeOut)
	} // end of def



	private static String getFctFromModObj() {

		return my.PropertiesReader.getMyProperty('CODESCREEN_' + Tools.getMobObj(GlobalVariable.CASDETESTENCOURS.toString()))
	}

} // end of class