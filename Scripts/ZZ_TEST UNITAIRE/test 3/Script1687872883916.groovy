List listeDeListes = [
	['1', 'X', 'A'],
	['1', 'X', 'A'],
	['3', 'Y', 'A']
]

String v1='toto'
String v2='titi'
String v3='tata'

println listeDeListes

List li = []

li.addAll([v1, v2, v3])
println li

listeDeListes.add(li)
println listeDeListes