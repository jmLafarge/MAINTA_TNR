package tnrResultManager


import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrCommon.FileUtils
import tnrCommon.HtmlTableBuilder
import tnrCommon.Tools
import tnrDevOps.DevOpsBug
import tnrDevOps.DevOpsClient
import tnrDevOps.DevOpsTask
import tnrLog.Log
import tnrSqlManager.SQL
import tnrTC.TCFileMapper
import tnrWebUI.WUI

@CompileStatic
public class TNRResult {


	private static Map <String,Integer> status = ['WARNING':0,'FAIL':0,'PASS':0,'ERROR':0]



	private static String PRESTEPTXT 		= '\tSTEP : '
	private static String PRESUBSTEPTXT		= '\t\t'
	private static String PREDETAILTXT		= '\t\t- '
	private static String PREINFOSTEPTXT	= '\t\t'

	private static final String SCREENSHOTSUBFOLDER = 'screenshot'

	private static String casDeTestFullname = ''
	private static boolean testCaseStarted 	= false
	private static Date startDatetimeCDT
	private static Date startDatetimeTNR
	private static Date endDatetimeTNR
	private static String durationTNR

	private static final String DATE_FORMAT 		= 'dd/MM/yyyy'
	private static final String DATETIME_FORMAT 	= 'dd/MM/yyyy HH:mm:ss'

	private static String devOpsSystemInfoValues 	=''

	private static String osName = ''
	private static String osVersion = ''
	private static String osArch = ''
	private static String browserName = '?'
	private static String browserVersion = '?'
	private static String maintaVersion = ''
	private static String baseURL = ''
	private static String pathDB = ''

	private static boolean allowScreenshots = true

	public static setAllowScreenshots(boolean allowScreenshots) {
		//XLSResult.setAllowScreenshots(allowScreenshots)
		this.allowScreenshots = allowScreenshots
	}

	public static addDETAIL (String msg) {
		if (testCaseStarted) {
			addStepInResult(msg,'DETAIL')
		}
		Log.add('',PREDETAILTXT+ msg)
	}


	private static Map takeScreenshot(Date date, String msg, String status) {

		String fileFullname = ''
		String relativePath = ''
		if (allowScreenshots) {
			String filename = date.format("yyyyMMdd_HHmmss.SSS") + '_'+ GlobalVariable.CAS_DE_TEST_EN_COURS + '_' + status + '.png'
			String path = new File(XLSResult.getResulFileName()).getParent()+ File.separator + SCREENSHOTSUBFOLDER
			FileUtils.createFolderIfNotExist(path)

			def screenshotOptions = [:] as Map<String, Object>
			screenshotOptions.put("text", status+':'+msg)
			screenshotOptions.put("fontSize", 24)
			screenshotOptions.put("fontColor", "#FF0000")
			fileFullname = path+ File.separator +filename
			WUI.takeScreenshot(fileFullname, screenshotOptions)
			relativePath = './'+SCREENSHOTSUBFOLDER+ '/' +filename
		}
		return [fileFullname:fileFullname , relativePath:relativePath]
	}


	public static addSTEP (String strStepID, String msg, String status = 'INFO') {

		switch (status) {
			case null :
			case 'INFO' :
				addSTEPINFO(strStepID, msg)
				break
			case 'PASS':
				addSTEPPASS(strStepID, msg)
				break
			case 'WARNING':
				addSTEPWARNING(strStepID, msg)
				break
			case 'FAIL':
				addSTEPFAIL(strStepID, msg)
				break
			case 'ERROR':
				addSTEPERROR(strStepID, msg)
				break
			default :
				Log.add(status,PRESTEPTXT+ msg)
				break
		}
	}



	public static addSUBSTEP (String msg) {
		Log.add('',PRESUBSTEPTXT+ msg)
		addStepInResult(msg,'SUBSTEP')
	}


	public static addSTEPACTION (String msg) {
		Log.add('','\t'+ msg.padRight(90, '_'))
		addStepInResult(msg,'STEPACTION')
	}


	public static addSTEPGRP (String msg) {
		Log.add('','\t'+ msg.padRight(90, '_'))
		addStepInResult(msg,'STEPGRP')
	}

