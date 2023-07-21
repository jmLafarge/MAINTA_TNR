
import javax.swing.JOptionPane

// Poser une question à l'utilisateur et récupérer sa réponse
String userInput = JOptionPane.showInputDialog("Quel est votre nom ?")

// Vérifier si l'utilisateur a cliqué sur "OK" ou "Annuler"
if (userInput == null) {
    // L'utilisateur a cliqué sur "Annuler"
    println("Vous avez annulé la saisie.")
} else {
    // L'utilisateur a saisi une réponse
    println("Bonjour, ${userInput} !")
}

