import internal.GlobalVariable
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult
import tnrSqlManager.SQL
import tnrWebUI.*


'Lecture du JDD'
JDD myJDD = new JDD()
		
		
for (String cdt in myJDD.getCDTList()) {
	
	myJDD.setCasDeTest(cdt)
		
	TNRResult.addStartTestCase(cdt)

	'Naviguer vers l url Copy'
	String url = GlobalVariable.BASE_URL + "CopyIdent?IDSOURCE=" + myJDD.getData('ID_CODINT_SRC') + "&TABLE=" + myJDD.getDBTableName()
	STEP.navigateToUrl(url,'Copie')
	
	"Vérifier l'écran"
	STEP.checkCartridge('Copy')
	
		
    STEP.setText(myJDD,'input_ID_COD', myJDD.getStrData('ID_CODINT'))

    STEP.setText(myJDD,'input_ST_NOMNEW', myJDD.getStrData('ST_NOM'))

    STEP.setText(myJDD,'input_ST_PRENEW', myJDD.getStrData('ST_PRE'))
			
	
	TNRResult.addSTEPACTION('VALIDATION')
		
    'Validation de la saisie'
    STEP.simpleClick(myJDD,'button_ValiderCopie')

    'Vérification du test case - écran résulat'
    STEP.checkReadUpdateDeleteScreen(myJDD.getData('ID_CODINT'))
	
	Map specificValueMap = [:]
	if (myJDD.getData('ID_NUMZON')==0) {
		specificValueMap.put('ID_NUMZON', 0)
	}
	
	'Vérification des valeurs en BD'
	STEP.checkJDDWithBD(myJDD,specificValueMap)

	//TNRResult.addEndTestCase()
} // fin du if



