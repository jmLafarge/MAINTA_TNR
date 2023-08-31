

import tnrCheck.data.CheckKW
import tnrLog.Log


/**
 * TESTS UNITAIRES
 *
 * static boolean run(String typeFile,List <Map<String, Map<String, String>>> datasList, String JDDFullName, String sheetName)
 * 
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_FOR_LOG = 'tnrCheck.data.CheckKW'


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

Log.addAssert(CLASS_FOR_LOG,"Controle OK dans le JDD avec status à TRUE",true,CheckKW.run('JDD',datasListTestJDDTRUE, 'le JDDFullName', 'le sheetName'))
Log.addAssert(CLASS_FOR_LOG,"Controle OK dans le PREJDD avec status à TRUE",true,CheckKW.run('JDD',datasListTestPREJDDTRUE, 'le JDDFullName', 'le sheetName'))
Log.addAssert(CLASS_FOR_LOG,"Mot clé '\$UPD*VAL*NEWVAL' dans le PREJDD avec status à TRUE",false,CheckKW.run('PREJDD',datasListTestJDDTRUE, 'le JDDFullName', 'le sheetName'))

Log.addAssert(CLASS_FOR_LOG,"Mot clé '\$UNK' avec status à TRUE",false,CheckKW.run('PREJDD',datasListTestFALSE, 'le JDDFullName', 'le sheetName'))
