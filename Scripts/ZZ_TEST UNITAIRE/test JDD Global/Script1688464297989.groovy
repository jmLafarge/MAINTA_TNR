
import internal.GlobalVariable
import my.InfoBDD
import my.JDDFiles
import my.NAV
import my.TCFiles

if (InfoBDD.map.isEmpty()) { InfoBDD.load() }
if (TCFiles.TCfileMap.isEmpty()) { TCFiles.load() }
if (JDDFiles.JDDfilemap.isEmpty()) { JDDFiles.load() }

NAV.loadJDDGLOBAL()

//println NAV.myGlobalJDD.internalValues.join('\t')

println NAV.myGlobalJDD.getInternalValueOf('GROUPE', 'GROUPE01')

println NAV.myGlobalJDD.getInternalValueOf('CRITICITE', 'CRITICITE02')

println NAV.myGlobalJDD.getInternalValueOf('TYPEMAT', 'Suivi en Stock')


GlobalVariable.CASDETESTPATTERN = 'RT.EQU.001'

