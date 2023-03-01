package my

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook



public class XLS {


	static XSSFWorkbook open(String fullname) {

		File sourceExcel = new File(fullname)
		def fxls = new FileInputStream(sourceExcel)
		return new XSSFWorkbook(fxls)
	}


	
	static writeCell(Row row, int colIdx,def val,CellStyle cellStyle = null) {
		
		Cell cell = row.getCell(colIdx)

		if (cell == null) { row.createCell(colIdx)}
		cell = row.getCell(colIdx)
		cell.setCellValue(val)

		if (cellStyle != null) { cell.setCellStyle(cellStyle)}

	}



	/**
	 *
	 * @param row
	 * @param colIndexMax
	 *
	 * @return 	retourne une List avec les valeurs de la ligne jusqu'à l'index "colIndexMax"
	 * 			Si colIndexMax=0 alors on remplit jusqu'à trouver une cellule vide
	 *
	 */
	static List loadRow(Row row, int size = 0) {
		List data = []
		for (Cell cell : row) {
			def value = my.XLS.getCellValue(cell)
			if ( (size == 0 && cell.getColumnIndex()!=0 && value =='') || (size != 0 && cell.getColumnIndex()>=size) ) {
				break
			}
			data[cell.getColumnIndex()] = value
		}
		return data
	}





	/**
	 * Format cell value to string 
	 *
	 * @param cell : a cell
	 *
	 * @return cell value (type)
	 */
	static public def getCellValue(Cell cell){

		def CellData = null;

		if (cell ==null) {

			CellData = ''

			my.Log.addDEBUG('\tgetCellValue() cell is null !',2)
		}else {
			//LOG
			my.Log.addDEBUG("\tcell.getAddress() = '" + cell.getAddress().toString() + "' getCellType() = '" + cell.getCellType(),3)

			switch (cell.getCellType()){
				case Cell.CELL_TYPE_STRING: // 1
					CellData = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC: // 0

					if (DateUtil.isCellDateFormatted(cell)) {
						my.Log.addDEBUG('\t\tisCellDateFormatted() = true',3)
						CellData = cell.getDateCellValue()
					}
					else {
						CellData = (long)cell.getNumericCellValue()
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN: // 4
					CellData = cell.getBooleanCellValue()
					break;
				case Cell.CELL_TYPE_FORMULA: // 2
				// get calculated value if the cell type is formula
					Workbook wb = cell.getSheet().getWorkbook()
					CreationHelper crateHelper = wb.getCreationHelper()
					FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator()
				// get recursively if the value is still formula
					CellData = this.getCellValue(evaluator.evaluateInCell(cell))
					break;

				case Cell.CELL_TYPE_BLANK: // 3
					CellData = ""
					break;

				case Cell.CELL_TYPE_ERROR: // 5
					CellData = 'ERROR type Cell'
					my.Log.addERROR('\tUnknown type Cell !')
					break;

				default :
					CellData = 'Unknown type Cell : ' + cell.getCellType()
					my.Log.addERROR('\tERROR type Cell !')
					break;

			}
		}
		return CellData;

	} //end


	static int getColumnIndexOfColumnName(Sheet sheet, String columnName, int numRow = 0) {

		for (Cell cell : sheet.getRow(numRow)) {
			if (this.getCellValue(cell) == columnName) {
				return cell.getColumnIndex()
			}
		}
		return null
	}






} // end of class
