
import groovy.sql.Sql
import internal.GlobalVariable
import my.SQL

String ID = 'FOUDOC_ID'
String req = "SELECT IDENT_CURRENT('$ID') as lastID"
def result = SQL.sqlInstance.rows(req)
int lastID = result[0].lastID
println "lastID: $lastID"

println "the last " + SQL.getLastSequence('ZONLIG_ID')


SQL.sqlInstance.close()

