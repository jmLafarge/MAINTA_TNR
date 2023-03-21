import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW

my.InfoBDD.load()

println my.InfoBDD.map['INTER']['ID_CODINT'][0]
println my.InfoBDD.map['INTER']['ID_CODINT'][1]
println my.InfoBDD.map['INTER']['ID_CODINT'][2]
println my.InfoBDD.map['INTER']['ID_CODINT'][3]
println my.InfoBDD.map['INTER']['ID_CODINT'][4]
println my.InfoBDD.map['INTER']['ID_CODINT'][5]
/*
my.InfoBDD.map['INTER'].each {k,v  ->
		println k.getClass()
		println k
		println v.getClass()
		println v.size()
		println v[0]
}

*/
//my.Tools.parseMap(my.InfoBDD.imap)

//println my.InfoBDD.imap['INTER-ID_CODINT']

//my.Tools.parseMap(my.InfoBDD.map)

//println my.InfoBDD.map['INTER']['ID_CODINT']

/*
println my.InfoBDD.paraMap.containsKey('ID_CODSER')

my.InfoBDD.paraMap.each{k,v ->
	
	if (v[2]!=null) println v[2]
	
}
*/
/*
my.InfoBDD.colnameMap['INTER'].eachWithIndex{v,i ->
	
	println "$i    $v"
	
}

println my.InfoBDD.getPK('INTER')


my.InfoBDD.line.eachWithIndex{v,i ->
	
	println "$i    $v"
	
}
*/

//println my.InfoBDD.line[0]

//my.Log.addINFO('deb')
//my.InfoBDD.line.eachWithIndex { v,i -> if (v[0]=='INTER' && v[1]==['ST_ETA']) my.Log.addINFO( i+':'+v) }
//my.InfoBDD.line.eachWithIndex { v,i -> if (v[0]=='INTER' && v[1]=='ST_ETA') println i+':'+v[1] }
//println my.InfoBDD.getDATA_TYPE('INTER', 'ST_ETA')

//if (ret !=null) my.Log.addINFO(ret)
//my.Log.addINFO('fin')
//my.Tools.parseMap(my.InfoBDD.colnameMap)




//println my.InfoBDD.colnameMap.containsKey('EMP')

//my.InfoBDD.line.eachWithIndex { v,i -> println i+':'+v }

//my.InfoBDD.line.sort { a,b -> b[0] <=> a[0] }.eachWithIndex { v,i -> println i+':'+v }

//Map map = my.InfoBDD.line.groupBy { it[ 0 ] } .collectEntries { key, value -> [key, value*.getAt( 1 )]}

//map.eachWithIndex { v,i -> println i+':'+v }






//my.Tools.parseMap(map)
/*
println map.getAt('INTER_HAB')

println my.InfoBDD.colnameMap.getAt('INTER_HAB')

map.getAt('INTER_HAB').eachWithIndex { v,i ->
	
	if (v!='NULL') {
		println my.InfoBDD.colnameMap.getAt('INTER_HAB')[i]
	}
	
	//println "$i : $v"
}

*/

//println my.InfoBDD.getPK('INTER')

