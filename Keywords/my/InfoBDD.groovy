package my

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.util.KeywordUtil

public class InfoBDD {

	private static Map colnameMap= [:]
	private static Map PKMap= [:]

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

	private static List line = []

	static load() {

		my.Log.addSubTITLE("Chargement de : " + my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOBDDFILENAME'))
		XSSFWorkbook book = my.XLS.open(my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOBDDFILENAME'))

		Sheet sheet = book.getSheet('INFO')

		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		List headers = my.XLS.loadRow(row)
		my.Log.addSTEP('Contrôle entête fichier')
		if (headers!=this.HEADERS) {
			my.Log.addERROR('Entête fichier différente de celle attendue :')
			my.Log.addDETAIL('Entête attendue : ' + this.HEADERS.join(' - '))
			my.Log.addDETAIL('Entête lue      : ' + headers.join(' - '))
			KeywordUtil.markErrorAndStop("Entête fichier InfoBDD différente de celle attendue")
		}

		//Row row = rowIt.next()
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}
			this.line << my.XLS.loadRow(row,this.HEADERS.size())
		}
		this.colnameMap = this.line.groupBy { it[ 0 ] } .collectEntries { key, value -> [key, value*.getAt(this.HEADERS.indexOf('COLUMN_NAME'))]}

		this.PKMap = this.line.groupBy { it[ 0 ] }.collectEntries { key, value -> [key, value*.getAt(this.HEADERS.indexOf('CONSTRAINT_NAME'))]}
	}



	static List getPK(String table) {

		List list=[]
		this.PKMap.getAt(table).eachWithIndex { v,i ->
			if (v!='NULL') {
				list.add(this.colnameMap.getAt(table)[i])
			}
		}
		return list
	}


	static boolean isTableExist(String table) {
		return this.colnameMap.containsKey(table)
	}


	private static String getDATA_TYPE(String table, String name) {
		String ret = null
		this.line.each {
			if (it[0]==table && it[1]==name) {
				ret = it[4]
			}
		}
		return ret
	}

	static castJDDVal(String table, String name, def val) {

		switch (this.getDATA_TYPE( table, name)) {

			case 'varchar':
				return val.toString()
				break
			default :
				return val
		}
	}
}// end of class
