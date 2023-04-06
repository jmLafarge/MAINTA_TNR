package my

import my.Log as MYLOG
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.util.KeywordUtil

public class InfoBDD {

	public static Map map = [:]      // Map de Map de List[TABLE_NAME] [COLUMN_NAME] [0]:ORDINAL_POSITION [1]:IS_NULLABLE [2]:DATA_TYPE [3]:MAXCHAR [4]:DOMAIN_NAME [5]:CONSTRAINT_NAME


	private static XSSFWorkbook book
	private static String fileName = ''

	private static final List HEADERS	 = ['TABLE_NAME', 'COLUMN_NAME', 'ORDINAL_POSITION', 'IS_NULLABLE', 'DATA_TYPE', 'MAXCHAR', 'DOMAIN_NAME', 'CONSTRAINT_NAME']



	public static load() {

		this.fileName = my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOBDDFILENAME')
		MYLOG.addSubTITLE("Chargement de : " + this.fileName,'-',120,1)
		this.book = my.XLS.open(this.fileName)

		Sheet sheet = this.book.getSheet('INFO')


		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		List headers = my.XLS.loadRow(row)
		MYLOG.addINFO('Contrôle entête fichier',1)
		if (headers!=this.HEADERS) {
			MYLOG.addERROR(this.fileName + ' Entête fichier différente de celle attendue :')
			MYLOG.addDETAIL('Entête attendue : ' + this.HEADERS.join(' - '))
			MYLOG.addDETAIL('Entête lue      : ' + headers.join(' - '))
			KeywordUtil.markErrorAndStop("Entête fichier ${this.fileName} différente de celle attendue")
		}

		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}

			List listxls = my.XLS.loadRow(row,this.HEADERS.size())

			if (!this.map[listxls[0]]) {
				this.map[listxls[0]] = [:]
			}
			this.map[listxls[0]][listxls[1]] = listxls.subList(2, 8)

		}

	}





	public static List getPK(String table) {

		List list=[]
		this.map[table].each { k, li ->

			if (li[5]!='NULL') list.add(k)
		}
		return list
	}




	public static boolean isTableExist(String table) {
		//return this.colnameMap.containsKey(table)
		return this.map.containsKey(table)
	}




	public static String getDATA_TYPE(String table, String name) {

		return this.map[table][name][2]
	}


	public static boolean isNumeric(String table, String name) {
		
		return this.map[table][name][2]=='numeric'
	}


	public static castJDDVal(String table, String name, def val) {

		switch (this.getDATA_TYPE( table, name)) {

			case 'varchar':
				return val.toString()
				break
			default :
				return val
		}
	}

	public static inTable(String table, String name) {
		println ("table $table , name $name")
		return this.map[table].containsKey(name)
	}


}// end of class
