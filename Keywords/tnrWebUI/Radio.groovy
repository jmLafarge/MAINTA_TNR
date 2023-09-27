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
 * Gère les boutons RADIO
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */
@CompileStatic
public class Radio {
	
	
	private static final String CLASS_FOR_LOG = 'Radio'
	
	private static final String CLASS_CODE = 'RAD'
	
	
	
	static void verifyRadioChecked(def stepID, JDD myJDD, String name,  int timeout = (int)GlobalVariable.TIMEOUT , String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "verifyRadioChecked", [stepID:stepID , myJDD: myJDD, name: name , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '01',stepID)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			if (JDDKW.isNU(myJDD.getStrData(name))) {
				TNRResult.addSTEP(strStepID, "Pas de vérification pour $name, valeur du JDD = NU")
			}else {
				String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
				if (!errMsg) {
					String val = WebUI.getAttribute(tObj, 'checked')
					Log.addTrace("value=$val")
					if (val==null) {
						TNRResult.addSTEP(strStepID, "Vérifier que le bouton radio '$name', soit sélectionné",status)
						TNRResult.addDETAIL("L'attribut 'checked' n'existe pas !")
					}else if (val=='true') {
						TNRResult.addSTEPPASS(strStepID, "Vérifier que le bouton radio '$name', soit sélectionné")
					}else {
						TNRResult.addSTEP(strStepID, "Vérifier que le bouton radio '$name', soit sélectionné",status)
						TNRResult.addDETAIL("La valeur de l'attribut checked est '$val' !")
					}
				}else {
					TNRResult.addSTEP(strStepID, "Vérifier que le bouton radio '$name', soit sélectionné",status)
					TNRResult.addDETAIL(errMsg)
				}
			}
		}else {
			TNRResult.addSTEPERROR(strStepID, "Vérifier que le bouton radio '$name', soit sélectionné impossible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "verifyRadioChecked")
	}


	static void scrollAndSetRadio(def stepID, JDD myJDD, String name, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "scrollAndSetRadio", [stepID:stepID , myJDD: myJDD, name: name , timeout:timeout , status:status])
		String strStepID = StepID.getStrStepID(CLASS_CODE, '02',stepID)
		//Création du TO label en tenant compte de la valeur --> voir resolveXpath
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		String nameWithoutLbl = name.substring(3)
		if (!msgTO) {
			String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				try {
					WebUI.click(tObj, FailureHandling.STOP_ON_FAILURE)
					TNRResult.addSTEPPASS(strStepID, "Clic sur bouton radio '$nameWithoutLbl' (" + myJDD.getStrData(nameWithoutLbl) + ')')
				} catch (Exception ex) {
					TNRResult.addSTEP(strStepID, "Clic sur bouton radio '$nameWithoutLbl' (" + myJDD.getStrData(nameWithoutLbl) + ')',status)
					TNRResult.addDETAIL(ex.getMessage())
				}
			}else {
				TNRResult.addSTEP(strStepID, "Clic sur bouton radio '$nameWithoutLbl' (" + myJDD.getStrData(nameWithoutLbl) + ')',status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID, "Clic sur '$nameWithoutLbl' imposible")
			TNRResult.addDETAIL(msgTO)
		}
		Log.addTraceEND(CLASS_FOR_LOG, "scrollAndSetRadio")
	}
	
	
}
