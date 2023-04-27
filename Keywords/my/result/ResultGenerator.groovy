package my.result


import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.apache.poi.common.usermodel.HyperlinkType
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import my.TCFiles
import my.Tools as TOOLS
import my.Log as MYLOG

public class ResultGenerator {

	private static String RES_MODELFILENAME = 'MODELE_RESULTATS_TNR.xlsx'
	private static String RES_FILENAME = '_TNR.xlsx'

	private static String RES_RESUMESHEETNAME ='RESUME'
	private static String RES_RESULTSHEETNAME ='RESULTATS'
	private static String SCREENSHOTSUBFOLDER = 'screenshot'

	private static String resulFileName = ''
	private static XSSFWorkbook book
	private static Sheet shRESUM
	private static Sheet shRESULT

	private static int lineNumberSTART = 2	// casDeTest first line

	private static int nextLineNumber  = 2 // ligne en cours
	private static int firstLineDETAIL = 0
	private static String statusDETAIL = ''
	private static String previousStatus =''

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



	private static groupDetail(String status='') {

		if (status == 'DETAIL') {
			if (firstLineDETAIL == 0) {
				//début du DETAIL
				firstLineDETAIL = nextLineNumber
				statusDETAIL = previousStatus
			}

		}else if (firstLineDETAIL != 0){

			shRESULT.setRowSumsBelow(false)
			shRESULT.groupRow(firstLineDETAIL, nextLineNumber-1)
			shRESULT.setRowGroupCollapsed(firstLineDETAIL, true)
			firstLineDETAIL=0
		}
		previousStatus=status
	}







	private static takeScreenshot(Row row,Date date, String msg, String status) {

		if (!msg.contains("Fin de la  vérification des valeurs en Base de Données") || status == 'ERROR') {

			String filename = date.format("yyyyMMdd_HHmmss.SSS") + '_'+ GlobalVariable.CASDETESTENCOURS + '_' + status + '.png'

			String path = new File(resulFileName).getParent()+ File.separator + SCREENSHOTSUBFOLDER

			TOOLS.createFolderIfNotExist(path)

			WebUI.takeScreenshot(path+ File.separator +filename,["text" : status+':'+msg, "fontSize" : 24, "fontColor": "#FF0000"])

			def hyperlink_screenshotFile = CSF.createHelper.createHyperlink(HyperlinkType.FILE)

			hyperlink_screenshotFile.setAddress('./'+SCREENSHOTSUBFOLDER+ '/' +filename)

			my.XLS.writeCell(row,11, filename  ,CSF.cellStyle_hyperlink,hyperlink_screenshotFile)

		}
	}






