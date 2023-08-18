package myJDDManager


import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import my.Log
import my.Tools

/**
 * Manage IHMTO Tab of JDD files
 * 
 *
 * @author JM Lafarge
 * @since 1.0
 */


@CompileStatic
public class JDDIHMTOs {

	private final String CLASS_FORLOG = 'JDDIHMTO'


	private Map <String,String> xpathMap  = [:]


	JDDIHMTOs(Sheet IHMTOSheet, String TCSheetName) {

		Log.addTraceBEGIN(CLASS_FORLOG, "JDDIHMTO", [IHMTOSheet:IHMTOSheet.getSheetName()])

		Iterator<Row> rowIt = IHMTOSheet.rowIterator()
		rowIt.next()
		while(rowIt.hasNext()) {
			Row row = rowIt.next()
			String tab = my.XLS.getCellValue(row.getCell(0))
			String name = my.XLS.getCellValue(row.getCell(1))
			String xpath = my.XLS.getCellValue(row.getCell(2))
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
		println Tools.displayWithQuotes(xpathMap)
		Log.addTraceEND(CLASS_FORLOG, "JDDIHMTO")
	}



	def Map <String,String> getXpaths() {
		return xpathMap
	}
}// end of Class