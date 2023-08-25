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

	private static final String CLASS_FORLOG = 'Tools'

	public static String getMobObj(String tc) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getModObj",[tc:tc])
		String ret = tc.find(/^\w+\.\w+/)
		Log.addTraceEND(CLASS_FORLOG,"getModObj",ret)
		return ret
	}


	public static getBrowserAndVersion() {
		Log.addTraceBEGIN(CLASS_FORLOG,"getBrowserAndVersion",[:])
		WebDriver driver = DriverFactory.getWebDriver()
		Capabilities caps = ((SmartWaitWebDriver) driver).getCapabilities()
		String browserName = DriverFactory.getExecutedBrowser()
		String browserVersion = caps.getVersion()
		Log.addTraceEND(CLASS_FORLOG,"getBrowserAndVersion",browserName + '/'+ browserVersion)
		return [browserName , browserVersion]
	}


	public static String getBrowserName() {
		Log.addTraceBEGIN(CLASS_FORLOG,"getBrowserName",[:])
		WebDriver driver = DriverFactory.getWebDriver()
		String browserName = DriverFactory.getExecutedBrowser()
		Log.addTraceEND(CLASS_FORLOG,"getBrowserName",browserName)
		return browserName
	}


	public static String getBrowserVersion() {
		Log.addTraceBEGIN(CLASS_FORLOG,"getBrowserVersion",[:])
		WebDriver driver = DriverFactory.getWebDriver()
		Capabilities caps = ((SmartWaitWebDriver) driver).getCapabilities()
		String browserVersion =  caps.getVersion()
		Log.addTraceEND(CLASS_FORLOG,"getBrowserVersion",browserVersion)
		return browserVersion
	}



	public static addInfoContext() {
		Log.addTraceBEGIN(CLASS_FORLOG,"addInfoContext",[:])
		Log.addSubTITLE('INFO CONTEXTE')
		Log.addINFO("\tNom de l'OS".padRight(26) + System.getProperty("os.name"))
		Log.addINFO("\tVersion de l'OS".padRight(26) + System.getProperty("os.version"))
		Log.addINFO("\tArchitecture de l'OS".padRight(26) + System.getProperty("os.arch"))
		Log.addINFO("\tVersion de MAINTA".padRight(26) + SQL.getMaintaVersion())
		Log.addINFO("\tURL".padRight(26) + GlobalVariable.BASE_URL.toString())
		Log.addINFO("\tBase de donnée".padRight(26) + SQL.getPathDB())
		Log.addINFO("\tGroovySystem.version".padRight(26) + GroovySystem.version)
		Log.addINFO('')
		Log.addTraceEND(CLASS_FORLOG,"addInfoContext")
	}


	public static String getDuration(Date start,Date stop) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getDuration",[startStr:start.toString(),stopStr:stop.toString()])
		TimeDuration timeDuration = TimeCategory.minus( stop, start )
		String SS = addZero(timeDuration.getSeconds())
		String MM = addZero(timeDuration.getMinutes())
		String HH = addZero(timeDuration.getHours() + (timeDuration.getDays()*24))
		String duration = "$HH:$MM:$SS"
		Log.addTraceEND(CLASS_FORLOG,"getDuration",duration)
		return duration
	}


	public static String addZero(int val) {
		Log.addTraceBEGIN(CLASS_FORLOG,"addZero",[val:val])
		String val0 = (val>=0 && val<=9)?"0$val":"$val"
		Log.addTraceEND(CLASS_FORLOG,"addZero",val0)
		return val0
	}



	static String cleanStr(String str) {
		Log.addTraceBEGIN(CLASS_FORLOG,"cleanStr",[str:str])
		String strCleaned = str.replaceAll("[^a-zA-Z0-9-_]", "")
		Log.addTraceEND(CLASS_FORLOG,"cleanStr",strCleaned)
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




} // end of class
