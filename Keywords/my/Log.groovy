package my

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

	private static String PRESTEPTXT 	= '            STEP : '
	private static String PRESUBSTEPTXT	= '                   '
	private static String PREDETAILTXT	= '                   - '



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
	

	static public addDEBUG (String msg, int level=1) {
		String stat= this.getStatFormat('DEBUG ' + level)
		String h = new Date().format(this.dateTimeFormat)
		if (level <= this.debugLevel) this.fileDebug.append("[$h][$stat]:" + this.tab +"$msg\n")
	}

	static public addINFO (String msg) {

		this.add('',msg )
	}



	static public addWARNING (String msg) {
		this.add('WARNING',msg )
	}


	static public addFAIL (String msg) {
		this.add('FAIL',msg )
	}


	static public addPASS (String msg) {
		this.add('PASS',msg )
	}


	static public addERROR (String msg) {
		this.add('ERROR',msg )
		this.status.ERROR++
	}


	static public addDETAIL (String msg) {
		this.addINFO(this.PREDETAILTXT+ msg)
	}


	static public addDETAILFAIL (String msg) {
		this.addFAIL(this.PREDETAILTXT+ msg)
	}


	static public addSTEP (String msg, String status = null) {
		switch (status) {
			case null :
				this.addINFO(this.PRESTEPTXT+ msg)
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
			default :
				this.add(status,this.PRESTEPTXT+ msg)
				break
		}
	}


	static public addSUBSTEP (String msg) {
		this.addINFO(this.PRESUBSTEPTXT+ msg)
	}


	static public addSTEPGRP (String msg) {
		this.addINFO('      '+ msg.padRight(80, '_'))
	}


	static public addSTEPLOOP (String msg) {
		this.addINFO('            '+ msg.padRight(40, '.'))
	}


	static public addSTEPPASS (String msg) {
		this.addPASS(this.PRESTEPTXT+ msg)
		this.status.PASS++
	}


	static public addSTEPFAIL (String msg) {
		this.addFAIL(this.PRESTEPTXT+ msg)
		this.status.FAIL++
	}


	static public addSTEPWARNING (String msg) {
		this.addWARNING(this.PRESTEPTXT+ msg)
		this.status.WARNING++
	}




	/*
	 *
	 */
	static public addStartTestCase (String testCaseName) {

		this.start = new Date()
		this.TCName = testCaseName
		this.status.WARNING = 0
		this.status.FAIL = 0
		this.status.PASS = 0
		this.status.ERROR = 0
		this.addINFO('')
		this.addINFO('START  TEST CASE : '+ testCaseName )
	}


	static public addEndTestCase () {

		Date stop = new Date()
		my.Result.addCasDeTest(this.TCName, this.status, this.start , stop)

		if (this.status.ERROR !=0) {
			this.addERROR('END    TEST CASE : ' + this.TCName.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(this.start,stop))
		} else if (this.status.FAIL !=0) {
			this.addFAIL('END    TEST CASE : ' + this.TCName.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(this.start,stop))
		} else if (this.status.WARNING !=0) {
			this.addWARNING('END    TEST CASE : ' + this.TCName.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(this.start,stop))
		} else {
			this.addPASS('END    TEST CASE : ' + this.TCName.padRight(100, '.') +  ' Duration : ' + my.Tools.getDuration(this.start,stop))
		}

		this.TCName = ''
	}



	static public addTITLE(String title, String car ='*',int nbcar = 140) {
		if (title.length()+4 >= nbcar) nbcar = title.length()+4

		this.addINFO('')
		this.addINFO(car*nbcar)
		this.addINFO(car + ' ' * (nbcar-2) + car)
		this.addINFO(car + title.center(nbcar-2) + car)
		this.addINFO(car + ' ' * (nbcar-2) + car)
		this.addINFO(car*nbcar)
		this.addINFO('')
	}


	static public addSubTITLE(String subtitle, String car ='-', int nbcar = 120 ) {
		this.addINFO('')
		this.addINFO(car*nbcar)
		this.addINFO(subtitle)
		this.addINFO(car*nbcar)
		this.addINFO('')
	}
}// end of class