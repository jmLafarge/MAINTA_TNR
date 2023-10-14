package tnrDevOps

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
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
public class DevOpsClient {

	private static final String CLASS_NAME = 'DevOpsClient'


	// Paramètres pour l'API Azure DevOps
	private static final String ORGANIZATION = "mainta"
	private static final String PROJECT = "ba23da28-ae09-446a-afac-f19fb6bd8d4e"
	private static final String API_VERSION = "7.0"

	private static final String AUTHORIZATION = "Basic OjRvNGUzY3g2MndhcTRkeHh0bmxvbW9rczNzbXNqM2xueHF5NnRwZHBoampyY3c2Z2g0cnE="
	
	protected static final String FIELD_AREA_PATH 		= "System.AreaPath"
	protected static final String FIELD_ITERATION_PATH 	= "System.IterationPath"
	protected static final String FIELD_TITLE 			= "System.Title"
	protected static final String FIELD_REPRO_STEPS 	= "Microsoft.VSTS.TCM.ReproSteps"
	protected static final String FIELD_SYSTEM_INFO 	= "Microsoft.VSTS.TCM.SystemInfo"
	protected static final String FIELD_NUM_TNR 		= "Custom.1229225a-1e58-41aa-8fef-9b91528941bf"
	protected static final String FIELD_HISTORY			= "System.History"
	
	


	private static Map sendRequest(def type, Map fields, String method) {
		Log.addTraceBEGIN(CLASS_NAME, "sendRequest", [type:type , fields:fields , method:method])
		String urlString = "https://dev.azure.com/$ORGANIZATION/$PROJECT/_apis/wit/workitems/$type?api-version=$API_VERSION"
		def url = new URL(urlString)
		HttpURLConnection conn = (HttpURLConnection) url.openConnection()

		if ("PATCH".equals(method)) {
			conn.setRequestMethod("POST")
			conn.setRequestProperty("X-HTTP-Method-Override", "PATCH")
		} else {
			conn.setRequestMethod(method)
		}
		conn.setRequestProperty("Content-Type", "application/json-patch+json")
		conn.setRequestProperty("Authorization", AUTHORIZATION)
		conn.setDoOutput(true)

		if (fields) {
			def body = JsonOutput.toJson(transformFields(fields))
			def writer = new OutputStreamWriter(conn.getOutputStream())
			writer.write(body)
			writer.flush()
			writer.close()
		}

		int responseCode = conn.getResponseCode()
		Map jsonResponse = [:]
		if (responseCode == 200) {
			def reader = new InputStreamReader(conn.getInputStream())
			jsonResponse = new JsonSlurper().parseText(reader.text) as Map
			reader.close()
		} else {
			Log.addERROR("${CLASS_NAME}.sendRequest() Failed: HTTP error code: $responseCode")
		}
		Log.addTraceEND(CLASS_NAME, "sendRequest",jsonResponse)
		return jsonResponse
	}




	//private static List<LinkedHashMap<String, Object>> transformFields(Map<String, Object> fields) {
	private static List<Map> transformFields(Map fields) {
		return fields.collect { key, value ->
			[
				"op": "add",
				"path": "/fields/$key",
				"value": value
			]
		} as List
	}



	static String createTask(Map fields) {
		def response = sendRequest('$Task', fields,'POST')
		if (response != null) {
			println "Task created: ${response.id}"
			
		}
		
	}




	static void updateTask(int taskId, Map fields) {
		def response = sendRequest(taskId, fields, 'PATCH')
		if (response != null) {
			println "Task updated: ${response.id}"
		}
	}




	static String createBug(Map fields) {
		Log.addTraceBEGIN(CLASS_NAME, "createBug", [fields:fields])
		def response = sendRequest('$Bug', fields,'POST')
		String id = response ? response.id : ''
		Log.addTraceEND(CLASS_NAME, "createBug",id)
		return id
	}




	static String updateBug(int bugId, Map fields) {
		Log.addTraceBEGIN(CLASS_NAME, "updateBug", [bugId:bugId , fields:fields])
		def response = sendRequest(bugId, fields, 'PATCH')
		String id = response ? response.id : ''
		Log.addTraceEND(CLASS_NAME, "updateBug",id)
		return id
	}



	static Map readBug(int bugId) {
		def response = sendRequest(bugId, null, "GET")
		if (response != null) {
			return response
		} else {
			println "Could not read bug"
			return null
		}
	}



}


