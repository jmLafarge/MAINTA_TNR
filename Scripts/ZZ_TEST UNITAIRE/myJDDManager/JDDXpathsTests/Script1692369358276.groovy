
import my.Log
import myJDDManager.JDDXpaths

/**
 * Unit tests
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */


Map <String,String> xpathMapTest1 = [
	'button_Valider':"//button[@id='ValidE39']",
	'button_Supprimer':"//button[@id='DeleteE39']",
	'input_Filtre_Grille':'$FILTREGRILLE$ID_CODINT',
	'td_Grille':'$TDGRILLE$4$ID_CODINT',
	'tab_Acteur':'$TAB$ID_CODINT',
	'tab_ActeurSelected':'$TABSELECTED$ID_CODINT'
	]
	

JDDXpaths JDDXpath = new JDDXpaths()

JDDXpath.addFromMap(xpathMapTest1)

Log.addAssert("JDDXpath.xpaths",xpathMapTest1,JDDXpath.xpaths)
