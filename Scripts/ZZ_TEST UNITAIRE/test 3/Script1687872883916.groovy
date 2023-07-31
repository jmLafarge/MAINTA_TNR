
def myMap = [nom: "John", age: 30, ville: "New York"]

String resultat = myMap.collect { cle, valeur -> "$cle: '$valeur'" }.join(', ')
println(resultat)


