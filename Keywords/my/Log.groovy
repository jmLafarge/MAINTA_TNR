package my


import com.kms.katalon.core.util.KeywordUtil

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

	private static Date startLog = null
	private static int debugLevel = 0
	private static int level = 0
	private static int maxLevel = 0

	private static List <String> debugClassesExcluded = []
	private static List <String> debugClassesAdded = []
	private static List <String> debugFunctionsExcluded = []
	private static List <String> debugFunctionsAdded = []

	private static List<Map<String, Boolean>> traceList = []

	private static boolean traceEnd 	= false
	private static boolean traceAllowed = true

	private static String tab = ''
	private static final String STRTABSEP = ' |\t'
	private static final String STRBEGIN = '>> '
	private static final String STREND = '<< '

	private static String PREDEBUGTXT	= '\t'
	private static String PREDETAILTXT	= '\t- '

	private static Map<String, ArrayList> lists = [:]



	static {

		//Create folder if not exist
		File dir = new File(my.PropertiesReader.getMyProperty('LOG_PATH'))
		if (!dir.exists()) dir.mkdirs()

		String dateFile = new Date().format("yyyyMMdd_HHmmss")
		startLog = new Date()

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
		if (!traceEnd) {
			stat= getStatusFormat(stat)
			logDate = new Date()
			String h = logDate.format(dateTimeFormat)
			file.append("[$h][$stat]:" +"$msg\n")
			//println "[my Log][$stat]:" +"$msg"
			//fileDebug.append("[$h][$stat]:" +"$msg\n")
			fileDebug.append("[$h][$stat]:" + PREDEBUGTXT + tab +"$msg\n")
		}
	}


	public static addB ( String msg) {
		if (!traceEnd) {
			file.append("\t\t$msg\n")
			fileDebug.append("\t\t$msg\n")
		}
	}


	public static addDEBUG (String msg) {
		if (!traceEnd) {
			String lev = (level>=0 && level<=9)?"0$level":"$level"
			String stat= getStatusFormat(" D$lev ")
			String h = new Date().format(dateTimeFormat)
			fileDebug.append("[$h][$stat]:" + PREDEBUGTXT + tab +"$msg\n")
			println "[my Log][$stat]:" + tab +"$msg"
		}
	}



	private static String getClassFunction(String myClass, String myFunction) {

		return myClass +'.' + myFunction

	}


	private static addToTraceList(String myClass, String myFunction) {

		//String classFunction = myClass +'.' + myFunction
		traceList.add([CLASSFUNCTION:getClassFunction(myClass,myFunction),TRACE:traceAllowed] as LinkedHashMap<String, Boolean>)
	}


	private static delToTraceList(String myClass, String myFunction) {

		int lastIdx = traceList.size() - 1

		//String classFunction = myClass +'.' + myFunction

		if (traceList[lastIdx]['CLASSFUNCTION']== getClassFunction(myClass,myFunction) ){
			traceList.remove(lastIdx)
		}else {
			addErrorAndStop("*** ERREUR TRACE *** delToTraceList(). La Class.Function '${getClassFunction(myClass,myFunction)}' n'a pas été trouvée.")
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




	public static addTraceBEGIN (String myClass, String myFunction, Map paras) {
		level++
		maxLevel = (level>maxLevel) ? level : maxLevel
		setTraceAllowed(myClass,myFunction,level)
		addToTraceList(myClass, myFunction)

		if (traceAllowed) {

			String para = ''
			if (paras) {
				para = paras.collect { cle, valeur -> "$cle: '$valeur'" }.join(', ')
			}
			addDEBUG(STRBEGIN + getClassFunction(myClass,myFunction) + '(' + para + ')')
		}
		tab = STRTABSEP.multiply(level) //
	}




	public static addTraceEND (String myClass, String myFunction, def ret = null) {
		level = (level > 0) ? level - 1 : 0
		tab = STRTABSEP.multiply(level) //

		if (traceAllowed) {
			String r = ret?" <-- '$ret'":' ---'
			addDEBUG(STREND + getClassFunction(myClass,myFunction) + '()' + r)
		}
		delToTraceList(myClass, myFunction)
	}




	private static setTraceAllowed(String myClass, String myFunction, int level) {

		traceAllowed =  (level <= debugLevel)

		if (debugClassesExcluded.contains(myClass)) {
			traceAllowed = false
		}else if (debugClassesAdded.contains(myClass)) {
			traceAllowed= true
		}
		if (debugFunctionsExcluded.contains(getClassFunction(myClass,myFunction))) {
			traceAllowed = false
		}else if (debugFunctionsAdded.contains(getClassFunction(myClass,myFunction))) {
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





	public static addErrorAndStop (String msg) {
		add('ERROR',msg )
		KeywordUtil.markErrorAndStop(msg + 'ARRET DU PROGRAMME')
		System.exit(0)
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
			addTrace("La liste '$nomDeLaListe' n'existe pas.")
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
	

	public static addEndLog(String msg='') {
		addTrace(msg)
		traceEnd=true
		Date stop = new Date()
		fileDebug.append("Max Level = $maxLevel\n")
		fileDebug.append('Time Duration : ' + Tools.getDuration(startLog,stop))
	}
	
	

}// end of class