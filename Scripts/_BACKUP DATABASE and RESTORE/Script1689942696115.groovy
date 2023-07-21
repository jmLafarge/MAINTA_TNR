
import internal.GlobalVariable
import my.SQL
import java.nio.file.Files

import java.nio.file.Path
import java.nio.file.Paths

/*
 * RAPPEL 
 * 
 * 
 * C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\Backup
 * 
 * 
 * 
*/

SQL.sqlInstance.close()

SQL.setNewInstance('','', 'MAINTA_TNR_MASTER', '', '')

String backupFilePath = SQL.backup()

SQL.sqlInstance.close()


String backupPATH = my.PropertiesReader.getMyProperty('BACKUP_PATH')


Path source = Paths.get(GlobalVariable.BDD_BACKUPPATH.toString() + File.separator + backupFilePath)
Path target = Paths.get(backupPATH + File.separator + backupFilePath)
Files.copy(source, target)

SQL.setNewInstance('','', 'MAINTA_TNR', '', '')

SQL.restore(backupFilePath)


