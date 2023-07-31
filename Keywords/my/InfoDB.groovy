package my

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic

@CompileStatic
public class InfoDB {

	private static final String CLASS_FORLOG = 'InfoDB'


	public static Map  <String, Map<String, List<Object>>> map = [:]      // Map de Map de List[TABLE_NAME] [COLUMN_NAME] [0]:ORDINAL_POSITION [1]:IS_NULLABLE [2]:DATA_TYPE [3]:MAXCHAR [4]:DOMAIN_NAME [5]:CONSTRAINT_NAME


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

	/*
	 public static load() {
	 if (map.isEmpty()) {
	 */			
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
			if (XLS.getCellValue(row.getCell(0))=='') {
				break
			}
			List listxls = XLS.loadRow(row,HEADERS.size())
			String tableName = listxls[0].toString()
			String columnName= listxls[1].toString()
			if (!map[tableName]) {
				map[tableName] = [:]
			}
			map[tableName][columnName] = listxls.subList(2, 8)
		}
		// }
		Log.addTraceEND(CLASS_FORLOG,"static")

	}





	public static List getPK(String table) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getPK",[table:table])
		List list=[]
		map[table].each { k, li ->
			if (li[5]!='NULL') list.add(k)
		}
		Log.addTraceEND(CLASS_FORLOG,"getPK",list)
		return list
	}


	public static boolean isPK(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isPK",[table:table,name:name])
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret= map[table][name][5] !='NULL'
		}
		Log.addTraceEND(CLASS_FORLOG,"isPK",ret)
		return ret
	}


	public static boolean isTableExist(String table) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isTableExist",[table:table])
		boolean ret = map.containsKey(table)
		Log.addTraceEND(CLASS_FORLOG,"isTableExist",ret)
		return ret
	}




	public static String getDATA_TYPE(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getDATA_TYPE",[table:table,name:name])
		String ret = map[table][name][2]
		Log.addTraceEND(CLASS_FORLOG,"getDATA_TYPE",ret)
		return ret
	}



	public static int getDATA_MAXCHAR(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getDATA_MAXCHAR",[table:table,name:name])
		int ret = (int)map[table][name][3]
		Log.addTraceEND(CLASS_FORLOG,"getDATA_MAXCHAR",ret)
		return ret
	}



	public static boolean isNumeric(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isNumeric",[table:table,name:name])
		boolean ret = map[table][name][2]==getNumeric()
		Log.addTraceEND(CLASS_FORLOG,"isNumeric",ret)
		return ret
	}

	public static boolean isImage(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"isImage",[table:table,name:name])
		boolean ret = map[table][name][2]==getImage()
		Log.addTraceEND(CLASS_FORLOG,"isImage",ret)
		return ret
	}



	public static String getNumeric() {
		return 'numeric'
	}



	public static String getVarchar() {
		return 'varchar'
	}


	public static String getImage() {
		return 'image'
	}

	public static castJDDVal(String table, String name, def val) {
		Log.addTraceBEGIN(CLASS_FORLOG,"castJDDVal",[table:table,name:name,val:val])
		def ret
		switch (getDATA_TYPE( table, name)) {
			case getVarchar():
				ret = val.toString()
				break
			/*
			 case getImage():
			 def texte = new DefaultStyledDocument()
			 def editorKit = new RTFEditorKit()
			 editorKit.read(new StringReader(val), texte, 0)
			 return texte.getText(0, texte.getLength())
			 break
			 */
			default :
				ret = val
		}
		Log.addTraceEND(CLASS_FORLOG,"castJDDVal",ret)
		return ret
	}


	public static boolean inTable(String table, String name) {
		Log.addTraceBEGIN(CLASS_FORLOG,"inTable",[table:table,name:name])
		boolean ret = map[table].containsKey(name)
		Log.addTraceEND(CLASS_FORLOG,"inTable",ret)
		return ret
	}


}// end of class
