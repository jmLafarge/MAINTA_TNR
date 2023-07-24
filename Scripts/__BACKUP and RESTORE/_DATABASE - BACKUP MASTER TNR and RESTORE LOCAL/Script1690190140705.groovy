
import internal.GlobalVariable
import my.SQL
import my.PropertiesReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import my.Tools


SQL.setNewInstance(SQL.AllowedDBProfilNames.MASTERTNR)

String backupFile = SQL.backup()

SQL.sqlInstance.close()

String sourceBackupFileFullname = PropertiesReader.getMyProperty('MASTERTNR_BDDBACKUPPATH') + File.separator + backupFile
String targetBackupFileFullname = PropertiesReader.getMyProperty('LOCAL_BDDBACKUPPATH') + File.separator + backupFile


Files.copy(Paths.get(sourceBackupFileFullname), Paths.get(targetBackupFileFullname))


SQL.setNewInstance(SQL.AllowedDBProfilNames.LOCAL)

SQL.restore(targetBackupFileFullname)

Tools.deleteFile(sourceBackupFileFullname)
