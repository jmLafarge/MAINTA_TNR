List listeDeListes = [
    ['1', 'X', 'A', 'A1', 'A4', '1.1'],
    ['1', 'X', 'A', 'A1', 'A3', '1.2'],
    ['3', 'Y', 'A', 'A1', 'A2', '1.3'],
    ['4', 'Y', 'A', 'A1', 'A1', '1.4'],
    ['1', '$Y', 'A', 'A1', 'A2', '1.5'],
    ['$1', 'X', 'A', 'A1', 'A3', '1.6'],
    ['4', 'Y', 'A', 'A1', 'A4', '1.7'],
]

List listPKi = [0,1]

println listPKi.size()

List li = []

println li.size()

def PKdatas = listeDeListes.collect { ligne -> listPKi.collect { ligne[it] }.join('') }

println PKdatas

def PKdatas2 = listeDeListes.findAll { ligne ->
	!listPKi.collect { ligne[it] }.any { it.startsWith('$') }
}.collect { ligne ->
	listPKi.collect { ligne[it] }.join('')
}

println PKdatas2