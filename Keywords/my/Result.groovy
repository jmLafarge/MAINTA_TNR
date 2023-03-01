package my


import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

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
	private static CellStyle cellStyle_WARNING
	private static CellStyle cellStyle_FAIL
	private static CellStyle cellStyle_ERROR
	
	private static CellStyle cellStyle_TOT
	private static CellStyle cellStyle_PASSTOT
	private static CellStyle cellStyle_WARNINGTOT
	private static CellStyle cellStyle_FAILTOT
	private static CellStyle cellStyle_ERRORTOT
	private static CellStyle cellStyle_STEPTOT
	
	private static CellStyle cellStyle_status

	private static Date startDate
	private static int totalPASS = 0
	private static int totalWARNING = 0
	private static int totalFAIL = 0
	private static int totalERROR = 0
	
	private static int totalStepPASS = 0
	private static int totalStepWARNING = 0
	private static int totalStepFAIL = 0
	private static int totalStepERROR = 0



	static public addCasDeTest(String casDeTestName, Map status, Date start, stop) {

		Row row = this.shRESULT.createRow(this.lineNumber)

		my.XLS.writeCell(row,0,casDeTestName)

		if (status.ERROR !=0 ) {

			my.XLS.writeCell(row,1,'ERROR',this.cellStyle_ERROR)
			this.totalERROR++
		}else if (status.FAIL != 0 ) {
			my.XLS.writeCell(row,1,'FAIL',this.cellStyle_FAIL)
			this.totalFAIL++
		}else if (status.WARNING != 0 ) {
			my.XLS.writeCell(row,1,'WARNING',this.cellStyle_WARNING)
			this.totalWARNING++
		}else {
			my.XLS.writeCell(row,1,'PASS',this.cellStyle_PASS)
			this.totalPASS++
		}

		int total = status.PASS + status.WARNING + status.FAIL + status.ERROR
		
		this.totalStepPASS += status.PASS
		this.totalStepWARNING += status.WARNING
		this.totalStepFAIL += status.FAIL
		this.totalStepERROR += status.ERROR

		if (status.PASS!=0) {
			my.XLS.writeCell(row,2,"${status.PASS} / $total",this.cellStyle_status)
		}

		if (status.WARNING!=0) {
			my.XLS.writeCell(row,3,"${status.WARNING} / $total",this.cellStyle_status)
		}

		if (status.FAIL!=0) {
			my.XLS.writeCell(row,4,"${status.FAIL} / $total",this.cellStyle_status)
		}

		if (status.ERROR!=0) {
			my.XLS.writeCell(row,5,"${status.ERROR} / $total",this.cellStyle_status)
		}

		my.XLS.writeCell(row,6,start,this.cellStyle_time)
		my.XLS.writeCell(row,7,stop,this.cellStyle_time)
		my.XLS.writeCell(row,8,my.Tools.getDuration(start, stop),this.cellStyle_duration)
		
		
		this.write()

		this.lineNumber ++

	}



	static public addStartInfo() {

		this.startDate = new Date()
		
		my.XLS.writeCell(this.shRESUM.getRow(1),1,this.startDate,this.cellStyle_date)
		my.XLS.writeCell(this.shRESUM.getRow(3),1,this.startDate,this.cellStyle_time)
		
		this.write()
	}


	static public addEndInfo() {

		Date endDate = new Date()

		my.XLS.writeCell(this.shRESUM.getRow(4),1,endDate,this.cellStyle_time)

		my.XLS.writeCell(this.shRESUM.getRow(5),1,my.Tools.getDuration(this.startDate, endDate),this.cellStyle_duration)

		Row row = this.shRESUM.getRow(15)

		my.XLS.writeCell(row,1,this.totalPASS + this.totalWARNING + this.totalFAIL + this.totalERROR,this.cellStyle_TOT)
		my.XLS.writeCell(row,2,this.totalPASS,this.cellStyle_PASSTOT)
		my.XLS.writeCell(row,3,this.totalWARNING,this.cellStyle_WARNINGTOT)
		my.XLS.writeCell(row,4,this.totalFAIL,this.cellStyle_FAILTOT)
		my.XLS.writeCell(row,5,this.totalERROR,this.cellStyle_ERRORTOT)
		
		row = this.shRESUM.getRow(16)
		
		my.XLS.writeCell(row,1,this.totalStepPASS + this.totalStepWARNING + this.totalStepFAIL + this.totalStepERROR,this.cellStyle_STEPTOT)
		my.XLS.writeCell(row,2,this.totalStepPASS,this.cellStyle_STEPTOT)
		my.XLS.writeCell(row,3,this.totalStepWARNING,this.cellStyle_STEPTOT)
		my.XLS.writeCell(row,4,this.totalStepFAIL,this.cellStyle_STEPTOT)
		my.XLS.writeCell(row,5,this.totalStepERROR,this.cellStyle_STEPTOT)

		this.write()
	}



	private static String createFileByCopy() {

		Path source = Paths.get(my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + my.PropertiesReader.getMyProperty('RES_MODELFILENAME'))

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
		this.cellStyle_PASS.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.index)
		this.cellStyle_PASS.setFillPattern(FillPatternType.SOLID_FOREGROUND)

		this.cellStyle_WARNING = this.book.createCellStyle()
		this.cellStyle_WARNING.setFillForegroundColor(IndexedColors.YELLOW.index)
		this.cellStyle_WARNING.setFillPattern(FillPatternType.SOLID_FOREGROUND)

		this.cellStyle_FAIL = this.book.createCellStyle()
		this.cellStyle_FAIL.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index)
		this.cellStyle_FAIL.setFillPattern(FillPatternType.SOLID_FOREGROUND)

		this.cellStyle_ERROR = this.book.createCellStyle()
		this.cellStyle_ERROR.setFillForegroundColor(IndexedColors.ORANGE.index)
		this.cellStyle_ERROR.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		
		def fontTOT = this.book.createFont()
		fontTOT.setFontName('Arial')
		fontTOT.setBold(true)
		fontTOT.setFontHeightInPoints(14 as short)

		this.cellStyle_TOT = this.book.createCellStyle()
		this.cellStyle_TOT.setFillPattern(FillPatternType.NO_FILL)
		this.cellStyle_TOT.setVerticalAlignment(VerticalAlignment.CENTER)
		this.cellStyle_TOT.setAlignment(HorizontalAlignment.RIGHT)
		this.cellStyle_TOT.setFont(fontTOT)
		
		this.cellStyle_PASSTOT = this.book.createCellStyle()
		this.cellStyle_PASSTOT.cloneStyleFrom(this.cellStyle_TOT)
		this.cellStyle_PASSTOT.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_PASSTOT.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.index)

		this.cellStyle_WARNINGTOT = this.book.createCellStyle()
		this.cellStyle_WARNINGTOT.cloneStyleFrom(this.cellStyle_PASSTOT)
		this.cellStyle_WARNINGTOT.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_WARNINGTOT.setFillForegroundColor(IndexedColors.YELLOW.index)

		this.cellStyle_FAILTOT = this.book.createCellStyle()
		this.cellStyle_FAILTOT.cloneStyleFrom(this.cellStyle_TOT)
		this.cellStyle_FAILTOT.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_FAILTOT.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index)

		this.cellStyle_ERRORTOT = this.book.createCellStyle()
		this.cellStyle_ERRORTOT.cloneStyleFrom(this.cellStyle_TOT)
		this.cellStyle_ERRORTOT.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_ERRORTOT.setFillForegroundColor(IndexedColors.ORANGE.index)
		
		def font = this.book.createFont()
		font.setFontName('Arial')
		font.setFontHeightInPoints(10 as short)
		
		this.cellStyle_STEPTOT = this.book.createCellStyle()
		this.cellStyle_STEPTOT.setFillPattern(FillPatternType.NO_FILL)
		this.cellStyle_STEPTOT.setVerticalAlignment(VerticalAlignment.CENTER)
		this.cellStyle_STEPTOT.setAlignment(HorizontalAlignment.RIGHT)
		//this.cellStyle_STEPTOT.setFont(font)
		
		this.cellStyle_status = this.book.createCellStyle()
		this.cellStyle_status.setAlignment(HorizontalAlignment.RIGHT)

		return resFullName

	}


	private static write(){

		OutputStream fileOut = new FileOutputStream(this.resulFileName)

		this.book.write(fileOut);


	}


} // end of class