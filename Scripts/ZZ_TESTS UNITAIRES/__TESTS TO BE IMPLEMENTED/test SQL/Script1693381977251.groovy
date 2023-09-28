import tnr.PropertiesReader
import tnrSqlManager.SQL
import tnrSqlManager.SQL.AllowedDBProfilNames


//def filename = 'TNR_JDD/RO/JDD.RO.ACT.xlsx'

//my.JDD myJDD = new my.JDD(filename,'003MET','RO.ACT.003MET.SRM.01')
//my.JDD myJDD = new my.JDD(filename,'001','RO.ACT.001.MAJ.01')
//STEP.checkJDDWithBD(myJDD)
println SQL.getPathDB()

SQL.setNewInstance(AllowedDBProfilNames.MASTERTNR)

println SQL.getPathDB()

println SQL.getMaintaVersion()
