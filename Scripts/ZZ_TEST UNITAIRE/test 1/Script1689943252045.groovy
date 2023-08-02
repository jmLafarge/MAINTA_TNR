import groovy.transform.CompileStatic

@CompileStatic

def maFonction(String param1 = null, def param2, boolean param3 = false) {
    println "Paramètre 1 : ${param1}"
    println "Paramètre 2 : ${param2}"
    println "Paramètre 3 : ${param3}"
}

// Appel de la fonction avec les paramètres 1 et 3 (le deuxième paramètre est omis en passant null)
maFonction("valeur1", null, true)

