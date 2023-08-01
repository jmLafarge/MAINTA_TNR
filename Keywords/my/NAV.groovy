package my



import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import my.JDD
import my.Log


@CompileStatic
class NAV {

	// Naviguer vers les URL et controler les écrans

	private static final String CLASS_FORLOG = 'NAV'


	public static JDD myGlobalJDD

	static {
		Log.addTraceBEGIN(CLASS_FORLOG,"static",[:])
		myGlobalJDD = new JDD(my.PropertiesReader.getMyProperty('JDD_PATH') + File.separator + my.PropertiesReader.getMyProperty('JDD_GLOBALFILENAME'),null,null,false)
		Log.addTraceEND(CLASS_FORLOG,"static")
	}


	/**
	 * Vérifier écran 
	 */
	public static verifierCartridge(String txt, int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FORLOG,"verifierCartridge",[txt:txt , timeOut:timeOut])
		WebUI.scrollToPosition(0, 0)
		KW.delay(1)
		KW.click(myGlobalJDD,'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', txt,timeOut,'WARNING')
		Log.addTraceEND(CLASS_FORLOG,"verifierCartridge")
	} // end of def


	/**
	 * Vérifier écran Grille / recherche
	 */
	public static verifierEcranGrille(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FORLOG,"verifierEcranGrille",[fct:fct , timeOut:timeOut])
		if (fct=='') { fct = getFctFromModObj() }
		String code = "E" + fct
		WebUI.scrollToPosition(0, 0)
		KW.delay(1)
		KW.click(myGlobalJDD, 'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', 'E'+ fct, timeOut,'WARNING')
		Log.addTraceEND(CLASS_FORLOG,"verifierEcranGrille")
	} // end of def

	/**
	 * Vérifier écran Résultat
	 */
	public static verifierEcranResultat(String val,String fct='', String name='Resultat_ID_a', int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FORLOG,"verifierEcranResultat",[val:val , fct:fct , name:name , timeOut:timeOut])
		if (!fct) fct = getFctFromModObj()
		WebUI.scrollToPosition(0, 0)
		KW.delay(1)
		KW.click(myGlobalJDD, 'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', fct,timeOut,'WARNING')
		KW.waitAndVerifyElementText(myGlobalJDD,name, val,timeOut)
		Log.addTraceEND(CLASS_FORLOG,"verifierEcranResultat")

	} // end of def
	
	

	/**
	 * Vérifier écran Création
	 */
	public static verifierEcranCreation(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FORLOG,"verifierEcranCreation",[fct:fct , timeOut:timeOut])
		if (fct=='') { fct = getFctFromModObj() }
		String code = fct + " - Création"
		WebUI.scrollToPosition(0, 0)
		KW.delay(1)
		KW.click(myGlobalJDD, 'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', code,timeOut,'WARNING')
		Log.addTraceEND(CLASS_FORLOG,"verifierEcranCreation")
	} // end of def

	/**
	 * Vérifier écran Lecture / modification / suppression
	 */
	public static verifierEcranRUD(String text, String fct='' , int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FORLOG,"verifierEcranRUD",[ idval:text , fct:fct ,  timeOut:timeOut])
		if (fct=='') { fct = getFctFromModObj() }
		String code = fct + " - Consultation ou modification"
		WebUI.scrollToPosition(0, 0)
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Selection_ID', text,timeOut,'WARNING')
		KW.click(myGlobalJDD, 'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', code,timeOut,'WARNING')
		Log.addTraceEND(CLASS_FORLOG,"verifierEcranRUD")
	} // end of def







	/**
	 * Aller à l'url de la Grille / recherche
	 */
	public static goToURL_Grille(String fct) {
		Log.addTraceBEGIN(CLASS_FORLOG,"goToURL_Grille",[fct:fct])
		String url = GlobalVariable.BASE_URL.toString() + "E" + fct + "?"
		KW.navigateToUrl(url,'Grille')
		Log.addTraceEND(CLASS_FORLOG,"goToURL_Grille")
	} // end of def


	/**
	 * Aller à l'url de création
	 */
	public static goToURL_Creation(String fct,String attr='') {
		Log.addTraceBEGIN(CLASS_FORLOG,"goToURL_Creation",[fct:fct , attr:attr])
		String url = GlobalVariable.BASE_URL.toString() + "FormE" + fct + "?" + attr
		KW.navigateToUrl(url,'Création')
		Log.addTraceEND(CLASS_FORLOG,"goToURL_Creation")
	} // end of def

	/**
	 * Aller à l'url de Lecture / modification / suppression
	 */
	public static goToURL_RUD(String idval, String fct='') {
		Log.addTraceBEGIN(CLASS_FORLOG,"goToURL_RUD",[idval:idval , fct:fct])
		if (fct=='') { fct = getFctFromModObj() }
		String url = GlobalVariable.BASE_URL.toString() + "FormE" + fct + "?" + "ID1=" + idval
		KW.navigateToUrl(url,'Consultation ou modification')
		Log.addTraceEND(CLASS_FORLOG,"goToURL_RUD")
	} // end of def





	/**
	 * Aller à l'url de Lecture / modification / suppression et vérifier le cartouche
	 */
	public static goToURL_RUD_and_checkCartridge(String idval, String fct='' , int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FORLOG,"goToURL_RUD_and_checkCartridge",[id:idval , fct:fct])
		goToURL_RUD(idval,fct)
		verifierEcranRUD(idval, fct, timeOut)
		Log.addTraceEND(CLASS_FORLOG,"goToURL_RUD_and_checkCartridge")
	} // end of def

	/**
	 * Aller à l'url de Grille / recherche et vérifier le cartouche
	 */
	public static goToURL_Grille_and_checkCartridge(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FORLOG,"goToURL_Grille_and_checkCartridge",[fct:fct])
		if (fct=='') { fct = getFctFromModObj() }
		goToURL_Grille(fct)
		verifierEcranGrille(fct,timeOut)
		Log.addTraceEND(CLASS_FORLOG,"goToURL_Grille_and_checkCartridge")
	} // end of def

	/**
	 * Aller à l'url de création et vérifier le cartouche
	 */
	public static goToURL_Creation_and_checkCartridge(String fct='', String attr='',int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FORLOG,"goToURL_Creation_and_checkCartridge",[fct:fct])
		if (fct=='') { fct = getFctFromModObj() }
		goToURL_Creation(fct,attr)
		verifierEcranCreation(fct,timeOut)
		Log.addTraceEND(CLASS_FORLOG,"goToURL_Creation_and_checkCartridge")
	} // end of def



	private static String getFctFromModObj() {
		Log.addTraceBEGIN(CLASS_FORLOG,"getFctFromModObj",[:])
		String ret = my.PropertiesReader.getMyProperty('CODESCREEN_' + Tools.getMobObj(GlobalVariable.CASDETESTENCOURS.toString()))
		Log.addTraceEND(CLASS_FORLOG,"getFctFromModObj")
		return ret
	}

} // end of class