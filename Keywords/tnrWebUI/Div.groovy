package tnrWebUI

import com.kms.katalon.core.testobject.TestObject

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.GlobalJDD
import tnrLog.Log
import tnrResultManager.TNRResult


/**
 * Gère les DIV modal
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */
@CompileStatic
public class Div {

	private static final String CLASS_NAME = 'Div'




	static boolean isDivModalOpened(String name, int timeout=(int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_NAME, "isDivModalOpened", [ name:name , timeout:timeout])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'isDivModalOpened' + name)
		boolean ret = false
		if (WUI.isElementPresent(GlobalJDD.myGlobalJDD, 'divModalActive', 2)) {
			TNRResult.addSTEPPASS(strStepID, "Ouverture de la fenetre de saisie ")
			ret = true
		}else {
			TNRResult.addSTEPFAIL(strStepID, "Ouverture de la fenetre de saisie ")
			TNRResult.addDETAIL("La fenetre de saisie ne s'est pas ouverte")
		}
		Log.addTraceEND(CLASS_NAME, "isDivModalOpened",ret)
		return ret
	}


	static boolean isDivModalClosed(String name, int timeout=(int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_NAME, "isDivModalClosed", [name:name , timeout:timeout])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'isDivModalClosed' + name)
		boolean ret = false
		if (WUI.isElementPresent(GlobalJDD.myGlobalJDD, 'divModalInactive', 2)) {
			TNRResult.addSTEPPASS(strStepID, "Fermeture de la fenetre de saisie")
			ret = true
		}else {
			TNRResult.addDETAIL("La fenetre de saisie ne s'est pas fermée")
		}
		Log.addTraceEND(CLASS_NAME, "isDivModalClosed",ret)
		return ret
	}
} //end of class
