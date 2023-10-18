

static void attachBugToTask(String taskId, String bugId) {
	Log.addTraceBEGIN(CLASS_NAME, "attachBugToTask", [taskId: taskId, bugId: bugId])
	
	def relation = [
		"rel": "System.LinkTypes.Hierarchy-Forward",  // pour un lien parent-enfant
		"url": "https://dev.azure.com/$ORGANIZATION/$PROJECT/_apis/wit/workItems/$bugId"
	]
	
	def fields = [
		"/relations/-": relation
	]
	
	// Utilisez votre fonction sendRequest existante avec un argument supplémentaire pour le chemin spécial
	def response = sendRequest(taskId, fields, 'PATCH', true)
	
	String id = response ? response.id : ''
	Log.addTraceEND(CLASS_NAME, "attachBugToTask", id)
}

// Mettez à jour la signature de votre fonction sendRequest pour accepter un argument de chemin spécial
private static Map sendRequest(def type, Map fields, String method, boolean fullPath = false) {
	// ... (le reste du code est inchangé)
	
	if (fields) {
		def body = JsonOutput.toJson(transformFields(fields, fullPath))
		// ... (le reste du code est inchangé)
	}
	// ...
}

// Mettez à jour la signature de votre fonction transformFields pour accepter un argument de chemin spécial
private static List<Map> transformFields(Map fields, boolean fullPath = false) {
	return fields.collect { key, value ->
		def path = fullPath ? "$key" : "/fields/$key"
		[
			"op": "add",
			"path": path,
			"value": value
		]
	} as List
}
