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

	private static String resulFileName = ''
	private static XSSFWorkbook book
	private static Sheet shRESUM
	private static Sheet shRESULT

	private static int lineNumberSTART = 2	// casDeTest first line

	private static int nextLineNumber  = 2 // ligne en cours
	//private static int firstLineDETAIL = 0
	//private static String statusDETAIL = ''
	//private static String previousStatus =''

	private static int firstLineToGroup = 0 		// premiere ligne à grouper
	private static boolean continueToGroup = true	// pour grouper toutes les  lignes qui ne sont pas WARNING, FAIL ou ERROR
	private static int lineBeginBlock = 0			// numéro de ligne du début d'un regroupement de ligne (Vérifiaction en BDD,..)

	private static CELLStyleFactory CSF


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

		if (!msg.contains("Fin de la  vérification des valeurs en Base de Données") || status == 'ERROR') {
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
			tnrCommon.ExcelUtils.writeCell(row,11, filename  ,CSF.cellStyle_hyperlink,hyperlink_screenshotFile)
		}

	}



	public static beginBlock () {
		lineBeginBlock = nextLineNumber
	}


	public static endBlock () {
		lineBeginBlock = 0
	}

	public static addStep(Date date, String msg, String status) {

		if (!resulFileName) return

			Row row = shRESULT.createRow(nextLineNumber)

		int rowResult = 2

		//groupDetail(status)

		boolean previousGroupLineOK = continueToGroup

		switch (status) {

			case 'PASS':
				tnrCommon.ExcelUtils.writeCell(row,0,date.format(DATETIMESTEP_FORMAT),CSF.cellStyle_RESULT_STEP)
				tnrCommon.ExcelUtils.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPPASS)
				tnrCommon.ExcelUtils.writeCell(row,2,'PASS',CSF.cellStyle_RESULT_STEPPASS)
				continueToGroup = true
				break
			case 'WARNING':
				takeScreenshot(row,date,msg,status)
				tnrCommon.ExcelUtils.writeCell(row,0,date.format(DATETIMESTEP_FORMAT),CSF.cellStyle_RESULT_STEP)
				tnrCommon.ExcelUtils.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPWARNING)
				tnrCommon.ExcelUtils.writeCell(row,2,'WARNING',CSF.cellStyle_RESULT_STEPWARNING)
				continueToGroup = false
				break
			case 'FAIL':
				takeScreenshot(row,date,msg,status)
				tnrCommon.ExcelUtils.writeCell(row,0,date.format(DATETIMESTEP_FORMAT),CSF.cellStyle_RESULT_STEP)
				tnrCommon.ExcelUtils.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPFAIL)
				tnrCommon.ExcelUtils.writeCell(row,2,'FAIL',CSF.cellStyle_RESULT_STEPFAIL)
				continueToGroup = false
				break
			case 'ERROR':
				takeScreenshot(row,date,msg,status)
				tnrCommon.ExcelUtils.writeCell(row,0,date.format(DATETIMESTEP_FORMAT),CSF.cellStyle_RESULT_STEP)
				tnrCommon.ExcelUtils.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPERROR)
				tnrCommon.ExcelUtils.writeCell(row,2,'ERROR',CSF.cellStyle_RESULT_STEPERROR)
				continueToGroup = false
				break
			case 'STEPLOOP':
				tnrCommon.ExcelUtils.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPLOOP)
				continueToGroup = true
				break
			case 'STEPGRP':
				tnrCommon.ExcelUtils.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPGRP)
				tnrCommon.ExcelUtils.writeCell(row,2,'',CSF.cellStyle_RESULT_STEPGRP)
				continueToGroup = true
				break

			case 'STEPACTION':
				tnrCommon.ExcelUtils.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPACTION)
				tnrCommon.ExcelUtils.writeCell(row,2,'',CSF.cellStyle_RESULT_STEPACTION)
				continueToGroup = true
				break

			case 'STEPBLOCK':
				tnrCommon.ExcelUtils.writeCell(row,1,(' '+msg+' ').center(70, '-'),CSF.cellStyle_RESULT_STEPBLOCK)
				tnrCommon.ExcelUtils.writeCell(row,2,'',CSF.cellStyle_RESULT_STEPBLOCK)
				continueToGroup = true
				break

			case 'SUBSTEP':
				tnrCommon.ExcelUtils.writeCell(row,1,msg,CSF.cellStyle_RESULT_SUBSTEP)
				continueToGroup = true
				break

			case 'INFO':
				tnrCommon.ExcelUtils.writeCell(row,0,date.format(DATETIMESTEP_FORMAT),CSF.cellStyle_RESULT_STEPDETAIL)
				tnrCommon.ExcelUtils.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPDETAIL)
				tnrCommon.ExcelUtils.writeCell(row,2,'INFO',CSF.cellStyle_RESULT_STEPDETAIL)
				continueToGroup = true
				break

			case 'DETAIL':
				tnrCommon.ExcelUtils.writeCell(row,1,' - '+msg,CSF.cellStyle_RESULT_STEPDETAIL)

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

		write()
		nextLineNumber ++

	}






	public static addStartCasDeTest(Date start) {

		if (!resulFileName) return

			Row row = shRESULT.createRow(lineNumberSTART)

		tnrCommon.ExcelUtils.writeCell(row,8,start.format(TIMESTEP_FORMAT),CSF.cellStyle_time)

		row = shRESULT.createRow(lineNumberSTART+1)
		tnrCommon.ExcelUtils.writeCell(row,1,"",CSF.cellStyle_RESULT_STEPDETAIL)

		write()

		nextLineNumber=lineNumberSTART+2

		firstLineToGroup = lineNumberSTART+1
		continueToGroup = true
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

		int rowResult = 2

		//groupDetail()

		boolean collapse = true //Mettre false si on veut collapser que les PASS

		if (status.ERROR !=0 ) {

			tnrCommon.ExcelUtils.writeCell(row,rowResult,'ERROR',CSF.cellStyle_ERROR)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_ERROR
			totalERROR++

		}else if (status.FAIL != 0 ) {

			tnrCommon.ExcelUtils.writeCell(row,rowResult,'FAIL',CSF.cellStyle_FAIL)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_FAIL
			totalFAIL++

		}else if (status.WARNING != 0 ) {

			tnrCommon.ExcelUtils.writeCell(row,rowResult,'WARNING',CSF.cellStyle_WARNING)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_WARNING
			totalWARNING++

		}else {

			tnrCommon.ExcelUtils.writeCell(row,rowResult,'PASS',CSF.cellStyle_PASS)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_PASS
			collapse = true
			totalPASS++
		}

		tnrCommon.ExcelUtils.writeCell(row,0,start.format(DATETIMESTEP_FORMAT),CSF.cellStyle_RESULT_CDT)
		tnrCommon.ExcelUtils.writeCell(row,1, cdt,CSF.cellStyle_RESULT_CDT)

		int total = status.PASS + status.WARNING + status.FAIL + status.ERROR

		totalStepPASS 		+= status.PASS
		totalStepWARNING 	+= status.WARNING
		totalStepFAIL 		+= status.FAIL
		totalStepERROR 	+= status.ERROR

		tnrCommon.ExcelUtils.writeCell(row,3,total,CSF.cellStyle_RESULT_STEPNBR)

		if (status.PASS!=0) 	tnrCommon.ExcelUtils.writeCell(row,4,status.PASS,CSF.cellStyle_RESULT_STEPNBR)

		if (status.WARNING!=0) 	tnrCommon.ExcelUtils.writeCell(row,5,status.WARNING,CSF.cellStyle_RESULT_STEPNBR)

		if (status.FAIL!=0) 	tnrCommon.ExcelUtils.writeCell(row,6,status.FAIL,CSF.cellStyle_RESULT_STEPNBR)

		if (status.ERROR!=0) 	tnrCommon.ExcelUtils.writeCell(row,7,status.ERROR,CSF.cellStyle_RESULT_STEPNBR)


		tnrCommon.ExcelUtils.writeCell(row,9,stop.format(TIMESTEP_FORMAT),CSF.cellStyle_time)
		tnrCommon.ExcelUtils.writeCell(row,10,tnrCommon.Tools.getDuration(start, stop),CSF.cellStyle_duration)

		Log.addTrace("addEndCasDeTest nextLineNumber=$nextLineNumber lineNumberSTART=$lineNumberSTART")


		shRESULT.setRowSumsBelow(false)
		shRESULT.groupRow(lineNumberSTART+1, nextLineNumber-1)
		shRESULT.setRowGroupCollapsed(lineNumberSTART+1, collapse)


		write()

		lineNumberSTART=nextLineNumber
	}





	public static addBrowserInfo(String browser, String version) {

		if (resulFileName) {
			browserName = browser
			tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(11),1,browser)
			tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(12),1,version)

			write()
		}
	}





	public static addStartInfo(String text) {

		if (!resulFileName) openResultFile()


		tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(1),3,text)

		startDateTNR = new Date()


		tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(1),1,startDateTNR.format(DATE_FORMAT),CSF.cellStyle_dateResultat)
		tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(3),1,startDateTNR.format(DATETIME_FORMAT),CSF.cellStyle_date)


		tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(8),1,System.getProperty("os.name"))
		tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(9),1,System.getProperty("os.version"))
		tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(10),1,System.getProperty("os.arch"))

		maintaVersion = SQL.getMaintaVersion()
		tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(13),1,maintaVersion)
		tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(14),1,GlobalVariable.BASE_URL.toString())
		tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(15),1,SQL.getPathDB())

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

		book = tnrCommon.ExcelUtils.open(resulFileName)

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

		tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(4),1,endDate.format(DATETIME_FORMAT),CSF.cellStyle_date)

		tnrCommon.ExcelUtils.writeCell(shRESUM.getRow(5),1,tnrCommon.Tools.getDuration(startDateTNR, endDate),CSF.cellStyle_durationResultat)

		Row row = shRESUM.getRow(18)

		tnrCommon.ExcelUtils.writeCell(row,1,totalPASS + totalWARNING + totalFAIL + totalERROR,CSF.cellStyle_TOT)
		tnrCommon.ExcelUtils.writeCell(row,2,totalPASS,CSF.cellStyle_PASSTOT)
		tnrCommon.ExcelUtils.writeCell(row,3,totalWARNING,CSF.cellStyle_WARNINGTOT)
		tnrCommon.ExcelUtils.writeCell(row,4,totalFAIL,CSF.cellStyle_FAILTOT)
		tnrCommon.ExcelUtils.writeCell(row,5,totalERROR,CSF.cellStyle_ERRORTOT)

		row = shRESUM.getRow(19)

		tnrCommon.ExcelUtils.writeCell(row,1,totalStepPASS + totalStepWARNING + totalStepFAIL + totalStepERROR,CSF.cellStyle_STEPTOT)
		tnrCommon.ExcelUtils.writeCell(row,2,totalStepPASS,CSF.cellStyle_STEPTOT)
		tnrCommon.ExcelUtils.writeCell(row,3,totalStepWARNING,CSF.cellStyle_STEPTOT)
		tnrCommon.ExcelUtils.writeCell(row,4,totalStepFAIL,CSF.cellStyle_STEPTOT)
		tnrCommon.ExcelUtils.writeCell(row,5,totalStepERROR,CSF.cellStyle_STEPTOT)

		write()

		if (resulFileName) {
			book.close()
			def file = new File(resulFileName)
			String newName = file.getParent()+ File.separator +file.getName().replace('.xlsx','')
			newName= newName + '_MSSQL_'+ browserName.split(' ')[0] + '_Mainta_' + maintaVersion.replace(' ', '_') + '.xlsx'
			file.renameTo(newName)
		}
	}




} // end of class