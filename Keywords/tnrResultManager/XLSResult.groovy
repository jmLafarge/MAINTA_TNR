package tnrResultManager

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.apache.poi.common.usermodel.HyperlinkType
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrCommon.ExcelUtils
import tnrCommon.FileUtils
import tnrCommon.TNRPropertiesReader
import tnrCommon.Tools
import tnrLog.Log
import tnrSqlManager.SQL

@CompileStatic
public class XLSResult {



	private static final String RES_MODELFILENAME 	= 'MODELE_RESULTATS_TNR.xlsx'
	private static final String RES_FILENAME 		= '_TNR.xlsx'

	private static final String RES_RESUMESHEETNAME = 'RESUME'
	private static final String RES_RESULTSHEETNAME = 'RESULTATS'
	private static final String SCREENSHOTSUBFOLDER = 'screenshot'

	private static final String DATE_FORMAT 		= 'dd/MM/yyyy'
	private static final String DATETIME_FORMAT 	= 'dd/MM/yyyy HH:mm:ss'
	private static final String DATETIMESTEP_FORMAT = 'yyyy-MM-dd HH:mm:ss.SSS'
	private static final String TIMESTEP_FORMAT 	= 'HH:mm:ss'

	// TAB RESUME
	private static final int RES_COL_TITLE		= 3
	private static final int RES_COL_INFO		= 1
	private static final int RES_COL_TOTAL		= 1
	private static final int RES_COL_PASS		= 2
	private static final int RES_COL_WARNING	= 3
	private static final int RES_COL_FAIL		= 4
	private static final int RES_COL_ERROR		= 5

	// TAB RESULTAT
	private static final int RST_COL_DATETIME	= 0
	private static final int RST_COL_CDT		= 1
	private static final int RST_COL_RESULT		= 2
	private static final int RST_COL_TOTAL		= 3
	private static final int RST_COL_PASS		= 4
	private static final int RST_COL_WARNING	= 5
	private static final int RST_COL_FAIL		= 6
	private static final int RST_COL_ERROR		= 7
	private static final int RST_COL_STARTTIME	= 8
	private static final int RST_COL_ENDTIME	= 9
	private static final int RST_COL_DURATION	= 10
	private static final int RST_COL_SCREENSHOT	= 11
	private static final int RST_COL_STEPID		= 12
	private static final int RST_COL_DEVOPS		= 13


	private static String resulFileName = ''
	private static XSSFWorkbook book
	private static Sheet shRESUM
	private static Sheet shRESULT

	private static int lineNumberSTART = 2	// casDeTest first line

	private static int nextLineNumber  = 2 // ligne en cours
	//private static int firstLineDETAIL = 0
	//private static String statusDETAIL = ''
	//private static String previousStatus =''

	private static int firstLineToGroup		= 0 		// premiere ligne à grouper
	private static boolean continueToGroup	= true	// pour grouper toutes les  lignes qui ne sont pas WARNING, FAIL ou ERROR
	private static int lineBeginBlock		= 0			// numéro de ligne du début d'un regroupement de ligne (Vérifiaction en BDD,..)

	private static CELLStyleFactory CSF


	private static Date startDateTNR
	private static int totalPASS 	= 0
	private static int totalWARNING	= 0
	private static int totalFAIL	= 0
	private static int totalERROR	= 0

	private static int totalStepPASS	= 0
	private static int totalStepWARNING	= 0
	private static int totalStepFAIL	= 0
	private static int totalStepERROR	= 0

	private static String browserName =''
	private static String maintaVersion =''

	private static boolean allowScreenshots = true


	/*
	 private static groupDetail(String status='') {
	 Log.addTrace("groupDetail nextLineNumber=$nextLineNumber firstLineDETAIL=$firstLineDETAIL")
	 if (status == 'DETAIL') {
	 if (firstLineDETAIL == 0) {
	 //début du DETAIL
	 firstLineDETAIL = nextLineNumber
	 statusDETAIL = previousStatus
	 }
	 }else if (firstLineDETAIL != 0){
	 Log.addTrace("groupDetail setRowGroupCollapsed")
	 shRESULT.setRowSumsBelow(false)
	 shRESULT.groupRow(firstLineDETAIL, nextLineNumber-1)
	 shRESULT.setRowGroupCollapsed(firstLineDETAIL, true)
	 firstLineDETAIL=0
	 }
	 previousStatus=status
	 }
	 */






