import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import my.Log
import myJDDManager.JDDDatas
import myJDDManager.JDDHeaders

/**
 * Unit tests
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */

Workbook  book = my.XLS.open('TNR_JDDTest\\JDD.AA.BBB.xlsx')
Sheet sheet = book.getSheet('001')

List<Map<String, Map<String, String>>> datasListTest = [
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

	
	
JDDHeaders JDDHeader = new JDDHeaders(sheet)

JDDDatas JDDData = new JDDDatas(sheet,JDDHeader,'CAS_DE_TEST')


Log.addAssert("JDDData.datasList",datasListTest,JDDData.datasList)
Log.addAssert("JDDData.getRawData('ST_INA', 'AA.BBB.001.LEC.01', 2)",'JMLLEC12_INA',JDDData.getRawData('ST_INA', 'AA.BBB.001.LEC.01', 2))
Log.addAssert("JDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01' )",'DESJMLMAJ01',JDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01' ))
Log.addAssert("JDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',1 )",'DESJMLMAJ01',JDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',1 ))
Log.addAssert("JDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',2 )",null,JDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01',2 ))
Log.addAssert("JDDData.getRawData('UNKNOWN', 'AA.BBB.001.MAJ.01' )",null,JDDData.getRawData('UNKNOWN', 'AA.BBB.001.MAJ.01' ))
Log.addAssert("JDDData.getRawData('ST_DES', 'AA.BBB.001.UNK.01' )",null,JDDData.getRawData('ST_DES', 'AA.BBB.001.UNK.01' ))



Log.addAssert("JDDData.getCdtsContainingSubstringWithoutDuplicates('BB.001' )",listCDTWithoutDuplicates,JDDData.getCdtsContainingSubstringWithoutDuplicates('BB.001' ))
Log.addAssert("JDDData.getCdtsContainingSubstringWithoutDuplicates('AA.BBB.001.LEC' )",['AA.BBB.001.LEC.01'],JDDData.getCdtsContainingSubstringWithoutDuplicates('AA.BBB.001.LEC' ))
Log.addAssert("JDDData.getCdtsContainingSubstringWithoutDuplicates('AA.BBB.001.UNK.01' )",[],JDDData.getCdtsContainingSubstringWithoutDuplicates('AA.BBB.001.UNK.01' ))

Log.addAssert("JDDData.getNbrLigneCasDeTest('AA.BBB.001.MAJ.01')",1, JDDData.getNbrLigneCasDeTest('AA.BBB.001.MAJ.01'))
Log.addAssert("JDDData.getNbrLigneCasDeTest('AA.BBB.001.LEC.01')",3, JDDData.getNbrLigneCasDeTest('AA.BBB.001.LEC.01'))
Log.addAssert("JDDData.getNbrLigneCasDeTest('AA.BBB.001.UNK.01')",0, JDDData.getNbrLigneCasDeTest('AA.BBB.001.UNK.01'))

Log.addAssert("JDDData.setValueOf('ST_DES','UPD_DESMAJ01','AA.BBB.001.MAJ.01',1)",null,JDDData.setValueOf('ST_DES','UPD_DESMAJ01','AA.BBB.001.MAJ.01',1))
Log.addAssert("JDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01' )",'UPD_DESMAJ01',JDDData.getRawData('ST_DES', 'AA.BBB.001.MAJ.01' ))



