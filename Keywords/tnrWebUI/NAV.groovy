package tnrWebUI



import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrCommon.TNRPropertiesReader
import tnrCommon.Tools
import tnrJDDManager.JDD
import tnrLog.Log


@CompileStatic
class NAV {

	// Naviguer vers les URL et controler les écrans

	private static final String CLASS_FOR_LOG = 'NAV'


	public static JDD myGlobalJDD

	static {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"static",[:])
		myGlobalJDD = new JDD(TNRPropertiesReader.getMyProperty('JDD_PATH') + File.separator + TNRPropertiesReader.getMyProperty('JDDGLOBAL_FILENAME'),'001',null,false)
		Log.addTraceEND(CLASS_FOR_LOG,"static")
	}


	/**
	 * Vérifier écran 
	 */
	public static verifierCartridge(String txt, int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"verifierCartridge",[txt:txt , timeOut:timeOut])
		KW.scrollToPosition(0, 0)
		KW.delay(1)
		KW.click(myGlobalJDD,'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', txt,timeOut,'WARNING')
		Log.addTraceEND(CLASS_FOR_LOG,"verifierCartridge")
	} // end of def


	/**
	 * Vérifier écran Grille / recherche
	 */
	public static verifierEcranGrille(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"verifierEcranGrille",[fct:fct , timeOut:timeOut])
		if (fct=='') { fct = getFctFromModObj() }
		String code = "E" + fct
		KW.scrollToPosition(0, 0)
		KW.delay(1)
		KW.click(myGlobalJDD, 'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', 'E'+ fct, timeOut,'WARNING')
		Log.addTraceEND(CLASS_FOR_LOG,"verifierEcranGrille")
	} // end of def

	/**
	 * Vérifier écran Résultat
	 */
	public static verifierEcranResultat(String val,String fct='', String name='Resultat_ID_a', int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"verifierEcranResultat",[val:val , fct:fct , name:name , timeOut:timeOut])
		if (!fct) fct = getFctFromModObj()
		KW.scrollToPosition(0, 0)
		KW.delay(1)
		KW.click(myGlobalJDD, 'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', fct,timeOut,'WARNING')
		KW.waitAndVerifyElementText(myGlobalJDD,name, val,timeOut)
		Log.addTraceEND(CLASS_FOR_LOG,"verifierEcranResultat")

	} // end of def



	/**
	 * Vérifier écran Création
	 */
	public static verifierEcranCreation(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"verifierEcranCreation",[fct:fct , timeOut:timeOut])
		if (fct=='') { fct = getFctFromModObj() }
		String code = fct + " - Création"
		KW.scrollToPosition(0, 0)
		KW.delay(1)
		KW.click(myGlobalJDD, 'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', code,timeOut,'WARNING')
		Log.addTraceEND(CLASS_FOR_LOG,"verifierEcranCreation")
	} // end of def

	/**
	 * Vérifier écran Lecture / modification / suppression
	 */
	public static verifierEcranRUD(String text, String fct='' , int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"verifierEcranRUD",[ idval:text , fct:fct ,  timeOut:timeOut])
		if (fct=='') { fct = getFctFromModObj() }
		String code = fct + " - Consultation ou modification"
		KW.scrollToPosition(0, 0)
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Selection_ID', text,timeOut,'WARNING')
		KW.click(myGlobalJDD, 'a_Toggle','WARNING')
		KW.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD, 'Fonction_code', code,timeOut,'WARNING')
		Log.addTraceEND(CLASS_FOR_LOG,"verifierEcranRUD")
	} // end of def







	/**
	 * Aller à l'url de la Grille / recherche
	 */
	public static goToURL_Grille(String fct) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_Grille",[fct:fct])
		String url = GlobalVariable.BASE_URL.toString() + "E" + fct + "?"
		KW.navigateToUrl(url,'Grille')
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_Grille")
	} // end of def


	/**
	 * Aller à l'url de création
	 */
	public static goToURL_Creation(String fct,String attr='') {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_Creation",[fct:fct , attr:attr])
		String url = GlobalVariable.BASE_URL.toString() + "FormE" + fct + "?" + attr
		KW.navigateToUrl(url,'Création')
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_Creation")
	} // end of def

	/**
	 * Aller à l'url de Lecture / modification / suppression
	 */
	public static goToURL_RUD(String idval, String fct='') {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_RUD",[idval:idval , fct:fct])
		if (fct=='') { fct = getFctFromModObj() }
		String url = GlobalVariable.BASE_URL.toString() + "FormE" + fct + "?" + "ID1=" + idval
		KW.navigateToUrl(url,'Consultation ou modification')
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_RUD")
	} // end of def





	/**
	 * Aller à l'url de Lecture / modification / suppression et vérifier le cartouche
	 */
	public static goToURL_RUD_and_checkCartridge(String idval, String fct='' , int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_RUD_and_checkCartridge",[id:idval , fct:fct])
		goToURL_RUD(idval,fct)
		verifierEcranRUD(idval, fct, timeOut)
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_RUD_and_checkCartridge")
	} // end of def

	/**
	 * Aller à l'url de Grille / recherche et vérifier le cartouche
	 */
	public static goToURL_Grille_and_checkCartridge(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_Grille_and_checkCartridge",[fct:fct])
		if (fct=='') { fct = getFctFromModObj() }
		goToURL_Grille(fct)
		verifierEcranGrille(fct,timeOut)
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_Grille_and_checkCartridge")
	} // end of def

	/**
	 * Aller à l'url de création et vérifier le cartouche
	 */
	public static goToURL_Creation_and_checkCartridge(String fct='', String attr='',int timeOut = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_Creation_and_checkCartridge",[fct:fct])
		if (fct=='') { fct = getFctFromModObj() }
		goToURL_Creation(fct,attr)
		verifierEcranCreation(fct,timeOut)
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_Creation_and_checkCartridge")
	} // end of def



	private static String getFctFromModObj() {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getFctFromModObj",[:])
		String ret = TNRPropertiesReader.getMyProperty('CODESCREEN_' + Tools.getMobObj(GlobalVariable.CAS_DE_TEST_EN_COURS.toString()))
		Log.addTraceEND(CLASS_FOR_LOG,"getFctFromModObj")
		return ret
	}

} // Fin de class