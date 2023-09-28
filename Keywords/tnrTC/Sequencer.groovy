package tnrTC

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import tnrCommon.ExcelUtils
import tnrCommon.TNRPropertiesReader
import tnrLog.Log


@CompileStatic
public class Sequencer {

	private static final String CLASS_NAME = 'Sequencer'


	public static List testCasesList = []
	/*
	 * 
	 * Example :
	 *
	 * [ TCFULLNAME : full path TC1 , CDTPATTERN : CDT1 , REP : 1 ] 
	 * 
	 * [ TCFULLNAME : full path TC2 , CDTPATTERN : CDT21 , REP : 5 ]
	 * 
	 * [ TCFULLNAME : full path TC2 , CDTPATTERN : CDT22 , REP : 1 ]
	 *
	 * [ TCFULLNAME : full path TC3 , CDTPATTERN : CDT3 ,  REP : 2 ] 
	 *
	 */


	static {
		Log.addTraceBEGIN(CLASS_NAME,"static",[:])
		Log.addSubTITLE('Load testCasesList from TNR sequencer file','-',120)
		Log.addINFO("\t" + 'CDTPATTERN'.padRight(24) + 'TCFULLNAME'.padRight(90) + 'REP')
		Log.addINFO("")

		Sheet shTNR = getSheetSequencerFile()

		Log.addTrace('shTNR.getLastRowNum() :' + shTNR.getLastRowNum())

		// for each data line
		for (int numline : (1..shTNR.getLastRowNum())) {

			Row row = shTNR.getRow(numline)

			// exit if lastRow of CAS_DE_TEST
			if (!row || ExcelUtils.getCellValue(row.getCell(0))=='') {
				break
			}

			String casDeTestPatternFromSequencer = row.getCell(0).getStringCellValue()

			Log.addTrace('casDeTestPatternFromSequencer = ' + casDeTestPatternFromSequencer)

			String repStr = ExcelUtils.getCellValue(row.getCell(1)).toString()
			int rep = 0

			if (repStr=='') {
				rep = 1
			}else {
				try {
					rep= repStr.toInteger()
				} catch (NumberFormatException e) {
					Log.addERROR("La répétition n'est pas un entier, ligne ${numline + 1}")
				}
			}

			// renvoie  tous les cas ou le nom de TC contient la valeur du sequenceur (équivalent de RO.ACT*)
			Map <String,String> testCasesFound = TCFileMapper.getValuesWhereTCNameStartsWith(casDeTestPatternFromSequencer)

			if (testCasesFound .size()==0) {

				// cas ou casDeTestPatternFromSequencer = RO.ACT.001.CRE.01 et TCName = RO.ACT.001.CRE
				String TCName = TCFileMapper.getTCNameWhereTxtStartingWithTCName(casDeTestPatternFromSequencer)

				if (TCName) {
					addToTestCasesList(casDeTestPatternFromSequencer,TCFileMapper.getTCFullname(TCName), rep)
				}else {
					Log.add('WARNING',"\tPas de fichier trouvé pour le pattern $casDeTestPatternFromSequencer")
				}
			}else {
				testCasesFound .each {
					addToTestCasesList(it.key,it.value, rep)
				}
			}

		} // end of for each data line
		Log.addTraceEND(CLASS_NAME,"static")
	}




	private static addToTestCasesList (String TCName,String TCFullName, int rep) {

		Map TCMap = [:]

		Log.addINFO('\t' + TCName.padRight(24) + TCFullName.padRight(90) + rep.toString().padLeft(3))

		TCMap.put('CDTPATTERN',TCName)
		TCMap.put('TCFULLNAME',TCFullName)
		TCMap.put('REP',rep)

		testCasesList.add(TCMap)

	}



	private static Sheet getSheetSequencerFile() {

		String tnrPath = TNRPropertiesReader.getMyProperty('TNR_PATH')
		String sequencerFilename = TNRPropertiesReader.getMyProperty('SEQUENCER_FILENAME')
		String sequencerSheetName = TNRPropertiesReader.getMyProperty('SEQUENCER_SHEETNAME')

		XSSFWorkbook book = ExcelUtils.open(tnrPath + File.separator + sequencerFilename )

		return book.getSheet(sequencerSheetName)

	}







} //Fin de class
