package my

import java.text.SimpleDateFormat

import org.openqa.selenium.Capabilities
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.SmartWaitWebDriver

import groovy.time.TimeCategory
import groovy.time.TimeDuration
import groovy.transform.CompileStatic
import internal.GlobalVariable


@CompileStatic
class Tools {


	public static String getMobObj(String tc) {

		return tc.find(/^\w+\.\w+/)
	}


	public static getBrowserAndVersion() {
		WebDriver driver = DriverFactory.getWebDriver()
		Capabilities caps = ((SmartWaitWebDriver) driver).getCapabilities()
		String browserName = DriverFactory.getExecutedBrowser()
		String browserVersion = caps.getVersion()
		return [browserName , browserVersion]
	}


	public static String getBrowserName() {
		WebDriver driver = DriverFactory.getWebDriver()
		return DriverFactory.getExecutedBrowser()
	}


	public static String getBrowserVersion() {
		WebDriver driver = DriverFactory.getWebDriver()
		Capabilities caps = ((SmartWaitWebDriver) driver).getCapabilities()
		return caps.getVersion()
	}



	public static addInfoContext() {
		Log.addINFO('INFO CONTEXTE')
		Log.addINFO("Nom de l'OS".padRight(26) + System.getProperty("os.name"))
		Log.addINFO("Version de l'OS".padRight(26) + System.getProperty("os.version"))
		Log.addINFO("Architecture de l'OS".padRight(26) + System.getProperty("os.arch"))
		Log.addINFO("Version de MAINTA".padRight(26) + my.SQL.getMaintaVersion())
		Log.addINFO("Base de donnée".padRight(26) + GlobalVariable.BDD_URL)
		Log.addINFO("GroovySystem.version".padRight(26) + GroovySystem.version)
		Log.addINFO('')
	}


	public static String getDuration(Date start,Date stop) {

		TimeDuration timeDuration = TimeCategory.minus( stop, start )

		String SS = addZero(timeDuration.getSeconds())
		String MM = addZero(timeDuration.getMinutes())
		String HH = addZero(timeDuration.getHours() + (timeDuration.getDays()*24))

		return "$HH:$MM:$SS"
	}


	public static String addZero(int val) {

		return (val>=0 && val<=9)?"0$val":"$val"
	}

/*
	public static int getDurationFromNow(String dateBDD) {

		def dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

		def date = dateFormat.parse(dateBDD)

		TimeDuration timeDuration = TimeCategory.minus( new Date(), date )

		return timeDuration.toMilliseconds()
	}
*/



	/**
	 *
	 * @param str : a text
	 *
	 * @return cleaned text, without special character
	 */
	static String cleanStr(String str) {

		return str.replaceAll("[^a-zA-Z0-9-_]", "")
	}


	static parseMap(Map mymap) {
		for (entry in mymap) {
			if (entry.value instanceof Map) {
				// Submap encountered
				print("$entry.key:[")
				parseMap((Map)entry.value)
				print("]\n")

			} else {
				print("\n\t$entry.key:$entry.value")
			}
		}
		println ''
	}


	static createFolderIfNotExist(String dir) {
		File fdir = new File(dir)
		if (!fdir.exists()) {
			fdir.mkdirs()
			Log.addDEBUG("createFolderIfNotExist() : Création dossier $dir")
		}else {
			Log.addDEBUG("createFolderIfNotExist() : Dossier $dir existe déjà")
		}
	}
} // end of class
