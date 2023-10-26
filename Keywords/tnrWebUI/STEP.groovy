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


	private static final String CLASS_NAME = 'STEP'




	/*
	 * ************************** Navigate
	 */

	public static void openBrowser( String url){
		Navigate.openBrowser( url)
	}


	public static void navigateToUrl( String url,String nomUrl){
		Navigate.navigateToUrl( url, nomUrl)
	}


	public static void closeBrowser(){
		Navigate.closeBrowser()
	}


	public static void maximizeWindow(){
		Navigate.maximizeWindow()
	}

	public static void scrollToPosition( int x, int y) {
		Navigate.scrollToPosition( x, y)
	}


	public static void switchToDefaultContent() {
		Navigate.switchToDefaultContent()
	}


	public static goToURLCreate(String fct='', String attr='') {
		Navigate.goToURLCreate( fct, attr)
	}

	public static goToURLReadUpdateDelete( String idval, String fct='') {
		Navigate.goToURLReadUpdateDelete( idval, fct)
	}

	public static goToGridURL( String fct='') {
		Navigate.goToGridURL( fct)
	}




	/*
	 * ************************** Screen
	 */

	public static checkCreateScreen(String fct='', int timeout = GlobalVariable.TIMEOUT) {
		Screen.checkCreateScreen( fct, timeout)
	}

	public static checkGridScreen( String fct='', int timeout = GlobalVariable.TIMEOUT) {
		Screen.checkGridScreen( fct, timeout)
	}

	public static checkResultScreen( String val,String fct='', String name='Resultat_ID_a', int timeout = GlobalVariable.TIMEOUT) {
		Screen.checkResultScreen( val, fct, name, timeout)
	}

	public static checkReadUpdateDeleteScreen( String textToVerify, String fct='' , int timeout = GlobalVariable.TIMEOUT) {
		Screen.checkReadUpdateDeleteScreen( textToVerify, fct, timeout)
	}


	public static checkCartridge( String txt, int timeout = GlobalVariable.TIMEOUT) {
		Screen.checkCartridge( txt, timeout)
	}









	/*
	 * ************************** CheckInDB
	 */


	public static void verifyMaintaVersion( String maintaVersion) {
		CheckInDB.verifyMaintaVersion( maintaVersion)
	}

	public static void checkIDNotInBD( JDD myJDD){
		CheckInDB.checkIDNotInBD( myJDD)
	}


	public static void checkJDDWithBD( JDD myJDD,Map specificValueMap=[:],String sql =''){
		CheckInDB.checkJDDWithBD( myJDD, specificValueMap, sql)
	}




	/*
	 * ************************** Text
	 */

	public static void verifyValue( JDD myJDD, String name, String text=null, int timeout  = (int) GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Text.verifyValue( myJDD, name, text, timeout, status)
	}


	public static void setDate( JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Text.setDate( myJDD, name, val, dateFormat, timeout, status)
	}


	public static void verifyDateText( JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Text.verifyDateText( myJDD, name, val, dateFormat, timeout, status)
	}


	public static void verifyDateValue( JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Text.verifyDateValue( myJDD, name, val, dateFormat, timeout, status)
	}


	public static void verifyTimeValue( JDD myJDD, String name, def val=null, String timeFormat = 'HH:mm:ss', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Text.verifyTimeValue( myJDD, name, val, timeFormat, timeout, status)
	}


	public static void setText( JDD myJDD, String name, String text=null , int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Text.setText( myJDD, name, text, timeout, status)
	}


	public static void setEncryptedText( JDD myJDD, String name, String text=null,int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Text.setEncryptedText( myJDD, name, text, timeout, status)
	}


	public static boolean verifyText(JDD myJDD, String name, String text=null, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		return Text.verifyText( myJDD, name, text, timeout*1000, status)
	}

	/* Not used at the moment
	 public static boolean verifyTextContains(JDD myJDD, String name, String text=null, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
	 return Text.verifyTextContains( myJDD, name, text, timeout, status)
	 }
	 */








	/*
	 * ************************** Checkbox
	 */


	public static void clickCheckboxIfNeeded( JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL')  {
		Checkbox.clickCheckboxIfNeeded( myJDD, name, textTrue, timeout, status)
	}


	public static void verifyBoxCheckedOrNot( JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Checkbox.verifyBoxCheckedOrNot( myJDD, name, textTrue, timeout, status)
	}





	/*
	 * ************************** CheckboxImg
	 */

	public static void clickImgboxIfNeeded( JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL')  {
		CheckboxImg.clickImgboxIfNeeded(myJDD, name, textTrue, timeout, status)
	}

	public static void verifyImgCheckedOrNot( JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		CheckboxImg.verifyImgCheckedOrNot(myJDD, name, textTrue, timeout, status)
	}



	/*
	 * ************************** Click
	 */

	public static boolean simpleClick(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		return Click.simpleClick( myJDD, name, timeout, status)
	}

	public static boolean doubleClick( JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		return Click.doubleClick( myJDD, name, timeout, status)
	}




	/*
	 * ************************** Element
	 */


	public static boolean verifyElementPresent( JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		return Element.verifyElementPresent( myJDD, name, timeout, status)
	}

	public static boolean verifyElementVisible( JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		return Element.verifyElementVisible( myJDD, name, timeout, status)
	}

	public static boolean verifyElementNotPresent( JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		return Element.verifyElementNotPresent( myJDD, name, timeout, status)
	}


	/*
	 * ************************** SearchWithHelper
	 */


	public static void searchWithHelper( JDD myJDD, String name , String btnXpath = '' , String inputSearchName = '', int index_td=3 ){
		SearchWithHelper.searchWithHelper( myJDD, name, btnXpath, inputSearchName, index_td)
	}





	/*
	 * ************************** Alert
	 */

	public static boolean waitAndAcceptAlert( int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Alert.waitAndAcceptAlert( timeout, status)
	}


	/*
	 * ************************** Select
	 */

	public static void verifyOptionSelectedByLabel( JDD myJDD, String name, String text=null, boolean isRegex = false, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Select.verifyOptionSelectedByLabel( myJDD, name, text, isRegex, timeout, status)
	}


	public static void selectOptionByLabel( JDD myJDD, String name, String text=null, boolean isRegex = true, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Select.selectOptionByLabel( myJDD, name, text, isRegex, timeout, status)
	}


	/*
	 * ************************** Key
	 */

	public static String sendKeys( JDD myJDD, String name, String keys, String msg = '' , String status = 'FAIL') {
		Key.sendKeys( myJDD, name, keys, msg, status)
	}



	/*
	 * ************************** Radio
	 */

	static void verifyRadioChecked( JDD myJDD, String name,  int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Radio.verifyRadioChecked( myJDD, name, timeout, status)
	}

	static void setRadio( JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Radio.setRadio( myJDD, name, timeout, status)
	}


	/*
	 * ************************** Memo
	 */

	public static void setMemoText( String newText, String memoName, boolean maj, JDD myJDD,String modifierNom) {
		Memo.setMemoText( newText, memoName, maj, myJDD, modifierNom)
	}


	/*
	 * ************************** DivModal
	 */


	static boolean isDivModalOpened(String name, int timeout=(int)GlobalVariable.TIMEOUT) {
		Div.isDivModalOpened(name, timeout)
	}

	static boolean isDivModalClosed(String name, int timeout=(int)GlobalVariable.TIMEOUT) {
		Div.isDivModalClosed(name, timeout)
	}
} // end of class
