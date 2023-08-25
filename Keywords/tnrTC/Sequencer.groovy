package tnrTC

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import groovy.transform.CompileStatic
import tnrCommon.TNRPropertiesReader
import tnrLog.Log


@CompileStatic
public class Sequencer {

	/*
	 * Read the TNRSequencer File
	 * 
	 * 
	 * 
	 */


	public static List testCasesList = []
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
	public static load() {
		
		if (testCasesList.isEmpty()){
*/
	static {
			Log.addSubTITLE('Load testCasesList from TNR sequencer file','-',120)
			Log.addINFO("\t" + 'CDTPATTERN'.padRight(24) + 'TCFULLNAME'.padRight(90) + 'REP')
			Log.addINFO("")
	
			// read JDD
			Sheet shTNR = readSequencerFile()
	
			Log.addTrace('shTNR.getLastRowNum() :' + shTNR.getLastRowNum())
	
			// for each data line
			for (int numline : (1..shTNR.getLastRowNum())) {
	
				Row row = shTNR.getRow(numline)
	
				// exit if lastRow of CAS_DE_TEST
				if (!row || tnrCommon.ExcelUtils.getCellValue(row.getCell(0))=='') {
					break
				}
	
				String casDeTestPatternFromSequencer = row.getCell(0).getStringCellValue()
	
				Log.addTrace('casDeTestPatternFromSequencer = ' + casDeTestPatternFromSequencer)
				
				String repStr = tnrCommon.ExcelUtils.getCellValue(row.getCell(1)).toString()
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

	
				Map res= TCFileMapper.TCfileMap.findAll { it.key.contains(casDeTestPatternFromSequencer) }
	
				if (res.size()==0) {
	
					def key = TCFileMapper.TCfileMap.keySet().find { casDeTestPatternFromSequencer.contains(it) }
	
					if (key) {
						addToTestCasesList(casDeTestPatternFromSequencer,TCFileMapper.TCfileMap[key], rep)
					}else {
						Log.add('WARNING',"\tPas de fichier trouvé pour le pattern $casDeTestPatternFromSequencer")
					}
				}else {
					res.each {
						addToTestCasesList(it.key,it.value, rep)
					}
				}
	
			} // end of for each data line
		//}
	}//end of constructor




	private static addToTestCasesList (String TCName,String TCFullName, int rep) {

		Map TCMap = [:]

		Log.addINFO('\t' + TCName.padRight(24) + TCFullName.padRight(90) + rep.toString().padLeft(3))

		TCMap.put('CDTPATTERN',TCName)
		TCMap.put('TCFULLNAME',TCFullName)
		TCMap.put('REP',rep)

		testCasesList.add(TCMap)

	}



	private static Sheet readSequencerFile() {

		//File sourceExcel = new File(TNRPropertiesReader.getMyProperty('SEQUENCER_PATH') + File.separator + TNRPropertiesReader.getMyProperty('SEQUENCER_FILENAME'))
		//FileInputStream fxls = new FileInputStream(sourceExcel)
		//XSSFWorkbook book = new XSSFWorkbook(fxls)

		XSSFWorkbook book = tnrCommon.ExcelUtils.open(TNRPropertiesReader.getMyProperty('SEQUENCER_PATH') + File.separator + TNRPropertiesReader.getMyProperty('SEQUENCER_FILENAME'))

		return book.getSheet(TNRPropertiesReader.getMyProperty('SEQUENCER_SHEETNAME'))

	}







} //end of class
