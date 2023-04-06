import my.Log as MYLOG

MYINFOBDD.load()

println MYINFOBDD.map['INTER']['ID_CODINT'][0]
println MYINFOBDD.map['INTER']['ID_CODINT'][1]
println MYINFOBDD.map['INTER']['ID_CODINT'][2]
println MYINFOBDD.map['INTER']['ID_CODINT'][3]
println MYINFOBDD.map['INTER']['ID_CODINT'][4]
println MYINFOBDD.map['INTER']['ID_CODINT'][5]

println MYINFOBDD.inTable('INTER', 'ID_CODINT')

/*
MYINFOBDD.map['INTER'].each {k,v  ->
		println k.getClass()
		println k
		println v.getClass()
		println v.size()
		println v[0]
}

*/
//my.Tools.parseMap(MYINFOBDD.imap)

//println MYINFOBDD.imap['INTER-ID_CODINT']

//my.Tools.parseMap(MYINFOBDD.map)

//println MYINFOBDD.map['INTER']['ID_CODINT']

/*
println MYINFOBDD.paraMap.containsKey('ID_CODSER')

MYINFOBDD.paraMap.each{k,v ->
	
	if (v[2]!=null) println v[2]
	
}
*/
/*
MYINFOBDD.colnameMap['INTER'].eachWithIndex{v,i ->
	
	println "$i    $v"
	
}

println MYINFOBDD.getPK('INTER')


MYINFOBDD.line.eachWithIndex{v,i ->
	
	println "$i    $v"
	
}
*/

//println MYINFOBDD.line[0]

//MYLOG.addINFO('deb')
//MYINFOBDD.line.eachWithIndex { v,i -> if (v[0]=='INTER' && v[1]==['ST_ETA']) MYLOG.addINFO( i+':'+v) }
//MYINFOBDD.line.eachWithIndex { v,i -> if (v[0]=='INTER' && v[1]=='ST_ETA') println i+':'+v[1] }
//println MYINFOBDD.getDATA_TYPE('INTER', 'ST_ETA')

//if (ret !=null) MYLOG.addINFO(ret)
//MYLOG.addINFO('fin')
//my.Tools.parseMap(MYINFOBDD.colnameMap)




//println MYINFOBDD.colnameMap.containsKey('EMP')

//MYINFOBDD.line.eachWithIndex { v,i -> println i+':'+v }

//MYINFOBDD.line.sort { a,b -> b[0] <=> a[0] }.eachWithIndex { v,i -> println i+':'+v }

//Map map = MYINFOBDD.line.groupBy { it[ 0 ] } .collectEntries { key, value -> [key, value*.getAt( 1 )]}

//map.eachWithIndex { v,i -> println i+':'+v }






//my.Tools.parseMap(map)
/*
println map.getAt('INTER_HAB')

println MYINFOBDD.colnameMap.getAt('INTER_HAB')

map.getAt('INTER_HAB').eachWithIndex { v,i ->
	
	if (v!='NULL') {
		println MYINFOBDD.colnameMap.getAt('INTER_HAB')[i]
	}
	
	//println "$i : $v"
}

*/

//println MYINFOBDD.getPK('INTER')

