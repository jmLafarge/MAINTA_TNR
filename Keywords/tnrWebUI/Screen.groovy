package tnrWebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrCommon.Tools
import tnrJDDManager.GlobalJDD
import tnrLog.Log

/**
 * Personalise les actions WebUI
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */

@CompileStatic
public class Screen {

	private static final String CLASS_FOR_LOG = 'Screen'

	private static final String CLASS_CODE = 'SCR'

	public static checkGridScreen(def stepID, String fct='', int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkGridScreen",[fct:fct , timeout:timeout])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '01',stepID)
		fct = fct ?: Tools.getFctFromModObj()
		String code = "E" + fct
		STEP.scrollToPosition('', 0, 0)
		STEP.simpleClick(StepID.addSubStep(strStepID,2), GlobalJDD.myGlobalJDD, 'a_Toggle',timeout,'WARNING')
		STEP.verifyText(StepID.addSubStep(strStepID,3), GlobalJDD.myGlobalJDD, 'Fonction_code', 'E'+ fct, timeout,'WARNING')
		Log.addTraceEND(CLASS_FOR_LOG,"checkGridScreen")
	}



	public static checkResultScreen(def stepID, String val,String fct='', String name='Resultat_ID_a', int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkResultScreen",[val:val , fct:fct , name:name , timeout:timeout])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '02',stepID)
		fct = fct ?: Tools.getFctFromModObj()
		STEP.scrollToPosition('', 0, 0)
		WUI.delay(500)
		STEP.simpleClick(StepID.addSubStep(strStepID,3), GlobalJDD.myGlobalJDD, 'a_Toggle',1,'WARNING')
		WUI.delay(500)
		STEP.verifyText(StepID.addSubStep(strStepID,5), GlobalJDD.myGlobalJDD, 'Fonction_code', fct,timeout,'WARNING')
		STEP.verifyText(StepID.addSubStep(strStepID,6), GlobalJDD.myGlobalJDD,name, val,timeout)
		Log.addTraceEND(CLASS_FOR_LOG,"checkResultScreen")
	}


	public static checkReadUpdateDeleteScreen(def stepID, String text, String fct='' , int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkReadUpdateDeleteScreen",[ idval:text , fct:fct ,  timeout:timeout])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '03',stepID)
		fct = fct ?: Tools.getFctFromModObj()
		String code = fct + " - Consultation ou modification"
		STEP.scrollToPosition('', 0, 0)
		STEP.verifyText(StepID.addSubStep(strStepID,2), GlobalJDD.myGlobalJDD, 'Selection_ID', text,timeout,'WARNING')
		STEP.simpleClick(StepID.addSubStep(strStepID,3), GlobalJDD.myGlobalJDD, 'a_Toggle',timeout,'WARNING')
		STEP.verifyText(StepID.addSubStep(strStepID,4), GlobalJDD.myGlobalJDD, 'Fonction_code', code,timeout,'WARNING')
		Log.addTraceEND(CLASS_FOR_LOG,"checkReadUpdateDeleteScreen")
	}



	public static checkCreateScreen(def stepID, String fct='', int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkCreateScreen",[stepID:stepID , fct:fct , timeout:timeout])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '04',stepID)
		fct = fct ?: Tools.getFctFromModObj()
		String code = fct + " - Création"
		STEP.scrollToPosition('',0, 0)
		STEP.simpleClick(StepID.addSubStep(strStepID,3), GlobalJDD.myGlobalJDD, 'a_Toggle',timeout,'WARNING')
		STEP.verifyText(StepID.addSubStep(strStepID,5), GlobalJDD.myGlobalJDD, 'Fonction_code', code,timeout,'WARNING')
		Log.addTraceEND(CLASS_FOR_LOG,"checkCreateScreen")
	}


	public static checkCartridge(def stepID, String txt, int timeout = GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"checkCartridge",[txt:txt , timeout:timeout])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '05',stepID)
		STEP.scrollToPosition('', 0, 0)
		STEP.simpleClick(StepID.addSubStep(strStepID,2), GlobalJDD.myGlobalJDD,'a_Toggle',timeout,'WARNING')
		STEP.verifyText(StepID.addSubStep(strStepID,3), GlobalJDD.myGlobalJDD, 'Fonction_code', txt,timeout,'WARNING')
		Log.addTraceEND(CLASS_FOR_LOG,"checkCartridge")
	}
}
