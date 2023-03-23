
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import my.Log as MYLOG
import my.InfoPARA
import my.JDDFiles

MYLOG.addSubTITLE('Renseigner InfoPARA avec le contenu des JDD')

InfoPARA.load()

JDDFiles.JDDfilemap.each { modObj,fullName ->


	def myJDD = new my.JDD(fullName)
	for(Sheet sheet: myJDD.book) {
		if (!(sheet.getSheetName() in myJDD.SKIP_LIST_SHEETNAME)) {

			if (my.XLS.getCellValue(sheet.getRow(0).getCell(0))!='') {
				
				MYLOG.addSUBSTEP("Onglet : " + sheet.getSheetName())
	
				myJDD.loadTCSheet(sheet)
	
				for (col in myJDD.headers.drop(1)) {
	
					InfoPARA.update(myJDD, col,fullName, modObj+'.'+sheet.getSheetName())
					//MYLOG.addDETAIL(col)
	
				}
			}
		}
	}
}

InfoPARA.write()

