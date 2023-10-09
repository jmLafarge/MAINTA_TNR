package tnrDevOps

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
public class DevOpsManager {





	public static void create() {



	}

	
	public static void read(int id) {

		// Paramètres pour l'API Azure DevOps
		def organization 	= "mainta"
		def project 		= "ba23da28-ae09-446a-afac-f19fb6bd8d4e"
		def apiVersion 		= "7.0"
		
		// Votre Token Personnel Azure DevOps
		def personalAccessToken = "vzhujvbyz6dckckj5zbjppgjiqjocdkxgu4cbfzo756cncepglvq" 
		
		// Encodage du token personnel en Base64
		def encodedPAT = Base64.getEncoder().encodeToString((personalAccessToken).getBytes("UTF-8"))
		
		// Créer l'URL
		URL url = new URL("https://dev.azure.com/${organization}/${project}/_apis/wit/workitems/${id}?api-version=${apiVersion}")
		
		println "URL:$url"

		// Ouvrir la connexion
		HttpURLConnection connection = (HttpURLConnection) url.openConnection()

		// Configurer la méthode et les headers
		connection.setRequestMethod("GET")
		connection.setRequestProperty("Content-Type", "application/json")
		//connection.setRequestProperty("Authorization", "Basic " + encodedPAT)
		connection.setRequestProperty("Authorization", "Basic dWdsMzRibXlzaHc3YXVrc2VxcHVzNW9mYXUzb3Z6N2R0dWpzNmI1NW41NWwzN2loYXdtcTo=")
		
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
