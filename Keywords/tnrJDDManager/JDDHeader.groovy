package tnrJDDManager

import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import tnrLog.Log


@CompileStatic
public class JDDHeader {



	private final String CLASS_FORLOG = 'JDDHeaders'

	private List <String> headersList = []

	private String tableName = ''



	JDDHeader(Sheet sheet) {
		Log.addTraceBEGIN(CLASS_FORLOG, "JDDHeaders", [sheet:sheet.getSheetName()])

		List line0 = tnrCommon.ExcelUtils.loadRow(sheet.getRow(0))
		tableName = line0[0]
		Log.addTrace('tableName : ' +tableName)
		headersList = line0.subList(1, line0.size())
		Log.addTrace('headers : ' +headersList)

		Log.addTraceEND(CLASS_FORLOG, "JDDHeaders")
	}


	def int getSize() {
		return headersList.size()
	}

	def List <String> getList() {
		return headersList
	}

	def String getTableName() {
		return tableName
	}
	
} //end of class
