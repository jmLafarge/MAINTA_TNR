package my

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

import groovy.transform.CompileStatic

@CompileStatic
public class RestoreDB {


	private static final String DBBACKUP_PATH 			= PropertiesReader.getMyProperty('DBBACKUP_PATH')
	private static final String MASTERTNR_DBBACKUPPATH 	= PropertiesReader.getMyProperty('MASTERTNR_DBBACKUPPATH')

	static run() {
		Log.addTraceBEGIN("RestoreDB.run()")
		List <String > backupFileList = Tools.getFilesFromFolder('^\\d{8}_\\d{6}-' + SQL.getProfileName() +'_' + SQL.getDatabaseName() + '.*\\.bak$',DBBACKUP_PATH)
		if (backupFileList.size() == 1) {
			restoreTNR(backupFileList[0])
		}else {
			initTNR()
		}
		Log.addTraceEND("RestoreDB.run()")
	}



	private static restoreTNR(String backupFilename) {
		Log.addTraceBEGIN("RestoreDB.restoreTNR('$backupFilename')")
		String origin = ''
		String dest = ''
		Log.addSubTITLE("Restauration de la BDD de test")
		Log.addDETAIL("Copie le fichier de backup du projet sur le server de TNR")

		origin = DBBACKUP_PATH + File.separator + backupFilename
		dest = SQL.getDBBackupPath() + File.separator + backupFilename
		Files.copy(Paths.get(origin), Paths.get(dest))


		Log.addDETAIL("Restaure la BDD TNR")

		SQL.restore(backupFilename)
		SQL.close()
		SQL.setNewInstance()


		Log.addDETAIL("Supprime le fichier de backup")

		Tools.deleteFilesFromFolder(dest)
		
		Log.addTraceEND("RestoreDB.restoreTNR()")
	}



	private static initTNR() {
		Log.addTraceBEGIN("RestoreDB.initTNR()")
		String origin = ''
		String dest = ''
		String backupFilename = ''

		Log.addSubTITLE("Initialisation complète de la BDD de test")

		Log.addDETAIL("Supprimer les anciens fichiers de backup du dossier projet")

		Tools.deleteFilesFromFolder('^\\d{8}_\\d{6}-.*\\.bak$',DBBACKUP_PATH)


		Log.addDETAIL("Sauvegarde de la BDD MASTER_TNR")

		SQL.setNewInstance(SQL.AllowedDBProfilNames.MASTERTNR)
		backupFilename =SQL.backup()
		SQL.close()
		SQL.setNewInstance()


		Log.addDETAIL("Déplacement du fichier de backup dans le dossier du projet")

		origin = MASTERTNR_DBBACKUPPATH + File.separator + backupFilename
		dest = DBBACKUP_PATH + File.separator + backupFilename
		Files.move(Paths.get(origin), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING)


		Log.addDETAIL("Copie du fichier de backup du projet sur le server de TNR")

		origin = DBBACKUP_PATH + File.separator + backupFilename
		dest = SQL.getDBBackupPath() + File.separator + backupFilename
		Files.copy(Paths.get(origin), Paths.get(dest))


		Log.addDETAIL("Restaure la BDD TNR")

		SQL.restore(backupFilename)
		SQL.close()
		SQL.setNewInstance()


		Log.addDETAIL("Supprime le le fichier de backup")

		Tools.deleteFilesFromFolder(dest)


		Log.addDETAIL("Création des PREJDD")

		//PREJDDFiles.createInDB()


		Log.addDETAIL("Sauvegarde de la BDD TNR avec les PREJDD")

		backupFilename = SQL.backup()


		Log.addDETAIL("Déplacement du fichier de backup dans le dossier du projet")

		origin = SQL.getDBBackupPath() + File.separator + backupFilename
		dest = DBBACKUP_PATH + File.separator + backupFilename
		Files.move(Paths.get(origin), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING)

		Log.addTraceEND("RestoreDB.initTNR()")
	}

}