package myJDDManager

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import my.Log
import my.Tools


@CompileStatic
public class JDDParams {


	private final String CLASS_FORLOG = 'JDDParams'

	private final enum PARAM_LIST_ALLOWED {
		PREREQUIS,
		FOREIGNKEY,
		LOCATOR,
		SEQUENCE,
		INTERNALVALUE
	}


	private Map <String, Map<String ,String>> paramsMap = [:]


	JDDParams(Sheet sheet, JDDHeaders JDDHeader,String START_DATA_WORD) {

		Log.addTraceBEGIN(CLASS_FORLOG, "JDDParams", [sheet:sheet.getSheetName() , JDDHeader:JDDHeader, START_DATA_WORD:START_DATA_WORD])

		Iterator<Row> rowIt = sheet.rowIterator()
		rowIt.next()
		while(rowIt.hasNext()) {
			Row row = rowIt.next()
			String param =  my.XLS.getCellValue(row.getCell(0)).toString()
			if (param ==START_DATA_WORD) {
				break
			}
			if (isParamAllowed(param)) {
				List paramLine = my.XLS.loadRow(row,JDDHeader.getHeaderSize()+1)
				def innerMap = [:]
				JDDHeader.headersList.eachWithIndex { header, index ->
					innerMap[header] = paramLine[index + 1]
				}
				paramsMap[param] = innerMap
			}else {
				Log.addERROR("Le paramètre '$param' n'est pas autorisé")
			}
		}
		Log.addTrace('- params : ' + paramsMap)
		
		Log.addTraceEND(CLASS_FORLOG, "JDDParams")
	}





	private boolean isParamAllowed(String name) {

		return PARAM_LIST_ALLOWED.values().find { it.toString() == name }
	}


	/*
	 def Map <String,String> getParamMap(PARAM_LIST_ALLOWED param) {
	 return params[param.name()]
	 }
	 */



	def String getPREREQUISFor(String name) {
		return paramsMap[PARAM_LIST_ALLOWED.PREREQUIS.name()][name]
	}

	def String getFOREIGNKEYFor(String name) {
		return paramsMap[PARAM_LIST_ALLOWED.FOREIGNKEY.name()][name]
	}

	def String getSEQUENCEFor(String name) {
		return paramsMap[PARAM_LIST_ALLOWED.SEQUENCE.name()][name]
	}

	def String getLOCATORFor(String name) {
		return paramsMap[PARAM_LIST_ALLOWED.LOCATOR.name()][name]
	}

	def String getINTERNALVALUEFor(String name) {
		return paramsMap[PARAM_LIST_ALLOWED.INTERNALVALUE.name()][name]
	}
} //end of class
