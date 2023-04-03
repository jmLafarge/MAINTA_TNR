package my


import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import internal.GlobalVariable

public class Result {

	private static String RES_MODELFILENAME = 'MODELE_RESULTATS_TNR.xlsx'
	private static String RES_FILENAME = '_TNR.xlsx'

	private static String RES_RESUMESHEETNAME ='RESUME'
	private static String RES_RESULTSHEETNAME ='RESULTATS'

	private static String resulFileName = this.openResultFile()
	private static XSSFWorkbook book
	private static Sheet shRESUM
	private static Sheet shRESULT

	private static int lineNumberSTART = 2	// casDeTest first line

	private static int nextLineNumber  = 2 // ligne en cours
	private static int firstLineDETAIL = 0
	private static String statusDETAIL = ''
	private static String previousStatus =''

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

	private static CellStyle cellStyle_RESULT_STEP
	private static CellStyle cellStyle_RESULT_CDT
	private static CellStyle cellStyle_RESULT_STEPNBR
	private static CellStyle cellStyle_RESULT_STEPPASS
	private static CellStyle cellStyle_RESULT_STEPFAIL
	private static CellStyle cellStyle_RESULT_STEPWARNING
	private static CellStyle cellStyle_RESULT_STEPERROR
	private static CellStyle cellStyle_RESULT_STEPGRP
	private static CellStyle cellStyle_RESULT_STEPACTION
	private static CellStyle cellStyle_RESULT_STEPBLOCK
	private static CellStyle cellStyle_RESULT_STEPLOOP
	private static CellStyle cellStyle_RESULT_SUBSTEP
	private static CellStyle cellStyle_RESULT_STEPDETAIL

	private static Date startDateTNR
	private static int totalPASS = 0
	private static int totalWARNING = 0
	private static int totalFAIL = 0
	private static int totalERROR = 0

	private static int totalStepPASS = 0
	private static int totalStepWARNING = 0
	private static int totalStepFAIL = 0
	private static int totalStepERROR = 0

	private static String browserName =''
	private static String maintaVersion =''



	private static groupDetail(String status='') {

		if (status == 'DETAIL') {
			if (this.firstLineDETAIL == 0) {
				//début du DETAIL
				this.firstLineDETAIL = this.nextLineNumber
				this.statusDETAIL = this.previousStatus
			}

		}else if (this.firstLineDETAIL != 0){
			//fin du DETAIL
			//boolean collapse = !(this.statusDETAIL in ['WARNING', 'FAIL', 'ERROR'])
			boolean collapse=true

			this.shRESULT.setRowSumsBelow(false)
			this.shRESULT.groupRow(this.firstLineDETAIL, this.nextLineNumber-1)
			this.shRESULT.setRowGroupCollapsed(this.firstLineDETAIL, collapse)
			this.firstLineDETAIL=0
		}
		this.previousStatus=status
	}




	public static addStep(Date date, String msg, String status) {

		Row row = this.shRESULT.createRow(this.nextLineNumber)

		int rowResult = 2


		this.groupDetail(status)


		switch (status) {

			case 'PASS':
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),this.cellStyle_RESULT_STEP)
				my.XLS.writeCell(row,1,msg,this.cellStyle_RESULT_STEPPASS)
				my.XLS.writeCell(row,2,'PASS',this.cellStyle_RESULT_STEPPASS)

