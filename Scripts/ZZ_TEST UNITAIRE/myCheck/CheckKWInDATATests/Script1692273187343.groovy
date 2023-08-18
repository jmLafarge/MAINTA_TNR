

import my.Log
import myCheck.CheckKWInDATA


List<Map<String, Map<String, String>>> datasListTestTRUE = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'$ORDRE', 'ST_INA':'$DATETIMESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'$SEQUENCEID', 'ST_DES':'DESJMLCRE02', 'ST_INA':'DATESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'$NULL', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC11', 'ST_DES':'$NU', 'ST_INA':'JMLLEC11_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'$ORDRE', 'ST_DES':'NULL', 'ST_INA':'JMLLEC12_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC13', 'ST_DES':'$VIDE', 'ST_INA':'JMLLEC13_INA', 'NU_IV':null]],
	['AA.BBB.001.MAJ.01':['ID_JML':'JMLMAJ01', 'ST_DES':'$TBD', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]],
	['AA.BBB.001.SUP.01':['ID_JML':'JMLMAJ02', 'ST_DES':'$UPD*VAL*NEWVAL', 'ST_INA':'JMLMAJ02_INA', 'NU_IV':null]]
]

List<Map<String, Map<String, String>>> datasListTestFALSE = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'$UNK', 'ST_INA':'$DATETIMESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'$UPD', 'ST_DES':'DESJMLCRE02', 'ST_INA':'DATESYS', 'NU_IV':null]]
]

Log.addAssert("CheckKWInDATA.run(datasListTestTRUE, false, 'le JDDFullName', 'le sheetName', true)",true,CheckKWInDATA.run(datasListTestTRUE, false, 'le JDDFullName', 'le sheetName', true))
Log.addAssert("CheckKWInDATA.run(datasListTestFALSE, false, 'le JDDFullName', 'le sheetName', false)",false,CheckKWInDATA.run(datasListTestFALSE, false, 'le JDDFullName', 'le sheetName', true))

Log.addAssert("CheckKWInDATA.run(datasListTestTRUE, false, 'le JDDFullName', 'le sheetName', false)",false,CheckKWInDATA.run(datasListTestTRUE, false, 'le JDDFullName', 'le sheetName', false))
Log.addAssert("CheckKWInDATA.run(datasListTestFALSE, false, 'le JDDFullName', 'le sheetName', false)",false,CheckKWInDATA.run(datasListTestFALSE, false, 'le JDDFullName', 'le sheetName', false))