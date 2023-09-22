package tnrJDDManager

import tnrCommon.TNRPropertiesReader
import tnrLog.Log

public class GlobalJDD {
	
	private static final String CLASS_FOR_LOG = 'GlobalJDD'
	
	public static JDD myGlobalJDD

	static {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"static",[:])
		myGlobalJDD = new JDD(TNRPropertiesReader.getMyProperty('JDD_PATH') + File.separator + TNRPropertiesReader.getMyProperty('JDDGLOBAL_FILENAME'),'001',null)
		Log.addTraceEND(CLASS_FOR_LOG,"static")
		}
}
