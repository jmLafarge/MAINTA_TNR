package tnrResultManager


import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrCommon.Tools
import tnrLog.Log
import tnrTC.TCFileMapper

@CompileStatic
public class TNRResult {


	private static Map <String,Integer> status = ['WARNING':0,'FAIL':0,'PASS':0,'ERROR':0]



	private static String PRESTEPTXT 		= '\tSTEP : '
	private static String PRESUBSTEPTXT		= '\t\t'
	private static String PREDETAILTXT		= '\t\t- '
	private static String PREINFOSTEPTXT	= '\t\t'



	private static boolean testCaseStarted = false
	private static Date start




	public static addDETAIL (String msg) {
		if (testCaseStarted) {
			addStepInResult(msg,'DETAIL')
		}
		Log.add('',PREDETAILTXT+ msg)
	}


	public static addSTEP (String stepID, String msg, String status = 'INFO') {

		switch (status) {
			case null :
			case 'INFO' :
				addSTEPINFO(stepID, msg)
				break
			case 'PASS':
				addSTEPPASS(stepID, msg)
				break
			case 'WARNING':
				addSTEPWARNING(stepID, msg)
				break
			case 'FAIL':
				addSTEPFAIL(stepID, msg)
				break
			case 'ERROR':
				addSTEPERROR(stepID, msg)
				break
			default :
				Log.add(status,PRESTEPTXT+ msg)
				break
		}
	}

	/*
	private static String formatStepIDAndMsg(String stepID, String msg) {
		
		return '(' + Tools.addZero(stepID, 3) + ') ' + msg
	}
	*/

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

	public static addSTEPINFO (String stepID, String msg) {
		msg = addStepIDInMsg(stepID, msg)
		Log.add('',PREINFOSTEPTXT+ msg)
		addStepInResult(msg,'INFO')
	}
	
	public static addSTEPPASS (String stepID, String msg) {
		msg = addStepIDInMsg(stepID, msg)
		Log.add('PASS',PRESTEPTXT+ msg)
		status.PASS++
		addStepInResult(msg,'PASS')
	}


	public static addSTEPFAIL (String stepID, String msg) {
		msg = addStepIDInMsg(stepID, msg)
		Log.add('FAIL',PRESTEPTXT+ msg)
		status.FAIL++
		addStepInResult(msg,'FAIL')
	}


	public static addSTEPWARNING (String stepID, String msg) {
		msg = addStepIDInMsg(stepID, msg)
		Log.add('WARNING',PRESTEPTXT+ msg)
		status.WARNING++
		addStepInResult(msg,'WARNING')
	}


	public static addSTEPERROR (String stepID, String msg) {
		msg = addStepIDInMsg(stepID, msg)
		Log.addERROR(PRESTEPTXT+ msg)
		status.ERROR++
		addStepInResult(msg,'ERROR')
	}



	public static addBeginBlock (String msg,String status='INFO') {
		addSTEP('',msg,status)
		XLSResult.beginBlock()
	}


	public static addEndBlock (String msg,String status) {
		addSTEP('',msg,status)
		XLSResult.endBlock()
	}



	private static addStepInResult(String msg, String status) {
		XLSResult.addStep(Log.logDate,msg,status)
	}
	
	
	private static String addStepIDInMsg(String stepID, String msg) {
		if (stepID) {
			return "($stepID) $msg"
		}else{
			return msg
		}
	}



	public static addStartTestCase (String cdt) {

		testCaseStarted = true

		GlobalVariable.CAS_DE_TEST_EN_COURS = cdt
		cdt += ' : ' + TCFileMapper.getTitle(cdt)
		status.WARNING = 0
		status.FAIL = 0
		status.PASS = 0
		status.ERROR = 0
		Log.add('','')
		Log.add('',"START TEST CASE : $cdt" )
		Log.setTabINFO(PRESUBSTEPTXT)
		start = Log.logDate
		XLSResult.addStartCasDeTest( start)
	}


	public static addEndTestCase () {

		if (testCaseStarted) {

			testCaseStarted = false
			String cdt = GlobalVariable.CAS_DE_TEST_EN_COURS.toString() + ' : ' + TCFileMapper.getTitle(GlobalVariable.CAS_DE_TEST_EN_COURS.toString())

			Date stop = new Date()

			XLSResult.addEndCasDeTest(status, start , stop,cdt)
			Log.setTabINFO('')
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

		Log.add('','')
		Log.add('',"************  FIN  du test : $text ************")

		XLSResult.close()
	}
}
