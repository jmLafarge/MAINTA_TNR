import tnrJDDManager.JDDFileMapper
import tnrLog.Log

/**
 * TESTS UNITAIRES
 * 
 * static String getFullnameFromCasDeTest(String casDeTest)
 * static String getFullnameFromModObj(String modObj)
 * static void add(String modObj,String fullName)
 *
 * @author JM Lafarge
 * @version 1.0
 */

Log.addAssert("JDDFileMapper.getFullnameFromCasDeTest('RO.ACT.001.MAJ.01')",'TNR_JDD\\JDD.RO.ACT.xlsx',JDDFileMapper.getFullnameFromCasDeTest('RO.ACT.001.MAJ.01'))
Log.addAssert("JDDFileMapper.getFullnameFromCasDeTest('RO.ACT.003MET.SRL.01')",'TNR_JDD\\JDD.RO.ACT.xlsx',JDDFileMapper.getFullnameFromCasDeTest('RO.ACT.003MET.SRL.01'))
Log.addAssert("JDDFileMapper.getFullnameFromCasDeTest('RO.ACT.001.UNK.01')",'TNR_JDD\\JDD.RO.ACT.xlsx',JDDFileMapper.getFullnameFromCasDeTest('RO.ACT.001.UNK.01'))

Log.addAssert("JDDFileMapper.getFullnameFromCasDeTest('RO.UNK.001.MAJ.01')",null,JDDFileMapper.getFullnameFromCasDeTest('RO.UNK.001.MAJ.01'))

Log.addAssert("JDDFileMapper.getFullnameFromModObj('RO.ACT')",'TNR_JDD\\JDD.RO.ACT.xlsx',JDDFileMapper.getFullnameFromModObj('RO.ACT'))
Log.addAssert("JDDFileMapper.getFullnameFromModObj('ROACT')",null,JDDFileMapper.getFullnameFromModObj('ROACT'))
Log.addAssert("JDDFileMapper.getFullnameFromModObj('')",null,JDDFileMapper.getFullnameFromModObj(''))
Log.addAssert("JDDFileMapper.getFullnameFromModObj(null)",null,JDDFileMapper.getFullnameFromModObj(null))

Log.addAssert("JDDFileMapper.add('AA.BBB','un fullname')",null,JDDFileMapper.add('AA.BBB','un fullname'))
Log.addAssert("JDDFileMapper.getFullnameFromModObj('AA.BBB')",'un fullname',JDDFileMapper.getFullnameFromModObj('AA.BBB'))
