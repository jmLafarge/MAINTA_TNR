package tnrDevOps


import org.apache.http.client.methods.HttpPatch
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients

@Grab(group='org.apache.httpcomponents', module='httpclient', version='4.5.13')

import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import tnrCommon.Tools



/**
 *
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

@CompileStatic
public class OLD_________DevOps {


	private static final String CLASS_NAME = 'DevOpsManager'


	// Paramètres pour l'API Azure DevOps
	private static final String ORGANIZATION = "mainta"
	private static final String PROJECT = "ba23da28-ae09-446a-afac-f19fb6bd8d4e"
	private static final String API_VERSION = "7.0"

	private static final String AUTHORIZATION = "Basic OjRvNGUzY3g2MndhcTRkeHh0bmxvbW9rczNzbXNqM2xueHF5NnRwZHBoampyY3c2Z2g0cnE="






	public static void read(int id) {


		// Votre Token Personnel Azure DevOps
		//def personalAccessToken = "mettre la PAT, ne pas oublier ':' devant"
		// Encodage du token personnel en Base64
		//def encodedPAT = Base64.getEncoder().encodeToString((personalAccessToken).getBytes("UTF-8"))
		//println encodedPAT
		//Mettre la clé coée directement dans setRequestProperty ... pour éviter de l'afficher en clair

		// Créer l'URL
		URL url = new URL("https://dev.azure.com/${ORGANIZATION}/${PROJECT}/_apis/wit/workitems/${id}?api-version=${API_VERSION}")

		println "URL:$url"

		// Ouvrir la connexion
		HttpURLConnection conn = (HttpURLConnection) url.openConnection()

		// Configurer la méthode et les headers
		conn.setRequestMethod("GET")
		conn.setRequestProperty("Content-Type", "application/json")
		//connection.setRequestProperty("Authorization", "Basic " + encodedPAT)
		conn.setRequestProperty("Authorization", AUTHORIZATION)

		// Obtenir la réponse
		int responseCode = conn.getResponseCode()

		println "responseCode : $responseCode"

		if (responseCode==200) {

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))
			String inputLine
			StringBuilder response = new StringBuilder()

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine)
			}
			br.close()

			// Analyse du JSON de la réponse
			JsonSlurper jsonSlurper = new JsonSlurper()
			Map parsedJson = jsonSlurper.parseText(response.toString()) as Map

			println("Response Data:")

			println(parsedJson)

			println Tools.displayWithQuotes(parsedJson)

			// Afficher l'ID, le titre et le statut
			def workItemId = parsedJson['id']
			def workItemTitle = parsedJson['fields']['System.Title']
			def workItemStatus = parsedJson['fields']['System.State']

			println("ID: $workItemId")
			println("Title: $workItemTitle")
			println("Status: $workItemStatus")

		}else {
			println "La lecture "
		}

		conn.disconnect()
	}




	public static void searchTNRNumber(String TNRNumber) {

		// Préparer l'URL et le corps de la requête
		def urlString = "https://dev.azure.com/$ORGANIZATION/$PROJECT/_apis/wit/wiql?api-version=$API_VERSION"
		println urlString

		def queryBody = """{
	        "query": "SELECT [System.Id] FROM WorkItems 
				WHERE [System.State] <> 'Resolved'
				AND [Custom.1229225a-1e58-41aa-8fef-9b91528941bf] <> ''"
	    }"""


		println queryBody
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
				println "ID: ${workItem['id']}"
			}
		} else {
			println("Failed : HTTP error code : ${responseCode}")
		}

		conn.disconnect()

	}







	public static void searchAllTNRNumber() {
		// 1ère requête pour obtenir les ID des WorkItems
		def urlString = "https://dev.azure.com/$ORGANIZATION/$PROJECT/_apis/wit/wiql?api-version=$API_VERSION"
		def queryBody = """{
        "query": "SELECT [System.Id] FROM WorkItems 
			WHERE [System.WorkItemType] = 'Bug' AND [System.State] <> 'Resolved' AND [Custom.1229225a-1e58-41aa-8fef-9b91528941bf] <> ''"
    }"""
		def ids = getWorkItemIds(urlString, queryBody)

		// 2ème requête pour obtenir les détails des WorkItems
		def detailsUrl = "https://dev.azure.com/$ORGANIZATION/$PROJECT/_apis/wit/workitems?ids=${ids.join(",")}&fields=System.Id,Custom.1229225a-1e58-41aa-8fef-9b91528941bf&api-version=$API_VERSION"
		def details = getWorkItemDetails(detailsUrl)

		println("Détails des WorkItems : $details")
	}

	public static List<Integer> getWorkItemIds(String urlString, String queryBody) {
		def conn = setupConnection(urlString, "POST")
		sendRequest(conn, queryBody)
		def jsonResponse = getResponse(conn)
		conn.disconnect()

		def parsedJson = new JsonSlurper().parseText(jsonResponse) as Map
		def ids = parsedJson.workItems.collect { it['id'] } as List
		println("Nombre de WorkItems : ${ids.size()}")
		println("ID des WorkItems : $ids")

		return ids
	}

	public static Map<Integer, String> getWorkItemDetails(String urlString) {
		def conn = setupConnection(urlString, "GET")
		def jsonResponse = getResponse(conn)
		conn.disconnect()

		def parsedJson = new JsonSlurper().parseText(jsonResponse)
		println("parsedJson: $parsedJson")
		def workItems = parsedJson['value'] as List<Map>
		println("workItems: $workItems")
		def details = [:]
		if (workItems != null) {
			for(item in workItems) {
				def fields = item['fields'] as Map
				def id = fields ? fields['System.Id'] : null
				def customField = fields ? fields['Custom.1229225a-1e58-41aa-8fef-9b91528941bf'] : null
				if (id && customField) {
					details[id] = customField
				}
			}
		} else {
			println("workItems est null.")
		}

		return details
	}

	public static HttpURLConnection setupConnection(String urlString, String requestMethod) {
		def url = new URL(urlString)
		HttpURLConnection conn = (HttpURLConnection) url.openConnection()
		conn.setRequestMethod(requestMethod)
		conn.setRequestProperty("Content-Type", "application/json")
		conn.setRequestProperty("Authorization", AUTHORIZATION)
		conn.setDoOutput(true)
		return conn
	}

	public static void sendRequest(HttpURLConnection conn, String body) {
		def writer = new OutputStreamWriter(conn.getOutputStream())
		writer.write(body)
		writer.flush()
		writer.close()
	}

	public static String getResponse(HttpURLConnection conn) {
		def responseCode = conn.getResponseCode()
		if (responseCode == 200) {
			def reader = new InputStreamReader(conn.getInputStream())
			def jsonResponse = reader.text
			reader.close()
			return jsonResponse
		} else {
			println("Failed : HTTP error code : $responseCode")
			return null
		}
	}
















}
