
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import InfoPARA
import tnrJDDManager.JDD
import tnrJDDManager.JDDFileMapper
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnrPREJDDManager.JDDFilesMapper
import ExcelUtils


Log.addTITLE("Lancement de FILL INFOPARA")

Log.addSubTITLE('Renseigner InfoPARA avec le contenu des JDD')


JDDFilesMapper.JDDfilemap.each { modObj,fullName ->

	Log.addINFO("")
	def myJDD = new my.JDD(fullName)
	for(Sheet sheet: myJDD.book) {
		if (myJDD.isSheetAvailable(sheet.getSheetName())) {

			if (ExcelUtils.getCellValue(sheet.getRow(0).getCell(0))!='') {
				
				Log.addTrace("Onglet : " + sheet.getSheetName())
	
				myJDD.loadTCSheet(sheet)
	
				for (col in myJDD.headers.drop(1)) {
					Log.addTrace("Traitement $col")
					InfoPARA.updateShPara(myJDD, col,fullName, modObj+'.'+sheet.getSheetName())
				}
			}
		}
	}
}







JDDFilesMapper.JDDfilemap.each { modObj,fullName ->
	
	Log.addINFO("")
	XSSFWorkbook book = ExcelUtils.open(fullName)
	myJDD = new JDD(JDDFilesMapper.JDDfilemap.getAt(modObj),null,null,false)
	for(Sheet sheet: book) {
		if (myJDD.isSheetAvailable(sheet.getSheetName())) {					// si le sheet est Available
			Log.addTrace("Onglet : " + sheet.getSheetName())
			List headersPREJDD = ExcelUtils.loadRow(sheet.getRow(0))
			for (def row in sheet) {									// pour chaque ligne
				String cdt = ExcelUtils.getCellValue(row.getCell(0)).toString()
				if (cdt!='') {											// si la premiere cellule (cdt) n'est pas vide
					headersPREJDD.eachWithIndex { name,idx ->
						String cellValue = ExcelUtils.getCellValue(row.getCell(idx)).toString()
						if (JDDKW.isAllowedKeyword(cellValue)){
							InfoPARA.writeLineKW(fullName,cdt,name,cellValue)
						}
					}
				}else {
					break
				}
			}
		}
	}
}
	

	
	



PREJDDFilesMapper.PREJDDfilemap.each { modObj,fullName ->
	
	Log.addINFO("")
	XSSFWorkbook book = ExcelUtils.open(fullName)
	myJDD = new JDD(JDDFilesMapper.JDDfilemap.getAt(modObj),null,null,false)
	for(Sheet sheet: book) {
		if (myJDD.isSheetAvailable(sheet.getSheetName())) {					// si le sheet est Available
			Log.addTrace("Onglet : " + sheet.getSheetName())
			List headersPREJDD = ExcelUtils.loadRow(sheet.getRow(0))		
			for (def row in sheet) {									// pour chaque ligne
				String cdt = ExcelUtils.getCellValue(row.getCell(0)).toString()
				if (cdt!='') {											// si la premiere cellule (cdt) n'est pas vide
					headersPREJDD.eachWithIndex { name,idx ->
						String cellValue = ExcelUtils.getCellValue(row.getCell(idx)).toString()
						if (JDDKW.isAllowedKeyword(cellValue)){
							InfoPARA.writeLineKW(fullName,cdt,name,cellValue)
						}
					}
				}else {
					break
				}
			}
		}
	}
}
	
	
	

InfoPARA.write()

