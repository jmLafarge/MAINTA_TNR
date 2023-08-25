import tnrCommon.InfoDB
import tnrLog.Log

/**
 * UNIT TESTS
 *
 * @author JM Lafarge
 * @version 1.0
 */



Log.addAssert("InfoDB.isTableExist('INTER')",true,InfoDB.isTableExist('INTER'))
Log.addAssert("InfoDB.isTableExist('UNK')",false,InfoDB.isTableExist('UNK'))
Log.addAssert("InfoDB.isTableExist('')",false,InfoDB.isTableExist(''))
Log.addAssert("InfoDB.isTableExist(null)",false,InfoDB.isTableExist(null))


Log.addAssert("InfoDB.inTable('INTER','ID_CODINT')",true,InfoDB.inTable('INTER','ID_CODINT'))
Log.addAssert("InfoDB.inTable('INTER','UNK')",false,InfoDB.inTable('INTER','UNK'))
Log.addAssert("InfoDB.inTable('TABLEUNK','ID_CODINT')",false,InfoDB.inTable('TABLEUNK','ID_CODINT'))
Log.addAssert("InfoDB.inTable('INTER','')",false,InfoDB.inTable('INTER',''))
Log.addAssert("InfoDB.inTable('','ID_CODINT')",false,InfoDB.inTable('','ID_CODINT'))
Log.addAssert("InfoDB.inTable('INTER',null)",false,InfoDB.inTable('INTER',null))
Log.addAssert("InfoDB.inTable(null,'ID_CODINT')",false,InfoDB.inTable(null,'ID_CODINT'))
Log.addAssert("InfoDB.inTable('','')",false,InfoDB.inTable('',''))
Log.addAssert("InfoDB.inTable(null,null)",false,InfoDB.inTable(null,null))

Log.addAssert("InfoDB.getPK('INTER')",['ID_CODINT'],InfoDB.getPK('INTER'))
Log.addAssert("InfoDB.getPK('INTER_HAB')",['ID_CODINT','ID_CODHAB'],InfoDB.getPK('INTER_HAB'))
Log.addAssert("InfoDB.getPK('UNK')",[],InfoDB.getPK('UNK'))
Log.addAssert("InfoDB.getPK('')",[],InfoDB.getPK(''))
Log.addAssert("InfoDB.getPK(null)",[],InfoDB.getPK(null))


Log.addAssert("InfoDB.isPK('INTER','ID_CODINT')",true,InfoDB.isPK('INTER','ID_CODINT'))
Log.addAssert("InfoDB.isPK('INTER','ST_NOM')",false,InfoDB.isPK('INTER','ST_NOM'))
Log.addAssert("InfoDB.isPK('UNK','ID_CODINT')",false,InfoDB.isPK('UNK','ID_CODINT'))
Log.addAssert("InfoDB.isPK('INTER','')",false,InfoDB.isPK('INTER',''))
Log.addAssert("InfoDB.isPK('INTER',null)",false,InfoDB.isPK('INTER',null))
Log.addAssert("InfoDB.isPK('','ID_CODINT')",false,InfoDB.isPK('','ID_CODINT'))
Log.addAssert("InfoDB.isPK(null,'ID_CODINT')",false,InfoDB.isPK(null,'ID_CODINT'))
Log.addAssert("InfoDB.isPK('','')",false,InfoDB.isPK('',''))
Log.addAssert("InfoDB.isPK(null,null)",false,InfoDB.isPK(null,null))

