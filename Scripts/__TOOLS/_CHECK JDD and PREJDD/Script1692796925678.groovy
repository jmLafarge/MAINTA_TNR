
import my.Log
import myCheck.CheckJDD
import myCheck.CheckPREJDD
import myCheck.CheckPrerequis
import myCheck.Check_CAL


Log.setDebugLevel(4)


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



