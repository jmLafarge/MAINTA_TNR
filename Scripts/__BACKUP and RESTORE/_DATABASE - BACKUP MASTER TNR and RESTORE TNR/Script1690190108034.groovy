

import internal.GlobalVariable
import my.Tools
import my.SQL
import my.PropertiesReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


SQL.setNewInstance(SQL.AllowedDBProfilNames.MASTERTNR)

String backupFilePath = SQL.backup()

SQL.sqlInstance.close()

SQL.setNewInstance(SQL.AllowedDBProfilNames.LEGACYTNR)

SQL.restore(backupFilePath)

Tools.deleteFile(backupFilePath)

