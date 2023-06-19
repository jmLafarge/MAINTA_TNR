

def chaine = "'toto.titi.tata' - 'EUR'"
def valeurs = chaine.split("'")

println valeurs.size()

println valeurs

println valeurs[3]



if (valeurs.size() >= 3) {
    def deuxiemeValeur = valeurs[1]
    println deuxiemeValeur
} else {
    println "Aucune correspondance trouvée."
}


