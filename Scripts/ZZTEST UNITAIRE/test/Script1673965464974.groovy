
my.InfoBDD.load()
int max =0
my.InfoBDD.paraMap.each { k,v ->
	
	if (v.size()>max) max = v.size()
	println "k: $k " +  v.size()
	for (int i = 0; i < v.size(); i++) {
		
		println "\t-i $i "+v[i]
	}
}

println "max $max"
