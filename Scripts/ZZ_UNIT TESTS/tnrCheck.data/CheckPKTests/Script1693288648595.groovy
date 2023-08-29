

import tnrCheck.data.CheckPK
import tnrLog.Log

/**
 * UNIT TESTS
 *
 * @author JM Lafarge
 * @version 1.0
 */


/**
 *
 * TEST 
 * 
 * CheckPK(String jddFullname, String shName,String tableName){
 * public boolean checkPKValues(String cdt, int numLine, String name, def val) {
 * public boolean checkDuplicates( String cdt, int numLine) {
 *
 */

CheckPK checkPK1 = new CheckPK('Le JDDFullname', 'Le sheetName','ACT')

Log.addAssert("addPKVal ID1",true,checkPK1.checkPKValues('Cas de test 1', 1,'ID_NUMACT','ID1'))
Log.addAssert("addPKVal type1",true,checkPK1.checkPKValues('Cas de test 1', 1,'ST_TYP','type1'))
Log.addAssert("addPKVal Lib1",true,checkPK1.checkPKValues('Cas de test 1', 1,'ST_LIB','Lib1'))
Log.addAssert("Controle OK",true,checkPK1.checkDuplicates('Cas de Test 1', 1))

Log.addAssert("addPKVal ID2",true,checkPK1.checkPKValues('Cas de test 2', 2,'ID_NUMACT','ID2'))
Log.addAssert("addPKVal type2",true,checkPK1.checkPKValues('Cas de test 2', 2,'ST_TYP','type2'))
Log.addAssert("addPKVal Lib2",true,checkPK1.checkPKValues('Cas de test 2', 2,'ST_LIB','Lib2'))
Log.addAssert("Controle OK",true,checkPK1.checkDuplicates('Cas de Test 2', 2))

Log.addAssert("addPKVal ID2",true,checkPK1.checkPKValues('Cas de test 3', 3,'ID_NUMACT','ID2'))
Log.addAssert("addPKVal type3",true,checkPK1.checkPKValues('Cas de test 3', 3,'ST_TYP','type3'))
Log.addAssert("addPKVal Lib3",true,checkPK1.checkPKValues('Cas de test 3', 3,'ST_LIB','Lib3'))
Log.addAssert("Controle doublon PK",false,checkPK1.checkDuplicates('Cas de Test 3', 3))

Log.addAssert("addPKVal ID2",true,checkPK1.checkPKValues('Cas de test 4', 5,'ID_NUMACT','ID2'))
Log.addAssert("Controle 2e doublon PK",false,checkPK1.checkDuplicates('Cas de Test 4', 4))

Log.addAssert("addPKVal ID4",true,checkPK1.checkPKValues('Cas de test 1', 1,'ID_NUMACT','ID4'))
Log.addAssert("Controle 2e ligne de Cas de Test 4",true,checkPK1.checkDuplicates('Cas de Test 4', 5))

Log.addAssert("addPKVal ID1",true,checkPK1.checkPKValues('Cas de test 4', 6,'ID_NUMACT','ID1'))
Log.addAssert("Controle autre doublon PK",false,checkPK1.checkDuplicates('Cas de Test 4', 6))


Log.addAssert("addPKVal ID5",true,checkPK1.checkPKValues('Cas de test 5', 7,'ID_NUMACT','ID5'))
Log.addAssert("addPKVal type3",true,checkPK1.checkPKValues('Cas de test 5', 7,'ST_TYP','type3'))
Log.addAssert("Controle doublon non PK",true,checkPK1.checkDuplicates('Cas de Test 5', 7))


Log.addAssert('addPKVal $SEQUENCEID',true,checkPK1.checkPKValues('Cas de test 6', 8,'ID_NUMACT','$SEQUENCEID'))
Log.addAssert('Controle doublon$SEQUENCEID',true,checkPK1.checkDuplicates('Cas de Test 6', 8))

Log.addAssert('addPKVal $SEQUENCEID',true,checkPK1.checkPKValues('Cas de test 7', 9,'ID_NUMACT','$SEQUENCEID'))
Log.addAssert('Controle doublon $SEQUENCEID',true,checkPK1.checkDuplicates('Cas de Test 7', 9))


Log.addAssert('addPKVal $ORDRE',true,checkPK1.checkPKValues('Cas de test 8', 10,'ID_NUMACT','$ORDRE'))
Log.addAssert('Controle doublon$ORDRE',true,checkPK1.checkDuplicates('Cas de Test 8', 10))

Log.addAssert('addPKVal $ORDRE',true,checkPK1.checkPKValues('Cas de test 9', 11,'ID_NUMACT','$ORDRE'))
Log.addAssert('Controle doublon $ORDRE',true,checkPK1.checkDuplicates('Cas de Test 9', 11))


