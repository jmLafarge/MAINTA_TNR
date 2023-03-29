import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.usermodel.XSSFWorkbook

//test XLSX

//							see https://poi.apache.org/components/spreadsheet/quick-guide.html
/* */
'LECTURE'

	// Ouvrir un fichier existant
	FileInputStream file = new FileInputStream(new File('workbook.xlsx'))
	XSSFWorkbook workbook = new XSSFWorkbook(file)
	
	println '************************************************************ ' +workbook.getSheet('toto')
	
	//iterate over all the sheets in a workbook, all the rows in a sheet, or all the cells in a row
/*
	for (Sheet sheet : workbook ) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				// Do something here
			}
		}
	}
	


'Getting the cell contents'

	DataFormatter formatter = new DataFormatter();
	Sheet sh = workbook.getSheetAt(0);
	for (Row row : sh) {
		for (Cell cell : row) {
			CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
			System.out.print(cellRef.formatAsString());
			System.out.print(" - ");
			// get the text that appears in the cell by getting the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
			String text = formatter.formatCellValue(cell);
			System.out.println(text);
			// Alternatively, get the value and format it yourself
			switch (cell.getCellType()) {
				case CellType.STRING:
					System.out.println(cell.getRichStringCellValue().getString());
					break;
				case CellType.NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						System.out.println(cell.getDateCellValue());
					} else {
						System.out.println(cell.getNumericCellValue());
					}
					break;
				case CellType.BOOLEAN:
					System.out.println(cell.getBooleanCellValue());
					break;
				case CellType.FORMULA:
					System.out.println(cell.getCellFormula());
					break;
				case CellType.BLANK:
					System.out.println();
					break;
				default:
					System.out.println();
			}
		}
	}

*/

'CREATION'

// on crée un book type xlsx
Workbook wb = new XSSFWorkbook()

CreationHelper createHelper = wb.getCreationHelper();

// on crée un sheet
Sheet sheet1 = wb.createSheet("new sheet");

'Creating Cells'
	// Create a row and put some cells in it. Rows are 0 based.
	Row row = sheet1.createRow(0);
	// Create a cell and put a value in it.
	Cell cell = row.createCell(0);
	cell.setCellValue(1);
	
	// Or do it on one line.
	row.createCell(1).setCellValue(1.2);
	row.createCell(2).setCellValue( createHelper.createRichTextString("This is a string"));
	row.createCell(3).setCellValue(true);