	private static takeScreenshot(Row row,Date date, String msg, String status) {

		if (allowScreenshots) {
			String filename = date.format("yyyyMMdd_HHmmss.SSS") + '_'+ GlobalVariable.CAS_DE_TEST_EN_COURS + '_' + status + '.png'
			String path = new File(resulFileName).getParent()+ File.separator + SCREENSHOTSUBFOLDER
			FileUtils.createFolderIfNotExist(path)

			def screenshotOptions = [:] as Map<String, Object>
			screenshotOptions.put("text", status+':'+msg)
			screenshotOptions.put("fontSize", 24)
			screenshotOptions.put("fontColor", "#FF0000")
			WebUI.takeScreenshot(path+ File.separator +filename, screenshotOptions)

			def hyperlink_screenshotFile = CSF.createHelper.createHyperlink(HyperlinkType.FILE)
			hyperlink_screenshotFile.setAddress('./'+SCREENSHOTSUBFOLDER+ '/' +filename)
			ExcelUtils.writeCell(row,RST_COL_SCREENSHOT, filename  ,CSF.cellStyle_hyperlink,hyperlink_screenshotFile)
		}

	}



	public static beginBlock () {
		lineBeginBlock = nextLineNumber
	}


	public static endBlock () {
		lineBeginBlock = 0
	}

