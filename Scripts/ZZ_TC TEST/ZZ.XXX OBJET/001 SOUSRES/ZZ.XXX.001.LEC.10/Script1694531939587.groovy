import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrLog.Log


'Lecture du JDD'
JDD myJDD = new JDD()

for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
	
	TNRResult.addStartTestCase(cdt)

	Log.addINFO(myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))
	
	TNRResult.addEndTestCase()
}
