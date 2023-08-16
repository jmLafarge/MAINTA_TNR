
class DataProcessor {

	// Liste des cdt avec map nom et valeur
	List<Map<String, Map<String, String>>> datas = [
		[
			"AA.BBB.001.CRE.01": [
				"ID_JML": "JMLCRE01",
				"ST_DES": "DESJMLCRE01",
				"ST_INA": null
			]
		],
		[
			"AA.BBB.001.CRE.02": [
				"ID_JML": "JMLCRE02",
				"ST_DES": "DESJMLCRE02",
				"ST_INA": null
			]
		],
		[
			"AA.BBB.001.CRE.03": [
				"ID_JML": "JMLCRE03",
				"ST_DES": "DESJMLCRE03",
				"ST_INA": null
			]
		],
		[
			"AA.BBB.001.LEC.01": [
				"ID_JML": "JMLLEC11",
				"ST_DES": "DESJMLLEC11",
				"ST_INA": null
			]
		],
		[
			"AA.BBB.001.LEC.01": [
				"ID_JML": "JMLLEC12",
				"ST_DES": "DESJMLLEC12",
				"ST_INA": null
			]
		],
		[
			"AA.BBB.001.LEC.01": [
				"ID_JML": "JMLLEC13",
				"ST_DES": "DESJMLLEC13",
				"ST_INA": null
			]
		],
		[
			"AA.BBB.001.MAJ.01": [
				"ID_JML": "JMLMAJ01",
				"ST_DES": "DESJMLMAJ01",
				"ST_INA": null
			]
		],
		[
			"AA.BBB.001.SUP.01": [
				"ID_JML": "JMLMAJ02",
				"ST_DES": "DESJMLMAJ02",
				"ST_INA": null
			]
		]
	]




	def getData(String name, String cdt, int occurence) {
	    // Filtrer les éléments qui ont la clé 'cdt'
	    List<Map<String, Map<String, String>>> filtered = datas.findAll { it.containsKey(cdt) }
	    
	    // Si l'occurrence demandée est hors limites, retourner null
	    if (occurence <= 0 || occurence > filtered.size()) {
	        return null
	    }
	
	    // Récupérer l'occurence-ième entrée et retourner la valeur associée à 'name'
	    return filtered[occurence - 1][cdt][name]
	}
	
	
	
	def getCdtsContainingPatternWithoutDuplicates(String substring) {
	    // Rechercher les cdt qui contiennent la sous-chaîne
	    List<String> matchingCdts = datas.collect { it.keySet().find { k -> k.contains(substring) } }.findAll { it != null }
	
	    return matchingCdts.unique()
	}


	void setValueOf(String cdt, int occurence, String name, String value) {
		// Filtrer les entrées qui correspondent au cdt spécifié
		List<Map<String, Map<String, String>>> matchingCdtEntries = datas.findAll { it.containsKey(cdt) }
		
		// Vérifier si l'occurrence spécifiée est valide
		if(occurence <= 0 || occurence > matchingCdtEntries.size()) {
			println "Occurrence spécifiée non valide."
			return
		}
		
		// Récupérer l'entrée correspondante à l'occurrence spécifiée
		Map<String, Map<String, String>> entry = matchingCdtEntries[occurence - 1]
		
		// Mettre à jour la valeur de la clé 'name'
		entry[cdt][name] = value
	}


	
	

}

def processor = new DataProcessor()

// Utilisation de la fonction
def result = processor.getData("ST_DES", "AA.BBB.001.MAJ.01", 2)
if (result) {
	println "Résultat : ${result}"
} else {
	println "Aucun résultat trouvé."
}

println processor.getCdtsContainingPatternWithoutDuplicates("BBB.001")  // Devrait afficher la liste des cdt qui correspondent au motif

println processor.getData("ST_DES", "AA.BBB.001.LEC.01", 2)
processor.setValueOf("AA.BBB.001.LEC.01", 2, "ST_DES", "NewValue")
println processor.datas[4]["AA.BBB.001.LEC.01"]["ST_DES"]
println processor.getData("ST_DES", "AA.BBB.001.LEC.01", 2)

