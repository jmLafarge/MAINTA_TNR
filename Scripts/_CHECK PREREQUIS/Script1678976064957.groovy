
import checkprerequis.* 

import my.InfoBDD
import my.TCFiles
import my.JDDFiles
import my.PREJDDFiles

if (InfoBDD.map.isEmpty()) { InfoBDD.load() }
if (TCFiles.TCfileMap.isEmpty()) { TCFiles.load() }
if (JDDFiles.JDDfilemap.isEmpty()) { JDDFiles.load() }
if (PREJDDFiles.PREJDDfilemap.isEmpty()) { PREJDDFiles.load() }



//CheckJDD.run()


//CheckPREJDD.run()

CheckPrerequis.run()

//Check_CAL.run()



