package zz


/*
 * use my.Log in the code without import --> to be sure that this Log is used
 * 
 * To log message in files
 * 
 */
class Log {

	private static Map status = ['WARNING':0,'FAIL':0,'PASS':0,'ERROR':0]

	private static String dateTimeFormat = 'yyyy-MM-dd HH:mm:ss.SSS'

	private static File file = this.createFile('')
	private static File fileDebug = this.createFile('DEBUG')

	private static int nbCarStatus = 7

	private static int debugLevel = 0

	private static String tab = ''

	private static String TCName = ''

	private static Date start


	/*
	 private static String PRESTEPTXT 	= '\t\t  STEP : '
	 private static String PRESUBSTEPTXT	= '\t\t\t    '
	 private static String PREDETAILTXT	= '\t\t\t    - '
	 private static String PREDEBUGTXT	= '\t\t\t\t  + '
	 */
	private static String PRESTEPTXT 	= '\tSTEP : '
	private static String PRESUBSTEPTXT	= '\t\t'
	private static String PREDETAILTXT	= '\t\t- '
	private static String PREDEBUGTXT	= '\t\t\t'

	private static File createFile(String txt){

		String dateFile = new Date().format("yyyyMMdd_HHmmss")

		File file =new File(my.PropertiesReader.getMyProperty('LOG_PATH') + File.separator +  dateFile + "-log${txt}.txt")

		this.debugLevel = my.PropertiesReader.getMyProperty('LOG_DEBUGLEVEL').toInteger()

		return file
	}


	private static String getStatFormat(String stat) {

		if (stat=='') {
			stat='-'*this.nbCarStatus
		}else {
			if (stat.length()> this.nbCarStatus) stat=stat.substring(0,this.nbCarStatus)
			stat=stat.center(this.nbCarStatus)
		}
	}


	private static add (String stat, String msg) {
		stat= this.getStatFormat(stat)
		String h = new Date().format(this.dateTimeFormat)
		this.file.append("[$h][$stat]:" + this.tab +"$msg\n")
		//println "[my Log][$stat]:" + this.tab +"$msg"
		this.fileDebug.append("[$h][$stat]:" + this.tab +"$msg\n")
	}


	public static addDEBUG (String msg, int level=1) {
		String stat= this.getStatFormat("  D $level  ")
		String h = new Date().format(this.dateTimeFormat)
		if (level <= this.debugLevel) {
			this.fileDebug.append("[$h][$stat]:" + this.PREDEBUGTXT + this.tab +"$msg\n")
			//println "[my Log][$stat]:" + this.tab +"$msg"
		}
	}





	public static addINFO (String msg,int level=0) {

		if (level==0) {
			this.add('',msg )
		}else {
			this.addDEBUG(msg,level )
		}
	}



	public static addWARNING (String msg) {
		this.add('WARNING',msg )
	}


	public static addFAIL (String msg) {
		this.add('FAIL',msg )
	}


	public static addPASS (String msg) {
		this.add('PASS',msg )
	}


	public static addERROR (String msg) {
		this.add('ERROR',msg )
		this.status.ERROR++
	}





	public static addDETAIL (String msg) {
		this.addINFO(this.PREDETAILTXT+ msg)
		this.addStepInResult(msg,'DETAIL')
	}


	public static addDETAILFAIL (String msg) {
		this.addFAIL(this.PREDETAILTXT+ msg)
	}

	public static addDETAILWARNING (String msg) {
		this.addWARNING(this.PREDETAILTXT+ msg)
	}

	public static addSTEP (String msg, String status = null) {
		switch (status) {
			case null :
				this.addINFO(this.PRESTEPTXT+ msg)
				this.addStepInResult(msg,'INFO')
				break
			case 'PASS':
				this.addSTEPPASS(msg)
				break
			case 'WARNING':
				this.addSTEPWARNING(msg)
				break
			case 'FAIL':
				this.addSTEPFAIL(msg)
				break
			case 'ERROR':
				this.addSTEPERROR(msg)
				break
			default :
				this.add(status,this.PRESTEPTXT+ msg)
				break
		}
	}


	public static addSUBSTEP (String msg) {
		this.addINFO(this.PRESUBSTEPTXT+ msg)
		this.addStepInResult(msg,'SUBSTEP')
	}

	
	
	public static addSTEPGRP (String msg) {
		this.addINFO('\t'+ msg.padRight(80, '_'))
		this.addStepInResult(msg,'STEPGRP')
	}


	public static addSTEPLOOP (String msg) {
		this.addINFO('\t'+ msg.padRight(40, '.'))
		this.addStepInResult(msg,'STEPLOOP')
	}


	public static addSTEPPASS (String msg) {
		this.addPASS(this.PRESTEPTXT+ msg)
		this.status.PASS++
		this.addStepInResult(msg,'PASS')
	}


	public static addSTEPFAIL (String msg) {
		this.addFAIL(this.PRESTEPTXT+ msg)
		this.status.FAIL++
		this.addStepInResult(msg,'FAIL')
	}


	public static addSTEPWARNING (String msg) {
		this.addWARNING(this.PRESTEPTXT+ msg)
		this.status.WARNING++
		this.addStepInResult(msg,'WARNING')
	}


	public static addSTEPERROR (String msg) {
		this.addERROR(this.PRESTEPTXT+ msg)
		this.addStepInResult(msg,'ERROR')
	}


	private static addStepInResult(String msg, String status) {

		my.Result.addStep(msg,status)
	}

	/*
	 *
	 */
	public static addStartTestCase (String testCaseName) {

		this.start = new Date()
		this.TCName = testCaseName
		this.status.WARNING = 0
		this.status.FAIL = 0
		this.status.PASS = 0
		this.status.ERROR = 0
		this.addINFO('')
		this.addINFO('START TEST CASE : '+ testCaseName )
		my.Result.addStartCasDeTest(this.TCName, this.start)
	}


	public static addEndTestCase () {

		Date stop = new Date()
		my.Result.addEndCasDeTest(this.TCName, this.status, this.start , stop)

		if (this.status.ERROR !=0) {
			this.addERROR('END TEST CASE : ' + this.TCName.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(this.start,stop))
		} else if (this.status.FAIL !=0) {
			this.addFAIL('END TEST CASE : ' + this.TCName.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(this.start,stop))
		} else if (this.status.WARNING !=0) {
			this.addWARNING('END TEST CASE : ' + this.TCName.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(this.start,stop))
		} else {
			this.addPASS('END TEST CASE : ' + this.TCName.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(this.start,stop))
		}

		this.TCName = ''
	}



	public static addTITLE(String title, String car ='*',int nbcar = 140,int level=0) {
		if (title.length()+4 >= nbcar) nbcar = title.length()+4

		this.addINFO('',level)
		this.addINFO(car*nbcar,level)
		this.addINFO(car + ' ' * (nbcar-2) + car,level)
		this.addINFO(car + title.center(nbcar-2) + car,level)
		this.addINFO(car + ' ' * (nbcar-2) + car,level)
		this.addINFO(car*nbcar,level)
		this.addINFO('',level)
	}


	public static addSubTITLE(String subtitle, String car ='-', int nbcar = 120 ,int level=0) {
		this.addINFO('',level)
		this.addINFO(car*nbcar,level)
		this.addINFO(subtitle,level)
		this.addINFO(car*nbcar,level)
		this.addINFO('',level)
	}
}// end of class