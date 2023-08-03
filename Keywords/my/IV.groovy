package my


public class IV {
	
	private static final String CLASS_FORLOG = 'IV'
	
	private static List <List <String>> list  = []
	
	

	
	public static String add(List li) {
	
		list.add(li)
	}

	
	public static List getList() {
		
		return list
	}
	
	/**
	 * Récupère la valeur interne d'un paramètre.
	 *
	 * @param para Nom du paramètre.
	 * @param val Valeur du paramètre.
	 * @return La valeur interne du paramètre.
	 */
	public static String getInternalValueOf(String para, String val) {
		Log.addTraceBEGIN(CLASS_FORLOG,"getInternalValueOf",[para:para,val:val])

		String res = list.find { it[0] == para && it[1] == val }?.get(2)

		Log.addTraceEND(CLASS_FORLOG,"getInternalValueOf",res)
		return res
	}
	
	
}
