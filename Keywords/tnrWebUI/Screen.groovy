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

	private static final String CLASS_NAME = 'Screen'


	public static checkGridScreen( String fct='', int timeout = (int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_NAME,"checkGridScreen",[fct:fct , timeout:timeout])
		StepID.setParentStepID(CLASS_NAME + 'checkGridScreen')
		fct = fct ?: Tools.getFctFromModObj()
		String code = "E" + fct
		STEP.scrollToPosition( 0, 0)
		STEP.simpleClick( GlobalJDD.myGlobalJDD, 'a_Toggle',timeout,'WARNING')
		STEP.verifyText( GlobalJDD.myGlobalJDD, 'Fonction_code', 'E'+ fct, timeout,'WARNING')
		StepID.clearParentStepID()
		Log.addTraceEND(CLASS_NAME,"checkGridScreen")
	}



	public static checkResultScreen( String val,String fct='', String name='Resultat_ID_a', int timeout =(int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_NAME,"checkResultScreen",[val:val , fct:fct , name:name , timeout:timeout])
		StepID.setParentStepID(CLASS_NAME + 'checkResultScreen')
		fct = fct ?: Tools.getFctFromModObj()
		STEP.scrollToPosition( 0, 0)
		STEP.simpleClick( GlobalJDD.myGlobalJDD, 'a_Toggle',1,'WARNING')
		STEP.verifyText( GlobalJDD.myGlobalJDD, 'Fonction_code', fct,timeout,'WARNING')
		STEP.verifyText( GlobalJDD.myGlobalJDD,name, val,timeout)
		StepID.clearParentStepID()
		Log.addTraceEND(CLASS_NAME,"checkResultScreen")
	}


	public static checkReadUpdateDeleteScreen( String textToVerify, String fct='' , int timeout = (int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_NAME,"checkReadUpdateDeleteScreen",[ textToVerify:textToVerify , fct:fct ,  timeout:timeout])
		StepID.setParentStepID(CLASS_NAME + 'checkReadUpdateDeleteScreen')
		fct = fct ?: Tools.getFctFromModObj()
		String code = fct + " - Consultation ou modification"
		STEP.scrollToPosition( 0, 0)
		STEP.verifyText( GlobalJDD.myGlobalJDD, 'Selection_ID', textToVerify,timeout,'WARNING')
		STEP.simpleClick( GlobalJDD.myGlobalJDD, 'a_Toggle',timeout,'WARNING')
		STEP.verifyText( GlobalJDD.myGlobalJDD, 'Fonction_code', code,timeout,'WARNING')
		StepID.clearParentStepID()
		Log.addTraceEND(CLASS_NAME,"checkReadUpdateDeleteScreen")
	}



	public static checkCreateScreen( String fct='', int timeout = (int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_NAME,"checkCreateScreen",[ fct:fct , timeout:timeout])
		StepID.setParentStepID(CLASS_NAME + 'checkCreateScreen')
		fct = fct ?: Tools.getFctFromModObj()
		String code = fct + " - Création"
		STEP.scrollToPosition(0, 0)
		STEP.simpleClick( GlobalJDD.myGlobalJDD, 'a_Toggle',timeout,'WARNING')
		STEP.verifyText( GlobalJDD.myGlobalJDD, 'Fonction_code', code,timeout,'WARNING')
		StepID.clearParentStepID()
		Log.addTraceEND(CLASS_NAME,"checkCreateScreen")
	}


	public static checkCartridge( String txt, int timeout = (int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_NAME,"checkCartridge",[txt:txt , timeout:timeout])
		StepID.setParentStepID(CLASS_NAME + 'checkCartridge')
		STEP.scrollToPosition( 0, 0)
		STEP.simpleClick( GlobalJDD.myGlobalJDD,'a_Toggle',timeout,'WARNING')
		STEP.verifyText( GlobalJDD.myGlobalJDD, 'Fonction_code', txt,timeout,'WARNING')
		StepID.clearParentStepID()
		Log.addTraceEND(CLASS_NAME,"checkCartridge")
	}
}
