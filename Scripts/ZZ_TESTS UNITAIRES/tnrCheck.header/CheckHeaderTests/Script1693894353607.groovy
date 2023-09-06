


import tnrCheck.header.CheckHeader
import tnrLog.Log


/**
 * TESTS UNITAIRES
 * 
 * public static boolean run(String typeFile, List <String> headersList, String tableName)
 *
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_FOR_LOG = 'tnrCheck.header.CheckHeader'

Log.addAssert(CLASS_FOR_LOG,"Controle OK",true,CheckHeader.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],'INTER_HAB'))
Log.addAssert(CLASS_FOR_LOG,"Colonnes 'DT_DATDEB'  'et 'DT_DATFIN' pas à la bonne place",false,CheckHeader.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATFIN'  ,'DT_DATDEB'],'INTER_HAB'))
Log.addAssert(CLASS_FOR_LOG,"Controle 'ID_CODHAB' manquante dans le PREJDD",false,CheckHeader.run('PREJDD',['ID_CODINT','DT_DATDEB'  ,'DT_DATFIN'],'INTER_HAB'))
Log.addAssert(CLASS_FOR_LOG,"Controle 'ID_CODHAB' manquante dans le JDD",false,CheckHeader.run('JDD',['ID_CODINT','DT_DATDEB'  ,'DT_DATFIN'],'INTER_HAB'))

Log.addAssert(CLASS_FOR_LOG,"Colonne 'UNK' en plus",true,CheckHeader.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN','UNK'],'INTER_HAB'))
Log.addAssert(CLASS_FOR_LOG,"Colonne '' en plus",true,CheckHeader.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN',''],'INTER_HAB'))
Log.addAssert(CLASS_FOR_LOG,"Colonne null en plus",true,CheckHeader.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN',null],'INTER_HAB'))

Log.addAssert(CLASS_FOR_LOG,"Colonne 'UNK' à la place de 'DT_DATFIN'",false,CheckHeader.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'UNK'],'INTER_HAB'))
Log.addAssert(CLASS_FOR_LOG,"Colonne '' à la place de 'DT_DATFIN'",false,CheckHeader.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,''],'INTER_HAB'))
Log.addAssert(CLASS_FOR_LOG,"Colonne null à la place de 'DT_DATFIN'",false,CheckHeader.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,null],'INTER_HAB'))


Log.addAssert(CLASS_FOR_LOG,"Table 'UNK'",false,CheckHeader.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],'UNK'))
Log.addAssert(CLASS_FOR_LOG,"Table ''",false,CheckHeader.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],''))
Log.addAssert(CLASS_FOR_LOG,"Table null",false,CheckHeader.run('PREJDD',['ID_CODINT','ID_CODHAB' ,'DT_DATDEB'  ,'DT_DATFIN'],null))