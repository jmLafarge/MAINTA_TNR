

import tnrLog.Log
import tnrCheck.CheckColumn


/**
 * UNIT TESTS
 *
 * @author JM Lafarge
 * @version 1.0
 */


/**
 *
 * TEST public static boolean run(String typeFile, List <String> headersList, String table)
 *
 */

Log.addAssert("Controle OK",true,CheckColumn.run(CheckColumn.FileType.PREJDD,['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],'INTER_HAB'))
Log.addAssert("Colonnes 'DT_DATDEB'  'et 'DT_DATFIN' pas à la bonne place",false,CheckColumn.run(CheckColumn.FileType.PREJDD,['ID_CODINT','ID_CODHAB' ,'DT_DATFIN'  ,'DT_DATDEB'],'INTER_HAB'))
Log.addAssert("Controle 'ID_CODHAB' manquante dans le PREJDD",false,CheckColumn.run(CheckColumn.FileType.PREJDD,['ID_CODINT','DT_DATDEB'  ,'DT_DATFIN'],'INTER_HAB'))
Log.addAssert("Controle 'ID_CODHAB' manquante dans le JDD",false,CheckColumn.run(CheckColumn.FileType.JDD,['ID_CODINT','DT_DATDEB'  ,'DT_DATFIN'],'INTER_HAB'))

Log.addAssert("Colonne 'UNK' en plus",true,CheckColumn.run(CheckColumn.FileType.PREJDD,['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN','UNK'],'INTER_HAB'))
Log.addAssert("Colonne '' en plus",true,CheckColumn.run(CheckColumn.FileType.PREJDD,['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN',''],'INTER_HAB'))
Log.addAssert("Colonne null en plus",true,CheckColumn.run(CheckColumn.FileType.PREJDD,['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN',null],'INTER_HAB'))

Log.addAssert("Colonne 'UNK' à la place de 'DT_DATFIN'",false,CheckColumn.run(CheckColumn.FileType.PREJDD,['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'UNK'],'INTER_HAB'))
Log.addAssert("Colonne '' à la place de 'DT_DATFIN'",false,CheckColumn.run(CheckColumn.FileType.PREJDD,['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,''],'INTER_HAB'))
Log.addAssert("Colonne null à la place de 'DT_DATFIN'",false,CheckColumn.run(CheckColumn.FileType.PREJDD,['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,null],'INTER_HAB'))

Log.addAssert("Table 'UNK'",false,CheckColumn.run(CheckColumn.FileType.PREJDD,['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],'UNK'))
Log.addAssert("Table ''",false,CheckColumn.run(CheckColumn.FileType.PREJDD,['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],''))
Log.addAssert("Table null",false,CheckColumn.run(CheckColumn.FileType.PREJDD,['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],null))