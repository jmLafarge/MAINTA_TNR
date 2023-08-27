import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import tnrLog.Log
import tnrJDDManager.JDDData
import tnrJDDManager.JDDHeader

/**
 * UNIT TESTS
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

Workbook  book = tnrCommon.ExcelUtils.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')
Sheet sheet = book.getSheet('001')

List<Map<String, Map<String, Object>>> datasListTest = [
	['AA.BBB.001.CRE.01':['ID_JML':'JMLCRE01', 'ST_DES':'DESJMLCRE01', 'ST_INA':'JMLCRE01_INA', 'NU_IV':null]], 
	['AA.BBB.001.CRE.02':['ID_JML':'JMLCRE02', 'ST_DES':'DESJMLCRE02', 'ST_INA':'JMLCRE02_INA', 'NU_IV':null]], 
	['AA.BBB.001.CRE.03':['ID_JML':'JMLCRE03', 'ST_DES':'DESJMLCRE03', 'ST_INA':'JMLCRE03_INA', 'NU_IV':null]], 
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC11', 'ST_DES':'DESJMLLEC11', 'ST_INA':'JMLLEC11_INA', 'NU_IV':null]], 
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC12', 'ST_DES':'DESJMLLEC12', 'ST_INA':'JMLLEC12_INA', 'NU_IV':null]], 
	['AA.BBB.001.LEC.01':['ID_JML':'JMLLEC13', 'ST_DES':'DESJMLLEC13', 'ST_INA':'JMLLEC13_INA', 'NU_IV':null]], 
	['AA.BBB.001.MAJ.01':['ID_JML':'JMLMAJ01', 'ST_DES':'DESJMLMAJ01', 'ST_INA':'JMLMAJ01_INA', 'NU_IV':null]], 
	['AA.BBB.001.SUP.01':['ID_JML':'JMLMAJ02', 'ST_DES':'DESJMLMAJ02', 'ST_INA':'JMLMAJ02_INA', 'NU_IV':null]]
]

List <String> listCDTWithoutDuplicates = [
	'AA.BBB.001.CRE.01',
	'AA.BBB.001.CRE.02',
	'AA.BBB.001.CRE.03',
	'AA.BBB.001.LEC.01',
	'AA.BBB.001.MAJ.01',
	'AA.BBB.001.SUP.01'
	]

	
	
JDDHeader myJDDHeader = new JDDHeader(sheet)

JDDData myJDDData = new JDDData(sheet,myJDDHeader,'CAS_DE_TEST')


Log.addAssert("myJDDData.datasList",datasListTest,myJDDData.datasList)

Log.addAssert("myJDDData.getRawData('ST_INA', 'AA.BBB.001.LEC.01', 2)",'JMLLEC12_INA',myJDDData.getRawData('ST_INA', 'AA.BBB.001.LEC.01', 2))
Log.addAssert("myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',1 )",'DESJMLMAJ01',myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',1 ))
Log.addAssert("myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',1 )",'DESJMLMAJ01',myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',1 ))
Log.addAssert("myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',2 )",null,myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',2 ))
Log.addAssert("myJDDData.getRawData('UNKNOWN', 'AA.BBB.001.MAJ.01',1)",null,myJDDData.getRawData('UNKNOWN', 'AA.BBB.001.MAJ.01',1 ))
Log.addAssert("myJDDData.getRawData('ST_DES', 'AA.BBB.001.UNK.01',1 )",null,myJDDData.getRawData('ST_DES', 'AA.BBB.001.UNK.01',1 ))



Log.addAssert("myJDDData.getCdtsContainingSubstringWithoutDuplicates('BB.001' )",listCDTWithoutDuplicates,myJDDData.getCdtsContainingSubstringWithoutDuplicates('BB.001' ))
Log.addAssert("myJDDData.getCdtsContainingSubstringWithoutDuplicates('AA.BBB.001.LEC' )",['AA.BBB.001.LEC.01'],myJDDData.getCdtsContainingSubstringWithoutDuplicates('AA.BBB.001.LEC' ))
Log.addAssert("myJDDData.getCdtsContainingSubstringWithoutDuplicates('AA.BBB.001.UNK.01' )",[],myJDDData.getCdtsContainingSubstringWithoutDuplicates('AA.BBB.001.UNK.01' ))

Log.addAssert("myJDDData.getNbrLigneCasDeTest('AA.BBB.001.MAJ.01')",1, myJDDData.getNbrLigneCasDeTest('AA.BBB.001.MAJ.01'))
Log.addAssert("myJDDData.getNbrLigneCasDeTest('AA.BBB.001.LEC.01')",3, myJDDData.getNbrLigneCasDeTest('AA.BBB.001.LEC.01'))
Log.addAssert("myJDDData.getNbrLigneCasDeTest('AA.BBB.001.UNK.01')",0, myJDDData.getNbrLigneCasDeTest('AA.BBB.001.UNK.01'))

Log.addAssert("myJDDData.setValueOf('ST_DES','UPD_DESMAJ01','AA.BBB.001.MAJ.01',1)",null,myJDDData.setValueOf('ST_DES','UPD_DESMAJ01','AA.BBB.001.MAJ.01',1))
Log.addAssert("myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',1 )",'UPD_DESMAJ01',myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01' ,1))



List<String> resultList1 =[
	'AA.BBB.001.CRE.01-JMLCRE01',
	'AA.BBB.001.CRE.02-JMLCRE02',
	'AA.BBB.001.CRE.03-JMLCRE03',
	'AA.BBB.001.LEC.01-JMLLEC11',
	'AA.BBB.001.LEC.01-JMLLEC12',
	'AA.BBB.001.LEC.01-JMLLEC13',
	'AA.BBB.001.MAJ.01-JMLMAJ01',
	'AA.BBB.001.SUP.01-JMLMAJ02']

Log.addAssert("concatenateCdtsAndValues(datasListTest, ['ID_JML']",resultList1,myJDDData.concatenateCdtsAndValues(datasListTest, ['ID_JML']))

List<String> resultList2 =[
	'AA.BBB.001.CRE.01-JMLCRE01-JMLCRE01_INA',
	'AA.BBB.001.CRE.02-JMLCRE02-JMLCRE02_INA',
	'AA.BBB.001.CRE.03-JMLCRE03-JMLCRE03_INA',
	'AA.BBB.001.LEC.01-JMLLEC11-JMLLEC11_INA',
	'AA.BBB.001.LEC.01-JMLLEC12-JMLLEC12_INA',
	'AA.BBB.001.LEC.01-JMLLEC13-JMLLEC13_INA',
	'AA.BBB.001.MAJ.01-JMLMAJ01-JMLMAJ01_INA',
	'AA.BBB.001.SUP.01-JMLMAJ02-JMLMAJ02_INA']

Log.addAssert("xxxxxxxxxx",resultList2,myJDDData.concatenateCdtsAndValues(datasListTest, ['ID_JML','ST_INA']))

Log.addAssert("Colonne UNK",[],myJDDData.concatenateCdtsAndValues(datasListTest, ['UNK']))

