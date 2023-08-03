package my.result

import groovy.transform.CompileStatic

import org.apache.poi.common.usermodel.HyperlinkType
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

@CompileStatic
public class CELLStyleFactory {


	public CreationHelper createHelper

	public CellStyle cellStyle_time
	public CellStyle cellStyle_duration
	public CellStyle cellStyle_durationResultat
	public CellStyle cellStyle_date
	public CellStyle cellStyle_dateResultat
	public CellStyle cellStyle_hyperlink

	public CellStyle cellStyle_PASS
	public CellStyle cellStyle_WARNING
	public CellStyle cellStyle_FAIL
	public CellStyle cellStyle_ERROR

	public CellStyle cellStyle_TOT
	public CellStyle cellStyle_PASSTOT
	public CellStyle cellStyle_WARNINGTOT
	public CellStyle cellStyle_FAILTOT
	public CellStyle cellStyle_ERRORTOT
	public CellStyle cellStyle_STEPTOT

	public CellStyle cellStyle_RESULT_STEP
	public CellStyle cellStyle_RESULT_CDT
	public CellStyle cellStyle_RESULT_STEPNBR
	public CellStyle cellStyle_RESULT_STEPPASS
	public CellStyle cellStyle_RESULT_STEPFAIL
	public CellStyle cellStyle_RESULT_STEPWARNING
	public CellStyle cellStyle_RESULT_STEPERROR
	public CellStyle cellStyle_RESULT_STEPGRP
	public CellStyle cellStyle_RESULT_STEPACTION
	public CellStyle cellStyle_RESULT_STEPBLOCK
	public CellStyle cellStyle_RESULT_STEPLOOP
	public CellStyle cellStyle_RESULT_SUBSTEP
	public CellStyle cellStyle_RESULT_STEPDETAIL

	private XSSFWorkbook book

	private Font fontCDT
	private Font fontStep
	private Font fontStepGRP





	public CELLStyleFactory(XSSFWorkbook wb) {

		book = wb
		createHelper = book.getCreationHelper()

		createCellStyle_fontCDT()
		createCellStyle_fontStep()
		createCellStyle_fontStepGRP()


		createCellStyle_time()
		createCellStyle_duration()
		createCellStyle_durationResultat()
		createCellStyle_date()
		createCellStyle_dateResultat()
		createCellStyle_hyperlink()
		createCellStyle_PASS()
		createCellStyle_WARNING()
		createCellStyle_FAIL()
		createCellStyle_ERROR()
		createCellStyle_TOT()
		createCellStyle_PASSTOT()
		createCellStyle_WARNINGTOT()
		createCellStyle_FAILTOT()
		createCellStyle_ERRORTOT()
		createCellStyle_STEPTOT()
		createCellStyle_RESULT_STEP()
		createCellStyle_RESULT_CDT()
		createCellStyle_RESULT_STEPNBR()
		createCellStyle_RESULT_STEPPASS()
		createCellStyle_RESULT_STEPFAI()
		createCellStyle_RESULT_STEPWARNING()
		createCellStyle_RESULT_STEPERROR()
		createCellStyle_RESULT_STEPGRP()
		createCellStyle_RESULT_STEPACTION()
		createCellStyle_RESULT_STEPBLOCK()
		createCellStyle_RESULT_STEPLOOP()
		createCellStyle_RESULT_SUBSTEP()
		createCellStyle_RESULT_STEPDETAIL()
	}






	private createCellStyle_time() {

		cellStyle_time = book.createCellStyle()
		cellStyle_time.setDataFormat( createHelper.createDataFormat().getFormat("HH:mm:ss"))
		cellStyle_time.setAlignment(HorizontalAlignment.RIGHT)
		cellStyle_time.setVerticalAlignment(VerticalAlignment.CENTER)
	}




