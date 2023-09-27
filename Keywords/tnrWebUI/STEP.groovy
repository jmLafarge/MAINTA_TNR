package tnrWebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrResultManager.TNRResult
import tnrSqlManager.CheckInDB





/**
 * 
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */

@CompileStatic
public class STEP {


	private static final String CLASS_FOR_LOG = 'STEP'




	/*
	 * ************************** Navigate
	 */

	public static void openBrowser(def stepID, String url){
		Navigate.openBrowser(stepID, url)
	}


	public static void navigateToUrl(def stepID, String url,String nomUrl){
		Navigate.navigateToUrl(stepID, url, nomUrl)
	}


	public static void closeBrowser(def stepID){
		Navigate.closeBrowser(stepID)
	}


	public static void maximizeWindow(def stepID){
		Navigate.maximizeWindow(stepID)
	}

	public static void scrollToPosition(def stepID, int x, int y) {
		Navigate.scrollToPosition(stepID, x, y)
	}


	public static void switchToDefaultContent(def stepID) {
		Navigate.switchToDefaultContent(stepID)
	}


	public static goToURLCreate(def stepID,String fct='', String attr='') {
		Navigate.goToURLCreate(stepID, fct, attr)
	}

	public static goToURLReadUpdateDelete(def stepID, String idval, String fct='') {
		Navigate.goToURLReadUpdateDelete(stepID, idval, fct)
	}

	public static goToGridURL(def stepID, String fct='') {
		Navigate.goToGridURL(stepID, fct)
	}




	/*
	 * ************************** Screen
	 */

	public static checkCreateScreen(def stepID,String fct='', int timeout = GlobalVariable.TIMEOUT) {
		Screen.checkCreateScreen(stepID, fct, timeout)
	}

	public static checkGridScreen(def stepID, String fct='', int timeout = GlobalVariable.TIMEOUT) {
		Screen.checkGridScreen(stepID, fct, timeout)
	}

	public static checkResultScreen(def stepID, String val,String fct='', String name='Resultat_ID_a', int timeout = GlobalVariable.TIMEOUT) {
		Screen.checkResultScreen(stepID, val, fct, name, timeout)
	}

	public static checkReadUpdateDeleteScreen(def stepID, String text, String fct='' , int timeout = GlobalVariable.TIMEOUT) {
		Screen.checkReadUpdateDeleteScreen(stepID, text, fct, timeout)
	}


	public static checkCartridge(def stepID, String txt, int timeout = GlobalVariable.TIMEOUT) {
		Screen.checkCartridge(stepID, txt, timeout)
	}









	/*
	 * ************************** CheckInDB
	 */


	public static void verifyMaintaVersion(def stepID, String maintaVersion) {
		CheckInDB.verifyMaintaVersion(stepID, maintaVersion)
	}

	public static void checkIDNotInBD(def stepID, JDD myJDD){
		CheckInDB.checkIDNotInBD(stepID, myJDD)
	}


	public static void checkJDDWithBD(def stepID, JDD myJDD,Map specificValueMap=[:],String sql =''){
		CheckInDB.checkJDDWithBD(stepID, myJDD, specificValueMap, sql)
	}




	/*
	 * ************************** Text
	 */

	public static void verifyValue(def stepID, JDD myJDD, String name, String text=null, int timeout  = (int) GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Text.verifyValue(stepID, myJDD, name, text, timeout, status)
	}


	public static void setDate(def stepID, JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Text.setDate(stepID, myJDD, name, val, dateFormat, timeout, status)
	}


	public static void verifyDateText(def stepID, JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Text.verifyDateText(stepID, myJDD, name, val, dateFormat, timeout, status)
	}


	public static void verifyDateValue(def stepID, JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Text.verifyDateValue(stepID, myJDD, name, val, dateFormat, timeout, status)
	}


	public static void verifyTimeValue(def stepID, JDD myJDD, String name, def val=null, String timeFormat = 'HH:mm:ss', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Text.verifyTimeValue(stepID, myJDD, name, val, timeFormat, timeout, status)
	}


	public static void setText(def stepID, JDD myJDD, String name, String text=null , int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Text.setText(stepID, myJDD, name, text, timeout, status)
	}


	public static void setEncryptedText(def stepID, JDD myJDD, String name, String text=null,int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Text.setEncryptedText(stepID, myJDD, name, text, timeout, status)
	}


	public static boolean verifyText(def stepID,JDD myJDD, String name, String text=null, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		return Text.verifyText(stepID, myJDD, name, text, timeout, status)
	}


