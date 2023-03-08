package my

public class JDDKW {

	private static final Map KEYWORD_ALLOWED			= [
		KW_VIDE:'$VIDE',
		KW_NULL:'$NULL',
		KW_DATE:'$DATESYS',
		KW_DATETIME:'$DATETIMESYS',
		KW_ORDRE:'$ORDRE',
		KW_SEQUENCEID:'$SEQUENCEID'
	]

	static boolean isAllowedKeyword(String val) {
		return this.KEYWORD_ALLOWED.containsValue(val)
	}



	static boolean isVIDE(String val) {
		return (val == this.KEYWORD_ALLOWED.getAt('KW_VIDE'))
	}

	static boolean isNULL(String val) {
		return (val == this.KEYWORD_ALLOWED.getAt('KW_NULL'))
	}

	static boolean isDATE(String val) {
		return (val == this.KEYWORD_ALLOWED.getAt('KW_DATE'))
	}

	static boolean isDATETIME(String val) {
		return (val == this.KEYWORD_ALLOWED.getAt('KW_DATETIME'))
	}

	static boolean isORDRE(String val) {
		return (val == this.KEYWORD_ALLOWED.getAt('KW_ORDRE'))
	}

	static boolean isSEQUENCEID(def val) {
		return ((val instanceof String) && val == this.KEYWORD_ALLOWED.getAt('KW_SEQUENCEID'))
	}



	static String getKW_VIDE() {
		return this.KEYWORD_ALLOWED.getAt('KW_VIDE')
	}

	static String getKW_NULL() {
		return this.KEYWORD_ALLOWED.getAt('KW_NULL')
	}

	static String getKW_DATE() {
		return this.KEYWORD_ALLOWED.getAt('KW_DATE')
	}

	static String getKW_DATETIME() {
		return this.KEYWORD_ALLOWED.getAt('KW_DATETIME')
	}

	static String getKW_ORDRE() {
		return this.KEYWORD_ALLOWED.getAt('KW_ORDRE')
	}

	static String getKW_SEQUENCEID() {
		return this.KEYWORD_ALLOWED.getAt('KW_SEQUENCEID')
	}
}