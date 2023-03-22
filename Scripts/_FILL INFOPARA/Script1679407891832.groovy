
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook


my.Log.addSubTITLE('Renseigner InfoPARA avec le contenu des JDD')

my.InfoPARA.load()

my.JDDFiles.JDDfilemap.each { modObj,fullName ->


	def myJDD = new my.JDD(fullName)
	for(Sheet sheet: myJDD.book) {
		if (!(sheet.getSheetName() in myJDD.SKIP_LIST_SHEETNAME)) {

			if (my.XLS.getCellValue(sheet.getRow(0).getCell(0))!='') {
				
				my.Log.addSUBSTEP("Onglet : " + sheet.getSheetName())
	
				myJDD.loadTCSheet(sheet)
	
				for (col in myJDD.headers.drop(1)) {
	
					my.InfoPARA.update(myJDD, col,fullName, modObj+'.'+sheet.getSheetName())
					//my.Log.addDETAIL(col)
	
				}
			}
		}
	}
}

my.InfoPARA.write()