	public static boolean verifyTextContains(def stepID,JDD myJDD, String name, String text=null, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		return Text.verifyTextContains(stepID, myJDD, name, text, timeout, status)
	}








	/*
	 * ************************** Checkbox
	 */


	public static void scrollAndCheckIfNeeded(def stepID, JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL')  {
		Checkbox.scrollAndCheckIfNeeded(stepID, myJDD, name, textTrue, timeout, status)
	}


	public static void verifyElementCheckedOrNot(def stepID, JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Checkbox.verifyElementCheckedOrNot(stepID, myJDD, name, textTrue, timeout, status)
	}


	public static void verifyCheckBoxImgChecked(String stepID, JDD myJDD, String name, String status = 'FAIL')  {
		Checkbox.verifyCheckBoxImgChecked(stepID, myJDD, name, status)
	}



	public static void verifyImgCheckedOrNot(String stepID, JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Checkbox.verifyImgCheckedOrNot(stepID, myJDD, name, textTrue)
	}



	public static void verifyImg(String stepID, JDD myJDD, String name, boolean cond, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Checkbox.verifyImg(stepID, myJDD, name, cond, timeout, status)
	}



	public static boolean getCheckBoxImgStatus(JDD myJDD, String name)  {
		Checkbox.getCheckBoxImgStatus(myJDD, name)
	}







	/*
	 * ************************** Click
	 */

	public static boolean simpleClick(def stepID,JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		return Click.simpleClick(stepID, myJDD, name, timeout, status)
	}

	public static boolean doubleClick(def stepID, JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		return Click.doubleClick(stepID, myJDD, name, timeout, status)
	}




	/*
	 * ************************** Element
	 */


	public static boolean verifyElementPresent(def stepID, JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		return Element.verifyElementPresent(stepID, myJDD, name, timeout, status)
	}

	public static boolean verifyElementVisible(def stepID, JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		return Element.verifyElementVisible(stepID, myJDD, name, timeout, status)
	}

	public static boolean verifyElementNotPresent(def stepID, JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		return Element.verifyElementNotPresent(stepID, myJDD, name, timeout, status)
	}


	/*
	 * ************************** SearchWithHelper
	 */


	public static void searchWithHelper(def stepID, JDD myJDD, String name , String btnXpath = '' , String inputSearchName = '', int index_td=3 ){
		SearchWithHelper.searchWithHelper(stepID, myJDD, name, btnXpath, inputSearchName, index_td)
	}





	/*
	 * ************************** Alert
	 */

	public static boolean waitAndAcceptAlert(def stepID, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Alert.waitAndAcceptAlert(stepID, timeout, status)
	}


	/*
	 * ************************** Select
	 */

	public static void verifyOptionSelectedByLabel(def stepID, JDD myJDD, String name, String text=null, boolean isRegex = false, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Select.verifyOptionSelectedByLabel(stepID, myJDD, name, text, isRegex, timeout, status)
	}


	public static void scrollAndSelectOptionByLabel(def stepID, JDD myJDD, String name, String text=null, boolean isRegex = true, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Select.scrollAndSelectOptionByLabel(stepID, myJDD, name, text, isRegex, timeout, status)
	}


	/*
	 * ************************** Key
	 */

	public static String sendKeys(def stepID, JDD myJDD, String name, String keys, String msg = '' , String status = 'FAIL') {
		Key.sendKeys(stepID, myJDD, name, keys, msg, status)
	}



	/*
	 * ************************** Radio
	 */

	static void verifyRadioChecked(def stepID, JDD myJDD, String name,  int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Radio.verifyRadioChecked(stepID, myJDD, name, timeout, status)
	}

	static void scrollAndSetRadio(def stepID, JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Radio.scrollAndSetRadio(stepID, myJDD, name, timeout, status)
	}


	/*
	 * ************************** Memo
	 */

	public static void setMemoText(def stepID, String newText, String memoName, boolean maj, JDD myJDD,String modifierNom) {
		Memo.setMemoText(stepID, newText, memoName, maj, myJDD, modifierNom)
	}


	/*
	 * ************************** DivModal
	 */


	static boolean isDivModalOpened(def stepID, int timeout=(int)GlobalVariable.TIMEOUT) {
		Div.isDivModalOpened(stepID, timeout)
	}

	static boolean isDivModalClosed(def stepID, int timeout=(int)GlobalVariable.TIMEOUT) {
		Div.isDivModalClosed(stepID, timeout)
	}
} // end of class
