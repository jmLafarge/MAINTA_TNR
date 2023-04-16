package my.result


import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import my.TCFiles as MYTCFILES
import my.Tools as TOOLS
import my.result.CELLStyleFactory

import internal.GlobalVariable

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







	private static takeScreenshot(Row row,Date date, String msg, String status) {

		if (!msg.contains("Fin de la  vérification des valeurs en Base de Données")) {

			String filename = date.format("yyyyMMdd_HHmmss.SSS") + '_'+ GlobalVariable.CASDETESTENCOURS + '_' + status + '.png'

			String path = new File(this.resulFileName).getParent()+ File.separator + this.SCREENSHOTSUBFOLDER

			TOOLS.createFolderIfNotExist(path)

			WebUI.takeScreenshot(path+ File.separator +filename,["text" : status+':'+msg, "fontSize" : 24, "fontColor": "#FF0000"])

			CSF.hyperlink_screenshotFile.setAddress('./'+this.SCREENSHOTSUBFOLDER+ '/' +filename);

			my.XLS.writeCell(row,11, filename  ,CSF.cellStyle_hyperlink,CSF.hyperlink_screenshotFile)

		}
	}






	public static addStep(Date date, String msg, String status) {

		Row row = this.shRESULT.createRow(this.nextLineNumber)

		int rowResult = 2

		this.groupDetail(status)

		switch (status) {

			case 'PASS':
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),CSF.cellStyle_RESULT_STEP)
				my.XLS.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPPASS)
				my.XLS.writeCell(row,2,'PASS',CSF.cellStyle_RESULT_STEPPASS)

				break
			case 'WARNING':
				this.takeScreenshot(row,date,msg,status)
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),CSF.cellStyle_RESULT_STEP)
				my.XLS.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPWARNING)
				my.XLS.writeCell(row,2,'WARNING',CSF.cellStyle_RESULT_STEPWARNING)
				break
			case 'FAIL':
				this.takeScreenshot(row,date,msg,status)
				my.XLS.writeCell(row,0,date.format('yyyy-MM-dd HH:mm:ss.SSS'),CSF.cellStyle_RESULT_STEP)
				my.XLS.writeCell(row,1,msg,CSF.cellStyle_RESULT_STEPFAIL)
				my.XLS.writeCell(row,2,'FAIL',CSF.cellStyle_RESULT_STEPFAIL)
				break
			case 'ERROR':
				this.takeScreenshot(row,date,msg,status)
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

		this.write()

		this.nextLineNumber ++

	}






	public static addStartCasDeTest(Date start) {

		Row row = this.shRESULT.createRow(this.lineNumberSTART)

		my.XLS.writeCell(row,0,start.format('yyyy-MM-dd HH:mm:ss.SSS'),CSF.cellStyle_RESULT_CDT)
		my.XLS.writeCell(row,1,GlobalVariable.CASDETESTENCOURS + ' : ' + MYTCFILES.getTCNameTitle(),CSF.cellStyle_RESULT_CDT)
		my.XLS.writeCell(row,8,start,CSF.cellStyle_time)

		this.write()

		nextLineNumber=this.lineNumberSTART+1
	}




	public static addEndCasDeTest(Map status, Date start, Date stop) {


		Row row = this.shRESULT.getRow(this.lineNumberSTART)

		int rowResult = 2

		this.groupDetail()

		boolean collapse = true //false

		if (status.ERROR !=0 ) {

			my.XLS.writeCell(row,rowResult,'ERROR',CSF.cellStyle_ERROR)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_ERROR
			this.totalERROR++

		}else if (status.FAIL != 0 ) {

			my.XLS.writeCell(row,rowResult,'FAIL',CSF.cellStyle_FAIL)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_FAIL
			this.totalFAIL++

		}else if (status.WARNING != 0 ) {

			my.XLS.writeCell(row,rowResult,'WARNING',CSF.cellStyle_WARNING)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_WARNING
			this.totalWARNING++

		}else {

			my.XLS.writeCell(row,rowResult,'PASS',CSF.cellStyle_PASS)
			CSF.cellStyle_RESULT_CDT=CSF.cellStyle_PASS
			collapse = true
			this.totalPASS++
		}

		my.XLS.writeCell(row,0,start.format('yyyy-MM-dd HH:mm:ss.SSS'),CSF.cellStyle_RESULT_CDT)
		my.XLS.writeCell(row,1, GlobalVariable.CASDETESTENCOURS + ' : ' + MYTCFILES.getTCNameTitle(),CSF.cellStyle_RESULT_CDT)

		int total = status.PASS + status.WARNING + status.FAIL + status.ERROR

		this.totalStepPASS 		+= status.PASS
		this.totalStepWARNING 	+= status.WARNING
		this.totalStepFAIL 		+= status.FAIL
		this.totalStepERROR 	+= status.ERROR

		my.XLS.writeCell(row,3,total,CSF.cellStyle_RESULT_STEPNBR)

		if (status.PASS!=0) 	my.XLS.writeCell(row,4,status.PASS,CSF.cellStyle_RESULT_STEPNBR)

		if (status.WARNING!=0) 	my.XLS.writeCell(row,5,status.WARNING,CSF.cellStyle_RESULT_STEPNBR)

		if (status.FAIL!=0) 	my.XLS.writeCell(row,6,status.FAIL,CSF.cellStyle_RESULT_STEPNBR)

		if (status.ERROR!=0) 	my.XLS.writeCell(row,7,status.ERROR,CSF.cellStyle_RESULT_STEPNBR)


		my.XLS.writeCell(row,9,stop,CSF.cellStyle_time)
		my.XLS.writeCell(row,10,my.Tools.getDuration(start, stop),CSF.cellStyle_duration)


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

		if (!this.resulFileName) this.openResultFile()


		my.XLS.writeCell(this.shRESUM.getRow(1),3,text)

		this.startDateTNR = new Date()

		my.XLS.writeCell(this.shRESUM.getRow(1),1,this.startDateTNR,CSF.cellStyle_date)
		my.XLS.writeCell(this.shRESUM.getRow(3),1,this.startDateTNR,CSF.cellStyle_time)


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

		my.XLS.writeCell(this.shRESUM.getRow(4),1,endDate,CSF.cellStyle_time)

		my.XLS.writeCell(this.shRESUM.getRow(5),1,my.Tools.getDuration(this.startDateTNR, endDate),CSF.cellStyle_duration)

		Row row = this.shRESUM.getRow(17)

		my.XLS.writeCell(row,1,this.totalPASS + this.totalWARNING + this.totalFAIL + this.totalERROR,CSF.cellStyle_TOT)
		my.XLS.writeCell(row,2,this.totalPASS,CSF.cellStyle_PASSTOT)
		my.XLS.writeCell(row,3,this.totalWARNING,CSF.cellStyle_WARNINGTOT)
		my.XLS.writeCell(row,4,this.totalFAIL,CSF.cellStyle_FAILTOT)
		my.XLS.writeCell(row,5,this.totalERROR,CSF.cellStyle_ERRORTOT)

		row = this.shRESUM.getRow(18)

		my.XLS.writeCell(row,1,this.totalStepPASS + this.totalStepWARNING + this.totalStepFAIL + this.totalStepERROR,CSF.cellStyle_STEPTOT)
		my.XLS.writeCell(row,2,this.totalStepPASS,CSF.cellStyle_STEPTOT)
		my.XLS.writeCell(row,3,this.totalStepWARNING,CSF.cellStyle_STEPTOT)
		my.XLS.writeCell(row,4,this.totalStepFAIL,CSF.cellStyle_STEPTOT)
		my.XLS.writeCell(row,5,this.totalStepERROR,CSF.cellStyle_STEPTOT)

		this.write()
	}



	private static String createFileByCopy() {

		Path source = Paths.get(my.PropertiesReader.getMyProperty('TNR_PATH') + File.separator + this.RES_MODELFILENAME)

		String dateFile = new Date().format("yyyyMMdd_HHmmss")

		String path = my.PropertiesReader.getMyProperty('RES_PATH')+ File.separator + dateFile

		TOOLS.createFolderIfNotExist(path)

		this.resulFileName = path + File.separator + dateFile + this.RES_FILENAME

		Path target = Paths.get(this.resulFileName)

		Files.copy(source, target)

	}





	private static openResultFile() {

		this.createFileByCopy()

		this.book = my.XLS.open(this.resulFileName)

		this.shRESUM = book.getSheet(this.RES_RESUMESHEETNAME)
		this.shRESULT = book.getSheet(this.RES_RESULTSHEETNAME)

		CSF  = new CELLStyleFactory(this.book)
	}



	private static write(){

		OutputStream fileOut = new FileOutputStream(this.resulFileName)
		this.book.write(fileOut);
	}




	private static close(){

		if (this.resulFileName) {
			this.book.close()
			def file = new File(this.resulFileName)
			String newName = file.getParent()+ File.separator +file.getName().replace('.xlsx','')
			newName= newName + '_MSSQL_'+ this.browserName.split(' ')[0] + '_Mainta_' + this.maintaVersion.replace(' ', '_') + '.xlsx'
			file.renameTo(newName)
		}
	}




} // end of class