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
import my.Log

@CompileStatic
class Tools {


	public static String getMobObj(String tc) {
		Log.addTraceBEGIN("Tools.getModObj($tc)")
		String ret = tc.find(/^\w+\.\w+/)
		Log.addTraceEND("Tools.getModObj()",ret)
		return ret
	}


	public static getBrowserAndVersion() {
		Log.addTraceBEGIN("Tools.getBrowserAndVersion()")
		WebDriver driver = DriverFactory.getWebDriver()
		Capabilities caps = ((SmartWaitWebDriver) driver).getCapabilities()
		String browserName = DriverFactory.getExecutedBrowser()
		String browserVersion = caps.getVersion()
		Log.addTraceEND("Tools.getBrowserAndVersion()",browserName + '/'+ browserVersion)
		return [browserName , browserVersion]
	}


	public static String getBrowserName() {
		Log.addTraceBEGIN("Tools.getBrowserName()")
		WebDriver driver = DriverFactory.getWebDriver()
		String browserName = DriverFactory.getExecutedBrowser()
		Log.addTraceEND("Tools.getBrowserName()",browserName)
		return browserName
	}


	public static String getBrowserVersion() {
		Log.addTraceBEGIN("Tools.getBrowserVersion()")
		WebDriver driver = DriverFactory.getWebDriver()
		Capabilities caps = ((SmartWaitWebDriver) driver).getCapabilities()
		String browserVersion =  caps.getVersion()
		Log.addTraceEND("Tools.getBrowserVersion()",browserVersion)
		return browserVersion
	}



	public static addInfoContext() {
		Log.addTraceBEGIN("Tools.addInfoContext()")
		Log.addINFO('INFO CONTEXTE')
		Log.addINFO("Nom de l'OS".padRight(26) + System.getProperty("os.name"))
		Log.addINFO("Version de l'OS".padRight(26) + System.getProperty("os.version"))
		Log.addINFO("Architecture de l'OS".padRight(26) + System.getProperty("os.arch"))
		Log.addINFO("Version de MAINTA".padRight(26) + my.SQL.getMaintaVersion())
		Log.addINFO("URL".padRight(26) + GlobalVariable.BASE_URL.toString())
		Log.addINFO("Base de donnée".padRight(26) + SQL.getPathDB())
		Log.addINFO("GroovySystem.version".padRight(26) + GroovySystem.version)
		Log.addINFO('')
		Log.addTraceEND("Tools.addInfoContext()")
	}


	public static String getDuration(Date start,Date stop) {
		Log.addTraceBEGIN("Tools.getDuration(${start.toString()},${stop.toString()})")
		TimeDuration timeDuration = TimeCategory.minus( stop, start )
		String SS = addZero(timeDuration.getSeconds())
		String MM = addZero(timeDuration.getMinutes())
		String HH = addZero(timeDuration.getHours() + (timeDuration.getDays()*24))
		String duration = "$HH:$MM:$SS"
		Log.addTraceEND("Tools.getDuration()",duration)
		return duration
	}


	public static String addZero(int val) {
		Log.addTraceBEGIN("Tools.addZero($val)")
		String val0 = (val>=0 && val<=9)?"0$val":"$val"
		Log.addTraceEND("Tools.addZero()",val0)
		return val0
	}



	static String cleanStr(String str) {
		Log.addTraceBEGIN("Tools.cleanStr($str)")
		String strCleaned = str.replaceAll("[^a-zA-Z0-9-_]", "")
		Log.addTraceEND("Tools.cleanStr()",strCleaned)
		return strCleaned
	}



	static createFolderIfNotExist(String dir) {
		Log.addTraceBEGIN("Tools.createFolderIfNotExist($dir)")
		File fdir = new File(dir)
		String msg
		if (!fdir.exists()) {
			fdir.mkdirs()
			msg = "Création dossier"
		}else {
			msg = "Dossier existe déjà"
		}
		Log.addTraceEND("Tools.createFolderIfNotExist()",msg)
	}

	static deleteFile(String filePath ) {
		Log.addTraceBEGIN("Tools.deleteFile($filePath)")
		def file = new File(filePath)
		String msg
		if (file.exists()) {
			if (file.delete()) {
				msg = "Le fichier a été supprimé avec succès."
			} else {
				msg = "Impossible de supprimer le fichier."
			}
		} else {
			msg = "Le fichier n'existe pas."
		}
		Log.addTraceEND("Tools.deleteFile()",msg)
	}
} // end of class
