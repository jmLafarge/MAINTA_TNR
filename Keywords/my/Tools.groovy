package my

import org.openqa.selenium.Capabilities
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.SmartWaitWebDriver

import groovy.time.TimeCategory
import groovy.time.TimeDuration

class Tools {


	static getBrowserAndVersion() {
		WebDriver driver = DriverFactory.getWebDriver()
		Capabilities caps = ((SmartWaitWebDriver) driver).getCapabilities()
		String browserName = caps.getBrowserName().capitalize()
		String browserVersion = caps.getVersion()
		return [NAME:browserName , VERSION:browserVersion]
	}




	static public String getDuration(Date start,stop) {

		TimeDuration timeDuration = TimeCategory.minus( stop, start )

		String SS = this.addZero(timeDuration.getSeconds())
		String MM = this.addZero(timeDuration.getMinutes())
		String HH = this.addZero(timeDuration.getHours() + (timeDuration.getDays()*24))

		return "$HH:$MM:$SS"
	}


	static public String addZero(int val) {

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
} // end of class
