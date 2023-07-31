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

	private static File file
	private static File fileDebug

	private static int nbCarStatus = 7

	private static int debugLevel = 0
	private static int level = 0

	private static List <String> debugClassesExcluded = []
	private static List <String> debugClassesAdded = []
	private static List <String> debugFunctionsExcluded = []
	private static List <String> debugFunctionsAdded = []

	private static List<Map<String, Boolean>> traceList = []

	private static boolean traceAllowed = true

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

		debugLevel = my.PropertiesReader.getMyProperty('LOG_DEBUGLEVEL').toInteger()

		String debugClassList = my.PropertiesReader.getMyProperty('LOG_DEBUGCLASSES')


		if (debugClassList) {
			debugClassList = debugClassList.replaceAll("[\\s\\t]+", "") // Supprime les espaces et les tabulations
			List <String> classList = debugClassList.split(',') as List
			debugClassesExcluded = classList.findAll { it[0] == '-' }.collect { it.substring(1) }
			debugClassesAdded = classList.findAll { it[0] == '+' }.collect { it.substring(1) }
		}

		String debugFunctionList = my.PropertiesReader.getMyProperty('LOG_DEBUGFUNCTION')

		if (debugFunctionList) {
			debugFunctionList = debugFunctionList.replaceAll("[\\s\\t]+", "") // Supprime les espaces et les tabulations
			List <String> functionList = debugFunctionList.split(',') as List
			debugFunctionsExcluded = functionList.findAll { it[0] == '-' }.collect { it.substring(1) }
			debugFunctionsAdded = functionList.findAll { it[0] == '+' }.collect { it.substring(1) }
		}


		add('','Fichier log de Katalon TNR')
		addDEBUG("Debug level : $debugLevel")
		addDEBUG('Debug classes excluded   = ' + debugClassesExcluded + '\tThese classes will never be traced' )
		addDEBUG('Debug classes added      = ' + debugClassesAdded + '\tThese classes will always be traced')
		addDEBUG('Debug functions excluded = ' + debugFunctionsExcluded + '\tThese functions will never be traced' )
		addDEBUG('Debug functions added    = ' + debugFunctionsAdded + '\tThese functions will always be traced')
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
		println "[my Log][$stat]:" + tab +"$msg"
	}





	private static String getClassFunction(String name) {
		name = name.replaceAll(' ', '') // Supprime les espaces
		boolean msgFunctionOK = (name ==~ /^[a-zA-Z0-9-_]+\.[a-zA-Z0-9-_]+\(.*/)
		boolean msgStaticOK = (name ==~ /^[a-zA-Z0-9-_]+\.static$/)

		if (msgFunctionOK) {
			return name.substring(0, name.indexOf('('))
		}
		if (msgStaticOK) {
			return name
		}
		addDEBUG("*** ERREUR TRACE *** getClassFunction() Le nom Class.Function '$name' n'est pas conforme - ARRET DU PROGRAMME")
		System.exit(0)
	}




	private static addToTraceList(String name) {

		traceList.add([NAME:name,TRACE:traceAllowed] as LinkedHashMap<String, Boolean>)

	}

	private static delToTraceList(String name) {

		int lastIdx = traceList.size() - 1

		if (traceList[lastIdx]['NAME']== name){
			traceList.remove(lastIdx)
		}else {
			addDEBUG("*** ERREUR TRACE *** delToTraceList(). Le nom Class.Function '$name' n'a pas été trouvé - ARRET DU PROGRAMME")
			System.exit(0)
		}

		if (lastIdx==0) {
			traceAllowed = true
		}else {
			traceAllowed = traceList[lastIdx-1]['TRACE']
		}

	}




	public static addTrace (String msg) {
		if (traceAllowed) {
			addDEBUG("$msg")
		}
	}




	public static addTraceBEGIN (String msg) {
		level++
		setTraceAllowed(msg,level)
		addToTraceList(getClassFunction(msg))

		if (traceAllowed) {
			addDEBUG(STRBEGIN + msg)
		}
		tab = STRTABSEP.multiply(level) //
	}




	public static addTraceEND (String msg, def ret = null) {
		level = (level > 0) ? level - 1 : 0
		tab = STRTABSEP.multiply(level) //

		if (traceAllowed ) {
			String r = ret?" --> '$ret'":' ---'
			addDEBUG(STREND + msg + r)
		}
		delToTraceList(getClassFunction(msg))
	}




	private static setTraceAllowed(String msg, int level) {

		traceAllowed =  (level <= debugLevel)

		boolean classExcludedStartsWith = debugClassesExcluded.any { msg.startsWith(it+'.') }
		boolean classAddedStartsWith = debugClassesAdded.any { msg.startsWith(it+'.') }
		boolean functionExcludedStartsWith = debugFunctionsExcluded.any { msg.startsWith(it) }
		boolean functionAddedStartsWith = debugFunctionsAdded.any { msg.startsWith(it) }

		if (classExcludedStartsWith) {
			traceAllowed = false
		}else if (classAddedStartsWith) {
			traceAllowed= true
		}
		if (functionExcludedStartsWith) {
			traceAllowed = false
		}else if (functionAddedStartsWith) {
			traceAllowed= true
		}

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



	public static setDebugLevel(int newLevel) {
		debugLevel = newLevel
		fileDebug.append("New debug level = $debugLevel\n")
	}

	public static setDebugClasses(String[] newClasses) {
		debugClasses = newClasses
		fileDebug.append("New debug Classes = $newClasses\n")
	}


}// end of class