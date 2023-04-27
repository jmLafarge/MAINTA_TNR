
import internal.GlobalVariable
import my.Log as MYLOG
import my.JDD

'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
		
	MYLOG.addStartTestCase(cdt)
	
	myJDD.setCasDeTest(cdt)
	
	MYLOG.addINFO("getNbrLigneCasDeTest : " + myJDD.getNbrLigneCasDeTest(cdt))
	
	for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		
		myJDD.setCasDeTestNum(i)
		
		MYLOG.addSTEP("cdt:$cdt ($i)-->" + myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))
		
	}
	
	MYLOG.addEndTestCase()
}

