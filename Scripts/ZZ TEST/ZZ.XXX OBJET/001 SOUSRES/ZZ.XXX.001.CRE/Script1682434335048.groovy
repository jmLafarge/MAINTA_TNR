
import internal.GlobalVariable
import my.result.TNRResult
import my.JDD
import my.Log

'Lecture du JDD'
def myJDD = new JDD()


for (String cdt in myJDD.CDTList) {
		
	TNRResult.addStartTestCase(cdt)
	
	myJDD.setCasDeTest(cdt)
	
	Log.addINFO("getNbrLigneCasDeTest : " + myJDD.getNbrLigneCasDeTest(cdt))
	
	for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		
		myJDD.setCasDeTestNum(i)
		
		TNRResult.addSTEP("cdt:$cdt ($i)-->" + myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))
		
	}
	
	TNRResult.addEndTestCase()
}

