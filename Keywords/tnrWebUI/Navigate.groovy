package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrCommon.Tools
import tnrLog.Log
import tnrResultManager.TNRResult
import tnrSelenium.SeleniumWUI


/**
 * Personalise les actions WebUI
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */

@CompileStatic
public class Navigate {


	private static final String CLASS_NAME = 'Navigate'


	public static void openBrowser( String url){
		String strStepID = StepID.getStrStepID(CLASS_NAME +'openBrowser')
		try {
			WebUI.openBrowser(url, FailureHandling.STOP_ON_FAILURE)
			SeleniumWUI.setMyWebDriver()
			TNRResult.addSTEPPASS(strStepID,"Ouverture du navigateur à l'URL :")
			TNRResult.addDETAIL(url)
			TNRResult.addBrowserInfo()
			TNRResult.createDevOpsCampaign()
			WUI.waitForPageLoad(5,strStepID)
			WUIWindow.storeMainWindowHandle()
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(strStepID,"Ouverture du navigateur à l'URL :")
			TNRResult.addDETAIL(url)
			TNRResult.addDETAIL(ex.getMessage())
		}
	}


	public static void navigateToUrl( String url,String nomUrl){
		Log.addTraceBEGIN(CLASS_NAME, "navigateToUrl", [url:url , nomUrl:nomUrl ])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'navigateToUrl' + nomUrl)
		WUI.waitForPageLoad(5,strStepID + 'avant')
		try {
			println 'AvantJMLnavigateToUrl'
			WebUI.navigateToUrl(url, FailureHandling.STOP_ON_FAILURE)
			println 'JustApresJMLnavigateToUrl'
			TNRResult.addSTEPPASS(strStepID,"Navigation vers l'URL '$nomUrl' :")
			TNRResult.addDETAIL(url)
			WUI.waitForPageLoad(5,strStepID + 'après')
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(strStepID,"Navigation vers l'URL '$nomUrl' :")
			TNRResult.addDETAIL(url)
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_NAME, "navigateToUrl")
	}


	public static void closeBrowser(){
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'closeBrowser')
		try {
			WebUI.closeBrowser(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS(strStepID,"Fermeture du navigateur")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(strStepID,"Fermeture du navigateur")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}


	public static void maximizeWindow(){
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'maximizeWindow')
		try {
			WebUI.maximizeWindow(FailureHandling.STOP_ON_FAILURE)
			int[] dimensions = Tools.getBrowserDimensions()
			TNRResult.addSTEPPASS(strStepID,"Maximise la fenêtre (${dimensions[0]}, ${dimensions[1]})")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(strStepID,"Maximise la fenêtre")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}



	public static void scrollToPosition( int x, int y) {
		String strStepID = '' //StepID.getStrStepID(CLASS_NAME + 'scrollToPosition')
		try {
			WebUI.scrollToPosition(x, y, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPINFO('',"Scroll à la position $x , $y")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(strStepID,"Scroll à la position $x , $y")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}





	static void switchToDefaultContent() {
		String strStepID = '' // StepID.getStrStepID(CLASS_NAME + 'switchToDefaultContent')
		try {
			WebUI.switchToDefaultContent(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPINFO('',"switchToDefaultContent")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR(strStepID,"switchToDefaultContent")
			TNRResult.addDETAIL(ex.getMessage())
		}
	}


	public static goToURLReadUpdateDelete( String idval, String fct='') {
		Log.addTraceBEGIN(CLASS_NAME,"goToURLReadUpdateDelete",[idval:idval , fct:fct])
		fct = fct ?: Tools.getFctFromModObj()
		String url = GlobalVariable.BASE_URL.toString() + "FormE" + fct + "?" + "ID1=" + idval
		navigateToUrl( url,'Consultation ou modification')
		Log.addTraceEND(CLASS_NAME,"goToURLReadUpdateDelete")
	}





	public static goToGridURL( String fct='') {
		Log.addTraceBEGIN(CLASS_NAME,"goToGridURL",[fct:fct])
		fct = fct ?: Tools.getFctFromModObj()
		String url = GlobalVariable.BASE_URL.toString() + "E" + fct + "?"
		navigateToUrl( url,'Grille')
		Log.addTraceEND(CLASS_NAME,"goToGridURL")
	}



	public static goToURLCreate( String fct='',String attr='') {
		Log.addTraceBEGIN(CLASS_NAME,"goToURLCreate",[ fct:fct , attr:attr])
		fct = fct ?: Tools.getFctFromModObj()
		String url = GlobalVariable.BASE_URL.toString() + "FormE" + fct + "?" + attr
		navigateToUrl( url,'Création')
		Log.addTraceEND(CLASS_NAME,"goToURLCreate")
	}
}
