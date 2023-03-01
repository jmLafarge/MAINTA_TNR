import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.*

// Ouvrir un fichier Excel
def fichier = new XSSFWorkbook(new FileInputStream("TNR_Log/fichier3.xlsx"))

// Obtenir la première feuille de calcul
def feuille = fichier.getSheetAt(0)

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



// Enregistrer les modifications dans le fichier Excel
def fileOut = new FileOutputStream("TNR_Log/fichier3.xlsx")
fichier.write(fileOut)
fileOut.close()
fichier.close()