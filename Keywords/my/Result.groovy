package my

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import my.Log.STATUS


public class Result {


	private static String resulFileName = this.openResultFile()
	private static XSSFWorkbook book
	private static Sheet shRESUM
	private static Sheet shRESULT

	private static int lineNumber = 1	// casDeTest first line

	private static CreationHelper createHelper
	private static CellStyle cellStyle_time
	private static CellStyle cellStyle_duration
	private static CellStyle cellStyle_date
	private static CellStyle cellStyle_PASS
	private static CellStyle cellStyle_FAIL
	private static CellStyle cellStyle_ERR

	private static Date startDate
	private static int totalPASS = 0
	private static int totalFAIL = 0
	private static int totalERR = 0




	static public addCasDeTest(String casDeTestName, STATUS result, Date start, stop) {

		Row row = this.shRESULT.createRow(this.lineNumber)
		Cell cell

		row.createCell(0).setCellValue(casDeTestName)

		cell = row.createCell(1)
		cell.setCellValue(result.toString())
		switch (result) {

			case STATUS.PASS :
				cell.setCellStyle(this.cellStyle_PASS)
				break
			case STATUS.FAIL :
				cell.setCellStyle(this.cellStyle_FAIL)
				break
			case STATUS.ERROR :
				cell.setCellStyle(this.cellStyle_ERR)
				break
			default :
				my.Log.addDEBUG("addCasDeTest() result inconnu = $result")
		}


		cell = row.createCell(2)
		cell.setCellValue(start)
		cell.setCellStyle(this.cellStyle_time)

		cell = row.createCell(3)
		cell.setCellValue(stop)
		cell.setCellStyle(this.cellStyle_time)

		cell = row.createCell(4)
		cell.setCellValue(my.Tools.getDuration(start, stop))
		cell.setCellStyle(this.cellStyle_duration)

		this.write()

		this.lineNumber ++

		switch (result) {

			case 'PASS': this.totalPASS++ ; break
			case 'FAIL': this.totalFAIL++ ; break
			case 'ERROR': this.totalERR++ ; break
			default : my.Log.addDEBUG("addCasDeTest() result inconnu : $result")

		}

		//my.Log.addINFO( 'resulFileName : ' + this.resulFileName)
	}



	static public addStartInfo() {

		this.startDate = new Date()

		Cell cell = this.shRESUM.getRow(1).getCell(1)
		cell.setCellValue(this.startDate)
		cell.setCellStyle(this.cellStyle_date)

		cell = this.shRESUM.getRow(3).getCell(1)
		cell.setCellValue(this.startDate)
		cell.setCellStyle(this.cellStyle_time)

		this.write()
	}


	static public addEndInfo() {

		Date endDate = new Date()

		this.writeCell(this.shRESUM.getRow(4),1,endDate,this.cellStyle_time)

		this.writeCell(this.shRESUM.getRow(5),1,my.Tools.getDuration(this.startDate, endDate),this.cellStyle_duration)

		Row row = this.shRESUM.getRow(15)

		this.writeCell(row,1,this.totalPASS+ this.totalFAIL+this.totalERR)

		this.writeCell(row,2,this.totalPASS)

		this.writeCell(row,3,this.totalFAIL)

		this.writeCell(row,4,this.totalERR)

		this.write()
	}



	private static writeCell(Row row, int colIdx,def val,CellStyle cellStyle = null) {

		Cell cell = row.getCell(colIdx)

		if (cell == null) { row.createCell(colIdx)}
		cell = row.getCell(colIdx)
		cell.setCellValue(val)

		if (cellStyle != null) { cell.setCellStyle(cellStyle)}

	}



	private static String createFileByCopy() {


		Path source = Paths.get(my.PropertiesReader.getMyProperty('RES_PATH') + File.separator + my.PropertiesReader.getMyProperty('RES_MODELFILENAME'))

		String dateFile = new Date().format("yyyyMMdd_HHmmss")
		String resFullName = my.PropertiesReader.getMyProperty('RES_PATH') + File.separator + dateFile + my.PropertiesReader.getMyProperty('RES_FILENAME')

		Path target = Paths.get(resFullName)

		Files.copy(source, target)

		return 	resFullName
	}





	private static String openResultFile() {

		String resFullName = this.createFileByCopy()

		this.book = my.XLS.open(resFullName)

		// set tab (sheet)
		this.shRESUM = book.getSheet(my.PropertiesReader.getMyProperty('RES_RESUMESHEETNAME'))
		this.shRESULT = book.getSheet(my.PropertiesReader.getMyProperty('RES_RESULTSHEETNAME'))

		//set style
		this.createHelper = book.getCreationHelper()
		this.cellStyle_time = this.book.createCellStyle()
		this.cellStyle_time.setDataFormat( this.createHelper.createDataFormat().getFormat("HH:mm:ss"))

		this.cellStyle_duration = this.book.createCellStyle()
		this.cellStyle_duration.setAlignment(HorizontalAlignment.RIGHT)

		this.cellStyle_date = this.book.createCellStyle()
		this.cellStyle_date.setDataFormat( this.createHelper.createDataFormat().getFormat("dd/MM/yyyy"))

		this.cellStyle_PASS = this.book.createCellStyle()
		this.cellStyle_PASS.setFillForegroundColor(IndexedColors.BRIGHT_GREEN1.index)
		this.cellStyle_PASS.setFillPattern(FillPatternType.SOLID_FOREGROUND)

		this.cellStyle_FAIL = this.book.createCellStyle()
		this.cellStyle_FAIL.setFillForegroundColor(IndexedColors.YELLOW1.index)
		this.cellStyle_FAIL.setFillPattern(FillPatternType.SOLID_FOREGROUND)

		this.cellStyle_ERR = this.book.createCellStyle()
		this.cellStyle_ERR.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex())
		this.cellStyle_ERR.setFillPattern(FillPatternType.SOLID_FOREGROUND)


		return resFullName

	}


	private static write(){

		OutputStream fileOut = new FileOutputStream(this.resulFileName)

		this.book.write(fileOut);


	}


} // end of class