	public static addSTEPBLOCK (String msg) {
		Log.add('','\t'+ msg.center(70, '-'))
		addStepInResult(msg,'STEPBLOCK')
	}



	public static addSTEPLOOP (String msg) {
		Log.add('','\t'+ msg.padRight(40, '.'))
		addStepInResult(msg,'STEPLOOP')
	}

	public static addSTEPINFO (String strStepID,String msg) {
		Log.add('',PREINFOSTEPTXT+ msg)
		addStepInResult(msg,'INFO',strStepID)
	}

	public static addSTEPPASS (String strStepID, String msg) {
		Log.add('PASS',PRESTEPTXT+ msg)
		status.PASS++
		addStepInResult(msg,'PASS', strStepID)
	}

	private static Map manageDevOpsBug(String strStepID, String msg, Map <String,String> screenshotFileInfo, String status) {

		String bugID =''
		String screenshotLink = screenshotFileInfo.relativePath
		String historyComment = ''
		String existingBugID = DevOpsClient.searchTNRNumber(strStepID)

		if (DevOpsClient.ALLOW_CREATION) {
			String cssLink = 'href="' + DevOpsTask.getTaskUrl() +'"'
			String aLink = " <a ${cssLink}>${DevOpsTask.getTaskId()} </a>"
			if (existingBugID) {
				historyComment = "Toujours présent pendant la campagne TNR du : ${startDatetimeTNR.format(DATETIME_FORMAT)} "
				DevOpsBug.updateHistoryBug(existingBugID,historyComment + aLink)
				screenshotLink = DevOpsBug.addFileToBug(existingBugID,screenshotFileInfo.fileFullname, historyComment)
				DevOpsTask.addBugToTask(existingBugID,'Toujours présent pendant cette campagne')
				bugID = existingBugID
			}else {
				historyComment = "Créé pendant la campagne TNR du : ${startDatetimeTNR.format(DATETIME_FORMAT)}"
				bugID = DevOpsBug.createBug("NE PAS TRAITER - TNR $status : $msg", casDeTestFullname, devOpsSystemInfoValues,strStepID,historyComment + aLink)
				screenshotLink = DevOpsBug.addFileToBug(bugID,screenshotFileInfo.fileFullname, historyComment)
				DevOpsTask.addBugToTask(bugID,'Nouveau, détecté pendant cette campagne')
			}
		}else {
			bugID = existingBugID
		}
		Map ret = [id:bugID , screenshotLink:screenshotLink]
		return ret
	}


	public static addSTEPFAIL (String strStepID, String msg) {
		Log.add('FAIL',PRESTEPTXT+ msg)
		status.FAIL++
		Map <String,String> screenshotFileInfo = takeScreenshot(Log.logDate, msg, 'FAIL')
		Map <String,String> bug = manageDevOpsBug(strStepID, msg, screenshotFileInfo,'FAIL')
		addStepInResult(msg,'FAIL', strStepID,bug.id,bug.screenshotLink)
	}


	public static addSTEPWARNING (String strStepID, String msg) {
		Log.add('WARNING',PRESTEPTXT+ msg)
		status.WARNING++
		Map <String,String> screenshotFileInfo = takeScreenshot(Log.logDate, msg, 'WARNING')
		Map <String,String> bug = manageDevOpsBug(strStepID, msg, screenshotFileInfo,'WARNING')
		addStepInResult(msg,'WARNING', strStepID,bug.id,bug.screenshotLink)
	}


	public static addSTEPERROR (String strStepID, String msg) {
		Log.addERROR(PRESTEPTXT+ msg)
		status.ERROR++
		Map <String,String> screenshotFileInfo = takeScreenshot(Log.logDate, msg, 'ERROR')
		Map <String,String> bug = manageDevOpsBug(strStepID, msg, screenshotFileInfo,'ERROR')
		addStepInResult(msg,'ERROR',strStepID,bug.id,bug.screenshotLink)
	}



	public static addBeginBlock (String msg,String status='INFO') {
		addSTEP('',msg,status)
		XLSResult.beginBlock()
	}


