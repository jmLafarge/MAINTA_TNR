
def currentValue = 0
def maxValue = 0

// Simulez l'évolution de la variable (vous devrez mettre à jour cette partie avec votre propre logique)
for (int i = 1; i <= 10; i++) {
    // Mettre à jour la valeur actuelle (simulée)
    currentValue = i * 5

    // Mettre à jour la valeur maximale si la nouvelle valeur est plus grande
    maxValue = (currentValue > maxValue) ? currentValue 
}

println "Valeur maximale atteinte : ${maxValue}"