	public static void addStep(Date date, String msg, String status, String strStepID, String devOpsID) {

		if (!resulFileName) return
		
		def hyperlink_devOps
		if (devOpsID!='') {
			hyperlink_devOps = CSF.createHelper.createHyperlink(HyperlinkType.URL)
			hyperlink_devOps.setAddress("https://dev.azure.com/mainta/H%C3%A9racl%C3%A8s/_workitems/edit/$devOpsID")
		}
		
		
		Row row = shRESULT.createRow(nextLineNumber)

		//groupDetail(status)

		boolean previousGroupLineOK = continueToGroup

		switch (status) {

			case 'PASS':
				ExcelUtils.writeCell(row,RST_COL_DATETIME,date.format(DATETIMESTEP_FORMAT),CSF.cellStyle_RESULT_STEP)
				ExcelUtils.writeCell(row,RST_COL_CDT,msg,CSF.cellStyle_RESULT_STEPPASS)
				ExcelUtils.writeCell(row, RST_COL_RESULT, 'PASS',CSF.cellStyle_RESULT_STEPPASS)
				ExcelUtils.writeCell(row, RST_COL_STEPID, ,strStepID,CSF.cellStyle_RESULT_STEPDETAIL)
				continueToGroup = true
				break
			case 'WARNING':
				takeScreenshot(row,date,msg,status)
				ExcelUtils.writeCell(row,RST_COL_DATETIME,date.format(DATETIMESTEP_FORMAT),CSF.cellStyle_RESULT_STEP)
				ExcelUtils.writeCell(row,RST_COL_CDT,msg,CSF.cellStyle_RESULT_STEPWARNING)
				ExcelUtils.writeCell(row, RST_COL_RESULT, 'WARNING',CSF.cellStyle_RESULT_STEPWARNING)
				ExcelUtils.writeCell(row, RST_COL_STEPID, ,strStepID,CSF.cellStyle_RESULT_STEPDETAIL)
				continueToGroup = false
				break
			case 'FAIL':
				takeScreenshot(row,date,msg,status)
				ExcelUtils.writeCell(row,RST_COL_DATETIME,date.format(DATETIMESTEP_FORMAT),CSF.cellStyle_RESULT_STEP)
				ExcelUtils.writeCell(row,RST_COL_CDT,msg,CSF.cellStyle_RESULT_STEPFAIL)
				ExcelUtils.writeCell(row, RST_COL_RESULT, 'FAIL',CSF.cellStyle_RESULT_STEPFAIL)
				ExcelUtils.writeCell(row, RST_COL_STEPID, ,strStepID,CSF.cellStyle_RESULT_STEPDETAIL)
				if (devOpsID!='') {
					ExcelUtils.writeCell(row,RST_COL_DEVOPS, devOpsID  ,CSF.cellStyle_hyperlink,hyperlink_devOps)
				}
				continueToGroup = false
				break
			case 'ERROR':
				takeScreenshot(row,date,msg,status)
				ExcelUtils.writeCell(row,RST_COL_DATETIME,date.format(DATETIMESTEP_FORMAT),CSF.cellStyle_RESULT_STEP)
				ExcelUtils.writeCell(row,RST_COL_CDT,msg,CSF.cellStyle_RESULT_STEPERROR)
				ExcelUtils.writeCell(row, RST_COL_RESULT, 'ERROR',CSF.cellStyle_RESULT_STEPERROR)
				ExcelUtils.writeCell(row, RST_COL_STEPID, ,strStepID,CSF.cellStyle_RESULT_STEPDETAIL)
				continueToGroup = false
				break
			case 'STEPLOOP':
				ExcelUtils.writeCell(row,RST_COL_CDT,msg,CSF.cellStyle_RESULT_STEPLOOP)
				continueToGroup = true
				break
			case 'STEPGRP':
				ExcelUtils.writeCell(row,RST_COL_CDT,msg,CSF.cellStyle_RESULT_STEPGRP)
				ExcelUtils.writeCell(row, RST_COL_RESULT, '',CSF.cellStyle_RESULT_STEPGRP)
				continueToGroup = true
				break

			case 'STEPACTION':
				ExcelUtils.writeCell(row,RST_COL_CDT,msg,CSF.cellStyle_RESULT_STEPACTION)
				ExcelUtils.writeCell(row, RST_COL_RESULT, '',CSF.cellStyle_RESULT_STEPACTION)
				continueToGroup = true
				break

			case 'STEPBLOCK':
				ExcelUtils.writeCell(row,RST_COL_CDT,(' '+msg+' ').center(70, '-'),CSF.cellStyle_RESULT_STEPBLOCK)
				ExcelUtils.writeCell(row, RST_COL_RESULT, '',CSF.cellStyle_RESULT_STEPBLOCK)
				continueToGroup = true
				break

			case 'SUBSTEP':
				ExcelUtils.writeCell(row,RST_COL_CDT,msg,CSF.cellStyle_RESULT_SUBSTEP)
				continueToGroup = true
				break

			case 'INFO':
				ExcelUtils.writeCell(row,RST_COL_DATETIME,date.format(DATETIMESTEP_FORMAT),CSF.cellStyle_RESULT_STEPDETAIL)
				ExcelUtils.writeCell(row,RST_COL_CDT,msg,CSF.cellStyle_RESULT_STEPDETAIL)
				ExcelUtils.writeCell(row, RST_COL_RESULT, 'INFO',CSF.cellStyle_RESULT_STEPDETAIL)
				ExcelUtils.writeCell(row, RST_COL_STEPID, ,strStepID,CSF.cellStyle_RESULT_STEPDETAIL)
				continueToGroup = true
				break

			case 'DETAIL':
				ExcelUtils.writeCell(row,RST_COL_CDT,' - '+msg,CSF.cellStyle_RESULT_STEPDETAIL)

				break
			default :
				Log.addERROR("Result.addStep, status inconnu $status")
		}


		if (previousGroupLineOK && !continueToGroup) { // true --> false debut d'un KO fin du group

			int lastGroupLine = row.getRowNum()-1
			if (lineBeginBlock!=0) {
				lastGroupLine = lineBeginBlock-2
			}
			lineBeginBlock=0
			shRESULT.setRowSumsBelow(false)
			shRESULT.groupRow(firstLineToGroup+1, lastGroupLine)
			shRESULT.setRowGroupCollapsed(firstLineToGroup+1, false)
		}

		if (!previousGroupLineOK && continueToGroup) { // false --> true debut d'un OK memorise la ligne
			firstLineToGroup =  row.getRowNum()-1
		}

		//write()
		nextLineNumber ++

	}






	public static addStartCasDeTest(Date start) {

		if (!resulFileName) return

			Row row = shRESULT.createRow(lineNumberSTART)

		ExcelUtils.writeCell(row,RST_COL_STARTTIME,start.format(TIMESTEP_FORMAT),CSF.cellStyle_time)

		row = shRESULT.createRow(lineNumberSTART+1)
		ExcelUtils.writeCell(row,RST_COL_CDT,"",CSF.cellStyle_RESULT_STEPDETAIL)

		write()

		nextLineNumber=lineNumberSTART+2

		firstLineToGroup = lineNumberSTART+1
		continueToGroup = true
		allowScreenshots = true
	}




