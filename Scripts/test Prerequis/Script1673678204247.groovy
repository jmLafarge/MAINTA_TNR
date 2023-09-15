import tnrLog.Log
import tnrJDDManager.JDDFileMapper
import tnrJDDManager.PREJDDFilesMapper






List list =[]

JDDFilesMapper.JDDfilemap.each { modObj,fullName ->
	
	JDD myJDD = new JDD(fullName)
	
	myJDD.getAllPrerequis(list)

	
}

list.eachWithIndex { map,idx ->
	
	Log.addTrace(idx + ' : ' + PREJDDFilesMapper.getFullnameFromModObj(map.getAt('PREJDDMODOBJ')))
	
	map.each { key,val ->
		Log.addTrace('\t' + key + ' : ' +val)
	}
	
	my.PREJDD.checkPREJDD(map)
}






/*
JDDFilesMapper.JDDfilemap.each { modObj,fullName ->
	
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

