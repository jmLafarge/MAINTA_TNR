import javax.swing.JOptionPane
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.main.TestCaseExecutor

// Fonction pour exécuter un script donné par son nom
def executeScript(String scriptName) {
    // Exécute le script spécifié par son nom
    TestCaseExecutor.getInstance().runTestCase(RunConfiguration.getProjectDir() + "/Test Cases/" + scriptName)
}

// Options du menu
String[] options = ["Script1", "Script2", "Script3", "Quitter"]

// Afficher le menu
String selectedOption = JOptionPane.showInputDialog(null, "Sélectionnez un script à exécuter :", "Menu des Scripts", JOptionPane.QUESTION_MESSAGE, null, options, options[0])

// Gérer la sélection de l'utilisateur
if (selectedOption) {
    switch (selectedOption) {
        case "Script1":
            executeScript("NomDuScript1")
            break
        case "Script2":
            executeScript("NomDuScript2")
            break
        case "Script3":
            executeScript("NomDuScript3")
            break
        case "Quitter":
            println("Menu fermé.")
            break
        default:
            println("Option invalide.")
            break
    }
} else {
    println("Menu fermé.")
}