				break
			case 'WARNING':
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),this.cellStyle_RESULT_STEP)
				my.XLS.writeCell(row,1,msg,this.cellStyle_RESULT_STEPWARNING)
				my.XLS.writeCell(row,2,'WARNING',this.cellStyle_RESULT_STEPWARNING)
				break
			case 'FAIL':
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),this.cellStyle_RESULT_STEP)
				my.XLS.writeCell(row,1,msg,this.cellStyle_RESULT_STEPFAIL)
				my.XLS.writeCell(row,2,'FAIL',this.cellStyle_RESULT_STEPFAIL)
				break
			case 'ERROR':
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),this.cellStyle_RESULT_STEP)
				my.XLS.writeCell(row,1,msg,this.cellStyle_RESULT_STEPERROR)
				my.XLS.writeCell(row,2,'ERROR',this.cellStyle_RESULT_STEPERROR)
				break
			case 'STEPLOOP':
				my.XLS.writeCell(row,1,msg,this.cellStyle_RESULT_STEPLOOP)

				break
			case 'STEPGRP':
				my.XLS.writeCell(row,1,msg,this.cellStyle_RESULT_STEPGRP)
				my.XLS.writeCell(row,2,'',this.cellStyle_RESULT_STEPGRP)

				break

			case 'STEPACTION':
				my.XLS.writeCell(row,1,msg,this.cellStyle_RESULT_STEPACTION)
				my.XLS.writeCell(row,2,'',this.cellStyle_RESULT_STEPACTION)

				break

			case 'STEPBLOCK':
				my.XLS.writeCell(row,1,(' '+msg+' ').center(70, '-'),this.cellStyle_RESULT_STEPBLOCK)
				my.XLS.writeCell(row,2,'',this.cellStyle_RESULT_STEPBLOCK)

				break

			case 'SUBSTEP':
				my.XLS.writeCell(row,1,msg,this.cellStyle_RESULT_SUBSTEP)

				break
			case 'INFO':
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),this.cellStyle_RESULT_STEPDETAIL)
				my.XLS.writeCell(row,1,msg,this.cellStyle_RESULT_STEPDETAIL)
				my.XLS.writeCell(row,2,'INFO',this.cellStyle_RESULT_STEPDETAIL)

				break
			case 'DETAIL':
				my.XLS.writeCell(row,1,' - '+msg,this.cellStyle_RESULT_STEPDETAIL)

				break
			default :
				println "*********************** Result.addStep, status inconnu $status"
		}

		this.write()

		this.nextLineNumber ++

	}


	public static addStartCasDeTest(String casDeTestName, Date start) {

		Row row = this.shRESULT.createRow(this.lineNumberSTART)

		my.XLS.writeCell(row,0,start.format('yyyy-MM-dd HH:mm:ss.SSS'),this.cellStyle_RESULT_CDT)
		my.XLS.writeCell(row,1,casDeTestName,this.cellStyle_RESULT_CDT)
		my.XLS.writeCell(row,8,start,this.cellStyle_time)

		this.write()

		nextLineNumber=this.lineNumberSTART+1
	}




	public static addEndCasDeTest(String casDeTestName, Map status, Date start, Date stop) {


		Row row = this.shRESULT.getRow(this.lineNumberSTART)

		int rowResult = 2

		this.groupDetail()

		boolean collapse = true //false

		if (status.ERROR !=0 ) {

			my.XLS.writeCell(row,rowResult,'ERROR',this.cellStyle_ERROR)
			this.cellStyle_RESULT_CDT=this.cellStyle_ERROR
			this.totalERROR++
		}else if (status.FAIL != 0 ) {
			my.XLS.writeCell(row,rowResult,'FAIL',this.cellStyle_FAIL)
			this.cellStyle_RESULT_CDT=this.cellStyle_FAIL
			this.totalFAIL++
		}else if (status.WARNING != 0 ) {
			my.XLS.writeCell(row,rowResult,'WARNING',this.cellStyle_WARNING)
			this.cellStyle_RESULT_CDT=this.cellStyle_WARNING
			this.totalWARNING++
		}else {
			my.XLS.writeCell(row,rowResult,'PASS',this.cellStyle_PASS)
			this.cellStyle_RESULT_CDT=this.cellStyle_PASS
			collapse = true
			this.totalPASS++
		}

		my.XLS.writeCell(row,0,start.format('yyyy-MM-dd HH:mm:ss.SSS'),this.cellStyle_RESULT_CDT)
		my.XLS.writeCell(row,1,casDeTestName,this.cellStyle_RESULT_CDT)

		int total = status.PASS + status.WARNING + status.FAIL + status.ERROR

		this.totalStepPASS += status.PASS
		this.totalStepWARNING += status.WARNING
		this.totalStepFAIL += status.FAIL
		this.totalStepERROR += status.ERROR

		my.XLS.writeCell(row,3,total,this.cellStyle_RESULT_STEPNBR)

		if (status.PASS!=0) {
			my.XLS.writeCell(row,4,status.PASS,this.cellStyle_RESULT_STEPNBR)
		}

		if (status.WARNING!=0) {
			my.XLS.writeCell(row,5,status.WARNING,this.cellStyle_RESULT_STEPNBR)
		}

		if (status.FAIL!=0) {
			my.XLS.writeCell(row,6,status.FAIL,this.cellStyle_RESULT_STEPNBR)
		}

		if (status.ERROR!=0) {
			my.XLS.writeCell(row,7,status.ERROR,this.cellStyle_RESULT_STEPNBR)
		}


		my.XLS.writeCell(row,9,stop,this.cellStyle_time)
		my.XLS.writeCell(row,10,my.Tools.getDuration(start, stop),this.cellStyle_duration)


		this.shRESULT.setRowSumsBelow(false)
		this.shRESULT.groupRow(this.lineNumberSTART+1, this.nextLineNumber-1)
		this.shRESULT.setRowGroupCollapsed(this.lineNumberSTART+1, collapse)


		this.write()

		this.lineNumberSTART=nextLineNumber

	}







	public static addBrowserInfo(String browser, String version) {


		this.browserName = browser
		my.XLS.writeCell(this.shRESUM.getRow(11),1,browser)
		my.XLS.writeCell(this.shRESUM.getRow(12),1,version)

		this.write()
	}





	public static addStartInfo(String text) {


		my.XLS.writeCell(this.shRESUM.getRow(1),3,text)

		this.startDateTNR = new Date()

		my.XLS.writeCell(this.shRESUM.getRow(1),1,this.startDateTNR,this.cellStyle_date)
		my.XLS.writeCell(this.shRESUM.getRow(3),1,this.startDateTNR,this.cellStyle_time)


		my.XLS.writeCell(this.shRESUM.getRow(8),1,System.getProperty("os.name"))
		my.XLS.writeCell(this.shRESUM.getRow(9),1,System.getProperty("os.version"))
		my.XLS.writeCell(this.shRESUM.getRow(10),1,System.getProperty("os.arch"))

		this.maintaVersion = my.SQL.getMaintaVersion()
		my.XLS.writeCell(this.shRESUM.getRow(13),1,this.maintaVersion)
		my.XLS.writeCell(this.shRESUM.getRow(14),1,GlobalVariable.BDD_URL)

		this.write()
	}


	public static addEndInfo() {

		Date endDate = new Date()

		my.XLS.writeCell(this.shRESUM.getRow(4),1,endDate,this.cellStyle_time)

		my.XLS.writeCell(this.shRESUM.getRow(5),1,my.Tools.getDuration(this.startDateTNR, endDate),this.cellStyle_duration)

		Row row = this.shRESUM.getRow(17)

		my.XLS.writeCell(row,1,this.totalPASS + this.totalWARNING + this.totalFAIL + this.totalERROR,this.cellStyle_TOT)
		my.XLS.writeCell(row,2,this.totalPASS,this.cellStyle_PASSTOT)
		my.XLS.writeCell(row,3,this.totalWARNING,this.cellStyle_WARNINGTOT)
		my.XLS.writeCell(row,4,this.totalFAIL,this.cellStyle_FAILTOT)
		my.XLS.writeCell(row,5,this.totalERROR,this.cellStyle_ERRORTOT)

		row = this.shRESUM.getRow(18)

		my.XLS.writeCell(row,1,this.totalStepPASS + this.totalStepWARNING + this.totalStepFAIL + this.totalStepERROR,this.cellStyle_STEPTOT)
		my.XLS.writeCell(row,2,this.totalStepPASS,this.cellStyle_STEPTOT)
		my.XLS.writeCell(row,3,this.totalStepWARNING,this.cellStyle_STEPTOT)
		my.XLS.writeCell(row,4,this.totalStepFAIL,this.cellStyle_STEPTOT)
		my.XLS.writeCell(row,5,this.totalStepERROR,this.cellStyle_STEPTOT)

		this.write()
	}



	private static String createFileByCopy() {

		Path source = Paths.get(my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + this.RES_MODELFILENAME)

		String dateFile = new Date().format("yyyyMMdd_HHmmss")

		//Create folder if not exist
		File dir = new File(my.PropertiesReader.getMyProperty('RES_PATH'))
		if (!dir.exists()) dir.mkdirs()

		String resFullName = my.PropertiesReader.getMyProperty('RES_PATH') + File.separator + dateFile + this.RES_FILENAME


		Path target = Paths.get(resFullName)

		Files.copy(source, target)

		return 	resFullName
	}





	private static String openResultFile() {


		String resFullName = this.createFileByCopy()

		this.book = my.XLS.open(resFullName)

		// set tab (sheet)
		this.shRESUM = book.getSheet(this.RES_RESUMESHEETNAME)
		this.shRESULT = book.getSheet(this.RES_RESULTSHEETNAME)

		//set style
		this.createHelper = book.getCreationHelper()
		this.cellStyle_time = this.book.createCellStyle()
		this.cellStyle_time.setDataFormat( this.createHelper.createDataFormat().getFormat("HH:mm:ss"))

		this.cellStyle_duration = this.book.createCellStyle()
		this.cellStyle_duration.setAlignment(HorizontalAlignment.RIGHT)


		this.cellStyle_date = this.book.createCellStyle()
		this.cellStyle_date.setDataFormat( this.createHelper.createDataFormat().getFormat("dd/MM/yyyy"))

		def fontCDT = this.book.createFont()
		fontCDT.setFontName('Arial')
		fontCDT.setFontHeightInPoints(10 as short)
		fontCDT.setBold(true)


		this.cellStyle_PASS = this.book.createCellStyle()
		this.cellStyle_PASS.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.index)
		this.cellStyle_PASS.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_PASS.setFont(fontCDT)

		this.cellStyle_WARNING = this.book.createCellStyle()
		this.cellStyle_WARNING.setFillForegroundColor(IndexedColors.YELLOW.index)
		this.cellStyle_WARNING.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_WARNING.setFont(fontCDT)

		this.cellStyle_FAIL = this.book.createCellStyle()
		this.cellStyle_FAIL.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index)
		this.cellStyle_FAIL.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_FAIL.setFont(fontCDT)

		this.cellStyle_ERROR = this.book.createCellStyle()
		this.cellStyle_ERROR.setFillForegroundColor(IndexedColors.ORANGE.index)
		this.cellStyle_ERROR.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_ERROR.setFont(fontCDT)

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



		this.cellStyle_STEPTOT = this.book.createCellStyle()
		this.cellStyle_STEPTOT.setFillPattern(FillPatternType.NO_FILL)
		this.cellStyle_STEPTOT.setVerticalAlignment(VerticalAlignment.CENTER)
		this.cellStyle_STEPTOT.setAlignment(HorizontalAlignment.RIGHT)




		this.cellStyle_RESULT_STEPNBR = this.book.createCellStyle()
		this.cellStyle_RESULT_STEPNBR.setFillPattern(FillPatternType.NO_FILL)
		this.cellStyle_RESULT_STEPNBR.setAlignment(HorizontalAlignment.RIGHT)


		this.cellStyle_RESULT_CDT = this.book.createCellStyle()



		def fontStep = this.book.createFont()
		fontStep.setFontName('Arial')
		fontStep.setFontHeightInPoints(10 as short)
		fontStep.setItalic(true)

		this.cellStyle_RESULT_STEP = this.book.createCellStyle()
		this.cellStyle_RESULT_STEP.setFont(fontStep)

		this.cellStyle_RESULT_STEPPASS = this.book.createCellStyle()
		this.cellStyle_RESULT_STEPPASS.cloneStyleFrom(this.cellStyle_RESULT_STEP)
		this.cellStyle_RESULT_STEPPASS.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_RESULT_STEPPASS.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index)

		this.cellStyle_RESULT_STEPWARNING = this.book.createCellStyle()
		this.cellStyle_RESULT_STEPWARNING.cloneStyleFrom(this.cellStyle_RESULT_STEP)
		this.cellStyle_RESULT_STEPWARNING.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_RESULT_STEPWARNING.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index)

		this.cellStyle_RESULT_STEPFAIL = this.book.createCellStyle()
		this.cellStyle_RESULT_STEPFAIL.cloneStyleFrom(this.cellStyle_RESULT_STEP)
		this.cellStyle_RESULT_STEPFAIL.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_RESULT_STEPFAIL.setFillForegroundColor(IndexedColors.TAN.index)

		this.cellStyle_RESULT_STEPERROR = this.book.createCellStyle()
		this.cellStyle_RESULT_STEPERROR.cloneStyleFrom(this.cellStyle_RESULT_STEP)
		this.cellStyle_RESULT_STEPERROR.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_RESULT_STEPERROR.setFillForegroundColor(IndexedColors.CORAL.index)

		def fontStepGRP = this.book.createFont()
		fontStepGRP.setFontName('Arial')
		fontStepGRP.setFontHeightInPoints(10 as short)
		fontStepGRP.setItalic(true)

		this.cellStyle_RESULT_STEPGRP = this.book.createCellStyle()
		this.cellStyle_RESULT_STEPGRP.setFont(fontStepGRP)
		this.cellStyle_RESULT_STEPGRP.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_RESULT_STEPGRP.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index)

		this.cellStyle_RESULT_STEPACTION = this.book.createCellStyle()
		this.cellStyle_RESULT_STEPACTION.setFont(fontStepGRP)
		this.cellStyle_RESULT_STEPACTION.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		this.cellStyle_RESULT_STEPACTION.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index)


		this.cellStyle_RESULT_STEPBLOCK = this.book.createCellStyle()
		this.cellStyle_RESULT_STEPBLOCK.setFont(fontStep)


		def fontStepLOOP = this.book.createFont()
		fontStepLOOP.setFontName('Arial')
		fontStepLOOP.setFontHeightInPoints(10 as short)
		fontStepLOOP.setItalic(true)
		fontStepLOOP.setBold(true)
		fontStepLOOP.setColor(IndexedColors.BLUE.getIndex())

		this.cellStyle_RESULT_STEPLOOP = this.book.createCellStyle()
		this.cellStyle_RESULT_STEPLOOP.setFont(fontStepLOOP)

		def fontStepSUB = this.book.createFont()
		fontStepSUB.setFontName('Arial')
		fontStepSUB.setFontHeightInPoints(10 as short)
		fontStepSUB.setItalic(true)
		fontStepSUB.setColor(IndexedColors.DARK_BLUE.getIndex())

		this.cellStyle_RESULT_SUBSTEP = this.book.createCellStyle()
		this.cellStyle_RESULT_SUBSTEP.setFont(fontStepSUB)

		def fontStepDetail = this.book.createFont()
		fontStepDetail.setFontName('Arial')
		fontStepDetail.setFontHeightInPoints(10 as short)
		fontStepDetail.setItalic(true)
		fontStepDetail.setColor(IndexedColors.GREY_80_PERCENT.getIndex())

		this.cellStyle_RESULT_STEPDETAIL = this.book.createCellStyle()
		this.cellStyle_RESULT_STEPDETAIL.setFont(fontStepDetail)


		return resFullName

	}


	private static write(){

		OutputStream fileOut = new FileOutputStream(this.resulFileName)

		this.book.write(fileOut);
	}

	private static close(){

		this.book.close()

		def file = new File(this.resulFileName)
		String newName = file.getParent()+ File.separator +file.getName().replace('.xlsx','')
		newName= newName + '_MSSQL_'+ this.browserName.split(' ')[0] + '_Mainta_' + this.maintaVersion.replace(' ', '_') + '.xlsx'
		file.renameTo(newName)

	}




} // end of class