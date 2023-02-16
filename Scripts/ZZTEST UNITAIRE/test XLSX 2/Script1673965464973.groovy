
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook


// test XLSX2

'CREATION'

// on crée un book type xlsx
Workbook wb = new XSSFWorkbook()

// on crée un sheet
Sheet sheet1 = wb.createSheet("new sheet");

'Creating Cells'

	List row=[]
	
	int i = 1
	int j = 3
	
	//XSSFColor clr = new XSSFColor()
	
	Row row0 = sheet1.createRow(0)
	
	row0.createCell(1).setCellValue('Color Name')
	row0.createCell(2).setCellValue('Color Code')

	
	FillPatternType.each{itt->
			
		row0.createCell(j).setCellValue(itt.toString())

		IndexedColors.eachWithIndex{it,index->
		
			if (row[i] == null) {
				row[i] = sheet1.createRow(i);
			}
			
			row[i].createCell(1).setCellValue(it.toString())
			row[i].createCell(2).setCellValue(index.toString())
	
			style = wb.createCellStyle();


			style.setFillForegroundColor((short)index)
			
			style.setFillPattern(itt)
				
			row[i].createCell(j).setCellStyle(style)
			
			i++
			
		}
		sheet1.autoSizeColumn(j);
		i=1
		j++
	}
	sheet1.autoSizeColumn(1);
	sheet1.autoSizeColumn(2);
	
	
	public void autoSizeColumns(Workbook workbook) {
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getPhysicalNumberOfRows() > 0) {
				Row row = sheet.getRow(sheet.getFirstRowNum());
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					sheet.autoSizeColumn(columnIndex);
				}
			}
		}
	}
	
	
	//println FillPatternType.BIG_SPOTS
	
	

	//cell.setCellValue("X");
	

	
	// Write the output to a file
	OutputStream fileOut = new FileOutputStream("workbook2.xlsx")
	wb.write(fileOut)
	
	
	
	
/*
String s = 'toto'

println '-------:' +s[-1]


println new Date().format("dd/MM/yyyy")

println new Date().format("yyyyMMdd")

println new Date().format("yyyyMMdd-hhmmss")

println new Date().format("yyyyMMdd-HHmmss")

println new Date().toString()


println new Date().format("yyyyMMdd-hhmmss")
WebUI.delay(1)
println new Date().format("yyyyMMdd-hhmmss")
println new Date().format("yyyyMMdd-hhmmssS")
println new Date().format("yyyyMMdd-hhmmssSSS")
*/

// create a reader object on the properties file
//FileReader reader = new FileReader("TNR.properties");

// create properties object
//Properties p = new Properties();

//p.load(reader);


//println p.getProperty('pathResultFile')

//println p.getProperty('pathLogFile')

