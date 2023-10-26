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

	private static final String CLASS_NAME = 'Text'


	static void verifyValue( JDD myJDD, String name, String text=null, int timeout  = (int) GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "verifyValue", [ myJDD:myJDD.toString() , name: name , text:text , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyValue'+ myJDD.toString() + name)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) text = myJDD.getStrData(name)
			if (JDDKW.isNU(text)) {
				TNRResult.addSTEP(strStepID, "Pas de vérification pour $name, valeur du JDD = NU")
			}else {
				String errMsg = '' //WUI.goToElementByObj(tObj, name, timeout, status)
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
						TNRResult.addDETAIL("La valeur du champ est '$val' !")
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
		Log.addTraceEND(CLASS_NAME, "verifyValue")
	}






	static void setDate( JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "setDate", [ myJDD:myJDD.toString() , name: name , val:val , dateFormat:dateFormat , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'setDate'+ myJDD.toString() + name)
		val = val ?: myJDD.getData(name)
		if (JDDKW.isNULL(val) || JDDKW.isNU(val)) {
			TNRResult.addSTEPINFO('',"Pas de saisie de texte sur '$name', valeur du JDD = $val")
		}else if ( val instanceof Date) {
			setText( myJDD, name, val.format(dateFormat), timeout,status)
		}else {
			TNRResult.addSTEPERROR(strStepID, "Saisie du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}

		Log.addTraceEND(CLASS_NAME, "setDate")
	}





	static void verifyDateText( JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Log.addTrace("verifyDateText : name='$name' val='${val.toString()} dateFormat='dateFormat' status='$status'")
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyDateText'+ myJDD.toString() + name)
		if (val==null) val = myJDD.getData(name)

		if (val == '$VIDE') {
			verifyText( myJDD, name, '', timeout, status)
		}else if ( val instanceof Date) {
			Log.addTrace('val.format(dateFormat) :' +val.format(dateFormat))
			verifyText(myJDD, name, val.format(dateFormat), timeout,status)
		}else {
			TNRResult.addSTEPERROR(strStepID, "Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
	}




	static void verifyDateValue( JDD myJDD, String name, def val=null, String dateFormat = 'dd/MM/yyyy', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTraceBEGIN(CLASS_NAME, "verifyDateValue", [ myJDD:myJDD.toString() , name: name , val:val , dateFormat:dateFormat , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyDateValue'+ myJDD.toString() + name)
		if (val==null) val = myJDD.getData(name)
		if (val == '$VIDE') {
			verifyValue(myJDD, name, '', timeout,status)
		}else if ( val instanceof Date) {
			verifyValue(myJDD, name, val.format(dateFormat),timeout, status)
		}else {
			TNRResult.addSTEPERROR(strStepID, "Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
		Log.addTraceEND(CLASS_NAME, "verifyDateValue")
	}




	static void verifyTimeValue( JDD myJDD, String name, def val=null, String timeFormat = 'HH:mm:ss', int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {

		Log.addTraceBEGIN(CLASS_NAME, "verifyTimeValue", [ myJDD:myJDD.toString() , name: name , val:val , timeFormat:timeFormat , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyTimeValue'+ myJDD.toString() + name)
		if (val==null) val = myJDD.getData(name)
		if (val == '$VIDE') {
			verifyValue(myJDD, name, '', timeout,status)
		}else if ( val instanceof Date) {
			verifyValue(myJDD, name, val.format(timeFormat), timeout,status)
		}else {
			TNRResult.addSTEPERROR(strStepID, "Vérification du texte '${val.toString()}' sur '$name'")
			TNRResult.addDETAIL("Erreur de JDD de '$name', la valeur '${val.toString()}' n'est pas une date ! getClass = " + val.getClass())
		}
		Log.addTraceEND(CLASS_NAME, "verifyTimeValue")
	}


	static void setText( JDD myJDD, String name, String text=null , int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "setText", [ myJDD:myJDD.toString() , name: name , text:text , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'setText'+ myJDD.toString() + name)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			if (JDDKW.isNULL(text) || JDDKW.isNU(text)) {
				TNRResult.addSTEPINFO('',"Pas de saisie de texte sur '${tObj.getObjectId()}', valeur du JDD = $text")
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
		Log.addTraceEND(CLASS_NAME, "setText")
	}




	static void setEncryptedText( JDD myJDD, String name, String text=null,int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_NAME, "setEncryptedText", [ myJDD:myJDD.toString() , name: name , text:text , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'setEncryptedText'+ myJDD.toString() + name)
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
		Log.addTraceEND(CLASS_NAME, "setEncryptedText")
	}


	static boolean verifyTextOLD(JDD myJDD, String name, String text=null, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
		Log.addTraceBEGIN(CLASS_NAME, "verifyText", [ myJDD:myJDD.toString() , name: name , text:text , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyText'+ myJDD.toString() + name)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			String errMsg = '' //WUI.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				String gText = WUI.getTextByObj(tObj)
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
		Log.addTraceEND(CLASS_NAME, "verifyText",ret)
		return ret
	}
	
	
	static boolean verifyText(JDD myJDD, String name, String text=null, int timeoutInMilliseconds, String status = 'FAIL')  {
		Log.addTraceBEGIN(CLASS_NAME, "verifyText", [ myJDD:myJDD.toString() , name: name , text:text , timeoutInMilliseconds:timeoutInMilliseconds , status:status])
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyText'+ myJDD.toString() + name)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		boolean ret = false
		String gText = ''
		if (!msgTO) {
			if (text==null) {
				text = myJDD.getStrData(name)
			}
			int waitedTime = 0
			while (waitedTime < timeoutInMilliseconds) {
				gText = WUI.getTextByObj(tObj)
				if (WebUI.verifyElementText(tObj, text,FailureHandling.OPTIONAL)) {
					ret = true
					break
				}else if (gText) {
					ret = false
					break
				}
				Thread.sleep(100)  // Pause pour 100 millisecondes
				waitedTime += 100
			}
			
			
			if (ret) {
				TNRResult.addSTEPPASS(strStepID,"Vérification du texte '$text' sur '${tObj.getObjectId()}'")
			} else {
				TNRResult.addSTEP(strStepID,"Vérification du texte '$text' sur '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL("la valeur est '$gText' !")
			}
			
			
			
		}else {
			TNRResult.addSTEPERROR(strStepID,"Vérification du texte '$text' sur '$name' impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_NAME, "verifyText",ret)
		return ret
	}


	/* Not used at the moment
	 static boolean verifyTextContains(JDD myJDD, String name, String text=null, int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL')  {
	 Log.addTraceBEGIN(CLASS_NAME, "verifyTextContains", [ myJDD:myJDD.toString() , name: name , text:text , timeout:timeout , status:status])
	 String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyTextContains'+ myJDD.toString() + name)
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
	 Log.addTraceEND(CLASS_NAME, "verifyTextContains",ret)
	 return ret
	 }
	 */





	public static String deleteText(TestObject tObj) {
		Log.addTraceBEGIN(CLASS_NAME, "deleteText", [tObj: tObj.getObjectId()])
		String msg =null
		try {
			WebUI.setText(tObj, '', FailureHandling.STOP_ON_FAILURE)
			Log.addTrace("Effacement du texte sur l'object '${tObj.getObjectId()}'")
		} catch (Exception ex) {
			msg = "Effacement du texte : " + ex.getMessage()
		}
		Log.addTraceEND(CLASS_NAME, "deleteText",msg)
		return msg
	}
}
