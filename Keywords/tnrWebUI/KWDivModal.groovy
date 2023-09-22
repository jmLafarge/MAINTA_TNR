package tnrWebUI

import com.kms.katalon.core.testobject.TestObject

import groovy.transform.CompileStatic
import internal.GlobalVariable
import tnrJDDManager.GlobalJDD
import tnrLog.Log
import tnrResultManager.TNRResult


/**
 * Gère les DIV modal
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */
@CompileStatic
public class KWDivModal {

	private static final String CLASS_FOR_LOG = 'KWDivModal'


	static boolean isOpened(int timeout=(int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "isOpened", [timeout:timeout])
		boolean ret = false
		if (KW.isElementPresent(GlobalJDD.myGlobalJDD, 'divModalActive', 2)) {
			TNRResult.addSTEPPASS("Ouverture de la fenetre de saisie ")
			ret = true
		}else {
			TNRResult.addDETAIL("La fenetre de saisie ne s'est pas ouverte")
		}
		Log.addTraceEND(CLASS_FOR_LOG, "isOpened",ret)
		return ret
	}


	static boolean isClosed(int timeout=(int)GlobalVariable.TIMEOUT) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "isClosed", [timeout:timeout])
		boolean ret = false
		if (KW.isElementPresent(GlobalJDD.myGlobalJDD, 'divModalInactive', 2)) {
			TNRResult.addSTEPPASS("Fermeture de la fenetre de saisie")
			ret = true
		}else {
			TNRResult.addDETAIL("La fenetre de saisie ne s'est pas fermée")
		}
		Log.addTraceEND(CLASS_FOR_LOG, "isClosed",ret)
		return ret
	}

	
	

	static boolean isNbRecordsEqualTo(int nbRecords, int timeout=(int)GlobalVariable.TIMEOUT,String status = 'FAIL') {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "isNbRecordsEqualTo", [nbRecords:nbRecords , timeout:timeout , status:status])
		String divModalNbRecords='divModalNbRecords'
		TO myTO = new TO() ; TestObject tObj  = myTO.make(GlobalJDD.myGlobalJDD,divModalNbRecords) ;String msgTO = myTO.getMsg()
		String gText =''
		boolean ret = false
		long ms = 0
		long timeoutms = timeout*1000
		if (!msgTO) {
			if (KW.isElementPresentByObj(tObj, timeout)) {
				while (ms < timeoutms) {
					try {
						gText = KW.getTextByObj(tObj)
					} catch (Exception ex) {
						Log.addINFO("Lecture du texte sur '$divModalNbRecords' KO")
						Log.addDETAIL(ex.getMessage())
					}
					if (gText==nbRecords.toString()) {
						ret=true
						break
					}
					Thread.sleep(100)
					ms+=100
				}
			}
		}
		
		
		
		
		while (ms < timeoutms) {
			gText = KW.getText(GlobalJDD.myGlobalJDD, 'divModalNbRecords',timeout)
			if (gText==nbRecords.toString()) {
				ret=true
				break
			}
			Thread.sleep(100)
			ms+=100
		}
		if (ret) {
			Log.addINFO("Nombre de records attendu '$nbRecords' obtenu en $ms ms")
		}else {
			Log.addINFO("Nombre de records attendu '$nbRecords' KO après $ms ms")
		}
		Log.addTraceEND(CLASS_FOR_LOG, "isNbRecordsEqualTo",ret)
		return ret
	}
}
