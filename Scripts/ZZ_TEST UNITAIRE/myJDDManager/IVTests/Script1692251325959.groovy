
import org.apache.poi.ss.usermodel.Workbook
import my.Log
import myJDDManager.IV

/**
 * Unit tests
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */


Workbook  book = my.XLS.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')


List<Map<String, String>> listTest = [
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

IV.addAll(book.getSheet('INTERNALVALUE'))

Log.addAssert("IV.list",listTest,IV.list)

Log.addAssert("IV.getInternalValueOf('COM.NU_MET','Non utilisé')",'$NULL',IV.getInternalValueOf('COM.NU_MET','Non utilisé'))
Log.addAssert("IV.getInternalValueOf('MAT.NU_TYP','Suivi en Stock')",'1',IV.getInternalValueOf('MAT.NU_TYP','Suivi en Stock'))

Log.addAssert("IV.getInternalValueOf('UNKNOWN','Suivi en Stock')",null,IV.getInternalValueOf('UNKNOWN','Suivi en Stock'))
Log.addAssert("IV.getInternalValueOf('MAT.NU_TYP','UNKNOWN')",null,IV.getInternalValueOf('MAT.NU_TYP','UNKNOWN'))
Log.addAssert("IV.getInternalValueOf('UNKNOWN','UNKNOWN')",null,IV.getInternalValueOf('UNKNOWN','UNKNOWN'))





