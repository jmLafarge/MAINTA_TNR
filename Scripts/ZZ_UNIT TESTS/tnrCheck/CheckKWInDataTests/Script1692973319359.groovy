

import tnrLog.Log
import tnrCheck.CheckKWInData

/**
 * UNIT TESTS
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

/**
 *
 * TEST static boolean run(String typeFile,List <Map<String, Map<String, String>>> datas, String JDDFullName, String sheetName, boolean status)
 *
 */



List<Map<String, Map<String, Object>>> datasListTestJDDTRUE = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'$ORDRE', 'ST_INA':'$DATETIMESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'$SEQUENCEID', 'ST_DES':'DESJMLCRE02', 'ST_INA':'DATESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'$NULL', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC11', 'ST_DES':'$NU', 'ST_INA':'JMLLEC11_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'$ORDRE', 'ST_DES':'NULL', 'ST_INA':'JMLLEC12_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC13', 'ST_DES':'$VIDE', 'ST_INA':'JMLLEC13_INA', 'NU_IV':null]],
	['AA.BBB.001.MAJ.01':['ID_JML':'JMLMAJ01', 'ST_DES':'$TBD', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]],
	['AA.BBB.001.SUP.01':['ID_JML':'JMLMAJ02', 'ST_DES':'$UPD*VAL*NEWVAL', 'ST_INA':'JMLMAJ02_INA', 'NU_IV':null]]
]

List<Map<String, Map<String, Object>>> datasListTestPREJDDTRUE = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'$ORDRE', 'ST_INA':'$DATETIMESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'$SEQUENCEID', 'ST_DES':'DESJMLCRE02', 'ST_INA':'DATESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'$NULL', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC11', 'ST_DES':'$NU', 'ST_INA':'JMLLEC11_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'$ORDRE', 'ST_DES':'NULL', 'ST_INA':'JMLLEC12_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC13', 'ST_DES':'$VIDE', 'ST_INA':'JMLLEC13_INA', 'NU_IV':null]],
	['AA.BBB.001.MAJ.01':['ID_JML':'JMLMAJ01', 'ST_DES':'$TBD', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]]
]

List<Map<String, Map<String, Object>>> datasListTestFALSE = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'$UNK', 'ST_INA':'$DATETIMESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'$UPD', 'ST_DES':'DESJMLCRE02', 'ST_INA':'DATESYS', 'NU_IV':null]]
]

Log.addAssert("Controle OK dans le JDD avec status à TRUE",true,CheckKWInData.run('JDD',datasListTestJDDTRUE, 'le JDDFullName', 'le sheetName', true))
Log.addAssert("Controle OK dans le PREJDD avec status à TRUE",true,CheckKWInData.run('JDD',datasListTestPREJDDTRUE, 'le JDDFullName', 'le sheetName', true))
Log.addAssert("Mot clé '\$UPD*VAL*NEWVAL' dans le PREJDD avec status à TRUE",false,CheckKWInData.run('PREJDD',datasListTestJDDTRUE, 'le JDDFullName', 'le sheetName', true))

Log.addAssert("Mot clé '\$UNK' avec status à TRUE",false,CheckKWInData.run('PREJDD',datasListTestFALSE, 'le JDDFullName', 'le sheetName', true))

Log.addAssert("Controle OK avec status à FALSE",false,CheckKWInData.run('PREJDD',datasListTestJDDTRUE, 'le JDDFullName', 'le sheetName', false))
Log.addAssert("Mot clé '\$UNK' avec status à FALSE",false,CheckKWInData.run('PREJDD',datasListTestFALSE, 'le JDDFullName', 'le sheetName', false))
