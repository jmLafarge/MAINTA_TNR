import my.InfoBDD

//JML InfoBDD.load()

/*
println InfoBDD.map['INTER']['ID_CODINT'][0]
println InfoBDD.map['INTER']['ID_CODINT'][1]
println InfoBDD.map['INTER']['ID_CODINT'][2]
println InfoBDD.map['INTER']['ID_CODINT'][3]
println InfoBDD.map['INTER']['ID_CODINT'][4]
println InfoBDD.map['INTER']['ID_CODINT'][5]

println InfoBDD.inTable('INTER', 'ID_CODINT')
*/

List <String> PKList = InfoBDD.getPK('ART_EQU')

println PKList

println(InfoBDD.isPK('ART_EQU', 'ID_NUMEQU'))
println(InfoBDD.isPK('ART_EQsU', 'ID_NUMEQU'))
println(InfoBDD.isPK('ART_EQU', 'ID_NUMEsQU'))

/*
InfoBDD.map['INTER'].each {k,v  ->
		println k.getClass()
		println k
		println v.getClass()
		println v.size()
		println v[0]
}

*/
//my.Tools.parseMap(InfoBDD.imap)

//println InfoBDD.imap['INTER-ID_CODINT']

//my.Tools.parseMap(InfoBDD.map)

//println InfoBDD.map['INTER']['ID_CODINT']

/*
println InfoBDD.paraMap.containsKey('ID_CODSER')

InfoBDD.paraMap.each{k,v ->
	
	if (v[2]!=null) println v[2]
	
}
*/
/*
InfoBDD.colnameMap['INTER'].eachWithIndex{v,i ->
	
	println "$i    $v"
	
}

println InfoBDD.getPK('INTER')


InfoBDD.line.eachWithIndex{v,i ->
	
	println "$i    $v"
	
}
*/

//println InfoBDD.line[0]

//Log.addINFO('deb')
//InfoBDD.line.eachWithIndex { v,i -> if (v[0]=='INTER' && v[1]==['ST_ETA']) Log.addINFO( i+':'+v) }
//InfoBDD.line.eachWithIndex { v,i -> if (v[0]=='INTER' && v[1]=='ST_ETA') println i+':'+v[1] }
//println InfoBDD.getDATA_TYPE('INTER', 'ST_ETA')

//if (ret !=null) Log.addINFO(ret)
//Log.addINFO('fin')
//my.Tools.parseMap(InfoBDD.colnameMap)




//println InfoBDD.colnameMap.containsKey('EMP')

//InfoBDD.line.eachWithIndex { v,i -> println i+':'+v }

//InfoBDD.line.sort { a,b -> b[0] <=> a[0] }.eachWithIndex { v,i -> println i+':'+v }

//Map map = InfoBDD.line.groupBy { it[ 0 ] } .collectEntries { key, value -> [key, value*.getAt( 1 )]}

//map.eachWithIndex { v,i -> println i+':'+v }






//my.Tools.parseMap(map)
/*
println map.getAt('INTER_HAB')

println InfoBDD.colnameMap.getAt('INTER_HAB')

map.getAt('INTER_HAB').eachWithIndex { v,i ->
	
	if (v!='NULL') {
		println InfoBDD.colnameMap.getAt('INTER_HAB')[i]
	}
	
	//println "$i : $v"
}

*/

//println InfoBDD.getPK('INTER')

