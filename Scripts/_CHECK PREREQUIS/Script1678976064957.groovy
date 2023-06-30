
import checkprerequis.* 
import internal.GlobalVariable
import my.InfoBDD
import my.JDDFiles
import my.PREJDDFiles
import my.TCFiles
import my.Log
import my.NAV

Log.addTITLE("Vérification des PRE REQUIS")

if (InfoBDD.map.isEmpty()) { InfoBDD.load() }
if (TCFiles.TCfileMap.isEmpty()) { TCFiles.load() }
if (JDDFiles.JDDfilemap.isEmpty()) { JDDFiles.load() }
if (PREJDDFiles.PREJDDfilemap.isEmpty()) { PREJDDFiles.load() }

NAV.loadJDDGLOBAL()

CheckJDD.run()

CheckPREJDD.run()

CheckPrerequis.run()

Check_CAL.run()

Log.writeListTBD()

Log.addTITLE("Fin des vérification des PRE REQUIS")



