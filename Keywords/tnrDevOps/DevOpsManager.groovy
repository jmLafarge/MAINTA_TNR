package tnrDevOps

import groovy.json.JsonBuilder
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
public class DevOpsManager {


	private static final String CLASS_NAME = 'DevOpsManager'


	public static void create() {
		
		// Paramètres pour l'API Azure DevOps
		def organization = "mainta"
		def project = "ba23da28-ae09-446a-afac-f19fb6bd8d4e"
		def apiVersion = "7.0"
		
		
		// Construire le corps de la requête
		def payload = new JsonBuilder([
			[
				"op": "add",
				"path": "/fields/System.Title",
				"from":null,
				"value": "TEST JML 001"
			],
		
			[
				"op": "add",
				"path": "/fields/System.AreaPath",
				"from":null,
				"value": "Héraclès\\GMAO"
			],
			[
				"op": "add",
				"path": "/fields/System.IterationPath",
				"value": "Héraclès\\GMAO\\MCO\\X3i\\Retour TNR X3i"
			]
			
		]).toString()
		
		
		
		// Créer l'URL
		URL url = new URL("https://dev.azure.com/${organization}/${project}/_apis/wit/workitems/\$Bug?api-version=${apiVersion}")
		
		// Ouvrir la connexion
		HttpURLConnection connection = (HttpURLConnection) url.openConnection()
		
		// Configurer la méthode et les headers
		connection.setRequestMethod("POST")
		connection.setRequestProperty("Content-Type", "application/json-patch+json")
		connection.setRequestProperty("Authorization", "Basic OjRvNGUzY3g2MndhcTRkeHh0bmxvbW9rczNzbXNqM2xueHF5NnRwZHBoampyY3c2Z2g0cnE=")
		
		connection.setDoOutput(true)
		
		// Envoyer la requête
		OutputStream os = connection.getOutputStream()
		os.write(payload.getBytes())
		os.flush()
		os.close()
		
		// Obtenir la réponse
		int responseCode = connection.getResponseCode()
		println("Response Code: " + responseCode)
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))
		String inputLine
		StringBuilder response = new StringBuilder()
		
		while ((inputLine = bufferedReader.readLine()) != null) {
			response.append(inputLine)
		}
		bufferedReader.close()
		
		// Analyse du JSON de la réponse
		JsonSlurper jsonSlurper = new JsonSlurper()
		Map parsedJson = jsonSlurper.parseText(response.toString()) as Map
		
		println("Response Data:")
		println(parsedJson)
		
		// Afficher l'ID du nouveau bug
		def newWorkItemId = parsedJson['id']
		println("ID du nouveau bug: $newWorkItemId")
		
		
	}


	public static void read(int id) {

		// Paramètres pour l'API Azure DevOps
		def organization 	= "mainta"
		def project 		= "ba23da28-ae09-446a-afac-f19fb6bd8d4e"
		def apiVersion 		= "7.0"

		// Votre Token Personnel Azure DevOps
		//def personalAccessToken = "mettre la PAT, ne pas oublier ':' devant"
		// Encodage du token personnel en Base64
		//def encodedPAT = Base64.getEncoder().encodeToString((personalAccessToken).getBytes("UTF-8"))
		//println encodedPAT
		//Mettre la clé coée directement dans setRequestProperty ... pour éviter de l'afficher en clair

		// Créer l'URL
		URL url = new URL("https://dev.azure.com/${organization}/${project}/_apis/wit/workitems/${id}?api-version=${apiVersion}")

		println "URL:$url"

		// Ouvrir la connexion
		HttpURLConnection connection = (HttpURLConnection) url.openConnection()

		// Configurer la méthode et les headers
		connection.setRequestMethod("GET")
		connection.setRequestProperty("Content-Type", "application/json")
		//connection.setRequestProperty("Authorization", "Basic " + encodedPAT)
		connection.setRequestProperty("Authorization", "Basic OjRvNGUzY3g2MndhcTRkeHh0bmxvbW9rczNzbXNqM2xueHF5NnRwZHBoampyY3c2Z2g0cnE=")

		// Obtenir la réponse
		int responseCode = connection.getResponseCode()

		println "responseCode : $responseCode"

		if (responseCode==200) {

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))
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
	}


}
