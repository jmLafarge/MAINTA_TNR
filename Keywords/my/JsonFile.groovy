package my

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic

@CompileStatic
class JsonFile {
	private String filePath
	private Map varMap = [:]

	JsonFile(String filePath) {
		this.filePath = filePath
		def file = new File(filePath)
		if (!file.exists()) {
            file.createNewFile()
			setVar('TNRVARINFO','Ce fichier permet la sauvegarde de certaines variables')
		}
		def jsonSlurper = new JsonSlurper()
		varMap = jsonSlurper.parseText(file.text) as Map
	}

	String getVar(String name) {
		return varMap.getAt(name)
	}

	void setVar(String name, String var) {
		varMap.put(name, var)
		def jsonString = JsonOutput.toJson(varMap)
		new File(filePath).text = jsonString
	}
}
