import groovy.sql.Sql
import internal.GlobalVariable
import my.SQL

def filename = 'TNR_JDD/RO/JDD.RO.ACT.xlsx'

my.JDD myJDD = new my.JDD(filename,'001','RO.ACT.001.LEC.01')

my.InfoBDD.load()

my.SQL.checkJDDWithBD(myJDD)
