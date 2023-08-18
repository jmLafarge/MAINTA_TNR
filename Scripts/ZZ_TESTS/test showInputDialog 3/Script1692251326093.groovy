import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JButton
import java.awt.GridLayout
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.main.TestCaseExecutor

// Fonction pour exécuter un script donné par son nom
def executeScript(String scriptName) {
	// Exécute le script spécifié par son nom
	//TestCaseExecutor.getInstance().runTestCase(RunConfiguration.getProjectDir() + "/Test Cases/" + scriptName)
	println "Lancement de $scriptName"
}

// Options du menu
def menuOptions = ["Script1", "Script2", "Script3", "Quitter"]

// Créer un JPanel pour contenir les boutons disposés verticalement
JPanel panel = new JPanel(new GridLayout(menuOptions.size(), 1))

// Ajouter chaque bouton au JPanel
menuOptions.each { option ->
	JButton button = new JButton(option)
	panel.add(button)
}

// Afficher le menu dans une boîte de dialogue personnalisée
int selectedOptionIndex = JOptionPane.showOptionDialog(
	null,
	panel,
	"Menu des Scripts",
	JOptionPane.DEFAULT_OPTION,
	JOptionPane.PLAIN_MESSAGE,
	null,
	null,
	null
)

// Gérer la sélection de l'utilisateur
if (selectedOptionIndex >= 0 && selectedOptionIndex < menuOptions.size()) {
	def selectedOption = menuOptions[selectedOptionIndex]
	if (selectedOption == "Quitter") {
		println("Menu fermé.")
	} else {
		executeScript("NomDuScript" + (selectedOptionIndex + 1))
	}
} else {
	println("Menu fermé.")
}
