package my2


import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.usermodel.XSSFWorkbook

public class ExcelSAV {

	def parse(path,tab) {
		/*
		 InputStream inp = new FileInputStream(path)
		 Workbook wb = WorkbookFactory.create(inp);
		 Sheet sheet = wb.getSheetAt(0);
		 */
		File sourceExcel = new File(path)
		def fxls = new FileInputStream(sourceExcel)
		XSSFWorkbook wb = new XSSFWorkbook(fxls)
		Sheet sheet = wb.getSheet(tab);
		//

		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		def headers = getRowData(row)

		def rows = []
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (row.getCell(0).toString()=='') {
				break
			}
			rows << getRowData(row)
		}
		[headers, rows]
	}

	def getRowData(Row row) {
		def data = []
		for (Cell cell : row) {
			getValue(row, cell, data)
		}
		data
	}
	
	
	/*
	 def getRowReference(Row row, Cell cell) {
	 def rowIndex = row.getRowNum()
	 def colIndex = cell.getColumnIndex()
	 CellReference ref = new CellReference(rowIndex, colIndex)
	 ref.getRichStringCellValue().getString()
	 }
	 */
	def getValue(Row row, Cell cell, List data) {
		def rowIndex = row.getRowNum()
		def colIndex = cell.getColumnIndex()
		def value = ""
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				value = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					value = cell.getDateCellValue();
				} else {
					value = cell.getNumericCellValue();
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				value = cell.getCellFormula();
				break;
			default:
				value = ""
		}
		data[colIndex] = value
		data
	}




	
	 def toXml(header, row) {
	 def obj = "<object>\n"
	 row.eachWithIndex { datum, i ->
	 def headerName = header[i]
	 obj += "\t<$headerName>$datum</$headerName>\n"
	 }
	 obj += "</object>"
	 }
	 public static void main(String[]args) {
	 def filename = 'temp.xlsx'
	 ExcelSAV parser = new ExcelSAV()
	 def (headers, rows) = parser.parse(filename)
	 println 'Headers'
	 println '------------------'
	 headers.each { header ->
	 println header
	 }
	 println "\n"
	 println 'Rows'
	 println '------------------'
	 rows.each { row ->
	 println parser.toXml(headers, row)
	 }
	 }
	 
}