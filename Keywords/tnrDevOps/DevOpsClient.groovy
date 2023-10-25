package tnrDevOps

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import tnrCommon.TNRPropertiesReader
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

	public static final boolean ALLOW_CREATION_FOR_WARNING = TNRPropertiesReader.getMyProperty('DEVOPS_TICKET_CREATION_FOR_WARNING') == 'ALLOW'
	public static final boolean ALLOW_CREATION_FOR_FAIL = TNRPropertiesReader.getMyProperty('DEVOPS_TICKET_CREATION_FOR_FAIL') == 'ALLOW'
	public static final boolean ALLOW_CREATION_FOR_ERROR = TNRPropertiesReader.getMyProperty('DEVOPS_TICKET_CREATION_FOR_ERROR') == 'ALLOW'

	public static final boolean ALLOW_CREATION = ALLOW_CREATION_FOR_WARNING || ALLOW_CREATION_FOR_FAIL || ALLOW_CREATION_FOR_ERROR

	// Paramètres pour l'API Azure DevOps
	private static final String ORGANIZATION 	= TNRPropertiesReader.getMyProperty('DEVOPS_ORGANIZATION')
	private static final String PROJECT 		= TNRPropertiesReader.getMyProperty('DEVOPS_PROJECT')
	private static final String API_VERSION 	= TNRPropertiesReader.getMyProperty('DEVOPS_API_VERSION')
	private static final String AUTHORIZATION 	= TNRPropertiesReader.getMyProperty('DEVOPS_AUTHORIZATION')
	private static final String AREA_PATH 		= TNRPropertiesReader.getMyProperty('DEVOPS_AREA_PATH')
	private static final String ITERATION_PATH	= TNRPropertiesReader.getMyProperty('DEVOPS_ITERATION_PATH')



	protected static final String FIELD_AREA_PATH 		= "/fields/System.AreaPath"
	protected static final String FIELD_ITERATION_PATH 	= "/fields/System.IterationPath"
	protected static final String FIELD_TITLE 			= "/fields/System.Title"
	protected static final String FIELD_REPRO_STEPS 	= "/fields/Microsoft.VSTS.TCM.ReproSteps"
	protected static final String FIELD_SYSTEM_INFO 	= "/fields/Microsoft.VSTS.TCM.SystemInfo"
	protected static final String FIELD_NUM_TNR 		= "/fields/Custom.1229225a-1e58-41aa-8fef-9b91528941bf"
	protected static final String FIELD_HISTORY			= "/fields/System.History"
	protected static final String FIELD_DESCRIPTION		= "/fields/System.Description"

	protected static final String RELATIONS 				= "/relations/-"

	public static final String BASE_URL		= "https://dev.azure.com/$ORGANIZATION/$PROJECT"

	protected enum WorkItemType {
		TASK('$Task'),
		USER_STORY('$User%20Story'),
		BUG('$Bug');
		final String apiName
		WorkItemType(String apiName) {
			this.apiName = apiName
		}
	}

	protected enum HttpMethod {
		POST('POST'),
		GET('GET'),
		PATCH('PATCH');

		final String methodName

		HttpMethod(String methodName) {
			this.methodName = methodName
		}

		String getMethodName() {
			return this.methodName
		}
	}



	public static boolean isCreationAllowedForStatus(String status) {
		Log.addTraceBEGIN(CLASS_NAME, "isCreationAllowedForStatus", [ status:status])
		boolean allowWARNING 	= ALLOW_CREATION_FOR_WARNING && status=='WARNING'
		Log.addTrace("allowWARNING:$allowWARNING")
		boolean allowFAIL 		= ALLOW_CREATION_FOR_FAIL && status=='FAIL'
		Log.addTrace("allowFAIL:$allowFAIL")
		boolean allowERROR 		= ALLOW_CREATION_FOR_ERROR && status=='ERROR'
		Log.addTrace("allowERROR:$allowERROR")
		boolean allow = allowWARNING || allowFAIL || allowERROR
		Log.addTraceEND(CLASS_NAME, "isCreationAllowedForStatus",allow)
		return allow
	}

	protected static String uploadFile( String filePath) {
		Log.addTraceBEGIN(CLASS_NAME, "sendRequest", [ filePath:filePath])
		String fileName = new File(filePath).getName()
		byte[] fileContent = new File(filePath).bytes
		String urlString = "$BASE_URL/_apis/wit/attachments?fileName=$fileName&api-version=$API_VERSION"
		Log.addTrace("urlString:$urlString")
		def url = new URL(urlString)
		HttpURLConnection conn = (HttpURLConnection) url.openConnection()
		conn.setRequestMethod('POST')
		conn.setRequestProperty("Content-Type",'application/octet-stream')
		conn.setRequestProperty("Authorization", AUTHORIZATION)
		conn.setDoOutput(true)

		int contentLength = fileContent.length
		Log.addTrace("contentLength:$contentLength")
		conn.setRequestProperty("Content-Length", Integer.toString(contentLength))
		OutputStream os = conn.getOutputStream()
		os.write(fileContent)
		os.close()

		int responseCode = conn.getResponseCode()
		Map jsonResponse = [:]
		if (responseCode == 201) {
			def reader = new InputStreamReader(conn.getInputStream())
			jsonResponse = new JsonSlurper().parseText(reader.text) as Map
			reader.close()
		} else {
			Log.addERROR("${CLASS_NAME}.sendRequest() Failed: HTTP error code: $responseCode")
		}
		if (conn != null) {
			conn.disconnect()
		}
		Log.addTraceEND(CLASS_NAME, "sendRequest",jsonResponse.url)
		return jsonResponse.url
	}




	private static Map sendRequest(String type, Map fields, String method) {
		Log.addTraceBEGIN(CLASS_NAME, "sendRequest", [type:type , fields:fields , method:method])
		String urlString = "$BASE_URL/_apis/wit/workitems/$type?api-version=$API_VERSION"
		Log.addTrace("urlString:$urlString")
		def url = new URL(urlString)
		HttpURLConnection conn = (HttpURLConnection) url.openConnection()

		if ("PATCH".equals(method)) {
			conn.setRequestMethod("POST")
			conn.setRequestProperty("X-HTTP-Method-Override", "PATCH")
		} else {
			conn.setRequestMethod(method)
		}
		conn.setRequestProperty("Content-Type",'application/json-patch+json')
		conn.setRequestProperty("Authorization", AUTHORIZATION)
		conn.setDoOutput(true)

		if (fields) {
			fields.put(FIELD_AREA_PATH, AREA_PATH)
			fields.put(FIELD_ITERATION_PATH, ITERATION_PATH)
			def body = JsonOutput.toJson(transformFields(fields))
			Log.addTrace("body:$body")
			def writer = new OutputStreamWriter(conn.getOutputStream())
			writer.write(body)
			//writer.flush()
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
		if (conn != null) {
			conn.disconnect()
		}
		Log.addTraceEND(CLASS_NAME, "sendRequest",jsonResponse)
		return jsonResponse
	}




	private static List<Map> transformFields(Map fields) {
		return fields.collect { path, value ->
			[
				"op": "add",
				"path": path,
				"value": value
			]
		} as List
	}

	/*
	 protected static String createTask(Map fields) {
	 Log.addTraceBEGIN(CLASS_NAME, "createTask", [fields:fields])
	 def response = sendRequest('$Task', fields,'POST')
	 Log.addTrace("response:$response")
	 String id = response ? response.id : ''
	 Log.addTraceEND(CLASS_NAME, "createTask",id)
	 return id
	 }
	 protected static void updateTask(String taskId, Map fields) {
	 Log.addTraceBEGIN(CLASS_NAME, "updateTask", [taskId:taskId , fields:fields])
	 def response = sendRequest(taskId, fields, 'PATCH')
	 String id = response ? response.id : ''
	 Log.addTraceEND(CLASS_NAME, "updateTask",id)
	 }
	 */


	protected static String createWorkItem(WorkItemType workItemType, Map fields) {
		Log.addTraceBEGIN(CLASS_NAME, "createWorkItem", [workItemType:workItemType , fields:fields])
		def response = sendRequest(workItemType.apiName, fields,HttpMethod.POST.methodName)
		Log.addTrace("response:$response")
		String newId = response ? response.id : ''
		Log.addTraceEND(CLASS_NAME, "createWorkItem",newId)
		return newId
	}




	protected static void updateWorkItem(WorkItemType workItemType, String id, Map fields) {
		Log.addTraceBEGIN(CLASS_NAME, "updateWorkItem", [workItemType:workItemType , id:id , fields:fields])
		def response = sendRequest(id, fields, HttpMethod.PATCH.methodName)
		Log.addTraceEND(CLASS_NAME, "updateWorkItem",response)
	}


	protected static String createBug(Map fields) {
		Log.addTraceBEGIN(CLASS_NAME, "createBug", [fields:fields])
		def response = sendRequest('$Bug', fields,'POST')
		String id = response ? response.id : ''
		Log.addTraceEND(CLASS_NAME, "createBug",id)
		return id
	}




	protected static String updateBug(String bugId, Map fields) {
		Log.addTraceBEGIN(CLASS_NAME, "updateBug", [bugId:bugId , fields:fields])
		def response = sendRequest(bugId, fields, 'PATCH')
		String id = response ? response.id : ''
		Log.addTraceEND(CLASS_NAME, "updateBug",id)
		return id
	}



	protected static Map readBug(String bugId) {
		def response = sendRequest(bugId, null, "GET")
		if (response != null) {
			return response
		} else {
			println "Could not read bug"
			return null
		}
	}



	public static String searchTNRNumber(String TNRNumber) {
		Log.addTraceBEGIN(CLASS_NAME, "searchTNRNumber", [TNRNumber:TNRNumber])
		// Préparer l'URL et le corps de la requête
		def urlString = "$BASE_URL/_apis/wit/wiql?api-version=$API_VERSION"
		println urlString

		def queryBody = """{
    "query": "SELECT [System.Id] FROM WorkItems 
		WHERE [System.State] <> 'Resolved'
		AND [Custom.1229225a-1e58-41aa-8fef-9b91528941bf] = '$TNRNumber'"
}"""


		def url = new URL(urlString)

		// Établir la connexion
		def conn = (HttpURLConnection) url.openConnection()
		conn.setRequestMethod("POST")
		conn.setRequestProperty("Content-Type", "application/json")
		conn.setRequestProperty("Authorization", AUTHORIZATION)
		conn.setDoOutput(true)

		// Envoyer le corps de la requête
		def writer = new OutputStreamWriter(conn.getOutputStream())
		writer.write(queryBody)
		writer.flush()
		writer.close()

		// Obtenir la réponse
		def responseCode = conn.getResponseCode()
		String bugId =''
		if (responseCode == 200) {
			def reader = new InputStreamReader(conn.getInputStream())
			def jsonResponse = reader.text
			println jsonResponse
			reader.close()

			def jsonSlurper = new JsonSlurper()
			def jsonObject = jsonSlurper.parseText(jsonResponse) as Map

			def workItems = jsonObject['workItems'] as List
			def numberOfIds = workItems.size()
			println "Nombre d'ID: ${numberOfIds}"

			workItems.each { workItem ->
				bugId = workItem['id'].toString()
			}
		} else {
			println("Failed : HTTP error code : ${responseCode}")
		}

		conn.disconnect()
		Log.addTraceEND(CLASS_NAME, "searchTNRNumber",bugId)
		return bugId
	}




}


