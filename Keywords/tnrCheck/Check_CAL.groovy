package tnrCheck

import groovy.transform.CompileStatic
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import tnrLog.Log
import tnrCommon.ExcelUtils
import tnrPREJDDManager.PREJDDFileMapper

@CompileStatic
public class Check_CAL {

	private static final String CLASS_FORLOG = 'Check_CAL'

	private static List listCAL =[]
	private static List listCALDEF=[]
	private static boolean status = true

	static run() {

		Log.addTraceBEGIN(CLASS_FORLOG,"run",[:])

		Log.addSubTITLE('Vérification spécifique de CAL/CALDEF')

		XSSFWorkbook book = tnrCommon.ExcelUtils.open(PREJDDFileMapper.PREJDDfilemap['RO.CAL'])

		Sheet shCAL = book.getSheet('001')
		Sheet shCALDEF = book.getSheet('001A')

		loadListCAL(shCAL)
		loadListCALDEF(shCALDEF)

		listCAL.each {

			for (def i in [1, 2, 3, 4, 5, 6, 7, '$VIDE']){
				if (!listCALDEF.contains(it.toString() +' - '+ i.toString())) {
					Log.addDETAILFAIL("Manque $it - $i dans CALDEF")
					status = false
				}
			}
		}
		if (status) {
			Log.addINFO('     ***  OK   ***')
		}
		Log.addTraceEND(CLASS_FORLOG,"run")
	}




	private static loadListCAL(Sheet sheet) {
		Log.addTraceBEGIN(CLASS_FORLOG,"loadListCAL",[sheet:sheet.getSheetName()])
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		Log.addTrace("Chargement de CAL")
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (tnrCommon.ExcelUtils.getCellValue(row.getCell(0)) == '') {
				break
			}
			listCAL.add(tnrCommon.ExcelUtils.getCellValue(row.getCell(0)).toString()+ ' - ' + tnrCommon.ExcelUtils.getCellValue(row.getCell(1)))
		}
		Log.addTraceEND(CLASS_FORLOG,"loadListCAL")
	}



	private static loadListCALDEF(Sheet sheet) {
		Log.addTraceBEGIN(CLASS_FORLOG,"loadListCALDEF",[sheet:sheet.getSheetName()])
		Iterator<Row> rowIt = sheet.rowIterator()
		Row row = rowIt.next()
		Log.addTrace("Chargement de CALDEF")
		while(rowIt.hasNext()) {
			row = rowIt.next()
			if (tnrCommon.ExcelUtils.getCellValue(row.getCell(0)) == '') {
				break
			}
			listCALDEF.add(tnrCommon.ExcelUtils.getCellValue(row.getCell(0)).toString() + ' - ' + tnrCommon.ExcelUtils.getCellValue(row.getCell(1))+ ' - ' + tnrCommon.ExcelUtils.getCellValue(row.getCell(5)))
		}
		Log.addTraceEND(CLASS_FORLOG,"loadListCALDEF")
	}
}
