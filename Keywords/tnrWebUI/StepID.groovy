package tnrWebUI

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

	private static final String CLASS_FOR_LOG = 'StepID'

	private static final int NBCAR_STEPID = 2


	private static boolean isMapValid(def stepID) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "isMapValid", [stepID:stepID])
		boolean ret = true
		if (stepID instanceof Map) {
			Map map  = stepID as Map
			int sizeMap = map.size()
			if (sizeMap != 1) {
				Log.addERROR("sizeMap = '$sizeMap' !")
				ret=false
			}
			// Check the types of the elements
			if (!(map.keySet().toList().first() instanceof String)) {
				Log.addERROR("map.keySet().toList().first() n'est pas un String !")
				ret=false
			}
			if (!(map.values().toList().first() instanceof Integer)) {
				Log.addERROR("map.values().toList().first() n'est pas un Integer !")
				ret=false
			}

		}else {
			Log.addERROR("stepID n'est pas un Map !")
			ret=false
		}
		Log.addTraceEND(CLASS_FOR_LOG, "isMapValid",ret)
		return ret
	}



	public static String getStrStepID(String classCode, String functionCode,def stepID) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getStrStepID", [classCode:classCode , functionCode:functionCode , stepID:stepID])
		String strStepID =''
		if (stepID instanceof Integer) {
			strStepID = classCode + functionCode + Tools.addZero(stepID, NBCAR_STEPID)
		}else if (stepID instanceof String) {
			strStepID = stepID
		}else if (isMapValid(stepID)) {
			Map <String, Integer> map  = stepID as Map
			String strStepIDContext = map.keySet().toList().first()
			int currentStepID = map.values().toList().first()
			strStepID = strStepIDContext + classCode + functionCode + Tools.addZero(currentStepID, NBCAR_STEPID)
		}
		String cdt = GlobalVariable.CAS_DE_TEST_EN_COURS.toString()
		
		// List<String> fullStepIDList = Log.getList('fullStepID') --> A terminer pour détecter la non unicité de fullStepID

		Log.addToList('fullStepID', cdt + '_' +strStepID)
		Log.addTraceEND(CLASS_FOR_LOG, "getStrStepID",strStepID)
		return strStepID
	}


	public static Map <String,Integer> addSubStep(String context, int stepNumber){
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getStrStepID", [context:context , stepNumber:stepNumber])
		Map map = [:]
		map[context] = stepNumber
		Log.addTraceEND(CLASS_FOR_LOG, "getStrStepID",map)
		return map
	}



} // end of class
