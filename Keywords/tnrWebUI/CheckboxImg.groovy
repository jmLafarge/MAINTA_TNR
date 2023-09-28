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
 * Gère les recherches par Assistant
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */
@CompileStatic
public class CheckboxImg {


	private static final String CLASS_NAME = 'CheckboxImg'



	static void clickImgboxIfNeeded( JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL')  {

		String strStepID = StepID.getStrStepID(CLASS_NAME + 'clickImgboxIfNeeded' + myJDD.toString() + name)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			boolean cond = myJDD.getStrData(name)==textTrue
			String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				if (cond) {
					if (isImgBoxChecked(tObj)) {
						TNRResult.addSTEPPASS(strStepID, "Cocher la case à cocher '$name'")
						TNRResult.addDETAIL("déjà cochée")
					}else {
						for ( n in 1..3) {
							TNRResult.addSTEPINFO("Tentative pour cocher '${tObj.getObjectId()}' $n/3" )
							STEP.simpleClick(myJDD,'ST_DEF')
							if (STEP.waitAndAcceptAlert((int)GlobalVariable.TIMEOUT,null)) {
								WUI.delay(1000)
								if (isImgBoxChecked(tObj)) {
									TNRResult.addSTEPPASS(strStepID, "Cocher la case à cocher '$name'")
									break
								}
							}
						}
					}
				}
			}else {
				TNRResult.addSTEP(strStepID, "Cocher/Décocher la case à cocher '" + name + "'", status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID, "Case à cocher '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}






	static void verifyImgCheckedOrNot( JDD myJDD, String name, String textTrue, int timeout = (int)GlobalVariable.TIMEOUT, String status = 'FAIL') {
		String strStepID = StepID.getStrStepID(CLASS_NAME + 'verifyImgCheckedOrNot'+ myJDD.toString() + name)
		TO myTO = new TO() ; TestObject tObj  = myTO.make(myJDD,name) ;String msgTO = myTO.getMsg()
		if (!msgTO) {
			boolean cond = myJDD.getStrData(name)==textTrue
			String errMsg = WUI.goToElementByObj(tObj, name, timeout, status)
			if (!errMsg) {
				String srcAttribut = WebUI.getAttribute(tObj, 'src')
				
				if (cond) {
					if (srcAttribut==null) {
						TNRResult.addSTEP(strStepID, "Vérifier que '${tObj.getObjectId()}'soit coché",status)
						TNRResult.addDETAIL("L'attribut 'scr' n'existe pas")
					}else if (srcAttribut.endsWith('133.gif')) {
						TNRResult.addSTEPPASS(strStepID, "Vérifier que '${tObj.getObjectId()}'soit coché")
					}else if (srcAttribut.endsWith('134.gif')) {
						TNRResult.addSTEP(strStepID, "Vérifier que '${tObj.getObjectId()}'soit coché",status)
						TNRResult.addDETAIL('La case est décochée !')
					}else {
						TNRResult.addSTEP(strStepID, "Vérifier que '${tObj.getObjectId()}'soit coché",status)
						TNRResult.addDETAIL("L'image n'est pas connu '$srcAttribut'")
					}
				}else {
					if (srcAttribut==null) {
						TNRResult.addSTEP(strStepID, "Vérifier que '${tObj.getObjectId()}'soit décoché",status)
						TNRResult.addDETAIL("L'attribut 'scr' n'existe pas")
					}else if (srcAttribut.endsWith('134.gif')) {
						TNRResult.addSTEPPASS(strStepID, "Vérifier que '${tObj.getObjectId()}'soit décoché")
					}else if (srcAttribut.endsWith('133.gif')) {
						TNRResult.addSTEP(strStepID, "Vérifier que '${tObj.getObjectId()}'soit décoché",status)
						TNRResult.addDETAIL('La case est cochée !')
					}else {
						TNRResult.addSTEP(strStepID, "Vérifier que '${tObj.getObjectId()}'soit décoché",status)
						TNRResult.addDETAIL("L'image n'est pas connu '$srcAttribut'")
					}
				}
			}else {
				TNRResult.addSTEP(strStepID, "Vérifier la case à cocher '${tObj.getObjectId()}'",status)
				TNRResult.addDETAIL(errMsg)
			}
		}else {
			TNRResult.addSTEPERROR(strStepID, "Vérifier état case à cocher '$name'")
			TNRResult.addDETAIL(msgTO)
		}
	}


	private static boolean isImgBoxChecked(TestObject tObj) {
		Log.addTraceBEGIN(CLASS_NAME, "isImgBoxChecked", [tObj: tObj])
		String srcAttribut = WebUI.getAttribute(tObj, 'src')
		boolean ret = srcAttribut && srcAttribut.endsWith('133.gif')
		Log.addTraceEND(CLASS_NAME, "isImgBoxChecked",ret)
		return ret
	}

	private static boolean isImgBoxUnchecked(TestObject tObj) {
		Log.addTraceBEGIN(CLASS_NAME, "isImgBoxUnchecked", [tObj: tObj])
		String srcAttribut = WebUI.getAttribute(tObj, 'src')
		boolean ret = srcAttribut && srcAttribut.endsWith('134.gif')
		Log.addTraceEND(CLASS_NAME, "isImgBoxUnchecked",ret)
		return ret
	}


} //end of class
