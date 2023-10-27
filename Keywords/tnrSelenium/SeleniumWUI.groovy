package tnrSelenium

import java.util.concurrent.TimeUnit

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory

import tnrLog.Log




public class SeleniumWUI {

	
	private static final String CLASS_NAME = 'SeleniumWUI'

	private static WebDriver myWebDriver


	public static void setMyWebDriver() {
		Log.addTrace("${CLASS_NAME}.setMyWebDriver")
		myWebDriver = DriverFactory.getWebDriver()
	}

	public static WebDriver getMyWebDriver() {
		Log.addTrace("${CLASS_NAME}.getMyWebDriver")
		return myWebDriver
	}


	// Fonction pour convertir un TestObject de Katalon en un objet By de Selenium
	static By convertTestObjectToBy(TestObject testObject) {
		String locator = testObject.getSelectorCollection().get(SelectorMethod.XPATH)
		Log.addTrace("convertTestObjectToBy locator='$locator'")
		if (locator != null) {
			return By.xpath(locator)
		}
		return null
	}
	
	

	static String placeElementInView(By locator, int timeoutInSeconds) {
		Log.addTraceBEGIN(CLASS_NAME, "placeElementInView", [ locator:locator , timeoutInSeconds: timeoutInSeconds])
		WebDriverWait wait = new WebDriverWait(myWebDriver, timeoutInSeconds)
		wait.pollingEvery(200, TimeUnit.MILLISECONDS)
		String errMsg =''
		boolean ret=false
		try {
			// Attendre que l'élément soit visible
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
			Log.addTrace("")
			// Ensuite, vérifie si l'élément est dans le viewport
			if (isElementInView(locator)) {
				ret= true
			}else {
				// Fait défiler l'élément dans le viewport et vérifie à nouveau
				((JavascriptExecutor) myWebDriver).executeScript("arguments[0].scrollIntoView(true);", element)
				wait.until({ isElementInView(locator) } as ExpectedCondition)
				ret= true
			}
		} catch (Exception e) {
			errMsg = ''
		}
		Log.addTraceEND(CLASS_NAME, "placeElementInView", ret)
		return ret
	}



	static boolean isElementInView(By locator) {
		Log.addTraceBEGIN(CLASS_NAME, "isElementInView", [ locator:locator])
		WebElement element = myWebDriver.findElement(locator)
		boolean inView = (Boolean) ((JavascriptExecutor) myWebDriver).executeScript("""
            var elem = arguments[0],
                rect = elem.getBoundingClientRect();
            return (
                rect.top >= 0 &&
                rect.left >= 0 &&
                rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
                rect.right <= (window.innerWidth || document.documentElement.clientWidth)
            );
        """, element)
		Log.addTraceEND(CLASS_NAME, "isElementInView", inView)
		return inView
	}
}