import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import tnrCommon.Tools
import tnrPREJDDManager.PREJDD
import tnrJDDManager.JDDData
import tnrJDDManager.JDDHeader
import tnrLog.Log
import java.lang.reflect.Method


/**
 * TESTS UNITAIRES
 * 
 * private static List getListOfCasDeTestAndIDValue(Sheet sheet, String ID)
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */


List<Map<String, Map<String, Object>>> datasTests = [
	['AA.BBB.001.CRE.01':['ID_NUMACT':'JMLCRE01', 'ID_CODNAF':1, 'ST_DES':'DES1', 'ST_TRAUTICRE':'JML', 'ST_TRAUTIUPD':'TUTU', 'DT_TRACRE':'D1', 'DT_TRAUPD':'DUPD1']], 
	['AA.BBB.001.CRE.02':['ID_NUMACT':'JMLCRE02', 'ID_CODNAF':2, 'ST_DES':'DES2', 'ST_TRAUTICRE':'TOTO', 'ST_TRAUTIUPD':'TITI', 'DT_TRACRE':'D2', 'DT_TRAUPD':'DUPD2']], 
	['AA.BBB.001.CRE.03':['ID_NUMACT':'JMLCRE03', 'ID_CODNAF':3, 'ST_DES':'DES3', 'ST_TRAUTICRE':'TITI', 'ST_TRAUTIUPD':'TOTO', 'DT_TRACRE':'D3', 'DT_TRAUPD':'DUPD3']], 
	['AA.BBB.001.LEC.01':['ID_NUMACT':'JMLLEC11', 'ID_CODNAF':4, 'ST_DES':'DES4', 'ST_TRAUTICRE':'TATA', 'ST_TRAUTIUPD':'JML', 'DT_TRACRE':'D4', 'DT_TRAUPD':'DUPD4']], 
	['AA.BBB.001.LEC.01':['ID_NUMACT':'JMLLEC12', 'ID_CODNAF':5, 'ST_DES':'DES5', 'ST_TRAUTICRE':'TUTU', 'ST_TRAUTIUPD':'TATA', 'DT_TRACRE':'D5', 'DT_TRAUPD':'DUPD5']]
	
]


List listTests1 = [
	"'AA.BBB.001.CRE.01' - 'JMLCRE01'", 
	"'AA.BBB.001.CRE.02' - 'JMLCRE02'", 
	"'AA.BBB.001.CRE.03' - 'JMLCRE03'", 
	"'AA.BBB.001.LEC.01' - 'JMLLEC11'", 
	"'AA.BBB.001.LEC.01' - 'JMLLEC12'"
	]
	
List listTests2 = [
	"'AA.BBB.001.CRE.01' - '1'",
	"'AA.BBB.001.CRE.02' - '2'",
	"'AA.BBB.001.CRE.03' - '3'",
	"'AA.BBB.001.LEC.01' - '4'",
	"'AA.BBB.001.LEC.01' - '5'"
	]

Workbook  book = tnrCommon.ExcelUtils.open('TNR_JDDTest\\PREJDD.AA.BBB.xlsx')
Sheet sheet = book.getSheet('001')	

JDDHeader myPREJDDheader = new JDDHeader(sheet)
JDDData myPREJDDData = new JDDData(sheet,myPREJDDheader.getList(),'')

Tools.displayWithQuotes(myPREJDDData.getList())

Log.addAssert("Lecture PREJDD myPREJDDData.getList()",datasTests,myPREJDDData.getList())


Method method = PREJDD.class.getDeclaredMethod("getListOfCasDeTestAndIDValue", Sheet.class,String.class);
method.setAccessible(true);

Log.addAssert("(private) getListOfCasDeTestAndIDValue(sheet, 'ID_NUMACT')",listTests1,(List) method.invoke(PREJDD, sheet,'ID_NUMACT'))
Log.addAssert("(private) getListOfCasDeTestAndIDValue(sheet, 'ID_CODNAF')",listTests2,(List) method.invoke(PREJDD, sheet,'ID_CODNAF'))

Log.addAssert("(private) getListOfCasDeTestAndIDValue(sheet, 'UNK')",[],(List) method.invoke(PREJDD, sheet,'UNK'))
Log.addAssert("(private) getListOfCasDeTestAndIDValue(sheet, '')",[],(List) method.invoke(PREJDD, sheet,''))
Log.addAssert("(private) getListOfCasDeTestAndIDValue(sheet, null)",[],(List) method.invoke(PREJDD, sheet,null))
Log.addAssert("(private) getListOfCasDeTestAndIDValue(null,'ID_NUMACT')",[],(List) method.invoke(PREJDD, null,'ID_NUMACT'))