	public static addEndCasDeTest(Map <String,Integer> status, Date start, Date stop,String cdt) {

		if (!resulFileName) return

			if (continueToGroup) {
				shRESULT.setRowSumsBelow(false)
				shRESULT.groupRow(firstLineToGroup+1, nextLineNumber-1)
				shRESULT.setRowGroupCollapsed(firstLineToGroup+1, false)
				write()
			}

		Row row = shRESULT.getRow(lineNumberSTART)

		//groupDetail()

		boolean collapse = true //Mettre false si on veut collapser que les PASS

		if (status.ERROR !=0 ) {

			ExcelUtils.writeCell(row,RST_COL_RESULT,'ERROR',CSF.cellStyle_ERROR)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_ERROR
			totalERROR++

		}else if (status.FAIL != 0 ) {

			ExcelUtils.writeCell(row,RST_COL_RESULT,'FAIL',CSF.cellStyle_FAIL)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_FAIL
			totalFAIL++

		}else if (status.WARNING != 0 ) {

			ExcelUtils.writeCell(row,RST_COL_RESULT,'WARNING',CSF.cellStyle_WARNING)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_WARNING
			totalWARNING++

		}else {

			ExcelUtils.writeCell(row,RST_COL_RESULT,'PASS',CSF.cellStyle_PASS)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_PASS
			collapse = true
			totalPASS++
		}

		ExcelUtils.writeCell(row,RST_COL_DATETIME,start.format(DATETIMESTEP_FORMAT),CSF.cellStyle_RESULT_CDT)
		ExcelUtils.writeCell(row,RST_COL_CDT, cdt,CSF.cellStyle_RESULT_CDT)

		int total = status.PASS + status.WARNING + status.FAIL + status.ERROR

		totalStepPASS 		+= status.PASS
		totalStepWARNING 	+= status.WARNING
		totalStepFAIL 		+= status.FAIL
		totalStepERROR 	+= status.ERROR

		ExcelUtils.writeCell(row,RST_COL_TOTAL,total,CSF.cellStyle_RESULT_STEPNBR)

		if (status.PASS!=0) 	ExcelUtils.writeCell(row,RST_COL_PASS,status.PASS,CSF.cellStyle_RESULT_STEPNBR)

		if (status.WARNING!=0) 	ExcelUtils.writeCell(row,RST_COL_WARNING,status.WARNING,CSF.cellStyle_RESULT_STEPNBR)

		if (status.FAIL!=0) 	ExcelUtils.writeCell(row,RST_COL_FAIL,status.FAIL,CSF.cellStyle_RESULT_STEPNBR)

		if (status.ERROR!=0) 	ExcelUtils.writeCell(row,RST_COL_ERROR,status.ERROR,CSF.cellStyle_RESULT_STEPNBR)


		ExcelUtils.writeCell(row,RST_COL_ENDTIME,stop.format(TIMESTEP_FORMAT),CSF.cellStyle_time)
		ExcelUtils.writeCell(row,RST_COL_DURATION,Tools.getDuration(start, stop),CSF.cellStyle_duration)

		Log.addTrace("addEndCasDeTest nextLineNumber=$nextLineNumber lineNumberSTART=$lineNumberSTART")


		shRESULT.setRowSumsBelow(false)
		shRESULT.groupRow(lineNumberSTART+1, nextLineNumber-1)
		shRESULT.setRowGroupCollapsed(lineNumberSTART+1, collapse)


		//write()

		lineNumberSTART=nextLineNumber
	}





	public static addBrowserInfo(String browser, String version) {

		if (resulFileName) {
			browserName = browser
			ExcelUtils.writeCell(shRESUM.getRow(11),RES_COL_INFO,browser)
			ExcelUtils.writeCell(shRESUM.getRow(12),RES_COL_INFO,version)

			write()
		}
	}





