package tnrJDDManager


import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

import groovy.transform.CompileStatic
import tnrLog.Log
import tnrCommon.Tools


@CompileStatic
public class JDDData {


	private final String CLASS_FOR_LOG = 'JDDDatas'

	// Liste des lignes de données du JDD/PREJDD
	private List<Map<String, Map<String, Object>>> datasList = []



	/**
	 * CONSTRUCTOR : Lit les données du fichier Excel et charge datasList.
	 * 
	 * @param sheet La feuille Excel à lire
	 * @param JDDHeader L'entête de la feuille Excel
	 * @param startDataWord Le mot de départ pour commencer à lire les données
	 */
	JDDData(Sheet sheet, JDDHeader JDDHeader,String startDataWord) {

		Log.addTraceBEGIN(CLASS_FOR_LOG, "JDDDatas", [sheet:sheet.getSheetName() , JDDHeader:JDDHeader, startDataWord:startDataWord])

		Iterator<Row> rowIt = sheet.rowIterator()
		
		skipToStartDataWord(rowIt, startDataWord)
		processData(rowIt, JDDHeader)

		Log.addTraceEND(CLASS_FOR_LOG, "JDDDatas")
	}


	/**
	 * Ignore les lignes jusqu'à trouver le mot de départ des lignes de données
	 * 
	 * @param rowIt Iterateur pour parcourir les lignes de la feuille Excel
	 * @param startDataWord Le mot de départ des lignes de données
	 */
	private void skipToStartDataWord(Iterator<Row> rowIt, String startDataWord) {
		// Skips rows until it finds the start data word or an empty string.
		while (rowIt.hasNext()) {
			Row row = rowIt.next()
			String cellValue = tnrCommon.ExcelUtils.getCellValue(row.getCell(0)).toString()
			if (cellValue == startDataWord || startDataWord == '') {
				Log.addTrace('- break')
				break
			}
		}
	}
	
	
	/**
	 * Traite les données de chaque ligne pour peupler datasList.
	 * 
	 * @param rowIt Iterateur pour parcourir les lignes de la feuille Excel
	 * @param JDDHeader L'entête de la feuille Excel
	 */
	private processData(Iterator<Row> rowIt, JDDHeader JDDHeader) {
		// Processes rows to populate datasList.
		while (rowIt.hasNext()) {
			Row row = rowIt.next()
			String cellValue = tnrCommon.ExcelUtils.getCellValue(row.getCell(0)).toString()
			if (cellValue == '') {
				Log.addTrace('- break')
				break
			}
			
			List<String> rowValues = tnrCommon.ExcelUtils.loadRow(row, JDDHeader.getSize() + 1)
			Log.addTrace("- rowValues: $rowValues")
			
			def fieldMap = [:]
			def rowMap = [:]
			
			JDDHeader.getList().eachWithIndex { header, index ->
				fieldMap[header] = rowValues[index + 1]
			}
			
			rowMap[cellValue] = fieldMap
			datasList.add(rowMap)
		}
	}


	/**
	 * Obtient la valeur brute d'un champ
	 * 
	 * @param name Le nom du champ à récupérer
	 * @param cdt Le cas de test (cdt) à utiliser
	 * @param cdtnum Le numéro d'occurrence du cdt
	 * @return La valeur brute du champ spécifié
	 */
	public getRawData(String name, String cdt, int cdtnum ) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getRawData", [name:name , cdt:cdt, cdtnum:cdtnum])
		// Filtrer les éléments qui ont la clé 'cdt'
		List<Map<String, Map<String, Object>>> filtered = datasList.findAll { it.containsKey(cdt) }
		def ret
		// Si l'occurrence demandée existe
		if (cdtnum >= 1 && cdtnum <= filtered.size()) {
			// Récupérer l'occurence cdtnum et retourne la valeur associée à 'name'
			ret = filtered[cdtnum - 1][cdt][name]
		}

