
import tnrCheck.CheckJDD
import tnrCheck.CheckPREJDD
import tnrCheck.CheckPrerequis
import tnrCheck.specific.Check_CAL
import tnrLog.Log


Log.setTraceLevel(0)


Log.addTITLE("Vérification des JDD et des PREJDD")


CheckJDD.run()

CheckPREJDD.run()

CheckPrerequis.run()

Check_CAL.run()

Log.addSubTITLE('Liste des $TBD avec valeur de test ')
Log.writeList('TBDOK','WARNING')

Log.addSubTITLE('Liste des $TBD sans valeur de test')
Log.writeList('TBDKO','FAIL')


Log.addTITLE("Fin des vérification des JDD et des PREJDD")



