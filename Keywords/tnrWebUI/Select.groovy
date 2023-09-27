package tnrWebUI

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.JDD
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
public class Select {
	
	private static final String CLASS_FOR_LOG = 'Select'
	
		private static final String CLASS_CODE = 'SEL'
		
		
		
		public static void verifyOptionSelectedByLabel(def stepID, JDD myJDD, String name, String text=null, boolean isRegex = false, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
			Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyOptionSelectedByLabel", [stepID:stepID , myJDD: myJDD, name: name , text:text , isRegex:isRegex , timeout:timeout , status:status])
			String strStepID = StepID.getStrStepID(CLASS_CODE, '01',stepID)
			
			TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
			if (!msgTO) {
				if (text==null) {
					text = myJDD.getStrData(name)
				}
				String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
				if (!errMsg) {
					String paraIV = myJDD.myJDDParam.getINTERNALVALUEFor(name)
					if (paraIV) {
						String valIV = myJDD.myJDDIV.getValueOf(paraIV, text)
						if (valIV) {
							try {
								WebUI.verifyOptionSelectedByLabel(tObj, valIV, isRegex,timeout, FailureHandling.STOP_ON_FAILURE)
								TNRResult.addSTEPPASS(strStepID, "Vérifier que l'option '$valIV'($text) de '${tObj.getObjectId()}' soit sélectionnée")
							} catch (Exception ex) {
								TNRResult.addSTEP(strStepID, "Vérifier que l'option '$valIV'($text) de '${tObj.getObjectId()}' soit sélectionnée",status)
								TNRResult.addDETAIL(ex.getMessage())
							}
						}else {
							TNRResult.addSTEP(strStepID, "Vérifier que l'option '?'($text) de '${tObj.getObjectId()}' soit sélectionnée",status)
							TNRResult.addDETAIL("Pas de valeur trouvée pour l'INTERNALVALUE")
						}
					}else {
						try {
							WebUI.verifyOptionSelectedByLabel(tObj, text, isRegex,timeout, FailureHandling.STOP_ON_FAILURE)
							TNRResult.addSTEPPASS(strStepID, "Vérifier que l'option '$text' de '${tObj.getObjectId()}' soit sélectionnée")
						} catch (Exception ex) {
							TNRResult.addSTEP(strStepID, "Vérifier que l'option '$text' de '${tObj.getObjectId()}' soit sélectionnée",status)
							TNRResult.addDETAIL(ex.getMessage())
						}
					}
				}else {
					TNRResult.addSTEP(strStepID, "Vérifier l'option '$text' de '$name'",status)
					TNRResult.addDETAIL(errMsg)
				}
			}else {
				TNRResult.addSTEPERROR(strStepID, "Vérifier l'option '$text' de '$name'")
				TNRResult.addDETAIL(msgTO)
			}
			Log.addTraceEND(CLASS_FOR_LOG, "verifyOptionSelectedByLabel")
		}
		
		
		public static void scrollAndSelectOptionByLabel(def stepID, JDD myJDD, String name, String text=null, boolean isRegex = true, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
			Log.addTraceBEGIN(CLASS_FOR_LOG, "scrollAndSelectOptionByLabel", [ stepID:stepID , timeout:timeout , status:status])
			String strStepID = StepID.getStrStepID(CLASS_CODE, '02',stepID)
			TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
			if (!msgTO) {
				if (text==null) {
					text = myJDD.getStrData(name)
				}
				String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
				if (!errMsg) {
					String paraIV = myJDD.myJDDParam.getINTERNALVALUEFor(name)
					if (paraIV) {
						String valIV = myJDD.myJDDIV.getValueOf(paraIV, text)
						if (valIV) {
							try {
								WebUI.selectOptionByLabel(tObj, valIV, isRegex,FailureHandling.STOP_ON_FAILURE)
								TNRResult.addSTEPPASS(strStepID, "Scroll et select option '$valIV'($text) sur '${tObj.getObjectId()}'")
							} catch (Exception ex) {
								TNRResult.addSTEP(strStepID, "Scroll et select option '$valIV'($text) sur '${tObj.getObjectId()}'",status)
								TNRResult.addDETAIL(ex.getMessage())
							}
						}else {
							TNRResult.addSTEP(strStepID, "Scroll et select option '?'($text) sur '${tObj.getObjectId()}'",status)
							TNRResult.addDETAIL("Pas de valeur trouvée pour l'INTERNALVALUE")
						}
					}else {
						try {
							WebUI.selectOptionByLabel(tObj, text, isRegex,FailureHandling.STOP_ON_FAILURE)
							TNRResult.addSTEPPASS(strStepID, "Scroll et select option '$text' sur '${tObj.getObjectId()}'")
						} catch (Exception ex) {
							TNRResult.addSTEP(strStepID, "Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
							TNRResult.addDETAIL(ex.getMessage())
						}
					}
				}else {
					TNRResult.addSTEP(strStepID, "Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(errMsg)
				}
			}else {
				TNRResult.addSTEPERROR(strStepID, "Scroll et select option '$text' sur '$name'")
				TNRResult.addDETAIL(msgTO)
			}
			Log.addTraceEND(CLASS_FOR_LOG, "scrollAndSelectOptionByLabel")
		}
		
		
		
		
		
		
		
		
		// pas utilisé, on utilise scrollAndSelectOptionByLabel
		private static void scrollAndSelectOptionByValue(def stepID , JDD myJDD, String name, String text=null, boolean isRegex = true, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
			Log.addTraceBEGIN(CLASS_FOR_LOG, "scrollAndSelectOptionByValue", [ stepID:stepID , timeout:timeout , status:status])
			String strStepID = StepID.getStrStepID(CLASS_CODE, '03',stepID)
			TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
			if (!msgTO) {
				if (text==null) text = myJDD.getStrData(name)
				String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
				if (!errMsg) {
					try {
						WebUI.selectOptionByValue(tObj, text, isRegex,FailureHandling.STOP_ON_FAILURE)
						TNRResult.addSTEPPASS(strStepID, "Scroll et select option '$text' sur '${tObj.getObjectId()}'")
					} catch (Exception ex) {
						TNRResult.addSTEP(strStepID, "Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
						TNRResult.addDETAIL(ex.getMessage())
					}
				}else {
					TNRResult.addSTEP(strStepID, "Scroll et select option '$text' sur '${tObj.getObjectId()}'",status)
					TNRResult.addDETAIL(errMsg)
				}
			}else {
				TNRResult.addSTEPERROR(strStepID, "Scroll et select option '$text' sur '$name'")
				TNRResult.addDETAIL(msgTO)
			}
			Log.addTraceEND(CLASS_FOR_LOG, "scrollAndSelectOptionByValue")
		}
	
		
}
