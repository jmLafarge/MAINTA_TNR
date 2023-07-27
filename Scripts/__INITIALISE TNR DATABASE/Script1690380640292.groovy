
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

import javax.swing.JOptionPane

import my.Log
import my.PREJDDFiles
import my.PropertiesReader
import my.SQL
import my.Tools



	def deleteFileIfExist(String filenamePath) {
		Log.addTraceBEGIN("deleteFileIfExist(String '$filenamePath')")
		File file = new File(filenamePath)
		if (file.exists()) {
			Log.addINFO("Le fichier existe déjà, demande de suppression ?")
			int option = JOptionPane.showConfirmDialog(
				null,
				"Le fichier '$filenamePath' existe déjà, voulez-vous le supprimer ?",
				"Confirmation de suppression",
				JOptionPane.YES_NO_OPTION
			)
					
			if (option == JOptionPane.YES_OPTION) {
				if (file.delete()) {
					Log.addDETAIL("Le fichier a été supprimé avec succès.")
				} else {
					Log.addERROR("Impossible de supprimer le fichier ! ARRET DU PROGRAMME")
					System.exit(0)
				}
			}else {
				Log.addINFO("Suppression annulée ! ARRET DU PROGRAMME")
				System.exit(0)
			}
		}
		Log.addTraceEND("deleteFileIfExist()")
	}




Log.addINFO("Sauvegarde de la BDD MASTER_TNR")
SQL.setNewInstance(SQL.AllowedDBProfilNames.MASTERTNR)
String backupFilename = "${SQL.getDatabaseName()}_${SQL.getMaintaVersion()}.bak"
String backupFilenamePath = PropertiesReader.getMyProperty('MASTERTNR_DBBACKUPPATH') + File.separator + backupFilename

deleteFileIfExist(backupFilenamePath)

SQL.backup(backupFilename)
SQL.sqlInstance.close()


Log.addINFO("Move du fichier de backup dans le dossier du projet")
String dest = PropertiesReader.getMyProperty('DBBACKUP_PATH') + File.separator + backupFilename
Files.move(Paths.get(backupFilenamePath), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING)

backupFilenamePath = PropertiesReader.getMyProperty('DBBACKUP_PATH') + File.separator + backupFilename


Log.addINFO("Copie le fichier de backup du projet sur le server de TNR")

dest = PropertiesReader.getMyProperty('LEGACYTNR_DBBACKUPPATH') + File.separator + backupFilename
deleteFileIfExist(dest)

Files.copy(Paths.get(backupFilenamePath), Paths.get(dest))


Log.addINFO("Restaure la BDD TNR")
SQL.setNewInstance()
SQL.restore(backupFilename)
SQL.sqlInstance.close()
SQL.setNewInstance()

Log.addINFO("Supprime le le fichier de backup")
Tools.deleteFile(dest)


Log.addINFO("Création des PREJDD")
PREJDDFiles.createInDB()


Log.addINFO("Sauvegarde de la BDD TNR avec les PREJDD")

backupFilenamePath = PropertiesReader.getMyProperty('LEGACYTNR_DBBACKUPPATH') + File.separator + backupFilename

SQL.backup(backupFilenamePath)
SQL.sqlInstance.close()


Log.addINFO("Déplacement du fichier de backup dans le dossier du projet")
dest = PropertiesReader.getMyProperty('DBBACKUP_PATH') + File.separator + backupFilename
Files.move(Paths.get(backupFilenamePath), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING)