	public static addEndBlock (String msg,String status) {
		addSTEP('',msg,status)
		XLSResult.endBlock()
	}



	private static addStepInResult(String msg, String status, String strStepID='', String devOpsBugID ='', String screenshotLink='') {
		XLSResult.addStep(Log.logDate,msg,status, strStepID, devOpsBugID,DevOpsBug.getBugUrl(devOpsBugID),screenshotLink)
	}




	public static addStartTestCase (String cdt) {

		testCaseStarted = true

		GlobalVariable.CAS_DE_TEST_EN_COURS = cdt
		casDeTestFullname = cdt + ' : ' + TCFileMapper.getTitle(cdt)
		status.WARNING = 0
		status.FAIL = 0
		status.PASS = 0
		status.ERROR = 0
		Log.add('','')
		Log.add('',"START TEST CASE : $casDeTestFullname" )
		Log.setTabINFO(PRESUBSTEPTXT)
		startDatetimeCDT = Log.logDate
		XLSResult.addStartCasDeTest( startDatetimeCDT)
		allowScreenshots = true
	}


	public static addEndTestCase () {

		if (testCaseStarted) {

			testCaseStarted = false

			Date stop = new Date()

			XLSResult.addEndCasDeTest(status, startDatetimeCDT , stop,casDeTestFullname)
			Log.setTabINFO('')
			if (status.ERROR !=0) {
				Log.addERROR('END TEST CASE : ' + casDeTestFullname.padRight(100, '.') +  ' Duration : ' + Tools.getDuration(startDatetimeCDT,stop))
			} else if (status.FAIL !=0) {
				Log.add('FAIL','END TEST CASE : ' + casDeTestFullname.padRight(100, '.') +  ' Duration : ' + Tools.getDuration(startDatetimeCDT,stop))
			} else if (status.WARNING !=0) {
				Log.add('WARNING','END TEST CASE : ' + casDeTestFullname.padRight(100, '.') +  ' Duration : ' + Tools.getDuration(startDatetimeCDT,stop))
			} else {
				Log.add('PASS','END TEST CASE : ' + casDeTestFullname.padRight(100, '.') +  ' Duration : ' + Tools.getDuration(startDatetimeCDT,stop))
			}
		}
		casDeTestFullname = ''
	}


	public static addStartInfo(String text) {

		startDatetimeTNR = new Date()

		osName = System.getProperty("os.name")
		osVersion = System.getProperty("os.version")
		osArch = System.getProperty("os.arch")
		maintaVersion = SQL.getMaintaVersion()
		baseURL = GlobalVariable.BASE_URL.toString()
		pathDB = SQL.getPathDB()

		XLSResult.addStartInfo(startDatetimeTNR, text, osName, osVersion, osArch, maintaVersion, baseURL, pathDB)
		setDevOpsSystemInfoValues()
	}



	public static void addBrowserInfo() {

		browserName = Tools.getBrowserName()
		browserVersion = Tools.getBrowserVersion()

		addDETAIL("Nom du navigateur : " + browserName)
		addDETAIL("Version du navigateur : " + browserVersion)

		XLSResult.addBrowserInfo(browserName, browserVersion)
		setDevOpsSystemInfoValues()
	}

	public static void createDevOpsTask() {
		if (DevOpsClient.ALLOW_CREATION) {
			String taskTitle = "${startDatetimeTNR.format('yyyyMMdd')}- CAMPAGNE TNR Mainta $maintaVersion MSSQL  ${browserName.split(' ')[0]}"
			DevOpsTask.createTask(taskTitle, devOpsSystemInfoValues )
			XLSResult.addDevOpsTaskId(DevOpsTask.getTaskId(), DevOpsTask.getTaskUrl())
		}
	}




