
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.Log
import my.PREJDDFiles
import my.PropertiesReader
import my.SQL
import my.Tools

String backupFilename = ''
String origin = ''
String dest = ''

String DBBACKUP_PATH 			= PropertiesReader.getMyProperty('DBBACKUP_PATH')
String MASTERTNR_DBBACKUPPATH 	= PropertiesReader.getMyProperty('MASTERTNR_DBBACKUPPATH')



Log.addINFO("Supprimer les anciens fichiers de backup du dossier projet")

	


Log.addINFO("Sauvegarde de la BDD MASTER_TNR")

	SQL.setNewInstance(SQL.AllowedDBProfilNames.MASTERTNR)
	backupFilename =SQL.backup()
	SQL.sqlInstance.close()
	SQL.setNewInstance()


Log.addINFO("Move du fichier de backup dans le dossier du projet")

	origin = MASTERTNR_DBBACKUPPATH + File.separator + backupFilename
	dest = DBBACKUP_PATH + File.separator + backupFilename
	Files.move(Paths.get(origin), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING)


Log.addINFO("Copie le fichier de backup du projet sur le server de TNR")

	origin = DBBACKUP_PATH + File.separator + backupFilename
	dest = SQL.getDBBackupPath() + File.separator + backupFilename
	Files.copy(Paths.get(origin), Paths.get(dest))

	
Log.addINFO("Restaure la BDD TNR")

	SQL.restore(backupFilename)
	SQL.sqlInstance.close()
	SQL.setNewInstance()

	
Log.addINFO("Supprime le le fichier de backup")

	Tools.deleteFile(dest)


Log.addINFO("Création des PREJDD")

	//PREJDDFiles.createInDB()


Log.addINFO("Sauvegarde de la BDD TNR avec les PREJDD")

	backupFilename = SQL.backup()


Log.addINFO("Déplacement du fichier de backup dans le dossier du projet")

	origin = SQL.getDBBackupPath() + File.separator + backupFilename
	dest = DBBACKUP_PATH + File.separator + backupFilename
	Files.move(Paths.get(origin), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING)



