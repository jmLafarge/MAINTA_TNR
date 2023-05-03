
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import my.result.TNRResult
import my.InfoPARA
import my.JDDFiles
import my.InfoBDD


Log.addTITLE("Lancement de FILL INFOPARA")
if (InfoBDD.map.isEmpty()) { InfoBDD.load() }
if (JDDFiles.JDDfilemap.isEmpty()) { JDDFiles.load() }


Log.addSubTITLE('Renseigner InfoPARA avec le contenu des JDD')

InfoPARA.load()

JDDFiles.JDDfilemap.each { modObj,fullName ->

	Log.addINFO("")
	def myJDD = new my.JDD(fullName)
	for(Sheet sheet: myJDD.book) {
		if (!(sheet.getSheetName() in myJDD.SKIP_LIST_SHEETNAME)) {

			if (my.XLS.getCellValue(sheet.getRow(0).getCell(0))!='') {
				
				TNRResult.addSUBSTEP("Onglet : " + sheet.getSheetName())
	
				myJDD.loadTCSheet(sheet)
	
				for (col in myJDD.headers.drop(1)) {
					Log.addDEBUG("Traitement $col")
					InfoPARA.update(myJDD, col,fullName, modObj+'.'+sheet.getSheetName())
					
	
				}
			}
		}
	}
}

InfoPARA.write()

