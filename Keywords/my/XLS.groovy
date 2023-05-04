package my

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CreationHelper
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Hyperlink
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic


/**
 * @author X1009638
 *
 */
@CompileStatic
public class XLS {


	static XSSFWorkbook open(String fullname) {

		if (!fullname) Log.addERROR("my.XLS.open() fullname = '$fullname'")

		File sourceExcel = new File(fullname)
		def fxls = new FileInputStream(sourceExcel)
		return new XSSFWorkbook(fxls)
	}



	static writeCell(Row row, int colIdx,def val,CellStyle cellStyle = null, Hyperlink hyperlink = null) {

		//Log.addDEBUG("\twriteCell( RowNum:${row.getRowNum()}, colIdx:$colIdx,$val, cellStyle:${cellStyle.toString()},hyperlink:${hyperlink.toString()}",2)

		if (row==null) {
			Log.addERROR("row is NULL")
		}else {
			Cell cell = row.getCell(colIdx)

			if (!cell) row.createCell(colIdx)

			cell = row.getCell(colIdx)

			if (val instanceof Number) {
				cell.setCellValue(val.doubleValue())
			} else if (val){
				cell.setCellValue(val.toString())
			}else {
				cell.setCellValue('')
			}


			if (hyperlink) cell.setHyperlink(hyperlink)
			if (cellStyle) cell.setCellStyle(cellStyle)
		}
	}



	/**
	 * Retourne une List avec les valeurs de la ligne
	 * @param row     : la ligne
	 * @param ideb    : col index de début
	 * @param size    : taille de la liste
	 * @param nullval : valeur pour une cell null
	 *
	 * @return 	retourne une List de taille size avec les valeurs de la ligne de ideb à 
	 * 			Si size=0 alors on remplit avec les valeurs de la ligne --> jusqu'à getLastCellNum()
	 *
	 */
	static List loadRow2(Row row, int ideb=0, int size=0, String nullval='') {
		List data = []
		if (size==0) size = row.getLastCellNum()
		if (ideb!=0) size = size + ideb

		for (int i = ideb; i < size; i++) {
			Cell cell = row.getCell(i)
			def value = my.XLS.getCellValue(cell,nullval)
			data[i-ideb] = value
		}
		return data
	}



	/**
	 * Retourne une List avec les valeurs de la ligne
	 * @param row     : la ligne
	 * @param size    : taille de la liste
	 * @param nullval : valeur pour une cell null
	 *
	 * @return 	retourne une List de taille size avec les valeurs de la ligne depuis 0
	 * 			Si size=0 alors on remplit avec les valeurs de la ligne --> jusqu'à trouver une cellule vide ou null
	 * 			si size > getLastCellNum alors on s'arrete à getLastCellNum
	 *
	 */
	static List loadRow(Row row, int size = 0) {
		List data = []
		for (Cell cell : row) {
			def value = my.XLS.getCellValue(cell)
			if ( (size == 0 && cell.getColumnIndex()!=0 && value == '') || (size != 0 && cell.getColumnIndex()>=size) ) {
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
	public static def getCellValue(Cell cell, String nullval =''){

		def CellData = null;

		if (cell ==null) {

			CellData = nullval

			Log.addDEBUG('\tgetCellValue() cell is null !',2)
		}else {
			//LOG
			Log.addDEBUG("\tcell.getAddress() = '" + cell.getAddress().toString() + "' getCellType() = '" + cell.getCellType(),3)

			switch (cell.getCellType()){
				case Cell.CELL_TYPE_STRING: // 1
					CellData = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC: // 0

					if (DateUtil.isCellDateFormatted(cell)) {
						Log.addDEBUG('\t\tisCellDateFormatted() = true',3)
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
					CellData = getCellValue(evaluator.evaluateInCell(cell))
					break;

				case Cell.CELL_TYPE_BLANK: // 3
					CellData = ""
					break;

				case Cell.CELL_TYPE_ERROR: // 5
					CellData = 'ERROR type Cell'
					Log.addERROR('\tUnknown type Cell !')
					break;

				default :
					CellData = 'Unknown type Cell : ' + cell.getCellType()
					Log.addERROR('\tERROR type Cell !')
					break;

			}
		}
		return CellData;

	} //end


	static int getColumnIndexOfColumnName(Sheet sheet, String columnName, int numRow = 0) {

		for (Cell cell : sheet.getRow(numRow)) {
			if (getCellValue(cell) == columnName) {
				return cell.getColumnIndex()
			}
		}
		return null
	}



	static int getLastColumnIndex(Sheet sheet, int numRow) {

		int lastCellNum = sheet.getRow(numRow).getLastCellNum()
		for (int i : (0..lastCellNum)) {
			Cell cell = sheet.getRow(numRow).getCell(i)
			if (!getCellValue(cell)) {
				return i
			}
		}
		return lastCellNum
	}







	/**
	 * Retourne le numéro de la 1ere ligne ou la cellule est vide ou null (commence à 0)
	 * @param sheet
	 * @param col
	 * @return
	 */
	static int getRowNumOfFirstCellFree(Sheet sheet, int col=0) {

		int num = -1
		for (int numLine : (0..sheet.getLastRowNum())) {
			Row row = sheet.getRow(numLine)
			if (row==null) {
				num=numLine
				break
			}else if (getCellValue(row.getCell(col))==''){
				num=numLine
				break
			}
			num=numLine+1
		}
		if (num==-1) {
			Log.addERROR("getRowNumOfFirstCellFree of "+sheet.getSheetName()+" : $num")
		}else {
			Log.addDEBUG("getRowNumOfFirstCellFree of "+sheet.getSheetName()+" : $num",2)
		}
		return num
	}



	static Row getNewRow(Sheet sheet, int numRow) {
		Row row
		if (sheet.getRow(numRow)==null) {
			row = sheet.createRow(numRow)
		}else {
			row = sheet.getRow(numRow)
		}
		return row
	}


	static Row getNextRow(Sheet sheet, int col=0) {

		return getNewRow(sheet, getRowNumOfFirstCellFree(sheet,col))
	}


} // end of class
