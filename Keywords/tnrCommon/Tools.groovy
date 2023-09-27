package tnrCommon

import org.openqa.selenium.Capabilities
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.SmartWaitWebDriver

import groovy.time.TimeCategory
import groovy.time.TimeDuration
import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrLog.Log
import tnrSqlManager.SQL

@CompileStatic
class Tools {

	private static final String CLASS_FOR_LOG = 'Tools'

	public static String getMobObj(String tc) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getModObj",[tc:tc])
		String ret = tc.find(/^\w+\.\w+/)
		Log.addTraceEND(CLASS_FOR_LOG,"getModObj",ret)
		return ret
	}

	public static String getFctFromModObj() {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getFctFromModObj",[:])
		String ret = TNRPropertiesReader.getMyProperty('CODESCREEN_' + Tools.getMobObj(GlobalVariable.CAS_DE_TEST_EN_COURS.toString()))
		Log.addTraceEND(CLASS_FOR_LOG,"getFctFromModObj")
		return ret
	}


	public static getBrowserAndVersion() {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getBrowserAndVersion",[:])
		WebDriver driver = DriverFactory.getWebDriver()
		Capabilities caps = ((SmartWaitWebDriver) driver).getCapabilities()
		String browserName = DriverFactory.getExecutedBrowser()
		String browserVersion = caps.getVersion()
		Log.addTraceEND(CLASS_FOR_LOG,"getBrowserAndVersion",browserName + '/'+ browserVersion)
		return [browserName , browserVersion]
	}


	public static String getBrowserName() {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getBrowserName",[:])
		WebDriver driver = DriverFactory.getWebDriver()
		String browserName = DriverFactory.getExecutedBrowser()
		Log.addTraceEND(CLASS_FOR_LOG,"getBrowserName",browserName)
		return browserName
	}


	public static String getBrowserVersion() {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getBrowserVersion",[:])
		WebDriver driver = DriverFactory.getWebDriver()
		Capabilities caps = ((SmartWaitWebDriver) driver).getCapabilities()
		String browserVersion =  caps.getVersion()
		Log.addTraceEND(CLASS_FOR_LOG,"getBrowserVersion",browserVersion)
		return browserVersion
	}



	public static addInfoContext() {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"addInfoContext",[:])
		Log.addSubTITLE('INFO CONTEXTE')
		Log.addINFO("\tNom de l'OS".padRight(26) + System.getProperty("os.name"))
		Log.addINFO("\tVersion de l'OS".padRight(26) + System.getProperty("os.version"))
		Log.addINFO("\tArchitecture de l'OS".padRight(26) + System.getProperty("os.arch"))
		Log.addINFO("\tVersion de MAINTA".padRight(26) + SQL.getMaintaVersion())
		Log.addINFO("\tURL".padRight(26) + GlobalVariable.BASE_URL.toString())
		Log.addINFO("\tBase de donnée".padRight(26) + SQL.getPathDB())
		Log.addINFO("\tGroovySystem.version".padRight(26) + GroovySystem.version)
		Log.addINFO('')
		Log.addTraceEND(CLASS_FOR_LOG,"addInfoContext")
	}


	public static String getDuration(Date start,Date stop) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getDuration",[startStr:start.toString(),stopStr:stop.toString()])
		TimeDuration timeDuration = TimeCategory.minus( stop, start )
		String SS = addZero(timeDuration.getSeconds())
		String MM = addZero(timeDuration.getMinutes())
		String HH = addZero(timeDuration.getHours() + (timeDuration.getDays()*24))
		String duration = "$HH:$MM:$SS"
		Log.addTraceEND(CLASS_FOR_LOG,"getDuration",duration)
		return duration
	}


	public static String addZero(int val, int n = 2) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"addZero",[val:val , n:n])
		String val0 = ''
		if (val>=0) {
			val0 = val.toString().padLeft(n,'0')
		}else {
			val0 = val.toString()
		}
		Log.addTraceEND(CLASS_FOR_LOG,"addZero",val0)
		return val0
	}






	static String cleanStr(String str) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"cleanStr",[str:str])
		String strCleaned = str.replaceAll("[^a-zA-Z0-9-_]", "")
		Log.addTraceEND(CLASS_FOR_LOG,"cleanStr",strCleaned)
		return strCleaned
	}



	static def displayWithQuotes(input) {
		if (input instanceof List) {
			// Gère le cas où la liste peut contenir d'autres listes, des maps ou des valeurs simples.
			return "[" + input.collect { item -> displayWithQuotes(item) }.join(', ') + "]"
		} else if (input instanceof Map) {
			def result = input.entrySet().collect { entry ->
				def key = entry.key
				def value = entry.value
				"'$key':${displayWithQuotes(value)}"
			}.join(', ')
			return "[${result}]"
		} else {
			return input == null ? 'null' : "'${input}'"
		}
	}



	static String convertFloatToHH_MM(float hoursFloat,String sep = 'h') {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"convertFloatToHH_MM",[hoursFloat:hoursFloat , sep:sep])
		int hours = hoursFloat.intValue()
		int minutes = ((hoursFloat - hours) * 60).round()
		String hhmm = hours + sep + addZero(minutes)
		Log.addTraceEND(CLASS_FOR_LOG,"convertFloatToHH_MM",hhmm)
		return hhmm
	}



} // Fin de class
