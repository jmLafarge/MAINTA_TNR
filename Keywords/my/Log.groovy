package my


import internal.GlobalVariable
import my.result.ResultGenerator as MYRES

/*
 * use my.Log in the code without import --> to be sure that this Log is used
 * 
 * To log message in files
 * 
 */
class Log {

	private static Map status = ['WARNING':0,'FAIL':0,'PASS':0,'ERROR':0]

	private static String dateTimeFormat = 'yyyy-MM-dd HH:mm:ss.SSS'

	private static File file = createFile('')
	private static File fileDebug = createFile('DEBUG')

	private static int nbCarStatus = 7

	private static int debugLevel = 0

	private static String tab = ''

	private static boolean testCaseStarted = false
	private static Date start
	private static Date logDate


	/*
	 private static String PRESTEPTXT 	= '\t\t  STEP : '
	 private static String PRESUBSTEPTXT	= '\t\t\t    '
	 private static String PREDETAILTXT	= '\t\t\t    - '
	 private static String PREDEBUGTXT	= '\t\t\t\t  + '
	 */
	private static String PRESTEPTXT 	= '\tSTEP : '
	private static String PRESUBSTEPTXT	= '\t\t'
	private static String PREDETAILTXT	= '\t\t- '
	private static String PREDEBUGTXT	= '\t\t'

	private static File createFile(String txt){

		//Create folder if not exist
		File dir = new File(my.PropertiesReader.getMyProperty('LOG_PATH'))
		if (!dir.exists()) dir.mkdirs()

		String dateFile = new Date().format("yyyyMMdd_HHmmss")

		File file =new File(my.PropertiesReader.getMyProperty('LOG_PATH') + File.separator +  dateFile + "-log${txt}.txt")

		debugLevel = my.PropertiesReader.getMyProperty('LOG_DEBUGLEVEL').toInteger()

		return file
	}


	private static String getStatFormat(String stat) {

		if (stat=='') {
			stat='-'*nbCarStatus
		}else {
			if (stat.length()> nbCarStatus) stat=stat.substring(0,nbCarStatus)
			stat=stat.center(nbCarStatus)
		}
	}


	private static add (String stat, String msg) {
		stat= getStatFormat(stat)
		logDate = new Date()
		String h = logDate.format(dateTimeFormat)
		file.append("[$h][$stat]:" + tab +"$msg\n")
		//println "[my Log][$stat]:" + tab +"$msg"
		fileDebug.append("[$h][$stat]:" + tab +"$msg\n")
	}


	public static addDEBUG (String msg, int level=1) {
		String stat= getStatFormat("  D $level  ")
		String h = new Date().format(dateTimeFormat)
		if (level <= debugLevel) {
			fileDebug.append("[$h][$stat]:" + PREDEBUGTXT + tab +"$msg\n")
			//println "[my Log][$stat]:" + tab +"$msg"
		}
	}


	public static addDEBUGDETAIL (String msg, int level=1) {
		addDEBUG('- '+ msg,level)
	}



	public static addINFO (String msg,int level=0) {

		if (level==0) {
			add('',msg )
		}else {
			addDEBUG(msg,level )
		}
	}



	public static addWARNING (String msg) {
		add('WARNING',msg )
	}


	public static addFAIL (String msg) {
		add('FAIL',msg )
	}


	public static addPASS (String msg) {
		add('PASS',msg )
	}


	public static addERROR (String msg) {
		add('ERROR',msg )
		// status.ERROR++ // 20230329 j'ai mis cette ligne en commentaire, on ne veut que les STEP ERROR
	}





	public static addDETAIL (String msg) {
		addINFO(PREDETAILTXT+ msg)
		addStepInResult(msg,'DETAIL')
	}


	public static addDETAILFAIL (String msg) {
		addFAIL(PREDETAILTXT+ msg)
	}

	public static addDETAILWARNING (String msg) {
		addWARNING(PREDETAILTXT+ msg)
	}

