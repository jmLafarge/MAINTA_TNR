package my

import my.Log as MYLOG

//Pour la lecture du format RTF
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.rtf.RTFEditorKit
import java.io.StringReader


import my.XLS as MYXLS
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.util.KeywordUtil

public class InfoBDD {

	public static Map map = [:]      // Map de Map de List[TABLE_NAME] [COLUMN_NAME] [0]:ORDINAL_POSITION [1]:IS_NULLABLE [2]:DATA_TYPE [3]:MAXCHAR [4]:DOMAIN_NAME [5]:CONSTRAINT_NAME


	private static XSSFWorkbook book
	private static String fileName = ''

	private static final List HEADERS	 = [
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

		fileName = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOBDDFILENAME')
		MYLOG.addSubTITLE("Chargement de : " + fileName,'-',120,1)

		book = MYXLS.open(fileName)

		Sheet sheet = book.getSheet('INFO')

		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		List headers = MYXLS.loadRow(row)

		MYLOG.addINFO('Contrôle entête fichier',1)
		if (headers!=HEADERS) {
			MYLOG.addERROR(fileName + ' Entête fichier différente de celle attendue :')
			MYLOG.addDETAIL('Entête attendue : ' + HEADERS.join(' - '))
			MYLOG.addDETAIL('Entête lue      : ' + headers.join(' - '))
			KeywordUtil.markErrorAndStop("Entête fichier ${fileName} différente de celle attendue")
		}

		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (MYXLS.getCellValue(row.getCell(0))=='') {
				break
			}
			List listxls = MYXLS.loadRow(row,HEADERS.size())
			if (!map[listxls[0]]) {
				map[listxls[0]] = [:]
			}
			map[listxls[0]][listxls[1]] = listxls.subList(2, 8)
		}

	}





	public static List getPK(String table) {
		List list=[]
		map[table].each { k, li ->
			if (li[5]!='NULL') list.add(k)
		}
		return list
	}


	public static boolean isPK(String table, String name) {

		return map[table][name][5]!='NULL'
	}


	public static boolean isTableExist(String table) {
		return map.containsKey(table)
	}




	public static String getDATA_TYPE(String table, String name) {
		return map[table][name][2]
	}



	public static def getDATA_MAXCHAR(String table, String name) {
		return map[table][name][3]
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
