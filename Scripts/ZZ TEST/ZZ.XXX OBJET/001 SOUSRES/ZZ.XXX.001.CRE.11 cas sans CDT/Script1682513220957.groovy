
import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrJDDManager.JDD


'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
	
	TNRResult.addStartTestCase(cdt)
	
	for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		
		myJDD.setCasDeTestNum(i)
	
		TNRResult.addSTEP(myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))
	}
	
	TNRResult.addEndTestCase()
}
