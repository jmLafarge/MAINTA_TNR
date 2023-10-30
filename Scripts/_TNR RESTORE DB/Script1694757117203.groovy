import tnrLog.Log
import tnrSqlManager.RestoreDB


Log.setTraceLevel(5)

boolean forceFull 	= false
boolean withPREJDD 	= true

RestoreDB.run(forceFull,withPREJDD)
