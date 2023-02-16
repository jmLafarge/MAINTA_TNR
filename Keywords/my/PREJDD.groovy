package my


import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook


public class PREJDD {


	static checkPREJDD(Map map){

		XSSFWorkbook book = my.XLS.open(my.PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ')))

		Sheet sheet = book.getSheet(map.getAt('PREJDDTAB'))

		List list = []
		this.getListOfCasDeTestAndIDValue(list,sheet, map.getAt('PREJDDID'))

		my.Log.addSTEP("Controle de '" + map.getAt('JDDID') +"' de '" + map.getAt('JDDNAME') + "' (" + map.getAt('TAB') + ") dans '" + my.PREJDDFiles.getFullName(map.getAt('PREJDDMODOBJ')) + "' '"+ map.getAt('PREJDDID') + "'")
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
				my.Log.addDEBUG(cdtVal+' trouvé')
			}else {
				my.Log.addDETAILFAIL(cdtVal+' non trouvé')
			}
		}
		my.Log.addDETAIL(nbFound + "/" +map.getAt('LISTCDTVAL').size() + ' trouvé(s)')
	}


	private static List getListOfCasDeTestAndIDValue(List list, Sheet sheet, String ID) {

		int idxID  = my.XLS.getColumnIndexOfColumnName(sheet, ID)

		for (int numLine : 1..sheet.getLastRowNum()) {

			Row row = sheet.getRow(numLine)

			String casDeTest = my.XLS.getCellValue(row.getCell(0))
			String IDvalue = my.XLS.getCellValue(row.getCell(idxID))

			// exit if lastRow of param
			if ( my.XLS.getCellValue(row.getCell(0))=="") {
				break
			}

			list.add("'" + casDeTest + "' - '" + IDvalue + "'")

		}

	}



	static List loadDATA(Sheet sheet,int colIndexMax) {
		my.Log.addDEBUG('Lecture PREJDD data')
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		List datas =[]
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			datas << my.XLS.loadRow(row,colIndexMax)
		}
		my.Log.addDEBUG('PREJDD data size = ' + datas.size() )
		return datas
	}


}// en dof class
