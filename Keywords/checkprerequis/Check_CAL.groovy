package checkprerequis

import groovy.transform.CompileStatic
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import my.PREJDDFiles
import my.Log
import my.XLS
import my.result.TNRResult

@CompileStatic
public class Check_CAL {


	private static List listCAL =[]
	private static List listCALDEF=[]

	static run() {

		Log.addSubTITLE('Vérification spécifique de CAL/CALDEF')

		XSSFWorkbook book = XLS.open(PREJDDFiles.PREJDDfilemap['RO.CAL'])

		Sheet shCAL = book.getSheet('001')
		Sheet shCALDEF = book.getSheet('001A')

		loadListCAL(shCAL)
		loadListCALDEF(shCALDEF)

		listCAL.each {

			for (def i in [1, 2, 3, 4, 5, 6, 7, '$VIDE']){
				if (!listCALDEF.contains(it.toString() +' - '+ i.toString())) {
					Log.addDETAILFAIL("Manque $it - $i dans CALDEF")
				}
			}
		}
	}




	private static loadListCAL(Sheet sheet) {
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		TNRResult.addDETAIL("Chargement de CAL")
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (XLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			listCAL.add(XLS.getCellValue(row.getCell(0)).toString()+ ' - ' + XLS.getCellValue(row.getCell(1)))
		}
	}



	private static loadListCALDEF(Sheet sheet) {
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		TNRResult.addDETAIL("Chargement de CALDEF")
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (XLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			listCALDEF.add(XLS.getCellValue(row.getCell(0)).toString() + ' - ' + XLS.getCellValue(row.getCell(1))+ ' - ' + XLS.getCellValue(row.getCell(5)))
		}
	}
}
