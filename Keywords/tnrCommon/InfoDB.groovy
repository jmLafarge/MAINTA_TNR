package tnrCommon

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import groovy.transform.CompileStatic
import tnrLog.Log

@CompileStatic


/**
 * The InfoDB class provides methods to manage and query information about database tables and columns.
 * It initializes from a specific Excel file and builds a complex map structure to represent the database schema.
 * The class also includes various utility methods to check specific attributes of tables and columns.
 *
 * <p>Structure of the 'datas' Map:</p>
 * <ul>
 *   <li>Key: Table Name (String)</li>
 *   <li>Value: Map where the key is the Column Name (String) and the value is a Map with column details (Map&lt;String, Object&gt;)</li>
 * </ul>
 *
 * <p>Header constants used to validate and read the Excel file:</p>
 * <ul>
 *   <li>TABLE_NAME</li>
 *   <li>COLUMN_NAME</li>
 *   <li>ORDINAL_POSITION</li>
 *   <li>IS_NULLABLE</li>
 *   <li>DATA_TYPE</li>
 *   <li>MAXCHAR</li>
 *   <li>DOMAIN_NAME</li>
 *   <li>CONSTRAINT_NAME</li>
 * </ul>
 *
 * <p>The class contains methods to perform various operations such as:</p>
 * <ul>
 *   <li>Check if a table or column exists.</li>
 *   <li>Get primary keys of a table.</li>
 *   <li>Check if a column is a primary key.</li>
 *   <li>Retrieve the data type, max character, ordinal position of a column.</li>
 *   <li>Check if a column is numeric, image, varchar, datetime, boolean.</li>
 *   <li>Cast a value based on the varchar type.</li>
 *   <li>Get data for a specific table.</li>
 * </ul>
 * 
 * @author JM Lafarge
 * @version 1.0
 * 
 * 
 **/


public class InfoDB {

	private static final String CLASS_FORLOG = 'InfoDB'

	
	/*
	 * Data structure to hold information about database tables
	 * 
	 * [TABLE_NAME : ???? ]
	 * 		[COLUMN_NAME : ????]
	 * 			[ORDINAL_POSITION 	: ????]
	 * 			[IS_NULLABLE 		: not used]
	 * 			[DATA_TYPE 			: ????]
	 * 			[MAXCHAR 			: ????]
	 * 			[DOMAIN_NAME		: ????]
	 * 			[CONSTRAINT_NAME	: ????]
	 *
	 *
	 */
	private static Map  <String, Map<String, Map <String , Object>>> datas = [:] 


	// List of expected headers in the Excel file
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

	
	private static XSSFWorkbook book
	private static String filename = ''
	
	

