package my


import groovy.transform.CompileStatic


/*
 * use my.Log in the code without import --> to be sure that this Log is used
 * 
 * To log message in files
 * 
 */
@CompileStatic
class Log {


	public static Date logDate = null

	private static String dateTimeFormat = 'yyyy-MM-dd HH:mm:ss.SSS'

	private static File file = createFile('')
	private static File fileDebug = createFile('DEBUG')

	private static int nbCarStatus = 7

	private static int debugLevel = 0

	private static String tab = ''


	private static String PREDEBUGTXT	= '\t\t'
	private static String PREDETAILTXT	= '\t\t- '





	private static File createFile(String txt){

		//Create folder if not exist
		File dir = new File(my.PropertiesReader.getMyProperty('LOG_PATH'))
		if (!dir.exists()) dir.mkdirs()

		String dateFile = new Date().format("yyyyMMdd_HHmmss")

		File file =new File(my.PropertiesReader.getMyProperty('LOG_PATH') + File.separator +  dateFile + "-log${txt}.txt")

		debugLevel = my.PropertiesReader.getMyProperty('LOG_DEBUGLEVEL').toInteger()

		return file
	}



	private static String getStatusFormat(String stat) {

		if (stat=='') {
			stat='-'*nbCarStatus
		}else {
			if (stat.length()> nbCarStatus) stat=stat.substring(0,nbCarStatus)
			stat=stat.center(nbCarStatus)
		}
	}



	public static add (String stat, String msg) {
		stat= getStatusFormat(stat)
		logDate = new Date()
		String h = logDate.format(dateTimeFormat)
		file.append("[$h][$stat]:" + tab +"$msg\n")
		//println "[my Log][$stat]:" + tab +"$msg"
		fileDebug.append("[$h][$stat]:" + tab +"$msg\n")
	}



	public static addDEBUG (String msg, int level=1) {
		String stat= getStatusFormat("  D $level  ")
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
			add('',msg)
		}else {
			addDEBUG(msg,level )
		}
	}





	public static addERROR (String msg) {
		add('ERROR',msg )
	}




	public static addDETAILFAIL (String msg) {
		add('FAIL',PREDETAILTXT+ msg)
	}

	public static addDETAILWARNING (String msg) {
		add('WARNING',PREDETAILTXT+ msg)
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