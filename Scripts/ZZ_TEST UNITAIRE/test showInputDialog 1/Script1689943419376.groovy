import javax.swing.JOptionPane

// Ouvrir une boîte de dialogue pour saisir un nom
String nom = JOptionPane.showInputDialog(null, "Veuillez saisir votre nom:", "Saisie du nom", JOptionPane.QUESTION_MESSAGE)

// Vérifier si l'utilisateur a cliqué sur le bouton "OK" ou "Annuler"
if (nom != null) {
    // Si l'utilisateur a saisi un nom et a cliqué sur "OK"
    println("Nom saisi : " + nom)
} else {
    // Si l'utilisateur a cliqué sur "Annuler" ou a fermé la boîte de dialogue
    println("Aucun nom saisi.")
}

