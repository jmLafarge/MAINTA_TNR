import groovy.sql.Sql
import internal.GlobalVariable
import my.SQL

def filename = 'TNR_JDD/RO/JDD.RO.ACT.xlsx'

my.JDD myJDD = new my.JDD(filename,'003MET','RO.ACT.003MET.SRM.01')
//my.JDD myJDD = new my.JDD(filename,'001','RO.ACT.001.MAJ.01')




SQL.checkJDDWithBD(myJDD)
