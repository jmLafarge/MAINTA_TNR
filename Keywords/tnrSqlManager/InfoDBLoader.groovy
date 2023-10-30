package tnrSqlManager

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import tnrCommon.ExcelUtils
import tnrCommon.TNRPropertiesReader
import tnrLog.Log


/**
 * Charge les informations relatives à la base de données à partir d'un fichier Excel.
 * 
 * @author JM Lafarge
 * @version 1.0
 * 
 * 
 **/
@CompileStatic
public class InfoDBLoader {

	private final String CLASS_NAME  = 'InfoDBLoader'


	/*
	 * Structure des données pour stocker les informations
	 * 
	 * [TABLE_NAME : nom de la table ]
	 * 		[COLUMN_NAME : nom de la colonne]
	 * 			[ORDINAL_POSITION 	: de 1 à n correspond à l'ordre de la colonne dans la table]
	 * 			[IS_NULLABLE 		: YES ou NO --> on ne l'utilise pas pour l'instant]
	 * 			[DATA_TYPE 			: le type de la donnée (varchar, numéric,...)]
	 * 			[MAXCHAR 			: le nombre de caractere pour un varchar]
	 * 			[DOMAIN_NAME		: le nom du domaine de données]
	 * 			[CONSTRAINT_NAME	: indique si c'est une clé primaire PK_leNomDuChamp]
	 *
	 *
	 */
	private Map  <String, Map<String, Map <String , Object>>> datas = [:]

	//Liste des en-têtes attendus dans le fichier Excel
	private final List <String> HEADERS	 = [
		'TABLE_NAME',
		'COLUMN_NAME',
		'ORDINAL_POSITION',
		'IS_NULLABLE',
		'DATA_TYPE',
		'MAXCHAR',
		'DOMAIN_NAME',
		'CONSTRAINT_NAME'
	]


	private String infoDBFilename = ''


	/**
	 * Constructeur : charge les données à partir du fichier Excel
	 */
	InfoDBLoader (){
		Log.addTraceBEGIN(CLASS_NAME,"InfoDBLoader",[:])

		infoDBFilename = getInfoDBFilename()

		XSSFWorkbook book = ExcelUtils.open(infoDBFilename)

		// feuille Excel qui contient les données (issues d'une extraction manuelle)
		Sheet sheet = book.getSheet('INFO')

		Iterator<Row> rowIt = sheet.rowIterator()

		List<String> headers = validateHeaders(sheet)

		populateDatas(sheet, headers)

		Log.addTraceEND(CLASS_NAME,"InfoDBLoader")
	}


	/**
	 * Renvoie le Map datas
	 * 
	 * @return le Map 'datas'
	 */
	public Map  <String, Map<String, Map <String , Object>>> getDatas() {
		return datas
	}






	/**
	 * Obtient le chemin complet du fichier Excel.
	 *
	 * @return Chemin complet du fichier Excel.
	 */
	private String getInfoDBFilename() {
		String path = TNRPropertiesReader.getMyProperty('TNR_PATH');
		String filename = TNRPropertiesReader.getMyProperty('INFO_DB_FILENAME');
		return path + File.separator + filename;
	}


	/**
	 * Valide les en-têtes du fichier Excel.
	 *
	 * @param sheet Feuille Excel des données.
	 * @return Liste des en-têtes.
	 */
	private List<String> validateHeaders(Sheet sheet) {
		Row row = sheet.rowIterator().next()
		List<String> headers = ExcelUtils.loadRow(row)
		if (headers != HEADERS) {
			Log.addERROR("'$infoDBFilename' : Entête fichier différente de celle attendue :")
			Log.addDETAIL('Entête attendue : ' + HEADERS.join(' - '))
			Log.addDETAIL('Entête lue      : ' + headers.join(' - '))
			Log.addErrorAndStop('')
		}
		return headers
	}


	/**
	 * Charge les données dans la structure 'datas'.
	 * 
	 * parcourt toutes les lignes de la feuille Excel 
	 * et remplit le Map 'datas' avec les détails de chaque table et de chaque colonne.
	 *
	 * @param sheet Feuille Excel contenant les données.
	 * @param headers Liste des en-têtes de la feuille.
	 */
	private void populateDatas(Sheet sheet, List<String> headers) {
		Iterator<Row> rowIt = sheet.rowIterator()
		rowIt.next() // on saute la 1ere ligne correspondant aux entetes
		while(rowIt.hasNext()) {
			Row row = rowIt.next()
			String tableName = row.getCell(headers.indexOf("TABLE_NAME")).getStringCellValue()

			// fin des datas, on arrete
			if (!tableName) {
				break
			}

			String columnName = row.getCell(headers.indexOf("COLUMN_NAME")).getStringCellValue()

			List lineXLS = ExcelUtils.loadRow(row,HEADERS.size())

			// Créer un Map avec les détails de la colonne
			Map<String,Object> columnDetails = [
				headers.subList(2, 8),
				lineXLS.subList(2, 8)
			].transpose().collectEntries()

			if (!datas[tableName]) {
				datas[tableName] = [:]
			}
			datas[tableName][columnName] = columnDetails
		}
	}


}// Fin de class