	private static void addDevOpsSystemInfoValuesToTable(HtmlTableBuilder htmlTable) {
		String cssLabel = 'font-weight: bold'
		htmlTable.addRow([["Nom de l'OS", cssLabel], [" : $osName"]])
		htmlTable.addRow([["Version de l'OS", cssLabel], [" : $osVersion"]])
		htmlTable.addRow([["Architecture de l'OS", cssLabel], [" : $osArch"]])
		htmlTable.addRow([["Nom du navigateur", cssLabel], [" : $browserName"]])
		htmlTable.addRow([["Version du navigateur", cssLabel], [" : $browserVersion"]])
		htmlTable.addRow([["Version de MAINTA", cssLabel], [" : $maintaVersion"]])
		htmlTable.addRow([["URL", cssLabel], [" : $baseURL"]])
		htmlTable.addRow([["Base de donnée", cssLabel], [" : $pathDB"]])
	}



	private static void setDevOpsSystemInfoValues() {

		devOpsSystemInfoValues = "<h2>Relevé pendant la campagne TNR du ${startDatetimeTNR.format(DATE_FORMAT)}</h2>"
		def htmlTable = new HtmlTableBuilder()
		addDevOpsSystemInfoValuesToTable(htmlTable)

		devOpsSystemInfoValues +=htmlTable.build()
	}


	public static void updateDevOpsTask() {

		String description =  "<h1>CAMPAGNE TNR du : ${startDatetimeTNR.format(DATE_FORMAT)}</h1>"

		def htmlTable = new HtmlTableBuilder()
		addDevOpsSystemInfoValuesToTable(htmlTable)

		String cssLabel = 'font-weight: bold;color: blue;'
		String cssValue = 'color: blue;'
		htmlTable.addRow([["Début des tests", cssLabel], [" : ${startDatetimeTNR.format(DATETIME_FORMAT)}", cssValue]])
		htmlTable.addRow([["Fin des tests", cssLabel], [" : ${endDatetimeTNR.format(DATETIME_FORMAT)}", cssValue]])
		htmlTable.addRow([["Durée", cssLabel], [" : $durationTNR", cssValue]])

		description += htmlTable.build()
		description += '<br/>'

		htmlTable = new HtmlTableBuilder()


		String cssTitle = 'width: 60px;'
		String cssTotal = 'font-weight: bold;text-align: right;'

		htmlTable.addRow([["", ''], ["  Total", cssTitle], ["   Pass", cssTitle], ["Warning", cssTitle], ["   Fail", cssTitle], ["  Error", cssTitle]],'th')

		int totalCDT = XLSResult.getTotalPASS() + XLSResult.getTotalWARNING() + XLSResult.getTotalFAIL() + XLSResult.getTotalERROR()
		htmlTable.addRow([["Nombre total de cas de tests", 'font-weight: bold;'], [totalCDT, cssTotal], [XLSResult.getTotalPASS(), cssTotal + 'background-color: lime;'], [XLSResult.getTotalWARNING(), cssTotal + 'background-color: yellow;'], [XLSResult.getTotalFAIL(), cssTotal + 'background-color: orange;'], [XLSResult.getTotalERROR(), cssTotal + 'background-color: red;']])


		String cssLabelStep = 'font-style: italic;font-size: 12px;'
		String cssTotalStep = 'font-style: italic;text-align: right;font-size: 12px;'
		int totalStep = XLSResult.getTotalStepPASS() + XLSResult.getTotalStepWARNING() + XLSResult.getTotalStepFAIL() + XLSResult.getTotalStepERROR()
		htmlTable.addRow([["Nombre total de STEP", cssLabelStep], [totalStep, cssTotalStep], [XLSResult.getTotalStepPASS(), cssTotalStep], [XLSResult.getTotalStepWARNING(), cssTotalStep], [XLSResult.getTotalStepFAIL(), cssTotalStep], [XLSResult.getTotalStepERROR(), cssTotalStep]])

		description += htmlTable.build()

		if (DevOpsClient.ALLOW_CREATION) {
			DevOpsTask.updateDescriptionTask(description)
			DevOpsTask.addFileToTask(XLSResult.getResulFileName())
		}
	}



	public static void close(String text) {
		endDatetimeTNR = new Date()
		durationTNR = Tools.getDuration(startDatetimeTNR, endDatetimeTNR)
		XLSResult.close(durationTNR,endDatetimeTNR)
		updateDevOpsTask()
		Log.add('','')
		Log.add('',"************  FIN  du test : $text ************")
	}
}
