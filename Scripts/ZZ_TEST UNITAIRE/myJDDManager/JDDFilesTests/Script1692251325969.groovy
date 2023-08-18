import myJDDManager.JDDFiles
import my.Log

/**
 * Unit tests
 *
 *
 * @author JM Lafarge
 * @since 1.0
 */

Log.addAssert("JDDFiles.getFullnameFromCasDeTest('RO.ACT.001.MAJ.01')",'TNR_JDD\\JDD.RO.ACT.xlsx',JDDFiles.getFullnameFromCasDeTest('RO.ACT.001.MAJ.01'))
Log.addAssert("JDDFiles.getFullnameFromCasDeTest('RO.ACT.003MET.SRL.01')",'TNR_JDD\\JDD.RO.ACT.xlsx',JDDFiles.getFullnameFromCasDeTest('RO.ACT.003MET.SRL.01'))
Log.addAssert("JDDFiles.getFullnameFromCasDeTest('RO.ACT.001.UNK.01')",'TNR_JDD\\JDD.RO.ACT.xlsx',JDDFiles.getFullnameFromCasDeTest('RO.ACT.001.UNK.01'))

Log.addAssert("JDDFiles.getFullnameFromCasDeTest('RO.UNK.001.MAJ.01')",null,JDDFiles.getFullnameFromCasDeTest('RO.UNK.001.MAJ.01'))

Log.addAssert("JDDFiles.getFullname('RO.ACT')",'TNR_JDD\\JDD.RO.ACT.xlsx',JDDFiles.getFullname('RO.ACT'))
Log.addAssert("JDDFiles.getFullname('ROACT')",null,JDDFiles.getFullname('ROACT'))
Log.addAssert("JDDFiles.getFullname('')",null,JDDFiles.getFullname(''))
Log.addAssert("JDDFiles.getFullname(null)",null,JDDFiles.getFullname(null))

Log.addAssert("JDDFiles.add('AA.BBB','un fullname')",null,JDDFiles.add('AA.BBB','un fullname'))
Log.addAssert("JDDFiles.getFullname('AA.BBB')",'un fullname',JDDFiles.getFullname('AA.BBB'))
