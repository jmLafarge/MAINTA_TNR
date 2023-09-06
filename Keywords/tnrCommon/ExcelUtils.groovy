package tnrCommon

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CreationHelper
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Hyperlink
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import tnrLog.Log

import groovy.transform.CompileStatic


/**
 * @author JM Lafarge
 *
 */
@CompileStatic
public class ExcelUtils {

	private static final String CLASS_FOR_LOG = 'ExcelUtils'



	/**
	 * Ouvre un classeur Excel à partir d'un fichier.
	 *
	 * @param fullname Le nom complet du fichier Excel à ouvrir.
	 * @return Le classeur Excel (XSSFWorkbook) ou null en cas d'erreur.
	 */
	static XSSFWorkbook open(String fullname) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"open",[fullname:fullname])
		XSSFWorkbook ret
		try {
			File sourceExcel = new File(fullname)
			FileInputStream fis = new FileInputStream(sourceExcel)
			XSSFWorkbook workbook = new XSSFWorkbook(fis)
			ret = workbook
		} catch (FileNotFoundException e) {
			Log.addERROR("Fichier non trouvé : ${e.message}")
			e.printStackTrace()
		} catch (IOException e) {
			Log.addERROR("Erreur d'entrée/sortie lors de l'ouverture du fichier : ${e.message}")
			e.printStackTrace()
		}
		Log.addTraceEND(CLASS_FOR_LOG,"open",ret.getAllNames())
		return ret
	}





	/**
	 * Écrit une valeur dans une cellule.
	 *
	 * @param row Ligne de la cellule.
	 * @param colIdx Indice de la colonne.
	 * @param val Valeur à écrire.
	 * @param cellStyle Style de la cellule (facultatif, valeur par défaut = null).
	 * @param hyperlink Hyperlien de la cellule (facultatif, valeur par défaut = null).
	 */
	static writeCell(Row row, int colIdx, def val, CellStyle cellStyle = null, Hyperlink hyperlink = null) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"writeCell",[rowNum:row.getRowNum(), colIdx:colIdx, val:val, cellStyle:cellStyle, hyperlink:hyperlink])

		if (row == null) {
			Log.addERROR("row is NULL")
		} else {
			Cell cell = row.getCell(colIdx)

			if (!cell) row.createCell(colIdx)

			cell = row.getCell(colIdx)

			if (val instanceof Number) {
				cell.setCellValue(val.doubleValue())
			} else if (val) {
				cell.setCellValue(val.toString())
			} else {
				cell.setCellValue('')
			}

			if (hyperlink) cell.setHyperlink(hyperlink)
			if (cellStyle) cell.setCellStyle(cellStyle)
		}

		Log.addTraceEND(CLASS_FOR_LOG,"writeCell")
	}



	/**
	 * Retourne une liste avec les valeurs de la ligne.
	 *
	 * @param row Ligne à traiter.
	 * @param ideb Indice de la colonne de début (facultatif, valeur par défaut = 0).
	 * @param size Taille de la liste (facultatif, valeur par défaut = 0).
	 * @param nullval Valeur pour une cellule null (facultatif, valeur par défaut = '').
	 * @return Liste contenant les valeurs de la ligne.
	 */
	static List loadRow2(Row row, int ideb = 0, int size = 0, String nullval = '') {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"loadRow2",[rowNum:row.getRowNum(),ideb:ideb,size:size,nullval:nullval])

		List data = []
		if (size == 0) size = row.getLastCellNum()
		if (ideb != 0) size = size + ideb

		for (int i = ideb; i < size; i++) {
			Cell cell = row.getCell(i)
			def value = ExcelUtils.getCellValue(cell, nullval)
			data[i - ideb] = value
		}

		Log.addTraceEND(CLASS_FOR_LOG,"loadRow2",data)

		return data
	}






	/**
	 * Charge les données d'une ligne dans une liste.
	 *
	 * @param row Ligne à charger.
	 * @param size Taille maximale de la liste (facultatif, valeur par défaut = 0).
	 * @return Liste des données de la ligne.
	 */
	static List loadRow(Row row, int size = 0) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"loadRow",[rowNum:row.getRowNum(),size:size])

		List data = []
		for (Cell cell : row) {
			def value = ExcelUtils.getCellValue(cell)
			if ((size == 0 && cell.getColumnIndex() != 0 && value == '') || (size != 0 && cell.getColumnIndex() >= size)) {
				break
			}
			data[cell.getColumnIndex()] = value
		}

		Log.addTraceEND(CLASS_FOR_LOG,"loadRow",data)
		return data
	}






	/**
	 * Formatte la valeur d'une cellule en chaîne de caractères.
	 *
	 * @param cell Cellule à traiter.
	 * @param nullval Valeur à utiliser pour une cellule nulle (facultatif, valeur par défaut = '').
	 * @return Valeur de la cellule formatée en tant que chaîne de caractères.
	 */
	public static def getCellValue(Cell cell, String nullval = '') {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getCellValue",[cell:cell,nullval:nullval])

		def CellData = null

		if (cell == null) {
			CellData = nullval
			Log.addTrace("getCellValue() cell is null!")
		} else {
			Log.addTrace("cell.getAddress() = '${cell.getAddress().toString()}' getCellType() = '${cell.getCellType()}'")

			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING: // 1
					Log.addTrace("CELL_TYPE_STRING")
					CellData = cell.getStringCellValue()
					break
				case Cell.CELL_TYPE_NUMERIC: // 0
					Log.addTrace("CELL_TYPE_NUMERIC")
					if (DateUtil.isCellDateFormatted(cell)) {
						Log.addTrace("isCellDateFormatted() = true" + cell.getDateCellValue().getClass())
						CellData = cell.getDateCellValue()
					} else {
						CellData = (long) cell.getNumericCellValue()
					}
					break
				case Cell.CELL_TYPE_BOOLEAN: // 4
					Log.addTrace("CELL_TYPE_BOOLEAN")
					CellData = cell.getBooleanCellValue()
					break
				case Cell.CELL_TYPE_FORMULA: // 2
					Log.addTrace("CELL_TYPE_FORMULA")
					Workbook wb = cell.getSheet().getWorkbook()
					CreationHelper crateHelper = wb.getCreationHelper()
					FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator()
					CellData = getCellValue(evaluator.evaluateInCell(cell))
					break
				case Cell.CELL_TYPE_BLANK: // 3
					Log.addTrace("CELL_TYPE_BLANK")
					CellData = ""
					break
				case Cell.CELL_TYPE_ERROR: // 5
					CellData = 'ERROR type Cell'
					Log.addERROR("Unknown type Cell!")
					break
				default:
					CellData = 'Unknown type Cell : ' + cell.getCellType()
					Log.addERROR("ERROR type Cell!")
					break
			}
		}

		Log.addTraceEND(CLASS_FOR_LOG,"getCellValue",CellData)
		return CellData
	}









	/**
	 * Retourne l'index de la colonne contenant le nom de colonne spécifié dans la feuille de calcul.
	 *
	 * @param sheet      Feuille de calcul dans laquelle rechercher la colonne.
	 * @param columnName Nom de la colonne à rechercher.
	 * @param numRow     Numéro de ligne à partir duquel rechercher le nom de colonne (facultatif, valeur par défaut = 0).
	 * @return Index de la colonne contenant le nom de colonne, ou -1 si le nom de colonne n'a pas été trouvé.
	 */
	static int getColumnIndexOfColumnName(Sheet sheet, String columnName, int numRow = 0) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getColumnIndexOfColumnName",[sheet:sheet.getSheetName(),columnName:columnName,numRow:numRow])
		int ret = -1
		for (Cell cell : sheet.getRow(numRow)) {
			if (getCellValue(cell) == columnName) {
				ret = cell.getColumnIndex()
				break
			}
		}
		if (ret==-1) {
			Log.addERROR("ExcelUtils.getColumnIndexOfColumnName() columnName='${columnName}' numRow='${numRow}' Nom de colonne non trouvé")
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getColumnIndexOfColumnName",ret)
		return ret
	}








	static int getLastColumnIndex(Sheet sheet, int numRow) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getLastColumnIndex",[sheet:sheet.getSheetName(),numRow:numRow])

		int lastCellNum = sheet.getRow(numRow).getLastCellNum()
		int ret = -1
		for (int i : (0..lastCellNum)) {
			Cell cell = sheet.getRow(numRow).getCell(i)
			if (!getCellValue(cell)) {
				ret = i
				break
			}
		}

		Log.addTraceEND(CLASS_FOR_LOG,"getLastColumnIndex",ret)
		return ret
	}










	/**
	 * Retourne le numéro de la première ligne où la cellule est vide ou nulle dans la colonne spécifiée.
	 *
	 * @param sheet Feuille de calcul dans laquelle rechercher la première cellule vide.
	 * @param col   Index de la colonne à vérifier (facultatif, valeur par défaut = 0).
	 * @return Numéro de la première ligne où la cellule est vide ou nulle, ou -1 si aucune ligne n'est trouvée.
	 */
	static int getRowNumOfFirstCellFree(Sheet sheet, int col = 0) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getRowNumOfFirstCellFree",[sheet:sheet.getSheetName(),col:col])

		int num = -1
		for (int numLine : (0..sheet.getLastRowNum())) {
			Row row = sheet.getRow(numLine)
			if (row == null) {
				num = numLine
				break
			} else if (getCellValue(row.getCell(col)) == '') {
				num = numLine
				break
			}
			num = numLine + 1
		}

		if (num == -1) {
			Log.addERROR("ExcelUtils.getRowNumOfFirstCellFree of ${sheet.getSheetName()}: ${num}")
		}
		Log.addTraceEND(CLASS_FOR_LOG,"getRowNumOfFirstCellFree",num)
		return num
	}





	/**
	 * Récupère une nouvelle ligne de la feuille de calcul ou crée une nouvelle ligne si elle n'existe pas.
	 *
	 * @param sheet  Feuille de calcul dans laquelle récupérer ou créer la ligne.
	 * @param numRow Numéro de la ligne à récupérer ou créer.
	 * @return L'objet Row correspondant à la ligne spécifiée.
	 */
	static Row getNewRow(Sheet sheet, int numRow) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getNewRow",[sheet:sheet.getSheetName(),numRow:numRow])

		Row row
		if (sheet.getRow(numRow) == null) {
			row = sheet.createRow(numRow)
		} else {
			row = sheet.getRow(numRow)
		}

		Log.addTraceEND(CLASS_FOR_LOG,"getNewRow",row.getRowNum())
		return row
	}


	/**
	 * Récupère la prochaine ligne libre dans la feuille de calcul à partir de la colonne spécifiée.
	 *
	 * @param sheet Feuille de calcul dans laquelle récupérer la prochaine ligne libre.
	 * @param col   Index de la colonne à partir de laquelle vérifier la prochaine ligne libre (facultatif, valeur par défaut = 0).
	 * @return L'objet Row correspondant à la prochaine ligne libre.
	 */
	static Row getNextRow(Sheet sheet, int col = 0) {
		Log.addTraceBEGIN(CLASS_FOR_LOG,"getNextRow",[sheet:sheet.getSheetName(),col:col])

		int rowNum = getRowNumOfFirstCellFree(sheet, col)
		Row row = getNewRow(sheet, rowNum)

		Log.addTraceEND(CLASS_FOR_LOG,"getNextRow",row.getRowNum())
		return row
	}



} // Fin de class