	public static addStartInfo(String title, String osName, String osVersion, String osArch, String maintaVersion, String baseURL, String pathDB) {

		if (!resulFileName) openResultFile()


		ExcelUtils.writeCell(shRESUM.getRow(1),RES_COL_TITLE,title)

		startDateTNR = new Date()


		ExcelUtils.writeCell(shRESUM.getRow(1),RES_COL_INFO,startDateTNR.format(DATE_FORMAT),CSF.cellStyle_dateResultat)
		ExcelUtils.writeCell(shRESUM.getRow(3),RES_COL_INFO,startDateTNR.format(DATETIME_FORMAT),CSF.cellStyle_date)


		ExcelUtils.writeCell(shRESUM.getRow(8),RES_COL_INFO,osName)
		ExcelUtils.writeCell(shRESUM.getRow(9),RES_COL_INFO,osVersion)
		ExcelUtils.writeCell(shRESUM.getRow(10),RES_COL_INFO,osArch)

		this.maintaVersion = maintaVersion
		ExcelUtils.writeCell(shRESUM.getRow(13),RES_COL_INFO,maintaVersion)
		ExcelUtils.writeCell(shRESUM.getRow(14),RES_COL_INFO,baseURL)
		ExcelUtils.writeCell(shRESUM.getRow(15),RES_COL_INFO,pathDB)

		write()
	}



	private static String createFileByCopy() {

		Path source = Paths.get(TNRPropertiesReader.getMyProperty('TNR_PATH') + File.separator + RES_MODELFILENAME)

		String dateFile = new Date().format("yyyyMMdd_HHmmss")

		String path = TNRPropertiesReader.getMyProperty('RES_PATH')+ File.separator + dateFile

		FileUtils.createFolderIfNotExist(path)

		resulFileName = path + File.separator + dateFile + RES_FILENAME

		Path target = Paths.get(resulFileName)

		Files.copy(source, target)

	}





	private static openResultFile() {

		createFileByCopy()

		book = ExcelUtils.open(resulFileName)

		shRESUM = book.getSheet(RES_RESUMESHEETNAME)
		shRESULT = book.getSheet(RES_RESULTSHEETNAME)

		CSF  = new CELLStyleFactory(book)
	}



	private static write(){

		OutputStream fileOut = new FileOutputStream(resulFileName)
		book.write(fileOut);
	}




	public static close(){

		Date endDate = new Date()

		ExcelUtils.writeCell(shRESUM.getRow(4),RES_COL_INFO,endDate.format(DATETIME_FORMAT),CSF.cellStyle_date)

		ExcelUtils.writeCell(shRESUM.getRow(5),RES_COL_INFO,Tools.getDuration(startDateTNR, endDate),CSF.cellStyle_durationResultat)

		Row row = shRESUM.getRow(18)

		ExcelUtils.writeCell(row,RES_COL_TOTAL,totalPASS + totalWARNING + totalFAIL + totalERROR,CSF.cellStyle_TOT)
		ExcelUtils.writeCell(row,RES_COL_PASS,totalPASS,CSF.cellStyle_PASSTOT)
		ExcelUtils.writeCell(row,RES_COL_WARNING,totalWARNING,CSF.cellStyle_WARNINGTOT)
		ExcelUtils.writeCell(row,RES_COL_FAIL,totalFAIL,CSF.cellStyle_FAILTOT)
		ExcelUtils.writeCell(row,RES_COL_ERROR,totalERROR,CSF.cellStyle_ERRORTOT)

		row = shRESUM.getRow(19)

		ExcelUtils.writeCell(row,RES_COL_TOTAL,totalStepPASS + totalStepWARNING + totalStepFAIL + totalStepERROR,CSF.cellStyle_STEPTOT)
		ExcelUtils.writeCell(row,RES_COL_PASS,totalStepPASS,CSF.cellStyle_STEPTOT)
		ExcelUtils.writeCell(row,RES_COL_WARNING,totalStepWARNING,CSF.cellStyle_STEPTOT)
		ExcelUtils.writeCell(row,RES_COL_FAIL,totalStepFAIL,CSF.cellStyle_STEPTOT)
		ExcelUtils.writeCell(row,RES_COL_ERROR,totalStepERROR,CSF.cellStyle_STEPTOT)

		write()

		if (resulFileName) {
			book.close()
			def file = new File(resulFileName)
			String newName = file.getParent()+ File.separator +file.getName().replace('.xlsx','')
			newName= newName + '_MSSQL_'+ browserName.split(' ')[0] + '_Mainta_' + maintaVersion.replace(' ', '_') + '.xlsx'
			file.renameTo(newName)
		}
	}


	public static setAllowScreenshots(boolean allowScreenshots) {
		this.allowScreenshots = allowScreenshots
	}


} // Fin de class