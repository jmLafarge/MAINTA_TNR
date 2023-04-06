package checkprerequis

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import my.PREJDDFiles as MYPREJDDFILES
import my.Log as MYLOG
import my.XLS as MYXLS


public class Check_CAL {
	
	
	private static List listCAL =[]
	private static List listCALDEF=[]	
		
	
	
	static run() {
				
		MYLOG.addSubTITLE('Vérification spécifique de CAL/CALDEF')
		
		XSSFWorkbook book = my.XLS.open(MYPREJDDFILES.PREJDDfilemap['RO.CAL'])
		
		Sheet shCAL = book.getSheet('001')
		Sheet shCALDEF = book.getSheet('001A')

		this.loadListCAL(shCAL)
		this.loadListCALDEF(shCALDEF)
		
		this.listCAL.each { 
			
			for (def i in [1,2,3,4,5,6,7,'$VIDE']){
				if (!this.listCALDEF.contains(it+' - '+ i.toString())) {
					MYLOG.addDETAIL("Manque $it - $i dans CALDEF")
				}
			}
		}
	}
	
	
	
	
	private static loadListCAL(Sheet sheet) {
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		MYLOG.addDETAIL("Chargement de CAL")
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (MYXLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			this.listCAL.add(MYXLS.getCellValue(row.getCell(0))+ ' - ' + MYXLS.getCellValue(row.getCell(1)))
		}
	}
	

	
	private static loadListCALDEF(Sheet sheet) {
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		MYLOG.addDETAIL("Chargement de CALDEF")
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (MYXLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			this.listCALDEF.add(MYXLS.getCellValue(row.getCell(0))+ ' - ' + MYXLS.getCellValue(row.getCell(1))+ ' - ' + MYXLS.getCellValue(row.getCell(5)))
		}
	}
	
}
