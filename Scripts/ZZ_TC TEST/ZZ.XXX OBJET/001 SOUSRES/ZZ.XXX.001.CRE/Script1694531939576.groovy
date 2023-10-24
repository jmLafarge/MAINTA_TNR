
import internal.GlobalVariable
import tnrResultManager.TNRResult
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrLog.Log

// Lecture du JDD
JDD myJDD = new JDD()


for (String cdt in myJDD.getCDTList()) {
		
	TNRResult.addStartTestCase(cdt)
	
	myJDD.setCasDeTest(cdt)
	
	Log.addINFO("getNbrLigneCasDeTest : " + myJDD.getNbrLigneCasDeTest(cdt))
	
	for (int i : (1..myJDD.getNbrLigneCasDeTest())) {
		
		myJDD.setCasDeTestNum(i)
		
		TNRResult.addSTEP("cdt:$cdt ($i)-->" + myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))
		
	}
	
	TNRResult.addEndTestCase()
}

