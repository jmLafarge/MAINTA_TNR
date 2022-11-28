package my

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

public class extend_KW {

	// Personaliser et de regrouper certaines actions WebUI

	/**
	 *
	 * @param
	 * @return
	 */
	@Keyword
	def scrollAndClick(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {

		WebUI.scrollToElement(tObj, timeOut)
		//WebUI.waitForElementVisible(tObj, timeOut)
		WebUI.waitForElementClickable(tObj, timeOut)
		WebUI.click(tObj)
		WebUI.delay(1)

	} // end of def

	/**
	 *
	 * @param
	 * @return
	 */
	@Keyword
	def scrollAndDoubleClick(TestObject tObj, int timeOut = GlobalVariable.TIMEOUT) {

		WebUI.scrollToElement(tObj, timeOut)
		WebUI.waitForElementVisible(tObj, timeOut)
		WebUI.doubleClick(tObj)
		WebUI.delay(1)

	} // end of def


	/**
	 *
	 * @param
	 * @return
	 */
	@Keyword
	def scrollAndSetText(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT) {

		WebUI.scrollToElement(tObj, timeOut)
		WebUI.waitForElementVisible(tObj, timeOut)
		WebUI.setText(tObj, text)

	} // end of def


	/**
	 * Parce que verifyElementText n'a pas de TIMEOUT
	 * @param
	 * @return
	 */
	@Keyword
	def scrollWaitAndVerifyText(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT) {

		WebUI.scrollToElement(tObj, timeOut)
		WebUI.waitForElementVisible(tObj, timeOut)
		WebUI.verifyElementText(tObj, text)

	} // end of def


	/**
	 * Parce que verifyElementText n'a pas de TIMEOUT
	 * @param
	 * @return
	 */
	@Keyword
	def waitAndVerifyText(TestObject tObj, String text, int timeOut = GlobalVariable.TIMEOUT )  {

		WebUI.waitForElementVisible(tObj, timeOut)
		WebUI.verifyElementText(tObj, text)

	} // end of def
} // end of class