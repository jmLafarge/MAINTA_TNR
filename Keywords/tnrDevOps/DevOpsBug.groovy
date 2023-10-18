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
		Log.addTraceBEGIN(CLASS_NAME, "createBug", [title:title , reproSteps:reproSteps , systemInfo:systemInfo , stepID:stepID , history:history ])
		String cssReproStep = 'style="color: blue; font-weight: bold;"'
		Map fields = [:]
		fields.put(DevOpsClient.FIELD_TITLE, title)
		fields.put(DevOpsClient.FIELD_REPRO_STEPS, "<p ${cssReproStep}>$reproSteps</p>")
		fields.put(DevOpsClient.FIELD_SYSTEM_INFO, systemInfo)
		fields.put(DevOpsClient.FIELD_NUM_TNR, stepID)
		fields.put(DevOpsClient.FIELD_HISTORY, history)

		String id = DevOpsClient.createBug(fields)
		Log.addTraceEND(CLASS_NAME, "createBug",id)
		return id
	}
	
	
	public static String addFileToBug(String bugId,String screenshotFileFullname, String attachmentComment) {
		Log.addTraceBEGIN(CLASS_NAME, "addFileToBug", [bugId:bugId , screenshotFileFullname:screenshotFileFullname , attachmentComment:attachmentComment])
		String urlFile = ''
		if (screenshotFileFullname) {
			urlFile = DevOpsClient.uploadFile(screenshotFileFullname)
			Map fields = [:]
			fields.put(DevOpsClient.RELATIONS, [
				"rel": "AttachedFile",
				"url": urlFile,
				"attributes": [
					"comment": attachmentComment
				]
			])
			DevOpsClient.updateBug(bugId, fields)
		}
		Log.addTraceEND(CLASS_NAME, "addFileToBug",urlFile)
		return urlFile
	}
	

	public static String getBugUrl(String bugId) {
		Log.addTraceBEGIN(CLASS_NAME, "getBugUrl", [bugId:bugId])
		String url = bugId ? "${DevOpsClient.BASE_URL}/_workitems/edit/$bugId" : ''
		Log.addTraceEND(CLASS_NAME, "getBugUrl",url)
		return url
	}

	
	static void updateHistoryBug(String bugId, String history) {
		Log.addTraceBEGIN(CLASS_NAME, "updateHistoryBug", [history:history])
		if (bugId) {
			Map fields = [:]
			fields.put(DevOpsClient.FIELD_HISTORY, history)
			DevOpsClient.updateBug(bugId, fields)
		}else {
			Log.addTrace("Pas de bugId")
		}
		Log.addTraceEND(CLASS_NAME, "updateHistoryBug")
	}


	/*NOT USED YET
	public static void updateBug(String bugId, String history) {

		Map fields = [:]
		fields.put(DevOpsClient.FIELD_HISTORY, history)

		DevOpsClient.updateBug(bugId, fields)
	}
	*/

	/* NOT USED YET
	static Map readBug(String bugId) {

		DevOpsClient.readBug(bugId)
	}
	*/
	

	
			
}