Log.addAssert("InfoDB.getDATA_TYPE('INTER','ID_CODINT')",'varchar',InfoDB.getDATA_TYPE('INTER','ID_CODINT'))
Log.addAssert("InfoDB.getDATA_TYPE('INTER','NU_COUHOR')",'numeric',InfoDB.getDATA_TYPE('INTER','NU_COUHOR'))
Log.addAssert("InfoDB.getDATA_TYPE('INTER','DT_CRE')",'datetime',InfoDB.getDATA_TYPE('INTER','DT_CRE'))
Log.addAssert("InfoDB.getDATA_TYPE('BTDOC','OL_DOC')",'image',InfoDB.getDATA_TYPE('BTDOC','OL_DOC'))
Log.addAssert("InfoDB.getDATA_TYPE('UNK','ID_CODINT')",null,InfoDB.getDATA_TYPE('UNK','ID_CODINT'))
Log.addAssert("InfoDB.getDATA_TYPE('INTER','')",null,InfoDB.getDATA_TYPE('INTER',''))
Log.addAssert("InfoDB.getDATA_TYPE('INTER',null)",null,InfoDB.getDATA_TYPE('INTER',null))
Log.addAssert("InfoDB.getDATA_TYPE('','ID_CODINT')",null,InfoDB.getDATA_TYPE('','ID_CODINT'))
Log.addAssert("InfoDB.getDATA_TYPE(null,'ID_CODINT')",null,InfoDB.getDATA_TYPE(null,'ID_CODINT'))
Log.addAssert("InfoDB.getDATA_TYPE('','')",null,InfoDB.getDATA_TYPE('',''))
Log.addAssert("InfoDB.getDATA_TYPE(null,null)",null,InfoDB.getDATA_TYPE(null,null))


Log.addAssert("InfoDB.getDATA_MAXCHAR('INTER','ID_CODINT')",50,InfoDB.getDATA_MAXCHAR('INTER','ID_CODINT'))
Log.addAssert("InfoDB.getDATA_MAXCHAR('INTER','ST_NOM')",30,InfoDB.getDATA_MAXCHAR('INTER','ST_NOM'))
Log.addAssert("InfoDB.getDATA_MAXCHAR('INTER','ID_EXT')",0,InfoDB.getDATA_MAXCHAR('INTER','ID_EXT'))
Log.addAssert("InfoDB.getDATA_MAXCHAR('UNK','ID_CODINT')",0,InfoDB.getDATA_MAXCHAR('UNK','ID_CODINT'))
Log.addAssert("InfoDB.getDATA_MAXCHAR('INTER','')",0,InfoDB.getDATA_MAXCHAR('INTER',''))
Log.addAssert("InfoDB.getDATA_MAXCHAR('INTER',null)",0,InfoDB.getDATA_MAXCHAR('INTER',null))
Log.addAssert("InfoDB.getDATA_MAXCHAR('','ID_CODINT')",0,InfoDB.getDATA_MAXCHAR('','ID_CODINT'))
Log.addAssert("InfoDB.getDATA_MAXCHAR(null,'ID_CODINT')",0,InfoDB.getDATA_MAXCHAR(null,'ID_CODINT'))
Log.addAssert("InfoDB.getDATA_MAXCHAR('','')",0,InfoDB.getDATA_MAXCHAR('',''))
Log.addAssert("InfoDB.getDATA_MAXCHAR(null,null)",0,InfoDB.getDATA_MAXCHAR(null,null))


Log.addAssert("InfoDB.getORDINAL_POSITION('INTER','ID_CODINT')",1,InfoDB.getORDINAL_POSITION('INTER','ID_CODINT'))
Log.addAssert("InfoDB.getORDINAL_POSITION('INTER','ST_NOM')",7,InfoDB.getORDINAL_POSITION('INTER','ST_NOM'))
Log.addAssert("InfoDB.getORDINAL_POSITION('UNK','ID_CODINT')",0,InfoDB.getORDINAL_POSITION('UNK','ID_CODINT'))
Log.addAssert("InfoDB.getORDINAL_POSITION('INTER','')",0,InfoDB.getORDINAL_POSITION('INTER',''))
Log.addAssert("InfoDB.getORDINAL_POSITION('INTER',null)",0,InfoDB.getORDINAL_POSITION('INTER',null))
Log.addAssert("InfoDB.getORDINAL_POSITION('','ID_CODINT')",0,InfoDB.getORDINAL_POSITION('','ID_CODINT'))
Log.addAssert("InfoDB.getORDINAL_POSITION(null,'ID_CODINT')",0,InfoDB.getORDINAL_POSITION(null,'ID_CODINT'))
Log.addAssert("InfoDB.getORDINAL_POSITION('','')",0,InfoDB.getORDINAL_POSITION('',''))
Log.addAssert("InfoDB.getORDINAL_POSITION(null,null)",0,InfoDB.getORDINAL_POSITION(null,null))



