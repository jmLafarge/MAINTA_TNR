
import checkprerequis.* 
import internal.GlobalVariable
import my.InfoBDD
import my.JDDFiles
import my.PREJDDFiles
import my.TCFiles
import my.Log
import my.NAV


// GlobalVariable.CHECKALLDATAS permet de supprimer le ctrl des datas qui contiennent ATTENTE et MOE

if (GlobalVariable.CHECKALLDATAS) {
	Log.addTITLE("Vérification des PRE REQUIS")
}else {
	Log.addTITLE("Vérification des PRE REQUIS - ATTENTION CHECKALLDATAS = false")
}

if (InfoBDD.map.isEmpty()) { InfoBDD.load() }
if (TCFiles.TCfileMap.isEmpty()) { TCFiles.load() }
if (JDDFiles.JDDfilemap.isEmpty()) { JDDFiles.load() }
if (PREJDDFiles.PREJDDfilemap.isEmpty()) { PREJDDFiles.load() }
NAV.loadJDDGLOBAL()

CheckJDD.run()

CheckPREJDD.run()

CheckPrerequis.run()

Check_CAL.run()

Log.addTITLE("Fin des vérification des PRE REQUIS")

