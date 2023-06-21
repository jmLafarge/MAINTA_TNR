
def liste = ['1X', '1X', '3Y', '4Y', '1Y', '1X', '4Y']

def doublons = []
liste.eachWithIndex { element, index ->
	if (liste.lastIndexOf(element) != index && !doublons.contains(index)) {
		doublons.add(index)
	}
}

println doublons