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

	//private static File file = createFile('')
	//private static File fileDebug = createFile('DEBUG')
	private static File file
	private static File fileDebug

	private static int nbCarStatus = 7

	private static int debugDeph = 0
	private static int deph = 0

	private static List <String> debugClassesExcluded = []
	private static List <String> debugClassesAdded = []

	private static String tab = ''
	private static final String STRTABSEP = ' |\t'
	private static final String STRBEGIN = '=> '
	private static final String STREND = '<= '

	private static String PREDEBUGTXT	= '\t'
	private static String PREDETAILTXT	= '\t- '

	private static Map<String, ArrayList> lists = [:]



	static {

		//Create folder if not exist
		File dir = new File(my.PropertiesReader.getMyProperty('LOG_PATH'))
		if (!dir.exists()) dir.mkdirs()

		String dateFile = new Date().format("yyyyMMdd_HHmmss")

		file 		=new File(my.PropertiesReader.getMyProperty('LOG_PATH') + File.separator +  dateFile + "-log.txt")
		fileDebug 	=new File(my.PropertiesReader.getMyProperty('LOG_PATH') + File.separator +  dateFile + "-logDEBUG.txt")

		debugDeph = my.PropertiesReader.getMyProperty('LOG_DEBUGDEPH').toInteger()

		String ccc = my.PropertiesReader.getMyProperty('LOG_DEBUGCLASSES')

		if (ccc) {
			List <String> classList = ccc.split(',') as List
			debugClassesExcluded = classList.findAll { it[0] == '-' }.collect { it.substring(1) }
			debugClassesAdded = classList.findAll { it[0] == '+' }.collect { it.substring(1) }
		}


		add('','Fichier log de Katalon TNR')

		addDEBUG('Debug class excluded = ' + debugClassesExcluded + '\tThese classes will never be traced' )
		addDEBUG('Debug class added    = ' + debugClassesAdded + '\tThese classes will always be traced')

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
		file.append("[$h][$stat]:" +"$msg\n")
		//println "[my Log][$stat]:" +"$msg"
		//fileDebug.append("[$h][$stat]:" +"$msg\n")
		fileDebug.append("[$h][$stat]:" + PREDEBUGTXT + tab +"$msg\n")
	}


	public static addB ( String msg) {

		file.append("\t\t$msg\n")
		fileDebug.append("\t\t$msg\n")
	}


	public static addDEBUG (String msg) {
		String stat= getStatusFormat("  D  ")
		String h = new Date().format(dateTimeFormat)
		fileDebug.append("[$h][$stat]:" + PREDEBUGTXT + tab +"$msg\n")
		//println "[my Log][$stat]:" + tab +"$msg"
	}


	public static addTrace (String msg) {
		if (isTraceAuthorized(msg,deph) ) {
			addDEBUG("$msg")
			//tab = STRTABSEP.multiply(deph)
		}
	}


	public static addTraceBEGIN (String msg) {
		deph++
		if (isTraceAuthorized(msg,deph) ) {
			addDEBUG(STRBEGIN + msg)
			//tab = STRTABSEP.multiply(deph)
		}
		tab = STRTABSEP.multiply(deph) //
	}


	public static addTraceEND (String msg, def ret = null) {
		deph = (deph > 0) ? deph - 1 : 0
		tab = STRTABSEP.multiply(deph) //
		if (isTraceAuthorized(msg,deph+1) ) {
			//tab = STRTABSEP.multiply(deph)
			String r = ret?" --> '$ret'":' ---'
			addDEBUG(STREND + msg + r)
		}
		
	}

	
	private static boolean isTraceAuthorized(String msg, int deph) {

		boolean ret =  (deph <= debugDeph)
		boolean startsWithExcluded = debugClassesExcluded.any { msg.startsWith(it) }
		boolean startsWithAdded = debugClassesAdded.any { msg.startsWith(it) }
		if (startsWithExcluded) {
			ret = false
		}else if (startsWithAdded) {
			ret= true
		}
		return ret
	}

	
	public static addDEBUGDETAIL (String msg) {
		addTrace('- '+ msg)
	}



	public static addINFO (String msg, boolean traceMode = false) {
		
		if (traceMode) {
			addTrace(msg)
		}else {
			add('',msg)
		}
	}





	public static addERROR (String msg) {
		add('ERROR',msg )
	}


	public static addDETAIL (String msg) {
		add('','\t'+ msg)
	}

	public static addDETAILFAIL (String msg) {
		add('FAIL',PREDETAILTXT+ msg)
	}

	public static addDETAILWARNING (String msg) {
		add('WARNING',PREDETAILTXT+ msg)
	}




	public static addTITLE(String title, String car ='*',int nbcar = 140, boolean traceMode = false) {
		if (title.length()+4 >= nbcar) nbcar = title.length()+4

		addINFO('',traceMode)
		addINFO(car*nbcar,traceMode)
		addINFO(car + ' ' * (nbcar-2) + car,traceMode)
		addINFO(car + title.center(nbcar-2) + car,traceMode)
		addINFO(car + ' ' * (nbcar-2) + car,traceMode)
		addINFO(car*nbcar,traceMode)
		addINFO('',traceMode)
	}


	public static addSubTITLE(String subtitle, String car ='-', int nbcar = 120, boolean traceMode = false)  {
		addINFO('',traceMode)
		addINFO(car*nbcar,traceMode)
		addINFO(subtitle,traceMode)
		addINFO(car*nbcar,traceMode)
		addINFO('',traceMode)
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



	public static setDebugDeph(int newDeph) {
		debugDeph = newDeph
		fileDebug.append("New debug deph = $debugDeph\n")
	}

	public static setDebugClasses(String[] newClasses) {
		debugClasses = newClasses
		fileDebug.append("New debug Classes = $newClasses\n")
	}


}// end of class