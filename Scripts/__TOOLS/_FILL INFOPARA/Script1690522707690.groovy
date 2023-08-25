
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import tnrCommon.InfoPARA
import tnrJDDManager.JDD
import tnrJDDManager.JDDFiles
import tnrJDDManager.JDDKW
import tnrLog.Log
import tnr.PREJDDFiles
import tnrCommon.ExcelUtils


Log.addTITLE("Lancement de FILL INFOPARA")

Log.addSubTITLE('Renseigner InfoPARA avec le contenu des JDD')


JDDFiles.JDDfilemap.each { modObj,fullName ->

	Log.addINFO("")
	def myJDD = new my.JDD(fullName)
	for(Sheet sheet: myJDD.book) {
		if (myJDD.isSheetAvailable(sheet.getSheetName())) {

			if (tnrCommon.ExcelUtils.getCellValue(sheet.getRow(0).getCell(0))!='') {
				
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







JDDFiles.JDDfilemap.each { modObj,fullName ->
	
	Log.addINFO("")
	XSSFWorkbook book = tnrCommon.ExcelUtils.open(fullName)
	myJDD = new JDD(JDDFiles.JDDfilemap.getAt(modObj),null,null,false)
	for(Sheet sheet: book) {
		if (myJDD.isSheetAvailable(sheet.getSheetName())) {					// si le sheet est Available
			Log.addTrace("Onglet : " + sheet.getSheetName())
			List headersPREJDD = tnrCommon.ExcelUtils.loadRow(sheet.getRow(0))
			for (def row in sheet) {									// pour chaque ligne
				String cdt = tnrCommon.ExcelUtils.getCellValue(row.getCell(0)).toString()
				if (cdt!='') {											// si la premiere cellule (cdt) n'est pas vide
					headersPREJDD.eachWithIndex { name,idx ->
						String cellValue = tnrCommon.ExcelUtils.getCellValue(row.getCell(idx)).toString()
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
	

	
	



PREJDDFiles.PREJDDfilemap.each { modObj,fullName ->
	
	Log.addINFO("")
	XSSFWorkbook book = tnrCommon.ExcelUtils.open(fullName)
	myJDD = new JDD(JDDFiles.JDDfilemap.getAt(modObj),null,null,false)
	for(Sheet sheet: book) {
		if (myJDD.isSheetAvailable(sheet.getSheetName())) {					// si le sheet est Available
			Log.addTrace("Onglet : " + sheet.getSheetName())
			List headersPREJDD = tnrCommon.ExcelUtils.loadRow(sheet.getRow(0))		
			for (def row in sheet) {									// pour chaque ligne
				String cdt = tnrCommon.ExcelUtils.getCellValue(row.getCell(0)).toString()
				if (cdt!='') {											// si la premiere cellule (cdt) n'est pas vide
					headersPREJDD.eachWithIndex { name,idx ->
						String cellValue = tnrCommon.ExcelUtils.getCellValue(row.getCell(idx)).toString()
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

