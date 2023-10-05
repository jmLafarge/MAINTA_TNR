package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import tnrLog.Log




/**
 * Gère les fenetres
 * 
 * @author JM LAFARGE
 * @version 1.0
 * 
 */
@CompileStatic
public class WUIWindow {


	private static final String CLASS_NAME = 'WUIWindow'

	private static Set<String> beforeHandles
	private static String newWindowHandle
	private static String mainWindowHandle



	/**
	 * initialise la liste des handles de fenêtre
	 */
	public static void init() {
		Log.addTraceBEGIN(CLASS_NAME, "init")
		beforeHandles = getWindowHandles()
		Log.addTraceEND(CLASS_NAME, "init")
	}


	/**
	 * Sauvegarde le handle de la fenêtre principale
	 */
	public static void storeMainWindowHandle() {
		mainWindowHandle = DriverFactory.getWebDriver().getWindowHandle()
		Log.addINFO("storeMainWindowHandle '$mainWindowHandle'")
	}


	/**
	 * Switch vers la fenêtre principale.
	 *
	 */
	public static void switchToMainWindow() {
		Log.addINFO("switchToMainWindow")
		switchToWindow(mainWindowHandle)
	}


	/**
	 * Obtient tous les handles de fenêtre actuellement ouverts.
	 *
	 * @return Set de strings représentant les handles de fenêtre.
	 */
	private static Set<String> getWindowHandles() {
		Log.addTraceBEGIN(CLASS_NAME, "getWindowHandles")
		Set<String> ret
		ret = DriverFactory.getWebDriver().getWindowHandles()
		Log.addTraceEND(CLASS_NAME, "getWindowHandles", ret)
		return ret
	}



	/**
	 * Obtient le handle de la nouvelle fenêtre ouverte.
	 *
	 * @return le handle de la nouvelle fenêtre, ou null si aucune nouvelle fenêtre n'a été trouvée.
	 */
	private static String getNewWindowHandle() {
		Log.addTraceBEGIN(CLASS_NAME, "getNewWindowHandle")
		String ret
		Set<String> afterHandles = getWindowHandles()
		afterHandles.removeAll(beforeHandles)
		if (afterHandles.size() > 0) {
			newWindowHandle = afterHandles.iterator().next()
			ret =  newWindowHandle
		}
		Log.addTraceEND(CLASS_NAME, "getNewWindowHandle", ret)
		return ret
	}



	/**
	 * Switch vers une fenêtre spécifiée par son handle.
	 *
	 * @param handle : le handle de la fenêtre vers laquelle basculer.
	 */
	private static void switchToWindow(String handle) {
		Log.addINFO("switchToWindow '$handle'")
		DriverFactory.getWebDriver().switchTo().window(handle)
	}

	/**
	 * Attend qu'une nouvelle fenêtre s'ouvre et y basculer.
	 *
	 * @param timeoutInMilliseconds : Le temps d'attente maximum en millisecondes (valeur par défaut 2 sec)
	 * @return Un booléen indiquant si l'opération a réussi ou non.
	 */
	public static boolean waitForNewWindowToOpenAndSwitch(int timeoutInMilliseconds = 2000) {
		Log.addTraceBEGIN(CLASS_NAME, "waitForNewWindowToOpenAndSwitch",[timeoutInMilliseconds:timeoutInMilliseconds])
		boolean ret = false
		int waitedTime = 0
		while (waitedTime < timeoutInMilliseconds) {
			if (getWindowHandles().size() > beforeHandles.size()) {
				switchToWindow(getNewWindowHandle())
				ret = true
				break
			}
			Thread.sleep(100)  // Pause pour 100 millisecondes
			waitedTime += 100
		}
		Log.addTraceEND(CLASS_NAME, "waitForNewWindowToOpenAndSwitch", ret)
		return ret
	}





	/**
	 * Méthode pour fermer la nouvelle fenêtre si elle est encore ouverte.
	 */
	public static void closeWindowIfOpen() {
		Log.addTraceBEGIN(CLASS_NAME, "closeWindowIfOpen")
		if (newWindowHandle && getWindowHandles().contains(newWindowHandle)) {
			Log.addINFO("closeWindow '$newWindowHandle'")
			WebUI.closeWindowIndex(newWindowHandle,FailureHandling.OPTIONAL)
			newWindowHandle = null
		}
		Log.addTraceEND(CLASS_NAME, "closeWindowIfOpen")
	}
} // end of class
