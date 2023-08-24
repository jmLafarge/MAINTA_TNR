import my.InfoDB

println my.InfoDB.isTableExist('INTER')

println my.InfoDB.datas['INTER'].each { col,details ->
	println col
}