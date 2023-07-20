
import groovy.sql.Sql
import internal.GlobalVariable
import my.SQL
import my.PREJDDFiles

String table = 'CAT'
String PKwhere = "ID_CODCAT like 'CAT.RO.ACT.001%'"

String req = "SELECT st_nom as nbr FROM inter where id_codint='TNR'"

def res = SQL.sqlInstance.firstRow(req)

println ' firstRow res.getAt(0) = ' + res.getAt(0)
println " firstRow res.getAt('nbr') = " + res.getAt('nbr')




SQL.sqlInstance.close()

