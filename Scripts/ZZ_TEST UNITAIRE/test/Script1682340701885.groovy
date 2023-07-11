def str = '$UPD$ss$qq'

if (str.matches('\\$UPD\\$.*\\$.*')) {
    println("La chaîne de caractères est au format attendu.")
} else {
    println("La chaîne de caractères n'est pas au format attendu.")
}



def parts = str.split('\\$')

println parts

if (parts.size() == 4 && parts[0] == "" && parts[1] == "UPD" && parts[2] && parts[3] ) {
	println("La chaîne de caractères est au format attendu.")
} else {
	println("La chaîne de caractères n'est pas au format attendu.")
}
