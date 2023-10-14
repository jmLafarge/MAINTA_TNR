package tnrDevOps

import groovy.transform.CompileStatic
import tnrLog.Log

/**
 *
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

@CompileStatic
public class DevOpsBug {
	
	
	private static final String CLASS_NAME = 'DevOpsBug'
	
	
	
	public static String createBug(String title, String reproSteps, String systemInfo, String stepID,  String history) {
		Log.addTraceBEGIN(CLASS_NAME, "createBug", [title:title , reproSteps:reproSteps , systemInfo:systemInfo , stepID:stepID , history:history])
		Map fields = [:]
		fields.put(DevOpsClient.FIELD_TITLE, title)
        fields.put(DevOpsClient.FIELD_REPRO_STEPS, reproSteps)
        fields.put(DevOpsClient.FIELD_SYSTEM_INFO, systemInfo)
        fields.put(DevOpsClient.FIELD_NUM_TNR, stepID)
        fields.put(DevOpsClient.FIELD_HISTORY, history)

		String id = DevOpsClient.createBug(fields)
		Log.addTraceEND(CLASS_NAME, "createBug",id)
		return id
	}




	static void updateBug(int bugId, String history) {
		
		Map fields = [:]
		fields.put(DevOpsClient.FIELD_HISTORY, history)

		DevOpsClient.updateBug(bugId, fields)
	}



	static Map readBug(int bugId) {
		

		DevOpsClient.readBug(bugId)
	}
	
	
}
