package my.result


import groovy.transform.CompileStatic
import internal.GlobalVariable
import my.Log
import my.TCFiles
import my.Tools

@CompileStatic
public class TNRResult {


	private static Map <String,Integer> status = ['WARNING':0,'FAIL':0,'PASS':0,'ERROR':0]



	private static String PRESTEPTXT 	= '\tSTEP : '
	private static String PRESUBSTEPTXT	= '\t\t'
	private static String PREDETAILTXT	= '\t\t- '



	private static boolean testCaseStarted = false
	private static Date start




	public static addDETAIL (String msg) {
		if (testCaseStarted) {
			Log.addINFO(PREDETAILTXT+ msg)
			addStepInResult(msg,'DETAIL')
		}
	}


	public static addSTEP (String msg, String status = null) {

		switch (status) {
			case null :
				Log.addINFO(PRESTEPTXT+ msg)
				addStepInResult(msg,'INFO')
				break
			case 'PASS':
				addSTEPPASS(msg)
				break
			case 'WARNING':
				addSTEPWARNING(msg)
				break
			case 'FAIL':
				addSTEPFAIL(msg)
				break
			case 'ERROR':
				addSTEPERROR(msg)
				break
			default :
				Log.add(status,PRESTEPTXT+ msg)
				break
		}
	}



	public static addSUBSTEP (String msg) {
		Log.addINFO(PRESUBSTEPTXT+ msg)
		addStepInResult(msg,'SUBSTEP')
	}


	public static addSTEPACTION (String msg) {
		Log.addINFO('\t'+ msg.padRight(90, '_'))
		addStepInResult(msg,'STEPACTION')
	}


	public static addSTEPGRP (String msg) {
		Log.addINFO('\t'+ msg.padRight(90, '_'))
		addStepInResult(msg,'STEPGRP')
	}

	public static addSTEPBLOCK (String msg) {
		Log.addINFO('\t'+ msg.center(70, '-'))
		addStepInResult(msg,'STEPBLOCK')
	}



	public static addSTEPLOOP (String msg) {
		Log.addINFO('\t'+ msg.padRight(40, '.'))
		addStepInResult(msg,'STEPLOOP')
	}


	public static addSTEPPASS (String msg) {
		Log.add('PASS',PRESTEPTXT+ msg)
		status.PASS++
		addStepInResult(msg,'PASS')
	}


	public static addSTEPFAIL (String msg) {
		Log.add('FAIL',PRESTEPTXT+ msg)
		status.FAIL++
		addStepInResult(msg,'FAIL')
	}


	public static addSTEPWARNING (String msg) {
		Log.add('WARNING',PRESTEPTXT+ msg)
		status.WARNING++
		addStepInResult(msg,'WARNING')
	}


	public static addSTEPERROR (String msg) {
		Log.addERROR(PRESTEPTXT+ msg)
		status.ERROR++
		addStepInResult(msg,'ERROR')
	}







	private static addStepInResult(String msg, String status) {

		XLSResult.addStep(Log.logDate,msg,status)
	}


	public static addStartTestCase (String cdt) {

		testCaseStarted = true

		GlobalVariable.CASDETESTENCOURS = cdt
		cdt += ' : ' + TCFiles.getTitle()
		status.WARNING = 0
		status.FAIL = 0
		status.PASS = 0
		status.ERROR = 0
		Log.addINFO('')
		Log.addINFO("START TEST CASE : $cdt" )
		start = Log.logDate
		XLSResult.addStartCasDeTest( start)
	}


	public static addEndTestCase () {

		if (testCaseStarted) {

			testCaseStarted = false
			String cdt = GlobalVariable.CASDETESTENCOURS.toString() + ' : ' + TCFiles.getTitle()

			Date stop = new Date()

			XLSResult.addEndCasDeTest(status, start , stop,cdt)

			if (status.ERROR !=0) {
				Log.addERROR('END TEST CASE : ' + cdt.padRight(100, '.') +  ' Duration : ' + Tools.getDuration(start,stop))
			} else if (status.FAIL !=0) {
				Log.add('FAIL','END TEST CASE : ' + cdt.padRight(100, '.') +  ' Duration : ' + Tools.getDuration(start,stop))
			} else if (status.WARNING !=0) {
				Log.add('WARNING','END TEST CASE : ' + cdt.padRight(100, '.') +  ' Duration : ' + Tools.getDuration(start,stop))
			} else {
				Log.add('PASS','END TEST CASE : ' + cdt.padRight(100, '.') +  ' Duration : ' + Tools.getDuration(start,stop))
			}
		}
	}


	public static addStartInfo(String text) {

		XLSResult.addStartInfo(text)
	}



	public static addBrowserInfo() {

		String browserName = Tools.getBrowserName()
		String browserVersion = Tools.getBrowserVersion()

		addDETAIL("Nom du navigateur : " + browserName)
		addDETAIL("Version du navigateur : " + browserVersion)

		XLSResult.addBrowserInfo(browserName, browserVersion)
	}


	public static close(String text) {

		Log.addINFO('')
		Log.addINFO("************  FIN  du test : $text ************")

		XLSResult.close()
	}
}