Log.addAssert("addPKVal vide",false,checkPK1.checkPKValues('Cas de test 10', 12,'ID_NUMACT',''))
Log.addAssert("Controle vide",true,checkPK1.checkDuplicates('Cas de Test 10', 12))

Log.addAssert("addPKVal vide",false,checkPK1.checkPKValues('Cas de test 11', 13,'ID_NUMACT',''))
Log.addAssert("Controle doublon vide",true,checkPK1.checkDuplicates('Cas de Test 11', 13))



Log.addAssert("addPKVal null",false,checkPK1.checkPKValues('Cas de test 13', 15,'ID_NUMACT',null))
Log.addAssert("Controle null '",true,checkPK1.checkDuplicates('Cas de Test 13', 15))

Log.addAssert("addPKVal null",false,checkPK1.checkPKValues('Cas de test 14', 16,'ID_NUMACT',null))
Log.addAssert("Controle doublon null '",true,checkPK1.checkDuplicates('Cas de Test 14', 16))


CheckPK checkPK2 = new CheckPK('Le JDDFullname', 'Le sheetName','INTER_HAB')

Log.addAssert("addPKVal codint1",true,checkPK2.checkPKValues('Cas de test 1', 1,'ID_CODINT','codint1'))
Log.addAssert("addPKVal codhab1",true,checkPK2.checkPKValues('Cas de test 1', 1,'ID_CODHAB','codhab1'))
Log.addAssert("Controle OK '",true,checkPK2.checkDuplicates('Cas de Test 1', 1))

Log.addAssert("addPKVal codint2",true,checkPK2.checkPKValues('Cas de test 2', 2,'ID_CODINT','codint2'))
Log.addAssert("addPKVal codhab2",true,checkPK2.checkPKValues('Cas de test 2', 2,'ID_CODHAB','codhab2'))
Log.addAssert("Controle OK '",true,checkPK2.checkDuplicates('Cas de Test 2', 2))

Log.addAssert("addPKVal codint1",true,checkPK2.checkPKValues('Cas de test 3', 3,'ID_CODINT','codint1'))
Log.addAssert("addPKVal codhab2",true,checkPK2.checkPKValues('Cas de test 3', 3,'ID_CODHAB','codhab2'))
Log.addAssert("Controle OK '",true,checkPK2.checkDuplicates('Cas de Test 3', 3))

Log.addAssert("addPKVal codint2",true,checkPK2.checkPKValues('Cas de test 4', 4,'ID_CODINT','codint2'))
Log.addAssert("addPKVal codhab1",true,checkPK2.checkPKValues('Cas de test 4', 4,'ID_CODHAB','codhab1'))
Log.addAssert("Controle OK '",true,checkPK2.checkDuplicates('Cas de Test 4', 4))

Log.addAssert("addPKVal codint1",true,checkPK2.checkPKValues('Cas de test 5', 5,'ID_CODINT','codint1'))
Log.addAssert("addPKVal codhab1",true,checkPK2.checkPKValues('Cas de test 5', 5,'ID_CODHAB','codhab1'))
Log.addAssert("Controle OK '",false,checkPK2.checkDuplicates('Cas de Test 5', 5))


Log.addAssert("addPKVal codint1",true,checkPK2.checkPKValues('Cas de test 6', 6,'ID_CODINT','codint1'))
Log.addAssert("addPKVal \$SEQUENCEID",true,checkPK2.checkPKValues('Cas de test 6', 6,'ID_CODHAB','$SEQUENCE'))
Log.addAssert("Controle SEQUENCE '",true,checkPK2.checkDuplicates('Cas de Test 6', 6))


Log.addAssert("addPKVal codint1",true,checkPK2.checkPKValues('Cas de test 7', 7,'ID_CODINT','codint1'))
Log.addAssert("addPKVal \$ORDRE",true,checkPK2.checkPKValues('Cas de test 7', 7,'ID_CODHAB','$ORDRE'))
Log.addAssert("Controle ORDRE '",true,checkPK2.checkDuplicates('Cas de Test 7', 7))

Log.addAssert("addPKVal codint1",true,checkPK2.checkPKValues('Cas de test 8', 8,'ID_CODINT','codint1'))
Log.addAssert("addPKVal vide",false,checkPK2.checkPKValues('Cas de test 8', 8,'ID_CODHAB',''))
Log.addAssert("Controle vide '",true,checkPK2.checkDuplicates('Cas de Test 8', 8))

Log.addAssert("addPKVal codint1",true,checkPK2.checkPKValues('Cas de test 9', 9,'ID_CODINT','codint1'))
Log.addAssert("addPKVal null",false,checkPK2.checkPKValues('Cas de test 9', 9,'ID_CODHAB',null))
Log.addAssert("Controle null '",true,checkPK2.checkDuplicates('Cas de Test 9', 9))
