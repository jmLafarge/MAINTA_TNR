package tnrJDDManager


import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import tnrLog.Log
import tnrCommon.Tools


@CompileStatic
public class JDDData {


	private final String CLASS_FORLOG = 'JDDDatas'

	private List<Map<String, Map<String, Object>>> datasList = []




	JDDData(Sheet sheet, JDDHeader JDDHeader,String startDataWord) {

		Log.addTraceBEGIN(CLASS_FORLOG, "JDDDatas", [sheet:sheet.getSheetName() , JDDHeader:JDDHeader, startDataWord:startDataWord])

		Iterator<Row> rowIt = sheet.rowIterator()

		// boucle jusqu'au début des DATAS
		while(rowIt.hasNext()) {
			Row row = rowIt.next()
			String cdt =  tnrCommon.ExcelUtils.getCellValue(row.getCell(0)).toString()
			if (cdt ==startDataWord || startDataWord=='') {
				Log.addTrace('- break')
				break
			}
		}
		//Début des DATAS
		while(rowIt.hasNext()) {
			Row row = rowIt.next()
			String cdt =  tnrCommon.ExcelUtils.getCellValue(row.getCell(0)).toString()
			if (cdt == '') {
				Log.addTrace('- break')
				break
			}
			Log.addTrace("- cas de test : $cdt")

			List cdtLine = tnrCommon.ExcelUtils.loadRow(row,JDDHeader.getSize()+1)
			Log.addTrace("- cdtLine : $cdtLine")

			def fieldMap = [:]
			def cdtMap=[:]

			JDDHeader.getList().eachWithIndex { header, index ->
				fieldMap[header] = cdtLine[index + 1]
			}
			cdtMap[cdt] = fieldMap

			datasList.add(cdtMap)


		}
		Log.addTrace('- datas : ' + datasList)
		println Tools.displayWithQuotes(datasList)
		Log.addTraceEND(CLASS_FORLOG, "JDDDatas")
	}




	public getRawData(String name, String cdt, int cdtnum ) {
		Log.addTraceBEGIN(CLASS_FORLOG, "getRawData", [name:name , cdt:cdt, cdtnum:cdtnum])
		// Filtrer les éléments qui ont la clé 'cdt'
		List<Map<String, Map<String, Object>>> filtered = datasList.findAll { it.containsKey(cdt) }
		def ret
		// Si l'occurrence demandée existe
		if (cdtnum >= 1 && cdtnum <= filtered.size()) {
			// Récupérer l'occurence cdtnum et retourne la valeur associée à 'name'
			ret = filtered[cdtnum - 1][cdt][name]
		}

		Log.addTraceEND(CLASS_FORLOG, "getRawData" , ret)
		return ret
	}




	public List<String> getCdtsContainingSubstringWithoutDuplicates(String substring) {
		Log.addTraceBEGIN(CLASS_FORLOG, "getCdtsContainingSubstringWithoutDuplicates", [substring:substring])
		// Rechercher les cdt qui contiennent la sous-chaîne
		List<String> ret = datasList.collect { it.keySet().find { k -> k.contains(substring) } }.findAll { it != null }.unique()
		Log.addTraceEND(CLASS_FORLOG, "getCdtsContainingSubstringWithoutDuplicates" , ret)
		return ret
	}



	public int getNbrLigneCasDeTest(String cdt) {
		Log.addTraceBEGIN(CLASS_FORLOG, "getNbrLigneCasDeTest", [cdt:cdt])
		// Compter les occurrences du cdt
		int ret = (int)datasList.count { it.containsKey(cdt) }
		Log.addTraceEND(CLASS_FORLOG, "getNbrLigneCasDeTest" , ret)
		return  ret

	}


	public List getList() {
		return  datasList
	}


	public void setValueOf(String name, def value, String cdt, int cdtnum) {
		Log.addTraceBEGIN(CLASS_FORLOG, "setValueOf", [name:name , value:value , cdt:cdt, cdtnum:cdtnum])
		// Filtrer les entrées qui correspondent au cdt spécifié
		List<Map<String, Map<String, Object>>> matchingCdtList = datasList.findAll { it.containsKey(cdt) }
		// Vérifier si l'occurrence spécifiée est valide
		if(cdtnum >= 1 && cdtnum <= matchingCdtList.size()) {
			// Mettre à jour la valeur de la clé 'name'
			matchingCdtList[cdtnum - 1][cdt][name] = value
		}
		Log.addTraceEND(CLASS_FORLOG, "setValueOf" )
	}





} //end of class
