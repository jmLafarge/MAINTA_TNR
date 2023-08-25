package tnrSqlManager

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

import groovy.transform.CompileStatic
import tnrCommon.FileUtils
import tnrCommon.TNRPropertiesReader
import tnrLog.Log
import tnrPREJDDManager.PREJDDFileMapper
import tnrWebUI.NAV

@CompileStatic
public class RestoreDB {

	private static final String CLASS_FORLOG = 'RestoreDB'

	private static final String DBBACKUP_PATH 			= TNRPropertiesReader.getMyProperty('DB_BACKUP_PATH')
	private static final String MASTERTNR_DBBACKUPPATH 	= TNRPropertiesReader.getMyProperty('MASTERTNR_DBBACKUPPATH')

	static run() {
		Log.addTraceBEGIN(CLASS_FORLOG,"run",[:])

		NAV.myGlobalJDD

		List <String > backupFileList = FileUtils.getFilesFromFolder('^\\d{8}_\\d{6}-' + SQL.getProfileName() +'_' + SQL.getDatabaseName() + '.*\\.bak$',DBBACKUP_PATH)
		if (backupFileList.size() == 1) {
			restoreTNR(backupFileList[0])
		}else {
			initTNR()
		}

		// Ajouter le recyclage

		Log.addTraceEND(CLASS_FORLOG,"run")
	}



	private static restoreTNR(String backupFilename) {
		Log.addTraceBEGIN(CLASS_FORLOG,"restoreTNR",[backupFilename:backupFilename])
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

		FileUtils.deleteFilesFromFolder(dest)

		Log.addTraceEND(CLASS_FORLOG,"restoreTNR")
	}





	private static initTNR() {
		Log.addTraceBEGIN(CLASS_FORLOG,"initTNR",[:])
		String origin = ''
		String dest = ''
		String backupFilename = ''

		Log.addSubTITLE("Initialisation complète de la BDD de test")

		Log.addDETAIL("Supprimer les anciens fichiers de backup du dossier projet")

		FileUtils.deleteFilesFromFolder('^\\d{8}_\\d{6}-.*\\.bak$',DBBACKUP_PATH)


		Log.addDETAIL("Sauvegarde de la BDD MASTER_TNR")

		SQL.setNewInstance(SQL.AllowedDBProfilNames.MASTERTNR)
		backupFilename =SQL.backup()
		SQL.close()
		SQL.setNewInstance()


		Log.addDETAIL("Déplacement du fichier de backup dans le dossier du projet")

		origin = MASTERTNR_DBBACKUPPATH + File.separator + backupFilename
		dest = DBBACKUP_PATH + File.separator + backupFilename
		Files.move(Paths.get(origin), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING)

		//

		Log.addDETAIL("Copie du fichier de backup du projet sur le server de TNR")

		origin = DBBACKUP_PATH + File.separator + backupFilename
		dest = SQL.getDBBackupPath() + File.separator + backupFilename
		Files.copy(Paths.get(origin), Paths.get(dest))





		Log.addDETAIL("Restaure la BDD TNR")

		SQL.restore(backupFilename)
		SQL.close()
		SQL.setNewInstance()


		Log.addDETAIL("Supprime le le fichier de backup")

		FileUtils.deleteFilesFromFolder(dest)


		Log.addDETAIL("Création des PREJDD")

		PREJDDFileMapper.createInDB()

		Log.addDETAIL("Sauvegarde de la BDD TNR avec les PREJDD")

		backupFilename = SQL.backup()


		Log.addDETAIL("Déplacement du fichier de backup dans le dossier du projet")

		origin = SQL.getDBBackupPath() + File.separator + backupFilename
		dest = DBBACKUP_PATH + File.separator + backupFilename
		Files.move(Paths.get(origin), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING)
		Log.addTraceEND(CLASS_FORLOG,"initTNR")

	}
}
