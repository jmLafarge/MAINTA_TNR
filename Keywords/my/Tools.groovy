package my

import java.util.regex.Pattern

import org.openqa.selenium.Capabilities
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.SmartWaitWebDriver

import groovy.io.FileType
import groovy.time.TimeCategory
import groovy.time.TimeDuration
import groovy.transform.CompileStatic
import internal.GlobalVariable

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
		Log.addINFO("\tVersion de MAINTA".padRight(26) + my.SQL.getMaintaVersion())
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



	static createFolderIfNotExist(String dir) {
		Log.addTraceBEGIN(CLASS_FORLOG,"createFolderIfNotExist",[dir:dir])
		File fdir = new File(dir)
		if (!fdir.exists()) {
			fdir.mkdirs()
			Log.addTrace("Création dossier")
		}else {
			Log.addTrace("Dossier existe déjà")
		}
		Log.addTraceEND(CLASS_FORLOG,"createFolderIfNotExist")
	}

	static deleteFilesFromFolder(String filePath ) {
		Log.addTraceBEGIN(CLASS_FORLOG,"deleteFilesFromFolder",[filePath:filePath])
		def file = new File(filePath)
		if (file.exists()) {
			if (file.delete()) {
				Log.addTrace("Le fichier a été supprimé avec succès.")
			} else {
				Log.addTrace("Impossible de supprimer le fichier.")
			}
		} else {
			Log.addTrace( "Le fichier n'existe pas.")
		}
		Log.addTraceEND(CLASS_FORLOG,"deleteFilesFromFolder")
	}


	/**
	 * Supprime les fichiers correspondant à une expression régulière dans un dossier spécifié.
	 *
	 * @param fileRegex  L'expression régulière pour filtrer les noms de fichiers à supprimer.
	 *                  Exemples d'expressions régulières :
	 *                  - Supprimer tous les fichiers qui commencent par "backup_", suivis de 8 chiffres, et se terminent par ".bak" :
	 *                    "backup_\\d{8}\\.bak"
	 *                  - Supprimer tous les fichiers qui commencent par "image" ou "photo", suivis de n'importe quel caractère, et se terminent par ".jpg" ou ".png" :
	 *                    "(image|photo).*\\.(jpg|png)"
	 *                  - Supprimer tous les fichiers dont le nom est exactement "logfile.log" (correspondance exacte) :
	 *                    "logfile\\.log"
	 *                  - Supprimer tous les fichiers qui contiennent "-old" ou "-backup" dans leur nom :
	 *                    ".*(-old|-backup).*"
	 *                  - Supprimer tous les fichiers dont le nom commence par une lettre majuscule, suivie de lettres minuscules et de chiffres, et se termine par ".txt" :
	 *                    "[A-Z][a-z0-9]*\\.txt"
	 *
	 * @param folderPath Le chemin du dossier où supprimer les fichiers.
	 * 
	 * @return Un message de statut indiquant le résultat de l'opération de suppression.
	 */
	static def deleteFilesFromFolder(String fileRegex , String folderPath) {
		Log.addTraceBEGIN(CLASS_FORLOG,"deleteFilesFromFolder",[fileRegex:fileRegex,folderPath:folderPath])
		def folder = new File(folderPath)
		//def regexPattern = Pattern.compile(fileRegex)
		if (folder.exists() && folder.isDirectory()) {
			folder.eachFileMatch (FileType.FILES,~/${fileRegex}/) { File file ->
					if (file.delete()) {
						Log.addTrace("Fichier '${file.name}' supprimé")
					}else {
						Log.addTrace("Impossible de supprimer le fichier '${file.name}'")
					}
			}
		} else {
			Log.addTrace("Le dossier spécifié n'existe pas ou n'est pas un dossier valide.")
		}
		Log.addTraceEND(CLASS_FORLOG,"deleteFilesFromFolder")
	}
	
	
	static List <String> getFilesFromFolder( String fileRegex , String folderPath) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getFilesFromFolder",[fileRegex:fileRegex,folderPath:folderPath])
		List <String> ret = []
		def folder = new File(folderPath)
		if (folder.exists() && folder.isDirectory()) {
			folder.eachFileMatch (FileType.FILES,~/${fileRegex}/) { File file ->
				ret.add(file.name)
				Log.addTrace("Ajout du fichier '${file.name}'")
			}
		} else {
			Log.addTrace("Le dossier spécifié n'existe pas ou n'est pas un dossier valide.")
		}
		Log.addTraceEND(CLASS_FORLOG,"getFilesFromFolder",ret)
		return ret
	}
	
	
	
} // end of class
