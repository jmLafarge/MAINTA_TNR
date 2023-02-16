

def myJDD = new my.JDD('TNR_JDD/RO/JDD.RT.EMP.xlsx')

myJDD.loadParam(myJDD.book.getSheet('001'),'SEQUENCE')

my.Tools.parseMap(myJDD.param)


/*
myJDD.prepareJDD('001')

my.Tools.parseMap(myJDD.param)

println myJDD.param.getAt('SEQUENCE').getClass()

if (myJDD.isParamExistForThisID('SEQUENCE')){
	
	myJDD.param.getAt('SEQUENCE').each { a,b -> println a + '   ' + b }
}
*/