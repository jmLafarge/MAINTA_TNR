package tnrJDDManager


import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import tnrLog.Log
import tnrCommon.Tools


/**
 * Gere les INTERNALVALUE (certains select qui ne sont pas associé à une table).
 * Chaque élément dans la liste excel est représenté par une map  'param', 'value', 'internalValue'.
 */
@CompileStatic
public class JDDIV {

	private static final String CLASS_FOR_LOG = 'IV'

	private static List<Map<String, String>> list = []



	public static addAll(Sheet sheet) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "addAll", [sheet:sheet.getSheetName()])
		Iterator<Row> rowIV = sheet.rowIterator()
		rowIV.next()
		while(rowIV.hasNext()) {
			Row row = rowIV.next()
			String IV_para = tnrCommon.ExcelUtils.getCellValue(row.getCell(0))
			String IV_val = tnrCommon.ExcelUtils.getCellValue(row.getCell(1))
			String IV_code = tnrCommon.ExcelUtils.getCellValue(row.getCell(2))
			Log.addTrace("IV_para : $IV_para IV_val : $IV_val IV_code : $IV_code ")
			if (IV_para == '') {
				break
			}else {
				Map<String, String> newItem = [param: IV_para, value: IV_val, internalValue: IV_code]
				list.add(newItem)
			}
		}
		Log.addTrace("internalValues = " + list.toString())
		println Tools.displayWithQuotes(list)
		Log.addTraceEND(CLASS_FOR_LOG, "addAll")
	}





	/**
	 * Récupère la valeur interne associée à un paramètre et une valeur donnés.
	 *
	 * @param para Le paramètre à rechercher.
	 * @param val La valeur à rechercher.
	 * @return La valeur interne correspondante, ou null si aucune valeur n'est trouvée.
	 */
	public static String getInternalValueOf(String para, String val) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getInternalValueOf", [para: para, val: val])

		String res = list.find { it['param'] == para && it['value'] == val }?.get('internalValue')

		if (!res) {
			Log.addERROR('Pas de valeur trouvée')
		}

		Log.addTraceEND(CLASS_FOR_LOG, "getInternalValueOf", res)
		return res
	}
}
