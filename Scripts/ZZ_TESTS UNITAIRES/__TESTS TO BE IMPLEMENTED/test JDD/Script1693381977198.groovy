
import internal.GlobalVariable
import tnrSqlManager.InfoDB
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDFileMapper

import tnr.TCFiles

 








GlobalVariable.CAS_DE_TEST_PATTERN = 'RT.MAT.001'

my.JDD myJDD = new my.JDD()

myJDD.addColumn('TITI')

//println myJDD.getCDTList().join('\t')

//println myJDD.addLOCATOR('ID_CODIMP', 'toto')
















/*
println myJDD.myJDDParam.isOBSOLETE('ID_CODSIT')
println myJDD.myJDDParam.isOBSOLETE('ID_CODART')
println myJDD.myJDDParam.isOBSOLETE('ID_CODGES')
println myJDD.myJDDParam.isOBSOLETE('DJKD')
*/


//checkprerequis.CheckTypeInData.run(myJDD.datas, myJDD, myJDD.getDBTableName())


/*
myJDD.datas.eachWithIndex { li,numli ->
	li.eachWithIndex { val,i ->
		
		if (val.toString().isNumber()) 	println "$val : " + val.getClass()
		
	}
}
*/


/*
TestObject tObj = myJDD.makeTO('a_AffectationSelected')

println tObj.getSelectorCollection().get(SelectorMethod.XPATH)


myJDD.setCasDeTestNum(2)

println '******************************************'



myJDD.replaceSEQUENCIDInJDD()


println '------------------------------------------------------------------------------------'

println myJDD.datas

println '------------------------------------------------------------------------------------'
*/






//println '******************************************' + fn2
//println SQL.getMaxFromTable(fn2, myJDD.getDBTableName())


//println myJDD.getDataLine('RO.ACT.004EMP.SRM.01',2)

//
/*
println myJDD.getParamForThisName('FOREIGNKE', 'TOTO')
println myJDD.getParamForThisName('FOREIGNKEY', 'TOTO')
println myJDD.getParamForThisName('FOREIGNKEY', 'ID_CODSER')
*/



//println '********** headers'
//myJDD.headers.eachWithIndex { v,i -> println i+':'+v }


//println '********** params'
//myJDD.params.eachWithIndex { v,i -> println i+':'+v }




//println '********** data'
//myJDD.datas.eachWithIndex { li,i -> println i+':'+li }


/*
myJDD.datas.eachWithIndex { li,numli -> 
	li.eachWithIndex { val,i -> 
		if ((val instanceof String) && val.startsWith('$') && !!JDDKW.isAllowedKeyword(val)) {
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