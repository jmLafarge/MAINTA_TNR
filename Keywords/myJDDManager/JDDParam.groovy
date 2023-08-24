package myJDDManager

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import my.Log
import my.Tools


@CompileStatic
public class JDDParam {


	private final String CLASS_FORLOG = 'JDDParams'


	private final enum PARAM_LIST_ALLOWED {
		PREREQUIS,
		FOREIGNKEY,
		LOCATOR,
		SEQUENCE,
		INTERNALVALUE
	}





	private Map <String, Map<String ,String>> paramsMap = [:]


	JDDParam(Sheet sheet, JDDHeader JDDHeader,String START_DATA_WORD) {

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
				List paramLine = my.XLS.loadRow(row,JDDHeader.getSize()+1)
				println "paramLine : $paramLine"
				def innerMap = [:]
				JDDHeader.list.eachWithIndex { header, index ->
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


	def List<String> getParamListAllowed() {
		return PARAM_LIST_ALLOWED.values().collect { it.toString() }
	}



	def Map<String ,String> getAllLOCATOR() {
		return paramsMap[PARAM_LIST_ALLOWED.LOCATOR.name()]
	}


	def Map<String ,String> getAllPREREQUIS() {
		return paramsMap[PARAM_LIST_ALLOWED.PREREQUIS.name()]
	}




	def String getParamFor(String param,String name) {
		Log.addTraceBEGIN(CLASS_FORLOG, "getParamFor", [param:param , name:name])
		String ret = ''
		if (isParamAllowed(param)){
			if (paramsMap.containsKey(param)) {
				ret = paramsMap[param][name]
			}else {
				ret = ''
			}
		}
		Log.addTraceEND(CLASS_FORLOG, "getParamFor" , ret)
		return ret
	}




	def String getPREREQUISFor(String name) {
		Log.addTraceBEGIN(CLASS_FORLOG, "getPREREQUISFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.PREREQUIS.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.PREREQUIS.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_FORLOG, "getPREREQUISFor" , ret)
		return ret
	}

	def String getFOREIGNKEYFor(String name) {
		Log.addTraceBEGIN(CLASS_FORLOG, "getFOREIGNKEYFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.FOREIGNKEY.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.FOREIGNKEY.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_FORLOG, "getFOREIGNKEYFor" , ret)
		return ret
	}

	def String getSEQUENCEFor(String name) {
		Log.addTraceBEGIN(CLASS_FORLOG, "getSEQUENCEFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.SEQUENCE.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.SEQUENCE.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_FORLOG, "getSEQUENCEFor" , ret)
		return ret
	}



	def String getLOCATORFor(String name) {
		Log.addTraceBEGIN(CLASS_FORLOG, "getLOCATORFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.LOCATOR.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.LOCATOR.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_FORLOG, "getLOCATORFor" , ret)
		return ret
	}



	def String getINTERNALVALUEFor(String name) {
		Log.addTraceBEGIN(CLASS_FORLOG, "getINTERNALVALUEFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.INTERNALVALUE.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.INTERNALVALUE.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_FORLOG, "getINTERNALVALUEFor" , ret)
		return ret
	}



	def boolean isRADIO(String name) {
		return paramsMap[PARAM_LIST_ALLOWED.LOCATOR.name()][name] == 'radio'
	}
} //end of class
