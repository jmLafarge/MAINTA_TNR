
import my.SQL


def backupFilePath = "F:/BACKUP/MAINTA_TNR/20230719-MAINTA_TNR_MASTER_V3.0.2_INIT.bak"

SQL.restore(backupFilePath)


SQL.sqlInstance.close()

