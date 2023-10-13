package tnrDevOps

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic



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



	private static Map sendRequest(def type, Map fields, String method) {
		
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
		if (responseCode == 200) {
			def reader = new InputStreamReader(conn.getInputStream())
			Map jsonResponse = new JsonSlurper().parseText(reader.text) as Map
			reader.close()
			return jsonResponse
		} else {
			println "Failed: HTTP error code: $responseCode"
			return null
		}
	}
	


	


	private static List<LinkedHashMap<String, Object>> transformFields(Map<String, Object> fields) {
	    return fields.collect { key, value ->
	        [
	            "op": "add",
	            "path": "/fields/$key",
	            "value": value
	        ] as LinkedHashMap<String, Object>
	    }
	}
	


	static void createTask(Map fields) {
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




	static void createBug(Map fields) {
		def response = sendRequest('$Bug', fields,'POST')
		if (response != null) {
			println "Bug created: ${response.id}"
		}
	}




	static void updateBug(int bugId, Map fields) {
		def response = sendRequest(bugId, fields, 'PATCH')
		if (response != null) {
			println "Bug updated: ${response.id}"
		}
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


