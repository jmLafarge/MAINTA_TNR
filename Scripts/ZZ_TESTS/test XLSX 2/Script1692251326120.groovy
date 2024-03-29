import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.*

// Ouvrir un fichier Excel
def fichier = new XSSFWorkbook(new FileInputStream("TNR/infoPARA.xlsx"))

// Obtenir la première feuille de calcul
def sh = fichier.getSheetAt(0)

Map paraMap=[:]

Iterator<Row> rowIt = sh.rowIterator()
Row row = rowIt.next()
println "après le 1er rowIT.next "+row.getRowNum()
while(rowIt.hasNext()) {
	row = rowIt.next()
	println "après rowIT.next "+row.getRowNum()
	if (!ExcelUtils.getCellValue(row.getCell(0))) {
		println 'cell vide'
		break
	}
	paraMap.putAt(ExcelUtils.getCellValue(row.getCell(0)),ExcelUtils.loadRow2(row,0,10,'x'))
}

Tools.parseMap(paraMap)

println paraMap.size()

println ExcelUtils.loadRow(sh.getRow(0),15)