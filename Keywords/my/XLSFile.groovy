package my

import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import com.kms.katalon.core.annotation.Keyword


public class XLSFile {


	/**
	 * 
	 * @param 
	 * @return 
	 */
	@Keyword
	def lireXLS(){

		File sourceExcel = new File('JDD/JDD.RO.ACTEUR.xlsx')
		FileInputStream fxls = new FileInputStream(sourceExcel)
		XSSFWorkbook book = new XSSFWorkbook(fxls)


		XSSFSheet sh = book.getSheet('ACTEUR')

		XSSFRow row = sh.getRow(0)

		int colNum = row.getLastCellNum()

		println('nbr col : ' + colNum)

		for (XSSFCell c in sh.getRow(0)) {
			if (c.toString()=="") {
				println " on sort"
				break
			}
			println "nom : " + c+ "index : " + c.getColumnIndex()
		}


		fxls.close()
	}



	///////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 *  import org.apache.poi.ss.usermodel.*
	 *  
	 def lireXLS(){
	 DataFormatter formatter = new DataFormatter()
	 File sourceExcel = new File('JDD/JDD.RO.ACTEUR.xlsx')
	 FileInputStream fis = new FileInputStream(sourceExcel)
	 // Open the .xlsx file and construct a workbook object
	 //XSSFWorkbook book = new XSSFWorkbook(fis)
	 Workbook book = new Workbook(fis)
	 println "----------------------------------------------------------"
	 // nbre d'onglet
	 int nrSheets = book.getNumberOfSheets();
	 println "Nbr de sheet : " + nrSheets
	 for ( int i = 0; i < nrSheets; i++ ) {
	 println "Sheet name : " + book.getSheetName( i );
	 }
	 // Get the top sheet out of the workbook
	 XSSFSheet sheet = book.getSheetAt(0)
	 Row frow = sheet.getRow(0)
	 for (Cell c in sheet.getRow(0)) {
	 if (formatter.formatCellValue(c) =="") {
	 println " on sort"
	 break
	 }
	 println "nom : " + formatter.formatCellValue(c) + "index : " + c.getColumnIndex()
	 }
	 //XSSFSheet shActeur = book.getSheetName('ACTEUR')
	 //println "sheet ACTEUR toprow : " + shActeur.getTopRow()
	 println "getFirstRowNum : " + sheet.getFirstRowNum()
	 println "getLastRowNum : " + sheet.getLastRowNum()
	 //Row frow = sheet.getRow(0)
	 // Iterating over rows in the sheet
	 for (Row row in sheet) {
	 // Iterating over each column of the row
	 for (Cell cell in row) {
	 switch (cell.getCellType()) {
	 case Cell.CELL_TYPE_STRING:
	 print(cell.getStringCellValue() + "(string) \t")
	 break
	 case Cell.CELL_TYPE_NUMERIC:
	 print(cell.getNumericCellValue() + "(numeric) \t")
	 break
	 case Cell.CELL_TYPE_BOOLEAN:
	 print(cell.getBooleanCellValue() + "(boolean) \t")
	 break
	 default:
	 break
	 }
	 }
	 print "\n"
	 }
	 }
	 */


	/*
	 * 
	 * lire un fichier xls il faut ajouter les import
	 * 
	 * 
	 import org.apache.poi.ss.usermodel.Cell
	 import org.apache.poi.ss.usermodel.Row
	 import org.apache.poi.ss.usermodel.Sheet
	 import org.apache.poi.xssf.usermodel.XSSFSheet
	 import org.apache.poi.xssf.usermodel.XSSFWorkbook
	 def lireXLS(){
	 File sourceExcel = new File("JDD/JDD.RO.xlsx");
	 FileInputStream fis = new FileInputStream(sourceExcel);
	 // Open the .xlsx file and construct a workbook object
	 XSSFWorkbook book = new XSSFWorkbook(fis)
	 // Get the top sheet out of the workbook
	 XSSFSheet sheet = book.getSheetAt(0)
	 // Iterating over rows in the sheet
	 for (Row row in sheet) {
	 // Iterating over each column of the row
	 for (Cell cell in row) {
	 switch (cell.getCellType()) {
	 case Cell.CELL_TYPE_STRING:
	 print(cell.getStringCellValue() + "(string) \t")
	 break
	 case Cell.CELL_TYPE_NUMERIC:
	 print(cell.getNumericCellValue() + "(numeric) \t")
	 break
	 case Cell.CELL_TYPE_BOOLEAN:
	 print(cell.getBooleanCellValue() + "(boolean) \t")
	 break
	 default:
	 break
	 }
	 }
	 print "\n"
	 }
	 }
	 */



	/*
	 * exemple de lecture de fichier par ligne, pas besoin d'import
	 * 
	 * 
	 def lireFichier(String modulename, String objetname){
	 File jddf = new File('JDD/JDD.RO.csv')
	 def line, noOfLines = 0;
	 jddf.withReader { reader ->
	 while ((line = reader.readLine()) != null) {
	 println "${line}"
	 noOfLines++
	 }
	 }
	 }
	 */
}
