
import my.JDD
import my.result.TNRResult
import my.JDD


'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
	
	TNRResult.addStartTestCase(cdt)
	
	for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		
		myJDD.setCasDeTestNum(i)
	
		TNRResult.addSTEP(myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))
	}
	
	TNRResult.addEndTestCase()
}