'Creating Date Cells'
	// Create a row and put some cells in it. Rows are 0 based.
	row = sheet1.createRow(1);

	// Create a cell and put a date value in it.  The first cell is not styled
	// as a date.
	cell = row.createCell(0);
	cell.setCellValue(new Date());
	
	// we style the second cell as a date (and time).  It is important to
	// create a new cell style from the workbook otherwise you can end up
	// modifying the built in style and effecting not only this cell but other cells.
	
	CellStyle cellStyle = wb.createCellStyle();
	cellStyle.setDataFormat(
		createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
	cell = row.createCell(1);
	cell.setCellValue(new Date());
	cell.setCellStyle(cellStyle);
	
	//you can also set date as java.util.Calendar
	cell = row.createCell(2);
	cell.setCellValue(Calendar.getInstance());
	cell.setCellStyle(cellStyle);

'Working with different types of cells'

	row = sheet1.createRow(2);
	row.createCell(0).setCellValue(1.1);
	row.createCell(1).setCellValue(new Date());
	row.createCell(2).setCellValue(Calendar.getInstance());
	row.createCell(3).setCellValue("a string");
	row.createCell(4).setCellValue(true);
	row.createCell(5).setCellType(CellType.ERROR);

'Demonstrates various alignment options'

	row = sheet1.createRow(3);
	row.setHeightInPoints(30);
	createCell(wb, row, 0, HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);
	createCell(wb, row, 1, HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.BOTTOM);
	createCell(wb, row, 2, HorizontalAlignment.FILL, VerticalAlignment.CENTER);
	createCell(wb, row, 3, HorizontalAlignment.GENERAL, VerticalAlignment.CENTER);
	createCell(wb, row, 4, HorizontalAlignment.JUSTIFY, VerticalAlignment.JUSTIFY);
	createCell(wb, row, 5, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
	createCell(wb, row, 6, HorizontalAlignment.RIGHT, VerticalAlignment.TOP);


'Working with borders'

	// Create a row and put some cells in it. Rows are 0 based.
	row = sheet1.createRow(4);
	// Create a cell and put a value in it.
	cell = row.createCell(1);
	cell.setCellValue(4);
	// Style the cell with borders all around.
	CellStyle style = wb.createCellStyle();
	style.setBorderBottom(BorderStyle.MEDIUM);
	style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	style.setBorderLeft(BorderStyle.MEDIUM);
	style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
	style.setBorderRight(BorderStyle.MEDIUM);
	style.setRightBorderColor(IndexedColors.BLUE.getIndex());
	style.setBorderTop(BorderStyle.MEDIUM_DASHED);
	style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	cell.setCellStyle(style);

	
	
'Fills and colors'

	// Create a row and put some cells in it. Rows are 0 based.
	row = sheet1.createRow(6);
	
	
	// Aqua background
	style = wb.createCellStyle();
	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());

	cell = row.createCell(1);
	//cell.setCellValue("X");
	cell.setCellStyle(style);
	
	// Orange "foreground", foreground being the fill foreground not the font color.
	style = wb.createCellStyle();
	style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	cell = row.createCell(2);
	//cell.setCellValue("X");
	cell.setCellStyle(style);
	
	//
	style = wb.createCellStyle();
	style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	style.setFillPattern(FillPatternType.BIG_SPOTS);
	cell = row.createCell(3);
	//cell.setCellValue("toot");
	cell.setCellStyle(style);
	
	// Orange "foreground", foreground being the fill foreground not the font color.
	style = wb.createCellStyle();
	style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	cell = row.createCell(4);
	//cell.setCellValue("X");
	cell.setCellStyle(style);
	
	
	// Orange "foreground", foreground being the fill foreground not the font color.
	style = wb.createCellStyle();
	style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	cell = row.createCell(5);
	//cell.setCellValue("X");
	cell.setCellStyle(style);
	
	
	// Orange "foreground", foreground being the fill foreground not the font color.
	style = wb.createCellStyle();
	style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	cell = row.createCell(6);
	//cell.setCellValue("X");
	cell.setCellStyle(style);
	
	
	// Orange "foreground", foreground being the fill foreground not the font color.
	style = wb.createCellStyle();
	style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	cell = row.createCell(7);
	//cell.setCellValue("X");
	cell.setCellStyle(style);
	
	// Orange "foreground", foreground being the fill foreground not the font color.
	style = wb.createCellStyle();
	style.setFillForegroundColor(IndexedColors.PLUM.getIndex());
	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	cell = row.createCell(8);
	//cell.setCellValue("X");
	cell.setCellStyle(style);

	
// Write the output to a file
OutputStream fileOut = new FileOutputStream("workbook.xlsx")
wb.write(fileOut)



/**
 * Creates a cell and aligns it a certain way.
 *
 * @param wb     the workbook
 * @param row    the row to create the cell in
 * @param column the column number to create the cell in
 * @param halign the horizontal alignment for the cell.
 * @param valign the vertical alignment for the cell.
 */
private static void createCell(Workbook wb, Row row, int column, HorizontalAlignment halign, VerticalAlignment valign) {
	Cell cell = row.createCell(column);
	cell.setCellValue("Align It");
	CellStyle cellStyle = wb.createCellStyle();
	cellStyle.setAlignment(halign);
	cellStyle.setVerticalAlignment(valign);
	cell.setCellStyle(cellStyle);
}
