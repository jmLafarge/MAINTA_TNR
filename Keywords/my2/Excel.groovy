package my2


import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.usermodel.XSSFWorkbook

public class Excel {

	List headers = []
	List params = []
	List datas= []

	def Excel(path,tab) {


		File sourceExcel = new File(path)
		def fxls = new FileInputStream(sourceExcel)
		XSSFWorkbook wb = new XSSFWorkbook(fxls)
		Sheet sheet = wb.getSheet(tab);


		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		this.headers = this.loadHeaders(row)


		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (my.XLS.getCellValue(row.getCell(0))=='DATA') {
				break
			}
			this.params << this.loadRow(row,headers.size())
		}


		while(rowIt.hasNext()) {
			row = rowIt.next()
			Cell c0 = row.getCell(0)
			if (my.XLS.getCellValue(row.getCell(0)) == '') {
				break
			}
			this.datas << this.loadRow(row,headers.size())
		}
	}







	private loadHeaders(Row row) {
		List header = []
		for (Cell cell : row) {
			def value = my.XLS.getCellValue(cell)
			if (value == '') {
				break
			}
			header[cell.getColumnIndex()] = value
		}
		return header
	}





	private loadRow(Row row, int colIndexMax) {
		List data = []
		for (Cell cell : row) {
			def value = my.XLS.getCellValue(cell)
			if (cell.getColumnIndex()>=colIndexMax) {
				break
			}
			data[cell.getColumnIndex()] = value
		}
		return data
	}
}