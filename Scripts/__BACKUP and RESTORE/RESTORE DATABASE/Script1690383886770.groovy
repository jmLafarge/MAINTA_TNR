

import javax.swing.JFileChooser

import my.Log
import my.SQL


JFileChooser fileChooser = new JFileChooser(my.PropertiesReader.getMyProperty('LEGACY_BACKUPPATH'))
fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY)

// Ouvrir la boîte de dialogue de sélection de fichier
def ret = fileChooser.showOpenDialog(null)

if (ret == JFileChooser.APPROVE_OPTION) {

	 SQL.setNewInstance(SQL.AllowedDBProfilNames.LEGACYTNR)
	 SQL.restore(fileChooser.getSelectedFile().getPath())

} else {

	Log.addINFO("Aucun fichier sélectionné.")
}