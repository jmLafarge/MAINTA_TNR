
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

import my.PREJDDFiles
import my.PropertiesReader
import my.SQL


SQL.setNewInstance(SQL.AllowedDBProfilNames.MASTERTNR)
String backupFilename = SQL.backup()
SQL.sqlInstance.close()

// on déplace le fichier dans le LEGACY_BACKUPPATH
String origin = PropertiesReader.getMyProperty('LEGACYTNR_BDDBACKUPPATH') + File.separator + backupFilename
String dest = PropertiesReader.getMyProperty('LEGACY_BACKUPPATH') + File.separator + backupFilename
Files.move(Paths.get(origin), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING)

// on copie le fichier en local
/*
origin = dest
dest = PropertiesReader.getMyProperty('LOCALTNR_BDDBACKUPPATH') + File.separator + backupFilename
Files.copy(Paths.get(origin), Paths.get(dest))
*/

SQL.setNewInstance()
SQL.restore(backupFilename)

PREJDDFiles.createInDB()

backupFilename = SQL.backup('WithPREJDD')
SQL.sqlInstance.close()
