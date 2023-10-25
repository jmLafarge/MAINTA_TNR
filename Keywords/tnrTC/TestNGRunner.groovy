package tnrTC

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.testng.TestNG
import org.testng.xml.XmlClass
import org.testng.xml.XmlSuite
import org.testng.xml.XmlTest

import tnrCommon.ExcelUtils
import tnrCommon.TNRPropertiesReader
import tnrCommon.Tools
import tnrJDDManager.GlobalJDD
import tnrResultManager.TNRResult



/**
 *
 * @author JM Lafarge
 * @version 1.0
 *
 */
//@CompileStatic
public class TestNGRunner {
	
	private static XmlSuite xmlSuite = new XmlSuite()
	private static XmlTest xmlTest = new XmlTest(xmlSuite)
	
	
	public static boolean doesClassExist(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	
	
	public static String findValidClass(String pattern) {
	
		// Vérifier si la première classe existe
		if (doesClassExist(pattern)) {
			return pattern;
		}
	
		String classWihout_nn = pattern.substring(0, pattern.lastIndexOf("_"));
		// Class sans _nn
		if (doesClassExist(classWihout_nn)) {
			return classWihout_nn;
		}
	
		// Aucune classe valide trouvée
		System.out.println("La classe " + pattern + " n'existe pas !");
		return null;
	}
	
	

	public static List<String> readClassesFromExcel() throws Exception {
		
		String tnrPath = TNRPropertiesReader.getMyProperty('TNR_PATH')
		String sequencerFilename = TNRPropertiesReader.getMyProperty('SEQUENCER_FILENAME')
		String sequencerSheetName = TNRPropertiesReader.getMyProperty('SEQUENCER_SHEETNAME')
		FileInputStream fis = new FileInputStream(new File(tnrPath + File.separator + sequencerFilename));
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheet(sequencerSheetName)
		List<String> myClasses = []
	    for (numLine in (1..sheet.getLastRowNum())) {
	        Row row = sheet.getRow(numLine)
	        if (row) {
	            Cell cell = row.getCell(0)
	            if (cell) {
	                myClasses.add(cell.getStringCellValue())
	            }
	        }
	    }
		workbook.close();
		return myClasses;
	}
	
	

	//public static XmlTest createTestNGXml(List<String> myClasses) {
	public static createTestNGXml(List<String> myClasses) {
		List<XmlClass> xmlClasses = []
		for (String className : myClasses) {
			className = className.replace('.', '_')
			String packageName = className.split("_")[0] + "_" + className.split("_")[1];
			String fullClassName = "tnrTC." + packageName + "." + className;
			String validClass = findValidClass(fullClassName)
			if (validClass) {
				xmlClasses.add(new XmlClass(validClass));
			}
		}
		/*
		XmlTest xmlTest = new XmlTest();
		xmlTest.setName("TNRTest");
		xmlTest.setVerbose(1); // Définir explicitement le niveau de verbose
		xmlTest.setXmlClasses(xmlClasses);
		return xmlTest;
		*/
		xmlTest.setName("TNRTest");
		//xmlTest.setVerbose(1); // Définir explicitement le niveau de verbose
		xmlTest.setXmlClasses(xmlClasses);
		return xmlTest;
	}
	
	

	public static void main(String[] args) throws Exception {
		TNRResult.addStartInfo('TNR TEST')
		Tools.logInfoContext()
		GlobalJDD.myGlobalJDD
		
		List<String> myClasses = readClassesFromExcel();
		
		
		//XmlSuite xmlSuite = new XmlSuite();
		
		
		//XmlTest xmlTest = 
		
		createTestNGXml(myClasses);
		
		xmlSuite.setName("TNRSuite");
		xmlSuite.setTests([xmlTest]);
		
		println 'xmlTest:------------------------------------------------------------------'
		println xmlTest
		println '------------------------------------------------------------------'
		
		TestNG testng = new TestNG();
		testng.setXmlSuites([xmlSuite]);
		
		println 'xmlSuite:------------------------------------------------------------------'
		println xmlSuite
		println '------------------------------------------------------------------'
		
		testng.run();
		TNRResult.closeTNR('TNR SEQUENCER')
	}
	

	public static void runWithXml(String[] args) {

		TNRResult.addStartInfo('TNR TEST')
		Tools.logInfoContext()
		GlobalJDD.myGlobalJDD
		TestNG testng = new TestNG();
		List<String> suites = new ArrayList<>();
		suites.add("tnr.xml");
		testng.setTestSuites(suites);
		testng.run();
		TNRResult.closeTNR('TNR SEQUENCER')
	}


}
