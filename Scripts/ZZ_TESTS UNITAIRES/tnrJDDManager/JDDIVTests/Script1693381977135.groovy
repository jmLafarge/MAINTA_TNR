
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import tnrCommon.ExcelUtils
import tnrCommon.TNRPropertiesReader
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDIV
import tnrLog.Log


/**
 * TESTS UNITAIRES
 * 
 * public static String getInternalValueOf(String para, String val)
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_FOR_LOG = 'tnrJDDManager.JDDIV'

Workbook  book = ExcelUtils.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')
Sheet IVSheet = book.getSheet(TNRPropertiesReader.getMyProperty('INTERNALVALUE_SHEET_NAME'))
JDDIV myJDDIV = new JDDIV(IVSheet)

List<Map<String, Object>> listTest = [
	['param':'COM.NU_MET', 'value':'Non utilisé', 'internalValue':'$NULL'], 
	['param':'COM.NU_MET', 'value':'Linéaire', 'internalValue':'0'], 
	['param':'COM.NU_MET', 'value':'Exponentielle', 'internalValue':'1'], 
	['param':'COM.NU_MET', 'value':'Puissance', 'internalValue':'2'], 
	['param':'COM.NU_MET', 'value':'Polynomiale', 'internalValue':'3'], 
	['param':'COM.NU_MET', 'value':'Linéaire avec recalage', 'internalValue':'4'], 
	['param':'ART_FOU.ST_QTETYP', 'value':'Documentaire', 'internalValue':'0'], 
	['param':'ART_FOU.ST_QTETYP', 'value':'Au moins', 'internalValue':'1'], 
	['param':'ART_FOU.ST_QTETYP', 'value':'Multiple de', 'internalValue':'2'], 
	['param':'MAT.NU_TYP', 'value':'Non suivi en Stock', 'internalValue':'0'], 
	['param':'MAT.NU_TYP', 'value':'Suivi en Stock', 'internalValue':'1'], 
	['param':'MAT.NU_TYP', 'value':'Moyen', 'internalValue':'2']
]


Log.addAssert(CLASS_FOR_LOG,"myJDDIV.list",listTest,myJDDIV.list)

Log.addAssert(CLASS_FOR_LOG,"myJDDIV.getValueOf('COM.NU_MET','\$NULL')",'Non utilisé',myJDDIV.getValueOf('COM.NU_MET','$NULL'))
Log.addAssert(CLASS_FOR_LOG,"myJDDIV.getValueOf('MAT.NU_TYP','1')",'Suivi en Stock',myJDDIV.getValueOf('MAT.NU_TYP','1'))

Log.addAssert(CLASS_FOR_LOG,"myJDDIV.getValueOf('UNKNOWN','1')",null,myJDDIV.getValueOf('UNKNOWN','1'))
Log.addAssert(CLASS_FOR_LOG,"myJDDIV.getValueOf('MAT.NU_TYP','UNKNOWN')",null,myJDDIV.getValueOf('MAT.NU_TYP','UNKNOWN'))
Log.addAssert(CLASS_FOR_LOG,"myJDDIV.getValueOf('UNKNOWN','UNKNOWN')",null,myJDDIV.getValueOf('UNKNOWN','UNKNOWN'))





