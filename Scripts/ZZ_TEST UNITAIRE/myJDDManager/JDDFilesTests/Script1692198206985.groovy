import myJDDManager.JDDFiles
import my.Log

Log.addAssert('JDDFiles.getJDDFullNameFromCasDeTest()','TNR_JDD\\JDD.RO.ACT.xlsx',JDDFiles.getJDDFullNameFromCasDeTest('RO.ACT.001.MAJ.01'))
Log.addAssert('JDDFiles.getJDDFullNameFromCasDeTest()','TNR_JDD\\JDD.RO.ACT.xlsx',JDDFiles.getJDDFullNameFromCasDeTest('RO.ACT.003MET.SRL.01'))
Log.addAssert('JDDFiles.getJDDFullNameFromCasDeTest()','TNR_JDD\\JDD.RO.ACT.xlsx',JDDFiles.getJDDFullNameFromCasDeTest('RO.ACT.001.UNK.01'))

Log.addAssert('JDDFiles.getJDDFullNameFromCasDeTest()',null,JDDFiles.getJDDFullNameFromCasDeTest('RO.UNK.001.MAJ.01'))

Log.addAssert('JDDFiles.getJDDFullName()','TNR_JDD\\JDD.RO.ACT.xlsx',JDDFiles.getJDDFullName('RO.ACT'))
Log.addAssert('JDDFiles.getJDDFullName()',null,JDDFiles.getJDDFullName('ROACT'))
