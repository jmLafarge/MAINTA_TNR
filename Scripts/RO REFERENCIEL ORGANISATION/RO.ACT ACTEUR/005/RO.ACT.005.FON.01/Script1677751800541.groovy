import internal.GlobalVariable
import my.KW
import my.NAV



'Lecture du JDD'
def myJDD = new my.JDD()
		
		
'Si il y a un test case'
if (myJDD.getNbrLigneCasDeTest() > 0) {
	
    'Naviguer vers la bonne url et controle des infos du cartouche'
    NAV.goToURL_RUD_and_checkCartridge(myJDD.getData('SOURCE'))

	'Naviguer vers l url Copy'
	String url = GlobalVariable.BASE_URL + "CopyIdent?IDSOURCE=" + myJDD.getData('SOURCE') + "&TABLE=" + myJDD.getDBTableName()
	KW.navigateToUrl(url,'Copie')
	
	"Vérifier l'écran"
	NAV.verifierCartridge('Copy')
	
		
    KW.scrollAndSetText(myJDD.makeTO('input_ID_COD'), myJDD.getStrData('ID_CODINT'))

    KW.scrollAndSetText(myJDD.makeTO('input_ST_NOMNEW'), myJDD.getStrData('ST_NOM'))

    KW.scrollAndSetText(myJDD.makeTO('input_ST_PRENEW'), myJDD.getStrData('ST_PRE'))
			
	
	my.Log.addSTEPGRP('VALIDATION')
		
    'Validation de la saisie'
    KW.scrollAndClick(myJDD.makeTO('button_ValiderCopie'))

    'Vérification du test case - écran résulat'
    NAV.verifierEcranRUD(myJDD.getData('ID_CODINT'))
	
	Map specificValueMap = [:]
	if (myJDD.getData('ID_NUMZON')==0) {
		specificValueMap.put('ID_NUMZON', 0)
	}
	
	'Vérification des valeurs en BD'
	my.SQL.checkJDDWithBD(myJDD,specificValueMap)

} // fin du if



