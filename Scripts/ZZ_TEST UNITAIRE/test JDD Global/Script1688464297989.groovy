
import internal.GlobalVariable
import my.InfoBDD
import my.JDDFiles
import my.NAV
import my.TCFiles

 



//println NAV.myGlobalJDD.internalValues.join('\t')

println NAV.myGlobalJDD.getInternalValueOf('GROUPE', 'GROUPE01')

println NAV.myGlobalJDD.getInternalValueOf('CRITICITE', 'CRITICITE02')

println NAV.myGlobalJDD.getInternalValueOf('TYPEMAT', 'Suivi en Stock')


GlobalVariable.CASDETESTPATTERN = 'RT.EQU.001'

