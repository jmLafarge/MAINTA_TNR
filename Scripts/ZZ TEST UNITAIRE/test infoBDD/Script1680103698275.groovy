import my.Log as MYLOG

INFOBDD.load()

println INFOBDD.map['INTER']['ID_CODINT'][0]
println INFOBDD.map['INTER']['ID_CODINT'][1]
println INFOBDD.map['INTER']['ID_CODINT'][2]
println INFOBDD.map['INTER']['ID_CODINT'][3]
println INFOBDD.map['INTER']['ID_CODINT'][4]
println INFOBDD.map['INTER']['ID_CODINT'][5]

println INFOBDD.inTable('INTER', 'ID_CODINT')

/*
INFOBDD.map['INTER'].each {k,v  ->
		println k.getClass()
		println k
		println v.getClass()
		println v.size()
		println v[0]
}

*/
//my.Tools.parseMap(INFOBDD.imap)

//println INFOBDD.imap['INTER-ID_CODINT']

//my.Tools.parseMap(INFOBDD.map)

//println INFOBDD.map['INTER']['ID_CODINT']

/*
println INFOBDD.paraMap.containsKey('ID_CODSER')

INFOBDD.paraMap.each{k,v ->
	
	if (v[2]!=null) println v[2]
	
}
*/
/*
INFOBDD.colnameMap['INTER'].eachWithIndex{v,i ->
	
	println "$i    $v"
	
}

println INFOBDD.getPK('INTER')


INFOBDD.line.eachWithIndex{v,i ->
	
	println "$i    $v"
	
}
*/

//println INFOBDD.line[0]

//MYLOG.addINFO('deb')
//INFOBDD.line.eachWithIndex { v,i -> if (v[0]=='INTER' && v[1]==['ST_ETA']) MYLOG.addINFO( i+':'+v) }
//INFOBDD.line.eachWithIndex { v,i -> if (v[0]=='INTER' && v[1]=='ST_ETA') println i+':'+v[1] }
//println INFOBDD.getDATA_TYPE('INTER', 'ST_ETA')

//if (ret !=null) MYLOG.addINFO(ret)
//MYLOG.addINFO('fin')
//my.Tools.parseMap(INFOBDD.colnameMap)




//println INFOBDD.colnameMap.containsKey('EMP')

//INFOBDD.line.eachWithIndex { v,i -> println i+':'+v }

//INFOBDD.line.sort { a,b -> b[0] <=> a[0] }.eachWithIndex { v,i -> println i+':'+v }

//Map map = INFOBDD.line.groupBy { it[ 0 ] } .collectEntries { key, value -> [key, value*.getAt( 1 )]}

//map.eachWithIndex { v,i -> println i+':'+v }






//my.Tools.parseMap(map)
/*
println map.getAt('INTER_HAB')

println INFOBDD.colnameMap.getAt('INTER_HAB')

map.getAt('INTER_HAB').eachWithIndex { v,i ->
	
	if (v!='NULL') {
		println INFOBDD.colnameMap.getAt('INTER_HAB')[i]
	}
	
	//println "$i : $v"
}

*/

//println INFOBDD.getPK('INTER')

