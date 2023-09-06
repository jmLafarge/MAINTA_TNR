package tnrJDDManager

import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import tnrCommon.ExcelUtils
import tnrCommon.TNRPropertiesReader
import tnrLog.Log


@CompileStatic
public class JDDHeader {



	private final String CLASS_FOR_LOG 		= 'JDDHeaders'
	
	public final String START_DATA_WORD 	= TNRPropertiesReader.getMyProperty('START_DATA_WORD')

	private List <String> headersList = []

	private String tableName = ''



	JDDHeader(Sheet sheet) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "JDDHeaders", [sheet:sheet.getSheetName()])

		List line0 = ExcelUtils.loadRow(sheet.getRow(0))
		tableName = line0[0]
		Log.addTrace('tableName : ' +tableName)
		headersList = line0.subList(1, line0.size())
		Log.addTrace('headers : ' +headersList)

		Log.addTraceEND(CLASS_FOR_LOG, "JDDHeaders")
	}


	public int getSize() {
		return headersList.size()
	}

	public List <String> getList() {
		return headersList
	}

	public String getTableName() {
		return tableName
	}


	public String add(String name) {
		return headersList.add(name)
	}
	
	
} //Fin de class
