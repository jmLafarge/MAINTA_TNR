package tnrWebUI

import groovy.transform.CompileStatic
import internal.GlobalVariable
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
		if (KW.isElementPresent(NAV.myGlobalJDD, 'divModalActive', 2)) {
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
		if (KW.isElementPresent(NAV.myGlobalJDD, 'divModalInactive', 2)) {
			TNRResult.addSTEPPASS("Fermeture de la fenetre de saisie")
			ret = true
		}else {
			TNRResult.addDETAIL("La fenetre de saisie ne s'est pas fermée")
		}
		Log.addTraceEND(CLASS_FOR_LOG, "isClosed",ret)
		return ret
	}


	static boolean isNbRecordsEqualTo(int nbRecords, int timeout=(int)GlobalVariable.TIMEOUT,String status = 'FAIL') {
		boolean ret = false
		Log.addTraceBEGIN(CLASS_FOR_LOG, "isNbRecordsEqualTo", [nbRecords:nbRecords , timeout:timeout , status:status])
		//"$nbRecords"
		String gtext = KW.getText(NAV.myGlobalJDD, 'divModalNbRecords',timeout)
		Log.addTraceEND(CLASS_FOR_LOG, "isNbRecordsEqualTo",ret)
		return ret
	}
}
