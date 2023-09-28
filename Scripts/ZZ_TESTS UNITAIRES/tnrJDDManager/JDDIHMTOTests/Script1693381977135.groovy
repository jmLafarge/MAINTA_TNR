
import org.apache.poi.ss.usermodel.Workbook

import tnrCommon.ExcelUtils
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDIHMTO
import tnrLog.Log

/**
 * TESTS UNITAIRES
 * 
 * public Map <String,String> getXpaths()
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */


final String CLASS_NAME = 'tnrJDDManager.JDDIHMTO'

Map <String,String> xpathMapTest1 = [
	'button_Valider':"//button[@id='ValidE39']", 
	'button_Supprimer':"//button[@id='DeleteE39']", 
	'input_Filtre_Grille':'$FILTREGRILLE$ID_CODINT', 
	'td_Grille':'$TDGRILLE$4$ID_CODINT', 
	'tab_Acteur':'$TAB$ID_CODINT', 
	'tab_ActeurSelected':'$TABSELECTED$ID_CODINT'
	]
	
Map <String,String> xpathMapTest2 = [

	'button_Valider':"//button[@id='ValidE39']",
	'button_Supprimer':"//button[@id='DeleteE39']",
	'a_AjouterHabilitation':"//div[@id='bAddGridHAB']/a[@id='add']", 
	'td_DateDebut':"//div[@id='v-dbtdhtmlxHAB']/table/tbody//td[3][text()='\${ID_CODHAB}']//following-sibling::td[3]",
	'tab_Acteur':'$TAB$ID_CODINT',
	'tab_ActeurSelected':'$TABSELECTED$ID_CODINT'
	]

Workbook  book = ExcelUtils.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')


JDDIHMTO IHMTO1 = new JDDIHMTO(book.getSheet('IHMTO'),'001')
Log.addAssert(CLASS_NAME,"IHMTO1.getXpaths()",xpathMapTest1,IHMTO1.getXpaths())

JDDIHMTO IHMTO2 = new JDDIHMTO(book.getSheet('IHMTO'),'003HAB')
Log.addAssert(CLASS_NAME,"IHMTO2.getXpaths()",xpathMapTest2,IHMTO2.getXpaths())

