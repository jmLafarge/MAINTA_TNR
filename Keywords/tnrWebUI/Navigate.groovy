package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
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
public class Navigate {


	private static final String CLASS_FOR_LOG = 'Navigate'

	private static final String CLASS_CODE = 'NAV'

	public static void openBrowser(def stepID, String url){
		String strStepID = StepID.getStrStepID(CLASS_CODE, '01',stepID)
		try {
			WebUI.openBrowser(url, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(strStepID,"Ouverture du navigateur à l'URL :")
			TNRResult.addDETAIL(url)
			TNRResult.addBrowserInfo()
			WebUI.waitForPageLoad((int)GlobalVariable.TIMEOUTForPageLoad, FailureHandling.STOP_ON_FAILURE)
			WUIWindow.storeMainWindowHandle()
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(strStepID,"Ouverture du navigateur à l'URL :")
			TNRResult.addDETAIL(url)
			TNRResult.addDETAIL(ex.getMessage())
		}
	}


	public static void navigateToUrl(def stepID, String url,String nomUrl){
		String strStepID = StepID.getStrStepID(CLASS_CODE, '02',stepID)
		try {
			WebUI.navigateToUrl(url, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(strStepID,"Navigation vers l'URL '$nomUrl' :")
			TNRResult.addDETAIL(url)
			WebUI.waitForPageLoad((int)GlobalVariable.TIMEOUTForPageLoad, FailureHandling.STOP_ON_FAILURE)
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(strStepID,"Navigation vers l'URL '$nomUrl' :")
			TNRResult.addDETAIL(url)
			TNRResult.addDETAIL(ex.getMessage())
		}
	}


	public static void closeBrowser(def stepID){
		String strStepID = StepID.getStrStepID(CLASS_CODE, '03',stepID)
		try {
			WebUI.closeBrowser(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(strStepID,"Fermeture du navigateur")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(strStepID,"Fermeture du navigateur")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}


	public static void maximizeWindow(def stepID){
		String strStepID = StepID.getStrStepID(CLASS_CODE, '04',stepID)
		try {
			WebUI.maximizeWindow(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(strStepID,"Maximise la fenêtre")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(strStepID,"Maximise la fenêtre")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}



	public static void scrollToPosition(def stepID, int x, int y) {
		String strStepID = StepID.getStrStepID(CLASS_CODE, '05',stepID)
		try {
			WebUI.scrollToPosition(x, y, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPINFO(strStepID, "Scroll à la position $x , $y")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR('-1',"Scroll à la position $x , $y")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}





	static void switchToDefaultContent(def stepID) {
		String strStepID = StepID.getStrStepID(CLASS_CODE, '06',stepID)
		try {
			WebUI.switchToDefaultContent(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPINFO(strStepID, "switchToDefaultContent")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR('-1',"switchToDefaultContent")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}


	public static goToURLReadUpdateDelete(def stepID, String idval, String fct='') {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURLReadUpdateDelete",[idval:idval , fct:fct])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '10',stepID)
		if (fct=='') {
			fct = Tools.getFctFromModObj()
		}
		String url = GlobalVariable.BASE_URL.toString() + "FormE" + fct + "?" + "ID1=" + idval
		navigateToUrl(StepID.addSubStep(strStepID,1), url,'Consultation ou modification')
		Log.addTraceEND(CLASS_FOR_LOG,"goToURLReadUpdateDelete")
	}





	public static goToGridURL(def stepID, String fct='') {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURL_Grille",[fct:fct])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '11',stepID)
		fct = fct ?: Tools.getFctFromModObj()
		String url = GlobalVariable.BASE_URL.toString() + "E" + fct + "?"
		navigateToUrl(StepID.addSubStep(strStepID,1), url,'Grille')
		Log.addTraceEND(CLASS_FOR_LOG,"goToURL_Grille")
	}



	public static goToURLCreate(def stepID, String fct='',String attr='') {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"goToURLCreate",[stepID:stepID , fct:fct , attr:attr])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '12',stepID)
		fct = fct ?: Tools.getFctFromModObj()
		String url = GlobalVariable.BASE_URL.toString() + "FormE" + fct + "?" + attr
		navigateToUrl(StepID.addSubStep(strStepID,1), url,'Création')
		Log.addTraceEND(CLASS_FOR_LOG,"goToURLCreate")
	}
}
