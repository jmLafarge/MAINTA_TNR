List<String> maListe = ['apple', 'orange', 'apple', 'banana', 'orange', 'pear']

def occurrences = maListe.groupBy { it }
def result = occurrences.findAll { k, v -> v.size() > 1 }
						.collect { k, v -> [k, v.size()] }

println("Éléments avec plus d'une occurrence :")
result.each {
	println("Élément: ${it[0]}, Nombre d'occurrences: ${it[1]}")
}
