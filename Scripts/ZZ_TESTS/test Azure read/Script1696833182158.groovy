import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import groovy.json.JsonSlurper

// L'URL de l'API Azure DevOps
URL apiUrl = new URL("https://dev.azure.com/mainta/ba23da28-ae09-446a-afac-f19fb6bd8d4e/_apis/wit/wiql?api-version=7.0")

// Ouvrir la connexion
HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection()

// Configurer la requête
conn.setRequestMethod("POST")
conn.setRequestProperty("Content-Type", "application/json")
conn.setRequestProperty("Authorization", "Basic dWdsMzRibXlzaHc3YXVrc2VxcHVzNW9mYXUzb3Z6N2R0dWpzNmI1NW41NWwzN2loYXdtcTo=")
conn.setDoOutput(true)

// Corps de la requête
String body = '''{
    "query": "SELECT [System.Id], [System.Title], [System.State] FROM WorkItems WHERE [System.WorkItemType] = 'Bug' AND [System.IterationPath] = 'Héraclès\\\\GMAO\\\\MCO\\\\X3i\\\\Retour TNR X3i'"
}'''

// Envoyer la requête
OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())
writer.write(body)
writer.flush()

// Obtenir la réponse
BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))
String line
StringBuilder response = new StringBuilder()
while ((line = reader.readLine()) != null) {
	response.append(line)
}
writer.close()
reader.close()

// Parser le JSON
JsonSlurper jsonSlurper = new JsonSlurper()
def result = jsonSlurper.parseText(response.toString())

// Extraire les work items
def workItems = result.workItems

// Afficher les titres de colonnes
println "| ID | URL |"

// Afficher les séparateurs
println "|----|-----|"

// Itérer sur chaque work item et afficher les détails
workItems.each { workItem ->
    def workItemID = workItem.id
    def workItemUrl = workItem.url
    
    println "| ${workItemID} | ${workItemUrl} |"
}