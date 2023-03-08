package my

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.io.FileType

public class Sequencer {

	/*
	 * Read the TNRSequencer File
	 * 
	 * 
	 * 
	 */


	static public List testCasesList = []
	/*
	 * 
	 * Example :
	 *
	 * [ FULLTCNAME : full path TC1 , NAME : CDT1 , REP : 1 ] 
	 * 
	 * [ FULLTCNAME : full path TC2 , NAME : CDT21 , REP : 5 ]
	 * 
	 * [ FULLTCNAME : full path TC2 , NAME : CDT22 , REP : 1 ]
	 *
	 * [ FULLTCNAME : full path TC3 , NAME : CDT3 ,  REP : 2 ] 
	 *
	 */



	/*
	 * CONSTRUCTOR
	 *
	 */
	static public load() {

		my.Log.addSubTITLE('Load testCasesList from TNR sequencer file')
		my.Log.addINFO("\t" + 'TCNAME'.padRight(24) + 'TCFULLNAME'.padRight(90) + 'REP')
		my.Log.addINFO("")

		// read JDD
		Sheet shTNR = this.readSequencerFile()

		my.Log.addDEBUG('shTNR.getLastRowNum() :' + shTNR.getLastRowNum(),2)

		// for each data line
		for (int numline : (1..shTNR.getLastRowNum())) {

			Row row = shTNR.getRow(numline)

			// exit if lastRow of CAS_DE_TEST
			if (row==null || row.getCell(0) == null) {
				break
			}

			String casDeTestPatternFromSequencer = row.getCell(0).getStringCellValue()

			my.Log.addDEBUG('casDeTestPatternFromSequencer = ' + casDeTestPatternFromSequencer)

			if (casDeTestPatternFromSequencer == "") {
				break
			}


			// Default value if REPETITION cell is null
			int rep = (row.getCell(1) == null) ? 1 : row.getCell(1).getNumericCellValue()

			//def res= my.TCFiles.TCfileList.findAll{ it.contains(casDeTestPatternFromSequencer)}
			Map res= my.TCFiles.TCfileMap.findAll { it.key.contains(casDeTestPatternFromSequencer) }

			if (res.size()==0) {
				my.Log.addERROR("Pas de fichier trouvé pour le pattern $casDeTestPatternFromSequencer")
			}else {
				res.each {
					this.addToTestCasesList(it.key,it.value, rep)
				}
			}

		} // end of for each data line

	}//end of constructor


	private static addToTestCasesList (String TCName,TCFullName, int rep) {

		Map TCMap = [:]

		my.Log.addINFO('\t' + TCName.padRight(24) + TCFullName.padRight(90) + rep.toString().padLeft(3))

		TCMap.put('TCNAME',TCName)
		TCMap.put('TCFULLNAME',TCFullName)
		TCMap.put('REP',rep)

		this.testCasesList.add(TCMap)

	}



	private static Sheet readSequencerFile() {

		//File sourceExcel = new File(my.PropertiesReader.getMyProperty('SEQUENCER_PATH') + File.separator + my.PropertiesReader.getMyProperty('SEQUENCER_FILENAME'))
		//FileInputStream fxls = new FileInputStream(sourceExcel)
		//XSSFWorkbook book = new XSSFWorkbook(fxls)

		XSSFWorkbook book = my.XLS.open(my.PropertiesReader.getMyProperty('SEQUENCER_PATH') + File.separator + my.PropertiesReader.getMyProperty('SEQUENCER_FILENAME'))

		return book.getSheet(my.PropertiesReader.getMyProperty('SEQUENCER_SHEETNAME'))

	}



} //end of class
