package my


import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import my.Log as MYLOG

public class PREJDD {


	static checkPREJDD(Map map){

		XSSFWorkbook book = my.XLS.open(my.PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ')))

		Sheet sheet = book.getSheet(map.getAt('PREJDDTAB'))

		List list = []
		MYLOG.addDEBUG("Controle de '" + map.getAt('JDDID') +"' de '" + map.getAt('JDDNAME') + "' (" + map.getAt('TAB') + ") dans '" + my.PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ')) + "' '"+ map.getAt('PREJDDID') + "'",0)
		
		this.getListOfCasDeTestAndIDValue(list,sheet, map.getAt('PREJDDID'))

		int nbFound =0
		map.getAt('LISTCDTVAL').each{ cdtVal ->
			boolean found = false
			list.each{ cdtValPre ->
				if (cdtVal == cdtValPre) {
					found =true
					nbFound++
				}
			}
			if (found) {
				MYLOG.addDEBUG(cdtVal+' trouvé')
			}else {
				MYLOG.addINFO("Controle de '" + map.getAt('JDDID') +"' de '" + map.getAt('JDDNAME') + "' (" + map.getAt('TAB') + ") dans '" + my.PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ')) + "' '"+ map.getAt('PREJDDID') + "'")
				MYLOG.addDETAILFAIL(cdtVal+' non trouvé')
			}
		}
		MYLOG.addDEBUGDETAIL(nbFound + "/" +map.getAt('LISTCDTVAL').size() + ' trouvé(s)',0)
	}


	private static List getListOfCasDeTestAndIDValue(List list, Sheet sheet, String ID) {

		int idxID  = my.XLS.getColumnIndexOfColumnName(sheet, ID)

		for (int numLine : 1..sheet.getLastRowNum()) {

			Row row = sheet.getRow(numLine)
			
			// exit if lastRow of param
			if (!row || my.XLS.getCellValue(row.getCell(0))=='') {
				break
			}
			
			String casDeTest = my.XLS.getCellValue(row.getCell(0))
			String IDvalue = my.XLS.getCellValue(row.getCell(idxID))

			list.add("'" + casDeTest + "' - '" + IDvalue + "'")
		}
	}



	static List loadDATA(Sheet sheet,int size) {
		MYLOG.addDEBUG('Lecture PREJDD data')
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		List datas =[]
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			datas << my.XLS.loadRow(row,size)
		}
		MYLOG.addDEBUG('PREJDD data size = ' + datas.size() )
		return datas
	}


}// en dof class
