package tnrLog


import com.kms.katalon.core.util.KeywordUtil

import groovy.transform.CompileStatic
import tnrCommon.TNRPropertiesReader
import tnrCommon.Tools


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
	private static int traceLevel = 0
	private static int level = 0
	private static int maxLevel = 0
	private static String tabINFO = ''

	private static List <String> traceClassesExcluded = []
	private static List <String> traceClassesAdded = []
	private static List <String> traceFunctionsExcluded = []
	private static List <String> traceFunctionsAdded = []

	private static List<Map<String, Boolean>> traceList = []

	private static boolean traceEnd 	= false
	private static boolean traceAllowed = true

	private static String tab = ''
	private static final String STRTABSEP = ' |\t'
	private static final String STRBEGIN = '>> '
	private static final String STREND = '<< '

	private static String PREDEBUGTXT	= '\t'
	private static String PREDETAILTXT	= '\t- '

	private static Map<String, ArrayList<String>> lists = [:]

	private static int nbrAssert = 0
	private static int nbrAssertKO = 0


	static {

		//Create folder if not exist
		File dir = new File(TNRPropertiesReader.getMyProperty('LOG_PATH'))
		if (!dir.exists()) dir.mkdirs()

		String dateFile = new Date().format("yyyyMMdd_HHmmss")
		startLog = new Date()

		file 		=new File(TNRPropertiesReader.getMyProperty('LOG_PATH') + File.separator +  dateFile + "-log.txt")
		fileDebug 	=new File(TNRPropertiesReader.getMyProperty('LOG_PATH') + File.separator +  dateFile + "-logDEBUG.txt")

		traceLevel = TNRPropertiesReader.getMyProperty('LOG_TRACE_LEVEL').toInteger()

		String traceClassList = TNRPropertiesReader.getMyProperty('LOG_TRACE_CLASSES')


		if (traceClassList) {
			traceClassList = traceClassList.replaceAll("[\\s\\t]+", "") // Supprime les espaces et les tabulations
			List <String> classList = traceClassList.split(',') as List
			traceClassesExcluded = classList.findAll { it[0] == '-' }.collect { it.substring(1) }
			traceClassesAdded = classList.findAll { it[0] == '+' }.collect { it.substring(1) }
		}

		String traceFunctionList = TNRPropertiesReader.getMyProperty('LOG_TRACE_FUNCTION')

		if (traceFunctionList) {
			traceFunctionList = traceFunctionList.replaceAll("[\\s\\t]+", "") // Supprime les espaces et les tabulations
			List <String> functionList = traceFunctionList.split(',') as List
			traceFunctionsExcluded = functionList.findAll { it[0] == '-' }.collect { it.substring(1) }
			traceFunctionsAdded = functionList.findAll { it[0] == '+' }.collect { it.substring(1) }
		}


		add('','Fichier log de Katalon TNR')
		addDEBUG("Trace niveau (profondeur)    : $traceLevel")
		addDEBUG('Trace des classes exclues    : ' + traceClassesExcluded + '\tCes classes ne seront jamais tracées' )
		addDEBUG('Trace classes ajoutées       : ' + traceClassesAdded + '\tCes classes seront toujours tracées')
		addDEBUG('Trace des fonctions exclues  : ' + traceFunctionsExcluded + '\tCes fonctions ne seront jamais tracées' )
		addDEBUG('Trace des fonctions ajoutées : ' + traceFunctionsAdded + '\tCes fonctions seront toujours tracées')
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




	public static addTraceBEGIN (String myClass, String myFunction, Map paras = [:]) {
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

		traceAllowed =  (level <= traceLevel)

		if (traceClassesExcluded.contains(myClass)) {
			traceAllowed = false
		}else if (traceClassesAdded.contains(myClass)) {
			traceAllowed= true
		}
		if (traceFunctionsExcluded.contains(getClassFunction(myClass,myFunction))) {
			traceAllowed = false
		}else if (traceFunctionsAdded.contains(getClassFunction(myClass,myFunction))) {
			traceAllowed= true
		}

	}



	public static addDEBUGDETAIL (String msg) {
		addDEBUG("- $msg")
	}



	public static addINFO (String msg, boolean traceMode = false) {

		if (traceMode) {
			addTrace(msg)
		}else {
			add('',tabINFO+msg)
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



	public static List<String> getList(String nomDeLaListe) {
		return lists[nomDeLaListe]
	}


	public static writeList(String nomDeLaListe, String status ='', boolean brut = false) {

		def liste = lists.get(nomDeLaListe)
		if (liste != null) {
			liste.each { val ->
				if (brut) {
					addB(val)
				}else {
					add(status,PREDETAILTXT+ val)
				}
			}
		} else {
			addTrace("La liste '$nomDeLaListe' n'existe pas.")
		}
	}



	public static setTraceLevel(int newLevel) {
		fileDebug.append("Ancien niveau de trace  : $traceLevel\n")
		traceLevel = newLevel
		fileDebug.append("Nouveau niveau de trace : $traceLevel\n")
	}




	public static addEndLog(String msg='') {
		addTrace(msg)
		if (nbrAssert>0) {
			file.append("Nombre de tests unitaires :) : ${nbrAssert-nbrAssertKO} / $nbrAssert\n")
			file.append("Nombre de tests unitaires KO : $nbrAssertKO / $nbrAssert\n")
			fileDebug.append("Nombre de tests unitaires KO : $nbrAssertKO / $nbrAssert\n")
			writeList('assertKO','',true)
		}

		//writeList('fullStepID','',true)

		traceEnd=true
		Date stop = new Date()
		fileDebug.append("Max Level = $maxLevel\n")
		fileDebug.append('Time Duration : ' + Tools.getDuration(startLog,stop)+'\n')
		file.append('Time Duration : ' + Tools.getDuration(startLog,stop)+'\n')
		fileDebug.append('*** END ***')
		file.append('*** END ***')
	}


	public static addAssert(String myClass,String msg,def expectedResult,def actualResult){

		nbrAssert++
		try{
			assert (expectedResult==actualResult)
			add(':)',"\tTEST '$msg' --> OK : '$expectedResult'")
		}catch(AssertionError  e){
			addToList('assertKO', "$myClass : $msg")
			nbrAssertKO++
			KeywordUtil.markWarning("Test $msg --> KO")
			add('KO',"\tTEST '$msg' " )
			String err = e.getMessage().replaceAll('\n','\n'+'\t'*5+'  ')
			addB('\t'*3 +"- $err")
			addB('\t'*4 +'  |               |')
			if(actualResult!=null) {
				addB('\t'*4 +'  |               ' + actualResult.getClass())
			}else {
				addB('\t'*4 +'  |')
			}
			if(expectedResult!=null) {
				addB('\t'*4 +'  ' + expectedResult.getClass())
			}
		}
	}

	public static setTabINFO(String tab){
		tabINFO=tab
	}


}// Fin de class