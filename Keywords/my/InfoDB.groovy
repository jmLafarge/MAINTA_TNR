package my

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic

@CompileStatic
public class InfoDB {

	private static final String CLASS_FORLOG = 'InfoDB'

	private static Map  <String, Map<String, Map <String , Object>>> datas = [:] // Map de Map de List[TABLE_NAME] [COLUMN_NAME] [0]:ORDINAL_POSITION [1]:IS_NULLABLE [2]:DATA_TYPE [3]:MAXCHAR [4]:DOMAIN_NAME [5]:CONSTRAINT_NAME

	private static XSSFWorkbook book
	private static String fileName = ''

	private static final List <String> HEADERS	 = [
		'TABLE_NAME',
		'COLUMN_NAME',
		'ORDINAL_POSITION',
		'IS_NULLABLE',
		'DATA_TYPE',
		'MAXCHAR',
		'DOMAIN_NAME',
		'CONSTRAINT_NAME'
	]


	static {
		Log.addTraceBEGIN(CLASS_FORLOG,"static",[:])
		fileName = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFODBFILENAME')

		book = XLS.open(fileName)

		Sheet sheet = book.getSheet('INFO')

		Iterator<Row> rowIt = sheet.rowIterator()

		Row row = rowIt.next()
		List <String> headers = XLS.loadRow(row)

		Log.addTrace('Contrôle entête fichier')
		if (headers!=HEADERS) {
			Log.addERROR(fileName + ' Entête fichier différente de celle attendue :')
			Log.addDETAIL('Entête attendue : ' + HEADERS.join(' - '))
			Log.addDETAIL('Entête lue      : ' + headers.join(' - '))
			Log.addErrorAndStop('')
		}


		while(rowIt.hasNext()) {
			row = rowIt.next()
			String tableName = row.getCell(headers.indexOf("TABLE_NAME")).getStringCellValue()

			if (!tableName) {
				break
			}

			String columnName = row.getCell(headers.indexOf("COLUMN_NAME")).getStringCellValue()

			List lineXLS = XLS.loadRow(row,HEADERS.size())

			Map columnDetails = [
				headers.subList(2, 8),
				lineXLS.subList(2, 8)
			].transpose().collectEntries()

			if (!datas[tableName]) {
				datas[tableName] = [:]
			}
			datas[tableName][columnName] = columnDetails
		}
		Log.addTraceEND(CLASS_FORLOG,"static")
	}




	public static boolean isTableExist(String table) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isTableExist",[table:table])
		boolean ret = datas.containsKey(table)
		Log.addTraceEND(CLASS_FORLOG,"isTableExist",ret)
		return ret
	}


	public static boolean inTable(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"inTable",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table)) {
			ret = datas[table].containsKey(name)
		}
		Log.addTraceEND(CLASS_FORLOG,"inTable",ret)
		return ret
	}


	public static List getPK(String table) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getPK",[table:table])
		List list= []
		if (isTableExist(table)) {
			list= datas[table].findAll { entry -> entry.value.CONSTRAINT_NAME != 'NULL'}.keySet().toList()
		}
		Log.addTraceEND(CLASS_FORLOG,"getPK",list)
		return list
	}


	public static boolean isPK(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isPK",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret= datas[table][name]['CONSTRAINT_NAME'] !='NULL'
		}
		Log.addTraceEND(CLASS_FORLOG,"isPK",ret)
		return ret
	}





	public static String getDATA_TYPE(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getDATA_TYPE",[table:table,name:name])
		String ret = null
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']
		}
		Log.addTraceEND(CLASS_FORLOG,"getDATA_TYPE",ret)
		return ret
	}



	public static int getDATA_MAXCHAR(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getDATA_MAXCHAR",[table:table,name:name])
		int ret = 0
		if (isTableExist(table) && inTable(table,name)) {
			def val = datas[table][name]['MAXCHAR']
			if (val && val!='NULL') {
				ret = (int) val
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"getDATA_MAXCHAR",ret)
		return ret
	}



	public static int getORDINAL_POSITION(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getORDINAL_POSITION",[table:table,name:name])
		int ret = 0
		if (isTableExist(table) && inTable(table,name)) {
			def val = datas[table][name]['ORDINAL_POSITION']
			if (val && val!='NULL') {
				ret = (int) val
			}
		}
		Log.addTraceEND(CLASS_FORLOG,"getORDINAL_POSITION",ret)
		return ret
	}





	public static boolean isNumeric(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isNumeric",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']=='numeric'
		}
		Log.addTraceEND(CLASS_FORLOG,"isNumeric",ret)
		return ret
	}


	public static boolean isImage(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isImage",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']=='image'
		}
		Log.addTraceEND(CLASS_FORLOG,"isImage",ret)
		return ret
	}


	public static boolean isVarchar(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isImage",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']=='varchar'
		}
		Log.addTraceEND(CLASS_FORLOG,"isImage",ret)
		return ret
	}


	public static boolean isDatetime(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isDatetime",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']=='datetime'
		}
		Log.addTraceEND(CLASS_FORLOG,"isDatetime",ret)
		return ret
	}


	public static boolean isBoolean(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isBoolean",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DOMAIN_NAME']=='T_BOOLEEN'
		}
		Log.addTraceEND(CLASS_FORLOG,"isBoolean",ret)
		return ret
	}






	public static castJDDVal(String table, String name, def val) {
		Log.addTraceBEGIN(CLASS_FORLOG,"castJDDVal",[table:table,name:name,val:val])
		def ret
		if (isVarchar(table,name)) {
			ret = val.toString()
		}else {
			ret = val
		}
		Log.addTraceEND(CLASS_FORLOG,"castJDDVal",ret)
		return ret
	}



	public static Map<String, Map <String , Object>> getDatasForTable(String table) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getDatasForTable",[table:table])
		Map<String, Map <String , Object>> ret =[:]
		if (isTableExist(table)) {
			ret = datas[table]
		}
		Log.addTraceEND(CLASS_FORLOG,"getDatasForTable",ret)
		return ret


	}





}// end of class