	/**
	 * Static block to initialize the class, read the file and load the datas Map
	 * 
	 */
	static {
		Log.addTraceBEGIN(CLASS_FORLOG,"static",[:])
		filename = TNRPropertiesReader.getMyProperty('TNR_PATH') + File.separator + TNRPropertiesReader.getMyProperty('INFO_DB_FILENAME')

		book = ExcelUtils.open(filename)

		Sheet sheet = book.getSheet('INFO')

		Iterator<Row> rowIt = sheet.rowIterator()

		// Read the first row (header)
		Row row = rowIt.next()
		List <String> headers = ExcelUtils.loadRow(row)

		Log.addTrace('Contrôle entête fichier')
		if (headers!=HEADERS) {
			Log.addERROR(filename + ' Entête fichier différente de celle attendue :')
			Log.addDETAIL('Entête attendue : ' + HEADERS.join(' - '))
			Log.addDETAIL('Entête lue      : ' + headers.join(' - '))
			Log.addErrorAndStop('')
		}

		// Iterate through remaining rows (datas)
		while(rowIt.hasNext()) {
			row = rowIt.next()
			String tableName = row.getCell(headers.indexOf("TABLE_NAME")).getStringCellValue()

			//end of data
			if (!tableName) {
				break
			}

			String columnName = row.getCell(headers.indexOf("COLUMN_NAME")).getStringCellValue()

			List lineXLS = ExcelUtils.loadRow(row,HEADERS.size())

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



	/**
	 * Checks if a table exists.
	 *
	 * @param table The name of the table to check.
	 * @return true if the table exists, false otherwise.
	 */
	public static boolean isTableExist(String table) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isTableExist",[table:table])
		boolean ret = datas.containsKey(table)
		Log.addTraceEND(CLASS_FORLOG,"isTableExist",ret)
		return ret
	}


	/**
	 * Checks if a name is present in the table.
	 *
	 * @param table The name of the table.
	 * @param name The name to check.
	 * @return true if the name is present, false otherwise.
	 */
	public static boolean inTable(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"inTable",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table)) {
			ret = datas[table].containsKey(name)
		}
		Log.addTraceEND(CLASS_FORLOG,"inTable",ret)
		return ret
	}


	/**
	 * Retrieves primary keys for a specific table.
	 *
	 * @param table The name of the table.
	 * @return A list of primary keys.
	 */
	public static List getPK(String table) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getPK",[table:table])
		List list= []
		if (isTableExist(table)) {
			list= datas[table].findAll { entry -> entry.value.CONSTRAINT_NAME != 'NULL'}.keySet().toList()
		}
		Log.addTraceEND(CLASS_FORLOG,"getPK",list)
		return list
	}


	
	/**
	 * Checks if a given name is a primary key in a specific table.
	 *
	 * @param table The name of the table.
	 * @param name The name to check.
	 * @return true if the name is a primary key, false otherwise.
	 */
	public static boolean isPK(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isPK",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret= datas[table][name]['CONSTRAINT_NAME'] !='NULL'
		}
		Log.addTraceEND(CLASS_FORLOG,"isPK",ret)
		return ret
	}



	/**
	 * Retrieves the data type of a specific name in a given table.
	 *
	 * @param table The name of the table.
	 * @param name The name to check.
	 * @return The data type of the name.
	 */
	public static String getDATA_TYPE(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getDATA_TYPE",[table:table,name:name])
		String ret = null
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']
		}
		Log.addTraceEND(CLASS_FORLOG,"getDATA_TYPE",ret)
		return ret
	}



	/**
	 * Retrieves the maximum character length for a specific name in a given table.
	 *
	 * @param table The name of the table.
	 * @param name The name to check.
	 * @return The maximum character length.
	 */
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



	/**
	 * Retrieves the ordinal position of a specific name in a given table.
	 *
	 * @param table The name of the table.
	 * @param name The name to check.
	 * @return The ordinal position.
	 */
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



	/**
	 * Checks if the specified name in a given table is of numeric data type.
	 *
	 * @param table The name of the table.
	 * @param name The name to check.
	 * @return true if the specified name is of numeric data type, false otherwise.
	 */
	public static boolean isNumeric(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isNumeric",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']=='numeric'
		}
		Log.addTraceEND(CLASS_FORLOG,"isNumeric",ret)
		return ret
	}


	/**
	 * Checks if the specified name in a given table is of image data type.
	 *
	 * @param table The name of the table.
	 * @param name The name to check.
	 * @return true if the specified name is of image data type, false otherwise.
	 */
	public static boolean isImage(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isImage",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']=='image'
		}
		Log.addTraceEND(CLASS_FORLOG,"isImage",ret)
		return ret
	}


	
	/**
	 * Checks if the specified name in a given table is of varchar data type.
	 *
	 * @param table The name of the table.
	 * @param name The name to check.
	 * @return true if the specified name is of varchar data type, false otherwise.
	 */
	public static boolean isVarchar(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isImage",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']=='varchar'
		}
		Log.addTraceEND(CLASS_FORLOG,"isImage",ret)
		return ret
	}

	
	/**
	 * Checks if the specified name in a given table is of datetime data type.
	 *
	 * @param table The name of the table.
	 * @param name The name to check.
	 * @return true if the specified name is of datetime data type, false otherwise.
	 */
	public static boolean isDatetime(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isDatetime",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DATA_TYPE']=='datetime'
		}
		Log.addTraceEND(CLASS_FORLOG,"isDatetime",ret)
		return ret
	}


	/**
	 * Checks if the specified name in a given table corresponds to a boolean domain name.
	 *
	 * @param table The name of the table.
	 * @param name The name to check.
	 * @return true if the specified name corresponds to the boolean domain name, false otherwise.
	 */
	public static boolean isBoolean(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isBoolean",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret = datas[table][name]['DOMAIN_NAME']=='T_BOOLEEN'
		}
		Log.addTraceEND(CLASS_FORLOG,"isBoolean",ret)
		return ret
	}



	/**
	 * Casts the value according to the data type of the specified name in a given table. If the name is of varchar data type, the value is converted to a string.
	 *
	 * @param table The name of the table.
	 * @param name The name to check.
	 * @param val The value to cast.
	 * @return The cast value.
	 */
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



	/**
	 * Retrieves the data for a specified table. If the table exists, returns the data; otherwise, returns an empty map.
	 *
	 * @param table The name of the table.
	 * @return A map containing the data for the specified table, or an empty map if the table does not exist.
	 */
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
