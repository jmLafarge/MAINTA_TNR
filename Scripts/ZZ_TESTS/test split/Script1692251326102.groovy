def str = '$TBD'
	def delimiter = '\\$'
	
	def parts = str.split('\\$')
	
	// Affichage des parties divisées
	parts.each { println it }
	
	println parts.size()
	
	if (parts.size()==3) {
		println parts[2]
	}