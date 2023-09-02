package tnrJDDManager


import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import tnrCommon.TNRPropertiesReader
import tnrCommon.Tools
import tnrLog.Log


/**
 * Gere les INTERNALVALUE (certains select qui ne sont pas associé à une table).
 * Chaque élément dans la liste excel est représenté par une map  'param', 'value', 'internalValue'.
 */
@CompileStatic
public class JDDIV {

	private final String CLASS_FOR_LOG = 'JDDIV'

	private final String INTERNALVALUE_SHEET_NAME	= TNRPropertiesReader.getMyProperty('INTERNALVALUE_SHEET_NAME')
	private final String JDDGLOBAL_FULLNAME 		= TNRPropertiesReader.getMyProperty('JDD_PATH') + File.separator + TNRPropertiesReader.getMyProperty('JDDGLOBAL_FILENAME')

	private List<Map<String, String>> list = []



	JDDIV(Sheet sheet){
		Log.addTraceBEGIN(CLASS_FOR_LOG, "JDDIV", [sheet:sheet.getSheetName()])
		Iterator<Row> rowIV = sheet.rowIterator()
		rowIV.next()
		while(rowIV.hasNext()) {
			Row row = rowIV.next()
			String IV_para = tnrCommon.ExcelUtils.getCellValue(row.getCell(0))
			String IV_intVal = tnrCommon.ExcelUtils.getCellValue(row.getCell(1))
			String IV_val = tnrCommon.ExcelUtils.getCellValue(row.getCell(2))
			Log.addTrace("IV_para : $IV_para  IV_code : $IV_intVal  IV_val : $IV_val")
			if (IV_para == '') {
				break
			}else {
				Map<String, String> newItem = [param: IV_para, internalValue: IV_intVal, value: IV_val]
				list.add(newItem)
			}
		}
		Log.addTrace("internalValues = " + list.toString())
		Log.addTraceEND(CLASS_FOR_LOG, "JDDIV")
	}







	/**
	 * Récupère la valeur interne associée à un paramètre et une valeur donnés. EX getInternalValueOf
	 *
	 * @param para Le paramètre à rechercher.
	 * @param val La valeur à rechercher.
	 * @return La valeur interne correspondante, ou null si aucune valeur n'est trouvée.
	 */
	public String getValueOf(String para, String intVal) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getValueOf", [para: para, intVal: intVal])
		Log.addTrace("internalValues = " + list.toString())
		String value = list.find { it['param'] == para && it['internalValue'] == intVal }?.get('value')
		if (!value) {
			Log.addERROR("Pas de valeur trouvée pour l'INTERNALVALUE '$intVal'")
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getValueOf", value)
		return value
	}
}
