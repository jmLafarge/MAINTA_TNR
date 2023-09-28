package tnrWebUI

import java.security.MessageDigest

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrCommon.Tools
import tnrLog.Log

/**
 * Gere 
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */

@CompileStatic
public class StepID {

	private static final String CLASS_NAME = 'StepID'



	public static String getStrStepID(String stepID) {
		Log.addTraceBEGIN(CLASS_NAME, "getStrStepID", [stepID:stepID])

		String cdt = GlobalVariable.CAS_DE_TEST_EN_COURS.toString()
		String strStepID = ''
		if (stepID) {
			strStepID = Tools.get256SHA(cdt + stepID)
			List<String> fullStepIDList = Log.getList('fullStepID')
			if (fullStepIDList && fullStepIDList.contains(strStepID)) {
				Log.addERROR("strStepID '$strStepID' existe déja dans la liste")
			}
			Log.addToList('fullStepID', strStepID)
		}
		Log.addTraceEND(CLASS_NAME, "getStrStepID",strStepID)
		return strStepID
	}
} // end of class
