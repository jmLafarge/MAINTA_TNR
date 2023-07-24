package my

import groovy.transform.CompileStatic
import my.Log
import my.result.TNRResult

//Pour la lecture du format RTF
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.rtf.RTFEditorKit
import java.io.StringReader


import my.XLS as MYXLS
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


	public static load() {
		
		if (map.isEmpty()) {

			fileName = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOBDDFILENAME')
			Log.addSubTITLE("Chargement de : " + fileName,'-',120,1)
	
			book = MYXLS.open(fileName)
	
			Sheet sheet = book.getSheet('INFO')
	
			Iterator<Row> rowIt = sheet.rowIterator()
			Row row = rowIt.next()
			List <String> headers = MYXLS.loadRow(row)
	
			Log.addINFO('Contrôle entête fichier',1)
			if (headers!=HEADERS) {
				Log.addERROR(fileName + ' Entête fichier différente de celle attendue :')
				TNRResult.addDETAIL('Entête attendue : ' + HEADERS.join(' - '))
				TNRResult.addDETAIL('Entête lue      : ' + headers.join(' - '))
				KeywordUtil.markErrorAndStop("Entête fichier ${fileName} différente de celle attendue")
			}
	
			while(rowIt.hasNext()) {
				row = rowIt.next()
				if (MYXLS.getCellValue(row.getCell(0))=='') {
					break
				}
				List listxls = MYXLS.loadRow(row,HEADERS.size())
				String tableName = listxls[0].toString()
				String columnName= listxls[1].toString()
				if (!map[tableName]) {
					map[tableName] = [:]
				}
				map[tableName][columnName] = listxls.subList(2, 8)
			}
		}

	}





	public static List getPK(String table) {
		Log.addTrace("getPK($table)")
		List list=[]
		map[table].each { k, li ->
			if (li[5]!='NULL') list.add(k)
		}
		Log.addTrace("getPK() --> ${list.join('|')}")
		return list
	}


	public static boolean isPK(String table, String name) {
		
		if (isTableExist(table) && inTable(table,name)) {
			return map[table][name][5] !='NULL'
		} else {
			return false
		}
	}


	public static boolean isTableExist(String table) {
		return map.containsKey(table)
	}




	public static String getDATA_TYPE(String table, String name) {
		return map[table][name][2]
	}



	public static int getDATA_MAXCHAR(String table, String name) {
		return (int)map[table][name][3]
	}



	public static boolean isNumeric(String table, String name) {
		return map[table][name][2]==getNumeric()
	}

	public static boolean isImage(String table, String name) {
		return map[table][name][2]==getImage()
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
		switch (getDATA_TYPE( table, name)) {
			case getVarchar():
				return val.toString()
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
				return val
		}
	}


	public static inTable(String table, String name) {
		return map[table].containsKey(name)
	}


}// end of class
