
import my.Log
import myCheck.CheckJDD
import myCheck.CheckPREJDD
import myCheck.CheckPrerequis
import myCheck.Check_CAL


Log.setDebugLevel(1)


Log.addTITLE("Vérification des PRE REQUIS")


CheckJDD.run(false)// true pour plus de détails

CheckPREJDD.run(false) // true pour plus de détails

CheckPrerequis.run()

Check_CAL.run()

Log.addSubTITLE('Liste des $TBD avec valeur de test ')
Log.writeList('TBDOK','WARNING')

Log.addSubTITLE('Liste des $TBD sans valeur de test')
Log.writeList('TBDKO','FAIL')


Log.addTITLE("Fin des vérification des PRE REQUIS")



