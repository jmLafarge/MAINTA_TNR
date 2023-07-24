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

	// not used for the moment
	private static enum AllowedStatus {
		PASS,
		WARNING,
		FAIL,
		ERROR
	}


	public static Date logDate = null

	private static String dateTimeFormat = 'yyyy-MM-dd HH:mm:ss.SSS'

	private static File file = createFile('')
	private static File fileDebug = createFile('DEBUG')

	private static int nbCarStatus = 7

	private static int debugLevel = 0
	private static int debugDeph = 0
	private static int deph = 0

	private static String tab = ''

	private static String PREDEBUGTXT	= '\t\t'
	private static String PREDETAILTXT	= '\t\t- '

	private static Map<String, ArrayList> lists = [:]



	private static File createFile(String txt){



		//Create folder if not exist
		File dir = new File(my.PropertiesReader.getMyProperty('LOG_PATH'))
		if (!dir.exists()) dir.mkdirs()

		String dateFile = new Date().format("yyyyMMdd_HHmmss")

		File file =new File(my.PropertiesReader.getMyProperty('LOG_PATH') + File.separator +  dateFile + "-log${txt}.txt")

		debugLevel = my.PropertiesReader.getMyProperty('LOG_DEBUGLEVEL').toInteger()
		debugDeph = my.PropertiesReader.getMyProperty('LOG_DEBUGDEPH').toInteger()

		if (txt=='DEBUG') {
			file.append("debugLevel = $debugLevel\ndebugDeph = $debugDeph\n")
		}

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


	public static addB ( String msg) {

		file.append("\t\t$msg\n")

		fileDebug.append("\t\t$msg\n")
	}


	public static addDEBUG (String msg, int level=1) {
		String stat= getStatusFormat("  D $level  ")
		String h = new Date().format(dateTimeFormat)
		if (level <= debugLevel) {
			fileDebug.append("[$h][$stat]:" + PREDEBUGTXT + tab +"$msg\n")
			//println "[my Log][$stat]:" + tab +"$msg"
		}
	}


	public static addTrace (String msg, int level=1) {

		if (deph <= debugDeph) {
			addDEBUG(msg,level)
			tab = '\t'.multiply(deph)
		}
	}


	public static addTraceBEGIN (String msg, int level=1) {

		deph++
		if (deph <= debugDeph) {
			addDEBUG('->> '+msg,level)
			tab = '\t'.multiply(deph)
		}
	}


	public static addTraceEND (String msg, def ret = null, int level=1) {

		deph = (deph > 0) ? deph - 1 : 0
		if (deph < debugDeph) {
			tab = '\t'.multiply(deph)
			String r = ret?" --> '$ret'":' ---'
			addDEBUG('<<- '+msg+r,level)
		}

	}

	public static addDEBUGDETAIL (String msg, int level=1) {
		addTrace('- '+ msg,level)
	}



	public static addINFO (String msg,int level=0) {

		if (level==0) {
			add('',msg)
		}else {
			addTrace(msg,level )
		}
	}





	public static addERROR (String msg) {
		add('ERROR',msg )
	}


	public static addDETAIL (String msg,int level=0) {

		if (level==0) {
			add('','\t'+ msg)
		}else {
			addTrace(msg,level )
		}
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




	public static  addToList(String nomDeLaListe, String valeurDeLaListe) {

		def liste = lists.get(nomDeLaListe)
		if (liste == null) {
			liste = []  //
			lists.put(nomDeLaListe, liste)
		}
		liste.add(valeurDeLaListe)
	}


	public static writeList(String nomDeLaListe, String status ='') {

		def liste = lists.get(nomDeLaListe)
		if (liste != null) {
			liste.each { val ->
				add(status,PREDETAILTXT+ val)
			}
		} else {
			addTrace("La liste \"$nomDeLaListe\" n'existe pas.")
		}
	}

	public static setDebugLevel(int newLevel) {
		debugLevel = newLevel
		fileDebug.append("New debug level = $debugLevel\n")
	}


	public static setDebugDeph(int newDeph) {
		debugDeph = newDeph
		fileDebug.append("New debug deph = $debugDeph\n")
	}



}// end of class