		Log.addTraceEND(CLASS_FOR_LOG, "getRawData" , ret)
		return ret
	}
	
	
	/**
	 * Récupère tous les cas de test (cdts) contenant une sous-chaîne donnée, sans doublons.
	 * 
	 * @param substring La sous-chaîne à rechercher
	 * @return Liste des cdts contenant la sous-chaîne, sans doublons
	 */
	public List<String> getCdtsContainingSubstringWithoutDuplicates(String substring) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getCdtsContainingSubstringWithoutDuplicates", [substring:substring])
		// Rechercher les cdt qui contiennent la sous-chaîne
		List<String> ret = datasList.collect { it.keySet().find { k -> k.contains(substring) } }.findAll { it != null }.unique()
		Log.addTraceEND(CLASS_FOR_LOG, "getCdtsContainingSubstringWithoutDuplicates" , ret)
		return ret
	}
	

	/**
	 * Concatène les cdts et les valeurs de certains champs de chaque ligne de données.
	 * 
	 * @param JDDDatas La liste des données à traiter
	 * @param namesToConcat Les champs à concaténer
	 * @return Liste des chaînes concaténées
	 */
	public List<String> concatenateCdtsAndValues(List<Map<String, Map<String, Object>>> JDDDatas, List<String> namesToConcat) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "concatenateCdtsAndValues", ['JDDDatas.size()':JDDDatas.size() , namesToConcat:namesToConcat])
		boolean err = false
	    List<String> list = (List<String>) JDDDatas.collect { cdtLine ->
	        cdtLine.collect { cdt, dataLine ->
	            String concatCdt = cdt
	            namesToConcat.each { name ->
					if (dataLine[name]) {
						concatCdt += "-" + dataLine[name]
					}else {
						Log.addERROR("concatenateCdtsAndValues() : $name n'est pas une colonne du JDD")
						err=true
					}
	            }
	            return concatCdt
	        }
	    }.flatten()
		//renvoie une List vide si une erreur est arrivée
		list = err?[] as List<String>:list
		Log.addTraceEND(CLASS_FOR_LOG, "concatenateCdtsAndValues" , list)
		return list
	}


	/**
	 * Obtient le nombre de lignes pour un cas de test donné (cdt).
	 * 
	 * @param cdt Le cas de test à utiliser
	 * @return Le nombre de lignes pour le cdt spécifié
	 */
	public int getNbrLigneCasDeTest(String cdt) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "getNbrLigneCasDeTest", [cdt:cdt])
		// Compter les occurrences du cdt
		int ret = (int)datasList.count { it.containsKey(cdt) }
		Log.addTraceEND(CLASS_FOR_LOG, "getNbrLigneCasDeTest" , ret)
		return  ret

	}

	/**
	 * Récupère la liste de données.
	 * @return La liste de données
	 */
	public List getList() {
		return  datasList
	}


	
	/**
	 * Modifie la valeur d'un champ spécifique pour un cdt et une occurrence donnée.
	 * 
	 * @param name Le nom du champ à modifier
	 * @param value La nouvelle valeur
	 * @param cdt Le cas de test (cdt) à utiliser
	 * @param cdtnum Le numéro d'occurrence du cdt
	 */
	public void setValueOf(String name, def value, String cdt, int cdtnum) {
		Log.addTraceBEGIN(CLASS_FOR_LOG, "setValueOf", [name:name , value:value , cdt:cdt, cdtnum:cdtnum])
		// Filtrer les entrées qui correspondent au cdt spécifié
		List<Map<String, Map<String, Object>>> matchingCdtList = datasList.findAll { it.containsKey(cdt) }
		// Vérifier si l'occurrence spécifiée est valide
		if(cdtnum >= 1 && cdtnum <= matchingCdtList.size()) {
			// Mettre à jour la valeur de la clé 'name'
			matchingCdtList[cdtnum - 1][cdt][name] = value
		}
		Log.addTraceEND(CLASS_FOR_LOG, "setValueOf" )
	}


} //end of class
