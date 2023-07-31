
/*
println my.PropertiesReader.getMyProperty('INFODBFILENAME') // ok
println "'"+my.PropertiesReader.getMyProperty('TEST')+"'" // c'et vide quand pas de valeur même si pas de =
println my.PropertiesReader.getMyProperty('EXISTEPAS') // c'est null quand existe pas
*/



/*
String debugClassList = my.PropertiesReader.getMyProperty('LOG_DEBUGCLASSES')
println debugClassList
List <String> debugClassesExcluded = []
List <String> debugClassesAdded = []
if (debugClassList) {
	debugClassList = debugClassList.replaceAll("[\\s\\t]+", "") // Supprime les espaces et les tabulations
	List <String> classList = debugClassList.split(',') as List
	debugClassesExcluded = classList.findAll { it[0] == '-' }.collect { it.substring(1) }
	debugClassesAdded = classList.findAll { it[0] == '+' }.collect { it.substring(1) }
}

println debugClassesExcluded

println debugClassesAdded
*/


String debugClassList = my.PropertiesReader.getMyProperty('LOG_DEBUGFUNCTION')
println debugClassList
List <String> debugClassesExcluded = []
List <String> debugClassesAdded = []
if (debugClassList) {
	debugClassList = debugClassList.replaceAll("[\\s\\t]+", "") // Supprime les espaces et les tabulations
	List <String> classList = debugClassList.split(',') as List
	debugClassesExcluded = classList.findAll { it[0] == '-' }.collect { it.substring(1) }
	debugClassesAdded = classList.findAll { it[0] == '+' }.collect { it.substring(1) }
}


println debugClassesExcluded

println debugClassesAdded