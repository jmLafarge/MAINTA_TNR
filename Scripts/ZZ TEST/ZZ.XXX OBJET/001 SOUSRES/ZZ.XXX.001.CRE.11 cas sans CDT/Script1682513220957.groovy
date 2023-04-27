
import my.JDD
import my.Log as MYLOG
import my.JDD


'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
	
	MYLOG.addStartTestCase(cdt)
	
	for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		
		myJDD.setCasDeTestNum(i)
	
		MYLOG.addSTEP(myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))
	}
	
	MYLOG.addEndTestCase()
}
