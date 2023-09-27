package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.JDD
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnrResultManager.TNRResult

/**
 * Gere
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */

@CompileStatic
public class Text {

	private static final String CLASS_FOR_LOG = 'Text'


	private static final String CLASS_CODE = 'TXT'


	static void verifyValue(def stepID, JDD myJDD, String name, String text=null, int timeout  = (int) GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyValue", [stepID:stepID , myJDD: myJDD, name: name , text:text , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '01',stepID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			if (JDDKW.isNU(text)) {
				TNRResult.addSTEP(strStepID, "Pas de vérification pour $name, valeur du JDD = NU")
			}else {
				String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
				if (!errMsg) {
					String val = WebUI.getAttribute(tObj, 'value')
					Log.addTrace("value=$val")
					if (val==null) {
						TNRResult.addSTEPERROR(strStepID, "Vérifier que la valeur de '" + name + "', soit '$text'")
						TNRResult.addDETAIL("L'attribut 'value' n'existe pas !")
					}else if (val==text) {
						TNRResult.addSTEPPASS(strStepID, "Vérifier que la valeur de '" + name + "', soit '$text'")
					}else if (text==tnrJDDManager.JDDKW.getKW_NULL() && val=='') {
						TNRResult.addSTEPPASS(strStepID, "Vérifier que la valeur de '" + name + "', soit Null ou Vide")
					}else {
						TNRResult.addSTEP(strStepID, "Vérifier que la valeur de '" + name + "', soit '$text'",status)
						TNRResult.addDETAIL("La valeur du champ est '" + WebUI.getAttribute(tObj, 'value') + "' !")
					}
				}else {
					TNRResult.addSTEP(strStepID, "Vérifier que la valeur de '" + name + "', soit '$text'",status)
					TNRResult.addDETAIL(errMsg)
				}
			}
		}else {
			TNRResult.addSTEPERROR(strStepID, "Vérifier que la valeur de '$name' = '$text'  impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyValue")
	}






	static void setDate(def stepID, JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setDate", [stepID:stepID , myJDD: myJDD, name: name , val:val , dateFormat:dateFormat , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '02',stepID)
		val = val ?: myJDD.getData(name)
		if (JDDKW.isNULL(val) || JDDKW.isNU(val)) {
			TNRResult.addSTEPINFO(strStepID, "Pas de saisie de texte sur '$name', valeur du JDD = $val")
		}else if ( val instanceof Date) {
			setText(strStepID, myJDD, name, val.format(dateFormat), timeout,status)
		}else {
			TNRResult.addSTEPERROR(strStepID, "Saisie du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}

		Log.addTraceEND(CLASS_FOR_LOG, "setDate")
	}





	static void verifyDateText(def stepID, JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Log.addTrace("verifyDateText : name='$name' val='${val.toString()} dateFormat='dateFormat' status='$status'")
		String strStepID = StepID.getStrStepID(CLASS_CODE, '03',stepID)
		if (val==null) val = myJDD.getData(name)

		if (val == '$VIDE') {
			verifyText(strStepID, myJDD, name, '', timeout, status)
		}else if ( val instanceof Date) {
			Log.addTrace('val.format(dateFormat) :' +val.format(dateFormat))
			verifyText(strStepID, myJDD, name, val.format(dateFormat), timeout,status)
		}else {
			TNRResult.addSTEPERROR(strStepID, "Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
	}




	static void verifyDateValue(def stepID, JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyDateValue", [stepID:stepID , myJDD: myJDD, name: name , val:val , dateFormat:dateFormat , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '04',stepID)
		if (val==null) val = myJDD.getData(name)
		if (val == '$VIDE') {
			verifyValue(strStepID, myJDD, name, '', timeout,status)
		}else if ( val instanceof Date) {
			verifyValue(strStepID, myJDD, name, val.format(dateFormat),timeout, status)
		}else {
			TNRResult.addSTEPERROR(strStepID, "Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyDateValue")
	}




	static void verifyTimeValue(def stepID, JDD myJDD, String name, def val=null, String timeFormat = 'HH:mm:ss', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyTimeValue", [stepID:stepID , myJDD: myJDD, name: name , val:val , timeFormat:timeFormat , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '05',stepID)
		if (val==null) val = myJDD.getData(name)
		if (val == '$VIDE') {
			verifyValue(strStepID, myJDD, name, '', timeout,status)
		}else if ( val instanceof Date) {
			verifyValue(strStepID, myJDD, name, val.format(timeFormat), timeout,status)
		}else {
			TNRResult.addSTEPERROR(strStepID, "Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyTimeValue")
	}


	static void setText(def stepID, JDD myJDD, String name, String text=null , int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setText", [stepID:stepID , myJDD: myJDD, name: name , text:text , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '06',stepID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			if (JDDKW.isNULL(text) || JDDKW.isNU(text)) {
				TNRResult.addSTEPINFO(strStepID, "Pas de saisie de texte sur '${tObj.getObjectId()}', valeur du JDD = $text")
			}else {
				String objValue = WebUI.getAttribute(tObj, 'value')
				Log.addTrace("objText:'$objValue'")
				if (objValue==text){
					TNRResult.addSTEPPASS(strStepID,"Saisie du texte '$text' sur '${tObj.getObjectId()}'")
					TNRResult.addDETAIL("La valeur est déjà saisie, pas de modification.")
				}else {
					String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
					if (!errMsg) {
						try {
							WebUI.setText(tObj, text, FailureHandling.STOP_ON_FAILURE)
							TNRResult.addSTEPPASS(strStepID,"Saisie du texte '$text' sur '${tObj.getObjectId()}'")
						} catch (Exception ex) {
							TNRResult.addSTEP(strStepID,"Saisie du texte '$text' sur '${tObj.getObjectId()}'", status)
							TNRResult.addDETAIL(ex.getMessage())
							Log.addTrace('')
						}
					}else {
						TNRResult.addSTEP(strStepID,"Saisie du texte sur '${tObj.getObjectId()}'",status)
						TNRResult.addDETAIL(errMsg)
					}
				}
			}
		}else {
			TNRResult.addSTEPERROR(strStepID,"Saisie du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "setText")
	}




	static void setEncryptedText(def stepID, JDD myJDD, String name, String text=null,int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setEncryptedText", [stepID:stepID , myJDD: myJDD, name: name , text:text , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '07',stepID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				try {
					WebUI.setEncryptedText(tObj, text, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS(strStepID,"Saisie du mot de passe sur '${tObj.getObjectId()}'")
				} catch (Exception ex) {
					TNRResult.addSTEP(strStepID,"Saisie du mot de passe sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP(strStepID,"Saisie du mot de passe sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID,"Saisie du mot de passe sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "setEncryptedText")
	}


	static boolean verifyText(def stepID,JDD myJDD, String name, String text=null, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyText", [stepID:stepID , myJDD: myJDD, name: name , text:text , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '08',stepID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				String gText = KW.getTextByObj(tObj)
				if (WebUI.verifyElementText(tObj, text,FailureHandling.OPTIONAL)) {
					TNRResult.addSTEPPASS(strStepID,"Vérification du texte '$text' sur '${tObj.getObjectId()}'")
					ret = true
				} else {
					TNRResult.addSTEP(strStepID,"Vérification du texte '$text' sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL("la valeur est '$gText' !")
				}
			}else {
				TNRResult.addSTEP(strStepID,"Vérification du texte '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID,"Vérification du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyText",ret)
		return ret
	}



	static boolean verifyTextContains(def stepID,JDD myJDD, String name, String text=null, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyTextContains", [stepID:stepID , myJDD: myJDD, name: name , text:text , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '09',stepID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			String gText = WebUI.getText(tObj)
			String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				if (gText.contains(text)) {
					TNRResult.addSTEPPASS(strStepID,"Vérification que le texte '$gText' contient '$text' sur '${tObj.getObjectId()}'")
					ret = true
				} else {
					TNRResult.addSTEP(strStepID,"Vérification que le texte '$gText' contient '$text' sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL("la valeur est '$gText' !")
				}
			}else {
				TNRResult.addSTEP(strStepID,"Vérification que le texte '$gText' contient '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID,"Vérification que le texte '?' contient '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyTextContains",ret)
		return ret
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
}
