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
public class DevOpsUserStory {

	private static final String CLASS_NAME = 'DevOpsUserStory'

	protected static String devOpsUserStoryID = ''


	public static String getUrl() {
		Log.addTraceBEGIN(CLASS_NAME, "getUrl")
		String url = devOpsUserStoryID ? "${DevOpsClient.BASE_URL}/_workitems/edit/$devOpsUserStoryID" : ''
		Log.addTraceEND(CLASS_NAME, "getUrl",url)
		return url
	}

	public static String getID() {
		return devOpsUserStoryID
	}


	public static createWorkItem(String title, String description) {
		Log.addTraceBEGIN(CLASS_NAME, "createWorkItem", [title:title , description:description ])
		if (!devOpsUserStoryID) {
			Map fields = [:]
			fields.put(DevOpsClient.FIELD_TITLE, title)
			fields.put(DevOpsClient.FIELD_DESCRIPTION, description)
			devOpsUserStoryID = DevOpsClient.createWorkItem(WorkItemType.USER_STORY,fields)
		}else {
			Log.addTrace("devOpsUserStoryID existe déjà")
		}
		Log.addTraceEND(CLASS_NAME, "createWorkItem")
	}




	static void updateDescription(String description) {
		Log.addTraceBEGIN(CLASS_NAME, "updateDescription", [description:description])
		if (devOpsUserStoryID) {
			Map fields = [:]
			fields.put(DevOpsClient.FIELD_DESCRIPTION, description)
			DevOpsClient.updateWorkItem(WorkItemType.USER_STORY, devOpsUserStoryID, fields)
		}else {
			Log.addTrace("Pas de devOpsUserStoryID")
		}
		Log.addTraceEND(CLASS_NAME, "updateDescription")
	}


	static void attachFile(String filePath) {
		Log.addTraceBEGIN(CLASS_NAME, "attachFile", [filePath:filePath])
		if (devOpsUserStoryID) {
			String urlFile = DevOpsClient.uploadFile(filePath)
			Map fields = [:]
			fields.put(DevOpsClient.RELATIONS, [
				"rel": "AttachedFile",
				"url": urlFile
			])
			DevOpsClient.updateWorkItem(WorkItemType.USER_STORY,devOpsUserStoryID, fields)
		}else {
			Log.addTrace("Pas de devOpsUserStoryID")
		}
		Log.addTraceEND(CLASS_NAME, "attachFile")
	}



	public static void attachBug(String bugId, String attachmntComment) {
		Log.addTraceBEGIN(CLASS_NAME, "attachBug", [bugId:bugId , attachmntComment:attachmntComment])
		if (devOpsUserStoryID) {
			Map fields = [:]
			fields.put(DevOpsClient.RELATIONS, [
				"rel": "System.LinkTypes.Related",
				"url":"${DevOpsClient.BASE_URL}/_apis/wit/workitems/$bugId",
				"attributes": [
					"comment": attachmntComment
				]
			])
			DevOpsClient.updateWorkItem(WorkItemType.USER_STORY,devOpsUserStoryID, fields)
		}else {
			Log.addTrace("Pas de devOpsUserStoryID")
		}
		Log.addTraceEND(CLASS_NAME, "attachBug")
	}
}
