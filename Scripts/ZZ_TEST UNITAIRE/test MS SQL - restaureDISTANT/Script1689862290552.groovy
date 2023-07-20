
import my.SQL

def serverName = "NomDuServeur"
def databaseName = "MAINTA_TNR"
def backupFilePath = "F:/BACKUP/MAINTA_TNR/20230719-MAINTA_TNR_MASTER_V3.0.2_INIT.bak"


    SQL.executeSQL("USE master")
	//SQL.executeSQL("ALTER DATABASE ${databaseName} SET SINGLE_USER WITH ROLLBACK IMMEDIATE")
	SQL.executeSQL("Alter database ${databaseName} set Offline with rollback immediate")
    SQL.executeSQL("RESTORE DATABASE ${databaseName} FROM DISK = '${backupFilePath}' WITH REPLACE")
	SQL.executeSQL("Alter database ${databaseName} set online")


SQL.sqlInstance.close()

