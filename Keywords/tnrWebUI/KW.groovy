package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnrResultManager.TNRResult

/**
 * Personalise les actions WebUI
 * 
 * @author JM LAFARGE
 * @version 1.0
 *
 */

@CompileStatic
class KW {


	private static final String CLASS_FOR_LOG = 'KW'








	
	
	
	
	
	static boolean isElementPresent(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "isElementPresent", [myJDD: myJDD.toString(), name: name , timeout:timeout])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			ret = isElementPresentByObj(tObj,timeout)
		}else {
			TNRResult.addSTEPERROR('-1',"Vérifier si l'élément '$name' est présent impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "isElementPresent",ret)
		return ret
	}


	static boolean isElementPresentByObj(TestObject tObj, int timeout = (int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "isElementPresent", [tObj: tObj , timeout:timeout])
		boolean ret = false
		if (WebUI.verifyElementPresent(tObj, timeout, FailureHandling.OPTIONAL)) {
			Log.addINFO("L'élément '${tObj.getObjectId()}' est présent")
			ret = true
		}else {
			Log.addINFO("L'élément '${tObj.getObjectId()}' n'est pas présent")
		}
		Log.addTraceEND(CLASS_FOR_LOG, "isElementPresent",ret)
		return ret
	}


	
	
	
	
	static String getText(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT )  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getText", [myJDD: myJDD.toString(), name: name , timeout:timeout ])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		String gText = ''
		if (!msgTO) {
			if (isElementPresentByObj(tObj,timeout)) {
				gText = getTextByObj(tObj)
			}
		}else {
			TNRResult.addSTEPERROR('-1', "Lecture du texte sur '${tObj.getObjectId()}' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getText",gText)
		return gText
	}
	

	static String getTextByObj(TestObject tObj )  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getTextByObj", [tObj:tObj ])
		String gText = ''
		try {
			gText = WebUI.getText(tObj,FailureHandling.STOP_ON_FAILURE)
			//Log.addINFO("Lecture du texte sur '${tObj.getObjectId()}' : '$gText'")
		} catch (Exception ex) {
			TNRResult.addSTEPERROR('-1',"Lecture du texte sur '${tObj.getObjectId()}' KO")
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getTextByObj",gText)
		return gText
	}

	
	
	
	
	static boolean isElementVisible(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "isElementVisible", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				Log.addINFO("L'élément '${tObj.getObjectId()}' est visible ")
				ret = true
			}else {
				Log.addINFO("L'élément '${tObj.getObjectId()}' n'est pas visible ")
				Log.addDETAIL(errMsg)
			}
		}else {
			Log.addINFO("Vérifier que l'élément '$name' soit visible impossible")
			Log.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "isElementVisible",ret)
		return ret
	}



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*

	


	
	



	static boolean doubleClick(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "doubleClick", [myJDD: myJDD.toString(), name: name, timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				try {
					WebUI.doubleClick(tObj, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS("Double click sur '${tObj.getObjectId()}'")
					ret= true
				} catch (Exception ex) {
					TNRResult.addSTEP("Double click sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP("Double clic sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Double click sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "doubleClick",ret)
		return ret
	}






	static boolean waitForAlert(int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "waitForAlert", [ timeout:timeout , status:status])
		boolean ret = false
		try {
			WebUI.waitForAlert(timeout, FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS("Attendre la fenetre de confirmation")
			ret = true
		} catch (Exception ex) {
			TNRResult.addSTEP("Attendre la fenetre de confirmation",status)
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "waitForAlert",ret)
		return ret
	}




	static boolean acceptAlert(String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "acceptAlert", [ status:status])
		boolean ret = false
		try {
			WebUI.acceptAlert(FailureHandling.STOP_ON_FAILURE)
			TNRResult.addSTEPPASS("Accepter la demande de confirmation")
			ret = true
		} catch (Exception ex) {
			TNRResult.addSTEP("Accepter la demande de confirmation", status)
			TNRResult.addDETAIL(ex.getMessage())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "acceptAlert",ret)
		return ret
	}


	static boolean waitAndAcceptAlert(int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "waitAndAcceptAlert", [ status:status])
		boolean ret = false
		if (waitForAlert(timeout, status)) {
			ret = acceptAlert(status)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "waitAndAcceptAlert",ret)
		return ret
	}







	static String sendKeys(JDD myJDD, String name, String keys, String msg = '' , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "sendKeys", [myJDD: myJDD.toString(), name: name , keys:keys, msg:msg , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		String ret = null
		if (!msgTO) {
			try {
				WebUI.sendKeys(tObj, keys, FailureHandling.STOP_ON_FAILURE)
				if (msg) {
					TNRResult.addSTEPPASS(msg)
				}else {
					Log.addTrace("Envoie touche(s) clavier '$keys' sur '${tObj.getObjectId()}'")
				}
			} catch (Exception ex) {
				if (msg) {
					TNRResult.addSTEP(msg,status)
					TNRResult.addDETAIL(ex.getMessage())
				}
				ret = ex.getMessage()
			}
		}else {
			TNRResult.addSTEPERROR("Envoie touche(s) clavier '$keys' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "sendKeys",ret)
		return ret
	}







	static boolean verifyElementNotPresent(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyElementNotPresent", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			try {
				WebUI.verifyElementNotPresent(tObj, timeout, FailureHandling.STOP_ON_FAILURE)
				TNRResult.addSTEPPASS("Vérifier que '${tObj.getObjectId()}' ne soit plus présent")
				ret = true
			} catch (Exception ex) {
				TNRResult.addSTEP("Vérifier que '${tObj.getObjectId()}' ne soit plus présent", status)
				TNRResult.addDETAIL(ex.getMessage())
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que '$name' ne soit plus présent impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyElementNotPresent",ret)
		return ret
	}

	
	
	
	
	
	

















	static void verifyValue(JDD myJDD, String name, String text=null, int timeout  = (int) GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyValue", [myJDD: myJDD.toString(), name: name , text:text , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			if (JDDKW.isNU(text)) {
				TNRResult.addSTEP("Pas de vérification pour $name, valeur du JDD = NU")
			}else {
				String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
				if (!errMsg) {
					String val = WebUI.getAttribute(tObj, 'value')
					Log.addTrace("value=$val")
					if (val==null) {
						TNRResult.addSTEPERROR("Vérifier que la valeur de '" + name + "', soit '$text'")
						TNRResult.addDETAIL("L'attribut 'value' n'existe pas !")
					}else if (val==text) {
						TNRResult.addSTEPPASS("Vérifier que la valeur de '" + name + "', soit '$text'")
					}else if (text==tnrJDDManager.JDDKW.getKW_NULL() && val=='') {
						TNRResult.addSTEPPASS("Vérifier que la valeur de '" + name + "', soit Null ou Vide")
					}else {
						TNRResult.addSTEP("Vérifier que la valeur de '" + name + "', soit '$text'",status)
						TNRResult.addDETAIL("La valeur du champ est '" + WebUI.getAttribute(tObj, 'value') + "' !")
					}

				}else {
					TNRResult.addSTEP("Vérifier que la valeur de '" + name + "', soit '$text'",status)
					TNRResult.addDETAIL(errMsg)
				}
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que la valeur de '$name' = '$text'  impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyValue")
	}



	static void verifyRadioChecked(JDD myJDD, String name,  int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyRadioChecked", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (JDDKW.isNU(myJDD.getStrData(name))) {
				TNRResult.addSTEP("Pas de vérification pour $name, valeur du JDD = NU")
			}else {
				String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
				if (!errMsg) {
					String val = WebUI.getAttribute(tObj, 'checked')
					Log.addTrace("value=$val")
					if (val==null) {
						TNRResult.addSTEP("Vérifier que le bouton radio '$name', soit sélectionné",status)
						TNRResult.addDETAIL("L'attribut 'checked' n'existe pas !")
					}else if (val=='true') {
						TNRResult.addSTEPPASS("Vérifier que le bouton radio '$name', soit sélectionné")
					}else {
						TNRResult.addSTEP("Vérifier que le bouton radio '$name', soit sélectionné",status)
						TNRResult.addDETAIL("La valeur de l'attribut checked est '$val' !")
					}
				}else {
					TNRResult.addSTEP("Vérifier que le bouton radio '$name', soit sélectionné",status)
					TNRResult.addDETAIL(errMsg)
				}
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier que le bouton radio '$name', soit sélectionné impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyRadioChecked")
	}




	private static void verifyOptionSelectedByLabel(JDD myJDD, String name, String text=null, boolean isRegex = false, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyOptionSelectedByLabel", [myJDD: myJDD.toString(), name: name , text:text , isRegex:isRegex , timeout:timeout , status:status])
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				String paraIV = myJDD.myJDDParam.getINTERNALVALUEFor(name)
				if (paraIV) {
					String valIV = myJDD.myJDDIV.getValueOf(paraIV, text)
					if (valIV) {
						try {
							WebUI.verifyOptionSelectedByLabel(tObj, valIV, isRegex,timeout, FailureHandling.STOP_ON_FAILURE)
							TNRResult.addSTEPPASS("Vérifier que l'option '$valIV'($text) de '${tObj.getObjectId()}' soit sélectionnée")
						} catch (Exception ex) {
							TNRResult.addSTEP("Vérifier que l'option '$valIV'($text) de '${tObj.getObjectId()}' soit sélectionnée",status)
							TNRResult.addDETAIL(ex.getMessage())
						}
					}else {
						TNRResult.addSTEP("Vérifier que l'option '?'($text) de '${tObj.getObjectId()}' soit sélectionnée",status)
						TNRResult.addDETAIL("Pas de valeur trouvée pour l'INTERNALVALUE")
					}
				}else {
					try {
						WebUI.verifyOptionSelectedByLabel(tObj, text, isRegex,timeout, FailureHandling.STOP_ON_FAILURE)
						TNRResult.addSTEPPASS("Vérifier que l'option '$text' de '${tObj.getObjectId()}' soit sélectionnée")
					} catch (Exception ex) {
						TNRResult.addSTEP("Vérifier que l'option '$text' de '${tObj.getObjectId()}' soit sélectionnée",status)
						TNRResult.addDETAIL(ex.getMessage())
					}
				}
			}else {
				TNRResult.addSTEP("Vérifier l'option '$text' de '$name'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Vérifier l'option '$text' de '$name'")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyOptionSelectedByLabel")
	}




























	static void scrollAndSelectOptionByLabel(JDD myJDD, String name, String text=null, boolean isRegex = true, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				String paraIV = myJDD.myJDDParam.getINTERNALVALUEFor(name)
				if (paraIV) {
					String valIV = myJDD.myJDDIV.getValueOf(paraIV, text)
					if (valIV) {
						try {
							WebUI.selectOptionByLabel(tObj, valIV, isRegex,FailureHandling.STOP_ON_FAILURE)
							TNRResult.addSTEPPASS("Scroll et select option '$valIV'($text) sur '${tObj.getObjectId()}'")
						} catch (Exception ex) {
							TNRResult.addSTEP("Scroll et select option '$valIV'($text) sur '${tObj.getObjectId()}'",status)
							TNRResult.addDETAIL(ex.getMessage())
						}
					}else {
						TNRResult.addSTEP("Scroll et select option '?'($text) sur '${tObj.getObjectId()}'",status)
						TNRResult.addDETAIL("Pas de valeur trouvée pour l'INTERNALVALUE")
					}
				}else {
					try {
						WebUI.selectOptionByLabel(tObj, text, isRegex,FailureHandling.STOP_ON_FAILURE)
						TNRResult.addSTEPPASS("Scroll et select option '$text' sur '${tObj.getObjectId()}'")
					} catch (Exception ex) {
						TNRResult.addSTEP("Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
						TNRResult.addDETAIL(ex.getMessage())
					}
				}
			}else {
				TNRResult.addSTEP("Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Scroll et select option '$text' sur '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}





	// pas utilisé, on utilise scrollAndSelectOptionByLabel
	private static void scrollAndSelectOptionByValue(JDD myJDD, String name, String text=null, boolean isRegex = true, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				
				try {
					WebUI.selectOptionByValue(tObj, text, isRegex,FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS("Scroll et select option '$text' sur '${tObj.getObjectId()}'")
				} catch (Exception ex) {
					TNRResult.addSTEP("Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP("Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Scroll et select option '$text' sur '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}





	static void scrollAndSetRadio(JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "scrollAndSetRadio", [myJDD: myJDD.toString(), name: name , timeout:timeout , status:status])
		//Création du TO label en tenant compte de la valeur --> voir resolveXpath
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		String nameWithoutLbl = name.substring(3)
		if (!msgTO) {
			String errMsg = KWElement.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				try {
					WebUI.click(tObj, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS("Clic sur bouton radio '$nameWithoutLbl' (" + myJDD.getStrData(nameWithoutLbl) + ')')
				} catch (Exception ex) {
					TNRResult.addSTEP("Clic sur bouton radio '$nameWithoutLbl' (" + myJDD.getStrData(nameWithoutLbl) + ')',status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP("Clic sur bouton radio '$nameWithoutLbl' (" + myJDD.getStrData(nameWithoutLbl) + ')',status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR("Clic sur '$nameWithoutLbl' imposible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "scrollAndSetRadio")
	}




	static void setDate(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setDate", [myJDD: myJDD.toString(), name: name , val:val , dateFormat:dateFormat , timeout:timeout , status:status])
		if (val==null) val = myJDD.getData(name)
		if ( val instanceof Date) {
			setText(myJDD, name, val.format(dateFormat), timeout,status)
		}else {
			TNRResult.addSTEPERROR("Saisie du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "setDate")
	}





	static void verifyDateText(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTrace("verifyDateText : name='$name' val='${val.toString()} dateFormat='dateFormat' status='$status'")

		if (val==null) val = myJDD.getData(name)

		if (val == '$VIDE') {
			verifyText(myJDD, name, '', timeout, status)
		}else if ( val instanceof Date) {
			Log.addTrace('val.format(dateFormat) :' +val.format(dateFormat))
			verifyText(myJDD, name, val.format(dateFormat), timeout,status)
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
	}




	static void verifyDateValue(JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyDateValue", [myJDD: myJDD.toString(), name: name , val:val , dateFormat:dateFormat , timeout:timeout , status:status])

		if (val==null) val = myJDD.getData(name)
		if (val == '$VIDE') {
			verifyValue(myJDD, name, '', timeout,status)
		}else if ( val instanceof Date) {
			verifyValue(myJDD, name, val.format(dateFormat),timeout, status)
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyDateValue")
	}




	static void verifyTimeValue(JDD myJDD, String name, def val=null, String timeFormat = 'HH:mm:ss', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyTimeValue", [myJDD: myJDD.toString(), name: name , val:val , timeFormat:timeFormat , timeout:timeout , status:status])

		if (val==null) val = myJDD.getData(name)
		if (val == '$VIDE') {
			verifyValue(myJDD, name, '', timeout,status)
		}else if ( val instanceof Date) {
			verifyValue(myJDD, name, val.format(timeFormat), timeout,status)
		}else {
			TNRResult.addSTEPERROR("Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyTimeValue")
	}









	public static String deleteText(TestObject tObj) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "deleteText", [tObj: tObj.getObjectId()])
		String msg =null
		try {
			WebUI.setText(tObj, '', FailureHandling.STOP_ON_FAILURE)
			Log.addTrace("Effacement du texte sur l'object '${tObj.getObjectId()}'")
		} catch (Exception ex) {
			msg = "Effacement du texte : " + ex.getMessage()
		}
		Log.addTraceEND(CLASS_FOR_LOG, "deleteText",msg)
		return msg
	}



*/




} // Fin de class