Log.addAssert("InfoDB.isVarchar('INTER','ID_CODINT')",true,InfoDB.isVarchar('INTER','ID_CODINT'))
Log.addAssert("InfoDB.isVarchar('INTER','NU_COUHOR')",false,InfoDB.isVarchar('INTER','NU_COUHOR'))
Log.addAssert("InfoDB.isVarchar('UNK','ID_CODINT')",false,InfoDB.isVarchar('UNK','ID_CODINT'))
Log.addAssert("InfoDB.isVarchar('INTER','')",false,InfoDB.isVarchar('INTER',''))
Log.addAssert("InfoDB.isVarchar('INTER',null)",false,InfoDB.isVarchar('INTER',null))
Log.addAssert("InfoDB.isVarchar('','ID_CODINT')",false,InfoDB.isVarchar('','ID_CODINT'))
Log.addAssert("InfoDB.isVarchar(null,'ID_CODINT')",false,InfoDB.isVarchar(null,'ID_CODINT'))
Log.addAssert("InfoDB.isVarchar('','')",false,InfoDB.isVarchar('',''))
Log.addAssert("InfoDB.isVarchar(null,null)",false,InfoDB.isVarchar(null,null))



Log.addAssert("InfoDB.isNumeric('INTER','NU_COUHOR')",true,InfoDB.isNumeric('INTER','NU_COUHOR'))
Log.addAssert("InfoDB.isNumeric('INTER','ST_NOM')",false,InfoDB.isNumeric('INTER','ST_NOM'))
Log.addAssert("InfoDB.isNumeric('UNK','NU_COUHOR')",false,InfoDB.isNumeric('UNK','NU_COUHOR'))
Log.addAssert("InfoDB.isNumeric('INTER','')",false,InfoDB.isNumeric('INTER',''))
Log.addAssert("InfoDB.isNumeric('INTER',null)",false,InfoDB.isNumeric('INTER',null))
Log.addAssert("InfoDB.isNumeric('','NU_COUHOR')",false,InfoDB.isNumeric('','NU_COUHOR'))
Log.addAssert("InfoDB.isNumeric(null,'NU_COUHOR')",false,InfoDB.isNumeric(null,'NU_COUHOR'))
Log.addAssert("InfoDB.isNumeric('','')",false,InfoDB.isNumeric('',''))
Log.addAssert("InfoDB.isNumeric(null,null)",false,InfoDB.isNumeric(null,null))


Log.addAssert("InfoDB.isImage('EQUDOC','OL_DOC')",true,InfoDB.isImage('EQUDOC','OL_DOC'))
Log.addAssert("InfoDB.isImage('INTER','NU_COUHOR')",false,InfoDB.isImage('INTER','NU_COUHOR'))
Log.addAssert("InfoDB.isImage('UNK','OL_DOC')",false,InfoDB.isImage('UNK','OL_DOC'))
Log.addAssert("InfoDB.isImage('EQUDOC','')",false,InfoDB.isImage('EQUDOC',''))
Log.addAssert("InfoDB.isImage('EQUDOC',null)",false,InfoDB.isImage('EQUDOC',null))
Log.addAssert("InfoDB.isImage('','OL_DOC')",false,InfoDB.isImage('','OL_DOC'))
Log.addAssert("InfoDB.isImage(null,'OL_DOC')",false,InfoDB.isImage(null,'OL_DOC'))
Log.addAssert("InfoDB.isImage('','')",false,InfoDB.isImage('',''))
Log.addAssert("InfoDB.isImage(null,null)",false,InfoDB.isImage(null,null))



