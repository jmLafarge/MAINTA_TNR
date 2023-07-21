
RunConfiguration.setExecutionProfile('PCJML')

import com.kms.katalon.core.configuration.RunConfiguration

import my.SQL


String req = "SELECT st_nom as nbr FROM inter where id_codint='TNR'"

def res = SQL.sqlInstance.firstRow(req)

println ' firstRow res.getAt(0) = ' + res.getAt(0)
println " firstRow res.getAt('nbr') = " + res.getAt('nbr')




SQL.sqlInstance.close()

