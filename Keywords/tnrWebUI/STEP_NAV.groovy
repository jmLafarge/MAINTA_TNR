package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrCommon.TNRPropertiesReader
import tnrCommon.Tools
import tnrJDDManager.GlobalJDD
import tnrLog.Log
import tnrResultManager.TNRResult


/**
 * Personalise les actions WebUI
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */

@CompileStatic
public class STEP_NAV {
	
	
	private static final String CLASS_FOR_LOG = 'STEP_NAV'
	
	
	
	protected static void openBrowser(String stepID, String url){
		try {
			WebUI.openBrowser(url, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(stepID,"Ouverture du navigateur à l'URL :")
			TNRResult.addDETAIL(url)
			TNRResult.addBrowserInfo()
			WebUI.waitForPageLoad((int)GlobalVariable.TIMEOUTForPageLoad, FailureHandling.STOP_ON_FAILURE)
			KWWindow.storeMainWindowHandle()
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(stepID,"Ouverture du navigateur à l'URL :")
			TNRResult.addDETAIL(url)
			TNRResult.addDETAIL(ex.getMessage())
		}
	}
	
	
	protected static void navigateToUrl(String stepID, String url,String nomUrl){
		try {
			WebUI.navigateToUrl(url, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(stepID,"Navigation vers l'URL '$nomUrl' :")
			TNRResult.addDETAIL(url)
			WebUI.waitForPageLoad((int)GlobalVariable.TIMEOUTForPageLoad, FailureHandling.STOP_ON_FAILURE)
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(stepID,"Navigation vers l'URL '$nomUrl' :")
			TNRResult.addDETAIL(url)
			TNRResult.addDETAIL(ex.getMessage())
		}
	}
	
	
	protected static void closeBrowser(String stepID){
		try {
			WebUI.closeBrowser(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(stepID,"Fermeture du navigateur")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(stepID,"Fermeture du navigateur")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}
	
	
	protected static void maximizeWindow(String stepID){
		try {
			WebUI.maximizeWindow(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(stepID,"Maximise la fenêtre")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(stepID,"Maximise la fenêtre")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static verifierCartridge(int stepID, String txt, int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"verifierCartridge",[txt:txt , timeout:timeout])
		STEP.scrollToPosition(0, 0)
		STEP.click(0, GlobalJDD.myGlobalJDD,'a_Toggle',timeout,'WARNING')
		//STEP.delay(1)
		STEP.verifyText(0, GlobalJDD.myGlobalJDD, 'Fonction_code', txt,timeout,'WARNING')
		Log.addTraceEND(CLASS_FOR_LOG,"verifierCartridge")
	}





	public static verifierEcranResultat(int stepID, String val,String fct='', String name='Resultat_ID_a', int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"verifierEcranResultat",[val:val , fct:fct , name:name , timeout:timeout])
		if (!fct) fct = getFctFromModObj()
		STEP.scrollToPosition(0, 0)
		STEP.click(0, GlobalJDD.myGlobalJDD, 'a_Toggle',1,'WARNING')
		//STEP.delay(1)
		STEP.verifyText(0, GlobalJDD.myGlobalJDD, 'Fonction_code', fct,timeout,'WARNING')
		STEP.verifyText(0, GlobalJDD.myGlobalJDD,name, val,timeout)
		Log.addTraceEND(CLASS_FOR_LOG,"verifierEcranResultat")

	}
	
	
	
	
	


	
	public static verifierEcranRUD(int stepID, String text, String fct='' , int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"verifierEcranRUD",[ idval:text , fct:fct ,  timeout:timeout])
		if (fct=='') { fct = getFctFromModObj() }
		String code = fct + " - Consultation ou modification"
		STEP.scrollToPosition(0, 0)
		STEP.verifyText(0, GlobalJDD.myGlobalJDD, 'Selection_ID', text,timeout,'WARNING')
		STEP.click(0, GlobalJDD.myGlobalJDD, 'a_Toggle',timeout,'WARNING')
		//STEP.delay(1)
		STEP.verifyText(0, GlobalJDD.myGlobalJDD, 'Fonction_code', code,timeout,'WARNING')
		Log.addTraceEND(CLASS_FOR_LOG,"verifierEcranRUD")
	}	


	public static goToURL_RUD(int stepID, String idval, String fct='') {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_RUD",[idval:idval , fct:fct])
		if (fct=='') { fct = getFctFromModObj() }
		String url = GlobalVariable.BASE_URL.toString() + "FormE" + fct + "?" + "ID1=" + idval
		STEP.navigateToUrl(0, url,'Consultation ou modification')
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_RUD")
	}


	public static goToURL_RUD_and_checkCartridge(int stepID, String idval, String fct='' , int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_RUD_and_checkCartridge",[id:idval , fct:fct])
		//goToURL_RUD(idval,fct)
		//verifierEcranRUD(idval, fct, timeout)
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_RUD_and_checkCartridge")
	}
	
	
	
	
	
	
	
	


	public static verifierEcranGrille(int stepID, String fct='', int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"verifierEcranGrille",[fct:fct , timeout:timeout])
		if (fct=='') { fct = getFctFromModObj() }
		String code = "E" + fct
		STEP.scrollToPosition(0, 0)
		STEP.click(0, GlobalJDD.myGlobalJDD, 'a_Toggle',timeout,'WARNING')
		//STEP.delay(1)
		STEP.verifyText(0, GlobalJDD.myGlobalJDD, 'Fonction_code', 'E'+ fct, timeout,'WARNING')
		Log.addTraceEND(CLASS_FOR_LOG,"verifierEcranGrille")
	}
	
	
	public static goToURL_Grille(int stepID, String fct) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_Grille",[fct:fct])
		String url = GlobalVariable.BASE_URL.toString() + "E" + fct + "?"
		STEP.navigateToUrl(stepID, url,'Grille')
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_Grille")
	}

	
	public static goToURL_Grille_and_checkCartridge(int stepID, String fct='', int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_Grille_and_checkCartridge",[fct:fct])
		if (fct=='') { fct = getFctFromModObj() }
		goToURL_Grille(stepID+1, fct)
		verifierEcranGrille(stepID+2, fct,timeout)
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_Grille_and_checkCartridge")
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static verifierEcranCreation(int stepID, String fct='', int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"verifierEcranCreation",[fct:fct , timeout:timeout])
		if (fct=='') { fct = getFctFromModObj() }
		String code = fct + " - Création"
		STEP.scrollToPosition(0, 0)
		STEP.click(stepID, GlobalJDD.myGlobalJDD, 'a_Toggle',timeout,'WARNING')
		//STEP.delay(1)
		STEP.verifyText(stepID, GlobalJDD.myGlobalJDD, 'Fonction_code', code,timeout,'WARNING')
		Log.addTraceEND(CLASS_FOR_LOG,"verifierEcranCreation")
	}
	
	
	public static goToURL_Creation(int stepID, String fct,String attr='') {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_Creation",[fct:fct , attr:attr])
		String url = GlobalVariable.BASE_URL.toString() + "FormE" + fct + "?" + attr
		//navigateToUrl(stepID, url,'Création')
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_Creation")
	}
	
	
	
	public static goToURL_Creation_and_checkCartridge(int stepID,String fct='', String attr='',int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_Creation_and_checkCartridge",[fct:fct])
		if (fct=='') { fct = getFctFromModObj() }
		//goToURL_Creation(stepID+1,fct,attr)
		//verifierEcranCreation(stepID+2,fct,timeout)
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_Creation_and_checkCartridge")
	} // end of def

	
	
	
	
	
	
	
	private static String getFctFromModObj() {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getFctFromModObj",[:])
		String ret = TNRPropertiesReader.getMyProperty('CODESCREEN_' + Tools.getMobObj(GlobalVariable.CAS_DE_TEST_EN_COURS.toString()))
		Log.addTraceEND(CLASS_FOR_LOG,"getFctFromModObj")
		return ret
	}
	
	
}
