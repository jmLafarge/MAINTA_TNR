import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.*

// Ouvrir un fichier Excel
def fichier = new XSSFWorkbook(new FileInputStream("workbook.xlsx"))

// Obtenir la première feuille de calcul
def sheet = fichier.getSheetAt(0)

sheet.setRowSumsBelow(false) // rouper par la ligne du haut

sheet.groupRow(4, 10) // ça veut dire que les lignes de 4 à 10 (5 à 11 sur xls) sont cachées. lka ligfne 3 (4 sur xls) est la ligne où il y a le +



sheet.setRowGroupCollapsed(4, true)

sheet.groupRow(12, 15)// là ça ce suit

sheet.setRowGroupCollapsed(12, false)


sheet.groupRow(17, 27)// là ça ce suit

sheet.setRowGroupCollapsed(17, true)
/*
// masque les lignes groupées
for (int i = rowStart; i <= rowEnd; i++) {
    sheet.getRow(i).setZeroHeight(true)
}
*/

/*

// Obtenir un style existant de cellule
def style = fichier.createCellStyle()
style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index)
style.setFillPattern(FillPatternType.SOLID_FOREGROUND)

// Changer la couleur de fond du style
style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index)

// Ajouter une cellule avec le style modifié
def ligne = feuille.createRow(feuille.getLastRowNum() + 1)
def cellule = ligne.createCell(0)
cellule.setCellValue("111")
cellule.setCellStyle(style)


// Changer la couleur de fond du style
style.setFillForegroundColor(IndexedColors.ORANGE.index)
cellule = ligne.createCell(1)

cellule.setCellValue("222")
cellule.setCellStyle(style)

*/

// Enregistrer les modifications dans le fichier Excel
def fileOut = new FileOutputStream("workbook.xlsx")
fichier.write(fileOut)
fileOut.close()
fichier.close()