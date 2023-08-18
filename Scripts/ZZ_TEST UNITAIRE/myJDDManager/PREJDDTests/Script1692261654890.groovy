import java.lang.reflect.Method

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import my.Log
import my.Tools
import myJDDManager.PREJDD

/**
 * Unit tests
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */

List datasTests = [
	['AA.BBB.001.CRE.01', 'JMLCRE01', 1, 'DES1', 'JML', 'TUTU', 'D1', 'DUPD1'], 
	['AA.BBB.001.CRE.02', 'JMLCRE02', 2, 'DES2', 'TOTO', 'TITI', 'D2', 'DUPD2'], 
	['AA.BBB.001.CRE.03', 'JMLCRE03', 3, 'DES3', 'TITI', 'TOTO', 'D3', 'DUPD3'], 
	['AA.BBB.001.LEC.01', 'JMLLEC11', 4, 'DES4', 'TATA', 'JML', 'D4', 'DUPD4'], 
	['AA.BBB.001.LEC.01', 'JMLLEC12', 5, 'DES5', 'TUTU', 'TATA', 'D5', 'DUPD5']
]


List listTests = [
	"'AA.BBB.001.CRE.01' - 'JMLCRE01'", 
	"'AA.BBB.001.CRE.02' - 'JMLCRE02'", 
	"'AA.BBB.001.CRE.03' - 'JMLCRE03'", 
	"'AA.BBB.001.LEC.01' - 'JMLLEC11'", 
	"'AA.BBB.001.LEC.01' - 'JMLLEC12'"
	]

Workbook  book = my.XLS.open('TNR_JDDTest\\PREJDD.AA.BBB.xlsx')
Sheet sheet = book.getSheet('001')	


	
Log.addAssert("PREJDD.loadDATA(sheet, 8)",datasTests,PREJDD.loadDATA(sheet, 8))

Method method = PREJDD.class.getDeclaredMethod("getListOfCasDeTestAndIDValue", Sheet.class,String.class);
method.setAccessible(true);

Log.addAssert("private JDDParam.getListOfCasDeTestAndIDValue('LOCATOR')",listTests,(List) method.invoke(PREJDD, sheet,'ID_NUMACT'))


