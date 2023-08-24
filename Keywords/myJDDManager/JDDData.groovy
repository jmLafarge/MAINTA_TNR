package myJDDManager


import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import my.Log
import my.Tools


@CompileStatic
public class JDDData {


	private final String CLASS_FORLOG = 'JDDDatas'

	private List<Map<String, Map<String, Object>>> datasList = []




	JDDData(Sheet sheet, JDDHeader JDDHeader,String START_DATA_WORD) {

		Log.addTraceBEGIN(CLASS_FORLOG, "JDDDatas", [sheet:sheet.getSheetName() , JDDHeader:JDDHeader, START_DATA_WORD:START_DATA_WORD])

		Iterator<Row> rowIt = sheet.rowIterator()

		// boucle jusqu'au début des DATAS
		while(rowIt.hasNext()) {
			Row row = rowIt.next()
			String cdt =  my.XLS.getCellValue(row.getCell(0)).toString()
			if (cdt ==START_DATA_WORD || START_DATA_WORD=='') {
				Log.addTrace('- break')
				break
			}
		}
		//Début des DATAS
		while(rowIt.hasNext()) {
			Row row = rowIt.next()
			String cdt =  my.XLS.getCellValue(row.getCell(0)).toString()
			if (cdt == '') {
				Log.addTrace('- break')
				break
			}
			Log.addTrace("- cas de test : $cdt")

			List cdtLine = my.XLS.loadRow(row,JDDHeader.getSize()+1)
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




	def getRawData(String name, String cdt, int cdtnum ) {
		// Filtrer les éléments qui ont la clé 'cdt'

		List<Map<String, Map<String, Object>>> filtered = datasList.findAll { it.containsKey(cdt) }

		// Si l'occurrence demandée est hors limites, retourner null
		if (cdtnum <= 0 || cdtnum > filtered.size()) {
			return null
		}

		// Récupérer l'occurence-ième entrée et retourner la valeur associée à 'name'
		return filtered[cdtnum - 1][cdt][name]
	}





	def List<String> getCdtsContainingSubstringWithoutDuplicates(String substring) {
		// Rechercher les cdt qui contiennent la sous-chaîne
		List<String> matchingCdts = datasList.collect { it.keySet().find { k -> k.contains(substring) } }.findAll { it != null }

		return matchingCdts.unique()
	}



	def int getNbrLigneCasDeTest(String cdt) {
		// Compter les occurrences du cdt
		return  (int)datasList.count { it.containsKey(cdt) }

	}


	def List getList() {
		return  datasList
	}


	void setValueOf(String name, def value, String cdt, int cdtnum) {
		// Filtrer les entrées qui correspondent au cdt spécifié
		List<Map<String, Map<String, Object>>> matchingCdtEntries = datasList.findAll { it.containsKey(cdt) }

		// Vérifier si l'occurrence spécifiée est valide
		if(cdtnum <= 0 || cdtnum > matchingCdtEntries.size()) {
			println "Occurrence spécifiée non valide."
			return
		}

		// Récupérer l'entrée correspondante à l'occurrence spécifiée
		Map<String, Map<String, Object>> entry = matchingCdtEntries[cdtnum - 1]

		// Mettre à jour la valeur de la clé 'name'
		entry[cdt][name] = value
	}





}
