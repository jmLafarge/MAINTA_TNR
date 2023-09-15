import tnrLog.Log
import tnrSqlManager.RestoreDB


Log.setTraceLevel(0)


//run(boolean forceFull, boolean withPREJDD)

RestoreDB.run(false,true)