	public static addStep(Date date, String msg, String status) {

		Row row = shRESULT.createRow(nextLineNumber)

		int rowResult = 2

		groupDetail(status)

		switch (status) {

			case 'PASS':
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),CSF.cellStyle_RESULT_STEP)
				my.XLS.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPPASS)
				my.XLS.writeCell(row,2,'PASS',CSF.cellStyle_RESULT_STEPPASS)

				break
			case 'WARNING':
				takeScreenshot(row,date,msg,status)
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),CSF.cellStyle_RESULT_STEP)
				my.XLS.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPWARNING)
				my.XLS.writeCell(row,2,'WARNING',CSF.cellStyle_RESULT_STEPWARNING)
				break
			case 'FAIL':
				takeScreenshot(row,date,msg,status)
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),CSF.cellStyle_RESULT_STEP)
				my.XLS.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPFAIL)
				my.XLS.writeCell(row,2,'FAIL',CSF.cellStyle_RESULT_STEPFAIL)
				break
			case 'ERROR':
				takeScreenshot(row,date,msg,status)
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),CSF.cellStyle_RESULT_STEP)
				my.XLS.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPERROR)
				my.XLS.writeCell(row,2,'ERROR',CSF.cellStyle_RESULT_STEPERROR)
				break
			case 'STEPLOOP':
				my.XLS.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPLOOP)

				break
			case 'STEPGRP':
				my.XLS.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPGRP)
				my.XLS.writeCell(row,2,'',CSF.cellStyle_RESULT_STEPGRP)

				break

			case 'STEPACTION':
				my.XLS.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPACTION)
				my.XLS.writeCell(row,2,'',CSF.cellStyle_RESULT_STEPACTION)

				break

			case 'STEPBLOCK':
				my.XLS.writeCell(row,1,(' '+msg+' ').center(70, '-'),CSF.cellStyle_RESULT_STEPBLOCK)
				my.XLS.writeCell(row,2,'',CSF.cellStyle_RESULT_STEPBLOCK)

				break

			case 'SUBSTEP':
				my.XLS.writeCell(row,1,msg,CSF.cellStyle_RESULT_SUBSTEP)

				break
			case 'INFO':
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),CSF.cellStyle_RESULT_STEPDETAIL)
				my.XLS.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPDETAIL)
				my.XLS.writeCell(row,2,'INFO',CSF.cellStyle_RESULT_STEPDETAIL)

				break
			case 'DETAIL':
				my.XLS.writeCell(row,1,' - '+msg,CSF.cellStyle_RESULT_STEPDETAIL)

				break
			default :
				println "*********************** Result.addStep, status inconnu $status"
		}

		write()

		nextLineNumber ++

	}






	public static addStartCasDeTest(Date start) {

		Row row = shRESULT.createRow(lineNumberSTART)

		my.XLS.writeCell(row,8,start,CSF.cellStyle_time)

		write()

		nextLineNumber=lineNumberSTART+1
	}




	public static addEndCasDeTest(Map status, Date start, Date stop,String cdt) {


		Row row = shRESULT.getRow(lineNumberSTART)

		int rowResult = 2

		groupDetail()

		boolean collapse = true //Mettre false si on veut collapser que les PASS

		if (status.ERROR !=0 ) {

			my.XLS.writeCell(row,rowResult,'ERROR',CSF.cellStyle_ERROR)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_ERROR
			totalERROR++

		}else if (status.FAIL != 0 ) {

			my.XLS.writeCell(row,rowResult,'FAIL',CSF.cellStyle_FAIL)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_FAIL
			totalFAIL++

		}else if (status.WARNING != 0 ) {

			my.XLS.writeCell(row,rowResult,'WARNING',CSF.cellStyle_WARNING)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_WARNING
			totalWARNING++

		}else {

			my.XLS.writeCell(row,rowResult,'PASS',CSF.cellStyle_PASS)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_PASS
			collapse = true
			totalPASS++
		}

		my.XLS.writeCell(row,0,start.format('yyyy-MM-dd HH:mm:ss.SSS'),CSF.cellStyle_RESULT_CDT)
		my.XLS.writeCell(row,1, cdt,CSF.cellStyle_RESULT_CDT)

		int total = status.PASS + status.WARNING + status.FAIL + status.ERROR

		totalStepPASS 		+= status.PASS
		totalStepWARNING 	+= status.WARNING
		totalStepFAIL 		+= status.FAIL
		totalStepERROR 	+= status.ERROR

		my.XLS.writeCell(row,3,total,CSF.cellStyle_RESULT_STEPNBR)

		if (status.PASS!=0) 	my.XLS.writeCell(row,4,status.PASS,CSF.cellStyle_RESULT_STEPNBR)

		if (status.WARNING!=0) 	my.XLS.writeCell(row,5,status.WARNING,CSF.cellStyle_RESULT_STEPNBR)

		if (status.FAIL!=0) 	my.XLS.writeCell(row,6,status.FAIL,CSF.cellStyle_RESULT_STEPNBR)

		if (status.ERROR!=0) 	my.XLS.writeCell(row,7,status.ERROR,CSF.cellStyle_RESULT_STEPNBR)


		my.XLS.writeCell(row,9,stop,CSF.cellStyle_time)
		my.XLS.writeCell(row,10,my.Tools.getDuration(start, stop),CSF.cellStyle_duration)

		shRESULT.setRowSumsBelow(false)
		shRESULT.groupRow(lineNumberSTART+1, nextLineNumber-1)
		shRESULT.setRowGroupCollapsed(lineNumberSTART+1, collapse)

		write()

		lineNumberSTART=nextLineNumber
	}





	public static addBrowserInfo(String browser, String version) {

		if (resulFileName) {
			browserName = browser
			my.XLS.writeCell(shRESUM.getRow(11),1,browser)
			my.XLS.writeCell(shRESUM.getRow(12),1,version)

			write()
		}
	}





	public static addStartInfo(String text) {

		if (!resulFileName) openResultFile()


		my.XLS.writeCell(shRESUM.getRow(1),3,text)

		startDateTNR = new Date()

		my.XLS.writeCell(shRESUM.getRow(1),1,startDateTNR,CSF.cellStyle_date)
		my.XLS.writeCell(shRESUM.getRow(3),1,startDateTNR,CSF.cellStyle_time)


		my.XLS.writeCell(shRESUM.getRow(8),1,System.getProperty("os.name"))
		my.XLS.writeCell(shRESUM.getRow(9),1,System.getProperty("os.version"))
		my.XLS.writeCell(shRESUM.getRow(10),1,System.getProperty("os.arch"))

		maintaVersion = my.SQL.getMaintaVersion()
		my.XLS.writeCell(shRESUM.getRow(13),1,maintaVersion)
		my.XLS.writeCell(shRESUM.getRow(14),1,GlobalVariable.BDD_URL)

		write()
	}





	public static addEndInfo() {

		Date endDate = new Date()

		my.XLS.writeCell(shRESUM.getRow(4),1,endDate,CSF.cellStyle_time)

		my.XLS.writeCell(shRESUM.getRow(5),1,my.Tools.getDuration(startDateTNR, endDate),CSF.cellStyle_duration)

		Row row = shRESUM.getRow(17)

		my.XLS.writeCell(row,1,totalPASS + totalWARNING + totalFAIL + totalERROR,CSF.cellStyle_TOT)
		my.XLS.writeCell(row,2,totalPASS,CSF.cellStyle_PASSTOT)
		my.XLS.writeCell(row,3,totalWARNING,CSF.cellStyle_WARNINGTOT)
		my.XLS.writeCell(row,4,totalFAIL,CSF.cellStyle_FAILTOT)
		my.XLS.writeCell(row,5,totalERROR,CSF.cellStyle_ERRORTOT)

		row = shRESUM.getRow(18)

		my.XLS.writeCell(row,1,totalStepPASS + totalStepWARNING + totalStepFAIL + totalStepERROR,CSF.cellStyle_STEPTOT)
		my.XLS.writeCell(row,2,totalStepPASS,CSF.cellStyle_STEPTOT)
		my.XLS.writeCell(row,3,totalStepWARNING,CSF.cellStyle_STEPTOT)
		my.XLS.writeCell(row,4,totalStepFAIL,CSF.cellStyle_STEPTOT)
		my.XLS.writeCell(row,5,totalStepERROR,CSF.cellStyle_STEPTOT)

		write()
	}



	private static String createFileByCopy() {

		Path source = Paths.get(my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + RES_MODELFILENAME)

		String dateFile = new Date().format("yyyyMMdd_HHmmss")

		String path = my.PropertiesReader.getMyProperty('RES_PATH')+ File.separator + dateFile

		TOOLS.createFolderIfNotExist(path)

		resulFileName = path + File.separator + dateFile + RES_FILENAME

		Path target = Paths.get(resulFileName)

		Files.copy(source, target)

	}





	private static openResultFile() {

		createFileByCopy()

		book = my.XLS.open(resulFileName)

		shRESUM = book.getSheet(RES_RESUMESHEETNAME)
		shRESULT = book.getSheet(RES_RESULTSHEETNAME)

		CSF  = new CELLStyleFactory(book)
	}



	private static write(){

		OutputStream fileOut = new FileOutputStream(resulFileName)
		book.write(fileOut);
	}




	private static close(){

		if (resulFileName) {
			book.close()
			def file = new File(resulFileName)
			String newName = file.getParent()+ File.separator +file.getName().replace('.xlsx','')
			newName= newName + '_MSSQL_'+ browserName.split(' ')[0] + '_Mainta_' + maintaVersion.replace(' ', '_') + '.xlsx'
			file.renameTo(newName)
		}
	}




} // end of class