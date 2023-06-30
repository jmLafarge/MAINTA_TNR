package my

import groovy.transform.CompileStatic

@CompileStatic
public class JDDKW {

	private static final Map KEYWORD_ALLOWED			= [
		KW_VIDE:'$VIDE',
		KW_NULL:'$NULL',
		KW_DATE:'$DATESYS',
		KW_DATETIME:'$DATETIMESYS',
		KW_ORDRE:'$ORDRE',
		KW_SEQUENCEID:'$SEQUENCEID',
		KW_NU:'$NU',
		KW_TBD:'$TBD'
	]

	static boolean isAllowedKeyword(String val) {
		return (KEYWORD_ALLOWED.containsValue(val) || startWithTBD(val))
	}

	static boolean isNU(def val) {
		return ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_NU'))
	}

	static boolean isVIDE(def val) {
		return ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_VIDE'))
	}

	static boolean isNULL(def val) {
		return ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_NULL'))
	}

	static boolean isDATE(def val) {
		return ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_DATE'))
	}

	static boolean isDATETIME(def val) {
		return ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_DATETIME'))
	}

	static boolean isORDRE(def val) {
		return ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_ORDRE'))
	}

	static boolean isSEQUENCEID(def val) {
		return ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_SEQUENCEID'))
	}

	static boolean isTBD(def val) {
		return ((val instanceof String) && val == KEYWORD_ALLOWED.getAt('KW_TBD'))
	}

	static boolean startWithTBD(def val) {
		return ((val instanceof String) && val.toString().startsWith(KEYWORD_ALLOWED.getAt('KW_TBD').toString()))
	}




	static String getKW_NU() {
		return KEYWORD_ALLOWED.getAt('KW_NU')
	}

	static String getKW_VIDE() {
		return KEYWORD_ALLOWED.getAt('KW_VIDE')
	}

	static String getKW_NULL() {
		return KEYWORD_ALLOWED.getAt('KW_NULL')
	}

	static String getKW_DATE() {
		return KEYWORD_ALLOWED.getAt('KW_DATE')
	}

	static String getKW_DATETIME() {
		return KEYWORD_ALLOWED.getAt('KW_DATETIME')
	}

	static String getKW_ORDRE() {
		return KEYWORD_ALLOWED.getAt('KW_ORDRE')
	}

	static String getKW_SEQUENCEID() {
		return KEYWORD_ALLOWED.getAt('KW_SEQUENCEID')
	}

	static String getKW_TBD() {
		return KEYWORD_ALLOWED.getAt('KW_TBD')
	}





	static def getValueOfKW_TBD(def val) {

		if (startWithTBD(val)) {
			def values = val.toString().split('\\$')
			if (values.size()==3) {
				return values[2]
			}
		}
		return null
	}
}