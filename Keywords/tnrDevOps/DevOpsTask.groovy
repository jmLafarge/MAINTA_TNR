package tnrDevOps

import groovy.transform.CompileStatic
import tnrDevOps.DevOpsClient.WorkItemType
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


	public static String getUrl() {
		Log.addTraceBEGIN(CLASS_NAME, "getTaskUrl")
		String url = devOpsTaskID ? "${DevOpsClient.BASE_URL}/_workitems/edit/$devOpsTaskID" : ''
		Log.addTraceEND(CLASS_NAME, "getTaskUrl",url)
		return url
	}

	public static String getID() {
		return devOpsTaskID
	}


	public static createWorkItem(String title, String description) {
		Log.addTraceBEGIN(CLASS_NAME, "createWorkItem", [title:title , description:description ])
		if (!devOpsTaskID) {
			Map fields = [:]
			fields.put(DevOpsClient.FIELD_TITLE, title)
			fields.put(DevOpsClient.FIELD_DESCRIPTION, description)
			devOpsTaskID = DevOpsClient.createWorkItem(WorkItemType.TASK,fields)
		}else {
			Log.addTrace("devOpsTaskID existe déjà")
		}
		Log.addTraceEND(CLASS_NAME, "createWorkItem")
	}




	static void updateDescription(String description) {
		Log.addTraceBEGIN(CLASS_NAME, "updateDescription", [description:description])
		if (devOpsTaskID) {
			Map fields = [:]
			fields.put(DevOpsClient.FIELD_DESCRIPTION, description)
			DevOpsClient.updateWorkItem(WorkItemType.TASK, devOpsTaskID, fields)
		}else {
			Log.addTrace("Pas de devOpsTaskID")
		}
		Log.addTraceEND(CLASS_NAME, "updateDescription")
	}


	static void attachFile(String filePath) {
		Log.addTraceBEGIN(CLASS_NAME, "attachFile", [filePath:filePath])
		if (devOpsTaskID) {
			String urlFile = DevOpsClient.uploadFile(filePath)
			Map fields = [:]
			fields.put(DevOpsClient.RELATIONS, [
				"rel": "AttachedFile",
				"url": urlFile
			])
			DevOpsClient.updateWorkItem(WorkItemType.TASK,devOpsTaskID, fields)
		}else {
			Log.addTrace("Pas de devOpsTaskID")
		}
		Log.addTraceEND(CLASS_NAME, "attachFile")
	}



	public static void attachBug(String bugId, String attachmntComment) {
		Log.addTraceBEGIN(CLASS_NAME, "attachBug", [bugId:bugId , attachmntComment:attachmntComment])
		if (devOpsTaskID) {
			Map fields = [:]
			fields.put(DevOpsClient.RELATIONS, [
				"rel": "System.LinkTypes.Related",
				"url":"${DevOpsClient.BASE_URL}/_apis/wit/workitems/$bugId",
				"attributes": [
					"comment": attachmntComment
				]
			])
			DevOpsClient.updateWorkItem(WorkItemType.TASK,devOpsTaskID, fields)
		}else {
			Log.addTrace("Pas de devOpsTaskID")
		}
		Log.addTraceEND(CLASS_NAME, "attachBug")
	}
}
