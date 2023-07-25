package my

import groovy.transform.CompileStatic
import my.Log
import my.result.TNRResult

//Pour la lecture du format RTF
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.rtf.RTFEditorKit
import java.io.StringReader


import my.XLS
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.util.KeywordUtil

@CompileStatic
public class InfoBDD {

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
		Log.addTraceBEGIN("InfoBDD()")
		fileName = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOBDDFILENAME')

		book = XLS.open(fileName)

		Sheet sheet = book.getSheet('INFO')

		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		List <String> headers = XLS.loadRow(row)

		Log.addTrace('Contrôle entête fichier',1)
		if (headers!=HEADERS) {
			Log.addERROR(fileName + ' Entête fichier différente de celle attendue :')
			Log.addDETAIL('Entête attendue : ' + HEADERS.join(' - '))
			Log.addDETAIL('Entête lue      : ' + headers.join(' - '))
			KeywordUtil.markErrorAndStop("Entête fichier ${fileName} différente de celle attendue")
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
		Log.addTraceEND("InfoBDD()")

	}





	public static List getPK(String table) {
		Log.addTraceBEGIN("InfoBDD.getPK($table)")
		List list=[]
		map[table].each { k, li ->
			if (li[5]!='NULL') list.add(k)
		}
		Log.addTraceEND("InfoBDD.getPK()",list)
		return list
	}


	public static boolean isPK(String table, String name) {
		Log.addTraceBEGIN("InfoBDD.isPK('$table' , '$name')")
		boolean ret = false
		if (isTableExist(table) && inTable(table,name)) {
			ret= map[table][name][5] !='NULL'
		}
		Log.addTraceEND("InfoBDD.isPK()",ret)
		return ret
	}


	public static boolean isTableExist(String table) {
		Log.addTraceBEGIN("InfoBDD.isTableExist('$table')")
		boolean ret = map.containsKey(table)
		Log.addTraceEND("InfoBDD.isTableExist()",ret)
		return ret
	}




	public static String getDATA_TYPE(String table, String name) {
		Log.addTraceBEGIN("InfoBDD.getDATA_TYPE('$table' , '$name')")
		String ret = map[table][name][2]
		Log.addTraceEND("InfoBDD.getDATA_TYPE()",ret)
		return ret
	}



	public static int getDATA_MAXCHAR(String table, String name) {
		Log.addTraceBEGIN("InfoBDD.getDATA_MAXCHAR('$table' , '$name')")
		int ret = (int)map[table][name][3]
		Log.addTraceEND("InfoBDD.getDATA_MAXCHAR()",ret)
		return ret
	}



	public static boolean isNumeric(String table, String name) {
		Log.addTraceBEGIN("InfoBDD.isNumeric('$table' , '$name')")
		boolean ret = map[table][name][2]==getNumeric()
		Log.addTraceEND("InfoBDD.isNumeric()",ret)
		return ret
	}

	public static boolean isImage(String table, String name) {
		Log.addTraceBEGIN("InfoBDD.isImage('$table' , '$name')")
		boolean ret = map[table][name][2]==getImage()
		Log.addTraceEND("InfoBDD.isImage()",ret)
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
		Log.addTraceBEGIN("InfoBDD.castJDDVal('$table' , '$name' , '$val')")
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
		Log.addTraceEND("InfoBDD.castJDDVal()",ret)
		return ret
	}


	public static boolean inTable(String table, String name) {
		Log.addTraceBEGIN("InfoBDD.inTable('$table' , '$name')")
		boolean ret = map[table].containsKey(name)
		Log.addTraceEND("InfoBDD.inTable()",ret)
		return ret
	}


}// end of class
