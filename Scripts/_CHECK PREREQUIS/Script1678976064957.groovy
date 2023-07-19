
import checkprerequis.* 
import internal.GlobalVariable
import my.InfoBDD
import my.JDDFiles
import my.PREJDDFiles
import my.TCFiles
import my.Log
import my.NAV

Log.setDebugLevel(0)
Log.setDebugDeph(1)


Log.addTITLE("Vérification des PRE REQUIS")

if (InfoBDD.map.isEmpty()) { InfoBDD.load() }
if (TCFiles.TCfileMap.isEmpty()) { TCFiles.load() }
if (JDDFiles.JDDfilemap.isEmpty()) { JDDFiles.load() }
if (PREJDDFiles.PREJDDfilemap.isEmpty()) { PREJDDFiles.load() }

NAV.loadJDDGLOBAL()

CheckJDD.run(false)// true pour plus de détails

CheckPREJDD.run(false) // true pour plus de détails

CheckPrerequis.run()

Check_CAL.run()

Log.addSubTITLE('Liste des $TBD avec valeur de test ')
Log.writeList('TBDOK','WARNING')

Log.addSubTITLE('Liste des $TBD sans valeur de test')
Log.writeList('TBDKO','FAIL')


Log.addTITLE("Fin des vérification des PRE REQUIS")



