package tnrWebUI

import groovy.transform.CompileStatic
import tnrResultManager.TNRResult
import tnrSqlManager.SQL

/**
 * 
 *
 * @author JM LAFARGE
 * @version 1.0
 *
 */

@CompileStatic
public class STEP_SQL {
	
	private static final String CLASS_FOR_LOG = 'STEP_SQl'
	
	
	protected static void verifyMaintaVersion(String strStepID, String maintaVersion) {
		
		String verBDD = SQL.getMaintaVersion()
		if (verBDD == maintaVersion) {
			TNRResult.addSTEPPASS(strStepID,"Controle de la version $maintaVersion en BDD")
		}else {
			TNRResult.addSTEPFAIL(strStepID,"Controle de la version $maintaVersion en BDD")
			TNRResult.addDETAIL("La valeur en BDD est $verBDD")
		}
	}
	
}