	public static addSTEP (String msg, String status = null) {
		switch (status) {
			case null :
				addINFO(PRESTEPTXT+ msg)
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
				add(status,PRESTEPTXT+ msg)
				break
		}
	}



	public static addSUBSTEP (String msg) {
		addINFO(PRESUBSTEPTXT+ msg)
		addStepInResult(msg,'SUBSTEP')
	}


	public static addSTEPACTION (String msg) {
		addINFO('\t'+ msg.padRight(90, '_'))
		addStepInResult(msg,'STEPACTION')
	}


	public static addSTEPGRP (String msg) {
		addINFO('\t'+ msg.padRight(90, '_'))
		addStepInResult(msg,'STEPGRP')
	}

	public static addSTEPBLOCK (String msg) {
		addINFO('\t'+ msg.center(70, '-'))
		addStepInResult(msg,'STEPBLOCK')
	}



	public static addSTEPLOOP (String msg) {
		addINFO('\t'+ msg.padRight(40, '.'))
		addStepInResult(msg,'STEPLOOP')
	}


	public static addSTEPPASS (String msg) {
		addPASS(PRESTEPTXT+ msg)
		status.PASS++
		addStepInResult(msg,'PASS')
	}


	public static addSTEPFAIL (String msg) {
		addFAIL(PRESTEPTXT+ msg)
		status.FAIL++
		addStepInResult(msg,'FAIL')
	}


	public static addSTEPWARNING (String msg) {
		addWARNING(PRESTEPTXT+ msg)
		status.WARNING++
		addStepInResult(msg,'WARNING')
	}


	public static addSTEPERROR (String msg) {
		addERROR(PRESTEPTXT+ msg)
		status.ERROR++
		addStepInResult(msg,'ERROR')
	}





	private static addStepInResult(String msg, String status) {

		if (MYRES.resulFileName) MYRES.addStep(logDate,msg,status)
	}




	public static addStartTestCase (String cdt) {

		testCaseStarted = true

		GlobalVariable.CASDETESTENCOURS = cdt
		cdt += ' : ' + TCFiles.getTitle()
		status.WARNING = 0
		status.FAIL = 0
		status.PASS = 0
		status.ERROR = 0
		addINFO('')
		addINFO("START TEST CASE : $cdt" )
		start = logDate
		if (MYRES.resulFileName) MYRES.addStartCasDeTest( start)
	}


	public static addEndTestCase () {

		if (testCaseStarted) {

			testCaseStarted = false
			String cdt = GlobalVariable.CASDETESTENCOURS + ' : ' + TCFiles.getTitle()

			Date stop = new Date()
			if (MYRES.resulFileName) MYRES.addEndCasDeTest(status, start , stop,cdt)

			if (status.ERROR !=0) {
				addERROR('END TEST CASE : ' + cdt.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(start,stop))
			} else if (status.FAIL !=0) {
				addFAIL('END TEST CASE : ' + cdt.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(start,stop))
			} else if (status.WARNING !=0) {
				addWARNING('END TEST CASE : ' + cdt.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(start,stop))
			} else {
				addPASS('END TEST CASE : ' + cdt.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(start,stop))
			}
		}
	}



	public static addTITLE(String title, String car ='*',int nbcar = 140,int level=0) {
		if (title.length()+4 >= nbcar) nbcar = title.length()+4

		addINFO('',level)
		addINFO(car*nbcar,level)
		addINFO(car + ' ' * (nbcar-2) + car,level)
		addINFO(car + title.center(nbcar-2) + car,level)
		addINFO(car + ' ' * (nbcar-2) + car,level)
		addINFO(car*nbcar,level)
		addINFO('',level)
	}


	public static addSubTITLE(String subtitle, String car ='-', int nbcar = 120 ,int level=0) {
		addINFO('',level)
		addINFO(car*nbcar,level)
		addINFO(subtitle,level)
		addINFO(car*nbcar,level)
		addINFO('',level)
	}
}// end of class