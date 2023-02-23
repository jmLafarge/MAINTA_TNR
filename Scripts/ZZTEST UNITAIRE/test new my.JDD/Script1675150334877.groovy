

def filename = 'TNR_JDD/RO/JDD.RO.ACT.xlsx'

my.JDD myJDD = new my.JDD(filename,'001')

//my.InfoBDD.load()

println myJDD.getParamForThisName('FOREIGNKE', 'TOTO')
println myJDD.getParamForThisName('FOREIGNKEY', 'TOTO')
println myJDD.getParamForThisName('FOREIGNKEY', 'ID_CODSER')

/*
//println '********** headers'
myJDD.headers.eachWithIndex { v,i -> println i+':'+v }


//println '********** params'
//myJDD.params.eachWithIndex { v,i -> println i+':'+v }

println '********** data'
myJDD.datas.eachWithIndex { li,i -> println i+':'+li }


myJDD.datas.eachWithIndex { li,numli ->
	li.eachWithIndex { val,i ->
		if (myJDD.headers.get(i) in my.InfoBDD.getPK('INTER') ) {
			println li[0] + '   ' + val
		}	
	}
}
*/



/*
myJDD.datas.eachWithIndex { li,numli -> 
	li.eachWithIndex { val,i -> 
		if ((val instanceof String) && val.startsWith('$') && !!my.JDDKW.isAllowedKeyword(val)) {
			println "Le mot clé '$val' n'est pas autorisé. Trouvé en ligne DATA ${numli+1} colonne ${i+1}"
			
		}
	}
}

*/

//println '********** xpath'
//myJDD.xpathTO.eachWithIndex { v,i -> println i+':'+v }


/*
myJDD.makeTO('ID_CODINT')
myJDD.getData('ID_CODINT')

println myJDD.getParam('PREREQUIS').getClass()
println myJDD.getParam('PREREQUIS')

myJDD.getParam('PREREQUIS').eachWithIndex { v,i -> 
	if (v!='') println i+':'+v 
}

println myJDD.getParam('PREREQUssIS').getClass()
println myJDD.getParam('PREREssQUIS')
*/