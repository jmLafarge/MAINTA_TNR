
def myList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

// Récupérez les éléments de la liste de l'index 2 (inclus) à l'index 7 (non inclus)
def subList = myList.subList(2, 7)

// Affichez les éléments de la sous-liste
println(subList)

String myString = '$Hello$world'
def parts = myString.split('\\$')
println parts[1]
println parts[2]
println parts.size()
