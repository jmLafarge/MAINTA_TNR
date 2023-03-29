package my

import org.openqa.selenium.Capabilities
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.SmartWaitWebDriver

import groovy.time.TimeCategory
import groovy.time.TimeDuration
import internal.GlobalVariable
import my.Log as MYLOG


class Tools {


	public static getBrowserAndVersion() {
		WebDriver driver = DriverFactory.getWebDriver()
		Capabilities caps = ((SmartWaitWebDriver) driver).getCapabilities()
		String browserName = DriverFactory.getExecutedBrowser()
		String browserVersion = caps.getVersion()
		return [NAME:browserName , VERSION:browserVersion]
	}

	

	public static addInfoContext() {
		MYLOG.addINFO('INFO CONTEXTE')
		MYLOG.addINFO("Nom de l'OS".padRight(26) + System.getProperty("os.name"))
		MYLOG.addINFO("Version de l'OS".padRight(26) + System.getProperty("os.version"))
		MYLOG.addINFO("Architecture de l'OS".padRight(26) + System.getProperty("os.arch"))
		MYLOG.addINFO("Version de MAINTA".padRight(26) + my.SQL.getMaintaVersion())
		MYLOG.addINFO("Base de donnée".padRight(26) + GlobalVariable.BDD_URL)
		MYLOG.addINFO('')
	}


	public static String getDuration(Date start,Date stop) {
		
		TimeDuration timeDuration = TimeCategory.minus( stop, start )

		String SS = this.addZero(timeDuration.getSeconds())
		String MM = this.addZero(timeDuration.getMinutes())
		String HH = this.addZero(timeDuration.getHours() + (timeDuration.getDays()*24))

		return "$HH:$MM:$SS"
	}


	public static String addZero(int val) {

		return (val>=0 && val<=9)?"0$val":"$val"
	}

	/**
	 *
	 * @param str : a text
	 *
	 * @return cleaned text, without special character
	 */
	static String cleanStr(String str) {

		return str.replaceAll("[^a-zA-Z0-9-_]", "")
	}


	static parseMap(mymap) {
		for (entry in mymap) {
			if (entry.value instanceof Map) {
				// Submap encountered
				print("$entry.key:[")
				parseMap(entry.value)
				print("]\n")

			} else {
				print("\n\t$entry.key:$entry.value")
			}
			if (entry.after != null) {
				print(", ")
			}
		}
		println ''
	}


	static createDir(String dir) {
		File fdir = new File(dir)
		if (!fdir.exists()) {
			fdir.mkdirs()
			MYLOG.addDEBUG("createDir() : Création dossier $dir")
		}else {
			MYLOG.addDEBUG("createDir() : Dossier $dir existe déjà")
		}
	}
} // end of class
