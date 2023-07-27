import my.InfoDB



/*
println InfoDB.map['INTER']['ID_CODINT'][0]
println InfoDB.map['INTER']['ID_CODINT'][1]
println InfoDB.map['INTER']['ID_CODINT'][2]
println InfoDB.map['INTER']['ID_CODINT'][3]
println InfoDB.map['INTER']['ID_CODINT'][4]
println InfoDB.map['INTER']['ID_CODINT'][5]

println InfoDB.inTable('INTER', 'ID_CODINT')
*/

List <String> PKList = InfoDB.getPK('ART_EQU')

println PKList

println(InfoDB.isPK('ART_EQU', 'ID_NUMEQU'))
println(InfoDB.isPK('ART_EQsU', 'ID_NUMEQU'))
println(InfoDB.isPK('ART_EQU', 'ID_NUMEsQU'))

/*
InfoDB.map['INTER'].each {k,v  ->
		println k.getClass()
		println k
		println v.getClass()
		println v.size()
		println v[0]
}

*/
//my.Tools.parseMap(InfoDB.imap)

//println InfoDB.imap['INTER-ID_CODINT']

//my.Tools.parseMap(InfoDB.map)

//println InfoDB.map['INTER']['ID_CODINT']

/*
println InfoDB.paraMap.containsKey('ID_CODSER')

InfoDB.paraMap.each{k,v ->
	
	if (v[2]!=null) println v[2]
	
}
*/
/*
InfoDB.colnameMap['INTER'].eachWithIndex{v,i ->
	
	println "$i    $v"
	
}

println InfoDB.getPK('INTER')


InfoDB.line.eachWithIndex{v,i ->
	
	println "$i    $v"
	
}
*/

//println InfoDB.line[0]

//Log.addINFO('deb')
//InfoDB.line.eachWithIndex { v,i -> if (v[0]=='INTER' && v[1]==['ST_ETA']) Log.addINFO( i+':'+v) }
//InfoDB.line.eachWithIndex { v,i -> if (v[0]=='INTER' && v[1]=='ST_ETA') println i+':'+v[1] }
//println InfoDB.getDATA_TYPE('INTER', 'ST_ETA')

//if (ret !=null) Log.addINFO(ret)
//Log.addINFO('fin')
//my.Tools.parseMap(InfoDB.colnameMap)




//println InfoDB.colnameMap.containsKey('EMP')

//InfoDB.line.eachWithIndex { v,i -> println i+':'+v }

//InfoDB.line.sort { a,b -> b[0] <=> a[0] }.eachWithIndex { v,i -> println i+':'+v }

//Map map = InfoDB.line.groupBy { it[ 0 ] } .collectEntries { key, value -> [key, value*.getAt( 1 )]}

//map.eachWithIndex { v,i -> println i+':'+v }






//my.Tools.parseMap(map)
/*
println map.getAt('INTER_HAB')

println InfoDB.colnameMap.getAt('INTER_HAB')

map.getAt('INTER_HAB').eachWithIndex { v,i ->
	
	if (v!='NULL') {
		println InfoDB.colnameMap.getAt('INTER_HAB')[i]
	}
	
	//println "$i : $v"
}

*/

//println InfoDB.getPK('INTER')

