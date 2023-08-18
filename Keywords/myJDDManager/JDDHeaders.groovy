package myJDDManager

import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import my.Log


@CompileStatic
public class JDDHeaders {



	private final String CLASS_FORLOG = 'JDDHeaders'

	private List <String> headersList = []

	private String tableName = ''



	JDDHeaders (Sheet sheet) {
		Log.addTraceBEGIN(CLASS_FORLOG, "JDDHeaders", [sheet:sheet.getSheetName()])

		List line0 = my.XLS.loadRow(sheet.getRow(0))
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
} //end of class
