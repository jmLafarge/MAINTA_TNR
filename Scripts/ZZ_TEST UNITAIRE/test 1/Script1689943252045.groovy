


def boolean isTraceAuthorized(String msg, int level) {
	
	def classList = ['-XLS', '+SQL', '+Ajout', '-Int']
	
	def debugClassesExcluded = classList.findAll { it[0] == '-' }.collect { it.substring(1) }
	def debugClassesAdded = classList.findAll { it[0] == '+' }.collect { it.substring(1) }
	
	
		boolean ret =  (level <= 10)
		def startsWithExcluded = debugClassesExcluded.any { msg.startsWith(it) }
		def startsWithAdded = debugClassesAdded.any { msg.startsWith(it) }
		
		println startsWithExcluded
		println startsWithAdded
		
		if (startsWithExcluded) {
			ret = false
		}else if (startsWithAdded) {
			ret= true
		}
		return ret
	}
		
println isTraceAuthorized('SQLkjlkjl',1)		
println isTraceAuthorized('SQcLkjlkjl',1)

println isTraceAuthorized('XLSkjlkjl',1)
println isTraceAuthorized('SXLSQcLkjlkjl',1)