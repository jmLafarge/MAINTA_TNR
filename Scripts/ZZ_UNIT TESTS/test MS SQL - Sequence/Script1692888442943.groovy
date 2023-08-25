
import groovy.sql.Sql
import internal.GlobalVariable
import tnrSqlManager.SQL
import tnr.PREJDDFiles

String ID = 'FOUDOC_ID'
String req = "SELECT IDENT_CURRENT('$ID') as lastID"
def result = SQL.sqlInstance.rows(req)
int lastID = result[0].lastID
println "lastID: $lastID"

println "the last " + SQL.getLastSequence('ZONLIG_ID')

String table = 'CAT'
String PKwhere = "ID_CODCAT = 'CAT.RO.ACT.001.CRE.01'"

req = "SELECT count(*) as nbr FROM $table WHERE $PKwhere"

def res = SQL.sqlInstance.firstRow(req)

println ' firstRow res.getAt(0)' + res.getAt(0)
println " firstRow res.getAt('nbr')" + res.getAt('nbr')


def res2 = SQL.getFirstRow(req)

println ' getFirstRow ' + res2.getAt('nbr')

PREJDDFiles.insertIfNotExist(table, PKwhere, [], [])






SQL.sqlInstance.close()

