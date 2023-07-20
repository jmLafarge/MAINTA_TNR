
import my.SQL

def serverName = "NomDuServeur"
def databaseName = "MAINTA_TEST"
def backupFilePath = "20230719-MAINTA_TNR_MASTER_V3.0.2_INIT.bak"


    SQL.executeSQL("USE master")
    SQL.executeSQL("RESTORE DATABASE ${databaseName} FROM DISK = '${backupFilePath}' WITH REPLACE")


SQL.sqlInstance.close()

