import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.*

// Ouvrir un fichier Excel
def fichier = new XSSFWorkbook(new FileInputStream("TNR_Log/fichier2.xlsx"))

// Obtenir la première feuille de calcul
def feuille = fichier.getSheetAt(0)

// Parcourir toutes les couleurs disponibles
IndexedColors.values().each { couleur ->
	// Créer un style avec la couleur
	def style = fichier.createCellStyle()
	style.setFillForegroundColor(couleur.index)
	style.setFillPattern(FillPatternType.SOLID_FOREGROUND)

	// Ajouter une cellule avec le style
	def ligne = feuille.createRow(feuille.getLastRowNum() + 1)
	def cellule = ligne.createCell(0)
	cellule.setCellValue(couleur.toString())
	cellule.setCellStyle(style)
}

// Enregistrer les modifications dans le fichier Excel
def fileOut = new FileOutputStream("TNR_Log/fichier2.xlsx")
fichier.write(fileOut)
fileOut.close()
fichier.close()