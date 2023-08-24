

import my.Log
import myCheck.CheckColumn


/**
 * UNIT TESTS
 *
 * @author JM Lafarge
 * @since 1.0
 */


/**
 *
 * TEST public static boolean run(String typeFile, List <String> headersList, String table, boolean status)
 *
 */

Log.addAssert("Controle OK",true,CheckColumn.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],'INTER_HAB', true))
Log.addAssert("Colonnes 'DT_DATDEB'  'et 'DT_DATFIN' pas à la bonne place",false,CheckColumn.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATFIN'  ,'DT_DATDEB'],'INTER_HAB', true))
Log.addAssert("Controle 'ID_CODHAB' manquante dans le PREJDD",false,CheckColumn.run('PREJDD',['ID_CODINT','DT_DATDEB'  ,'DT_DATFIN'],'INTER_HAB', true))
Log.addAssert("Controle 'ID_CODHAB' manquante dans le JDD",false,CheckColumn.run('JDD',['ID_CODINT','DT_DATDEB'  ,'DT_DATFIN'],'INTER_HAB', true))

Log.addAssert("Colonne 'UNK' en plus",true,CheckColumn.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN','UNK'],'INTER_HAB', true))
Log.addAssert("Colonne '' en plus",true,CheckColumn.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN',''],'INTER_HAB', true))
Log.addAssert("Colonne null en plus",true,CheckColumn.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN',null],'INTER_HAB', true))

Log.addAssert("Colonne 'UNK' à la place de 'DT_DATFIN'",false,CheckColumn.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'UNK'],'INTER_HAB', true))
Log.addAssert("Colonne '' à la place de 'DT_DATFIN'",false,CheckColumn.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,''],'INTER_HAB', true))
Log.addAssert("Colonne null à la place de 'DT_DATFIN'",false,CheckColumn.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,null],'INTER_HAB', true))

Log.addAssert("Controle OK mais status déjà à false",false,CheckColumn.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],'INTER_HAB', false))


Log.addAssert("Table 'UNK'",false,CheckColumn.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],'UNK', true))
Log.addAssert("Table ''",false,CheckColumn.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],'', true))
Log.addAssert("Table null",false,CheckColumn.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],null, true))