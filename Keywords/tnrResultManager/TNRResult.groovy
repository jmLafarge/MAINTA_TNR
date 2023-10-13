package tnrResultManager


import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrCommon.Tools
import tnrDevOps.DevOpsManager
import tnrLog.Log
import tnrSqlManager.SQL
import tnrTC.TCFileMapper

@CompileStatic
public class TNRResult {


	private static Map <String,Integer> status = ['WARNING':0,'FAIL':0,'PASS':0,'ERROR':0]



	private static String PRESTEPTXT 		= '\tSTEP : '
	private static String PRESUBSTEPTXT		= '\t\t'
	private static String PREDETAILTXT		= '\t\t- '
	private static String PREINFOSTEPTXT	= '\t\t'


	private static String casDeTestFullname = ''
	private static boolean testCaseStarted 	= false
	private static Date startDatetimeCDT
	private static Date startDatetimeTNR
	
	private static final String DATE_FORMAT 		= 'dd/MM/yyyy'
	private static final String DATETIME_FORMAT 	= 'dd/MM/yyyy HH:mm:ss'
	
	private static String systemInfoValues 	=''

	private static String osName = ''
	private static String osVersion = ''
	private static String osArch = ''
	private static String browserName = '?'
	private static String browserVersion = '?'
	private static String maintaVersion = ''
	private static String baseURL = ''
	private static String pathDB = ''



	public static setAllowScreenshots(boolean allowScreenshots) {
		XLSResult.setAllowScreenshots(allowScreenshots)
	}

	public static addDETAIL (String msg) {
		if (testCaseStarted) {
			addStepInResult(msg,'DETAIL')
		}
		Log.add('',PREDETAILTXT+ msg)
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


	public static addSTEPFAIL (String strStepID, String msg) {
		Log.add('FAIL',PRESTEPTXT+ msg)
		status.FAIL++
		String historyDevOps = "Créé pendant la campagne TNR du : ${startDatetimeTNR.format(DATETIME_FORMAT)}"
		String devOpsID = DevOpsManager.create('NE PAS TRAITER - TNR FAIL : ' + msg, casDeTestFullname, systemInfoValues,strStepID,historyDevOps)
		addStepInResult(msg,'FAIL', strStepID,devOpsID)
		
	}


	public static addSTEPWARNING (String strStepID, String msg) {
		Log.add('WARNING',PRESTEPTXT+ msg)
		status.WARNING++
		addStepInResult(msg,'WARNING', strStepID)
		//devops
	}


	public static addSTEPERROR (String strStepID, String msg) {
		Log.addERROR(PRESTEPTXT+ msg)
		status.ERROR++
		addStepInResult(msg,'ERROR',strStepID)
		//devops
	}



	public static addBeginBlock (String msg,String status='INFO') {
		addSTEP('',msg,status)
		XLSResult.beginBlock()
	}


	public static addEndBlock (String msg,String status) {
		addSTEP('',msg,status)
		XLSResult.endBlock()
	}



	private static addStepInResult(String msg, String status, String strStepID='', String devOpsID ='') {
		XLSResult.addStep(Log.logDate,msg,status, strStepID, devOpsID)
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
		setSystemInfoValues()		
	}



	public static void addBrowserInfo() {

		browserName = Tools.getBrowserName()
		browserVersion = Tools.getBrowserVersion()

		addDETAIL("Nom du navigateur : " + browserName)
		addDETAIL("Version du navigateur : " + browserVersion)

		XLSResult.addBrowserInfo(browserName, browserVersion)
		setSystemInfoValues()	
	}
	
	

	private static void setSystemInfoValues() {
		String beginOfLine = '<tr><td><b>'
		String endOfTitle = '</b></td><td>'
		String endOfLine = '</td></tr>'
		systemInfoValues = "<h1>CAMPAGNE TNR du : ${startDatetimeTNR.format(DATE_FORMAT)}</h1>"
		systemInfoValues += "<table>"
		systemInfoValues += "${beginOfLine}Nom de l'OS $endOfTitle: $osName $endOfLine"
		systemInfoValues += "${beginOfLine}Version de l'OS $endOfTitle: $osVersion $endOfLine"
		systemInfoValues +="${beginOfLine}Architecture de l'OS $endOfTitle: $osArch $endOfLine"
		systemInfoValues +="${beginOfLine}Nom du navigateur $endOfTitle: $browserName $endOfLine"
		systemInfoValues +="${beginOfLine}Version du navigateur $endOfTitle: $browserVersion $endOfLine"
		systemInfoValues +="${beginOfLine}Version de MAINTA $endOfTitle: $maintaVersion $endOfLine"
		systemInfoValues +="${beginOfLine}URL $endOfTitle: $baseURL $endOfLine"
		systemInfoValues +="${beginOfLine}Base de donnée $endOfTitle: $pathDB $endOfLine"
		systemInfoValues +="</table>"
	}

	public static void close(String text) {
		Date endDatetimeTNR = new Date()
		String duration = Tools.getDuration(startDatetimeTNR, endDatetimeTNR)
		XLSResult.close(duration,endDatetimeTNR)
		Log.add('','')
		Log.add('',"************  FIN  du test : $text ************")
	}
	


}
