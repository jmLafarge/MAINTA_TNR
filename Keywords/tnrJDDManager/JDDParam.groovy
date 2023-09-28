package tnrJDDManager

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import tnrCommon.ExcelUtils
import tnrLog.Log


@CompileStatic
public class JDDParam {


	private final String CLASS_NAME  = 'JDDParams'


	private final enum PARAM_LIST_ALLOWED {
		PREREQUIS,
		FOREIGNKEY,
		LOCATOR,
		SEQUENCE,
		INTERNALVALUE
	}





	private Map <String, Map<String ,String>> paramsMap = [:]


	JDDParam(Sheet sheet, JDDHeader JDDHeader) {

		Log.addTraceBEGIN(CLASS_NAME, "JDDParams", [sheet:sheet.getSheetName() , JDDHeader:JDDHeader])

		Iterator<Row> rowIt = sheet.rowIterator()
		rowIt.next()
		while(rowIt.hasNext()) {
			Row row = rowIt.next()
			String param =  ExcelUtils.getCellValue(row.getCell(0)).toString()
			if (param ==JDDHeader.START_DATA_WORD) {
				break
			}
			if (isParamAllowed(param)) {
				List paramLine = ExcelUtils.loadRow(row,JDDHeader.getSize()+1)
				Log.addTrace("paramLine : $paramLine")
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

		Log.addTraceEND(CLASS_NAME, "JDDParams")
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
		Log.addTraceBEGIN(CLASS_NAME, "getParamFor", [param:param , name:name])
		String ret = ''
		if (isParamAllowed(param)){
			if (paramsMap.containsKey(param)) {
				ret = paramsMap[param][name]
			}else {
				ret = ''
			}
		}
		Log.addTraceEND(CLASS_NAME, "getParamFor" , ret)
		return ret
	}




	public String getPREREQUISFor(String name) {
		Log.addTraceBEGIN(CLASS_NAME, "getPREREQUISFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.PREREQUIS.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.PREREQUIS.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_NAME, "getPREREQUISFor" , ret)
		return ret
	}

	public String getFOREIGNKEYFor(String name) {
		Log.addTraceBEGIN(CLASS_NAME, "getFOREIGNKEYFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.FOREIGNKEY.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.FOREIGNKEY.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_NAME, "getFOREIGNKEYFor" , ret)
		return ret
	}

	public String getSEQUENCEFor(String name) {
		Log.addTraceBEGIN(CLASS_NAME, "getSEQUENCEFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.SEQUENCE.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.SEQUENCE.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_NAME, "getSEQUENCEFor" , ret)
		return ret
	}



	public String getLOCATORFor(String name) {
		Log.addTraceBEGIN(CLASS_NAME, "getLOCATORFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.LOCATOR.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.LOCATOR.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_NAME, "getLOCATORFor" , ret)
		return ret
	}



	public String getINTERNALVALUEFor(String name) {
		Log.addTraceBEGIN(CLASS_NAME, "getINTERNALVALUEFor", [name:name])
		String ret = ''
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.INTERNALVALUE.name())) {
			ret=  paramsMap[PARAM_LIST_ALLOWED.INTERNALVALUE.name()][name]
		}else {
			ret = ''
		}
		Log.addTraceEND(CLASS_NAME, "getINTERNALVALUEFor" , ret)
		return ret
	}



	public boolean isRADIO(String name) {
		return paramsMap[PARAM_LIST_ALLOWED.LOCATOR.name()][name] == 'radio'
	}



	public int getLOCATORIndex() {
		Log.addTraceBEGIN(CLASS_NAME, "getLOCATORIndex", [:])
		int ret = -1
		if (paramsMap.containsKey(PARAM_LIST_ALLOWED.LOCATOR.name())) {
			ret = paramsMap.keySet().toList().findIndexOf { it == PARAM_LIST_ALLOWED.LOCATOR.name() }
		}else {
			ret = -1
		}
		Log.addTraceEND(CLASS_NAME, "getLOCATORIndex" , ret)
		return ret
	}


	public int getSize() {
		return paramsMap.size()
	}



	/**
	 * Vérifie si une valeur est une clé étrangère.
	 *
	 * @param name Le nom de la valeur à vérifier.
	 * @return True si la valeur est une clé étrangère, false sinon.
	 */
	public boolean isFK(String name) {
		Log.addTraceBEGIN(CLASS_NAME,"isFK",[name:name])
		boolean result = getFOREIGNKEYFor(name)!=''
		Log.addTraceEND(CLASS_NAME,"isFK",result)
		return result
	}


	/**
	 * Vérifie si une valeur est obsolète.
	 *
	 * @param name Le nom de la valeur à vérifier.
	 * @return True si la valeur est obsolète, false sinon.
	 */
	public boolean isOBSOLETE(String name) {
		Log.addTraceBEGIN(CLASS_NAME,"isOBSOLETE",[name:name])
		String ret = getPREREQUISFor(name)
		boolean result = ret ? ret == 'OBSOLETE' : false
		Log.addTraceEND(CLASS_NAME,"isOBSOLETE",result)
		return result
	}






	public boolean isCALCULEE(String name) {
		Log.addTraceBEGIN(CLASS_NAME,"isCALCULEE",[name:name])
		String ret = getPREREQUISFor(name)
		boolean result = ret ? ret == 'CALCULEE' : false
		Log.addTraceEND(CLASS_NAME,"isCALCULEE",result)
		return result
	}
} //Fin de class