	private createCellStyle_duration() {

		cellStyle_duration = book.createCellStyle()
		cellStyle_duration.setAlignment(HorizontalAlignment.RIGHT)
		cellStyle_duration.setVerticalAlignment(VerticalAlignment.CENTER)
	}

	
	private createCellStyle_durationResultat() {

		cellStyle_durationResultat = book.createCellStyle()
		cellStyle_durationResultat.setAlignment(HorizontalAlignment.LEFT)
		cellStyle_durationResultat.setVerticalAlignment(VerticalAlignment.CENTER)
	}



	private createCellStyle_date() {

		cellStyle_date = book.createCellStyle()
		cellStyle_date.setDataFormat( createHelper.createDataFormat().getFormat("dd/MM/yyyy"))
		cellStyle_date.setAlignment(HorizontalAlignment.LEFT)
		cellStyle_date.setVerticalAlignment(VerticalAlignment.CENTER)
	}

	private createCellStyle_dateResultat() {
		def font = book.createFont()
		font.setFontName('Arial')
		font.setFontHeightInPoints(14 as short)

		cellStyle_dateResultat = book.createCellStyle()
		cellStyle_dateResultat.setDataFormat( createHelper.createDataFormat().getFormat("dd/MM/yyyy"))
		cellStyle_dateResultat.setVerticalAlignment(VerticalAlignment.CENTER)
		cellStyle_dateResultat.setAlignment(HorizontalAlignment.LEFT)
		cellStyle_dateResultat.setFont(font)
	}


	private createCellStyle_hyperlink() {

		def fontHyperLink = book.createFont()
		fontHyperLink.setFontName('Arial')
		fontHyperLink.setFontHeightInPoints(10 as short)
		fontHyperLink.setUnderline(FontUnderline.SINGLE.getByteValue())
		fontHyperLink.setColor(IndexedColors.BLUE.getIndex())

		cellStyle_hyperlink = book.createCellStyle()
		cellStyle_hyperlink.setVerticalAlignment(VerticalAlignment.CENTER)
		cellStyle_hyperlink.setFont(fontHyperLink)
	}




	private createCellStyle_fontCDT() {

		fontCDT = book.createFont()
		fontCDT.setFontName('Arial')
		fontCDT.setFontHeightInPoints(10 as short)
		fontCDT.setBold(true)
	}



	private createCellStyle_PASS() {

		cellStyle_PASS = book.createCellStyle()
		cellStyle_PASS.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.index)
		cellStyle_PASS.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_PASS.setVerticalAlignment(VerticalAlignment.CENTER)

