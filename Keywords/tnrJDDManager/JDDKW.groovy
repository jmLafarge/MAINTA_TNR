package tnrJDDManager

import groovy.transform.CompileStatic
import tnrLog.Log

@CompileStatic
public class JDDKW {


	private static final String CLASS_FORLOG = 'JDDKW'

	private static final Map KEYWORD_ALLOWED			= [
		KW_VIDE:'$VIDE',
		KW_NULL:'$NULL',
		KW_DATE:'$DATESYS',
		KW_DATETIME:'$DATETIMESYS',
		KW_ORDRE:'$ORDRE',
		KW_SEQUENCEID:'$SEQUENCEID',
		KW_NU:'$NU',
		KW_TBD:'$TBD',
		KW_UPD:'$UPD'
	]



	static List getKWAllowedList() {
		Log.addTrace("${CLASS_FORLOG}.getKWAllowedList()")
		return KEYWORD_ALLOWED.values() as List
	}



	static boolean isAllowedKeyword(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'isAllowedKeyword', [val:val])
		boolean ret = ((val instanceof String) && (KEYWORD_ALLOWED.containsValue(val) && val!=KEYWORD_ALLOWED.getAt('KW_UPD').toString())|| startWithTBD(val) || isUPD(val))
		Log.addTraceEND(CLASS_FORLOG, 'isAllowedKeyword', ret)
		return ret
	}

	static boolean isNU(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'isNU', [val:val])
		boolean ret =  ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_NU'))
		Log.addTraceEND(CLASS_FORLOG, 'isNU', ret)
		return ret
	}

	static boolean isVIDE(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'isVIDE', [val:val])
		boolean ret =  ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_VIDE'))
		Log.addTraceEND(CLASS_FORLOG, 'isVIDE', ret)
		return ret
	}

	static boolean isNULL(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'isNULL', [val:val])
		boolean ret =  ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_NULL'))
		Log.addTraceEND(CLASS_FORLOG, 'isNULL', ret)
		return ret
	}

	static boolean isDATE(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'isDATE', [val:val])
		boolean ret =  ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_DATE'))
		Log.addTraceEND(CLASS_FORLOG, 'isDATE', ret)
		return ret
	}

	static boolean isDATETIME(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'isDATETIME', [val:val])
		boolean ret =  ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_DATETIME'))
		Log.addTraceEND(CLASS_FORLOG, 'isDATETIME', ret)
		return ret
	}

	static boolean isORDRE(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'isORDRE', [val:val])
		boolean ret =  ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_ORDRE'))
		Log.addTraceEND(CLASS_FORLOG, 'isORDRE', ret)
		return ret
	}

	static boolean isSEQUENCEID(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'isSEQUENCEID', [val:val])
		boolean ret =  ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_SEQUENCEID'))
		Log.addTraceEND(CLASS_FORLOG, 'isSEQUENCEID', ret)
		return ret
	}


	static boolean startWithTBD(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'startWithTBD', [val:val])
		boolean ret =  ((val instanceof String) && val.toString().startsWith(KEYWORD_ALLOWED.getAt('KW_TBD').toString()))
		Log.addTraceEND(CLASS_FORLOG, 'startWithTBD', ret)
		return ret
	}

	static boolean startWithUPD(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'isAllowedKeyword', [val:val])
		boolean ret =  ((val instanceof String) && val.toString().startsWith(KEYWORD_ALLOWED.getAt('KW_UPD').toString()))
		Log.addTraceEND(CLASS_FORLOG, 'isAllowedKeyword', ret)
		return ret
	}


	static boolean isTBD(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'isTBD', [val:val])
		boolean ret = false
		if (startWithTBD(val)) {
			def li = val.toString().split('\\*')
			ret = li.size() == 2
		}
		Log.addTraceEND(CLASS_FORLOG, 'isTBD', ret)
		return ret
	}



	static boolean isUPD(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'isUPD', [val:val])
		boolean ret = false
		if (startWithUPD(val)) {
			def li = val.toString().split('\\*')
			ret = li.size() == 3
		}
		Log.addTraceEND(CLASS_FORLOG, 'isUPD', ret)
		return ret
	}




	static String getKW_NU() {
		Log.addTrace("${CLASS_FORLOG}.getKW_NU()")
		return KEYWORD_ALLOWED.getAt('KW_NU')
	}

	static String getKW_VIDE() {
		Log.addTrace("${CLASS_FORLOG}.getKW_VIDE()")
		return KEYWORD_ALLOWED.getAt('KW_VIDE')
	}

	static String getKW_NULL() {
		Log.addTrace("${CLASS_FORLOG}.getKW_NULL()")
		return KEYWORD_ALLOWED.getAt('KW_NULL')
	}

	static String getKW_DATE() {
		Log.addTrace("${CLASS_FORLOG}.getKW_DATE()")
		return KEYWORD_ALLOWED.getAt('KW_DATE')
	}

	static String getKW_DATETIME() {
		Log.addTrace("${CLASS_FORLOG}.getKW_DATETIME()")
		return KEYWORD_ALLOWED.getAt('KW_DATETIME')
	}

	static String getKW_ORDRE() {
		Log.addTrace("${CLASS_FORLOG}.getKW_ORDRE()")
		return KEYWORD_ALLOWED.getAt('KW_ORDRE')
	}

	static String getKW_SEQUENCEID() {
		Log.addTrace("${CLASS_FORLOG}.getKW_SEQUENCEID()")
		return KEYWORD_ALLOWED.getAt('KW_SEQUENCEID')
	}

	static String getKW_TBD() {
		Log.addTrace("${CLASS_FORLOG}.getKW_TBD()")
		return KEYWORD_ALLOWED.getAt('KW_TBD')
	}





	static def getValueOfKW_TBD(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'getValueOfKW_TBD', [val:val])
		def ret
		if (isTBD(val)) {
			def values = val.toString().split('\\*')
			if (values.size()==2) {
				ret = values[1]
			}
		}
		Log.addTraceEND(CLASS_FORLOG, 'getValueOfKW_TBD', ret)
		return ret
	}

	static def getOldValueOfKW_UPD(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'getOldValueOfKW_UPD', [val:val])
		def ret
		if (isUPD(val)) {
			def values = val.toString().split('\\*')
			ret = values[1]
		}
		Log.addTraceEND(CLASS_FORLOG, 'getOldValueOfKW_UPD', ret)
		return ret
	}

	static def getNewValueOfKW_UPD(def val) {
		Log.addTraceBEGIN(CLASS_FORLOG, 'getNewValueOfKW_UPD', [val:val])
		def ret
		if (isUPD(val)) {
			def values = val.toString().split('\\*')
			ret = values[2]
		}
		Log.addTraceEND(CLASS_FORLOG, 'getNewValueOfKW_UPD', ret)
		return ret
	}
}