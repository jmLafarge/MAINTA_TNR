package tnrJDDManager

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import tnrLog.Log
import tnrCommon.Tools


@CompileStatic
public class JDDParam {


	private final String CLASS_FOR_LOG = 'JDDParams'


	private final enum PARAM_LIST_ALLOWED {
		PREREQUIS,
		FOREIGNKEY,
		LOCATOR,
		SEQUENCE,
		INTERNALVALUE
	}





	private Map <String, Map<String ,String>> paramsMap = [:]


	JDDParam(Sheet sheet, JDDHeader JDDHeader,String startDataWord) {

		Log.addTraceBEGIN(CLASS_FOR_LOG, "JDDParams", [sheet:sheet.getSheetName() , JDDHeader:JDDHeader, startDataWord:startDataWord])

		Iterator<Row> rowIt = sheet.rowIterator()
		rowIt.next()
		while(rowIt.hasNext()) {
			Row row = rowIt.next()
			String param =  tnrCommon.ExcelUtils.getCellValue(row.getCell(0)).toString()
			if (param ==startDataWord) {
				break
			}
			if (isParamAllowed(param)) {
				List paramLine = tnrCommon.ExcelUtils.loadRow(row,JDDHeader.getSize()+1)
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

		Log.addTraceEND(CLASS_FOR_LOG, "JDDParams")
	}





	private boolean isParamAllowed(String name) {

		return PARAM_LIST_ALLOWED.values().find { it.toString() == name }
	}


	public List<String> getParamListAllowed() {
		return PARAM_LIST_ALLOWED.values().collect { it.toString() }
	}



	public Map<String ,String> getAllLOCATOR() {
		return paramsMap[PARAM_LIST_ALLOWED.LOCATOR.name()]
	}


	public Map<String ,String> getAllPREREQUIS() {
		Map <String ,String> map =[:]
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.PREREQUIS.name())) {
			map = paramsMap[PARAM_LIST_ALLOWED.PREREQUIS.name()]
		}
		return map
	}




	public String getParamFor(String param,String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getParamFor", [param:param , name:name])
		String ret = ''
		if (isParamAllowed(param)){
			if (paramsMap.containsKey(param)) {
				ret = paramsMap[param][name]
			}else {
				ret = ''
			}
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getParamFor" , ret)
		return ret
	}




	public String getPREREQUISFor(String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getPREREQUISFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.PREREQUIS.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.PREREQUIS.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getPREREQUISFor" , ret)
		return ret
	}

	public String getFOREIGNKEYFor(String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getFOREIGNKEYFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.FOREIGNKEY.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.FOREIGNKEY.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getFOREIGNKEYFor" , ret)
		return ret
	}

	public String getSEQUENCEFor(String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getSEQUENCEFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.SEQUENCE.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.SEQUENCE.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getSEQUENCEFor" , ret)
		return ret
	}



	public String getLOCATORFor(String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getLOCATORFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.LOCATOR.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.LOCATOR.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getLOCATORFor" , ret)
		return ret
	}



	public String getINTERNALVALUEFor(String name) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getINTERNALVALUEFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.INTERNALVALUE.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.INTERNALVALUE.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getINTERNALVALUEFor" , ret)
		return ret
	}



	public boolean isRADIO(String name) {
		return paramsMap[PARAM_LIST_ALLOWED.LOCATOR.name()][name] == 'radio'
	}
	
	
	
	public int getLOCATORIndex() {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getLOCATORIndex", [:])
		int ret = -1
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.LOCATOR.name())) {
			ret = paramsMap.keySet().toList().findIndexOf { it == PARAM_LIST_ALLOWED.LOCATOR.name() }
		}else {
			ret = -1
		}
		Log.addTraceEND(CLASS_FOR_LOG, "getLOCATORIndex" , ret)
		return ret
	}

	
	public int getSize() {
		return paramsMap.size()
	}
	
	
} //Fin de class
