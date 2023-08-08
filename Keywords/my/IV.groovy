package my

/**
 * Gere les INTERNALVALUE (certains select qui ne sont pas associé à une table).
 * Chaque élément dans la liste excel est représenté par une map  'param', 'value', 'internalValue'.
 */
public class IV {

	private static final String CLASS_FORLOG = 'IV'

	private static List<Map<String, String>> list = []

	/**
	 * Ajoute une nouvelle entrée à la liste
	 *
	 * @param param Le parametre
	 * @param value La valeur
	 * @param internalValue La valeur interne 
	 */
	public static void add(String param, String value, String internalValue) {
		Map<String, String> newItem = [param: param, value: value, internalValue: internalValue]
		list.add(newItem)
	}

	/**
	 * Récupère la liste de toutes les valeurs
	 *
	 * @return La liste des  map.
	 */
	public static List<Map<String, String>> getList() {
		return list
	}

	/**
	 * Récupère la valeur interne associée à un paramètre et une valeur donnés.
	 *
	 * @param para Le paramètre à rechercher.
	 * @param val La valeur à rechercher.
	 * @return La valeur interne correspondante, ou null si aucune valeur n'est trouvée.
	 */
	public static String getInternalValueOf(String para, String val) {
		Log.addTraceBEGIN(CLASS_FORLOG, "getInternalValueOf", [para: para, val: val])

		String res = list.find { it['param'] == para && it['value'] == val }?.get('internalValue')

		if (!res) {
			Log.addERROR('Pas de valeur trouvée')
		}

		Log.addTraceEND(CLASS_FORLOG, "getInternalValueOf", res)
		return res
	}
}
