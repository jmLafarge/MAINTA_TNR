
import tnrSqlManager.SQL

def backupFilePath = "20230719-MAINTA_TNR_MASTER_V3.0.2_INIT.bak"


SQL.restore(backupFilePath)


SQL.sqlInstance.close()

