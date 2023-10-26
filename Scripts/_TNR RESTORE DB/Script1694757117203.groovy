import tnrLog.Log
import tnrSqlManager.RestoreDB


Log.setTraceLevel(0)

boolean forceFull 	= true
boolean withPREJDD 	= forceFull

RestoreDB.run(forceFull,withPREJDD)
