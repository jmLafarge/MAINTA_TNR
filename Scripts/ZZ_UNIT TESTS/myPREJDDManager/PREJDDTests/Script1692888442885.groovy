import java.lang.reflect.Method

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import my.Log
import my.Tools
import myJDDManager.JDDData
import myJDDManager.JDDHeader
import myPREJDDManager.PREJDD

/**
 * UNIT TESTS
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */

List<Map<String, Map<String, Object>>> datasTests = [
	['AA.BBB.001.CRE.01':['ID_NUMACT':'JMLCRE01', 'ID_CODNAF':1, 'ST_DES':'DES1', 'ST_TRAUTICRE':'JML', 'ST_TRAUTIUPD':'TUTU', 'DT_TRACRE':'D1', 'DT_TRAUPD':'DUPD1']], 
	['AA.BBB.001.CRE.02':['ID_NUMACT':'JMLCRE02', 'ID_CODNAF':2, 'ST_DES':'DES2', 'ST_TRAUTICRE':'TOTO', 'ST_TRAUTIUPD':'TITI', 'DT_TRACRE':'D2', 'DT_TRAUPD':'DUPD2']], 
	['AA.BBB.001.CRE.03':['ID_NUMACT':'JMLCRE03', 'ID_CODNAF':3, 'ST_DES':'DES3', 'ST_TRAUTICRE':'TITI', 'ST_TRAUTIUPD':'TOTO', 'DT_TRACRE':'D3', 'DT_TRAUPD':'DUPD3']], 
	['AA.BBB.001.LEC.01':['ID_NUMACT':'JMLLEC11', 'ID_CODNAF':4, 'ST_DES':'DES4', 'ST_TRAUTICRE':'TATA', 'ST_TRAUTIUPD':'JML', 'DT_TRACRE':'D4', 'DT_TRAUPD':'DUPD4']], 
	['AA.BBB.001.LEC.01':['ID_NUMACT':'JMLLEC12', 'ID_CODNAF':5, 'ST_DES':'DES5', 'ST_TRAUTICRE':'TUTU', 'ST_TRAUTIUPD':'TATA', 'DT_TRACRE':'D5', 'DT_TRAUPD':'DUPD5']]
	
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

JDDHeader myPREJDDheader = new JDDHeader(sheet)
JDDData myPREJDDData = new JDDData(sheet,myPREJDDheader,'')

Tools.displayWithQuotes(myPREJDDData.getList())

Log.addAssert("Lecture PREJDD myPREJDDData.getList()",datasTests,myPREJDDData.getList())

Method method = PREJDD.class.getDeclaredMethod("getListOfCasDeTestAndIDValue", Sheet.class,String.class);
method.setAccessible(true);

Log.addAssert("(private) JDDParam.getListOfCasDeTestAndIDValue('LOCATOR')",listTests,(List) method.invoke(PREJDD, sheet,'ID_NUMACT'))


