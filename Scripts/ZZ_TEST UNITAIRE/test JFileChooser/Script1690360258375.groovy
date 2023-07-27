import javax.swing.JFileChooser

// Sélectionner le dossier dans lequel vous voulez lister les fichiers
def dossier = my.PropertiesReader.getMyProperty('LOCALTNR_BDDBACKUPPATH')

// Créer une boîte de dialogue de sélection de fichier
JFileChooser fileChooser = new JFileChooser(dossier)
fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY)

// Ouvrir la boîte de dialogue de sélection de fichier
def choix = fileChooser.showOpenDialog(null)

if (choix == JFileChooser.APPROVE_OPTION) {
	// L'utilisateur a sélectionné un fichier
	def fichierSelectionne = fileChooser.getSelectedFile()
	println("Fichier sélectionné : " + fichierSelectionne.getAbsolutePath())
} else {
	// L'utilisateur a annulé la sélection du fichier
	println("Aucun fichier sélectionné.")
}
