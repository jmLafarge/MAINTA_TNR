

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import tnrCheck.data.CheckKW
import tnrCommon.ExcelUtils
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDHeader
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDParam
import tnrLog.Log


/**
 * TESTS UNITAIRES
 *
 * static boolean run(String typeFile,List <Map<String, Map<String, Object>>> datasList, JDDParam myJDDParam, String JDDFullName, String sheetName) {
 * 
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_NAME = 'tnrCheck.data.CheckKW'

Workbook  book = ExcelUtils.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')
Sheet sheet = book.getSheet('001')
JDDHeader myJDDHeader = new JDDHeader(sheet)
JDDParam myJDDParam = new JDDParam(sheet,myJDDHeader)


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
Log.addAssert(CLASS_NAME,"Controle OK dans le JDD",true,CheckKW.run('JDD',datasListTestJDDTRUE, myJDDParam,'le JDDFullName', 'le sheetName'))
Log.addAssert(CLASS_NAME,"Mot clé '\$UPD*VAL*NEWVAL' dans le PREJDD",false,CheckKW.run('PREJDD',datasListTestJDDTRUE, myJDDParam,'le JDDFullName', 'le sheetName'))



List<Map<String, Map<String, Object>>> datasListTestPREJDDTRUE = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'$ORDRE', 'ST_INA':'$DATETIMESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'$SEQUENCEID', 'ST_DES':'DESJMLCRE02', 'ST_INA':'DATESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'$NULL', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC11', 'ST_DES':'$NU', 'ST_INA':'JMLLEC11_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'$ORDRE', 'ST_DES':'NULL', 'ST_INA':'JMLLEC12_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC13', 'ST_DES':'$VIDE', 'ST_INA':'JMLLEC13_INA', 'NU_IV':null]],
	['AA.BBB.001.MAJ.01':['ID_JML':'JMLMAJ01', 'ST_DES':'$TBD', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]]
]
Log.addAssert(CLASS_NAME,"Controle OK dans le PREJDD",true,CheckKW.run('JDD',datasListTestPREJDDTRUE, myJDDParam,'le JDDFullName', 'le sheetName'))



List<Map<String, Map<String, Object>>> datasListTestFALSE = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'$UNK', 'ST_INA':'$DATETIMESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'$UPD', 'ST_DES':'DESJMLCRE02', 'ST_INA':'DATESYS', 'NU_IV':null]]
]
Log.addAssert(CLASS_NAME,"Mot clé '\$UNK'",false,CheckKW.run('PREJDD',datasListTestFALSE, myJDDParam,'le JDDFullName', 'le sheetName'))




List<Map<String, Map<String, Object>>> datasListTestTableSEQUENCEKO = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'$ORDRE', 'ST_INA':'$DATETIMESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'$SEQUENCEID', 'ST_DES':'DESJMLCRE02', 'ST_INA':'DATESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'$NULL', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC11', 'ST_DES':'$NU', 'ST_INA':'$SEQUENCEID', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'$ORDRE', 'ST_DES':'NULL', 'ST_INA':'$SEQUENCEID', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC13', 'ST_DES':'$VIDE', 'ST_INA':'$SEQUENCEID', 'NU_IV':null]],
	['AA.BBB.001.MAJ.01':['ID_JML':'JMLMAJ01', 'ST_DES':'$TBD', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]],
	['AA.BBB.001.SUP.01':['ID_JML':'JMLMAJ02', 'ST_DES':'$UPD*VAL*NEWVAL', 'ST_INA':'JMLMAJ02_INA', 'NU_IV':null]]
]
Log.addAssert(CLASS_NAME,"Controle table SEQUENCE KO sur ST_INA",false,CheckKW.run('JDD',datasListTestTableSEQUENCEKO, myJDDParam,'le JDDFullName', 'le sheetName'))


List<Map<String, Map<String, Object>>> datasListTestSEQUENCEKO = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'$ORDRE', 'ST_INA':'$DATETIMESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.02':['ID_JML':'$SEQUENCEID', 'ST_DES':'DESJMLCRE02', 'ST_INA':'DATESYS', 'NU_IV':null]],
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'$NULL', 'ST_INA':'JMLCRE03_INA', 'NU_IV':'$SEQUENCEID']],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC11', 'ST_DES':'$NU', 'ST_INA':'JMLLEC11_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'$ORDRE', 'ST_DES':'NULL', 'ST_INA':'JMLLEC12_INA', 'NU_IV':null]],
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC13', 'ST_DES':'$VIDE', 'ST_INA':'JMLLEC13_INA', 'NU_IV':null]],
	['AA.BBB.001.MAJ.01':['ID_JML':'JMLMAJ01', 'ST_DES':'$TBD', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]],
	['AA.BBB.001.SUP.01':['ID_JML':'JMLMAJ02', 'ST_DES':'$UPD*VAL*NEWVAL', 'ST_INA':'JMLMAJ02_INA', 'NU_IV':null]]
]
Log.addAssert(CLASS_NAME,"Controle SEQUENCE KO sur NU_IV",false,CheckKW.run('JDD',datasListTestSEQUENCEKO, myJDDParam,'le JDDFullName', 'le sheetName'))
