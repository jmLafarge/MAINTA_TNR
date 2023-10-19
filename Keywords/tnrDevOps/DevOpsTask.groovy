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
public class DevOpsTask {

	private static final String CLASS_NAME = 'DevOpsTask'

	protected static String devOpsTaskID = ''


	public static String getTaskUrl() {
		Log.addTraceBEGIN(CLASS_NAME, "getTaskUrl")
		String url = devOpsTaskID ? "${DevOpsClient.BASE_URL}/_workitems/edit/$devOpsTaskID" : ''
		Log.addTraceEND(CLASS_NAME, "getTaskUrl",url)
		return url
	}

	public static String getTaskId() {
		return devOpsTaskID
	}


	public static createTask(String title, String description) {
		Log.addTraceBEGIN(CLASS_NAME, "createTask", [title:title , description:description ])
		if (!devOpsTaskID) {
			Map fields = [:]
			fields.put(DevOpsClient.FIELD_TITLE, title)
			fields.put(DevOpsClient.FIELD_DESCRIPTION, description)

			devOpsTaskID = DevOpsClient.createTask(fields)
		}else {
			Log.addTrace("devOpsTaskID existe déjà")
		}
		Log.addTraceEND(CLASS_NAME, "createTask")
	}




	static void updateDescriptionTask(String description) {
		Log.addTraceBEGIN(CLASS_NAME, "updateDescriptionTask", [description:description])
		if (devOpsTaskID) {
			Map fields = [:]
			fields.put(DevOpsClient.FIELD_DESCRIPTION, description)
			DevOpsClient.updateTask(devOpsTaskID, fields)
		}else {
			Log.addTrace("Pas de devOpsTaskID")
		}
		Log.addTraceEND(CLASS_NAME, "updateDescriptionTask")
	}


	static void addFileToTask(String filePath) {
		Log.addTraceBEGIN(CLASS_NAME, "addFileToTask", [filePath:filePath])
		if (devOpsTaskID) {
			String urlFile = DevOpsClient.uploadFile(filePath)
			Map fields = [:]
			fields.put(DevOpsClient.RELATIONS, [
				"rel": "AttachedFile",
				"url": urlFile
			])
			DevOpsClient.updateTask(devOpsTaskID, fields)
		}else {
			Log.addTrace("Pas de devOpsTaskID")
		}
		Log.addTraceEND(CLASS_NAME, "addFileToTask")
	}



	public static void addBugToTask(String bugId, String attachmntComment) {
		Log.addTraceBEGIN(CLASS_NAME, "addBugToTask", [bugId:bugId , attachmntComment:attachmntComment])
		if (devOpsTaskID) {
			Map fields = [:]
			fields.put(DevOpsClient.RELATIONS, [
				"rel": "System.LinkTypes.Related",
				"url":"${DevOpsClient.BASE_URL}/_apis/wit/workitems/$bugId",
				"attributes": [
					"comment": attachmntComment
				]
			])
			DevOpsClient.updateTask(devOpsTaskID, fields)
		}else {
			Log.addTrace("Pas de devOpsTaskID")
		}
		Log.addTraceEND(CLASS_NAME, "addBugToTask")
	}
}
