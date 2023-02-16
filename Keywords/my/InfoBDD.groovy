package my

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

public class InfoBDD {

	private static Map colnameMap= [:]
	private static Map PKMap= [:]

	private static List line = []

	static load() {

		my.Log.addSubTITLE("Chargement de : " + my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOBDDFILENAME'))
		XSSFWorkbook book = my.XLS.open(my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('INFOBDDFILENAME'))

		Sheet sheet = book.getSheet('INFO')

		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()

		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}
			this.line << my.XLS.loadRow(row,8)
		}
		this.colnameMap = this.line.groupBy { it[ 0 ] } .collectEntries { key, value -> [key, value*.getAt( 1 )]}

		this.PKMap = this.line.groupBy { it[ 0 ] }.collectEntries { key, value -> [key, value*.getAt( 7 )]}
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