Log.addAssert("InfoDB.isDatetime('ADR','DT_TRACRE')",true,InfoDB.isDatetime('ADR','DT_TRACRE'))
Log.addAssert("InfoDB.isDatetime('ADR','ST_ADR')",false,InfoDB.isDatetime('ADR','ST_ADR'))
Log.addAssert("InfoDB.isDatetime('UNK','DT_TRACRE')",false,InfoDB.isDatetime('UNK','DT_TRACRE'))
Log.addAssert("InfoDB.isDatetime('ADR','')",false,InfoDB.isDatetime('ADR',''))
Log.addAssert("InfoDB.isDatetime('ADR',null)",false,InfoDB.isDatetime('ADR',null))
Log.addAssert("InfoDB.isDatetime('','DT_TRACRE')",false,InfoDB.isDatetime('','DT_TRACRE'))
Log.addAssert("InfoDB.isDatetime(null,'DT_TRACRE')",false,InfoDB.isDatetime(null,'DT_TRACRE'))
Log.addAssert("InfoDB.isDatetime('','')",false,InfoDB.isDatetime('',''))
Log.addAssert("InfoDB.isDatetime(null,null)",false,InfoDB.isDatetime(null,null))


Log.addAssert("InfoDB.isBoolean('ADR','ST_ADRPAR')",true,InfoDB.isBoolean('ADR','ST_ADRPAR'))
Log.addAssert("InfoDB.isBoolean('ADR','ST_ADR')",false,InfoDB.isBoolean('ADR','ST_ADR'))
Log.addAssert("InfoDB.isBoolean('UNK','ST_ADRPAR')",false,InfoDB.isBoolean('UNK','ST_ADRPAR'))
Log.addAssert("InfoDB.isBoolean('ADR','')",false,InfoDB.isBoolean('ADR',''))
Log.addAssert("InfoDB.isBoolean('ADR',null)",false,InfoDB.isBoolean('ADR',null))
Log.addAssert("InfoDB.isBoolean('','ST_ADRPAR')",false,InfoDB.isBoolean('','ST_ADRPAR'))
Log.addAssert("InfoDB.isBoolean(null,'ST_ADRPAR')",false,InfoDB.isBoolean(null,'ST_ADRPAR'))
Log.addAssert("InfoDB.isBoolean('','')",false,InfoDB.isBoolean('',''))
Log.addAssert("InfoDB.isBoolean(null,null)",false,InfoDB.isBoolean(null,null))

Log.addAssert("InfoDB.castJDDVal('INTER','ST_NOM','Coucou')",'Coucou',InfoDB.castJDDVal('INTER','ST_NOM','Coucou'))
Log.addAssert("InfoDB.castJDDVal('INTER','ST_NOM',12)",'12',InfoDB.castJDDVal('INTER','ST_NOM',12))


Log.addAssert("InfoDB.castJDDVal('INTER','NU_COUHOR','Coucou')",'Coucou',InfoDB.castJDDVal('INTER','NU_COUHOR','Coucou'))
Log.addAssert("InfoDB.castJDDVal('INTER','NU_COUHOR',12)",12,InfoDB.castJDDVal('INTER','NU_COUHOR',12))


Map<String, Map <String , Object>> mapTest = [
	'ID_NUMDOC':['ORDINAL_POSITION':1, 'IS_NULLABLE':'NO', 'DATA_TYPE':'numeric', 'MAXCHAR':'NULL', 'DOMAIN_NAME':'T_NUMINT', 'CONSTRAINT_NAME':'PK_BTDOC'], 
	'OL_DOC':['ORDINAL_POSITION':2, 'IS_NULLABLE':'YES', 'DATA_TYPE':'image', 'MAXCHAR':2147483647, 'DOMAIN_NAME':'T_OLE', 'CONSTRAINT_NAME':'NULL'], 
	'ID_ARC':['ORDINAL_POSITION':3, 'IS_NULLABLE':'YES', 'DATA_TYPE':'numeric', 'MAXCHAR':'NULL', 'DOMAIN_NAME':'T_NUMARC', 'CONSTRAINT_NAME':'NULL']
	]

Log.addAssert("InfoDB.getDatasForTable('INTER_HAB')",mapTest,InfoDB.getDatasForTable('BTDOC'))
Log.addAssert("InfoDB.getDatasForTable('UNK')",[:],InfoDB.getDatasForTable('UNK'))
Log.addAssert("InfoDB.getDatasForTable('')",[:],InfoDB.getDatasForTable(''))
Log.addAssert("InfoDB.getDatasForTable(null)",[:],InfoDB.getDatasForTable(null))