		cellStyle_PASS.setFont(fontCDT)
	}




	private createCellStyle_WARNING() {

		cellStyle_WARNING = book.createCellStyle()
		cellStyle_WARNING.setFillForegroundColor(IndexedColors.YELLOW.index)
		cellStyle_WARNING.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_WARNING.setVerticalAlignment(VerticalAlignment.CENTER)

		cellStyle_WARNING.setFont(fontCDT)
	}




	private createCellStyle_FAIL() {

		cellStyle_FAIL = book.createCellStyle()
		cellStyle_FAIL.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index)
		cellStyle_FAIL.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_FAIL.setVerticalAlignment(VerticalAlignment.CENTER)

		cellStyle_FAIL.setFont(fontCDT)
	}




	private createCellStyle_ERROR() {

		cellStyle_ERROR = book.createCellStyle()
		cellStyle_ERROR.setFillForegroundColor(IndexedColors.ORANGE.index)
		cellStyle_ERROR.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_ERROR.setVerticalAlignment(VerticalAlignment.CENTER)

		cellStyle_ERROR.setFont(fontCDT)
	}






	private createCellStyle_TOT() {

		def fontTOT = book.createFont()
		fontTOT.setFontName('Arial')
		fontTOT.setBold(true)
		fontTOT.setFontHeightInPoints(14 as short)

		cellStyle_TOT = book.createCellStyle()
		cellStyle_TOT.setFillPattern(FillPatternType.NO_FILL)
		cellStyle_TOT.setVerticalAlignment(VerticalAlignment.CENTER)
		cellStyle_TOT.setAlignment(HorizontalAlignment.RIGHT)
		cellStyle_TOT.setVerticalAlignment(VerticalAlignment.CENTER)
		cellStyle_TOT.setFont(fontTOT)
	}




	private createCellStyle_PASSTOT() {

		cellStyle_PASSTOT = book.createCellStyle()
		cellStyle_PASSTOT.cloneStyleFrom(cellStyle_TOT)

		cellStyle_PASSTOT.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_PASSTOT.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.index)
	}




	private createCellStyle_WARNINGTOT() {

		cellStyle_WARNINGTOT = book.createCellStyle()
		cellStyle_WARNINGTOT.cloneStyleFrom(cellStyle_TOT)

		cellStyle_WARNINGTOT.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_WARNINGTOT.setFillForegroundColor(IndexedColors.YELLOW.index)
	}




	private createCellStyle_FAILTOT() {

		cellStyle_FAILTOT = book.createCellStyle()
		cellStyle_FAILTOT.cloneStyleFrom(cellStyle_TOT)

		cellStyle_FAILTOT.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_FAILTOT.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index)
	}




	private createCellStyle_ERRORTOT() {

		cellStyle_ERRORTOT = book.createCellStyle()
		cellStyle_ERRORTOT.cloneStyleFrom(cellStyle_TOT)

		cellStyle_ERRORTOT.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_ERRORTOT.setFillForegroundColor(IndexedColors.ORANGE.index)
	}




	private createCellStyle_STEPTOT() {

		cellStyle_STEPTOT = book.createCellStyle()
		cellStyle_STEPTOT.setFillPattern(FillPatternType.NO_FILL)
		cellStyle_STEPTOT.setVerticalAlignment(VerticalAlignment.CENTER)
		cellStyle_STEPTOT.setAlignment(HorizontalAlignment.RIGHT)
		cellStyle_STEPTOT.setVerticalAlignment(VerticalAlignment.CENTER)
	}




	private createCellStyle_RESULT_STEPNBR() {

		cellStyle_RESULT_STEPNBR = book.createCellStyle()
		cellStyle_RESULT_STEPNBR.setFillPattern(FillPatternType.NO_FILL)
		cellStyle_RESULT_STEPNBR.setAlignment(HorizontalAlignment.RIGHT)
		cellStyle_RESULT_STEPNBR.setVerticalAlignment(VerticalAlignment.CENTER)
	}


	//////////////////////////////////////////////////////////////////////////////////////////// à vérifier le besoin
	private createCellStyle_RESULT_CDT() {

		cellStyle_RESULT_CDT = book.createCellStyle()
	}



	private createCellStyle_fontStep() {

		fontStep = book.createFont()
		fontStep.setFontName('Arial')
		fontStep.setFontHeightInPoints(10 as short)
		fontStep.setItalic(true)
	}




	private createCellStyle_RESULT_STEP() {

		cellStyle_RESULT_STEP = book.createCellStyle()
		cellStyle_RESULT_STEP.setFont(fontStep)
		cellStyle_RESULT_STEP.setVerticalAlignment(VerticalAlignment.CENTER)
	}




	private createCellStyle_RESULT_STEPPASS() {

		cellStyle_RESULT_STEPPASS = book.createCellStyle()
		cellStyle_RESULT_STEPPASS.cloneStyleFrom(cellStyle_RESULT_STEP)

		cellStyle_RESULT_STEPPASS.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_RESULT_STEPPASS.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index)
	}




	private createCellStyle_RESULT_STEPWARNING() {

		cellStyle_RESULT_STEPWARNING = book.createCellStyle()
		cellStyle_RESULT_STEPWARNING.cloneStyleFrom(cellStyle_RESULT_STEP)

		cellStyle_RESULT_STEPWARNING.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_RESULT_STEPWARNING.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index)
	}




	private createCellStyle_RESULT_STEPFAI() {

		cellStyle_RESULT_STEPFAIL = book.createCellStyle()
		cellStyle_RESULT_STEPFAIL.cloneStyleFrom(cellStyle_RESULT_STEP)

		cellStyle_RESULT_STEPFAIL.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_RESULT_STEPFAIL.setFillForegroundColor(IndexedColors.TAN.index)
	}




	private createCellStyle_RESULT_STEPERROR() {

		cellStyle_RESULT_STEPERROR = book.createCellStyle()
		cellStyle_RESULT_STEPERROR.cloneStyleFrom(cellStyle_RESULT_STEP)

		cellStyle_RESULT_STEPERROR.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_RESULT_STEPERROR.setFillForegroundColor(IndexedColors.CORAL.index)
	}




	private createCellStyle_fontStepGRP() {
		def fontStepGRP = book.createFont()
		fontStepGRP.setFontName('Arial')
		fontStepGRP.setFontHeightInPoints(10 as short)
		fontStepGRP.setItalic(true)
	}




	private createCellStyle_RESULT_STEPGRP() {

		cellStyle_RESULT_STEPGRP = book.createCellStyle()
		cellStyle_RESULT_STEPGRP.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_RESULT_STEPGRP.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index)
		cellStyle_RESULT_STEPGRP.setVerticalAlignment(VerticalAlignment.CENTER)
		cellStyle_RESULT_STEPGRP.setFont(fontStepGRP)
	}




	private createCellStyle_RESULT_STEPACTION() {

		cellStyle_RESULT_STEPACTION = book.createCellStyle()
		cellStyle_RESULT_STEPACTION.setFillPattern(FillPatternType.SOLID_FOREGROUND)
		cellStyle_RESULT_STEPACTION.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index)
		cellStyle_RESULT_STEPACTION.setVerticalAlignment(VerticalAlignment.CENTER)
		cellStyle_RESULT_STEPACTION.setFont(fontStepGRP)
	}




	private createCellStyle_RESULT_STEPBLOCK() {

		cellStyle_RESULT_STEPBLOCK = book.createCellStyle()
		cellStyle_RESULT_STEPBLOCK.setVerticalAlignment(VerticalAlignment.CENTER)
		cellStyle_RESULT_STEPBLOCK.setFont(fontStep)
	}



	private createCellStyle_RESULT_STEPLOOP() {

		def fontStepLOOP = book.createFont()
		fontStepLOOP.setFontName('Arial')
		fontStepLOOP.setFontHeightInPoints(10 as short)
		fontStepLOOP.setItalic(true)
		fontStepLOOP.setBold(true)
		fontStepLOOP.setColor(IndexedColors.BLUE.getIndex())

		cellStyle_RESULT_STEPLOOP = book.createCellStyle()
		cellStyle_RESULT_STEPLOOP.setVerticalAlignment(VerticalAlignment.CENTER)
		cellStyle_RESULT_STEPLOOP.setFont(fontStepLOOP)
	}





	private createCellStyle_RESULT_SUBSTEP() {

		def fontStepSUB = book.createFont()
		fontStepSUB.setFontName('Arial')
		fontStepSUB.setFontHeightInPoints(10 as short)
		fontStepSUB.setItalic(true)
		fontStepSUB.setColor(IndexedColors.DARK_BLUE.getIndex())

		cellStyle_RESULT_SUBSTEP = book.createCellStyle()
		cellStyle_RESULT_SUBSTEP.setVerticalAlignment(VerticalAlignment.CENTER)
		cellStyle_RESULT_SUBSTEP.setFont(fontStepSUB)
	}





	private createCellStyle_RESULT_STEPDETAIL() {

		def fontStepDetail = book.createFont()
		fontStepDetail.setFontName('Arial')
		fontStepDetail.setFontHeightInPoints(10 as short)
		fontStepDetail.setItalic(true)
		fontStepDetail.setColor(IndexedColors.GREY_80_PERCENT.getIndex())

		cellStyle_RESULT_STEPDETAIL = book.createCellStyle()
		cellStyle_RESULT_STEPDETAIL.setVerticalAlignment(VerticalAlignment.CENTER)
		cellStyle_RESULT_STEPDETAIL.setFont(fontStepDetail)
	}


}
