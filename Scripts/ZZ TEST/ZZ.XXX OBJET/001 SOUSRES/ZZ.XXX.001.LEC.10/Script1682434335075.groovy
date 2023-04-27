import my.JDD
import my.Log as MYLOG



'Lecture du JDD'
def myJDD = new JDD()

for (String cdt in myJDD.CDTList) {
	
	myJDD.setCasDeTest(cdt)
	
	MYLOG.addStartTestCase(cdt)

	MYLOG.addINFO(myJDD.getData('ID_XXX')+'\t'+myJDD.getData('ST_XXX'))
	
	MYLOG.addEndTestCase()
}
