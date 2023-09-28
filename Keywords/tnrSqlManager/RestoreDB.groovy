package tnrSqlManager

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

import groovy.transform.CompileStatic
import tnrCommon.FileUtils
import tnrCommon.TNRPropertiesReader
import tnrLog.Log
import tnrPREJDDManager.PREJDDFileMapper

@CompileStatic
public class RestoreDB {

	private static final String CLASS_NAME = 'RestoreDB'

	private static final String DBBACKUP_PATH 			= TNRPropertiesReader.getMyProperty('DB_BACKUP_PATH')
	private static final String MASTERTNR_DBBACKUPPATH 	= TNRPropertiesReader.getMyProperty('MASTERTNR_DBBACKUPPATH')



	static run(boolean forceFull, boolean withPREJDD) {
		Log.addTraceBEGIN(CLASS_NAME,"run",[forceFull:forceFull , withPREJDD:withPREJDD])

		Log.addTITLE("Restauration de la base de données")

		List <String > backupFileList = FileUtils.getFilesFromFolder('^\\d{8}_\\d{6}-' + SQL.getProfileName() +'_' + SQL.getDatabaseName() + '.*\\.bak$',DBBACKUP_PATH)
		if (backupFileList.size() == 1 && !forceFull) {
			Log.addSubTITLE("Restauration de la BDD de test")
			String TNRBackupFilename = backupFileList[0]
			String destFullname = restoreTNRDB(TNRBackupFilename)
			deleteFileFromTNRFolder(destFullname)
		}else {
			if (forceFull) {
				Log.addDETAIL(' /!\\ Forcage restauration complete')
			}else {
				Log.addDETAIL("Il n'existe pas de suavegarde de la base TNR")
			}
			Log.addSubTITLE("Initialisation complète de la BDD de test")

			deleteFilesFromBackupFolder()
			String masterBackupFilename = saveMasterDB()
			moveMasterBackupFileToBackupFolder(masterBackupFilename)
			String destFullname = restoreTNRDB(masterBackupFilename)
			if (withPREJDD) {
				createPREJDD()
			}else {
				Log.addDETAIL(' /!\\ Pas de création de PREJDD')
			}
			String TNRBackupFilename = saveTNRDB()
			moveTNRBackupFileToBackupFolder(TNRBackupFilename)
			deleteFileFromTNRFolder(destFullname)
		}

		// Ajouter le recyclage

		Log.addTraceEND(CLASS_NAME,"run")
	}




	private static void deleteFilesFromBackupFolder() {
		Log.addTraceBEGIN(CLASS_NAME,"deleteFilesFromBackupFolder",[:])
		Log.addDETAIL("Supprimer les anciens fichiers de backup du dossier projet")
		FileUtils.deleteFilesFromFolder('^\\d{8}_\\d{6}-.*\\.bak$',DBBACKUP_PATH)
		Log.addTraceEND(CLASS_NAME,"deleteFilesFromBackupFolder")
	}


	private static String saveMasterDB() {
		Log.addTraceBEGIN(CLASS_NAME,"saveMasterDB",[:])
		Log.addDETAIL("Sauvegarde de la BDD MASTER_TNR")
		SQL.setNewInstance(SQL.AllowedDBProfilNames.MASTERTNR)
		String backupFilename =SQL.backup()
		SQL.close()
		Log.addTraceEND(CLASS_NAME,"saveMasterDB", backupFilename)
		return backupFilename
	}

	private static void moveMasterBackupFileToBackupFolder(String masterBackupFilename) {
		Log.addTraceBEGIN(CLASS_NAME,"moveMasterBackupFileToBackupFolder",[masterBackupFilename:masterBackupFilename])
		Log.addDETAIL("Déplace le fichier de backup dans le dossier du projet")
		String originFullname = MASTERTNR_DBBACKUPPATH + File.separator + masterBackupFilename
		Log.addTrace("originFullname:$originFullname")
		String destFullname = DBBACKUP_PATH + File.separator + masterBackupFilename
		Log.addTrace("destFullname:$destFullname")
		Files.move(Paths.get(originFullname), Paths.get(destFullname), StandardCopyOption.REPLACE_EXISTING)
		Log.addTraceEND(CLASS_NAME,"moveMasterBackupFileToBackupFolder")
	}



	private static String copyBackupFileToTNRFolder(String backupFilename) {
		Log.addTraceBEGIN(CLASS_NAME,"copyBackupFileToTNRFolder",[backupFilename:backupFilename])
		Log.addDETAIL("Copie du fichier de backup du projet sur le server de TNR")
		String originFullname = DBBACKUP_PATH + File.separator + backupFilename
		Log.addTrace("originFullname:$originFullname")
		String destFullname = SQL.getDBBackupPath() + File.separator + backupFilename
		Log.addTrace("destFullname:$destFullname")
		Files.copy(Paths.get(originFullname), Paths.get(destFullname))
		Log.addTraceEND(CLASS_NAME,"copyBackupFileToTNRFolder",destFullname)
		return destFullname
	}


	private static String restoreTNRDB(String backupFilename) {
		Log.addTraceBEGIN(CLASS_NAME,"restoreTNRDB",[backupFilename:backupFilename])
		Log.addDETAIL("Restaure la BDD TNR")
		SQL.setNewInstance()
		String destFullname = copyBackupFileToTNRFolder(backupFilename)
		SQL.restore(backupFilename)
		SQL.close()
		Log.addTraceEND(CLASS_NAME,"restoreTNRDB",destFullname)
		return destFullname
	}

	private static void deleteFileFromTNRFolder(String destFullname) {
		Log.addTraceBEGIN(CLASS_NAME,"deleteFileFromTNRFolder",[destFullname:destFullname])
		Log.addDETAIL("Supprime le fichier de backup")
		FileUtils.deleteFilesFromFolder(destFullname)
		Log.addTraceEND(CLASS_NAME,"deleteFileFromTNRFolder")
	}


	private static void createPREJDD() {
		Log.addTraceBEGIN(CLASS_NAME,"createPREJDD",[:])
		Log.addDETAIL("Création des PREJDD")
		SQL.setNewInstance()
		PREJDDFileMapper.createInDB()
		SQL.close()
		Log.addTraceEND(CLASS_NAME,"createPREJDD")
	}

	private static String saveTNRDB() {
		Log.addTraceBEGIN(CLASS_NAME,"saveTNRDB",[:])
		Log.addDETAIL("Sauvegarde de la BDD TNR avec les PREJDD")
		SQL.setNewInstance()
		String backupFilename =SQL.backup()
		SQL.close()
		Log.addTraceEND(CLASS_NAME,"saveTNRDB",backupFilename)
		return backupFilename
	}

	private static void moveTNRBackupFileToBackupFolder(String backupFilename) {
		Log.addTraceBEGIN(CLASS_NAME,"moveTNRBackupFileToBackupFolder",[backupFilename:backupFilename])
		Log.addDETAIL("Déplace le fichier de backup dans le dossier du projet")
		String originFullname = SQL.getDBBackupPath() + File.separator + backupFilename
		Log.addTrace("originFullname:$originFullname")
		String destFullname = DBBACKUP_PATH + File.separator + backupFilename
		Log.addTrace("destFullname:$destFullname")
		Files.move(Paths.get(originFullname), Paths.get(destFullname), StandardCopyOption.REPLACE_EXISTING)
		Log.addTraceEND(CLASS_NAME,"moveTNRBackupFileToBackupFolder")
	}
}
