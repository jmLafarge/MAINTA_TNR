import tnrSqlManager.InfoDB
import tnrLog.Log

/**
 * TESTS UNITAIRES
 * 
 * public static boolean isTableExist(String table)
 * public static boolean inTable(String table, String name)
 * public static List getPK(String table)
 * public static boolean isPK(String table, String name)
 * public static String getDATA_TYPE(String table, String name)
 * public static int getDATA_MAXCHAR(String table, String name)
 * public static int getORDINAL_POSITION(String table, String name)
 * public static boolean isNumeric(String table, String name)
 * public static boolean isImage(String table, String name)
 * public static boolean isVarchar(String table, String name)
 * public static boolean isDatetime(String table, String name)
 * public static boolean isBoolean(String table, String name)
 * public static castJDDVal(String table, String name, def val)
 * public static Map<String, Map <String , Object>> getDatasForTable(String table)
 *
 * @author JM Lafarge
 * @version 1.0
 */

final String CLASS_FOR_LOG = 'tnrSqlManager.InfoDB'

Log.addAssert(CLASS_FOR_LOG,"InfoDB.isTableExist('INTER')",true,InfoDB.isTableExist('INTER'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isTableExist('UNK')",false,InfoDB.isTableExist('UNK'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isTableExist('')",false,InfoDB.isTableExist(''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isTableExist(null)",false,InfoDB.isTableExist(null))


Log.addAssert(CLASS_FOR_LOG,"InfoDB.inTable('INTER','ID_CODINT')",true,InfoDB.inTable('INTER','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.inTable('INTER','UNK')",false,InfoDB.inTable('INTER','UNK'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.inTable('TABLEUNK','ID_CODINT')",false,InfoDB.inTable('TABLEUNK','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.inTable('INTER','')",false,InfoDB.inTable('INTER',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.inTable('','ID_CODINT')",false,InfoDB.inTable('','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.inTable('INTER',null)",false,InfoDB.inTable('INTER',null))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.inTable(null,'ID_CODINT')",false,InfoDB.inTable(null,'ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.inTable('','')",false,InfoDB.inTable('',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.inTable(null,null)",false,InfoDB.inTable(null,null))

Log.addAssert(CLASS_FOR_LOG,"InfoDB.getPK('INTER')",['ID_CODINT'],InfoDB.getPK('INTER'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getPK('INTER_HAB')",['ID_CODINT','ID_CODHAB'],InfoDB.getPK('INTER_HAB'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getPK('UNK')",[],InfoDB.getPK('UNK'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getPK('')",[],InfoDB.getPK(''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getPK(null)",[],InfoDB.getPK(null))


Log.addAssert(CLASS_FOR_LOG,"InfoDB.isPK('INTER','ID_CODINT')",true,InfoDB.isPK('INTER','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isPK('INTER','ST_NOM')",false,InfoDB.isPK('INTER','ST_NOM'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isPK('UNK','ID_CODINT')",false,InfoDB.isPK('UNK','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isPK('INTER','')",false,InfoDB.isPK('INTER',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isPK('INTER',null)",false,InfoDB.isPK('INTER',null))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isPK('','ID_CODINT')",false,InfoDB.isPK('','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isPK(null,'ID_CODINT')",false,InfoDB.isPK(null,'ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isPK('','')",false,InfoDB.isPK('',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isPK(null,null)",false,InfoDB.isPK(null,null))

Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_TYPE('INTER','ID_CODINT')",'varchar',InfoDB.getDATA_TYPE('INTER','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_TYPE('INTER','NU_COUHOR')",'numeric',InfoDB.getDATA_TYPE('INTER','NU_COUHOR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_TYPE('INTER','DT_CRE')",'datetime',InfoDB.getDATA_TYPE('INTER','DT_CRE'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_TYPE('BTDOC','OL_DOC')",'image',InfoDB.getDATA_TYPE('BTDOC','OL_DOC'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_TYPE('UNK','ID_CODINT')",null,InfoDB.getDATA_TYPE('UNK','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_TYPE('INTER','')",null,InfoDB.getDATA_TYPE('INTER',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_TYPE('INTER',null)",null,InfoDB.getDATA_TYPE('INTER',null))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_TYPE('','ID_CODINT')",null,InfoDB.getDATA_TYPE('','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_TYPE(null,'ID_CODINT')",null,InfoDB.getDATA_TYPE(null,'ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_TYPE('','')",null,InfoDB.getDATA_TYPE('',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_TYPE(null,null)",null,InfoDB.getDATA_TYPE(null,null))


Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_MAXCHAR('INTER','ID_CODINT')",50,InfoDB.getDATA_MAXCHAR('INTER','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_MAXCHAR('INTER','ST_NOM')",30,InfoDB.getDATA_MAXCHAR('INTER','ST_NOM'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_MAXCHAR('INTER','ID_EXT')",0,InfoDB.getDATA_MAXCHAR('INTER','ID_EXT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_MAXCHAR('UNK','ID_CODINT')",0,InfoDB.getDATA_MAXCHAR('UNK','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_MAXCHAR('INTER','')",0,InfoDB.getDATA_MAXCHAR('INTER',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_MAXCHAR('INTER',null)",0,InfoDB.getDATA_MAXCHAR('INTER',null))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_MAXCHAR('','ID_CODINT')",0,InfoDB.getDATA_MAXCHAR('','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_MAXCHAR(null,'ID_CODINT')",0,InfoDB.getDATA_MAXCHAR(null,'ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_MAXCHAR('','')",0,InfoDB.getDATA_MAXCHAR('',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDATA_MAXCHAR(null,null)",0,InfoDB.getDATA_MAXCHAR(null,null))


Log.addAssert(CLASS_FOR_LOG,"InfoDB.getORDINAL_POSITION('INTER','ID_CODINT')",1,InfoDB.getORDINAL_POSITION('INTER','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getORDINAL_POSITION('INTER','ST_NOM')",7,InfoDB.getORDINAL_POSITION('INTER','ST_NOM'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getORDINAL_POSITION('UNK','ID_CODINT')",0,InfoDB.getORDINAL_POSITION('UNK','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getORDINAL_POSITION('INTER','')",0,InfoDB.getORDINAL_POSITION('INTER',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getORDINAL_POSITION('INTER',null)",0,InfoDB.getORDINAL_POSITION('INTER',null))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getORDINAL_POSITION('','ID_CODINT')",0,InfoDB.getORDINAL_POSITION('','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getORDINAL_POSITION(null,'ID_CODINT')",0,InfoDB.getORDINAL_POSITION(null,'ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getORDINAL_POSITION('','')",0,InfoDB.getORDINAL_POSITION('',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getORDINAL_POSITION(null,null)",0,InfoDB.getORDINAL_POSITION(null,null))





Log.addAssert(CLASS_FOR_LOG,"InfoDB.isNumeric('INTER','NU_COUHOR')",true,InfoDB.isNumeric('INTER','NU_COUHOR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isNumeric('INTER','ST_NOM')",false,InfoDB.isNumeric('INTER','ST_NOM'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isNumeric('UNK','NU_COUHOR')",false,InfoDB.isNumeric('UNK','NU_COUHOR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isNumeric('INTER','')",false,InfoDB.isNumeric('INTER',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isNumeric('INTER',null)",false,InfoDB.isNumeric('INTER',null))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isNumeric('','NU_COUHOR')",false,InfoDB.isNumeric('','NU_COUHOR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isNumeric(null,'NU_COUHOR')",false,InfoDB.isNumeric(null,'NU_COUHOR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isNumeric('','')",false,InfoDB.isNumeric('',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isNumeric(null,null)",false,InfoDB.isNumeric(null,null))





Log.addAssert(CLASS_FOR_LOG,"InfoDB.isImage('EQUDOC','OL_DOC')",true,InfoDB.isImage('EQUDOC','OL_DOC'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isImage('INTER','NU_COUHOR')",false,InfoDB.isImage('INTER','NU_COUHOR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isImage('UNK','OL_DOC')",false,InfoDB.isImage('UNK','OL_DOC'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isImage('EQUDOC','')",false,InfoDB.isImage('EQUDOC',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isImage('EQUDOC',null)",false,InfoDB.isImage('EQUDOC',null))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isImage('','OL_DOC')",false,InfoDB.isImage('','OL_DOC'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isImage(null,'OL_DOC')",false,InfoDB.isImage(null,'OL_DOC'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isImage('','')",false,InfoDB.isImage('',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isImage(null,null)",false,InfoDB.isImage(null,null))






Log.addAssert(CLASS_FOR_LOG,"InfoDB.isVarchar('INTER','ID_CODINT')",true,InfoDB.isVarchar('INTER','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isVarchar('INTER','NU_COUHOR')",false,InfoDB.isVarchar('INTER','NU_COUHOR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isVarchar('UNK','ID_CODINT')",false,InfoDB.isVarchar('UNK','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isVarchar('INTER','')",false,InfoDB.isVarchar('INTER',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isVarchar('INTER',null)",false,InfoDB.isVarchar('INTER',null))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isVarchar('','ID_CODINT')",false,InfoDB.isVarchar('','ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isVarchar(null,'ID_CODINT')",false,InfoDB.isVarchar(null,'ID_CODINT'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isVarchar('','')",false,InfoDB.isVarchar('',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isVarchar(null,null)",false,InfoDB.isVarchar(null,null))



Log.addAssert(CLASS_FOR_LOG,"InfoDB.isDatetime('ADR','DT_TRACRE')",true,InfoDB.isDatetime('ADR','DT_TRACRE'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isDatetime('ADR','ST_ADR')",false,InfoDB.isDatetime('ADR','ST_ADR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isDatetime('UNK','DT_TRACRE')",false,InfoDB.isDatetime('UNK','DT_TRACRE'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isDatetime('ADR','')",false,InfoDB.isDatetime('ADR',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isDatetime('ADR',null)",false,InfoDB.isDatetime('ADR',null))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isDatetime('','DT_TRACRE')",false,InfoDB.isDatetime('','DT_TRACRE'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isDatetime(null,'DT_TRACRE')",false,InfoDB.isDatetime(null,'DT_TRACRE'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isDatetime('','')",false,InfoDB.isDatetime('',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isDatetime(null,null)",false,InfoDB.isDatetime(null,null))


Log.addAssert(CLASS_FOR_LOG,"InfoDB.isBoolean('ADR','ST_ADRPAR')",true,InfoDB.isBoolean('ADR','ST_ADRPAR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isBoolean('ADR','ST_ADR')",false,InfoDB.isBoolean('ADR','ST_ADR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isBoolean('UNK','ST_ADRPAR')",false,InfoDB.isBoolean('UNK','ST_ADRPAR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isBoolean('ADR','')",false,InfoDB.isBoolean('ADR',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isBoolean('ADR',null)",false,InfoDB.isBoolean('ADR',null))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isBoolean('','ST_ADRPAR')",false,InfoDB.isBoolean('','ST_ADRPAR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isBoolean(null,'ST_ADRPAR')",false,InfoDB.isBoolean(null,'ST_ADRPAR'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isBoolean('','')",false,InfoDB.isBoolean('',''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.isBoolean(null,null)",false,InfoDB.isBoolean(null,null))

Log.addAssert(CLASS_FOR_LOG,"InfoDB.castJDDVal('INTER','ST_NOM','Coucou')",'Coucou',InfoDB.castJDDVal('INTER','ST_NOM','Coucou'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.castJDDVal('INTER','ST_NOM',12)",'12',InfoDB.castJDDVal('INTER','ST_NOM',12))


Log.addAssert(CLASS_FOR_LOG,"InfoDB.castJDDVal('INTER','NU_COUHOR','Coucou')",'Coucou',InfoDB.castJDDVal('INTER','NU_COUHOR','Coucou'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.castJDDVal('INTER','NU_COUHOR',12)",12,InfoDB.castJDDVal('INTER','NU_COUHOR',12))


Map<String, Map <String , Object>> mapTest = [
	'ID_NUMDOC':['ORDINAL_POSITION':1, 'IS_NULLABLE':'NO', 'DATA_TYPE':'numeric', 'MAXCHAR':'NULL', 'DOMAIN_NAME':'T_NUMINT', 'CONSTRAINT_NAME':'PK_BTDOC'], 
	'OL_DOC':['ORDINAL_POSITION':2, 'IS_NULLABLE':'YES', 'DATA_TYPE':'image', 'MAXCHAR':2147483647, 'DOMAIN_NAME':'T_OLE', 'CONSTRAINT_NAME':'NULL'], 
	'ID_ARC':['ORDINAL_POSITION':3, 'IS_NULLABLE':'YES', 'DATA_TYPE':'numeric', 'MAXCHAR':'NULL', 'DOMAIN_NAME':'T_NUMARC', 'CONSTRAINT_NAME':'NULL']
	]

Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDatasForTable('INTER_HAB')",mapTest,InfoDB.getDatasForTable('BTDOC'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDatasForTable('UNK')",[:],InfoDB.getDatasForTable('UNK'))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDatasForTable('')",[:],InfoDB.getDatasForTable(''))
Log.addAssert(CLASS_FOR_LOG,"InfoDB.getDatasForTable(null)",[:],InfoDB.getDatasForTable(null))











