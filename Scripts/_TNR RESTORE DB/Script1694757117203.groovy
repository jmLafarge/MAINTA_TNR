import tnrLog.Log
import tnrSqlManager.RestoreDB


Log.setTraceLevel(0)

boolean forceFull 	= false
boolean withPREJDD 	= false

RestoreDB.run(forceFull,withPREJDD)
