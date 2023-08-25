import tnrLog.Log
import tnrJDDManager.JDDFiles
import tnrJDDManager.PREJDDFiles






List list =[]

JDDFiles.JDDfilemap.each { modObj,fullName ->
	
	def myJDD = new my.JDD(fullName)
	
	myJDD.getAllPrerequis(list)

	
}

list.eachWithIndex { map,idx ->
	
	Log.addTrace(idx + ' : ' + PREJDDFiles.getFullnameFromModObj(map.getAt('PREJDDMODOBJ')))
	
	map.each { key,val ->
		Log.addTrace('\t' + key + ' : ' +val)
	}
	
	my.PREJDD.checkPREJDD(map)
}






/*
JDDFiles.JDDfilemap.each { modObj,fullName ->
	
	//println modObj + '            ' +fullName
	
	// read JDD
	File sourceExcel = new File(fullName)
	FileInputStream fxls = new FileInputStream(sourceExcel)
	XSSFWorkbook book = new XSSFWorkbook(fxls)
	
	//println fullName + ' : ' +book.getNumberOfSheets()
	
		for(Sheet sheet: book) {
			if (sheet.getSheetName() in SKIP_LIST_SHEETNAME) {
	            // skip
	        }else {
				
				//println("=> " + sheet.getSheetName())
				
				//println myJDD.getColIndexOfFieldName(sheet,'ID_CODCAT')
			}
			
		}
}
*/

