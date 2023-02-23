package my



import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable



class NAV {

	// Naviguer vers les URL et controler les écrans

	/**
	 * Permet de calculer l'url pour acceder directement au SCRUD des objets
	 * et de vérifier l'écran correspondant
	 */



	static def myGlobalJDD = null

	static public loadJDDGLOBAL() {

		my.Log.addSubTITLE('Load JDD GLOBAL')
		this.myGlobalJDD = new my.JDD(my.PropertiesReader.getMyProperty('JDD_PATH') + File.separator + my.PropertiesReader.getMyProperty('JDD_GLOBALFILENAME'),'001')

	}




	/**
	 * Vérifier écran Grille / recherche
	 */
	static public verifierEcranGrille(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		if (fct=='') { fct = this.getFctFromModObj() }
		String code = "E" + fct
		WebUI.scrollToPosition(0, 0)
		WebUI.delay(1)
		KW.click(myGlobalJDD.makeTO('a_Toggle'))
		WebUI.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD.makeTO('span_Ecran'), 'E'+ fct, timeOut)
	} // end of def

	/**
	 * Vérifier écran Résultat
	 */
	static public verifierEcranResultat(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		//my.Log.addSTEP("NAV.verifierEcranResultat( fct=$fct)")
		if (fct=='') { fct = this.getFctFromModObj() }
		String code = fct
		WebUI.scrollToPosition(0, 0)
		WebUI.delay(1)
		KW.click(myGlobalJDD.makeTO('a_Toggle'))
		WebUI.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD.makeTO('span_Ecran'), code,timeOut)
	} // end of def

	/**
	 * Vérifier écran Création
	 */
	static public verifierEcranCreation(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		if (fct=='') { fct = this.getFctFromModObj() }
		String code = fct + " - Création"
		WebUI.scrollToPosition(0, 0)
		WebUI.delay(1)
		KW.click(myGlobalJDD.makeTO('a_Toggle'))
		WebUI.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD.makeTO('span_Ecran'), code,timeOut)
	} // end of def

	/**
	 * Vérifier écran Lecture / modification / suppression
	 */
	static public verifierEcranRUD(String id, String fct='' , int timeOut = GlobalVariable.TIMEOUT) {
		if (fct=='') { fct = this.getFctFromModObj() }
		String code = fct + " - Consultation ou modification"
		WebUI.scrollToPosition(0, 0)
		WebUI.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD.makeTO('span_Selection'), id,timeOut)
		KW.click(myGlobalJDD.makeTO('a_Toggle'))
		WebUI.delay(1)
		KW.waitAndVerifyElementText(myGlobalJDD.makeTO('span_Ecran'), code,timeOut)
	} // end of def







	/**
	 * Aller à l'url de la Grille / recherche
	 */
	private static goToURL_Grille(String fct) {
		String url = GlobalVariable.BASE_URL + "E" + fct + "?"
		KW.navigateToUrl(url,'Grille')
	} // end of def


	/**
	 * Aller à l'url de création
	 */
	private static goToURL_Creation(String fct) {
		String url = GlobalVariable.BASE_URL + "FormE" + fct + "?"
		KW.navigateToUrl(url,'Création')
	} // end of def

	/**
	 * Aller à l'url de Lecture / modification / suppression
	 */
	private static goToURL_RUD(String fct, String id) {
		String url = GlobalVariable.BASE_URL + "FormE" + fct + "?" + "ID1=" + id
		KW.navigateToUrl(url,'Consultation ou modification')
	} // end of def





	/**
	 * Aller à l'url de Lecture / modification / suppression et vérifier le cartouche
	 */
	static public goToURL_RUD_and_checkCartridge(String id, String fct='' , int timeOut = GlobalVariable.TIMEOUT) {
		if (fct=='') { fct = this.getFctFromModObj() }
		my.Log.addDEBUG("NAV.goToURL_RUD_and_checkCartridge(id='$id', fct='$fct')")
		this.goToURL_RUD(fct, id)
		this.verifierEcranRUD(id, fct, timeOut)
	} // end of def

	/**
	 * Aller à l'url de Grille / recherche et vérifier le cartouche
	 */
	static public goToURL_Grille_and_checkCartridge(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		if (fct=='') { fct = this.getFctFromModObj() }
		my.Log.addDEBUG("NAV.goToURL_Grille_and_checkCartridge(fct='$fct')")
		this.goToURL_Grille(fct)
		this.verifierEcranGrille(fct,timeOut)
	} // end of def

	/**
	 * Aller à l'url de création et vérifier le cartouche
	 */
	static public goToURL_Creation_and_checkCartridge(String fct='', int timeOut = GlobalVariable.TIMEOUT) {
		if (fct=='') { fct = this.getFctFromModObj() }
		my.Log.addDEBUG("NAV.goToURL_Creation_and_checkCartridge(fct='$fct')")
		this.goToURL_Creation(fct)
		this.verifierEcranCreation(fct,timeOut)
	} // end of def



	private static String getFctFromModObj() {

		return my.PropertiesReader.getMyProperty('CODESCREEN_' + GlobalVariable.CASDETESTENCOURS.find(/^\w+\.\w+/))
	}

} // end of class