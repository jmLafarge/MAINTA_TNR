
import org.apache.poi.ss.usermodel.Workbook
import tnrLog.Log
import tnrJDDManager.JDDIV

/**
 * TESTS UNITAIRES
 * 
 * public static String getInternalValueOf(String para, String val)
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */


Workbook  book = tnrCommon.ExcelUtils.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')


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

JDDIV.addAll(book.getSheet('INTERNALVALUE'))

Log.addAssert("JDDIV.list",listTest,JDDIV.list)

Log.addAssert("JDDIV.getInternalValueOf('COM.NU_MET','Non utilisé')",'$NULL',JDDIV.getInternalValueOf('COM.NU_MET','Non utilisé'))
Log.addAssert("JDDIV.getInternalValueOf('MAT.NU_TYP','Suivi en Stock')",'1',JDDIV.getInternalValueOf('MAT.NU_TYP','Suivi en Stock'))

Log.addAssert("JDDIV.getInternalValueOf('UNKNOWN','Suivi en Stock')",null,JDDIV.getInternalValueOf('UNKNOWN','Suivi en Stock'))
Log.addAssert("JDDIV.getInternalValueOf('MAT.NU_TYP','UNKNOWN')",null,JDDIV.getInternalValueOf('MAT.NU_TYP','UNKNOWN'))
Log.addAssert("JDDIV.getInternalValueOf('UNKNOWN','UNKNOWN')",null,JDDIV.getInternalValueOf('UNKNOWN','UNKNOWN'))





