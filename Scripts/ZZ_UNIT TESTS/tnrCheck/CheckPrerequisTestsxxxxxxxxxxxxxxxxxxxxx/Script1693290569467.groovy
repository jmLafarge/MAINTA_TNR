

import java.lang.reflect.Method

import tnrCheck.CheckPrerequis
import tnrLog.Log

/**
 * UNIT TESTS
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

/**
 *
 * TEST private static List <String >getListCDTVAL(List <Map<String, Map<String, Object>>> datas,String table, String name) {
 *
 */

List<Map<String, Map<String, Object>>> datasListTest_PK = [
	['AA.BBB.001.CRE.01':['ID_CODINT':'JMLCRE01', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_CODINT':'JMLCRE02', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_CODINT':'JMLCRE03', 'ST_DES':'DESJMLCRE03', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_CODINT':'JMLLEC11', 'ST_DES':'DESJMLLEC11', 'ST_INA':'JMLLEC11_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_CODINT':'JMLLEC12', 'ST_DES':'DESJMLLEC12', 'ST_INA':'JMLLEC12_INA', 'NU_IV':null]],
	['AA.BBB.001.MAJ.01':['ID_CODINT':null, 'ST_DES':'DESJMLMAJ01', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]],
	['AA.BBB.001.MAJ.02':['ID_CODINT':'', 'ST_DES':'DESJMLMAJ01', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]],
	['AA.BBB.001.MAJ.03':['ID_CODINT':'$NU', 'ST_DES':'DESJMLMAJ01', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]],
	['AA.BBB.001.MAJ.04':['ID_CODINT':'$NULL', 'ST_DES':'DESJMLMAJ01', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]],
	['AA.BBB.001.MAJ.05':['ID_CODINT':'$VIDE', 'ST_DES':'DESJMLMAJ01', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]],
	['AA.BBB.001.MAJ.06':['ID_CODINT':'$UPD*JMLMAJ01*NEWVAL', 'ST_DES':'DESJMLMAJ01', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]],
	['AA.BBB.001.SUP.01':['ID_CODINT':'JMLMAJ02', 'ST_DES':'DESJMLMAJ02', 'ST_INA':'JMLMAJ02_INA', 'NU_IV':null]]
]

List<String> resultListTest_PK = [
	"'AA.BBB.001.LEC.01' - 'JMLLEC11'",
	"'AA.BBB.001.LEC.01' - 'JMLLEC12'",
	"'AA.BBB.001.MAJ.06' - 'JMLMAJ01'",
	"'AA.BBB.001.SUP.01' - 'JMLMAJ02'"

]


List<Map<String, Map<String, Object>>> datasListTest_nonPK = [
	['AA.BBB.001.CRE.01':['ID_CODINT':'JMLCRE01', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_CODINT':'JMLCRE02', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_CODINT':'JMLCRE03', 'ST_DES':'DESJMLCRE03', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_CODINT':'JMLLEC11', 'ST_DES':'DESJMLLEC11', 'ST_INA':'JMLLEC11_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_CODINT':'JMLLEC12', 'ST_DES':'DESJMLLEC12', 'ST_INA':'JMLLEC12_INA', 'NU_IV':null]]
]

List<String> resultListTest_nonPK = [
	"'AA.BBB.001.CRE.01' - 'DESJMLCRE01'",
	"'AA.BBB.001.CRE.02' - 'DESJMLCRE02'",
	"'AA.BBB.001.CRE.03' - 'DESJMLCRE03'",
	"'AA.BBB.001.LEC.01' - 'DESJMLLEC11'",
	"'AA.BBB.001.LEC.01' - 'DESJMLLEC12'"

]


Method method = CheckPrerequis.class.getDeclaredMethod("getListCDTVAL", List.class, String.class, String.class);
method.setAccessible(true);
Log.addAssert("(private) CheckPrerequis d'une colonne PK",resultListTest_PK, method.invoke(CheckPrerequis, datasListTest_PK, 'INTER','ID_CODINT'))
Log.addAssert("(private) CheckPrerequis d'une colonne non PK",resultListTest_nonPK, method.invoke(CheckPrerequis, datasListTest_nonPK, 'INTER','ST_DES'))

Log.addAssert("(private) CheckPrerequis d'une colonne UNK",[], method.invoke(CheckPrerequis, datasListTest_PK, 'INTER','UNK'))
Log.addAssert("(private) CheckPrerequis d'une colonne vide",[], method.invoke(CheckPrerequis, datasListTest_PK, 'INTER',''))
Log.addAssert("(private) CheckPrerequis d'une colonne null",[], method.invoke(CheckPrerequis, datasListTest_PK, 'INTER',null))


Log.addAssert("(private) CheckPrerequis d'une table UNK",[], method.invoke(CheckPrerequis, datasListTest_PK, 'UNK','ID_CODINT'))
Log.addAssert("(private) CheckPrerequis d'une table vide",[], method.invoke(CheckPrerequis, datasListTest_PK, '','ID_CODINT'))
Log.addAssert("(private) CheckPrerequis d'une table null",[], method.invoke(CheckPrerequis, datasListTest_PK, null,'ID_CODINT'))




