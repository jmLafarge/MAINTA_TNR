package tnrJDDManager


import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import tnrLog.Log
import tnrCommon.Tools

/**
 * Manage IHMTO Tab of JDD files
 * 
 *
 * @author JM Lafarge
 * @version 1.0
 */


@CompileStatic
public class JDDIHMTO {

	private final String CLASS_FOR_LOG = 'JDDIHMTO'


	private Map <String,String> xpathMap  = [:]


	JDDIHMTO(Sheet IHMTOSheet, String TCSheetName) {

		Log.addTraceBEGIN(CLASS_FOR_LOG, "JDDIHMTO", [IHMTOSheet:IHMTOSheet.getSheetName() , TCSheetName:TCSheetName])

		Iterator<Row> rowIt = IHMTOSheet.rowIterator()
		rowIt.next()
		while(rowIt.hasNext()) {
			Row row = rowIt.next()
			String tab = tnrCommon.ExcelUtils.getCellValue(row.getCell(0))
			String name = tnrCommon.ExcelUtils.getCellValue(row.getCell(1))
			String xpath = tnrCommon.ExcelUtils.getCellValue(row.getCell(2))
			Log.addTrace("tab : $tab name : $name xpath : $xpath")
			if (tab == '') {
				break
			}else if (tab in [TCSheetName, 'ALL']) {
				if (xpathMap.containsKey(name)) {
					Log.addERROR("Le xpath pour '$name' existe déjà !")
				}else {
					Log.addTrace("add $name = '$xpath'")
					xpathMap[name] = xpath
				}
			}
		}

		Log.addTrace('- xpathMap : ' + xpathMap)
		Log.addTraceEND(CLASS_FOR_LOG, "JDDIHMTO")
	}



	public Map <String,String> getXpaths() {
		return xpathMap
	}
}// Fin de class