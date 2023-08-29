
import java.lang.reflect.Method

import internal.GlobalVariable
import tnrCheck.data.CheckType
import tnrJDDManager.JDD
import tnrLog.Log

/**
 * UNIT TESTS
 *
 *
 * @author JM Lafarge
 * @version 1.0
 */

/**
 *
 * TEST 
 * 
 * 	CheckType ( JDD myJDD, String tableName, String JDDFilename, String sheetName) {
 * 	public boolean checkValue(String cdt, String name, def value) {
 * 	private boolean isCasDeTestSUPorREC(String cdt)
 *
 */

GlobalVariable.CAS_DE_TEST_PATTERN = 'RT.MAT.001'

JDD myJDD = new JDD()

CheckType checkType = new CheckType(myJDD,  'MAT',myJDD.getJDDFullName() , myJDD.getTCSheetName())

Log.addAssert("varchar 'MAT01' OK",true,checkType.checkValue('Un Cas de Test', 'ID_NUMMAT', 'MAT01'))
Log.addAssert("varchar (1) 'P' OK",true,checkType.checkValue('Un Cas de Test', 'ST_POS','P'))
Log.addAssert("varchar (1) 'TITI' OK",false,checkType.checkValue('Un Cas de Test', 'ST_POS','TITI'))
Log.addAssert("numéric 100 OK",true,checkType.checkValue('Un Cas de Test', 'ID_NUMEXT', 100))
Log.addAssert('numéric $NU OK',true,checkType.checkValue('Un Cas de Test', 'ID_NUMEXT', '$NU'))
Log.addAssert('numéric $NULL OK',true,checkType.checkValue('Un Cas de Test', 'ID_NUMEXT', '$NULL'))
Log.addAssert("numéric 'TOTO' KO",false,checkType.checkValue('Un Cas de Test', 'ID_NUMEXT', 'TOTO'))
Log.addAssert("numéric vide KO",false,checkType.checkValue('Un Cas de Test', 'ID_NUMEXT', ''))
Log.addAssert("numéric null KO",false,checkType.checkValue('Un Cas de Test', 'ID_NUMEXT', null))
Log.addAssert('colonne UNK',true,checkType.checkValue('Un Cas de Test', 'UNK', 100))
Log.addAssert('varchar vide',true,checkType.checkValue('Un Cas de Test', 'ST_DES', ''))
Log.addAssert('varchar null',true,checkType.checkValue('Un Cas de Test', 'ST_DES', null))

Log.addAssert('colonne vide',true,checkType.checkValue('Un Cas de Test', '', 'TITI'))
Log.addAssert('colonne null',true,checkType.checkValue('Un Cas de Test', null, 'TITI'))

Log.addAssert('$TBD*TOTO',true,checkType.checkValue('Un Cas de Test', 'ST_DES', '$TBD*TOTO'))
Log.addAssert('$TBD*TOTO',false,checkType.checkValue('Un Cas de Test', 'ID_NUMEXT', '$TBD*TOTO'))

Log.addAssert('colonne FK',true,checkType.checkValue('Un Cas de Test', 'ID_NUMEQU', 'TITI'))

Log.addAssert('OBSOLETE',true,checkType.checkValue('Un Cas de Test', 'ID_CODSIT', 100))


Method method = checkType.class.getDeclaredMethod("isCasDeTestSUPorREC", String.class);
method.setAccessible(true);

Log.addAssert("private isCasDeTestSUPorREC : controle true avec *.SUP.nn",true, method.invoke(checkType, 'AA.BBB.001.SUP.01'))
Log.addAssert("private isCasDeTestSUPorREC : controle true avec *.REC.nn",true, method.invoke(checkType, 'AA.BBB.001.REC.01'))
Log.addAssert("private isCasDeTestSUPorREC : controle false avec *.LEC.nn",false, method.invoke(checkType, 'AA.BBB.001.LEC.01'))

Log.addAssert("private isCasDeTestSUPorREC : controle false avec *.RECnn",false, method.invoke(checkType, 'AA.BBB.001.REC01'))
Log.addAssert("private isCasDeTestSUPorREC : controle false avec *RECnn",false, method.invoke(checkType, 'AA.BBB.001REC.01'))

Log.addAssert("private isCasDeTestSUPorREC : controle false avec .REC.nn",false, method.invoke(checkType, '.REC.01'))
Log.addAssert("private isCasDeTestSUPorREC : controle false avec *.REC.",false, method.invoke(checkType, 'AA.BBB.001.REC.'))
Log.addAssert("private isCasDeTestSUPorREC : controle false avec *.REC.AA",false, method.invoke(checkType, 'AA.BBB.001.REC.AA'))