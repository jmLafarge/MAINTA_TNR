
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD


// Lecture du JDD
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
	
	TNRResult.addStartTestCase(cdt)
	
	for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		
		myJDD.setCasDeTestNum(i)

		TNRResult.addSTEP(myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))
	}
	
	TNRResult.addEndTestCase()
}
