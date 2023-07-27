
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import my.InfoBDD
import my.JDDFiles
import my.Log
import my.NAV
import my.PREJDDFiles
import my.XLS

Log.setDebugLevel(1)
Log.setDebugDeph(4)

Log.addTITLE("Lancement de CREATE PREJDD IN DB")
 

def listPREJDD =	 [
		 ['RO.CAT','001'],
		 ['RO.HAB','001'],
		 ['RO.MET','001'],
		 ['RO.CAL','001'],
		 ['RO.CAL','001A'],
		 ['RT.EMP','001'],
		 ['RO.ORG','001'],
		 ['RO.ORG','001A'],
		 ['RO.ACT','001'],
		 ['RO.ACT','005'],
		 ['RO.ACT','003HAB'],
		 ['RO.ACT','003MET'],
		 ['RO.ACT','004EMP'],
		 ['RO.SOCIETE','001'],
		 ['RO.UTI','001'],
		 ['RO.CCO','001'],
		 ['RO.DEV','001'],
		 ['AC.CEM','001'],
		 ['AC.CMR','001'],
		 ['AC.CPA','001'],
		 ['AC.CPO','001'],
		 ['RO.ADR','001'],
		 ['RO.LIE','001'],
		 ['RO.FOU','001A'],
		 ['RO.FOU','001'],
		 ['MP.CPT','001'],
		 ['RT.NOM','001'],
		 ['RT.ART','001'],
		 ['RT.ART','001A'],
		 ['RT.ART','001B'],
		 ['RT.ART','001C'],
		 ['RT.MOY','001'],
		 ['RT.EQU','001'],
		 ['RT.EQU','001A'],
		 ['RT.EQU','001B'],
		 ['RT.EQU','001C'],
		 ['RT.MAT','001'],
		 ['RT.MAT','001A'],
		 ['RT.MAT','001B'],
		 ['RT.MAT','001C'],
		 ['RO.IMP','001'],
		 ['RO.IMP','001A']
	]





Log.addSubTITLE('Création des PREJDD')

listPREJDD.each { li ->
	
	PREJDDFiles.insertPREJDDinDB(li[0],li[1])
	
}


Log.addSubTITLE('Liste des PREJDD manquant :')

PREJDDFiles.PREJDDfilemap.each { modObj,fullName ->
	
	XSSFWorkbook book = XLS.open(fullName)
	for (Sheet sheet : book ) {
		String sheetName = sheet.getSheetName()
		if (!['Version','MODELE'].contains(sheetName)) {
			//println sheet.getSheetName()
			if (!listPREJDD.contains([modObj,sheetName])) {
				if (XLS.getCellValue(sheet.getRow(1).getCell(0))=='') {
					Log.addToList('emptyPREJDD',"$fullName ($sheetName) est vide")
				}else {
					Log.add('WARNING',"\tManque $fullName ($sheetName)")
					// for each data line
					for (int numline : (1..sheet.getLastRowNum())) {
						Row row = sheet.getRow(numline)
						// exit if lastRow
						String cdt = my.XLS.getCellValue(row.getCell(0))
						if (!row || cdt =='') {
							break
						}else {
							Log.addDETAIL("\t- $cdt")
						}
					}
				}

			}
		}

	}
	
}



Log.addSubTITLE('Liste des PREJDD vide :')
Log.writeList('emptyPREJDD')


Log.addTITLE("Fin des créations des PRE REQUIS")



