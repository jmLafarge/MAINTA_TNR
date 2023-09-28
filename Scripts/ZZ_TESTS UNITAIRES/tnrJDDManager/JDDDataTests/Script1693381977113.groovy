import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import tnrCommon.ExcelUtils
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDData
import tnrJDDManager.JDD; import tnrJDDManager.GlobalJDDHeader
import tnrLog.Log

/**
 * TESTS UNITAIRES
 * 
 * public getRawData(String name, String cdt, int cdtnum )
 * public List<String> getCdtsStartsWithStrWithoutDuplicates(String str)
 * public List<String> concatenateCdtsAndValues(List<Map<String, Map<String, Object>>> JDDDatas, List<String> namesToConcat)
 * public int getNbrLigneCasDeTest(String cdt)
 * public void setValueOf(String name, def value, String cdt, int cdtnum)
 * public String getValueOf(String name,String cdt,String whereName, def value) {
 * 
 *
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_NAME = 'tnrJDDManager.JDDData'

Workbook  book = ExcelUtils.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')
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
JDDData myJDDData = new JDDData(sheet,myJDDHeader.getList(),'CAS_DE_TEST')
Log.addAssert(CLASS_NAME,"myJDDData.datasList",datasListTest,myJDDData.datasList)


Log.addAssert(CLASS_NAME,"myJDDData.getRawData('ST_INA', 'AA.BBB.001.LEC.01', 2)",'JMLLEC12_INA',myJDDData.getRawData('ST_INA', 'AA.BBB.001.LEC.01', 2))
Log.addAssert(CLASS_NAME,"myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',1 )",'DESJMLMAJ01',myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',1 ))
Log.addAssert(CLASS_NAME,"myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',1 )",'DESJMLMAJ01',myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',1 ))
Log.addAssert(CLASS_NAME,"myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',2 )",null,myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',2 ))
Log.addAssert(CLASS_NAME,"myJDDData.getRawData('UNKNOWN', 'AA.BBB.001.MAJ.01',1)",null,myJDDData.getRawData('UNKNOWN', 'AA.BBB.001.MAJ.01',1 ))
Log.addAssert(CLASS_NAME,"myJDDData.getRawData('ST_DES', 'AA.BBB.001.UNK.01',1 )",null,myJDDData.getRawData('ST_DES', 'AA.BBB.001.UNK.01',1 ))




Log.addAssert(CLASS_NAME,"myJDDData.getCdtsStartsWithStrWithoutDuplicates('AA.BBB' )",listCDTWithoutDuplicates,myJDDData.getCdtsStartsWithStrWithoutDuplicates('AA.BBB' ))
Log.addAssert(CLASS_NAME,"myJDDData.getCdtsStartsWithStrWithoutDuplicates('AA.BBB.001.LEC' )",['AA.BBB.001.LEC.01'],myJDDData.getCdtsStartsWithStrWithoutDuplicates('AA.BBB.001.LEC' ))
Log.addAssert(CLASS_NAME,"myJDDData.getCdtsStartsWithStrWithoutDuplicates('AA.BBB.001.LEC.01' )",['AA.BBB.001.LEC.01'],myJDDData.getCdtsStartsWithStrWithoutDuplicates('AA.BBB.001.LEC.01' ))
Log.addAssert(CLASS_NAME,"myJDDData.getCdtsStartsWithStrWithoutDuplicates('AA.BBB.001.UNK.01' )",[],myJDDData.getCdtsStartsWithStrWithoutDuplicates('AA.BBB.001.UNK.01' ))
Log.addAssert(CLASS_NAME,"myJDDData.getCdtsStartsWithStrWithoutDuplicates('BBB.001' )",[],myJDDData.getCdtsStartsWithStrWithoutDuplicates('BBB.001' ))



List<String> resultList1 =[
	'AA.BBB.001.CRE.01-JMLCRE01',
	'AA.BBB.001.CRE.02-JMLCRE02',
	'AA.BBB.001.CRE.03-JMLCRE03',
	'AA.BBB.001.LEC.01-JMLLEC11',
	'AA.BBB.001.LEC.01-JMLLEC12',
	'AA.BBB.001.LEC.01-JMLLEC13',
	'AA.BBB.001.MAJ.01-JMLMAJ01',
	'AA.BBB.001.SUP.01-JMLMAJ02']
Log.addAssert(CLASS_NAME,"concatenateCdtsAndValues(datasListTest, ['ID_JML']",resultList1,myJDDData.concatenateCdtsAndValues(datasListTest, ['ID_JML']))

List<String> resultList2 =[
	'AA.BBB.001.CRE.01-JMLCRE01-JMLCRE01_INA',
	'AA.BBB.001.CRE.02-JMLCRE02-JMLCRE02_INA',
	'AA.BBB.001.CRE.03-JMLCRE03-JMLCRE03_INA',
	'AA.BBB.001.LEC.01-JMLLEC11-JMLLEC11_INA',
	'AA.BBB.001.LEC.01-JMLLEC12-JMLLEC12_INA',
	'AA.BBB.001.LEC.01-JMLLEC13-JMLLEC13_INA',
	'AA.BBB.001.MAJ.01-JMLMAJ01-JMLMAJ01_INA',
	'AA.BBB.001.SUP.01-JMLMAJ02-JMLMAJ02_INA']
Log.addAssert(CLASS_NAME,"concatenateCdtsAndValues(datasListTest, ['ID_JML','ST_INA']",resultList2,myJDDData.concatenateCdtsAndValues(datasListTest, ['ID_JML','ST_INA']))
Log.addAssert(CLASS_NAME,"concatenateCdtsAndValues(datasListTest, ['UNK']",[],myJDDData.concatenateCdtsAndValues(datasListTest, ['UNK']))



Log.addAssert(CLASS_NAME,"myJDDData.getNbrLigneCasDeTest('AA.BBB.001.MAJ.01')",1, myJDDData.getNbrLigneCasDeTest('AA.BBB.001.MAJ.01'))
Log.addAssert(CLASS_NAME,"myJDDData.getNbrLigneCasDeTest('AA.BBB.001.LEC.01')",3, myJDDData.getNbrLigneCasDeTest('AA.BBB.001.LEC.01'))
Log.addAssert(CLASS_NAME,"myJDDData.getNbrLigneCasDeTest('AA.BBB.001.UNK.01')",0, myJDDData.getNbrLigneCasDeTest('AA.BBB.001.UNK.01'))

Log.addAssert(CLASS_NAME,"myJDDData.setValueOf('ST_DES','UPD_DESMAJ01','AA.BBB.001.MAJ.01',1)",null,myJDDData.setValueOf('ST_DES','UPD_DESMAJ01','AA.BBB.001.MAJ.01',1))
Log.addAssert(CLASS_NAME,"myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',1 )",'UPD_DESMAJ01',myJDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01' ,1))



Log.addAssert(CLASS_NAME, "myJDDData.getValueOf('ST_DES','AA.BBB.001.LEC.01','ID_JML','JMLLEC12')",'DESJMLLEC12',myJDDData.getValueOf('ST_DES','AA.BBB.001.LEC.01','ID_JML','JMLLEC12'))
Log.addAssert(CLASS_NAME, "myJDDData.getValueOf('UNK','AA.BBB.001.LEC.01','ID_JML','JMLLEC12')",null,myJDDData.getValueOf('UNK','AA.BBB.001.LEC.01','ID_JML','JMLLEC12'))
Log.addAssert(CLASS_NAME, "myJDDData.getValueOf('ST_DES','AA.BBB.001.LEC.01','ID_JML','UNK')",null,myJDDData.getValueOf('ST_DES','AA.BBB.001.LEC.01','ID_JML','UNK'))
Log.addAssert(CLASS_NAME, "myJDDData.getValueOf('ST_DES','AA.BBB.001.LEC.01','UNK','JMLLEC12')",null,myJDDData.getValueOf('ST_DES','AA.BBB.001.LEC.01','UNK','JMLLEC12'))
Log.addAssert(CLASS_NAME, "myJDDData.getValueOf('ST_DES','UNK','ID_JML','JMLLEC12')",null,myJDDData.getValueOf('ST_DES','UNK','ID_JML','JMLLEC12'))
Log.addAssert(CLASS_NAME, "myJDDData.getValueOf('','AA.BBB.001.LEC.01','ID_JML','JMLLEC12')",null,myJDDData.getValueOf('','AA.BBB.001.LEC.01','ID_JML','JMLLEC12'))
Log.addAssert(CLASS_NAME, "myJDDData.getValueOf('ST_DES','AA.BBB.001.LEC.01','ID_JML','')",null,myJDDData.getValueOf('ST_DES','AA.BBB.001.LEC.01','ID_JML',''))
Log.addAssert(CLASS_NAME, "myJDDData.getValueOf('ST_DES','AA.BBB.001.LEC.01','','JMLLEC12')",null,myJDDData.getValueOf('ST_DES','AA.BBB.001.LEC.01','','JMLLEC12'))
Log.addAssert(CLASS_NAME, "myJDDData.getValueOf('ST_DES','','ID_JML','JMLLEC12')",null,myJDDData.getValueOf('ST_DES','','ID_JML','JMLLEC12'))




